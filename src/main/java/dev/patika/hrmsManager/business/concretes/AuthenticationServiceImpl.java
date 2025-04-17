package dev.patika.hrmsManager.business.concretes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import dev.patika.hrmsManager.business.abstracts.IAuthenticationService;
import dev.patika.hrmsManager.core.exception.NotFoundException;
import dev.patika.hrmsManager.dao.RefreshTokenDao;
import dev.patika.hrmsManager.dao.UserDao;
import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.ActionType;
import dev.patika.hrmsManager.entities.User;
import dev.patika.hrmsManager.jwt.AuthRequest;
import dev.patika.hrmsManager.jwt.JWTService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.patika.hrmsManager.entities.RefreshToken;
import dev.patika.hrmsManager.jwt.AuthResponse;
import dev.patika.hrmsManager.jwt.RefreshTokenRequest;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import lombok.RequiredArgsConstructor;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserDao userDao;

    private final EventLogServiceImpl eventLogService;
    private final AuthenticationProvider authenticationProvider;

    private final JWTService jwtService;

    private final RefreshTokenDao refreshTokenDao;
    private final IModelMapperService modelMapper;

    public AuthenticationServiceImpl(BCryptPasswordEncoder passwordEncoder, UserDao userDao, EventLogServiceImpl eventLogService, AuthenticationProvider authenticationProvider, JWTService jwtService, RefreshTokenDao refreshTokenDao, IModelMapperService modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.eventLogService = eventLogService;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
        this.refreshTokenDao = refreshTokenDao;
        this.modelMapper = modelMapper;
    }

    private User createUser(UserSaveRequest  request) {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        return refreshToken;
    }

    private boolean isRefreshTokenValid(Date expireDate) {
        return new Date().before(expireDate);
    }

    @Override
    public UserResponse register(UserSaveRequest request) {

        User user = createUser(request);
        User savedUser = userDao.save(user);

        eventLogService.createLogEventTest(savedUser,ActionType.OTHER,"USER CREATED :");

        return modelMapper.forResponse().map(savedUser, UserResponse.class);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {

        logger.info("Authentication attempt for user: {}", authRequest.getUsername());


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        try {
            authenticationProvider.authenticate(authenticationToken);
            Optional<User> optional = userDao.findByName(authRequest.getUsername());

            User user = optional.get();

            String accessToken = jwtService.generateToken(user);
            RefreshToken savedRefreshToken = refreshTokenDao.save(createRefreshToken(user));

            user.setFailedLoginAttempts(0);
            userDao.save(user);

            logger.info("Successful authentication for user: {} | Last login time: {}",
                    user.getName(),
                    LocalDateTime.now());

            return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
        } catch (Exception e) {

            Optional<User> optional = userDao.findByName(authRequest.getUsername());

            User user = optional.get();
            if (user.getFailedLoginAttempts() >= 3) {
                throw new NotFoundException("USER_BLOCKED");
            }

            int failedAttempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failedAttempts);
            userDao.save(user);

            logger.warn("Failed login attempt #{} for user: {} | Time: {}",
                    failedAttempts,
                    user.getName(),
                    LocalDateTime.now()
            );

            throw new NotFoundException("USERNAME_OR_PASSWORD_INVALID");
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optRefreshToken = refreshTokenDao.findByRefreshToken(request.getRefreshToken());
        if(optRefreshToken.isEmpty()) {
            throw new NotFoundException("REFRESH_TOKEN_NOT_FOUND");
        }

        if(!isRefreshTokenValid(optRefreshToken.get().getExpireDate())) {
            throw new NotFoundException("REFRESH_TOKEN_IS_EXPIRED");
        }

        String accessToken = jwtService.generateToken(optRefreshToken.get().getUser());
        RefreshToken savedRefreshToken = refreshTokenDao.save(createRefreshToken(optRefreshToken.get().getUser()));



        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

}

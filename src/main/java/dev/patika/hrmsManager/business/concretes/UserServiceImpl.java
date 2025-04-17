package dev.patika.hrmsManager.business.concretes;

import dev.patika.hrmsManager.business.abstracts.IUserService;
import dev.patika.hrmsManager.core.exception.NotFoundException;
import dev.patika.hrmsManager.core.utilities.Msg;
import dev.patika.hrmsManager.dao.EventLogDao;
import dev.patika.hrmsManager.dao.UserDao;
import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.request.UserUpdateRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.ActionType;
import dev.patika.hrmsManager.entities.User;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;

    private final EventLogServiceImpl eventLogService;
    private final IModelMapperService modelMapper;

    public UserServiceImpl(UserDao userDao, EventLogServiceImpl eventLogService, IModelMapperService modelMapper) {
        this.userDao = userDao;
        this.eventLogService = eventLogService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse createUser(UserSaveRequest userSaveRequest) {
        User user = modelMapper.forRequest().map(userSaveRequest, User.class);
        User savedUser = userDao.save(user);

        eventLogService.createLogEvent(ActionType.OTHER,"USER CREATED :" + savedUser.getId());


        return modelMapper.forResponse().map(savedUser, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userDao.findAll();

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW ALL USERS");

        return users.stream()
                .map(user -> modelMapper.forResponse().map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.USER_NOT_FOUND));

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW USER BY ID :" + id);

        return modelMapper.forResponse().map(user, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userDTO) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.USER_NOT_FOUND));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setBlocked(userDTO.isBlocked());
        user.setFailedLoginAttempts(userDTO.getFailedLoginAttempts());


        User updatedUser = userDao.save(user);



        return modelMapper.forResponse().map(updatedUser, UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.USER_NOT_FOUND));
        userDao.delete(user);

        eventLogService.createLogEvent(ActionType.OTHER," USER DELETED : " + id);
    }

    @Override
    public Page<User> findAllPageable(Pageable pageable) {

        Page<User> allUser = userDao.findAll(pageable);

        eventLogService.createLogEvent(ActionType.OTHER," VIEW ALL USERS PAGEABLE ");

        return allUser;
    }

    @Override
    public List<UserResponse> toDTOList(List<User> userList) {

        List<UserResponse> dtoList = new ArrayList<>();

        for (User user: userList) {

            // User nesnesini UserResponse'a dönüştür
            UserResponse dtoUser = modelMapper.forResponse().map(user, UserResponse.class);

            // dtoUser'u listeye ekle
            dtoList.add(dtoUser);
        }

        return dtoList;
    }

}


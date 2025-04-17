package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.business.abstracts.IAuthenticationService;
import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.ResultHelper;
import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.jwt.AuthRequest;
import dev.patika.hrmsManager.jwt.AuthResponse;
import dev.patika.hrmsManager.jwt.RefreshTokenRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    // TODO Burayı UDEMY security kısmını dinledikten sonra tekrar bak

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<UserResponse> register(@Valid @RequestBody UserSaveRequest request) {
        return ResultHelper.created(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResultHelper.success(authenticationService.authenticate(request));
    }

    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResultHelper.success(authenticationService.refreshToken(request));
    }
}

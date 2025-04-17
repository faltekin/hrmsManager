package dev.patika.hrmsManager.business.abstracts;

import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.jwt.AuthRequest;
import dev.patika.hrmsManager.jwt.AuthResponse;
import dev.patika.hrmsManager.jwt.RefreshTokenRequest;

public interface IAuthenticationService {

    UserResponse register(UserSaveRequest request);


    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse refreshToken(RefreshTokenRequest request);
}

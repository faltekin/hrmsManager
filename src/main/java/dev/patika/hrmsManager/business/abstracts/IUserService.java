package dev.patika.hrmsManager.business.abstracts;

import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.request.UserUpdateRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserSaveRequest userSaveRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long id);


    List<UserResponse> toDTOList(List<User> personelList);

    Page<User> findAllPageable(Pageable pageable);
}

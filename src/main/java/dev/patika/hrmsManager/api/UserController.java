package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.api.abstracts.IUserController;
import dev.patika.hrmsManager.business.abstracts.IUserService;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.core.result.Result;
import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.core.utilities.ResultHelper;
import dev.patika.hrmsManager.dto.request.UserSaveRequest;
import dev.patika.hrmsManager.dto.request.UserUpdateRequest;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController extends BaseController implements IUserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // TODO User üzerinden yapılan save işlemi kaldırılacak
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<UserResponse> createUser(@RequestBody UserSaveRequest userSaveRequest) {
        return ResultHelper.created(userService.createUser(userSaveRequest));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<UserResponse>> getAllUsers() {
        return ResultHelper.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<UserResponse> getUserById(@PathVariable Long id) {
        return ResultHelper.success(userService.getUserById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResultHelper.success(userService.updateUser(id, userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResultHelper.Ok();
    }

    @GetMapping("/list/pageable")
    @Override
    public ResultData<RestPageableEntity<UserResponse>> findAllPageable(RestPageableRequest pageable) {
        Page<User> page = userService.findAllPageable(toPageable(pageable));

        RestPageableEntity<UserResponse> pageableResponse = toPageableResponse(page, userService.toDTOList(page.getContent()));

        return ResultHelper.success(pageableResponse);
    }
}

package dev.patika.hrmsManager.api.abstracts;

import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.dto.response.LeaveResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;

public interface ILeaveController {

    ResultData<RestPageableEntity<LeaveResponse>> findAllPageable(RestPageableRequest pageable);
}

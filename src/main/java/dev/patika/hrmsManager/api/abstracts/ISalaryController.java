package dev.patika.hrmsManager.api.abstracts;

import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.dto.response.SalaryResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;

public interface ISalaryController {

    ResultData<RestPageableEntity<SalaryResponse>> findAllPageable(RestPageableRequest pageable);
}

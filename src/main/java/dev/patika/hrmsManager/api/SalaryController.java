package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.api.abstracts.IEventLogController;
import dev.patika.hrmsManager.api.abstracts.ISalaryController;
import dev.patika.hrmsManager.business.abstracts.ISalaryService;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.core.result.Result;
import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.core.utilities.ResultHelper;
import dev.patika.hrmsManager.dto.request.SalarySaveRequest;
import dev.patika.hrmsManager.dto.response.SalaryResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.Salary;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/salaries")
public class SalaryController extends BaseController implements ISalaryController {

    private final ISalaryService salaryService;

    public SalaryController(ISalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<SalaryResponse> createSalary(@RequestBody SalarySaveRequest salarySaveRequest) {
        return ResultHelper.created(salaryService.createSalary(salarySaveRequest));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<SalaryResponse>> getAllSalaries() {
        return ResultHelper.success(salaryService.getAllSalaries());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<SalaryResponse> getSalaryById(@PathVariable Long id) {
        return ResultHelper.success(salaryService.getSalaryById(id));
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<SalaryResponse>> getSalariesByUserId(@PathVariable Long userId) {
        return ResultHelper.success(salaryService.getSalariesByUserId(userId));
    }

    @GetMapping("/list/pageable")
    @Override
    public ResultData<RestPageableEntity<SalaryResponse>> findAllPageable(RestPageableRequest pageable) {

        Page<Salary> page = salaryService.findAllPageable(toPageable(pageable));

        RestPageableEntity<SalaryResponse> pageableResponse = toPageableResponse(page, salaryService.toDTOList(page.getContent()));

        return ResultHelper.success(pageableResponse);


    }
}

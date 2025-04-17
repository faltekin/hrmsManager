package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.api.abstracts.IEventLogController;
import dev.patika.hrmsManager.api.abstracts.ILeaveController;
import dev.patika.hrmsManager.business.abstracts.ILeaveService;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.core.result.Result;
import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.core.utilities.ResultHelper;
import dev.patika.hrmsManager.dto.request.LeaveSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.LeaveResponse;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.Leave;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/leaves")
public class LeaveController extends BaseController implements ILeaveController {

    private final ILeaveService leaveService;

    public LeaveController(ILeaveService leaveService) {
        this.leaveService = leaveService;

    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<LeaveResponse> createLeaveRequest(@RequestBody LeaveSaveRequest leaveSaveRequest) {
        return ResultHelper.created(leaveService.createLeaveRequest(leaveSaveRequest));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<LeaveResponse>> getAllLeaves() {
        return ResultHelper.success(leaveService.getAllLeaves());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<LeaveResponse> getLeaveById(@PathVariable Long id) {
        return ResultHelper.success(leaveService.getLeaveById(id));
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<LeaveResponse>> getLeavesByUserId(@PathVariable Long userId) {
        return ResultHelper.success(leaveService.getLeavesByUserId(userId));
    }

    @GetMapping("/list/pageable")
    @Override
    public ResultData<RestPageableEntity<LeaveResponse>> findAllPageable(RestPageableRequest pageable) {

        Page<Leave> page = leaveService.findAllPageable(toPageable(pageable));

        RestPageableEntity<LeaveResponse> pageableResponse = toPageableResponse(page, leaveService.toDTOList(page.getContent()));

        return ResultHelper.success(pageableResponse);
    }
}

package dev.patika.hrmsManager.api;

import dev.patika.hrmsManager.api.abstracts.IEventLogController;
import dev.patika.hrmsManager.api.abstracts.IUserController;
import dev.patika.hrmsManager.business.abstracts.IEventLogService;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.core.result.Result;
import dev.patika.hrmsManager.core.result.ResultData;
import dev.patika.hrmsManager.core.utilities.RestPageableEntity;
import dev.patika.hrmsManager.core.utilities.RestPageableRequest;
import dev.patika.hrmsManager.core.utilities.ResultHelper;
import dev.patika.hrmsManager.dto.request.EventLogSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/eventLogs")
public class EventLogController extends BaseController implements IEventLogController {

    private final IEventLogService eventLogService;


    public EventLogController(IEventLogService eventLogService) {
        this.eventLogService = eventLogService;

    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<EventLogResponse> createEventLog(@RequestBody EventLogSaveRequest eventLogSaveRequest) {
        return ResultHelper.created(eventLogService.createEventLog(eventLogSaveRequest));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<EventLogResponse>> getAllEventLogs() {
        return ResultHelper.success(eventLogService.getAllEventLogs());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<EventLogResponse> getEventLogById(@PathVariable Long id) {
        return ResultHelper.success(eventLogService.getEventLogById(id));
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<EventLogResponse>> getEventLogsByUserId(@PathVariable Long userId) {
        return ResultHelper.success(eventLogService.getEventLogsByUserId(userId));
    }

    @GetMapping("/list/pageable")
    @Override
    public ResultData<RestPageableEntity<EventLogResponse>> findAllPageable(RestPageableRequest pageable) {

        Page<EventLog> page = eventLogService.findAllPageable(toPageable(pageable));

        RestPageableEntity<EventLogResponse> pageableResponse = toPageableResponse(page, eventLogService.toDTOList(page.getContent()));

        return ResultHelper.success(pageableResponse);


    }
}

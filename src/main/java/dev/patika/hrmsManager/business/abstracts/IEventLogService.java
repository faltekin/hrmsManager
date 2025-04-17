package dev.patika.hrmsManager.business.abstracts;


import dev.patika.hrmsManager.dto.request.EventLogSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.ActionType;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventLogService {

    EventLogResponse createEventLog(EventLogSaveRequest eventLogSaveRequest);

    List<EventLogResponse> getAllEventLogs();

    List<EventLogResponse> getEventLogsByUserId(Long userId);

    EventLogResponse getEventLogById(Long id);

    void createLogEvent(ActionType actionType, String details);

    List<EventLogResponse> toDTOList(List<EventLog> eventList);

    Page<EventLog> findAllPageable(Pageable pageable);

}

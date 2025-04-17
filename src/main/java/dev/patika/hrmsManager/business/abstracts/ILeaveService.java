package dev.patika.hrmsManager.business.abstracts;

import dev.patika.hrmsManager.dto.request.LeaveSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.LeaveResponse;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILeaveService {

    LeaveResponse createLeaveRequest(LeaveSaveRequest leaveSaveRequest);

    List<LeaveResponse> getAllLeaves();

    LeaveResponse getLeaveById(Long id);

    List<LeaveResponse> getLeavesByUserId(Long userId);

    List<LeaveResponse> toDTOList(List<Leave> leaveList);

    Page<Leave> findAllPageable(Pageable pageable);
}

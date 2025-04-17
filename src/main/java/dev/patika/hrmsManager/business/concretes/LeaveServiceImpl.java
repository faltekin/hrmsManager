package dev.patika.hrmsManager.business.concretes;

import dev.patika.hrmsManager.business.abstracts.ILeaveService;
import dev.patika.hrmsManager.core.exception.NotFoundException;
import dev.patika.hrmsManager.core.utilities.Msg;
import dev.patika.hrmsManager.dao.LeaveDao;
import dev.patika.hrmsManager.dao.UserDao;
import dev.patika.hrmsManager.dto.request.LeaveSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.LeaveResponse;
import dev.patika.hrmsManager.entities.ActionType;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.Leave;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements ILeaveService {

    private final LeaveDao leaveDao;
    private final UserDao userDao;

    private final EventLogServiceImpl eventLogService;
    private final IModelMapperService modelMapper;

    public LeaveServiceImpl(LeaveDao leaveDao, UserDao userDao, EventLogServiceImpl eventLogService, IModelMapperService modelMapper) {
        this.leaveDao = leaveDao;
        this.userDao = userDao;
        this.eventLogService = eventLogService;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeaveResponse createLeaveRequest(LeaveSaveRequest leaveSaveRequest) {

        Leave leave = modelMapper.forRequest().map(leaveSaveRequest, Leave.class);
        User user = this.userDao.getById(leaveSaveRequest.getUserId());

        leave.setUser(user);
        Leave savedLeave = leaveDao.save(leave);

        eventLogService.createLogEvent(ActionType.OTHER," LEAVE CREATED :" + savedLeave.getUser().getId());


        return modelMapper.forResponse().map(savedLeave, LeaveResponse.class);
    }


    @Override
    public List<LeaveResponse> getAllLeaves() {
        List<Leave> leaves = leaveDao.findAll();

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW ALL LEAVES");

        return leaves.stream()
                .map(leave -> modelMapper.forResponse().map(leave, LeaveResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeaveResponse getLeaveById(Long id) {
        Leave leave = leaveDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW LEAVES BY ID : " + id);

        return modelMapper.forResponse().map(leave, LeaveResponse.class);
    }

    @Override
    public List<LeaveResponse> getLeavesByUserId(Long userId) {

        if (!userDao.existsById(userId)) {
            throw new NotFoundException(Msg.USER_NOT_FOUND);
        }

        List<Leave> leaves = leaveDao.findByUserId(userId);

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW LEAVES BY USER ID :" + userId);

        return leaves.stream()
                .map(leave -> modelMapper.forResponse().map(leave, LeaveResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Leave> findAllPageable(Pageable pageable) {

        Page<Leave> allLeave = leaveDao.findAll(pageable);

        eventLogService.createLogEvent(ActionType.OTHER," VIEW ALL LEAVES PAGEABLE ");

        return allLeave;
    }

    @Override
    public List<LeaveResponse> toDTOList(List<Leave> leaveList) {

        List<LeaveResponse> dtoList = new ArrayList<>();

        for (Leave leave: leaveList) {

            LeaveResponse dtoEvent = modelMapper.forResponse().map(leave, LeaveResponse.class);
            dtoList.add(dtoEvent);
        }

        return dtoList;
    }



}

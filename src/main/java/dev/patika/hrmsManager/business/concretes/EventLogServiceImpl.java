package dev.patika.hrmsManager.business.concretes;

import dev.patika.hrmsManager.business.abstracts.IEventLogService;
import dev.patika.hrmsManager.core.exception.NotFoundException;
import dev.patika.hrmsManager.core.utilities.Msg;
import dev.patika.hrmsManager.dao.EventLogDao;
import dev.patika.hrmsManager.dao.UserDao;
import dev.patika.hrmsManager.dto.request.EventLogSaveRequest;
import dev.patika.hrmsManager.dto.response.EventLogResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.ActionType;
import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;

import dev.patika.hrmsManager.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventLogServiceImpl implements IEventLogService {

    private final EventLogDao eventLogDao;
    private final UserDao userDao;
    private final IModelMapperService modelMapper;

    public EventLogServiceImpl(EventLogDao eventLogDao, UserDao userDao, IModelMapperService modelMapper) {
        this.eventLogDao = eventLogDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public EventLogResponse createEventLog(EventLogSaveRequest eventLogSaveRequest) {

        EventLog eventLog = modelMapper.forRequest().map(eventLogSaveRequest, EventLog.class);
        User user = this.userDao.getById(eventLogSaveRequest.getUserId());

        eventLog.setUser(user);
        EventLog savedEventLog = eventLogDao.save(eventLog);

        return modelMapper.forResponse().map(savedEventLog, EventLogResponse.class);
    }


    @Override
    public List<EventLogResponse> getAllEventLogs() {
        List<EventLog> eventLogs = eventLogDao.findAll();
        return eventLogs.stream()

                .map(eventLog -> modelMapper.forResponse().map(eventLog, EventLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventLogResponse> getEventLogsByUserId(Long userId) {

        if (!userDao.existsById(userId)) {
            throw new NotFoundException(Msg.USER_NOT_FOUND);
        }

        List<EventLog> eventLogs = eventLogDao.findByUserId(userId);
        return eventLogs.stream()
                .map(eventLog -> modelMapper.forResponse().map(eventLog, EventLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventLogResponse getEventLogById(Long id) {
        EventLog eventLog = eventLogDao.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.EVENT_NOT_FOUND));


        return modelMapper.forResponse().map(eventLog, EventLogResponse.class);
    }

    @Override
    public void createLogEvent(ActionType actionType, String details) {

        // Şu anki kullanıcıyı SecurityContext üzerinden alıyoruz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Token'dan alınan kullanıcı adı

        // Şu anki kullanıcıyı veritabanından alıyoruz (user_id'sini elde etmek için)
        User currentUser = userDao.findByName(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));


        EventLog log = new EventLog();
        log.setUser(currentUser);
        log.setActionType(actionType);
        log.setDetails(details);

        eventLogDao.save(log);

    }

    public void createLogEventTest(User user,ActionType actionType, String details) {


        EventLog log = new EventLog();
        log.setUser(user);
        log.setActionType(actionType);
        log.setDetails(details);

        eventLogDao.save(log);

    }




    @Override
    public Page<EventLog> findAllPageable(Pageable pageable) {

        Page<EventLog> allEvent = eventLogDao.findAll(pageable);

        return allEvent;
    }


    @Override
    public List<EventLogResponse> toDTOList(List<EventLog> eventList) {

        List<EventLogResponse> dtoList = new ArrayList<>();

        for (EventLog event: eventList) {

            EventLogResponse dtoEvent = modelMapper.forResponse().map(event, EventLogResponse.class);

            dtoList.add(dtoEvent);
        }

        return dtoList;


    }




}

package dev.patika.hrmsManager.business.concretes;

import dev.patika.hrmsManager.business.abstracts.ISalaryService;
import dev.patika.hrmsManager.core.exception.NotFoundException;
import dev.patika.hrmsManager.core.utilities.Msg;
import dev.patika.hrmsManager.dao.SalaryDao;
import dev.patika.hrmsManager.dao.UserDao;
import dev.patika.hrmsManager.dto.request.SalarySaveRequest;
import dev.patika.hrmsManager.dto.response.SalaryResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.Salary;
import dev.patika.hrmsManager.core.config.modelMapper.IModelMapperService;
import dev.patika.hrmsManager.entities.User;
import dev.patika.hrmsManager.entities.ActionType;
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
public class SalaryServiceImpl implements ISalaryService {

    private final SalaryDao salaryDao;
    private final UserDao userDao;
    private final EventLogServiceImpl eventLogService;

    private final IModelMapperService modelMapper;

    public SalaryServiceImpl(SalaryDao salaryDao, UserDao userDao, EventLogServiceImpl eventLogService, IModelMapperService modelMapper) {
        this.salaryDao = salaryDao;
        this.userDao = userDao;
        this.eventLogService = eventLogService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SalaryResponse createSalary(SalarySaveRequest salarySaveRequest) {

        Salary salary = modelMapper.forRequest().map(salarySaveRequest, Salary.class);
        User user = this.userDao.getById(salarySaveRequest.getUserId());

        salary.setUser(user);
        Salary savedSalary = salaryDao.save(salary);

        eventLogService.createLogEvent(ActionType.OTHER," SALARY CREATED :" + savedSalary.getUser().getId());

        return modelMapper.forResponse().map(savedSalary, SalaryResponse.class);
    }


    @Override
    public List<SalaryResponse> getAllSalaries() {
        List<Salary> salaries = salaryDao.findAll();

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW ALL SALARIES");

        return salaries.stream()
                .map(salary -> modelMapper.forResponse().map(salary, SalaryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public SalaryResponse getSalaryById(Long id) {
        Salary salary = salaryDao.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.SALARY_NOT_FOUND));

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW SALARIES BY SALARY ID : " + id);

        return modelMapper.forResponse().map(salary, SalaryResponse.class);
    }

    @Override
    public List<SalaryResponse> getSalariesByUserId(Long userId) {

       if (!userDao.existsById(userId)) {
            throw new NotFoundException(Msg.USER_NOT_FOUND);
       }

        List<Salary> salaries = salaryDao.findByUserId(userId);

        eventLogService.createLogEvent(ActionType.OTHER,"VIEW SALARIES BY USER ID :" + userId);

        return salaries.stream()
                .map(salary -> modelMapper.forResponse().map(salary, SalaryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Salary> findAllPageable(Pageable pageable) {

        Page<Salary> allSalary = salaryDao.findAll(pageable);

        eventLogService.createLogEvent(ActionType.OTHER," VIEW ALL SALARIES PAGEABLE ");


        return allSalary;
    }

    @Override
    public List<SalaryResponse> toDTOList(List<Salary> salaryList) {

        List<SalaryResponse> dtoList = new ArrayList<>();

        for (Salary salary: salaryList) {

            SalaryResponse dtoSalary = modelMapper.forResponse().map(salary, SalaryResponse.class);

            dtoList.add(dtoSalary);
        }

        return dtoList;
    }


}

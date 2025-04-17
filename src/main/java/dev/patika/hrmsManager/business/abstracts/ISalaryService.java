package dev.patika.hrmsManager.business.abstracts;


import dev.patika.hrmsManager.dto.request.SalarySaveRequest;
import dev.patika.hrmsManager.dto.response.SalaryResponse;
import dev.patika.hrmsManager.dto.response.UserResponse;
import dev.patika.hrmsManager.entities.Salary;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISalaryService {

    SalaryResponse createSalary(SalarySaveRequest salarySaveRequest);

    List<SalaryResponse> getAllSalaries();

    SalaryResponse getSalaryById(Long id);

    List<SalaryResponse> getSalariesByUserId(Long userId);

    List<SalaryResponse> toDTOList(List<Salary> salaryList);

    Page<Salary> findAllPageable(Pageable pageable);
}

package dev.patika.hrmsManager.dao;

import dev.patika.hrmsManager.entities.Leave;
import dev.patika.hrmsManager.entities.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryDao extends JpaRepository<Salary, Long> {

    List<Salary> findByUserId(Long userId);
    List<Salary> findByPaymentDateBefore(LocalDate paymentDate);

    Page<Salary> findAll(Pageable pageable);
}

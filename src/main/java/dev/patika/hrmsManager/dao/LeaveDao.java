package dev.patika.hrmsManager.dao;

import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveDao extends JpaRepository<Leave, Long> {

    List<Leave> findByUserId(Long userId);

    List<Leave> findByStartDateAfter(LocalDate startDate);

    List<Leave> findByEndDateBefore(LocalDate endDate);

    Page<Leave> findAll(Pageable pageable);
}

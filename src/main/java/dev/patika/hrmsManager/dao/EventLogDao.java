package dev.patika.hrmsManager.dao;

import dev.patika.hrmsManager.entities.EventLog;
import dev.patika.hrmsManager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventLogDao extends JpaRepository<EventLog, Long> {

    List<EventLog> findByUserId(Long userId);

    List<EventLog> findByActionType(String actionType);

    List<EventLog> findByEventTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<EventLog> findById(Long id);

    Page<EventLog> findAll(Pageable pageable);
}


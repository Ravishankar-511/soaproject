package soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import soa.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
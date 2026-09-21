package soa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import soa.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentId(Long studentId);
}
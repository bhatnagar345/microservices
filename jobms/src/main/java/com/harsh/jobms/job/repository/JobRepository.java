package com.harsh.jobms.job.repository;


import com.harsh.jobms.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}

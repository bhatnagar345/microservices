package com.harsh.jobms.job.mapper;

import com.harsh.jobms.external.Company;
import com.harsh.jobms.external.Review;
import com.harsh.jobms.job.Job;
import com.harsh.jobms.job.dto.JobDTO;

import java.util.List;

public class JobMapper {
    public static JobDTO mapDTOwithJobAndCompan(Job job, Company company, List<Review> reviews)
    {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setCompany(company);
        jobDTO.setId(job.getId());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}

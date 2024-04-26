package com.harsh.jobms.job.service;

import com.harsh.jobms.external.Company;
import com.harsh.jobms.external.Review;
import com.harsh.jobms.job.Job;
import com.harsh.jobms.job.client.CompanyClient;
import com.harsh.jobms.job.client.ReviewClient;
import com.harsh.jobms.job.dto.JobDTO;
import com.harsh.jobms.job.mapper.JobMapper;
import com.harsh.jobms.job.repository.JobRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{
//    private List<Job> jobs = new ArrayList<>();

    JobRepository jobRepository;

//    @Autowired
//    RestTemplate restTemplate;

    private CompanyClient companyClient;

    private ReviewClient reviewClient;
    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker",
//                    fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
//    @RateLimiter(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
//        RestTemplate restTemplate = new RestTemplate();
//        for(Job job : jobs)
//        {
//          jobDTOS.add(convertDTO(job));
//        }
//        return jobDTOS;
        return jobs.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e)
    {
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    private JobDTO convertDTO(Job job)
    {
//        Company company = restTemplate.getForObject("http://companyms:8081/companies/"+ job.getCompanyID(),
//                Company.class);
//
//        ResponseEntity<List<Review>> responseEntity = restTemplate.exchange(
//                "http://reviewms:8083/reviews?companyId=" + job.getCompanyID(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//        List<Review> reviews = responseEntity.getBody();

        Company company = companyClient.getCompany(job.getCompanyID());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyID());

        return JobMapper.mapDTOwithJobAndCompan(job,company,reviews);
    }

    @Override
    public void createJobs(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if(jobOptional.isPresent())
            {
                Job theJob = jobOptional.get();
                theJob.setTitle(updatedJob.getTitle());
                theJob.setDescription(updatedJob.getDescription());
                theJob.setMinSalary(updatedJob.getMinSalary());
                theJob.setMaxSalary(updatedJob.getMaxSalary());
                theJob.setLocation(updatedJob.getLocation());
                theJob.setCompanyID(updatedJob.getCompanyID());
                jobRepository.save(theJob);
                return true;
            }

        return false;
    }
}

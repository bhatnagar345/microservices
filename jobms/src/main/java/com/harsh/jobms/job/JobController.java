package com.harsh.jobms.job;

import com.harsh.jobms.job.dto.JobDTO;
import com.harsh.jobms.job.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

  private JobService jobService;

  public JobController(JobService theJobService)
  {
      jobService = theJobService;
  }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll()
    {
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJobs(@RequestBody Job job)
    {
        jobService.createJobs(job);

        return new ResponseEntity<>("Jobs Added Successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable Long id)
    {

        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO != null)
            return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id)
    {
          boolean deleted = jobService.deleteJobById(id);

          if(deleted)
          {
              return new ResponseEntity<>("Deleted Job Successfully",HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
//    @RequestMapping(value = "jobs/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id,
                                            @RequestBody Job updatedJob)
    {
        boolean updated = jobService.updateJobById(id,updatedJob);
        if(updated)
            return new ResponseEntity<>("Job updated",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

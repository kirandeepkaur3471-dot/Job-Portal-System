package com.kiran.jobportal.controller;

import com.kiran.jobportal.model.Job;
import com.kiran.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin("*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/post")
    public String postJob(@RequestBody Job job) {

        jobRepository.save(job);

        return "Job posted successfully!";
    }

    @GetMapping
    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }
}
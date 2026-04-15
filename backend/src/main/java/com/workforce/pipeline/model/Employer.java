package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;

import java.util.ArrayList;

public class Employer extends User {

    private String companyName;
    private ArrayList<Job> postedJobs;

    public Employer(String name, String email, String password, UserRole role, String companyName) {
        super(name, email, password, role);
        this.companyName = companyName;
        this.postedJobs = new ArrayList<>();
    }

    public void postJob(Job job) {
        postedJobs.add(job);
    }

    /*
    public void updateJob(int jobId, Job updatedJob) {
        for (int i = 0; i < postedJobs.size(); i++) {
            if (postedJobs.get(i).getId() == jobId) {
                postedJobs.set(i, updatedJob);
                return;
            }
        }
    }

    public void deleteJob(int jobId) {
        postedJobs.removeIf(job -> job.getId() == jobId);
    }
     */

    public ArrayList<Job> getPostedJobs() {
        return new ArrayList<>(postedJobs);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

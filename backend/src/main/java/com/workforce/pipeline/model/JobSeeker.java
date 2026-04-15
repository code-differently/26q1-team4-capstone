package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;

import java.util.ArrayList;

public class JobSeeker extends User {
    private ArrayList<Skill> listOfSkills;

    public JobSeeker(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
        this.listOfSkills = new ArrayList<>();
    }

    public void addSkill(Skill skill) {
        listOfSkills.add(skill);
    }
    public void removeSkill(Skill skill) {
        listOfSkills.remove(skill);
    }
    public ArrayList<Skill> getListOfSkills() {
        return new ArrayList<>(listOfSkills);
    }

}

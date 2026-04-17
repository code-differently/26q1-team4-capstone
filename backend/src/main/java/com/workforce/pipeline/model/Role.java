package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;     // ADD THIS
    private String region;
    private String industry;
    private double demandScore;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Job> jobs = new ArrayList<>();

    public Role() {
    }


}

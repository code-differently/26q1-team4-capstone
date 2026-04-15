# Workforce Pipeline Intelligence System — Team Work Split Guide

## 📌 Purpose

This document defines how the backend work is split between two developers so that:

* both people can work in parallel from Day 1
* there is no blocking or waiting
* ownership of files is clearly defined
* the project stays aligned with the UML + Spring Boot architecture

---

# 🧱 High-Level Architecture

```
Frontend (React)
        ↓
Controllers (API layer)
        ↓
Services (business logic)
        ↓
Repositories (database access)
        ↓
Models (data structure)
```

---

# 👥 TEAM SPLIT OVERVIEW

## 👤 Person 1 — “Jobs & Skills Owner (Supply Side)”

## 👤 Person 2 — “Users & Intelligence Owner (Demand + AI Side)”

---

# 👤 PERSON 1 — JOBS + SKILLS (CORE DATA SYSTEM)

## 🎯 Main Responsibility

Build the **foundation of the platform**: job postings and required skills.

This is the “supply side” of the workforce system.

---

## 📦 Owned Features

### 🔹 Job System

* Create jobs
* List jobs
* Define required skills for jobs

### 🔹 Skill System

* Create skills
* Normalize skill names
* Track skill relationships

### 🔹 Role System

* Define job roles
* Compute demand score

---

## 📁 Files Owned

### model/

* Job.java
* Skill.java
* Role.java

### repository/

* JobRepository.java
* SkillRepository.java
* RoleRepository.java

### service/

* JobService.java
* SkillService.java
* RoleService.java

### controller/

* JobController.java
* SkillController.java
* RoleController.java

---

## ⚙️ Key Responsibilities

* Job CRUD (POST /jobs, GET /jobs)
* Skill CRUD (POST /skills, GET /skills)
* Role creation + demandScore logic
* Define required skills per job

---

## 🧠 Notes

* This side creates the core dataset used by the entire system
* Must ensure clean and consistent Job/Skill structure
* Avoid editing User/Application/AI-related logic

---

# 👤 PERSON 2 — USERS + APPLICATIONS + INTELLIGENCE LAYER

## 🎯 Main Responsibility

Build the **user system + AI intelligence layer** that uses job and skill data.

This is the “demand + decision-making side” of the system.

---

## 📦 Owned Features

### 🔹 User System

* Job seeker accounts
* Employer accounts
* Training provider accounts
* Login/register/profile

### 🔹 Application System

* Apply to jobs
* Track application status
* View applications per user

### 🔹 Training System

* Create training programs
* Map skills taught
* Support workforce development

### 🔹 Intelligence Layer (CORE CAPSTONE VALUE)

* Recommendation engine
* Gap analysis system
* Analytics dashboard

---

## 📁 Files Owned

### model/

* User.java
* JobSeeker.java
* Employer.java
* TrainingProvider.java
* Application.java
* TrainingProgram.java
* Recommendation.java
* GapAnalysis.java

### repository/

* UserRepository.java
* ApplicationRepository.java
* TrainingRepository.java
* RecommendationRepository.java
* GapAnalysisRepository.java

### service/

* UserService.java
* ApplicationService.java
* TrainingService.java
* RecommendationService.java
* GapAnalysisService.java
* AnalyticsService.java

### controller/

* UserController.java
* ApplicationController.java
* TrainingController.java
* RecommendationController.java
* AnalyticsController.java

---

## ⚙️ Key Responsibilities

### 🔹 User System

* Register/login users
* Manage profiles
* Assign roles

### 🔹 Applications

* Submit job applications
* Track status updates

### 🔹 Training Programs

* Create programs tied to skills
* Support skill gap closure

### 🔹 AI / Intelligence

* Match users to jobs
* Recommend training programs
* Generate explainable recommendations
* Compute skill & role gap analysis
* Produce analytics reports

---

## 🧠 Notes

* This is the “AI + insights” layer of the system
* Uses Job and Skill data created by Person 1
* Must not modify Job/Skill/Role definitions

---

# 🔗 HOW BOTH SIDES CONNECT

## Data Flow

```
Person 1 builds:
Jobs + Skills + Roles
        ↓
Person 2 uses:
Applications + Recommendations + Gap Analysis
```

---

## Shared Dependency Rule

* Person 1 = defines data
* Person 2 = consumes data
* No circular dependencies

---

# 🚀 DAY 1 PARALLEL WORK PLAN

## 👤 Person 1 Starts With:

* POST /jobs
* GET /jobs
* POST /skills
* GET /skills

## 👤 Person 2 Starts With:

* POST /users
* GET /users
* POST /applications
* GET /applications
* POST /training

---

# ⚠️ IMPORTANT RULES

* ❌ No overlapping file ownership
* ❌ No modifying each other’s services
* ❌ No waiting for full completion before starting
* ✅ Use shared models (Job, Skill) as read-only for Person 2
* ✅ Keep logic inside service layer, not controllers

---

# 🎯 GOAL

This split ensures:

* both developers work immediately
* no blocking dependencies
* clean separation of concerns
* alignment with UML and Spring Boot architecture
* faster and more organized development

---

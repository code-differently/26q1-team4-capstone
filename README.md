# Workforce Pipeline Intelligence System – Backend Guide

## 📌 Overview

This document explains the purpose of each folder in our backend so everyone on the team understands:

* where code should go
* what each layer is responsible for
* how to split work efficiently

---

# 🧱 System Architecture (How Everything Connects)

```
Frontend (React)
        ↓
controller/   → handles requests
        ↓
service/      → business logic (core of app)
        ↓
repository/   → database access
        ↓
model/        → data structure (entities)
```

---

# 📁 Folder Breakdown

## 📁 model/ → “What is our data?”

### Purpose:

Defines the core objects in the system (basically your UML classes).

### Contains:

* User, JobSeeker, Employer, TrainingProvider
* Job, Skill, Role, Application
* TrainingProgram
* Recommendation
* GapAnalysis
* Analytics

### Notes:

* These map directly to database tables
* Should NOT contain heavy logic

---

## 📁 repository/ → “How do we store/retrieve data?”

### Purpose:

Handles all database interactions (CRUD operations).

### Contains:

* UserRepository
* JobRepository
* SkillRepository
* RoleRepository
* ApplicationRepository
* TrainingRepository
* RecommendationRepository
* GapAnalysisRepository

### Notes:

* Only database queries here
* No business logic

---

## 📁 service/ → “How does the system think?” ⭐ MOST IMPORTANT

### Purpose:

Contains all business logic and core functionality.

### Contains:

* UserService
* JobService
* SkillService
* RoleService
* ApplicationService
* TrainingService
* RecommendationService
* GapAnalysisService
* AnalyticsService

### Responsibilities:

* Demand score calculation
* Skill gap analysis
* Job matching
* Recommendation generation

### Notes:

* If it involves logic → it goes here
* This is the “brain” of the system

---

## 📁 controller/ → “How does the outside world interact?”

### Purpose:

Handles API endpoints (HTTP requests from frontend).

### Contains:

* JobController
* UserController
* SkillController
* ApplicationController
* TrainingController
* RecommendationController
* AnalyticsController
* RoleController

### Responsibilities:

* Receive requests (GET, POST, etc.)
* Call service methods
* Return responses

### Notes:

* NO business logic here
* Just routing

---

## 📁 dto/ → “What data do we send to frontend?”

### Purpose:

Formats data for frontend safely and cleanly.

### Contains:

* JobDTO
* UserDTO
* RecommendationDTO
* GapAnalysisDTO

### Notes:

* Prevents sending unnecessary or sensitive data (e.g., passwords)
* Acts as a “clean version” of models

---

## 📁 enums/ → “Fixed values”

### Purpose:

Stores predefined constants.

### Contains:

* UserRole (JOB_SEEKER, EMPLOYER, TRAINING_PROVIDER)

---

## 📁 util/ → “Helper tools”

### Purpose:

Reusable logic used by services.

### Contains:

* ScoreCalculator (demandScore, gapScore)
* SkillMatcher (matching skills)
* AnalyticsCalculator (aggregations)

### Notes:

* No database access here
* Pure helper logic

---

## 📁 config/ → “Application setup”

### Purpose:

Configuration for the app.

### Examples (future):

* Security config
* CORS config
* API integrations

---

# 🧠 Simple Mental Model

Think of the system like a company:

| Folder     | Role                     |
| ---------- | ------------------------ |
| model      | data (records/files)     |
| repository | storage system           |
| service    | employees doing the work |
| controller | front desk (API)         |
| dto        | what customers see       |
| util       | tools used by employees  |
| config     | company rules/setup      |

---

# 👥 Team Work Split (Recommended)

## 🔹 Person 1: Job + Skill System

* JobService
* SkillService
* JobController
* SkillController

## 🔹 Person 2: User + Applications

* UserService
* ApplicationService
* UserController
* ApplicationController

## 🔹 Person 3: Training + Role

* TrainingService
* RoleService
* TrainingController
* RoleController

## 🔹 Person 4: AI + Analytics (CORE FEATURE)

* RecommendationService
* GapAnalysisService
* AnalyticsService
* RecommendationController
* AnalyticsController

---

# 🚀 Development Order (IMPORTANT)

1. ✅ Job + Skill CRUD (start here)
2. ✅ User + Application
3. ✅ Training Programs
4. ✅ Role + demandScore
5. ✅ GapAnalysis logic
6. ✅ Recommendation system (AI requirement)

---

# ⚠️ Key Rules

* ❌ No logic in controllers
* ❌ No database calls in util
* ❌ No extra classes not in UML
* ✅ Services handle ALL logic
* ✅ Models match UML exactly

---

# 🎯 Goal

By following this structure:

* everyone knows where to code
* no overlapping work
* clean, scalable backend
* easy integration with frontend

---

If anything is unclear, ask before coding — it’s easier to fix structure early than later.

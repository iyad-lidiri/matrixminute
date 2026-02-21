# 🧠 Matrix Minute

Matrix Minute is a daily linear algebra challenge platform where users solve one matrix-based problem per day under attempt limits.

This project is built as a full-stack web application with deterministic daily problem generation and persistent user tracking.

---

## 🚀 Features

- 📅 Deterministic daily problems (same problem for all users each day)
- 🧮 Multiple problem types:
  - 2×2 determinant (area scale)
  - Solve-for-x determinant
  - 3×3 determinant
- 🔒 Attempt limiting + locking system
- 💾 Persistent user tracking using PostgreSQL
- ♻ Restart-safe backend (database-backed, not in-memory)
- 🔐 Secure API (answers are never sent to the frontend)

---

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL

### Frontend (In Progress)
- HTML
- CSS
- JavaScript

---

## 🧱 Architecture Overview

### API Endpoints

- `GET /api/daily`
  - Returns the daily problem (without the answer)

- `POST /api/guess`
  - Processes user guesses
  - Updates attempt count
  - Locks user after max attempts
  - Persists state in database

---

### Data Model

**AttemptEntity**
- `userId`
- `date`
- `attemptsUsed`
- `solved`

- Unique constraint on `(userId, date)`
- Ensures one attempt record per user per day

---

### Daily Problem Generation

- Deterministic random generator seeded by date
- Ensures identical problem globally per day
- Prevents answer exposure
- Designed for scalability and future expansion

---

## 🎯 Why I Built This

I wanted to build a production-style backend system that:

- Persists user state in a real database
- Enforces daily limits and locking logic
- Uses deterministic problem generation
- Separates frontend and backend via REST APIs

Matrix Minute is being developed as a long-term passion project focused on scalable backend design and mathematical problem generation.

---

## 🔜 Planned Features

- 📊 Leaderboard system
- 🔥 Daily streak tracking
- ⏱ Solve-time tracking
- 🌐 Cloud deployment
- 🔑 Authentication system

---

## 📦 Running Locally

```bash
git clone <your-repo-url>
cd matrixminute
mvn spring-boot:run

# Ngoma - Authentication App (Spring Boot)

* Note that this repository is public and 'ngoma.db' (the SQLite database file) is committed and visible to anyone. Do not sign up with a real email or password — use throwaway test credentials only.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Cloning the Project](#cloning-the-project)
- [Install Requirements](#install-requirements)
- [Set Environment Variables](#set-environment-variables)
- [Run the Project](#run-the-project)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Tech Stack](#tech-stack)

## Prerequisites
- [Java 21+ (JDK)](https://adoptium.net/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- No separate database install needed — the project uses SQLite, which runs embedded (no server process)

## Getting Started

These are the steps to get your project up and running.

### Cloning the Project

To clone this project to local your local machine, use the following command:

*linux, macOS, windows*

git clone http://github.com/oyisam40/Ngoma.git

### Install Requirements

Navigate to your project directory. All dependencies are managed by Maven and declare in `pom.xml` - no manual installation step needed.The project includes the Maven Wrapper, so a separate Maven installation is not required either.
./mvnw clean install  

*windows*

.\mvnw.cmd clean install

### Set Environment Variables

No environment variables or secrets are required to run this project locally. Database connection settings are already configured in:

### Run the project 

Run the application using the wrapper:

*linux, macOS*
./mvnw spring-boot:run

*windows*
.\mvnw.cmd spring-boot:run

Or run `NgomaApplication.java` directly from IntelliJ

Once running, open your browser to:
https//localhost:8081

You'll land on the login page. From there you can sign up, log in, and view the dashboard.

**Remember: this repo is public and `ngoma.db` is currently tracked in it — use a test/throwaway email and password, never a real one.**

## Project Structure

```
src/main/java/com/example/ngoma/
├── NgomaApplication.java        # Application entry point
├── User.java                    # JPA entity — maps to the `users` table
├── UserRepository.java          # Spring Data JPA repository
├── SignupRequest.java           # DTO for signup requests, with validation
├── AuthController.java          # REST endpoint: POST /api/signup
├── CustomerUserDetailsService.java  # Loads user details for Spring Security
├── SecurityConfig.java          # Security rules, password hashing, login/logout config
└── PageController.java          # Serves static HTML pages

src/main/resources/
├── static/
│   ├── index.html               # Login page
│   ├── signup.html              # Signup page
│   ├── dashboard.html           # Dashboard (post-login)
│   └── bg.jpg                   # Background image (login/signup)
└── application.properties       # App + database configuration
```

## API Endpoints

| Method | Endpoint       | Description                          | Auth required |
|--------|----------------|---------------------------------------|----------------|
| POST   | `/api/signup`  | Create a new account (email + password) | No |
| POST   | `/api/login`   | Log in, starts a session               | No |
| POST   | `/api/logout`  | Ends the current session               | Yes |
| GET    | `/dashboard.html` | Protected dashboard page            | Yes |

## Tech Stack

- **Spring Boot** - application framework
- **Spring Security** — authentication, session management, password hashing (BCrypt)
- **Spring Data JPA + Hibernate** — persistence layer
- **SQLite** — embedded database (no separate server process)
- **Lombok** — reduces boilerplate on the `User` entity
- **HTML / CSS / vanilla JavaScript** — frontend, using the native `fetch()` API
- **Chart.js** — dashboard charts
- **Maven** — build tool

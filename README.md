
# CoolApp 📚

CoolApp is a comprehensive platform designed to manage users, organizations, schools, and applications with hierarchical precedence rules. The application allows dynamic assignment and modification of applications across different levels (`ROOT`, `ORGANIZATION`, `SCHOOL`), ensuring tailored experiences for end-users.

---

## Table of Contents ✏️
1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Setup Instructions](#setup-instructions)
4. [Docker Setup](#docker-setup)
5. [Endpoints](#endpoints)
6. [Testing](#testing)
7. [Usage Examples](#usage-examples)
8. [Development Notes](#development-notes)

---

## Features ⚙️

- **Dynamic Application Management**: Applications can be defined at `ROOT`, `ORGANIZATION`, and `SCHOOL` levels with precedence rules.
- **User-Specific Views**: Users see applications tailored to their school or organization hierarchy.
- **CRUD Operations**: Manage users, schools, organizations, and applications seamlessly.
- **Hierarchical Overriding**: Applications defined at a lower level (e.g., school) override those at higher levels (e.g., root or organization).
---

## Tech Stack 🛠️

- **Java**: Main programming language.
- **Spring Boot**: Framework for building the backend services.
  - Spring Data JPA for persistence.
  - Spring Web for REST API development.
- **MySQL**: Relational database for persistence.
- **Hibernate**: ORM framework for database operations.
- **Lombok**: Boilerplate code reduction for Java classes.
- **JUnit 5**: Unit testing framework.
- **Mockito + PowerMock**: Mocking frameworks for testing.
- **Docker Compose**: Containerized deployment.

---

## Setup Instructions 🔧

### Prerequisites
1. **Java 17 or later**: Ensure you have Java installed. [Download Java](https://adoptopenjdk.net/).
2. **Maven**: Required for building and running the application. [Install Maven](https://maven.apache.org/).
3. **Docker & Docker Compose**: For containerized deployment. [Install Docker](https://www.docker.com/).

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo-url/coolapp.git
   cd coolapp
   ```

2. **Configure the database**:
   - Create a database named `coolapp`.
   - You can leave the `application.properties` and `docker-compose.yml` the way it is:

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

---

## Docker Setup 🐳

### Steps to Run with Docker Compose
1. Ensure Docker and Docker Compose are installed.
2. Run the following command:
   ```bash
   docker-compose up --build
   ```

## Endpoints ⛳️

### Applications
| Method | Endpoint              | Description                              |
|--------|-----------------------|------------------------------------------|
| POST   | `/applications`       | Create a new application.                |
| GET    | `/applications`       | List all applications.                   |
| GET    | `/applications/filter`| Filter applications for a specific user. |

### Organizations
| Method | Endpoint              | Description                              |
|--------|-----------------------|------------------------------------------|
| POST   | `/organizations`      | Create a new organization.               |
| GET    | `/organizations`      | List all organizations.                  |

### Schools
| Method | Endpoint              | Description                              |
|--------|-----------------------|------------------------------------------|
| POST   | `/schools`            | Create a new school.                     |
| GET    | `/schools`            | List all schools.                        |

### Users
| Method | Endpoint              | Description                              |
|--------|-----------------------|------------------------------------------|
| POST   | `/users`              | Create a new user.                       |
| GET    | `/users`              | List all users.                          |
| GET    | `/users/{id}`         | Fetch user details by ID.                |

---

## Testing 🪂

### Unit Tests
The project includes extensive test cases covering:
1. **Application Precedence Rules**:
   - Applications defined at `SCHOOL` level override `ORGANIZATION` and `ROOT`.
   - Applications at `ROOT` level apply universally unless overridden.
2. **CRUD Operations**:
   - Validations for create, update, and fetch operations.
3. **Edge Cases**:
   - Users with no school ID.
   - Applications with duplicate IDs across levels.


---

## Usage Examples 🧮

### Create an Application
```bash
curl -X POST http://localhost:8080/applications -H "Content-Type: application/json" -d '{
  "id": "app1",
  "name": "Gmail",
  "url": "http://www.gmail.com",
  "level": "ROOT"
}'
```

### Update an Application
```bash
curl -X POST http://localhost:8080/applications -H "Content-Type: application/json" -d '{
  "id": "app1",
  "name": "Gmail Updated",
  "url": "http://school.gmail.com",
  "level": "SCHOOL"
}'
```

### Get Applications for a User
```bash
curl -X GET "http://localhost:8080/applications/filter?userId=us1"
```

---

## Development Notes ✍🏻

### Precedence Rules
- **SCHOOL > ORGANIZATION > ROOT**:
  - Applications defined at the school level take precedence.

### Coding Conventions
- **Lombok Annotations**: Use `@Getter`, `@Setter`, `@Builder`, etc., to reduce boilerplate.
- **Exception Handling**: Centralized exception handling using `@ControllerAdvice`.

---

### Enjoy! 🚀

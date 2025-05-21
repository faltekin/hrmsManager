
# HRMS Manager

HRMS Manager is a human resources management system built with Java and Spring Boot. The project was designed with a multi-layered architecture and incorporates core enterprise-level backend patterns such as role-based access control, entity relationship mapping, logging, and DTO validation.

## 🌟 Features

- JWT-based authentication and refresh tokens
- Role-based authorization (ADMIN / USER)
- Password masking and login attempt limitation
- CRUD operations for users, salaries, and leave requests
- Logging via EventLog entity
- Centralized logging using Log4j2 (console and rolling file configuration)
- Pageable REST endpoints
- Database persistence (MySQL or H2 supported)
- Fully testable with Postman collection

## 🛠 Technologies Used

- Java 17
- Spring Boot
- Spring Security
- Hibernate / JPA
- Lombok
- JWT
- Maven
- Postman (for API testing)
- ModelMapper
- Swagger (optional)

## 🔐 Roles & Access

- ADMIN: Full control over all endpoints (create/update/delete users, view all salaries/leaves)
- USER: Limited access, can login and view own data

Account blocking is enforced after 3 failed login attempts.


## 📬 Postman Collection

You can download and test all endpoints using the Postman collection:

[Download hrmsManager.postman_collection.json](./postman/hrmsManager.postman_collection.json)

## 📬 API Endpoints

You can test the system using these documented REST endpoints:

- `POST http://localhost:8080/v1/auth/register`
- `POST http://localhost:8080/v1/auth/authenticate`
- `POST http://localhost:8080/v1/auth/refreshToken`
- `GET http://localhost:8080/v1/users/getAll`
- `DELETE http://localhost:8080/v1/users/19`
- `GET http://localhost:8080/v1/users/18`
- `PUT http://localhost:8080/v1/users/update/42`
- `GET http://localhost:8080/v1/users/list/pageable?pageNumber=1&pageSize=1&columnName=id&asc=true
  `
- `POST http://localhost:8080/v1/salaries/save`
- `GET http://localhost:8080/v1/salaries/getAll`
- `GET http://localhost:8080/v1/salaries/4`
- `GET http://localhost:8080/v1/salaries/user/180`
- `GET http://localhost:8080/v1/salaries/list/pageable?pageNumber=0&pageSize=10&columnName=id&asc=true
  `
- `GET http://localhost:8080/v1/leaves/2`
- `POST http://localhost:8080/v1/leaves/save`
- `GET http://localhost:8080/v1/leaves/getAll`
- `GET http://localhost:8080/v1/leaves/user/18`
- `GET http://localhost:8080/v1/leaves/list/pageable?pageNumber=0&pageSize=10&columnName=id&asc=true
  `
- `POST http://localhost:8080/v1/eventLogs/save`
- `GET http://localhost:8080/v1/eventLogs/getAll`
- `GET http://localhost:8080/v1/eventLogs/1`
- `GET http://localhost:8080/v1/eventLogs/user/22`
- `GET http://localhost:8080/v1/eventLogs/list/pageable?pageNumber=0&pageSize=10&columnName=id&asc=true
  `

## 🧠 Developer Notes

This project was developed as part of a backend bootcamp curriculum with a focus on:

- Clean Code & SOLID principles
- OOP best practices (interfaces, inheritance, enums)
- Custom exception handling
- Layered architecture and separation of concerns

Additional technical highlights:
- Log4j2 XML configuration with RollingFileAppender and PatternLayout
- JWT token-based stateless authentication system
- DTO mapping using ModelMapper
- JPA entity relationships: OneToMany, ManyToOne
- Layered architecture: Controller → Service → Repository → Entity → DTO

## 👤 Author

Fuat Altekin  
[LinkedIn](https://www.linkedin.com/in/fuataltekin) · [GitHub](https://github.com/faltekin)

---

This project is complete and currently maintained. You are welcome to review, fork, or contact for feedback.

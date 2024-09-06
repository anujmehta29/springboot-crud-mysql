# CRUD Example

A simple CRUD application using Spring Boot and MySQL.

## Setup

1. Update `src/main/resources/application.properties` with your database credentials.
2. Run the application using `mvn spring-boot:run`.

## Endpoints

- `GET /users` - Retrieve all users
- `GET /users/{id}` - Retrieve a user by ID
- `POST /users` - Create a new user
- `PUT /users/{id}` - Update a user
- `DELETE /users/{id}` - Delete a user

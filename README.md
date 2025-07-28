
# Movie Metadata CRUD API

This project is a Spring Boot microservice that provides CRUD (Create, Read, Update, Delete) operations for managing movie metadata. It is designed to power a movie website similar to IMDb.com.

## Features

- Create a new movie
- Read/Get movies by title and/or release year (search supported using either or both)
- Update an existing movie by ID
- Delete a movie by ID

## Movie Data Model

Each movie contains the following fields:
- **ID** (UUID)
- **Title** (String)
- **Release Year** (String)

## Technology Stack

- Java 17+
- Spring Boot (Web, Data JPA)
- H2 In-Memory Database
- Maven
- SLF4J for logging

## API Endpoints

| Method | Endpoint     | Description                                  |
|--------|--------------|----------------------------------------------|
| POST   | `/`          | Create a new movie                           |
| GET    | `/`          | Search for movies by title and/or year       |
| PUT    | `/{id}`      | Update movie metadata by ID                  |
| DELETE | `/{id}`      | Delete a movie by ID                         |

## Request/Response Examples

### 1. Create Movie

**POST** `/`  
Request:
```json
{
  "title": "Inception",
  "releaseYear": "2010"
}
```

### 2. Search Movies

**GET** `/`  
Query Parameters:
- `title` (optional)
- `releaseYear` (optional)

Examples:
- `/api?title=Inception`
- `/api?releaseYear=2010`
- `/api?title=Inception&releaseYear=2010`

Response:
```json
{
  "id": "uuid-value",
  "title": "Inception",
  "releaseYear": "2010"
}
```

### 3. Update Movie

**PUT** `/123e4567-e89b-12d3-a456-426614174000`  
Request:
```json
{
  "title": "Interstellar",
  "releaseYear": "2014"
}
```

### 4. Delete Movie

**DELETE** `/123e4567-e89b-12d3-a456-426614174000`

## How to Run the Application

### Prerequisites

- Java 17+
- Maven

### Steps

1. Clone the repository:
```bash
git clone https://github.com/rtata28/IMDBWebservices.git
cd IMDBWebservices
```

2. Run the application:
```bash
./mvnw spring-boot:run
```

The application will start at:
```
http://localhost:8080/
```

## Database

This app uses the H2 in-memory database. You can access the H2 console at:
```
http://localhost:8080/h2-console
```
Use the default JDBC URL: `jdbc:h2:mem:testdb`

## Error Handling

- If no query parameters are provided on a GET request, a `400 Bad Request` is returned.
- Invalid input or missing required fields also return `400 Bad Request`.
- Proper logging is implemented using SLF4J.

## Notes

- Data will be reset every time the application restarts since H2 is in-memory.
- The project includes basic validation and exception handling.
- Designed for quick development/testing purposes.

## Author

This project was created as part of a timed technical interview exercise. For any questions or follow-ups, feel free to reach out.

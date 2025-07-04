# 📘 Quill API

### 🖋️ Gestion d’un Éditeur — REST API with Spring Boot

Quill is a simple editorial management system for handling **publications** (books & magazines) and their **authors**.  
Built using Spring Boot, PostgreSQL, and Java 21.

---

### 🛠️ Technologies Used

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA**
- **PostgreSQL**
- **Hibernate (JPA Inheritance)**
- **Lombok**
- **Maven/Gradle**
- **JUnit 5 + Mockito**
- **DTO Validation**
- **Swagger/OpenAPI**
- **Postman (Collection for testing)**

---

### 📦 Features

- 📚 Add and retrieve **Books** and **Magazines**
- ✍️ Manage **Authors**
- 🔍 Search publications by title or ISBN
- ➕ Bulk insert for Books, Magazines, Authors
- 🧭 Pagination for publication listings
- ✅ Input validation via DTOs
- 🧪 Unit tests for all service layers

---

### 🧱 Data Model

- **Publication** (abstract)
  - `id`, `title`, `publicationDate`
- **Book** *(extends Publication)*
  - `isbn`, `author` (N:1)
- **Magazine** *(extends Publication)*
  - `issueNumber`, `authors` (N:N)
- **Author**
  - `id`, `name`, `birthDate`, `nationality`

---

### 🧪 API Endpoints (Sample)

| Method | Endpoint                     | Description                    |
|--------|------------------------------|--------------------------------|
| GET    | `/authors`                   | List all authors               |
| POST   | `/authors`                   | Add one or many authors        |
| GET    | `/books/isbn/{isbn}`         | Search book by ISBN            |
| POST   | `/books`                     | Add one or many books          |
| POST   | `/magazines`                 | Add one or many magazines      |
| GET    | `/publications`              | Paginated list of publications |
| GET    | `/publications?title=x`      | Search publications by title   |

✅ All endpoints accept and return **DTOs**  
✅ Request validation is enforced with `@Valid`

---

### 🚀 Getting Started

#### 1. Clone the repository
```bash
git clone https://github.com/yourusername/quill-api.git
cd quill-api
```

#### 2. Setup PostgreSQL
- Create a PostgreSQL user and password (default: `postgres` / `postgres`)
- Spring Boot will auto-create the database on first run

#### 3. Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quill_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 4. Run the application
```bash
./gradlew bootRun
```

---

### 🧪 Testing

Run unit tests using:

```bash
./gradlew test
```

---

### 📬 API Documentation

Once the app is running, open:

```
http://localhost:8080/swagger-ui/index.html
```

> Swagger UI is included via `springdoc-openapi` (if enabled).

---

### 📫 Postman Collection

A complete [Postman collection](./postman/Quill_API_Collection.json) is included with:

- Successful and failed test cases
- Bulk insert samples
- Validation scenarios

---

### 🙌 Author

**Saber Oueslati**  
Software Engineer — Mobile, Backend & Testing enthusiast  
📍 Tunisia

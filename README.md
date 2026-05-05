# 📚 Library Management System

A full-featured **Library Management System** REST API built with **Spring Boot**, featuring JWT-based authentication, role-based access control, and comprehensive book and genre management.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT |
| Database | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| Utilities | Lombok |

---

## 📁 Project Structure

```
src/main/java/com/vijay/
├── configuration/
│   ├── JwtConstant.java
│   ├── JwtValidator.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── BookController.java
│   └── GenreController.java
├── domain/
│   ├── AuthProvider.java
│   └── UserRole.java
├── exception/
│   ├── BookException.java
│   ├── GenreException.java
│   ├── GlobalException.java
│   └── UserException.java
├── mapper/
│   ├── BookMapper.java
│   └── GenreMapper.java
├── modal/
│   ├── Book.java
│   ├── Genre.java
│   └── User.java
├── payload/
│   ├── dto/
│   │   ├── BookDTO.java
│   │   ├── GenreDTO.java
│   │   └── UserDto.java
│   ├── request/
│   │   └── BookSearchRequest.java
│   └── response/
│       ├── ApiResponse.java
│       ├── AuthResponse.java
│       ├── BookStatsResponse.java
│       └── PageResponse.java
├── repository/
│   ├── BookRepository.java
│   ├── GenreRepository.java
│   └── UserRepository.java
└── service/
    ├── AuthService.java
    ├── BookService.java
    ├── GenreService.java
    └── impl/
        ├── AuthServiceImpl.java
        ├── BookServiceImpl.java
        └── GenreServiceImpl.java
```

---

## ✨ Features

### 🔐 Authentication & Security
- JWT based stateless authentication
- Role based access control (ADMIN, USER)
- Password encoding with BCrypt
- Google OAuth2 support (coming soon)
- Password reset via email token (coming soon)
- CORS configuration for frontend integration

### 📖 Genre Management
- Hierarchical genre structure (parent → child → grandchild)
- Self-referencing parent-child relationship
- Soft delete and hard delete support
- Active/inactive genre management
- Top-level genre listing
- Sub-genre support with recursive mapping

### 📚 Book Management
- Full CRUD operations
- Bulk book creation
- ISBN uniqueness validation
- Available copies vs total copies validation
- Soft delete and hard delete support
- Advanced search with filters (title, author, ISBN)
- Filter by genre, availability, active status
- Pagination and sorting support
- Book statistics (total active, total available)

### 👤 User Management
- User registration with email validation
- Duplicate email prevention
- Role assignment (ADMIN / USER)
- Auth provider tracking (LOCAL / GOOGLE)
- Profile image support

---

## 🔧 Setup & Installation

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

### 2. Configure database
Create a MySQL database:
```sql
CREATE DATABASE library_management;
```

### 3. Update `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_secret_key_here
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The server will start at `http://localhost:8080`

---

## 📡 API Endpoints

### 🔐 Auth
| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/signup` | Register new user | Public |
| POST | `/api/auth/login` | Login and get JWT | Public |

### 🎭 Genre
| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/genres/create` | Create a genre | Authenticated |
| GET | `/api/genres` | Get all genres | Authenticated |
| GET | `/api/genres/{id}` | Get genre by ID | Authenticated |
| PUT | `/api/genres/{id}` | Update genre | Authenticated |
| DELETE | `/api/genres/{id}` | Soft delete genre | Authenticated |
| DELETE | `/api/genres/{id}/hard-delete` | Hard delete genre | Admin |
| GET | `/api/genres/top-level` | Get top level genres | Authenticated |
| GET | `/api/genres/active` | Get all active genres with sub-genres | Authenticated |
| GET | `/api/genres/count` | Get total active genre count | Authenticated |

### 📚 Book
| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/books` | Create a book | Authenticated |
| POST | `/api/books/bulk` | Create books in bulk | Authenticated |
| GET | `/api/books` | Search books with filters | Authenticated |
| GET | `/api/books/{id}` | Get book by ID | Authenticated |
| GET | `/api/books/isbn/{isbn}` | Get book by ISBN | Authenticated |
| PUT | `/api/books/{id}` | Update book | Authenticated |
| DELETE | `/api/books/{id}` | Soft delete book | Authenticated |
| DELETE | `/api/books/{id}/hard-delete` | Hard delete book | Admin |
| POST | `/api/books/search` | Advanced search with pagination | Authenticated |
| GET | `/api/books/stats` | Get book statistics | Authenticated |

---

## 🔑 Authentication

All protected endpoints require a JWT token in the request header:

```
Authorization: Bearer <your_jwt_token>
```

---

## 📦 Sample Requests

### Register
```json
POST /api/auth/signup
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phone": "9999999999"
}
```

### Create Genre
```json
POST /api/genres/create
{
  "code": "FICTION",
  "name": "Fiction",
  "description": "Imaginative and narrative-based literary works",
  "displayOrder": 1,
  "active": true,
  "parentGenreId": null
}
```

### Create Book
```json
POST /api/books
{
  "isbn": "978-0-13-468599-1",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genreId": 1,
  "publisher": "Prentice Hall",
  "publicationDate": "2008-08-01",
  "language": "English",
  "pages": 431,
  "description": "A handbook of agile software craftsmanship",
  "totalCopies": 10,
  "availableCopies": 10,
  "price": 499.99,
  "active": true
}
```

### Search Books
```json
POST /api/books/search
{
  "searchTerm": "Clean",
  "genreId": null,
  "availableOnly": false,
  "page": 0,
  "size": 10,
  "sortBy": "createdAt",
  "sortDirection": "DESC"
}
```

---

## 🗺️ Roadmap

- [x] Genre Module
- [x] Book Module
- [x] JWT Authentication
- [x] Spring Security & RBAC
- [ ] User Module (in progress)
- [ ] Loan Module
- [ ] Reservation Module
- [ ] Password Reset via Email
- [ ] Google OAuth2 Integration
- [ ] Book Count by Genre (after Loan module)
- [ ] Notifications

---

## 👨‍💻 Author

**Vijay Kumar**
- GitHub: [@dvijaykumarr](https://github.com/dvijaykumarr)

---

## 📄 License

This project is licensed under the MIT License.

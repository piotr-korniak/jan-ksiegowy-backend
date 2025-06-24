📚 [English](README.md) | 🇵🇱 [Polski](README.pl.md)
# Jan Księgowy - Backend

> Accounting software for small businesses and freelancers in Poland

## 📋 Project Description

Jan Księgowy is modern accounting software dedicated to Polish small businesses and freelancers. The project is built using Domain-Driven Design (DDD) principles and Clean Architecture, ensuring high code quality, maintainability, and scalability.

## 🏗️ Architecture

The project implements:
- **Domain-Driven Design (DDD)** - modeling aligned with business domain
- **Clean Architecture** - layer separation and independence from external frameworks
- **CQRS** - separation of commands from queries
- **Event Sourcing** - optional support for change tracking

## 🛠️ Technologies

- **Java 17+** - main programming language
- **Spring Boot 3.x** - application framework
- **Spring Data JPA** - data access layer
- **Hibernate** - ORM
- **Gradle** - dependency management and build tool
- **JUnit 5** - unit testing
- **Mockito** - mocking in tests
- **PostgreSQL** - database
- **Docker** - application containerization
- **GitHub Actions** - CI/CD pipeline
- **AWS** - hosting and cloud infrastructure

## 📦 **Deployment**
The project uses GitHub Actions for automatic deployment to AWS:
* CI/CD pipeline automatically builds and tests code on every push
* Automatic deployment to production environment after merge to `main` branch
* AWS configuration is located in `.github/workflows/`

## 🚀 Features

### Accounting Module
- Complete accounting compliant with Polish accounting law
- Bookkeeping management
- Chart of accounts compliant with Polish standards
- Automatic posting of business transactions
- Fixed assets register and depreciation
- Accruals and deferrals
- Year-end closing

### Balance Sheet Module
- Generation of balance sheet and profit & loss statement
- Statement of changes in equity
- Cash flow statement
- Additional information to financial statements

### VAT Module
- VAT sales and purchase register
- VAT declaration generation (VAT-7, JPK_VAT)
- Support for different VAT rates and special procedures
- VAT corrections and reversals

### Contractors Module
- Customer and supplier database management
- Integration with REGON/CEIDG database
- NIP/REGON data validation

### Reports Module
- Balance sheet and profit & loss statement
- Trial balance
- General ledger
- VAT register
- Export to PDF/Excel formats
- Financial statements according to Polish standards

## 📦 Installation

### System Requirements
- Java 17 or newer
- Gradle 7.0+
- PostgreSQL 13+

### Local Setup

1. **Clone repository**
```bash
git clone https://github.com/piotr-korniak/jan-ksiegowy-backend.git
cd jan-ksiegowy-backend
```

2. **Build**
```bash
./gradlew build
```

3. **Run tests**
```bash
./gradlew test
```

4. **Start application**
```bash
./gradlew bootRun
```

Application will be available at: `http://localhost:8080`

### Database Configuration

#### PostgreSQL (production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jan_ksiegowy
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
```

## 🧪 Testing

The project contains comprehensive test suite:

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew integrationTest

# All tests with coverage report
./gradlew test jacocoTestReport
```

### Test Structure
- **Unit Tests** - business logic testing
- **Integration Tests** - application layer testing
- **Repository Tests** - data access layer testing
- **API Tests** - REST endpoint testing

## 📊 API Documentation

After starting the application, API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

### Main Endpoints

```http
# Accounting documents
GET    /api/v1/documents
POST   /api/v1/documents
PUT    /api/v1/documents/{id}
DELETE /api/v1/documents/{id}

# Journal entries
GET    /api/v1/entries
POST   /api/v1/entries
GET    /api/v1/entries/journal

# Chart of accounts
GET    /api/v1/accounts
POST   /api/v1/accounts
PUT    /api/v1/accounts/{id}

# Contractors
GET    /api/v1/contractors
POST   /api/v1/contractors
PUT    /api/v1/contractors/{id}

# Accounting reports
GET    /api/v1/reports/balance-sheet
GET    /api/v1/reports/profit-loss
GET    /api/v1/reports/trial-balance
GET    /api/v1/reports/vat-register
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/janksirgowy/
│   │       ├── application/        # Application layer
│   │       ├── domain/             # Business logic
│   │       ├── infrastructure/     # Technical implementations
│   │       └── presentation/       # REST controllers
│   └── resources/
│       ├── application.yml
│       └── db/changelog/          # Liquibase scripts
└── test/
    ├── java/
    │   └── com/janksirgowy/
    │       ├── unit/              # Unit tests
    │       ├── integration/       # Integration tests
    │       └── architecture/      # Architecture tests
    └── resources/
```

## 🔧 Configuration

### Application Profiles

- **dev** - development environment (local PostgreSQL, debug logging)
- **test** - testing environment (test PostgreSQL)
- **prod** - production environment (production PostgreSQL)

```bash
# Run with profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Environment Variables

```bash
# Database
export DB_URL=jdbc:postgresql://localhost:5432/jan_ksiegowy
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# Application
export SERVER_PORT=8080
export LOG_LEVEL=INFO
```

## 📈 Monitoring and Metrics

The application includes built-in monitoring tools:
- **Spring Boot Actuator** - application metrics
- **Micrometer** - metrics export
- **Health Checks** - application health monitoring

Monitoring endpoints:
- `/actuator/health` - application status
- `/actuator/metrics` - metrics
- `/actuator/info` - application information

## 🚀 Deployment

### Docker

```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/jan-ksiegowy-backend-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# Build image
docker build -t jan-ksiegowy-backend .

# Run container
docker run -p 8080:8080 jan-ksiegowy-backend
```

### Docker Compose

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      - SPRING_PROFILES_ACTIVE=prod
  
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: jan_ksiegowy
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
```
## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Piotr Korniak** - *Author and main maintainer* - [@piotr-korniak](https://github.com/piotr-korniak)

## 📞 Contact

- 📧 Email: [contact via GitHub profile]
- 🐛 Issues: [GitHub Issues](https://github.com/piotr-korniak/jan-ksiegowy-backend/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/piotr-korniak/jan-ksiegowy-backend/discussions)

## 🙏 Acknowledgments

- Spring Boot community
- Polish accountants for business feedback
- All contributors

---

**Jan Księgowy** - Modern accounting for modern businesses 🇬🇧



📚 [English](README.md) | 🇵🇱 [Polski](README.pl.md)
# Jan Księgowy - Backend

> Oprogramowanie księgowe dla małych firm i freelancerów w Polsce

## 📋 Opis projektu

Jan Księgowy to nowoczesne oprogramowanie księgowe dedykowane polskim małym przedsiębiorstwom i freelancerom. Projekt został zbudowany z wykorzystaniem zasad Domain-Driven Design (DDD) i Clean Architecture, zapewniając wysoką jakość kodu, łatwość utrzymania i skalowalność.

## 🏗️ Architektura

Projekt implementuje:
- **Domain-Driven Design (DDD)** - modelowanie zgodne z domeną biznesową
- **Clean Architecture** - separacja warstw i niezależność od zewnętrznych frameworków
- **CQRS** - rozdzielenie komend od zapytań
- **Event Sourcing** - opcjonalne wsparcie dla śledzenia zmian

## 🛠️ Technologie

- **Java 17+** - główny język programowania
- **Spring Boot 3.x** - framework aplikacyjny
- **Spring Data JPA** - warstwa dostępu do danych
- **Hibernate** - ORM
- **Gradle** - zarządzanie zależnościami i build tool
- **JUnit 5** - testy jednostkowe
- **Mockito** - mockowanie w testach
- **PostgreSQL** - baza danych*
- **Docker** - konteneryzacja aplikacji
- **GitHub Actions** - CI/CD pipeline
- **AWS** - hosting i infrastruktura chmurowa

## 📦 **Wdrażanie**
Projekt wykorzystuje GitHub Actions do automatycznego wdrażania na AWS:
* Pipeline CI/CD automatycznie buduje i testuje kod przy każdym push
* Automatyczne wdrażanie na środowisko produkcyjne po merge do branch `main`
* Konfiguracja AWS znajduje się w `.github/workflows/`

## 🚀 Funkcjonalności

### Moduł Księgowy
- Pełna księgowość zgodna z polskim prawem bilansowym
- Prowadzenie ksiąg rachunkowych
- Plan kont zgodny z polskim standardem
- Automatyczne księgowania operacji gospodarczych
- Ewidencja środków trwałych i amortyzacja
- Rozliczenia międzyokresowe
- Zamknięcie roku księgowego

### Moduł Bilansowy
- Generowanie bilansu i rachunku zysków i strat
- Zestawienie zmian w kapitale własnym
- Rachunek przepływów pieniężnych
- Informacja dodatkowa do sprawozdania finansowego

### Moduł VAT
- Ewidencja VAT sprzedaży i zakupu
- Generowanie deklaracji VAT (VAT-7, JPK_VAT)
- Obsługa różnych stawek VAT i procedur szczególnych
- Korekty VAT i storno

### Moduł Kontrahentów
- Zarządzanie bazą klientów i dostawców
- Integracja z bazą REGON/CEIDG
- Walidacja danych NIP/REGON

### Moduł Raportów
- Bilans oraz rachunek zysków i strat
- Zestawienie obrotów i sald kont
- Dziennik księgowań
- Rejestr VAT
- Eksport do formatów PDF/Excel
- Sprawozdania finansowe według polskich standardów

## 📦 Instalacja

### Wymagania systemowe
- Java 17 lub nowszy
- Gradle 7.0+
- PostgreSQL 13+

### Uruchomienie lokalnie

1. **Klonowanie repozytorium**
```bash
git clone https://github.com/piotr-korniak/jan-ksiegowy-backend.git
cd jan-ksiegowy-backend
```

2. **Kompilacja**
```bash
./gradlew build
```

3. **Uruchomienie testów**
```bash
./gradlew test
```

4. **Uruchomienie aplikacji**
```bash
./gradlew bootRun
```

Aplikacja będzie dostępna pod adresem: `http://localhost:8080`

### Konfiguracja bazy danych

#### PostgreSQL (production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jan_ksiegowy
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
```

## 🧪 Testowanie

Projekt zawiera kompleksowy zestaw testów:

```bash
# Testy jednostkowe
./gradlew test

# Testy integracyjne
./gradlew integrationTest

# Wszystkie testy z raportem pokrycia
./gradlew test jacocoTestReport
```

### Struktura testów
- **Unit Tests** - testy logiki biznesowej
- **Integration Tests** - testy warstwy aplikacyjnej
- **Repository Tests** - testy warstwy dostępu do danych
- **API Tests** - testy endpointów REST

## 📊 API Documentation

Po uruchomieniu aplikacji dokumentacja API jest dostępna pod adresem:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

### Główne endpointy

```http
# Dokumenty księgowe
GET    /api/v1/documents
POST   /api/v1/documents
PUT    /api/v1/documents/{id}
DELETE /api/v1/documents/{id}

# Księgowania
GET    /api/v1/entries
POST   /api/v1/entries
GET    /api/v1/entries/journal

# Plan kont
GET    /api/v1/accounts
POST   /api/v1/accounts
PUT    /api/v1/accounts/{id}

# Kontrahenci
GET    /api/v1/contractors
POST   /api/v1/contractors
PUT    /api/v1/contractors/{id}

# Raporty księgowe
GET    /api/v1/reports/balance-sheet
GET    /api/v1/reports/profit-loss
GET    /api/v1/reports/trial-balance
GET    /api/v1/reports/vat-register
```

## 🏗️ Struktura projektu

```
src/
├── main/
│   ├── java/
│   │   └── com/janksirgowy/
│   │       ├── application/        # Warstwa aplikacyjna
│   │       ├── domain/             # Logika biznesowa
│   │       ├── infrastructure/     # Implementacje techniczne
│   │       └── presentation/       # Kontrolery REST
│   └── resources/
│       ├── application.yml
│       └── db/changelog/          # Skrypty Liquibase
└── test/
    ├── java/
    │   └── com/janksirgowy/
    │       ├── unit/              # Testy jednostkowe
    │       ├── integration/       # Testy integracyjne
    │       └── architecture/      # Testy architektury
    └── resources/
```

## 🔧 Konfiguracja

### Profile aplikacji

- **dev** - środowisko deweloperskie (PostgreSQL lokalne, debug logging)
- **test** - środowisko testowe (PostgreSQL testowe)
- **prod** - środowisko produkcyjne (PostgreSQL produkcyjne)

```bash
# Uruchomienie z profilem
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Zmienne środowiskowe

```bash
# Baza danych
export DB_URL=jdbc:postgresql://localhost:5432/jan_ksiegowy
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# Aplikacja
export SERVER_PORT=8080
export LOG_LEVEL=INFO
```

## 📈 Monitoring i Metryki

Aplikacja zawiera wbudowane narzędzia monitoringu:
- **Spring Boot Actuator** - metryki aplikacji
- **Micrometer** - eksport metryk
- **Health Checks** - sprawdzanie stanu aplikacji

Endpointy monitoringu:
- `/actuator/health` - status aplikacji
- `/actuator/metrics` - metryki
- `/actuator/info` - informacje o aplikacji

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

## 🤝 Współpraca

### Jak zacząć?

1. Fork repozytorium
2. Utwórz branch dla swojej funkcji (`git checkout -b feature/new-feature`)
3. Commituj zmiany (`git commit -am 'Add new feature'`)
4. Push do brancha (`git push origin feature/new-feature`)
5. Utwórz Pull Request

### Standardy kodowania

- Używamy **Google Java Style Guide**
- Pokrycie testami minimum 80%
- Wszystkie publiczne klasy i metody muszą mieć dokumentację
- Commity w języku angielskim zgodnie z **Conventional Commits**

### Code Review

Każdy Pull Request musi być zatwierdzony przez co najmniej jednego reviewera przed mergem do brancha `main`.

## 📄 Licencja

Ten projekt jest licencjonowany na podstawie licencji MIT - zobacz plik [LICENSE](LICENSE) dla szczegółów.

## 👥 Autorzy

- **Piotr Korniak** - *Autor i główny maintainer* - [@piotr-korniak](https://github.com/piotr-korniak)

## 📞 Kontakt

- 📧 Email: [kontakt w profilu GitHub]
- 🐛 Issues: [GitHub Issues](https://github.com/piotr-korniak/jan-ksiegowy-backend/issues)
- 💬 Dyskusje: [GitHub Discussions](https://github.com/piotr-korniak/jan-ksiegowy-backend/discussions)

## 🙏 Podziękowania

- Społeczność Spring Boot
- Polscy księgowi za feedback biznesowy
- Wszyscy kontrybutorzy

---

**Jan Księgowy** - Nowoczesne księgowość dla nowoczesnych firm 🇵🇱

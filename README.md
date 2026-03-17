# 🔗 Advanced URL Shortener & Analytics Engine
A high-performance **URL Shortening Service** built with **Spring Boot 3** and **PostgreSQL 16**.  
This project goes beyond basic CRUD by implementing a **non-blocking telemetry engine** to capture real-time visitor analytics without impacting redirection latency.

## 🧱 Tech Stack
- **Language:** Java 21 (utilizing modern **Records**)
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL 16
- **DevOps:** Docker & Docker Compose
- **Concurrency:** Spring `@Async` Task Execution
- **Utilities:** Base62 Encoding, User-Agent Parsing

---

## ⚙️ Features
✅ **Async Telemetry:** Captures visitor metadata (IP, OS, Browser) in the background.  
✅ **Base62 Encoding:** Generates unique, URL-safe short codes.  
✅ **Unique Visitor Tracking:** Uses set-logic to distinguish between total clicks and unique users.  
✅ **Dockerized:** One-command deployment for both the app and the database.  
✅ **X-Forwarded-For Support:** Correctly identifies client IPs behind proxies/Docker bridges.

## 📁 Project Structure
```
src/
└── main/
├── java/
│    └── org.vedant.urlshortener/
│          ├── config/       # Async & Web Configuration
│          ├── controller/   # REST Endpoints
│          ├── dto/          # Data Transfer Objects (Records)
│          ├── service/      # Business Logic & Analytics
│          ├── repository/   # JPA Repositories
│          ├── model/        # Database Entities
│          └── UrlShortenerApplication.java
└── resources/
├── application.properties
└── static/
```
## ⚡ API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/shorten` | Shortens a long URL and returns the mapping |
| `GET`  | `/{shortCode}` | **Redirector:** Triggers Async telemetry and redirects |
| `GET`  | `/api/getstats/{shortCode}` | Fetches full analytics (Total/Unique clicks + Event List) |

---

## 🐳 Run with Docker (Recommended)

The entire stack is containerized. To start the app and PostgreSQL database together:
``` bash
docker-compose up --build
```

```
http://localhost:8080/api/urls/
```

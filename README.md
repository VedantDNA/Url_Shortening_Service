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
The application will be live at: http://localhost:8080/api/urls/
```
---

# 🧩 Example Analytics Usage

## Request:
```
GET /api/getstats/1yG5Z/
```
## Response:

``` json
{
  "shortCode": "1yG5Z",
  "originalUrl": "[https://www.youtube.com/](https://www.youtube.com/)...",
  "totalClicks": 6,
  "uniqueVisitors": 2,
  "clickStats": [
    {
      "ipAddress": "192.168.65.1",
      "platform": "MacOS",
      "browser": "Chrome",
      "createdAt": "2026-03-17T12:57:08"
    }
  ]
}
```
---

# 🧠 Learning Outcomes
System Design: Decoupled the critical path (Redirect) from side effects (Telemetry) using @Async.

Data Aggregation: Implemented DTOs to merge relational data with analytical event streams.

Modern Java: Utilized Java Records for immutable, thread-safe data transfer.

DevOps: Managed multi-container environments using Docker Compose.
---
## 👨 Author
Vedant Arsule 📍 Aspiring Backend Developer

🔗 [GitHub](https://github.com/VedantDNA/) • [LinkedIn](https://www.linkedin.com/in/vedant-arsule/)
---
⭐ If you like this project, give it a star on GitHub!

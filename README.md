Perfect 👍 Here’s a **complete and polished `README.md`** for your URL Shortener project — ready to copy directly into your repo root.

---

```markdown
# 🔗 URL Shortener Application

A simple and efficient **URL Shortening Service** built with **Spring Boot** and **PostgreSQL**.  
It provides REST APIs to shorten long URLs, redirect to original links, and fetch URL statistics.  
This project demonstrates key backend concepts such as layered architecture, entity mapping, and clean RESTful design.

---

## 🧱 Tech Stack
- **Language:** Java 17  
- **Framework:** Spring Boot  
- **Database:** PostgreSQL  
- **ORM:** Spring Data JPA  
- **Utilities:** Lombok, Base62 Encoding  
- **Build Tool:** Maven  

---

## ⚙️ Features
✅ Shorten any long URL into a unique Base62 short code  
✅ Redirect users from the short code to the original URL  
✅ Retrieve all stored URL mappings  
✅ RESTful endpoints following clean service-repository design  
✅ Auto-timestamped URL entries (using `@PrePersist`)  

---

## 📁 Project Structure
```

src/
└── main/
├── java/
│    └── org.vedant.urlshortener/
│          ├── controller/
│          ├── service/
│          ├── repository/
│          ├── model/
│          └── UrlShortenerApplication.java
└── resources/
├── application.properties
└── static/

````

---

## ⚡ API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/shorten` | Shortens a long URL and returns the short code |
| `GET`  | `/{shortCode}` | Redirects to the original URL |
| `GET`  | `/api/{shortCode}` | Fetches URL details (original URL, created date, etc.) |
| `GET`  | `/api/all` | Returns all URL mappings |

---

## 🗄️ Database Setup (PostgreSQL)

1. **Create a database**  
   ```sql
   CREATE DATABASE url_shortener;
````

2. **Create a user (optional)**

   ```sql
   CREATE ROLE url_short WITH LOGIN PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE url_shortener TO url_short;
   ```

3. **Update your `application.properties`:**

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
   spring.datasource.username=url_short
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

---

## ▶️ Run the Application

### Using Maven:

```bash
mvn spring-boot:run
```

### Or build and run the JAR:

```bash
mvn clean package
java -jar target/UrlShortenerApplication-0.0.1-SNAPSHOT.jar
```

Once started, visit:

```
http://localhost:8080
```

---

## 🧩 Example Usage

**Request:**

```bash
POST /api/shorten
Content-Type: application/json

{
  "longUrl": "https://www.example.com/articles/spring-boot-url-shortener"
}
```

**Response:**

```json
{
  "shortCode": "aB3x9Z",
  "originalUrl": "https://www.example.com/articles/spring-boot-url-shortener",
  "createdAt": "2025-10-14T12:34:56"
}
```

Now you can access the original link using:

```
GET http://localhost:8080/aB3x9Z
```

---

## 🧠 Learning Outcomes

* Implemented layered Spring Boot architecture (`Controller → Service → Repository`)
* Used JPA entity lifecycle (`@PrePersist`) for timestamps
* Practiced Base62 encoding for short code generation
* Integrated PostgreSQL with Spring Data JPA

---

## 💡 Future Improvements

* Add click tracking and analytics
* Implement user-based URL management
* Add expiration dates for short links
* Deploy to cloud (Render / Railway / AWS)

---

## 👨‍💻 Author

**Vedant Arsule**
📍 Backend Developer in progress
🔗 [GitHub](https://github.com/vedantarsule) • [LinkedIn](https://www.linkedin.com/in/vedantarsule)

---

⭐ *If you like this project, give it a star on GitHub!*

```

---

Would you like me to make it more **recruiter-friendly** (like including resume-ready project summary and deployment section), or keep it simple for now?
```

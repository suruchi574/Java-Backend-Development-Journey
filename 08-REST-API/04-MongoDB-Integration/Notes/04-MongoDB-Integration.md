# MongoDB Integration with Spring Boot

## Today's Topic

Integrated **MongoDB with Spring Boot** in the JournalApp and started building REST APIs using Spring MVC and Spring Data MongoDB.

---

## 1. Adding MongoDB Dependency

Added the Spring Data MongoDB starter in `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

This provides the required Spring Data MongoDB support, including `MongoRepository`.

---

## 2. MongoDB Configuration

Configured MongoDB in `application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=journaldb

# Optional authentication
# spring.data.mongodb.username=MyJournaluser
# spring.data.mongodb.password=mypassword
```

### Important

- MongoDB runs locally on port `27017`.
- The application uses the `journaldb` database.
- MongoDB can create the database when data is first stored if it does not already exist.

---

## 3. MongoDB Data Model

For MongoDB, the `JournalEntry` class is mapped as a document.

```java
@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private String id;

    private String title;
    private String content;
    private Date date;

    // Getters and Setters
}
```

### `@Document`

```java
@Document(collection = "journal_entries")
```

Tells Spring Data MongoDB that `JournalEntry` is a MongoDB document and specifies the collection name.

If the collection name is not specified, Spring Data MongoDB can derive it from the class name.

### `@Id`

```java
@Id
private String id;
```

Marks `id` as the identifier of the MongoDB document.

MongoDB uses an identifier for each document. Spring Data MongoDB maps the Java field marked with `@Id` to the document's `_id` field.

---

## 4. Repository Layer

Created `JournalEntryRepository`:

```java
package com.barbighaiya.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.barbighaiya.journalApp.entity.JournalEntry;

public interface JournalEntryRepository
        extends MongoRepository<JournalEntry, String> {

}
```

### What I Learned

`MongoRepository<T, ID>` provides ready-made database operations.

Here:

- `JournalEntry` → entity/document type
- `String` → type of the document ID

Common methods include:

```java
findAll()
findById(id)
save(entity)
deleteById(id)
insert(entity)
```

This means we do not need to write basic CRUD database operations manually.

---

## 5. Application Layer Flow

The basic backend flow is:-

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MongoDB
```

The Controller handles HTTP requests, the Service contains business logic, and the Repository communicates with MongoDB.

---

# 6. REST Controller

Created `JournalEntryController`:

```java
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @GetMapping
    public List<JournalEntry> getAll() {
        return null;
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry journalEntryByID(@PathVariable String myId) {
        return null;
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntryByID(@PathVariable String myId) {
        return null;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalById(
            @PathVariable String id,
            @RequestBody JournalEntry myEntry) {
        return null;
    }
}
```

At this stage, the controller methods are only the initial API structure. The actual MongoDB operations will be connected through the Service and Repository layers.

---

## 7. `@RestController`

```java
@RestController
```

Marks the class as a REST controller.

Instead of returning an HTML view, the methods return data such as Java objects that Spring converts into JSON.

---

## 8. `@RequestMapping`

```java
@RequestMapping("/journal")
```

Defines the common base URL for all endpoints in this controller.

For example:

```text
GET /journal
POST /journal
GET /journal/id/{myId}
DELETE /journal/id/{myId}
PUT /journal/id/{id}
```

---

# 9. REST APIs Implemented/Planned

## Get All Journal Entries

```java
@GetMapping
public List<JournalEntry> getAll()
```

### Request

```http
GET /journal
```

### Expected Response

```json
[
  {
    "id": "64abc123",
    "title": "My First Entry",
    "content": "Learning MongoDB",
    "date": "2026-08-25T00:00:00.000+00:00"
  }
]
```

The current method returns `null`; the next step is to fetch the data using the Service and Repository.

---

## Create Journal Entry

```java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry)
```

### Request

```http
POST /journal
Content-Type: application/json
```

### Request Body

```json
{
  "title": "My First Entry",
  "content": "Learning MongoDB with Spring Boot",
  "date": "2026-08-25"
}
```

### `@RequestBody`

`@RequestBody` tells Spring to take the JSON request body and convert it into a Java `JournalEntry` object.

Flow:

```text
JSON Request
     ↓
@RequestBody
     ↓
JournalEntry Java Object
     ↓
Service
     ↓
Repository
     ↓
MongoDB
```

---

## Get Journal Entry by ID

```java
@GetMapping("id/{myId}")
public JournalEntry journalEntryByID(@PathVariable String myId)
```

### Request

```http
GET /journal/id/64abc123
```

### `@PathVariable`

```java
@PathVariable String myId
```

Extracts the value from the URL.

For:

```text
/journal/id/64abc123
```

the value of `myId` is:

```text
64abc123
```

This ID can then be used to find a specific document.

---

## Delete Journal Entry

```java
@DeleteMapping("id/{myId}")
public JournalEntry deleteJournalEntryByID(@PathVariable String myId)
```

### Request

```http
DELETE /journal/id/64abc123
```

The ID from the URL is used to identify the journal entry that should be deleted.

---

## Update Journal Entry

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable String id,
        @RequestBody JournalEntry myEntry)
```

### Request

```http
PUT /journal/id/64abc123
Content-Type: application/json
```

### Request Body

```json
{
  "title": "Updated Entry",
  "content": "Updated journal content",
  "date": "2026-08-25"
}
```

The `id` identifies which document should be updated, while `@RequestBody` provides the new data.

---

# 10. MongoDB vs Relational Database Mapping

For the JournalApp, the concepts can be understood as:

| Relational Database | MongoDB |
|---|---|
| Database | Database |
| Table | Collection |
| Row | Document |
| Primary Key | `_id` |
| JPA Entity | MongoDB Document |

For example:

```text
MongoDB
└── journaldb
    └── journal_entries
        ├── Document 1
        ├── Document 2
        └── Document 3
```

A document can look like:

```json
{
  "_id": "64abc123",
  "title": "My First Entry",
  "content": "Learning MongoDB",
  "date": "2026-08-25"
}
```

---

# 11. What I Implemented Today

- Added Spring Data MongoDB dependency.
- Configured local MongoDB connection.
- Created the `JournalEntry` MongoDB document.
- Used `@Document` to map the class to a collection.
- Used `@Id` for the document identifier.
- Created `JournalEntryRepository`.
- Extended `MongoRepository<JournalEntry, String>`.
- Learned the basic MongoDB CRUD repository methods.
- Created the REST controller structure.
- Added endpoints for GET, POST, PUT and DELETE.
- Practiced `@RequestBody` for JSON request data.
- Practiced `@PathVariable` for IDs in URLs.
- Added `/journal` as the base API path.

---

# 12. Current Project Structure

```text
src/main/java
└── com.barbighaiya.journalApp
    │
    ├── JournalApplication.java
    │
    ├── controller
    │   ├── HealthCheck.java
    │   └── JournalEntryController.java
    │
    ├── entity
    │   └── JournalEntry.java
    │
    ├── repository
    │   └── JournalEntryRepository.java
    │
    └── service
        └── JournalEntryService.java

src/main/resources
├── static
├── templates
└── application.properties
```

---

# 13. Next Step

The controller currently contains placeholder return values.

Next, connect the layers:

```text
Controller
    ↓
Service
    ↓
JournalEntryRepository
    ↓
MongoDB
```

Then implement:

- Save journal entry
- Get all entries
- Get entry by ID
- Delete entry by ID
- Update entry by ID

---

# 🔑 Key Takeaways

1. `spring-boot-starter-data-mongodb` provides Spring Data MongoDB support.
2. `@Document` maps a Java class to a MongoDB collection.
3. `@Id` maps the Java ID field to the MongoDB document identifier.
4. `MongoRepository` provides ready-made CRUD operations.
5. `@RequestBody` converts JSON request data into a Java object.
6. `@PathVariable` extracts values such as IDs from the URL.
7. `@RequestMapping("/journal")` provides a common base path for the controller.
8. The intended architecture is:

```text
Controller → Service → Repository → MongoDB
```

9. MongoDB stores data as **documents inside collections**, rather than rows inside tables.

# MongoDB Integration with Spring Boot – Journal Entry CRUD

## What I Learned

- How to connect a Spring Boot application with MongoDB.
- How to use `MongoRepository` through a service layer.
- How `@RequestBody` converts JSON request data into a Java object.
- How `@PathVariable` extracts values such as an ID from the URL.
- How `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping` are used for REST APIs.
- How the Controller → Service → Repository flow works.

## MongoDB Configuration

`application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/journaldb
```

- MongoDB is running on `localhost:27017`.
- `journaldb` is the database used by the application.
- MongoDB can create the database when data is first inserted.

## MongoDB Configuration Class

```java
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "journaldb";
    }
}
```

This configuration specifies `journaldb` as the MongoDB database.

## Service Layer

The `JournalEntryService` uses `JournalEntryRepository` to save journal entries.

```java
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
}
```

### Flow

```text
Client
   ↓
Controller
   ↓
Service
   ↓
JournalEntryRepository
   ↓
MongoDB
```

## REST API Implementation

### Create Journal Entry

**Endpoint:**

```http
POST /journal
```

**Request Body:**

```json
{
    "title": "My First Entry",
    "content": "Learning MongoDB with Spring Boot"
}
```

Controller:

```java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {
    journalEntryService.saveEntry(myEntry);
    return true;
}
```

`@RequestBody` converts the JSON request into a `JournalEntry` Java object.

## API Endpoints Planned

| HTTP Method | Endpoint | Purpose |
|---|---|---|
| GET | `/journal` | Get all journal entries |
| POST | `/journal` | Create a journal entry |
| GET | `/journal/id/{myId}` | Get entry by ID |
| DELETE | `/journal/id/{myId}` | Delete entry by ID |
| PUT | `/journal/id/{id}` | Update entry by ID |

## Current Implementation Status

- ✅ MongoDB connection configured
- ✅ `JournalEntryService` created
- ✅ Save operation implemented
- ✅ POST `/journal` API implemented
- ⏳ Get all entries
- ⏳ Get entry by ID
- ⏳ Delete entry
- ⏳ Update entry

## Key Takeaways

- `Controller` handles HTTP requests.
- `Service` contains application logic.
- `Repository` handles database operations.
- `@RequestBody` is used for JSON → Java object conversion.
- `@PathVariable` is used to extract values from the URL.
- MongoDB integration uses Spring Data MongoDB instead of JPA.
- CRUD APIs can be built using Spring Boot REST controllers with `MongoRepository`.

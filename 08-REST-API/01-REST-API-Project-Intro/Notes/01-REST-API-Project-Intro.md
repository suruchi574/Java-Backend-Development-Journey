# REST API – Journal App

## 1. Project Introduction

In this project, we are creating a completely new **Journal App** using **Spring Boot**.

The purpose of this project is to understand important backend concepts practically, starting from a simple REST API and gradually moving towards a complete Journal application.

We will use this project to learn concepts such as:

- REST API
- HTTP methods
- Controllers
- Endpoints
- POJO / Entity classes
- Request and response
- Postman
- CRUD operations
- Database integration
- Repository and Service layers
- Exception handling
- Validation
- Authentication and other backend concepts

---

# 2. Project Configuration in Eclipse

The project was created using **Spring Starter Project** in Eclipse.

### Configuration

| Setting | Value |
|---|---|
| Project Type | Maven |
| Language | Java |
| Spring Boot | Spring Boot version selected by Spring Starter |
| Java Version | 21 |
| Group | `com.barbighaiya` |
| Artifact | `journalApp` |
| Name | `journalApp` |
| Version | `0.0.1-SNAPSHOT` |
| Description | Can be set as `E2EE Journal App` |
| Package | `com.barbighaiya.journalApp` |
| Packaging | Jar |
| Dependency | Spring Web |

### Important

The lecture configuration used an older setup:

```text
Spring Boot: 2.7.16
Java: 8
```

However, **our Eclipse project is being created with Java 21**, as shown in the configuration window.

Therefore, these notes follow **our actual Eclipse project configuration**, not the Java 8 configuration from the lecture.

The main concepts of REST API, controllers, mappings, Postman, etc. remain the same.

---

# 3. Why Spring Web Dependency?

While creating the project, we selected:

```text
Spring Web
```

Spring Web is required because we are building a web application / REST API.

It provides the functionality required to:

- Handle HTTP requests
- Create REST controllers
- Map URLs to Java methods
- Build REST APIs
- Run the application using an embedded web server
- Return responses to clients

For example, annotations such as:

```java
@RestController
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

are part of the Spring Web ecosystem.

---

# 4. What is a REST API?

REST stands for:

> **Representational State Transfer**

REST is an **architectural style** used to design web services that allow applications to communicate over HTTP.

API stands for:

> **Application Programming Interface**

Therefore, a REST API provides a standardized way for a client and server to communicate using HTTP.

### Simple example

Suppose Netflix exposes an endpoint:

```text
http://172.17.18.19:8080/netflix/subscription
```

A client can send a request to this endpoint to retrieve subscription information.

The server processes the request and sends a response.

```text
Client
   |
   | HTTP Request
   v
Server
   |
   | HTTP Response
   v
Client
```

For example, the response may contain subscription plans such as:

```text
₹60
₹600
₹3999
```

---

# 5. Why do we need REST APIs?

Suppose we have a frontend application and a backend server.

The frontend needs some information from the backend.

For example:

```text
Frontend
   |
   | "Give me all journal entries"
   v
Backend
   |
   | Journal entries
   v
Frontend
```

The REST API provides the communication mechanism between them.

So:

```text
Client
   ↓
REST API
   ↓
Backend
   ↓
Database
```

and the response travels back:

```text
Database
   ↓
Backend
   ↓
REST API
   ↓
Client
```

---

# 6. URL, IP Address, Port and Endpoint

Consider:

```text
http://172.17.18.19:8080/netflix/subscription
```

We can understand it as:

```text
http://172.17.18.19:8080/netflix/subscription
       └──────┬──────┘ └─┬─┘ └──────┬────────┘
              IP        Port       Endpoint
```

### IP Address

The IP address identifies the machine/server.

Example:

```text
172.17.18.19
```

### Port

A port identifies a particular service/application running on a machine.

Example:

```text
8080
```

Spring Boot commonly uses port `8080` by default.

### Endpoint

The endpoint identifies a particular resource or operation exposed by the application.

Example:

```text
/netflix/subscription
```

So, in a simplified form:

```text
Server Address + Port + Endpoint
```

makes up the URL used to access the API.

---

# 7. HTTP Request

A URL alone does not tell the server exactly what operation we want to perform.

We also use an **HTTP method / HTTP verb**.

For example:

```text
GET http://localhost:8080/health-check
```

Here:

```text
GET
 ↓
HTTP Method

http://localhost:8080
 ↓
Server Address

/health-check
 ↓
Endpoint
```

---

# 8. HTTP Methods

The four main HTTP methods we will use are:

| HTTP Method | Purpose |
|---|---|
| GET | Retrieve/read data |
| POST | Create new data |
| PUT | Update/modify existing data |
| DELETE | Delete data |

---

## 8.1 GET

`GET` is used to retrieve data.

Example:

```http
GET /journal
```

Meaning:

> Give me the journal entries.

Our first API will also use GET:

```http
GET /health-check
```

---

## 8.2 POST

`POST` is generally used to create new data.

Example:

```http
POST /journal
```

Request body:

```json
{
    "title": "My First Entry",
    "content": "Today I started learning REST API."
}
```

The server can create a new journal entry using this information.

---

## 8.3 PUT

`PUT` is used to update existing data.

Example:

```http
PUT /journal/1
```

Meaning:

> Update journal entry with ID 1.

---

## 8.4 DELETE

`DELETE` is used to remove data.

Example:

```http
DELETE /journal/1
```

Meaning:

> Delete journal entry with ID 1.

---

# 9. REST API and HTTP Methods

At a basic level, we can think of an API operation as:

```text
HTTP Method + Endpoint
```

For example:

```text
GET     /journal
POST    /journal
PUT     /journal/1
DELETE  /journal/1
```

Notice that the endpoint can be similar while the HTTP method changes the intended operation.

```text
GET /journal
      ↓
Read journals

POST /journal
      ↓
Create journal

PUT /journal/1
      ↓
Update journal 1

DELETE /journal/1
      ↓
Delete journal 1
```

> REST is broader than simply "URL + HTTP verb"; REST is an architectural style with several principles. HTTP methods and resource-oriented URLs are common ways of implementing RESTful APIs.

---

# 10. What is a Controller?

A **Controller** is a Spring component responsible for handling incoming HTTP requests.

In our REST application, the controller acts as a bridge between the client and backend logic.

It can:

- Receive HTTP requests
- Match requests to Java methods
- Process or delegate the request
- Return a response

Basic flow:

```text
Client
   |
   | HTTP Request
   v
Controller
   |
   | Java Method
   v
Response
   |
   v
Client
```

---

# 11. What is @RestController?

`@RestController` is a Spring annotation used to mark a class as a **REST controller**.

Example:

```java
@RestController
public class HealthCheck {

}
```

It tells Spring:

> Treat this class as a controller that handles HTTP requests and returns data directly in the HTTP response.

`@RestController` is effectively a combination of:

```java
@Controller
@ResponseBody
```

The `@ResponseBody` behavior means that the method's return value is written directly to the HTTP response body instead of being interpreted as a view name.

---

# 12. What is @GetMapping?

`@GetMapping` is used to map an HTTP **GET request** to a Java method.

Example:

```java
@GetMapping("/health-check")
public String healthCheck() {
    return "ok";
}
```

This means:

```text
GET /health-check
       |
       v
healthCheck()
       |
       v
"ok"
```

Whenever a GET request comes to:

```text
http://localhost:8080/health-check
```

Spring finds the matching method and executes it.

---

# 13. Our First REST Controller

We created a `HealthCheck` controller.

## HealthCheck.java

```java
package com.barbighaiya.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }
}
```

---

# 14. Understanding HealthCheck.java

Let's understand it line by line.

### Package

```java
package com.barbighaiya.journalApp.controller;
```

The class belongs to the `controller` package.

---

### Import

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
```

These imports allow us to use:

```java
@RestController
@GetMapping
```

---

### @RestController

```java
@RestController
```

Tells Spring that this class is a REST controller.

---

### Class

```java
public class HealthCheck {
```

This creates the controller class.

---

### @GetMapping

```java
@GetMapping("/health-check")
```

Maps:

```text
GET /health-check
```

to the method below it.

---

### Method

```java
public String healthCheck() {
```

This method handles the request.

---

### Return value

```java
return "ok";
```

The method returns:

```text
ok
```

Spring sends this back as the HTTP response.

---

# 15. Complete Health Check Flow

```text
Postman
   |
   | GET /health-check
   v
Spring Boot Application
   |
   v
HealthCheck Controller
   |
   v
@GetMapping("/health-check")
   |
   v
healthCheck()
   |
   v
return "ok"
   |
   v
HTTP Response
   |
   v
Postman
```

Response:

```text
ok
```

Status:

```text
200 OK
```

---

# 16. Spring Boot Main Class

Our main class is:

```java
package com.barbighaiya.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }
}
```

This is the starting point of our Spring Boot application.

---

# 17. What is @SpringBootApplication?

`@SpringBootApplication` is one of the most important Spring Boot annotations.

It combines three annotations:

```text
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

---

## 17.1 @SpringBootConfiguration

It identifies the class as a source of Spring Boot configuration.

---

## 17.2 @EnableAutoConfiguration

It tells Spring Boot to automatically configure the application based on the dependencies present in the project.

For example, because we selected:

```text
Spring Web
```

Spring Boot can configure the web application infrastructure and embedded web server.

---

## 17.3 @ComponentScan

It tells Spring to scan the package and its subpackages for Spring components.

Examples:

```text
@RestController
@Controller
@Service
@Repository
@Component
```

Our main class is located at:

```text
com.barbighaiya.journalApp
```

and our controller is located at:

```text
com.barbighaiya.journalApp.controller
```

Since `controller` is a subpackage of `journalApp`, Spring can discover the controller through component scanning.

---

# 18. Why is Package Structure Important?

Our structure is:

```text
com.barbighaiya.journalApp
│
├── JournalApplication.java
│
├── controller
│   ├── HealthCheck.java
│   └── JournalEntryController.java
│
└── entity
    └── JournalEntry.java
```

The main application class is at the root:

```text
com.barbighaiya.journalApp
```

and other components are placed below it.

This makes component scanning straightforward.

---

# 19. What does SpringApplication.run() do?

Inside our main method:

```java
SpringApplication.run(JournalApplication.class, args);
```

starts the Spring Boot application.

Simplified flow:

```text
main()
   |
   v
SpringApplication.run()
   |
   v
Spring Boot starts
   |
   v
Spring Application Context starts
   |
   v
Component scanning
   |
   v
Controllers detected
   |
   v
Embedded server starts
   |
   v
Application ready
```

---

# 20. Embedded Tomcat

A Spring Boot web application can run using an embedded web server.

With Spring Web, our application can use embedded Tomcat.

This means we do not need to separately deploy the application to an externally installed Tomcat server just to run it.

The application can be started directly using:

```java
SpringApplication.run(...)
```

By default, the server runs on:

```text
http://localhost:8080
```

---

# 21. Project Structure

Our Eclipse project will look approximately like this:

```text
journalApp
│
├── src
│   │
│   ├── main
│   │   │
│   │   ├── java
│   │   │   │
│   │   │   └── com.barbighaiya.journalApp
│   │   │       │
│   │   │       ├── JournalApplication.java
│   │   │       │
│   │   │       ├── controller
│   │   │       │   ├── HealthCheck.java
│   │   │       │   └── JournalEntryController.java
│   │   │       │
│   │   │       └── entity
│   │   │           └── JournalEntry.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│
├── target
├── pom.xml
├── mvnw
├── mvnw.cmd
└── HELP.md
```

---

# 22. What does each package/file do?

## controller

The controller package contains classes responsible for handling HTTP requests.

Current classes:

```text
HealthCheck.java
JournalEntryController.java
```

Example:

```java
@GetMapping("/health-check")
```

belongs to a controller.

---

## entity

The entity package contains classes that represent application data.

Current class:

```text
JournalEntry.java
```

It represents a journal entry.

---

## JournalApplication.java

This is the main Spring Boot class.

It starts the application using:

```java
SpringApplication.run()
```

---

## resources

The resources folder contains configuration and other application resources.

For example:

```text
application.properties
```

Later we may also have:

```text
schema.sql
data.sql
```

depending on the application requirements.

---

## pom.xml

`pom.xml` is Maven's project configuration file.

It contains information such as:

- Project metadata
- Dependencies
- Plugins
- Build configuration

Because we selected Spring Web, Maven adds the required Spring Web dependencies to the project.

---

# 23. JournalEntry Class

We created a class to represent our journal entry.

```java
package com.barbighaiya.journalApp.entity;

public class JournalEntry {

    private long id;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```

---

# 24. What is a POJO?

POJO stands for:

> **Plain Old Java Object**

A POJO is a normal Java class used to represent or hold data.

Our current `JournalEntry` class is a simple POJO.

It contains:

```java
private long id;
private String title;
private String content;
```

and corresponding getters and setters.

---

# 25. Why did we create JournalEntry?

Our application is a Journal App.

Therefore, we first need to define what a journal entry looks like.

For example:

```text
JournalEntry
-------------------
id
title
content
```

One journal entry could be:

```text
id      = 1
title   = "My First Entry"
content = "Today I started learning Spring Boot."
```

Currently, the class only represents the structure of the data.

Later, we can store this data in a database.

---

# 26. JournalEntryController

We also created:

```java
package com.barbighaiya.journalApp.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalEntryController {

    /*
     * We will be writing specific endpoints here
     */

}
```

This controller will contain the APIs related to journal entries.

For example, later we can create:

```text
GET     /journal
GET     /journal/{id}
POST    /journal
PUT     /journal/{id}
DELETE  /journal/{id}
```

---

# 27. CRUD Operations

CRUD stands for:

```text
C → Create
R → Read
U → Update
D → Delete
```

Typical REST mapping:

| CRUD | HTTP Method | Example |
|---|---|---|
| Create | POST | `POST /journal` |
| Read | GET | `GET /journal` |
| Update | PUT | `PUT /journal/1` |
| Delete | DELETE | `DELETE /journal/1` |

These will eventually become the main operations of our Journal App.

---

# 28. What is Postman?

**Postman** is an API testing tool.

It allows us to send HTTP requests to our backend application and inspect the response.

We can use Postman to test:

```text
GET
POST
PUT
DELETE
```

requests.

---

# 29. Why do we use Postman?

Suppose our API is:

```text
GET http://localhost:8080/health-check
```

We can open this GET URL in a browser.

But as our application grows, we will need to test:

```text
POST
PUT
DELETE
```

and send request bodies, headers, parameters, etc.

Postman makes this much easier.

Therefore:

```text
Postman
   |
   | HTTP Request
   v
Spring Boot REST API
   |
   | HTTP Response
   v
Postman
```

---

# 30. Running the Application in Eclipse

In Eclipse:

1. Open the `journalApp` project.
2. Open:

```text
JournalApplication.java
```

3. Right-click the file.
4. Select:

```text
Run As → Java Application
```

or use the Spring Boot run option available in your Eclipse/Spring Tools installation.

Spring Boot starts the application.

The embedded server starts, normally on:

```text
8080
```

---

# 31. Testing Our API in Postman

We created:

```java
@GetMapping("/health-check")
public String healthCheck() {
    return "ok";
}
```

Now we can test it using Postman.

### Step 1 – Open Postman

Open the Postman application.

### Step 2 – Select Method

Select:

```text
GET
```

### Step 3 – Enter URL

Enter:

```text
http://localhost:8080/health-check
```

### Step 4 – Click Send

Click:

```text
Send
```

### Step 5 – Check Response

The response should be:

```text
ok
```

and the status should be:

```text
200 OK
```

---

# 32. What happened when we clicked Send?

When we clicked **Send** in Postman:

```text
Postman
   |
   | GET http://localhost:8080/health-check
   v
localhost:8080
   |
   v
Spring Boot
   |
   v
HealthCheck Controller
   |
   v
@GetMapping("/health-check")
   |
   v
healthCheck()
   |
   v
"ok"
   |
   v
HTTP 200 OK
   |
   v
Postman
```

Postman displays:

```text
ok
```

---

# 33. What does 200 OK mean?

HTTP status codes tell the client the result of a request.

Our request was successfully processed.

Therefore:

```text
200 OK
```

means the request was successful.

Some common status codes are:

| Status | Meaning |
|---|---|
| 200 | OK / Successful |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

We will learn these in more detail as we build the application.

---

# 34. Complete REST API Flow

The complete flow of our first API is:

```text
                 JOURNAL APP

              +-------------+
              |   Postman   |
              +-------------+
                     |
                     |
               HTTP GET Request
                     |
                     v
        GET /health-check
                     |
                     v
          +-------------------+
          |   Spring Boot     |
          |    Application    |
          +-------------------+
                     |
                     v
          +-------------------+
          |   HealthCheck     |
          |    Controller     |
          +-------------------+
                     |
                @GetMapping
                     |
                     v
              healthCheck()
                     |
                     v
                   "ok"
                     |
                     v
                HTTP 200 OK
                     |
                     v
              +-------------+
              |   Postman   |
              +-------------+
```

---

# 35. Request-to-Response Flow

It is important to understand this sequence:

```text
1. Client sends HTTP request
              ↓
2. Request reaches embedded Tomcat
              ↓
3. Spring receives the request
              ↓
4. Spring finds the appropriate controller
              ↓
5. @GetMapping matches HTTP method + URL
              ↓
6. Java method executes
              ↓
7. Method returns the response
              ↓
8. Spring creates the HTTP response
              ↓
9. Client receives the response
```

For our API:

```text
GET http://localhost:8080/health-check
                    ↓
              HealthCheck
                    ↓
         healthCheck() method
                    ↓
                 "ok"
                    ↓
                200 OK
```

---

# 36. Important Annotations Used So Far

| Annotation | Purpose |
|---|---|
| `@SpringBootApplication` | Marks the main Spring Boot class and enables configuration, auto-configuration and component scanning |
| `@RestController` | Marks a class as a REST controller |
| `@GetMapping` | Maps an HTTP GET request to a Java method |

Example:

```java
@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }
}
```

---

# 37. Difference Between URL and Endpoint

These terms are related but not exactly the same.

### URL

A complete address:

```text
http://localhost:8080/health-check
```

### Endpoint

The path exposed by the application:

```text
/health-check
```

Therefore:

```text
http://localhost:8080
        +
/health-check
        =
http://localhost:8080/health-check
```

---

# 38. Why are we creating a Health Check API?

The health-check endpoint is a simple way to verify whether our application is running and able to respond to HTTP requests.

Our endpoint:

```text
GET /health-check
```

returns:

```text
ok
```

If we receive:

```text
200 OK
```

we know that the basic REST setup is working.

It is a simple first step before implementing more complicated APIs.

---

# 39. Current Project Architecture

At this initial stage:

```text
                Client / Postman
                       |
                       v
                 HTTP Request
                       |
                       v
              +----------------+
              |   Controller   |
              +----------------+
                       |
                       v
                Java Method
                       |
                       v
                  Response
                       |
                       v
                Client/Postman
```

Later, as the project becomes more complete, we will move towards:

```text
Client
  |
  v
Controller
  |
  v
Service
  |
  v
Repository
  |
  v
Database
```

and the response will travel back:

```text
Database
   ↓
Repository
   ↓
Service
   ↓
Controller
   ↓
Client
```

---

# 40. Why are we building the project step by step?

Instead of creating the complete application at once, we are starting with a very small API:

```text
GET /health-check
```

Then we can gradually add:

```text
REST API
    ↓
Controller
    ↓
Journal API
    ↓
CRUD Operations
    ↓
Service Layer
    ↓
Repository Layer
    ↓
Database
    ↓
Exception Handling
    ↓
Validation
    ↓
Authentication / Authorization
    ↓
Complete Journal Application
```

This makes it easier to understand what each layer and concept is actually doing.

---

# 41. Current Project Status

At this stage, our project contains:

```text
journalApp
│
├── JournalApplication.java
│       └── Starts Spring Boot application
│
├── controller
│   ├── HealthCheck.java
│   │       └── GET /health-check
│   │
│   └── JournalEntryController.java
│           └── Future Journal APIs
│
└── entity
    └── JournalEntry.java
            └── Represents journal entry data
```

Our first working API is:

```http
GET http://localhost:8080/health-check
```

Response:

```text
ok
```

Status:

```text
200 OK
```

---

# 42. Quick Revision

## REST

```text
Representational State Transfer
```

An architectural style for designing networked APIs.

## API

```text
Application Programming Interface
```

A way for software components to communicate.

## Endpoint

A path exposed by the application.

Example:

```text
/health-check
```

## HTTP Methods

```text
GET     → Read
POST    → Create
PUT     → Update
DELETE  → Delete
```

## @RestController

Marks a class as a REST controller.

## @GetMapping

Maps an HTTP GET request to a Java method.

## @SpringBootApplication

Marks the main Spring Boot class and enables important Spring Boot features.

## SpringApplication.run()

Starts the Spring Boot application.

## POJO

Plain Old Java Object used to represent/hold data.

## Postman

Tool used to send HTTP requests and test APIs.

## Spring Web

Dependency that provides the web/REST functionality required for our application.

---

# 43. Final Mental Model

The most important thing to remember from this topic is:

```text
                 CLIENT
            (Postman / Frontend)
                    |
                    | HTTP Request
                    | GET /health-check
                    v
              SPRING BOOT
                    |
                    v
              REST CONTROLLER
                    |
              @GetMapping
                    |
                    v
             JAVA METHOD
                    |
                    v
                RESPONSE
                    |
                    | "ok"
                    v
                 CLIENT
```

For our first API:

```text
GET /health-check
        ↓
HealthCheck Controller
        ↓
healthCheck()
        ↓
"ok"
        ↓
200 OK
```

This is the basic foundation of the REST API part of our **Journal App**.

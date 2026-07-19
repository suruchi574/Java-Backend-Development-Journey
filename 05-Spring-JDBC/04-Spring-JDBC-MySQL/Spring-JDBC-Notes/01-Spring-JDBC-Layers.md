# Spring JDBC (JDBC Template, H2 Database & Layers)

Spring JDBC is a module of the Spring Framework that simplifies database operations.

Instead of writing a large amount of JDBC code, Spring provides the **JdbcTemplate** class, which reduces boilerplate code and makes database programming easier.

---

# What is JDBC?

**JDBC (Java Database Connectivity)** is a Java API that allows a Java application to communicate with a database.

Using JDBC, we can

- Connect to a database
- Insert data
- Retrieve data
- Update data
- Delete data

Without Spring, we have to write many lines of code for every database operation.

Spring JDBC removes most of this repetitive code.

---

# JDBC Steps Without Spring

Whenever we use traditional JDBC, we need to follow these steps.

### 1. Load the Driver

Load the JDBC driver of the database.

Example

```java
Class.forName("org.h2.Driver");
```

---

### 2. Define the Connection URL

Provide the database URL along with username and password.

Example

```java
jdbc:h2:mem:testdb
```

---

### 3. Establish Connection

Create a connection with the database.

```java
Connection con = DriverManager.getConnection(url, username, password);
```

---

### 4. Create Statement

Create a `Statement` or `PreparedStatement` object to execute SQL queries.

```java
PreparedStatement ps = con.prepareStatement(sql);
```

---

### 5. Execute Query

Execute SQL statements.

Examples

```java
executeQuery();
```

or

```java
executeUpdate();
```

---

### 6. Process the Result

Read the returned data using `ResultSet`.

```java
while(resultSet.next()){

}
```

---

### 7. Close Resources

Close the

- ResultSet
- Statement
- Connection

This avoids memory leaks and releases database resources.

---

# Problems with Traditional JDBC

Traditional JDBC requires a lot of repetitive code.

For every database operation, we have to

- Create a connection
- Create statements
- Execute SQL queries
- Handle exceptions
- Close all resources manually

This makes the code lengthy and difficult to maintain.

---

# Spring JDBC

Spring JDBC simplifies database programming.

Instead of writing all the JDBC code manually, Spring provides the **JdbcTemplate** class.

It automatically handles most of the repetitive tasks.

Because of this, developers mainly focus on writing SQL queries and business logic.

---

# JdbcTemplate

`JdbcTemplate` is the core class of Spring JDBC.

It internally handles

- Database connection
- SQL query execution
- Exception handling
- Closing database resources
- Returning query results

Some commonly used methods are

- `update()`
- `query()`
- `queryForObject()`

This reduces boilerplate code and makes the application cleaner.

---

# DataSource

`DataSource` is responsible for providing database connections.

It acts as a **factory** for creating `Connection` objects.

Instead of creating connections manually using `DriverManager`, Spring uses `DataSource`.

Benefits of DataSource

- Creates database connections
- Manages connections automatically
- Supports connection pooling
- Improves application performance

---

# Connection Pooling

Creating a new database connection every time is slow.

Spring stores a group of reusable connections.

Whenever required,

- Spring gives an available connection.
- After use, the connection is returned to the pool.

This process is called **Connection Pooling**.

Benefits

- Better performance
- Faster database access
- Efficient resource management

---

# H2 In-Memory Database

Spring Boot provides built-in support for the **H2 Database**.

H2 is an **In-Memory Database**, which means the data is stored in RAM.

Characteristics

- No separate database installation is required.
- Data is stored only while the application is running.
- When the application stops or restarts, all data is lost.

H2 is mainly used for

- Learning Spring JDBC
- Development
- Testing

It is generally not used in production because the data is temporary.

---

# Why are we using H2 in this project?

We are using H2 because

- It is lightweight.
- It comes with Spring Boot support.
- No manual installation is needed.
- It is perfect for practicing Spring JDBC concepts.

---

# Layered Architecture

Our Spring JDBC project follows a layered architecture.

Each layer has its own responsibility.

```

Application Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Database

```

The request always flows from top to bottom.

After understanding these basic concepts, let's understand each layer of our project.

---

# Project Flow

Application Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Database

The request always flows from top to bottom.

Example:

Application
     │
service.addStudent(student)
     │
StudentService
     │
repo.save(student)
     │
StudentRepo
     │
Database

---

# 1. Model Layer

**Class: Student.java**

The Model layer represents the entity that maps to a table in the database.

In a Spring JDBC project,

- One class represents one database table.
- One object of the class represents one row in that table.

Example:

Student Table

| name | rollNo | marks |

If we create:

Student student = new Student();

that object represents one student's record.

### What is present inside the Model class?

- Variables
- Getter methods
- Setter methods
- toString() method

Example:

```java
private String name;
private int rollNo;
private int marks;
```

### @Component

```java
@Component
```

Makes the Student class a Spring Bean.

Instead of creating the object manually using

```java
new Student();
```

Spring creates and manages the object.

We can get it using

```java
Student s = context.getBean(Student.class);
```

---

### @Scope("prototype")

```java
@Scope("prototype")
```

Every time we call

```java
context.getBean(Student.class);
```

Spring creates a **new object**.

Without prototype scope, Spring creates only one object (Singleton).

---

### Getter and Setter Methods

Getter methods are used to read the values.

Setter methods are used to assign values.

Example

```java
s.setName("Suruchi");
s.setRollNo(61);
s.setMarks(78);
```

---

### toString() Method

The `toString()` method is used to print the object values instead of the object's memory address.

Without `toString()`

```
Student@5a07e868
```

With `toString()`

```
Student[name=Suruchi, rollNo=61, marks=78]
```

---

# 2. Repository Layer

**Class: StudentRepo.java**

The Repository layer is responsible for interacting with the database.

It contains methods to

- Save data
- Retrieve data
- Update data
- Delete data

In this project, we have two methods.

### save()

```java
public void save(Student s)
```

This method receives the Student object from the Service layer.

Currently it only prints

```
Saved
```

Later, we will write SQL queries here to insert data into the database.

---

### findAll()

```java
public List<Student> findAll()
```

This method returns all student records.

Currently it returns an empty list.

Later, this method will fetch all records from the database.

---

### @Repository

```java
@Repository
```

Marks this class as the Repository layer.

Spring automatically creates its object and manages it as a Bean.

---

# 3. Service Layer

**Class: StudentService.java**

The Service layer contains the business logic.

It acts as a bridge between the Application layer and the Repository layer.

The Application layer never directly communicates with the Repository.

Instead, it always goes through the Service layer.

Flow:

```
Application
      │
      ▼
Service
      │
      ▼
Repository
```

---

### Dependency Injection

The Service class needs the Repository object.

```java
private StudentRepo repo;
```

Instead of creating it manually,

Spring injects the object automatically.

```java
@Autowired
public void setRepo(StudentRepo repo) {
    this.repo = repo;
}
```

This is called **Setter Injection**.

---

### addStudent()

```java
public void addStudent(Student s)
```

This method receives the Student object from the Application layer.

It passes the object to the Repository layer.

```java
repo.save(s);
```

Flow

```
Application
      │
      ▼
addStudent()
      │
      ▼
repo.save()
```

---

### getStudents()

```java
public List<Student> getStudents()
```

This method asks the Repository for all student records.

```java
return repo.findAll();
```

Flow

```
Application
      │
      ▼
getStudents()
      │
      ▼
repo.findAll()
```

---

### @Service

```java
@Service
```

Marks this class as the Service layer.

Spring creates and manages this object automatically.

---

# 4. Application Layer

**Class: SpringBootJdbcApplication.java**

The Application layer is the entry point of the Spring Boot application.

Execution starts from the `main()` method.

```java
SpringApplication.run(...)
```

starts the Spring Boot application and creates the Spring Container.

---

### ApplicationContext

```java
ApplicationContext context = SpringApplication.run(...);
```

ApplicationContext stores all Spring Beans.

We use it to get objects managed by Spring.

Example

```java
Student s = context.getBean(Student.class);
```

```java
StudentService service = context.getBean(StudentService.class);
```

---

### Creating Student Object

```java
Student s = context.getBean(Student.class);

s.setName("Suruchi");
s.setRollNo(61);
s.setMarks(78);
```

This creates a Student object and sets its values.

---

### Adding Student

```java
service.addStudent(s);
```

Flow

```
Application
      │
      ▼
StudentService
      │
      ▼
StudentRepo
```

---

### Retrieving Students

```java
List<Student> students = service.getStudents();
```

The request goes

```
Application
      │
      ▼
StudentService
      │
      ▼
StudentRepo
```

The Repository returns the list back to the Service.

The Service returns the list back to the Application.

Finally,

```java
System.out.println(students);
```

prints the list.

---

# Complete Flow of the Program

```
Application Starts
        │
        ▼
Spring creates all required Beans
        │
        ▼
Student Bean is obtained
        │
        ▼
Student details are assigned
        │
        ▼
StudentService Bean is obtained
        │
        ▼
service.addStudent(student)
        │
        ▼
repo.save(student)
        │
        ▼
Student saved
        │
        ▼
service.getStudents()
        │
        ▼
repo.findAll()
        │
        ▼
Student List Returned
        │
        ▼
Print Student List
```

---

# Layers Summary

## Model Layer

- Represents a database table.
- One object represents one row.
- Contains variables, getters, setters, and `toString()`.
- Uses `@Component`.
- Uses `@Scope("prototype")` to create a new object every time.

---

## Repository Layer

- Communicates with the database.
- Contains methods like `save()` and `findAll()`.
- Uses `@Repository`.

---

## Service Layer

- Contains business logic.
- Connects the Application layer with the Repository layer.
- Uses `@Service`.
- Uses Dependency Injection with `@Autowired`.

---

## Application Layer

- Entry point of the application.
- Starts Spring Boot.
- Gets Beans from the Spring Container.
- Calls Service methods to add and retrieve data.

---

# Important Interview Points

**What is the Model Layer?**

Represents a database table. One object represents one row.

---

**What is the Repository Layer?**

Handles database operations like save, retrieve, update, and delete.

---

**What is the Service Layer?**

Contains business logic and acts as a bridge between the Application layer and Repository layer.

---

**What is the Application Layer?**

Starts the application and coordinates the complete flow.

---

**Why do we use `@Component`?**

To make the class a Spring Bean so that Spring can create and manage its object.

---

**Why do we use `@Repository`?**

To mark the database access layer.

---

**Why do we use `@Service`?**

To mark the business logic layer.

---

**Why do we use `@Autowired`?**

To let Spring automatically inject the required dependency instead of creating it manually.

---

**Why do we use `@Scope("prototype")`?**

To create a new object every time `getBean()` is called.

---

# Key Points to Remember

- One class represents one database table.
- One object represents one row.
- Application Layer calls the Service Layer.
- Service Layer calls the Repository Layer.
- Repository Layer communicates with the database.
- Spring creates and manages objects using annotations.
- `ApplicationContext` is used to get Spring Beans.
- `@Autowired` performs Dependency Injection automatically.
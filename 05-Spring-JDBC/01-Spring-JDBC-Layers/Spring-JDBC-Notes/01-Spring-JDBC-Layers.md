# 03: Spring JDBC Layers (Model, Repository, Service & Application)

In this project, we are following a layered architecture.  
Each layer has a different responsibility. This makes the code clean, reusable, and easy to maintain.

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

## 1. Model Layer

**Class:** `Student.java`

The **Model Layer** represents the entity that maps to a table in the database.

- One class represents one database table.
- One object of the class represents one row in that table.
- Contains variables (fields), getters, setters, and the `toString()` method.
- Uses `@Component` so that Spring creates and manages the object.
- Uses `@Scope("prototype")` to create a new object every time `getBean()` is called.

### Code Example

```java
@Component
@Scope("prototype")
public class Student {

    private String name;
    private int rollNo;
    private int marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", rollNo=" + rollNo + ", marks=" + marks + "]";
    }
}
```

---

## 2. Repository Layer

**Class:** `StudentRepo.java`

The **Repository Layer** is responsible for interacting with the database.

- Communicates directly with the database.
- Contains methods to save and retrieve data.
- Uses `@Repository` annotation.
- In this project, `save()` prints a message and `findAll()` returns an empty list. Later, these methods will contain SQL queries.

### Code Example

```java
@Repository
public class StudentRepo {

    public void save(Student s) {
        System.out.println("Saved");
    }

    public List<Student> findAll() {
        List<Student> student = new ArrayList<>();
        return student;
    }
}
```

---

## 3. Service Layer

**Class:** `StudentService.java`

The **Service Layer** contains the business logic of the application.

- Acts as a bridge between the Application Layer and the Repository Layer.
- Uses `@Service` annotation.
- Uses Dependency Injection with `@Autowired`.
- Calls Repository methods to save and retrieve student data.

### Code Example

```java
@Service
public class StudentService {

    private StudentRepo repo;

    @Autowired
    public void setRepo(StudentRepo repo) {
        this.repo = repo;
    }

    public void addStudent(Student s) {
        repo.save(s);
    }

    public List<Student> getStudents() {
        return repo.findAll();
    }
}
```

---

## 4. Application Layer

**Class:** `SpringBootJdbcApplication.java`

The **Application Layer** is the entry point of the Spring Boot application.

- Starts the Spring Boot application.
- Creates the Spring Container.
- Gets Spring Beans using `ApplicationContext`.
- Creates a Student object.
- Calls the Service Layer to add and retrieve student data.

### Code Example

```java
@SpringBootApplication
public class SpringBootJdbcApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(SpringBootJdbcApplication.class, args);

        Student s = context.getBean(Student.class);

        s.setName("Suruchi");
        s.setRollNo(61);
        s.setMarks(78);

        StudentService service = context.getBean(StudentService.class);

        service.addStudent(s);

        List<Student> students = service.getStudents();

        System.out.println(students);
    }
}
```

---

## Layer Flow

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

**Flow Explanation:**

1. The **Application Layer** creates the `Student` object and calls the Service Layer.
2. The **Service Layer** contains the business logic and forwards the request to the Repository Layer.
3. The **Repository Layer** communicates with the database to save or retrieve data.
4. The retrieved data is returned back through the Service Layer to the Application Layer.



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
# Spring JDBC - JdbcTemplate, schema.sql & data.sql

Spring JDBC provides the **JdbcTemplate** class, which makes database operations much easier.

Instead of writing many lines of JDBC code, we can use **JdbcTemplate** methods to perform database operations with very little code.

---

# What is JdbcTemplate?

`JdbcTemplate` is the core class of Spring JDBC.

It simplifies database programming by handling most of the repetitive JDBC work automatically.

It internally manages:

- Creating database connections
- Executing SQL queries
- Handling exceptions
- Closing database resources
- Returning query results

Because of this, developers mainly focus on writing SQL queries instead of boilerplate JDBC code.

---

# Traditional JDBC vs Spring JDBC

## Traditional JDBC

For every database operation, we need to

- Create Connection
- Create Statement or PreparedStatement
- Execute SQL
- Handle Exceptions
- Close Connection

Example

```java
PreparedStatement ps = con.prepareStatement(sql);
ps.executeUpdate();
```

---

## Spring JDBC

Spring provides the **JdbcTemplate** class.

Instead of writing all the JDBC code manually, we simply call its methods.

Example

```java
jdbc.update(sql);
```

Spring automatically manages the remaining work.

---

# JdbcTemplate Methods

The most commonly used methods are

| Method | Purpose |
|---------|----------|
| update() | Insert, Update, Delete |
| query() | Retrieve multiple records |
| queryForObject() | Retrieve a single record |

---

# update() Method

The `update()` method is used for

- INSERT
- UPDATE
- DELETE

It works like `executeUpdate()` in normal JDBC.

### Normal JDBC

```java
PreparedStatement ps = con.prepareStatement(sql);
ps.executeUpdate();
```

### Spring JDBC

```java
jdbc.update(sql);
```

---

# JdbcTemplate in Our Project

In the Repository layer, we use `JdbcTemplate` to insert student data into the database.

### Injecting JdbcTemplate

```java
private JdbcTemplate jdbc;

@Autowired
public void setJdbc(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
}
```

### Explanation

- `JdbcTemplate` is a Spring Bean.
- Spring automatically creates its object.
- `@Autowired` injects the object into the Repository class.
- Now we can use `jdbc.update()` to execute SQL queries.

---

# Saving Student Using JdbcTemplate

### Code Example

```java
public void save(Student s) {

    String sql = "insert into student (name, rollNo, marks) values (?, ?, ?)";

    int rows = jdbc.update(
            sql,
            s.getName(),
            s.getRollNo(),
            s.getMarks()
    );

    System.out.println(rows + " effected");
}
```

### Explanation

First, we write the SQL query.

```java
String sql = "insert into student (name, rollNo, marks) values (?, ?, ?)";
```

The `?` symbols are placeholders.

Their values are passed using

```java
s.getName(),
s.getRollNo(),
s.getMarks()
```

`jdbc.update()` executes the query.

It returns the number of affected rows.

If one student is inserted,

```java
1 effected
```

is printed.

---

# Flow of save() Method

```
Application Layer
        │
        ▼
StudentService.addStudent()
        │
        ▼
StudentRepo.save()
        │
        ▼
JdbcTemplate.update()
        │
        ▼
Database
```

---

# Why do we use '?' in SQL?

Instead of writing

```sql
insert into student values('Suruchi',61,78)
```

we write

```sql
insert into student values(?,?,?)
```

Then pass the values separately.

Benefits

- Prevents SQL Injection
- Makes code reusable
- Easy to read
- Easy to maintain

---

# What is schema.sql?

`schema.sql` is used to create the database structure.

It contains SQL statements like

- CREATE TABLE
- ALTER TABLE
- DROP TABLE

Spring Boot automatically executes this file when the application starts.

---

# schema.sql Location

```
src
 └── main
      └── resources
            └── schema.sql
```

---

# schema.sql Example

```sql
create table Student
(
    name varchar(20),
    rollNo int primary key,
    marks int
);
```

### Explanation

This creates a table named **Student** with

- name
- rollNo
- marks

The `rollNo` column is the **Primary Key**, so every student must have a unique roll number.

---

# What is data.sql?

`data.sql` is used to insert initial data into the database.

After Spring executes `schema.sql`, it automatically executes `data.sql`.

---

# data.sql Location

```
src
 └── main
      └── resources
            └── data.sql
```

---

# data.sql Example

```sql
insert into Student(name, rollNo, marks)
values ('Rahul',58,89);

insert into Student(name, rollNo, marks)
values ('Shubham',59,95);

insert into Student(name, rollNo, marks)
values ('Sumran',60,87);
```

### Explanation

When the application starts,

Spring automatically inserts these three student records into the Student table.

No Java code is required.

---

# Execution Order

Spring Boot automatically executes SQL files in this order.

```
Application Starts
        │
        ▼
schema.sql
(Create Table)
        │
        ▼
data.sql
(Insert Data)
        │
        ▼
Spring Boot Application Starts
```

---

# Why do we need schema.sql?

Without `schema.sql`, the table does not exist.

When we execute

```java
jdbc.update(sql);
```

Spring tries to insert data into the Student table.

If the table is missing,

we get an error like

```
Table "STUDENT" not found
```

So `schema.sql` creates the table before any insert operation.

---

# Why do we need data.sql?

Instead of inserting records manually every time,

Spring automatically inserts default data whenever the application starts.

This is useful for

- Learning
- Testing
- Development

---

# Custom SQL File Names

By default, Spring Boot searches for

- schema.sql
- data.sql

If we use different names, we must configure them in

`application.properties`

Example

```properties
spring.sql.init.schema-locations=classpath:my_custom_schema.sql

spring.sql.init.data-locations=classpath:my_custom_data.sql
```

---

# Project Structure

```
Spring-Boot-JDBC

src
│
├── main
│     ├── java
│     └── resources
│            ├── application.properties
│            ├── schema.sql
│            └── data.sql
│
└── test
```

---

# Complete Flow

```
Application Starts
        │
        ▼
Spring Boot looks for schema.sql
        │
        ▼
Creates Student Table
        │
        ▼
Spring Boot looks for data.sql
        │
        ▼
Inserts Initial Student Records
        │
        ▼
Application Runs
        │
        ▼
Application Layer
        │
        ▼
StudentService
        │
        ▼
StudentRepo
        │
        ▼
JdbcTemplate.update()
        │
        ▼
Student Added Successfully
```

---

# Interview Questions

### What is JdbcTemplate?

JdbcTemplate is the core class of Spring JDBC that simplifies database operations by handling connection management, SQL execution, exception handling, and resource cleanup automatically.

---

### Which JdbcTemplate method is used for INSERT?

`update()`

---

### Which JdbcTemplate method is used for SELECT multiple rows?

`query()`

---

### Which JdbcTemplate method is used for SELECT one row?

`queryForObject()`

---

### What is schema.sql?

It contains SQL statements to create the database structure, such as tables.

---

### What is data.sql?

It contains SQL statements to insert initial data into the database.

---

### Where should schema.sql and data.sql be placed?

```
src/main/resources
```

---

### Which file executes first?

```
schema.sql
```

Then

```
data.sql
```

---

### Why do we use '?' in SQL queries?

They are placeholders for values.

They make the query reusable and help prevent SQL Injection.

---

# Key Points to Remember

- JdbcTemplate is the core class of Spring JDBC.
- `update()` is used for INSERT, UPDATE, and DELETE operations.
- `query()` retrieves multiple records.
- `queryForObject()` retrieves a single record.
- `schema.sql` creates database tables.
- `data.sql` inserts initial records.
- Both files should be placed inside `src/main/resources`.
- Spring Boot automatically executes `schema.sql` first and then `data.sql`.
- `JdbcTemplate` is injected using `@Autowired`.
- `jdbc.update()` returns the number of rows affected.
```
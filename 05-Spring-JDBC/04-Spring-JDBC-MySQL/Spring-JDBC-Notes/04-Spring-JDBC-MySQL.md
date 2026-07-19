# 07 - Spring Boot JDBC with MySQL

MySQL is one of the most popular open-source Relational Database Management Systems (RDBMS).

- It stores data in tables and uses **SQL (Structured Query Language)** to perform database operations.
- Unlike the H2 database, MySQL stores data permanently (persistent storage).
- Spring Boot can easily connect to MySQL using **Spring JDBC** and **JdbcTemplate**.
- Download MySQL: https://dev.mysql.com/downloads/mysql/

---

# Steps to Use MySQL with Spring Boot

## 1. Add MySQL Dependency

To connect a Spring Boot application with MySQL, add the following dependency to the `pom.xml` file.

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

### Explanation

- **groupId** : `com.mysql`
  - Represents the organization that provides the dependency.

- **artifactId** : `mysql-connector-j`
  - The MySQL JDBC Driver used by Java applications to communicate with the MySQL database.

- No version is specified because **Spring Boot automatically manages the compatible version**.

> **Note:** If you were previously using the H2 database, comment out or remove the H2 dependency before adding MySQL.

---

# 2. Configure MySQL Connection

Create or modify the `application.properties` file inside

```
src/main/resources/application.properties
```

Add the following configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Suruchi
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

## Understanding Each Property

### 1. spring.datasource.url

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Suruchi
```

This property specifies the JDBC URL used to connect to the MySQL database.

### Breakdown

| Part | Meaning |
|------|---------|
| jdbc | Java Database Connectivity |
| mysql | Database type |
| localhost | MySQL server is running on the local machine |
| 3306 | Default MySQL port number |
| Suruchi | Database name |

If the database is installed on another machine, replace **localhost** with the server IP address.

Example

```properties
spring.datasource.url=jdbc:mysql://192.168.1.100:3306/Suruchi
```

---

### 2. spring.datasource.username

```properties
spring.datasource.username=root
```

Specifies the username used to log in to MySQL.

Here,

- **root** is the default administrator account created during MySQL installation.

---

### 3. spring.datasource.password

```properties
spring.datasource.password=root
```

Specifies the password of the MySQL user.

In this project, the password is

```
root
```

Use your own password if it is different.

---

### 4. spring.datasource.driver-class-name

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Specifies the JDBC Driver class used to communicate with MySQL.

Spring Boot loads this driver automatically and uses it to establish the database connection.

---

# 3. Create Database

Open MySQL Workbench or MySQL Command Line.

Execute:

```sql
CREATE DATABASE Suruchi;
```

Select the database.

```sql
USE Suruchi;
```

---

# 4. Create Student Table

```sql
CREATE TABLE Student(
    name VARCHAR(20),
    rollNo INT PRIMARY KEY,
    marks INT
);
```

### Explanation

- **name** → Stores the student's name.
- **rollNo** → Stores the student's roll number and acts as the Primary Key.
- **marks** → Stores the student's marks.

---

# 5. Insert Sample Data

```sql
INSERT INTO Student(name, rollNo, marks)
VALUES ('Rahul',88,65);

INSERT INTO Student(name, rollNo, marks)
VALUES ('Rahulwa',32,90);

INSERT INTO Student(name, rollNo, marks)
VALUES ('Halwa',10,75);
```

Verify the inserted records.

```sql
SELECT * FROM Student;
```

---

# 6. How Spring Boot Connects to MySQL

When the application starts, Spring Boot performs the following steps automatically:

1. Reads the database configuration from `application.properties`.
2. Creates a **DataSource** using the provided URL, username, password, and JDBC driver.
3. Creates a **JdbcTemplate** bean.
4. `JdbcTemplate` uses the DataSource to execute SQL queries.
5. The Repository layer communicates with the MySQL database using JdbcTemplate methods.

---

# 7. JdbcTemplate Methods Used

## update()

Equivalent to JDBC's `executeUpdate()`.

Used for

- INSERT
- UPDATE
- DELETE

Example

```java
String sql = "insert into student(name, rollNo, marks) values(?,?,?)";

int rows = jdbc.update(sql,
        s.getName(),
        s.getRollNo(),
        s.getMarks());

System.out.println(rows + " affected");
```

### Explanation

- `?` are placeholders.
- Spring automatically replaces each placeholder with the corresponding value.
- `update()` returns the number of rows affected.

Output

```
1 affected
```

meaning one row was successfully inserted.

---

## query()

Equivalent to JDBC's `executeQuery()`.

Used for

- SELECT

Example

```java
String sql = "select * from Student";

return jdbc.query(sql, (rs, rowNum) -> {

    Student st = new Student();

    st.setName(rs.getString("name"));
    st.setRollNo(rs.getInt("rollNo"));
    st.setMarks(rs.getInt("marks"));

    return st;

});
```

### Explanation

- Executes the SELECT query.
- Reads one row at a time from the `ResultSet`.
- Converts every row into a `Student` object.
- Returns a `List<Student>`.

---

# 8. Output

```
1 affected

[
Student [name=Halwa, rollNo=10, marks=75],
Student [name=Rahulwa, rollNo=32, marks=90],
Student [name=Suruchi, rollNo=61, marks=78],
Student [name=Rahul, rollNo=88, marks=65]
]
```

### Explanation

```
1 affected
```

One record was inserted successfully into the Student table.

```
Student [name=Halwa, rollNo=10, marks=75]
```

The first row from the database was converted into a `Student` object.

Similarly,

```
Student [name=Rahulwa, rollNo=32, marks=90]

Student [name=Suruchi, rollNo=61, marks=78]

Student [name=Rahul, rollNo=88, marks=65]
```

represent the remaining rows fetched from the database.

The output is displayed because `jdbc.query()` returns a `List<Student>` and the list is printed.

---

# Key Points

- MySQL is a persistent database.
- Default MySQL port is **3306**.
- `mysql-connector-j` provides the MySQL JDBC Driver.
- Spring Boot automatically creates the `DataSource` and `JdbcTemplate`.
- `update()` is used for **INSERT**, **UPDATE**, and **DELETE** operations.
- `query()` is used for **SELECT** operations.
- A lambda expression is used as a `RowMapper` to convert each database row into a Java object.
- `application.properties` contains all the database connection details required by Spring Boot.
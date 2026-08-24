# JPA vs MongoDB — Notebook Notes

## 1. Why JPA is Not Used with MongoDB

### Definition

**JPA is primarily designed for working with relational databases**, where data is stored in tables with a predefined schema.

**MongoDB, on the other hand, is a NoSQL database** that uses a different data model, typically based on collections of documents, which are schema-less or have flexible schemas.

This fundamental difference in **data models and storage structures** is why JPA is not used with MongoDB.

### Why is JPA needed?

JPA (**Java Persistence API**) provides a standard way to map Java objects to **relational database tables**.

```text
Java Object
    ↓
JPA / Hibernate
    ↓
Database Table
```

JPA works with concepts such as:

- Tables
- Rows
- Columns
- Primary keys
- Relationships
- SQL

MongoDB does not follow this relational table-based model.

```text
MongoDB:

Collection
   ↓
Document
   ↓
JSON/BSON-like data
```

Therefore, JPA is not the appropriate persistence technology for MongoDB.

---

## 2. What is Used with MongoDB?

In the case of MongoDB, we don't have a traditional **JPA persistence provider**.

MongoDB is a NoSQL database, and **Spring Data MongoDB** serves a similar persistence/data-access role for MongoDB.

It provides the necessary abstractions and implementations to work with MongoDB in Spring applications.

### Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### Simple Flow

```text
Relational DB:
Java Object → JPA → Hibernate → MySQL

MongoDB:
Java Object → Spring Data MongoDB → MongoDB
```

> **point:** Hibernate is a common JPA implementation for relational databases. Spring Data MongoDB is not JPA; it is the Spring Data module designed specifically for MongoDB.

---

## 3. Spring Data JPA vs Spring Data MongoDB

### Definition

**Spring Data JPA** is a part of the Spring Framework that simplifies data access in Java applications using JPA with relational databases.

**Spring Data MongoDB** provides similar functionality for MongoDB.

### Difference

| Spring Data JPA | Spring Data MongoDB |
|---|---|
| Relational databases | MongoDB |
| Tables | Collections |
| Rows | Documents |
| Columns | Fields |
| SQL/JPA queries | MongoDB queries |
| Uses JPA | Does not use JPA |
| Common provider: Hibernate | MongoDB-specific implementation |

---

## 4. Query Method DSL

### Definition

**Query Method DSL is a simple and convenient way to create queries based on method naming conventions.**

Spring Data can derive the query from the method name.

### Simple Example

```java
List<User> findByName(String name);
```

The method name tells Spring Data to find users where:

```text
name = given value
```

Another example:

```java
List<User> findByAgeGreaterThan(int age);
```

Means:

```text
Find users whose age is greater than the given age.
```

### Important Point

You don't always need to write the query manually.

```text
Method Name
     ↓
Spring Data
     ↓
Query Generated
```

Query Method DSL can be used with different Spring Data modules, including **Spring Data JPA and Spring Data MongoDB**, although the underlying query generated is database-specific.

---

## 5. Criteria API

### Definition

The **Criteria API** offers a more dynamic and programmatic approach for building complex and custom queries.

It is useful when query conditions are decided dynamically at runtime.

### Simple Example

Suppose we want:

```text
Find users
WHERE age > 20
AND city = "Bangalore"
```

Instead of creating many different repository methods, criteria-based querying can build the conditions programmatically.

### Why use it?

Useful when:

- Query conditions are dynamic
- Multiple optional filters exist
- Query becomes complex
- We need programmatic control over query construction

---

## 6. Query Method DSL vs Criteria API

| Query Method DSL | Criteria API |
|---|---|
| Simple | More complex |
| Based on method names | Programmatic |
| Good for common queries | Good for dynamic queries |
| Easy to read | More flexible |
| Can become lengthy for complex conditions | Better for complex/dynamic conditions |

### Easy way to remember

```text
Simple query → Query Method DSL

Dynamic/complex query → Criteria API
```

---

## 7. Important Points

- JPA is designed for **relational databases**.
- JPA works with the **relational/table-based model**.
- MongoDB is a **NoSQL document-oriented database**.
- MongoDB stores data in **collections and documents**.
- JPA is **not used with MongoDB**.
- Spring Data MongoDB is used to simplify MongoDB data access in Spring applications.
- Spring Data MongoDB is **not a JPA implementation**.
- Hibernate is a popular **JPA implementation** for relational databases.
- Spring Data JPA and Spring Data MongoDB provide similar repository-style abstractions but target different database models.
- Query Method DSL uses **method naming conventions**.
- Criteria API provides a more **dynamic/programmatic** way to build queries.
- Query Method DSL is generally preferred for simple queries.
- Criteria-style querying is useful for complex and dynamic queries.

---

## 8. Common Mistakes

### ❌ Mistake 1: Saying MongoDB uses JPA

Wrong:

```text
MongoDB → JPA → Hibernate
```

Correct:

```text
MongoDB → Spring Data MongoDB
```

---

### ❌ Mistake 2: Saying Spring Data MongoDB is JPA

Spring Data MongoDB and Spring Data JPA are **different Spring Data modules**.

```text
Spring Data JPA → Relational DB + JPA

Spring Data MongoDB → MongoDB
```

---

### ❌ Mistake 3: Confusing JPA with Hibernate

**JPA = Specification**

**Hibernate = Implementation of JPA**

So:

```text
JPA → defines standard
Hibernate → implements that standard
```

---

### ❌ Mistake 4: Thinking all Spring Data repositories use SQL

Spring Data provides a common programming style, but the actual database technology determines how queries are executed.

---

## 9. Important Questions & Short Answers

### Q1. Why is JPA not used with MongoDB?

**Answer:** JPA is designed for relational databases and works with concepts such as tables, rows, columns, and relationships. MongoDB uses a document-oriented NoSQL model, so JPA is not suitable for MongoDB.

### Q2. What is used instead of JPA with MongoDB in Spring?

**Answer:** **Spring Data MongoDB** is used to simplify data access and provide repository/query abstractions for MongoDB.

### Q3. Is Spring Data MongoDB a JPA implementation?

**Answer:** No. Spring Data MongoDB is a separate Spring Data module specifically designed for MongoDB.

### Q4. What is JPA?

**Answer:** JPA is a Java specification that provides a standard way to map Java objects to relational database tables and perform persistence operations.

### Q5. What is Hibernate?

**Answer:** Hibernate is a popular implementation of the JPA specification and acts as the ORM framework for relational databases.

### Q6. What is Spring Data JPA?

**Answer:** Spring Data JPA is a Spring module that simplifies database access using JPA and provides features such as repositories and query derivation.

### Q7. What is Spring Data MongoDB?

**Answer:** Spring Data MongoDB is a Spring module that provides abstractions and implementations for working with MongoDB.

### Q8. What is Query Method DSL?

**Answer:** It is a way of creating queries using method naming conventions.

Example:

```java
findByName(String name)
```

### Q9. What is Criteria API?

**Answer:** Criteria API provides a programmatic and dynamic way to construct complex queries.

### Q10. Query Method DSL vs Criteria API?

**Answer:** Query Method DSL is simpler and based on method names, while Criteria API is more flexible and useful for dynamic and complex queries.

### Q11. What is the difference between JPA and Spring Data MongoDB?

**Answer:** JPA is a specification for persistence with relational databases, while Spring Data MongoDB is a Spring Data module designed for MongoDB's document-oriented model.

---

## Quick Revision

```text
JPA
↓
Relational DB
↓
Tables / Rows / Columns
↓
Hibernate can implement JPA


MongoDB
↓
NoSQL
↓
Collections / Documents / Fields
↓
Spring Data MongoDB


Query Method DSL
↓
Simple queries
↓
Method naming convention


Criteria API
↓
Dynamic / complex queries
↓
Programmatic query construction
```

### One-Line Answer

> **JPA is primarily designed for relational databases, whereas MongoDB is a NoSQL document-oriented database, so Spring Data MongoDB is used instead of JPA to provide database-access abstractions suitable for MongoDB.**

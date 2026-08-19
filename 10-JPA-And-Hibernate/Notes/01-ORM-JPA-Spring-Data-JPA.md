# ORM, JPA & Spring Data JPA

---

# 1. ORM

## Definition

**ORM — Object Relational Mapping**

ORM is a technique used to **map Java objects to database tables**.

It allows developers to work with databases using **object-oriented programming concepts**, making it easier to interact with relational databases.

Consider a Java class `User` and a database table `users`.

An ORM framework like **Hibernate** can map the fields in the `User` class to columns in the `users` table, making it easier to **insert, update, retrieve, and delete records**.

### Simple Example

Java class:

```java
class User {
    private Long id;
    private String name;
    private String email;
}
```

Database table:

```text
users
--------------------------------
id | name | email
--------------------------------
1  | Suruchi | suruchi@gmail.com
```

ORM maps:

```text
Java Object          Database Table
--------------------------------------
User                 users
id                   id
name                 name
email                email
```

## Why is ORM needed?

Without ORM, developers often need to write SQL and manually convert database records into Java objects.

ORM handles much of this mapping automatically.

```text
Java Object
     ↓
    ORM
     ↓
   SQL
     ↓
 Database
```

## Important Points

* ORM stands for **Object Relational Mapping**.
* It is a **technique/concept**, not a specific tool.
* It maps Java objects to relational database tables.
* It reduces boilerplate database code.
* Hibernate is a popular ORM framework.

---

# 2. JPA

## Definition

**JPA — Java Persistence API**

**Persistence:** Permanently stores data.

**API:** A set of rules/interfaces that developers can use.

JPA is a way to achieve ORM. It includes **interfaces and annotations** that we use in our Java classes and requires a **persistence provider (ORM tool)** for implementation.

### Simple Example

```java
@Entity
public class User {

    @Id
    private Long id;

    private String name;

    private String email;
}
```

Here:

* `@Entity` → tells JPA that `User` is a persistent entity.
* `@Id` → identifies the primary key.
* Fields such as `name` and `email` can be mapped to database columns.

## Why is JPA needed?

JPA provides a **standard way** to perform persistence and ORM in Java.

Instead of every ORM framework having completely different APIs, JPA defines common rules and interfaces.

```text
JPA
 ↓
Defines standard rules/interfaces
 ↓
JPA Provider implements them
 ↓
Database interaction
```

## Important Point

**JPA is a specification, not an implementation.**

It defines **what should be done**, while the JPA provider defines **how it is actually done**.

---

# 3. Persistence Provider / ORM Tools

## Definition

To use JPA, you need a **persistence provider**.

A persistence provider is a **specific implementation of the JPA specification**.

Examples of JPA persistence providers include:

* Hibernate
* EclipseLink
* OpenJPA

These providers implement the JPA interfaces and provide the underlying functionality to interact with databases.

### Simple Understanding

```text
JPA
 ↓
Specification
 ↓
Hibernate
 ↓
Implementation
 ↓
Database
```

For example:

```text
@Entity
   ↓
JPA defines what @Entity means
   ↓
Hibernate implements that behavior
   ↓
Hibernate performs ORM/database operations
```

---

# 4. Spring Data JPA

## Definition

Spring Data JPA is built on top of the **JPA (Java Persistence API) specification**, but it is **not a JPA implementation itself**.

Instead, it simplifies working with JPA by providing **higher-level abstractions and utilities**.

However, to use Spring Data JPA effectively, we still need a **JPA implementation**, such as Hibernate, EclipseLink, or another JPA-compliant provider, to handle the actual database interactions.

## Why is Spring Data JPA needed?

With JPA, we may have to write more code to perform common database operations.

Spring Data JPA simplifies this by providing **repository interfaces** and ready-made methods.

Example:

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

We can then use methods such as:

```java
save()
findById()
findAll()
deleteById()
```

without implementing these common operations ourselves.

## How does Spring Data JPA work?

The basic flow is:

```text
Application
     ↓
Spring Data JPA
     ↓
JPA
     ↓
Hibernate
     ↓
JDBC
     ↓
Database
```

### Important Point

Spring Data JPA **does not replace Hibernate/JPA implementation**.

It makes working with JPA easier.

---

# 5. ORM vs JPA vs Hibernate vs Spring Data JPA

| Term                | Meaning                                                     |
| ------------------- | ----------------------------------------------------------- |
| **ORM**             | Technique for mapping objects to relational database tables |
| **JPA**             | Specification/API for persistence and ORM                   |
| **Hibernate**       | JPA implementation and ORM framework                        |
| **Spring Data JPA** | Abstraction that simplifies working with JPA                |

### Easy Way to Remember

```text
ORM
↓
Concept / Technique

JPA
↓
Specification / Rules

Hibernate
↓
Implementation

Spring Data JPA
↓
Simplifies JPA usage
```

---

# 6. Common Mistakes

### Mistake 1: JPA = Hibernate

❌ JPA and Hibernate are the same.

✅ JPA is a **specification** and Hibernate is an **implementation**.

---

### Mistake 2: Spring Data JPA = Hibernate

❌ Spring Data JPA is an ORM implementation.

✅ Spring Data JPA is a **higher-level abstraction built on JPA**.

---

### Mistake 3: ORM = Hibernate

❌ ORM is Hibernate.

✅ ORM is a **technique**. Hibernate is a framework/tool that provides ORM functionality.

---

### Mistake 4: JPA works completely by itself

❌ JPA directly communicates with the database.

✅ JPA needs a **JPA persistence provider**, such as Hibernate, to perform the actual implementation.

---

# 7. Questions

### Q1. What is ORM?

**Answer:** ORM stands for Object Relational Mapping. It is a technique used to map Java objects to relational database tables.

### Q2. Why do we use ORM?

**Answer:** ORM reduces boilerplate database code and allows developers to work with database data using Java objects instead of manually handling SQL and object mapping.

### Q3. What is JPA?

**Answer:** JPA stands for Java Persistence API. It is a Java specification that provides standard interfaces and annotations for persistence and ORM.

### Q4. Is JPA an implementation?

**Answer:** No. JPA is a specification. A persistence provider such as Hibernate provides the actual implementation.

### Q5. What is a JPA Persistence Provider?

**Answer:** It is an implementation of the JPA specification. Examples include Hibernate, EclipseLink, and OpenJPA.

### Q6. What is Hibernate?

**Answer:** Hibernate is an ORM framework and a popular implementation of the JPA specification.

### Q7. What is Spring Data JPA?

**Answer:** Spring Data JPA is a Spring abstraction built on top of JPA that simplifies database operations by providing repository abstractions and ready-made methods.

### Q8. Is Spring Data JPA a JPA implementation?

**Answer:** No. It simplifies working with JPA but still requires a JPA implementation such as Hibernate.

### Q9. What is the difference between JPA and Hibernate?

**Answer:** JPA is a specification, whereas Hibernate is an implementation of that specification.

### Q10. What is the difference between Hibernate and Spring Data JPA?

**Answer:** Hibernate performs the actual ORM and database interaction, while Spring Data JPA simplifies the use of JPA through repository abstractions.

---

# 8. Quick Revision

```text
ORM
→ Technique to map Java objects to database tables.

JPA
→ Java specification for persistence and ORM.

Persistence Provider
→ Implementation of JPA.

Hibernate
→ Popular JPA implementation and ORM framework.

Spring Data JPA
→ Simplifies working with JPA using repositories and higher-level abstractions.
```

### Overall Flow

```text
Java Application
       ↓
Spring Data JPA
       ↓
      JPA
       ↓
   Hibernate
       ↓
      JDBC
       ↓
   Database
```

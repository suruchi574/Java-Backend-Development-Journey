# Spring JDBC - RowMapper (With and Without Lambda Expression)

## What is RowMapper?

`RowMapper` is an interface provided by Spring JDBC.

It is used to **convert each row of a ResultSet into a Java object**.

When we execute a **SELECT** query, the database returns the data as a **ResultSet**.

Since Java cannot directly convert a `ResultSet` into objects, Spring provides the `RowMapper` interface to perform this conversion.

> **One database row = One Java object**

---

# Why do we need RowMapper?

Suppose we execute the following query.

```sql
SELECT * FROM Student;
```

The database returns

```text
----------------------------------
name      rollNo      marks
----------------------------------
Rahul      58          89
Shubham    59          95
Sumran     60          87
Suruchi    61          78
```

This data is returned as a **ResultSet**, not as `Student` objects.

To convert every row into a `Student` object, we use **RowMapper**.

---

# What is ResultSet?

`ResultSet` is an object that stores the data returned by a **SELECT** query.

It contains all the rows returned by the database.

We use methods like

```java
rs.getString("name");
rs.getInt("rollNo");
rs.getInt("marks");
```

to read values from the current row.

---

# How RowMapper Works

```text
SELECT Query
      │
      ▼
Database
      │
      ▼
ResultSet
      │
      ▼
RowMapper
      │
      ▼
Student Object
      │
      ▼
List<Student>
```

---

# mapRow() Method

The `RowMapper` interface contains one abstract method.

```java
public Student mapRow(ResultSet rs, int rowNum)
        throws SQLException
```

### Parameters

### ResultSet rs

- Contains the current row returned by the database.
- Used to read column values.

Example

```java
rs.getString("name");
rs.getInt("rollNo");
rs.getInt("marks");
```

### int rowNum

- Represents the current row number.
- Spring automatically increases it for every row.
- Usually, we don't use it.

---

# Steps Performed by mapRow()

For every row returned by the database,

1. Create a Java object.
2. Read column values from the `ResultSet`.
3. Store the values in the object.
4. Return the object.

Spring repeats this process for every row and stores all objects inside a `List`.

---

# RowMapper Example (Without Lambda Expression)

Without using a Lambda Expression, we implement `RowMapper` using an anonymous class.

Example from our project:

```java
String sql = "select * from Student";

RowMapper<Student> mapper = new RowMapper<>() {

    @Override
    public Student mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        Student st = new Student();

        st.setName(rs.getString("name"));
        st.setRollNo(rs.getInt("rollNo"));
        st.setMarks(rs.getInt("marks"));

        return st;
    }
};

List<Student> students = jdbc.query(sql, mapper);

return students;
```

### Explanation

```java
String sql = "select * from Student";
```

Creates the SQL query.

---

```java
RowMapper<Student> mapper = new RowMapper<>()
```

Creates a `RowMapper` object.

---

```java
public Student mapRow(ResultSet rs, int rowNum)
```

Spring automatically calls this method once for every row returned by the query.

---

```java
Student st = new Student();
```

Creates a new Student object.

---

```java
st.setName(rs.getString("name"));
st.setRollNo(rs.getInt("rollNo"));
st.setMarks(rs.getInt("marks"));
```

Reads values from the current row and stores them in the Student object.

---

```java
return st;
```

Returns the Student object.

Spring stores each object inside a `List<Student>`.

---

```java
List<Student> students = jdbc.query(sql, mapper);
```

`jdbc.query()`

- Executes the SELECT query.
- Gets the ResultSet.
- Calls `mapRow()` for every row.
- Creates Student objects.
- Returns a `List<Student>`.

---

# Lambda Expression

## What is a Lambda Expression?

A **Lambda Expression** is a shorter way of writing the implementation of a **Functional Interface**.

Instead of creating an anonymous class and overriding its method, we write only the implementation.

It makes the code **shorter, cleaner, and easier to read**.

---

## Why do we use Lambda Expression?

Without Lambda Expression, we need to

- Create an anonymous class.
- Override the abstract method.
- Write extra boilerplate code.

With Lambda Expression, we simply write the method logic.

This reduces the amount of code.

---

## When can we use Lambda Expression?

A Lambda Expression can be used **only with Functional Interfaces**.

A **Functional Interface** is an interface that contains **only one abstract method**.

Examples:

- RowMapper
- Runnable
- Comparator
- Predicate
- Consumer

Since `RowMapper` contains only one abstract method (`mapRow()`), it is a Functional Interface.

Therefore, we can use a Lambda Expression with it.

---

# RowMapper Example (Using Lambda Expression)

Instead of using an anonymous class,

we can write

```java
String sql = "select * from Student";

RowMapper<Student> mapper = (rs, rowNum) -> {

    Student st = new Student();

    st.setName(rs.getString("name"));
    st.setRollNo(rs.getInt("rollNo"));
    st.setMarks(rs.getInt("marks"));

    return st;
};

List<Student> students = jdbc.query(sql, mapper);

return students;
```

### What changed?

We removed

- `new RowMapper<>()`
- `@Override`
- `mapRow()` method declaration

and directly wrote

```java
(rs, rowNum) -> {
    // implementation
}
```

The functionality remains exactly the same.

---

# Direct Lambda Expression (Used in Our Project)

Instead of creating a separate `RowMapper` object,

we can directly pass the Lambda Expression to `jdbc.query()`.

```java
public List<Student> findAll() {

    String sql = "select * from Student";

    return jdbc.query(sql, (rs, rowNum) -> {

        Student st = new Student();

        st.setName(rs.getString("name"));
        st.setRollNo(rs.getInt("rollNo"));
        st.setMarks(rs.getInt("marks"));

        return st;

    });
}
```

This is the approach most commonly used in Spring Boot projects.

---

# Flow of jdbc.query()

```text
jdbc.query(sql, RowMapper)
          │
          ▼
Execute SELECT Query
          │
          ▼
Receive ResultSet
          │
          ▼
Call mapRow() for every row
          │
          ▼
Create Student Object
          │
          ▼
Store objects in List<Student>
          │
          ▼
Return List<Student>
```

---

# Output

```text
1 effected

[
Student [name=Rahul, rollNo=58, marks=89],
Student [name=Shubham, rollNo=59, marks=95],
Student [name=Sumran, rollNo=60, marks=87],
Student [name=Suruchi, rollNo=61, marks=78]
]
```

### Explanation

- `1 effected` means one student (**Suruchi**) was successfully inserted using `jdbc.update()`.
- `jdbc.query()` executed the SELECT query.
- `RowMapper` converted each database row into a Student object.
- Finally, a `List<Student>` was printed.

---

# Summary

- `RowMapper` converts one database row into one Java object.
- It is mainly used with `jdbc.query()`.
- `ResultSet` stores the data returned by a SELECT query.
- `mapRow()` is automatically called for every row in the `ResultSet`.
- `jdbc.query()` executes the query and returns a `List` of objects.
- `RowMapper` is a Functional Interface because it has only one abstract method.
- Since it is a Functional Interface, we can implement it using a Lambda Expression.
- Lambda Expressions reduce boilerplate code and improve readability.
- Direct Lambda Expressions inside `jdbc.query()` are the most common approach in Spring Boot.
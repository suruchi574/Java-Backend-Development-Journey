## Why is the output `1 effected` and `[]`?

When we run the application, we get the output:

```text
1 effected
[]
```

Both outputs come from different methods.

---

# Why is `1 effected` printed?

In the `save()` method, we use the `update()` method of `JdbcTemplate`.

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

The `update()` method returns the number of rows affected by the SQL query.

Since our SQL statement inserts **one student record**, only **one row** is affected.

Therefore,

```java
System.out.println(rows + " effected");
```

prints

```text
1 effected
```

If two records were inserted, it would return

```text
2 effected
```

---

# Why is `[]` printed?

In the Application Layer, we call

```java
List<Student> students = service.getStudents();

System.out.println(students);
```

The `getStudents()` method calls the Repository method.

```java
public List<Student> getStudents() {
    return repo.findAll();
}
```

Now look at the Repository method.

```java
public List<Student> findAll() {

    List<Student> student = new ArrayList<>();

    return student;
}
```

### Explanation

Currently, this method only

- Creates an empty `ArrayList`
- Returns that empty list

It **does not fetch any records from the database**.

Therefore,

```java
System.out.println(students);
```

prints

```text
[]
```

---

# Why doesn't it print the inserted student?

Although the student is successfully inserted into the database using

```java
jdbc.update(...)
```

the `findAll()` method is **not reading data from the database yet**.

It simply returns an empty list.

Later, we will write a SQL query like

```sql
SELECT * FROM student;
```

using `jdbc.query()` to fetch all student records.

After implementing `findAll()`, the output will look like

```text
1 effected

[
Student [name=Rahul, rollNo=58, marks=89],
Student [name=Shubham, rollNo=59, marks=95],
Student [name=Sumran, rollNo=60, marks=87],
Student [name=Suruchi, rollNo=61, marks=78]
]
```

---

# Flow of the Program

```text
Application Starts
        │
        ▼
StudentService.addStudent()
        │
        ▼
StudentRepo.save()
        │
        ▼
jdbc.update()
        │
        ▼
One row inserted
        │
        ▼
Output:
1 effected
        │
        ▼
StudentService.getStudents()
        │
        ▼
StudentRepo.findAll()
        │
        ▼
Returns an empty ArrayList
        │
        ▼
Output:
[]
```

---

# Key Points to Remember

- `jdbc.update()` returns the number of rows affected by the SQL query.
- `1 effected` means one row was successfully inserted into the database.
- `findAll()` currently returns an empty `ArrayList`.
- No data is fetched from the database yet.
- To retrieve records, we need to implement `findAll()` using `jdbc.query()`.
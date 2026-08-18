# MongoDB — Development Notes

## 1. What is MongoDB?

**MongoDB is a NoSQL, document-oriented database** used to store and manage application data.

Unlike relational databases such as MySQL, MongoDB does not primarily store data in tables and rows. Instead, it stores data in the form of **documents**, which are grouped together inside **collections**.

MongoDB stores documents in a JSON-like format called **BSON (Binary JSON)**.

Example MongoDB document:

```javascript
{
    "name": "Suruchi",
    "age": 23,
    "role": "Java Backend Developer"
}
```

Here:

* `name`, `age`, and `role` are **fields**.
* The complete `{ ... }` is a **document**.
* Multiple documents are stored inside a **collection**.

---

## 2. Why MongoDB?

MongoDB is useful when applications need a flexible and scalable way to store data.

Important characteristics:

* **NoSQL database** — does not use traditional relational tables as its primary structure.
* **Document-based** — data is stored as documents.
* **Flexible schema** — documents in the same collection do not necessarily need exactly the same fields.
* **JSON-like structure** — convenient for modern applications and REST APIs.
* **Scalable** — designed to handle large amounts of data and traffic.
* **Object/document friendly** — application data can naturally be represented as documents.

---

## 3. MongoDB vs MySQL

The basic structure can be compared like this:

| MySQL       | MongoDB    |
| ----------- | ---------- |
| Database    | Database   |
| Table       | Collection |
| Row         | Document   |
| Column      | Field      |
| Primary Key | `_id`      |

### MySQL

```text
Database
   ↓
Table
   ↓
Rows + Columns
```

### MongoDB

```text
Database
   ↓
Collection
   ↓
Documents
   ↓
Fields
```

For example, in MySQL:

```text
students table

id | name    | age
---|---------|----
1  | Suruchi | 23
2  | Rahul   | 24
```

Equivalent MongoDB documents:

```javascript
{
    "_id": 1,
    "name": "Suruchi",
    "age": 23
}

{
    "_id": 2,
    "name": "Rahul",
    "age": 24
}
```

> MongoDB is not a direct one-to-one replacement for MySQL. The comparison is mainly useful for understanding the basic terminology.

---

## 4. MongoDB Database Structure

The basic hierarchy is:

```text
MongoDB Server
      ↓
   Database
      ↓
  Collection
      ↓
  Document
      ↓
    Fields
```

Example:

```text
MongoDB
   ↓
school
   ↓
students
   ↓
{
    name: "Suruchi",
    age: 23
}
```

Here:

* `school` → Database
* `students` → Collection
* `{ name: "Suruchi", age: 23 }` → Document
* `name`, `age` → Fields

---

## 5. Document

A **document** is the basic unit of data in MongoDB.

Example:

```javascript
{
    "name": "Suruchi",
    "age": 23
}
```

A document is similar to a **row in MySQL**, but it can contain nested objects and arrays.

Example:

```javascript
{
    "name": "Suruchi",
    "age": 23,
    "skills": ["Java", "Spring Boot", "MongoDB"]
}
```

---

## 6. Collection

A **collection** is a group of MongoDB documents.

It is roughly equivalent to a **table in MySQL**.

Example:

```text
students collection

Document 1
{
    name: "Suruchi",
    age: 23
}

Document 2
{
    name: "Rahul",
    age: 24
}
```

MongoDB collections can contain documents with different fields.

For example, both documents can exist in the same collection:

```javascript
{
    "name": "Suruchi",
    "age": 23
}
```

```javascript
{
    "name": "Rahul",
    "age": 24,
    "city": "Bangalore"
}
```

---

## 7. `_id` Field

Every MongoDB document has a unique `_id` field.

If we do not provide one ourselves, MongoDB automatically generates it.

Example:

```javascript
{
    "_id": ObjectId("6a83a99548cc2d96c6ecb384"),
    "name": "Suruchi",
    "age": 23
}
```

The `_id` uniquely identifies the document within the collection.

MongoDB commonly generates `_id` values using `ObjectId`.

---

## 8. JSON vs BSON

MongoDB documents look like JSON:

```javascript
{
    "name": "Suruchi",
    "age": 23
}
```

Internally, MongoDB stores documents using **BSON**, which stands for **Binary JSON**.

For development, remember:

```text
Application representation → JSON-like document
MongoDB storage format     → BSON
```

BSON also supports additional data types such as:

* `ObjectId`
* `Date`

---

# MongoDB Shell Practice

## 9. Starting MongoDB Shell

MongoDB can be accessed from the command line using **MongoDB Shell (`mongosh`)**.

Start the shell:

```bash
mongosh
```

Example:

```text
C:\Users\barbi>mongosh
```

Environment used during practice:

```text
MongoDB: 8.3.8
Mongosh: 2.10.0
```

MongoDB was running locally on:

```text
mongodb://127.0.0.1:27017
```

---

## 10. Basic MongoDB Flow

The basic workflow is:

```text
Start MongoDB
      ↓
Open mongosh
      ↓
Select database
      ↓
Create/use collection
      ↓
Insert documents
      ↓
Read documents
      ↓
Update documents
      ↓
Delete documents
```

This is the basic **CRUD** flow:

```text
C → Create
R → Read
U → Update
D → Delete
```

---

# Database Commands

## 11. Show Databases

To display the databases currently present:

```javascript
show dbs
```

Example:

```text
admin
config
local
```

---

## 12. Select a Database

Use:

```javascript
use school
```

Output:

```text
switched to db school
```

> `use` switches to the specified database. A new database is persisted when data is stored in it.

---

## 13. Show Current Database

To check which database is currently selected:

```javascript
db
```

Example:

```text
school
```

---

## 14. Show Collections

To display collections inside the current database:

```javascript
show collections
```

Example:

```text
students
```

If a database has just been selected and no data has been inserted, there may be no collections yet.

---

# Create and Insert Data

## 15. Create a Collection and Insert One Document

MongoDB can automatically create a collection when the first document is inserted.

```javascript
db.students.insertOne({
    name: "Suruchi",
    age: 23
})
```

Response:

```javascript
{
    acknowledged: true,
    insertedId: ObjectId('...')
}
```

The `students` collection is created automatically if it does not already exist.

Insert another document:

```javascript
db.students.insertOne({
    name: "Rahul",
    age: 24
})
```

Check the collection:

```javascript
show collections
```

Output:

```text
students
```

---

## 16. Insert Multiple Documents

Use `insertMany()` when multiple documents need to be inserted.

```javascript
db.students.insertMany([
    {
        name: "Amit",
        age: 25
    },
    {
        name: "Priya",
        age: 22
    }
])
```

### Difference

```text
insertOne()  → Inserts one document

insertMany() → Inserts multiple documents
```

---

# Read Data

## 17. Find All Documents

To retrieve documents from a collection:

```javascript
db.students.find()
```

Example:

```javascript
[
    {
        _id: ObjectId('...'),
        name: 'Suruchi',
        age: 23
    },
    {
        _id: ObjectId('...'),
        name: 'Rahul',
        age: 24
    }
]
```

---

## 18. Display Documents in Readable Format

```javascript
db.students.find().pretty()
```

This can be used to display the result in a more readable format.

---

## 19. Find One Document

To retrieve one matching document:

```javascript
db.students.findOne({
    name: "Suruchi"
})
```

### Difference

```text
find()    → Finds matching documents

findOne() → Finds one matching document
```

---

## 20. Find a Particular Document

The search condition is passed as an object:

```javascript
db.students.find({
    name: "Suruchi"
})
```

Correct:

```javascript
db.students.find({
    name: "Suruchi"
})
```

Incorrect:

```javascript
db.students.find(name: "Suruchi")
```

The filter must be written as:

```javascript
{
    field: value
}
```

---

# Update Data

## 21. Update One Document

Use `updateOne()` to modify the first matching document.

```javascript
db.students.updateOne(
    { name: "Suruchi" },
    { $set: { age: 24 } }
)
```

The `$set` operator changes the specified field without replacing the complete document.

Before:

```javascript
{
    name: "Suruchi",
    age: 23
}
```

After:

```javascript
{
    name: "Suruchi",
    age: 24
}
```

---

## 22. Update Multiple Documents

Use `updateMany()` when multiple documents match the condition.

```javascript
db.students.updateMany(
    { age: 24 },
    { $set: { status: "Active" } }
)
```

### Difference

```text
updateOne()  → Updates one matching document

updateMany() → Updates all matching documents
```

---

# Delete Data

## 23. Delete One Document

Use `deleteOne()` to delete one matching document.

```javascript
db.students.deleteOne({
    name: "Suruchi"
})
```

Response:

```javascript
{
    acknowledged: true,
    deletedCount: 1
}
```

---

## 24. Delete Multiple Documents

Use `deleteMany()` when multiple documents match the condition.

```javascript
db.students.deleteMany({
    age: 24
})
```

### Difference

```text
deleteOne()  → Deletes one matching document

deleteMany() → Deletes all matching documents
```

---

# Other Useful Commands

## 25. Count Documents

Count all documents:

```javascript
db.students.countDocuments()
```

Count documents matching a condition:

```javascript
db.students.countDocuments({
    age: 24
})
```

---

## 26. Drop a Collection

To completely remove a collection:

```javascript
db.students.drop()
```

This removes the collection and its documents.

> Use carefully because this is a destructive operation.

---

## 27. Drop a Database

To remove the currently selected database:

```javascript
use school
db.dropDatabase()
```

> Use carefully because this deletes the database and its data.

---

# CRUD Summary

## 28. MongoDB CRUD Commands

| Operation | Command        | Purpose                   |
| --------- | -------------- | ------------------------- |
| Create    | `insertOne()`  | Insert one document       |
| Create    | `insertMany()` | Insert multiple documents |
| Read      | `find()`       | Find documents            |
| Read      | `findOne()`    | Find one document         |
| Update    | `updateOne()`  | Update one document       |
| Update    | `updateMany()` | Update multiple documents |
| Delete    | `deleteOne()`  | Delete one document       |
| Delete    | `deleteMany()` | Delete multiple documents |

### CRUD Flow

```text
Create → insertOne() / insertMany()

Read   → find() / findOne()

Update → updateOne() / updateMany()

Delete → deleteOne() / deleteMany()
```

---

# Today's Hands-On Practice

## 29. Commands Actually Implemented

During today's practice, I worked with the `school` database and `students` collection.

### Select Database

```javascript
use school
```

### Insert Suruchi

```javascript
db.students.insertOne({
    name: "Suruchi",
    age: 23
})
```

### Insert Rahul

```javascript
db.students.insertOne({
    name: "Rahul",
    age: 24
})
```

### Check Collection

```javascript
show collections
```

### Find All Students

```javascript
db.students.find()
```

### Display Data

```javascript
db.students.find().pretty()
```

### Find Suruchi

```javascript
db.students.find({
    name: "Suruchi"
})
```

### Delete Suruchi

```javascript
db.students.deleteOne({
    name: "Suruchi"
})
```

### Verify Remaining Data

```javascript
db.students.find().pretty()
```

After deletion, the `Suruchi` document was removed and the `Rahul` document remained.

---

# Errors Faced and Fixes

## 30. Error — Copying the MongoDB Prompt

I accidentally entered:

```text
local> use school
```

instead of only entering:

```javascript
use school
```

MongoDB interpreted `local>` as part of the JavaScript command and produced:

```text
SyntaxError: Missing semicolon
```

### Fix

Do not copy the shell prompt.

Incorrect:

```text
local> use school
```

Correct:

```javascript
use school
```

The following:

```text
school>
```

is the **MongoDB shell prompt**, not part of the command.

---

## 31. Error — Incorrect `find()` Syntax

Initially:

```javascript
db.students.find(name: "Suruchi")
```

This resulted in a syntax error.

### Fix

The condition must be passed as an object:

```javascript
db.students.find({
    name: "Suruchi"
})
```

Remember:

```text
{ field: value }
```

---

## 32. Error — Incorrect `deleteOne()` Syntax

Initially:

```javascript
db.students.deleteOne{name: "Suruchi"})
```

This resulted in a syntax error.

### Fix

MongoDB methods require parentheses:

```javascript
db.students.deleteOne({
    name: "Suruchi"
})
```

---

# Key Takeaways

## 33. What I Learned Today

* MongoDB is a **NoSQL, document-oriented database**.
* MongoDB stores data in **collections and documents**.
* A MongoDB document contains **fields**.
* MongoDB uses `_id` to uniquely identify documents.
* MongoDB stores documents internally using **BSON**.
* `mongosh` is used to interact with MongoDB from the command line.
* A collection can be created automatically when the first document is inserted.
* `insertOne()` inserts one document.
* `insertMany()` inserts multiple documents.
* `find()` retrieves documents.
* `findOne()` retrieves one document.
* `updateOne()` and `updateMany()` modify documents.
* `deleteOne()` and `deleteMany()` remove documents.
* `$set` is used to update specific fields.
* Query conditions are passed as objects inside `{ }`.
* The MongoDB shell prompt such as `school>` should not be included when entering commands.
* I practiced basic MongoDB **CRUD operations** using a `school` database and `students` collection.

---

# Next MongoDB Topics

The next topics to learn are:

1. Query operators — `$gt`, `$lt`, `$gte`, `$lte`
2. `$in` and `$nin`
3. `$and` and `$or`
4. Projection — selecting specific fields
5. Sorting
6. Limiting results
7. MongoDB data types
8. Indexing
9. Connecting MongoDB with **Spring Boot**
10. **Spring Data MongoDB**

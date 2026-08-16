# Spring Boot REST API — GET by ID, PathVariable, DELETE and PUT

## 1. Overview

In a REST API, different HTTP methods are used for different operations on resources.

For our **JournalApp**, the resource is a `JournalEntry`.

A journal entry contains:

```java
id
title
content
```

Example:

```json
{
    "id": 1,
    "title": "Development",
    "content": "I learned the use of PathVariable today"
}
```

Our controller uses a `Map` to temporarily store journal entries:

```java
private Map<Long, JournalEntry> journalEntries = new HashMap<>();
```

Conceptually:

```text
1L → JournalEntry(id=1, title=Development, ...)
2L → JournalEntry(id=2, title=DSA, ...)
3L → JournalEntry(id=3, title=Apply, ...)
```

The following APIs allow us to:

1. Get all journal entries
2. Get one journal entry by ID
3. Delete a journal entry by ID
4. Update a journal entry by ID

---

# 2. `@RequestMapping("/journal")`

Before understanding the individual APIs, understand the common URL mapping.

```java
@RestController
@RequestMapping("/journal")
public class JournalEntryController {
```

### What does `@RequestMapping("/journal")` do?

It defines the **base URL** for all methods inside the controller.

So if we write:

```java
@GetMapping
```

the complete URL becomes:

```text
GET http://localhost:8080/journal
```

If we write:

```java
@GetMapping("id/{myId}")
```

the complete URL becomes:

```text
GET http://localhost:8080/journal/id/{myId}
```

### Interview explanation

> `@RequestMapping` is used to map a common base URL to a controller class or method. When it is placed at the class level, all endpoint mappings inside that controller inherit the base path.

---

# 3. GET — Get All Journal Entries

## Code

```java
@GetMapping
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

Because the class already has:

```java
@RequestMapping("/journal")
```

the complete endpoint is:

```text
GET /journal
```

For example:

```text
GET http://localhost:8080/journal
```

---

## What happens internally?

Suppose the Map contains:

```text
1L → Development
2L → DSA
3L → Apply
```

This code:

```java
journalEntries.values()
```

returns all the `JournalEntry` objects.

Then:

```java
new ArrayList<>(journalEntries.values())
```

converts those values into a `List`.

Spring Boot converts the Java `List<JournalEntry>` into JSON.

### Example response

```json
[
    {
        "id": 1,
        "title": "Development",
        "content": "I learned the use of PathVariable today"
    },
    {
        "id": 2,
        "title": "DSA",
        "content": "I will solve one string question today"
    },
    {
        "id": 3,
        "title": "Apply",
        "content": "I will apply in 1-2 companies today"
    }
]
```

### Interview explanation

> `@GetMapping` is used to handle HTTP GET requests. In this method, I retrieve all values from the `HashMap`, convert them into an `ArrayList`, and return them. Spring Boot automatically serializes the Java objects into JSON.

---

# 4. Why Do We Need GET by ID?

Getting all journal entries is not always useful.

Suppose we have 100 journal entries but we only want:

```text
Journal Entry with ID = 2
```

Instead of returning all 100 entries, we can request a specific resource.

We can use:

```text
GET /journal/id/2
```

This is where **Path Variable** becomes important.

---

# 5. What is a Path Variable?

A **Path Variable** is a dynamic value that is directly included in the URL path.

Example:

```text
http://localhost:8080/journal/id/2
```

Here:

```text
/journal/id/
```

is the fixed part.

```text
2
```

is the dynamic value.

We can also have:

```text
/journal/id/5
/journal/id/10
/journal/id/25
```

The value changes depending on which journal entry we want.

---

# 6. `@PathVariable`

`@PathVariable` is used to extract a value from the URL path and pass it into a controller method.

Example:

```java
@GetMapping("id/{myId}")
public JournalEntry journalEntryByID(@PathVariable Long myId) {
    return journalEntries.get(myId);
}
```

There are two important parts here.

### Part 1 — `{myId}`

```java
@GetMapping("id/{myId}")
```

`{myId}` represents a **variable part of the URL**.

### Part 2 — `@PathVariable Long myId`

```java
@PathVariable Long myId
```

This tells Spring:

> Take the value from `{myId}` in the URL and put it into the Java variable `myId`.

---

# 7. GET Journal Entry by ID

## Code

```java
@GetMapping("id/{myId}")
public JournalEntry journalEntryByID(@PathVariable Long myId) {
    return journalEntries.get(myId);
}
```

### Request

```text
GET http://localhost:8080/journal/id/2
```

Spring sees:

```text
{id} = 2
```

More precisely, because our mapping uses `{myId}`:

```text
{myId} = 2
```

Therefore:

```java
Long myId = 2L;
```

Then this code executes:

```java
journalEntries.get(myId);
```

which is equivalent to:

```java
journalEntries.get(2L);
```

The Map searches for key `2L`.

---

## Example Map

```text
Key       Value
-------------------------------
1L   →    Development JournalEntry
2L   →    DSA JournalEntry
3L   →    Apply JournalEntry
```

Request:

```text
GET /journal/id/2
```

Result:

```text
2L → DSA JournalEntry
```

### Response

```json
{
    "id": 2,
    "title": "DSA",
    "content": "I will solve one string question today"
}
```

---

# 8. Request Flow for `@PathVariable`

Understand this flow carefully for interviews:

```text
Client / Postman
       |
       | GET /journal/id/2
       ↓
Spring Boot
       |
       | matches @GetMapping("id/{myId}")
       ↓
@PathVariable
       |
       | extracts 2
       ↓
Long myId = 2L
       |
       ↓
journalEntries.get(2L)
       |
       ↓
JournalEntry object
       |
       ↓
Spring converts object to JSON
       |
       ↓
Postman
```

---

# 9. Path Variable vs Request Parameter

A common interview question is:

> What is the difference between `@PathVariable` and `@RequestParam`?

First, note the correct annotation name:

```java
@RequestParam
```

not:

```java
@RequestParameter
```

---

## `@PathVariable`

The value is part of the URL path.

Example:

```text
GET /journal/id/2
```

Code:

```java
@GetMapping("id/{myId}")
public JournalEntry getJournalEntryByID(
        @PathVariable Long myId) {

    return journalEntries.get(myId);
}
```

Here:

```text
2
```

is a path variable.

---

## `@RequestParam`

The value is passed as a query parameter.

Example:

```text
GET /journal?id=2
```

Code:

```java
@GetMapping
public JournalEntry getJournalEntry(
        @RequestParam Long id) {

    return journalEntries.get(id);
}
```

Here:

```text
?id=2
```

is a query parameter.

---

## Difference

| `@PathVariable` | `@RequestParam` |
|---|---|
| Value is part of URL path | Value is a query parameter |
| `/journal/id/2` | `/journal?id=2` |
| Usually identifies a specific resource | Usually used for filtering, searching, sorting, pagination, optional parameters |
| Uses `{}` in mapping | Does not use `{}` in mapping |

### Easy way to remember

```text
PathVariable
/journal/id/2
            ↑
       value is in path
```

```text
RequestParam
/journal?id=2
          ↑
     value is a parameter
```

### Interview explanation

> `@PathVariable` extracts a value directly from the URI path, whereas `@RequestParam` extracts a value from the query string. I would commonly use a path variable when identifying a specific resource, such as `/journal/id/2`, and a request parameter for filtering or searching, such as `/journal?title=DSA`.

---

# 10. `@DeleteMapping`

When we want to delete a resource, we use the HTTP `DELETE` method.

Spring provides:

```java
@DeleteMapping
```

For deleting a journal entry by ID:

```java
@DeleteMapping("id/{myId}")
public JournalEntry deleteJournalEntryByID(
        @PathVariable Long myId) {

    return journalEntries.remove(myId);
}
```

---

# 11. How DELETE Works

Suppose our Map contains:

```text
1L → Development
2L → DSA
3L → Apply
```

We send:

```text
DELETE http://localhost:8080/journal/id/3
```

Spring extracts:

```text
myId = 3
```

Then this code executes:

```java
journalEntries.remove(3L);
```

The entry with key `3L` is removed.

Before:

```text
1L → Development
2L → DSA
3L → Apply
```

After:

```text
1L → Development
2L → DSA
```

---

# 12. What Does `Map.remove()` Return?

This is important for understanding your code.

```java
return journalEntries.remove(myId);
```

`remove()` does two things:

1. Removes the entry from the Map
2. Returns the value that was removed

For example:

```java
JournalEntry deleted = journalEntries.remove(3L);
```

If ID `3` exists:

```text
deleted = JournalEntry with ID 3
```

So your API can return the deleted object.

### Example response

```json
{
    "id": 3,
    "title": "Apply",
    "content": "I will apply in 1-2 companies today"
}
```

---

# 13. DELETE Request Flow

```text
Postman
   |
   | DELETE /journal/id/3
   ↓
@Get/Delete Mapping
   |
   | extracts 3 using @PathVariable
   ↓
myId = 3L
   |
   ↓
journalEntries.remove(3L)
   |
   ├── removes entry
   └── returns removed JournalEntry
   |
   ↓
Postman receives JSON response
```

### Interview explanation

> `@DeleteMapping` is used to handle HTTP DELETE requests. In this example, I use `@PathVariable` to identify the journal entry and `Map.remove()` to remove it from the in-memory Map. `remove()` also returns the object that was removed.

---

# 14. `@PutMapping`

When we want to update an existing resource, we commonly use the HTTP `PUT` method.

Spring provides:

```java
@PutMapping
```

Example:

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable Long id,
        @RequestBody JournalEntry myEntry) {

    journalEntries.put(id, myEntry);
    return myEntry;
}
```

There are two important inputs:

### 1. ID from URL

```java
@PathVariable Long id
```

### 2. New data from request body

```java
@RequestBody JournalEntry myEntry
```

---

# 15. Why Does PUT Need Both `@PathVariable` and `@RequestBody`?

Suppose we want to update journal entry `1`.

We send:

```text
PUT /journal/id/1
```

The URL tells us:

```text
Which entry should be updated?
```

Answer:

```text
ID = 1
```

Then the request body tells us:

```text
What should the new data be?
```

Example body:

```json
{
    "id": 1,
    "title": "Development",
    "content": "I learned the use of PathVariable today"
}
```

So:

```text
@PathVariable
        ↓
Which resource?

@RequestBody
        ↓
What new data?
```

---

# 16. PUT Example with Postman

### Request

```text
PUT http://localhost:8080/journal/id/1
```

### Body

Select:

```text
Body → raw → JSON
```

Send:

```json
{
    "id": 1,
    "title": "Development",
    "content": "I learned the use of PathVariable today"
}
```

---

# 17. What Happens Internally During PUT?

Suppose before update:

```text
1L → {
       id: 1,
       title: "Development",
       content: "I learned PUT mapping"
     }
```

Request:

```text
PUT /journal/id/1
```

Body:

```json
{
    "id": 1,
    "title": "Development",
    "content": "I learned the use of PathVariable today"
}
```

Spring converts the JSON request body into:

```java
JournalEntry myEntry
```

The path variable becomes:

```java
Long id = 1L;
```

Then:

```java
journalEntries.put(id, myEntry);
```

replaces the value associated with key `1L`.

After update:

```text
1L → {
       id: 1,
       title: "Development",
       content: "I learned the use of PathVariable today"
     }
```

---

# 18. Important Correction in the PUT Code

Your pasted code is:

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable Long id,
        @RequestBody JournalEntry myEntry) {

    return journalEntries.put(id, myEntry);
}
```

There is an important Java `Map` concept here.

### `Map.put()` returns the previous value

This:

```java
journalEntries.put(id, myEntry);
```

updates the Map.

But:

```java
return journalEntries.put(id, myEntry);
```

returns the **old value that was previously stored**, not the newly inserted value.

For a clearer response, write:

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable Long id,
        @RequestBody JournalEntry myEntry) {

    journalEntries.put(id, myEntry);
    return myEntry;
}
```

Now the API returns the updated object.

### Interview point

> `Map.put()` inserts or replaces a value for a key and returns the previous value associated with that key.

This is a useful Java interview concept as well.

---

# 19. Important Difference: `put()` vs `remove()`

### `put()`

```java
journalEntries.put(id, myEntry);
```

Meaning:

```text
Add if key does not exist
OR
Replace if key already exists
```

Return value:

```text
Previous value
```

---

### `remove()`

```java
journalEntries.remove(id);
```

Meaning:

```text
Remove the entry associated with the key
```

Return value:

```text
Removed value
```

---

# 20. Complete CRUD Picture

Your current JournalApp controller is implementing basic CRUD operations.

CRUD means:

```text
C → Create
R → Read
U → Update
D → Delete
```

| Operation | HTTP Method | Endpoint | Annotation |
|---|---|---|---|
| Create entry | POST | `/journal` | `@PostMapping` |
| Get all entries | GET | `/journal` | `@GetMapping` |
| Get entry by ID | GET | `/journal/id/{id}` | `@GetMapping` + `@PathVariable` |
| Update entry | PUT | `/journal/id/{id}` | `@PutMapping` + `@PathVariable` + `@RequestBody` |
| Delete entry | DELETE | `/journal/id/{id}` | `@DeleteMapping` + `@PathVariable` |

---

# 21. Complete API Flow

## Create

Request:

```text
POST /journal
```

Body:

```json
{
    "id": 1,
    "title": "Development",
    "content": "Learning Spring Boot"
}
```

Code:

```java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {
    journalEntries.put(myEntry.getId(), myEntry);
    return true;
}
```

Result:

```text
Journal entry is stored in the Map.
```

---

## Read All

Request:

```text
GET /journal
```

Code:

```java
@GetMapping
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

Result:

```text
Returns all journal entries.
```

---

## Read One

Request:

```text
GET /journal/id/2
```

Code:

```java
@GetMapping("id/{myId}")
public JournalEntry journalEntryByID(
        @PathVariable Long myId) {

    return journalEntries.get(myId);
}
```

Result:

```text
Returns journal entry with ID 2.
```

---

## Update

Request:

```text
PUT /journal/id/1
```

Body:

```json
{
    "id": 1,
    "title": "Development",
    "content": "I learned PathVariable today"
}
```

Code:

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable Long id,
        @RequestBody JournalEntry myEntry) {

    journalEntries.put(id, myEntry);
    return myEntry;
}
```

Result:

```text
Entry with ID 1 is replaced with the new data.
```

---

## Delete

Request:

```text
DELETE /journal/id/3
```

Code:

```java
@DeleteMapping("id/{myId}")
public JournalEntry deleteJournalEntryByID(
        @PathVariable Long myId) {

    return journalEntries.remove(myId);
}
```

Result:

```text
Entry with ID 3 is removed.
```

---

# 22. Why Do We Use `@RequestBody` in POST and PUT?

`@RequestBody` tells Spring to take the JSON data from the HTTP request body and convert it into a Java object.

Example JSON:

```json
{
    "id": 3,
    "title": "Apply",
    "content": "I will apply in 1-2 companies today"
}
```

Spring converts it approximately into:

```java
JournalEntry myEntry
```

So we can use:

```java
myEntry.getId();
myEntry.getTitle();
myEntry.getContent();
```

### Interview explanation

> `@RequestBody` binds the HTTP request body to a Java object. In a REST API, Spring typically uses JSON and Jackson to deserialize the JSON request body into the corresponding Java object.

---

# 23. `@PathVariable` + `@RequestBody` Together

This combination is extremely common in update APIs.

```java
@PutMapping("/id/{id}")
public JournalEntry updateJournalById(
        @PathVariable Long id,
        @RequestBody JournalEntry myEntry) {

    journalEntries.put(id, myEntry);
    return myEntry;
}
```

Think of it as:

```text
URL
 ↓
@PathVariable
 ↓
Which resource?
 ↓
ID = 1


Request Body
 ↓
@RequestBody
 ↓
What data?
 ↓
New JournalEntry
```

Together:

```text
PUT /journal/id/1
+
JSON body
        ↓
Update journal entry 1
```

---

# 24. What Happens If the ID Does Not Exist?

With your current Map-based implementation:

### GET

```java
journalEntries.get(id);
```

If the ID does not exist, `get()` returns:

```java
null
```

### DELETE

```java
journalEntries.remove(id);
```

If the ID does not exist, `remove()` returns:

```java
null
```

### PUT

```java
journalEntries.put(id, myEntry);
```

If the ID does not exist, `put()` will create a new entry with that key.

So technically, the current PUT implementation behaves like:

```text
Existing ID → update/replace
New ID      → create
```

In a production REST API, you would usually validate whether the resource exists and return an appropriate HTTP status such as `404 Not Found` when an update/delete/read target does not exist.

---

# 25. Endpoint Mapping — How Spring Decides Which Method to Call

Suppose the client sends:

```text
GET /journal
```

Spring finds:

```java
@GetMapping
```

---

If the client sends:

```text
GET /journal/id/2
```

Spring finds:

```java
@GetMapping("id/{myId}")
```

---

If the client sends:

```text
POST /journal
```

Spring finds:

```java
@PostMapping
```

---

If the client sends:

```text
PUT /journal/id/1
```

Spring finds:

```java
@PutMapping("/id/{id}")
```

---

If the client sends:

```text
DELETE /journal/id/3
```

Spring finds:

```java
@DeleteMapping("id/{myId}")
```

The **HTTP method + URL pattern** together determine which controller method should execute.

---

# 26. Interview Questions and Answers

## Q1. What is `@PathVariable`?

> `@PathVariable` is a Spring MVC annotation used to extract a dynamic value from the URI path and bind it to a method parameter. For example, in `/journal/id/2`, the value `2` can be captured using `@PathVariable Long myId`.

---

## Q2. Why do we use `{myId}` in `@GetMapping`?

```java
@GetMapping("id/{myId}")
```

> `{myId}` defines a dynamic URI segment. It tells Spring that this part of the URL is a variable and its value should be available to the controller method.

---

## Q3. How does Spring know that `myId` should receive the value from `{myId}`?

Because we write:

```java
@PathVariable Long myId
```

The name matches the variable in the URL:

```text
{id}
```

and the method parameter:

```java
myId
```

If the names are different, we can explicitly specify the name:

```java
@PathVariable("myId") Long id
```

---

## Q4. What is the difference between `@PathVariable` and `@RequestParam`?

> `@PathVariable` extracts values from the URI path, while `@RequestParam` extracts values from query parameters.

Example:

```text
/journal/id/2
```

uses:

```java
@PathVariable
```

Whereas:

```text
/journal?id=2
```

uses:

```java
@RequestParam
```

---

## Q5. What is `@DeleteMapping`?

> `@DeleteMapping` is a Spring MVC annotation used to map HTTP DELETE requests to a controller method. It is commonly used to delete a resource.

Example:

```java
@DeleteMapping("id/{myId}")
public JournalEntry deleteJournalEntryByID(
        @PathVariable Long myId) {

    return journalEntries.remove(myId);
}
```

---

## Q6. What does `Map.remove()` return?

> `Map.remove(key)` removes the mapping for the key and returns the value that was previously associated with that key. If the key does not exist, it returns `null`.

---

## Q7. What is `@PutMapping`?

> `@PutMapping` is used to map HTTP PUT requests. It is commonly used to update an existing resource.

---

## Q8. Why are `@PathVariable` and `@RequestBody` both used in PUT?

> The path variable identifies which resource should be updated, while the request body contains the new data.

Example:

```text
PUT /journal/id/1
```

```text
@PathVariable → identifies ID 1
@RequestBody  → contains the updated JournalEntry
```

---

## Q9. What does `Map.put()` return?

> `Map.put(key, value)` inserts or replaces a value and returns the previous value associated with that key. If there was no previous mapping, it returns `null`.

Therefore:

```java
return journalEntries.put(id, myEntry);
```

returns the **old object**, not the newly updated object.

If we want to return the updated object:

```java
journalEntries.put(id, myEntry);
return myEntry;
```

---

## Q10. What is CRUD?

> CRUD stands for Create, Read, Update, and Delete. REST APIs commonly use POST for Create, GET for Read, PUT/PATCH for Update, and DELETE for Delete.

---

# 27. Easy Interview Memory Trick

Remember this sequence:

```text
POST
 ↓
Create

GET
 ↓
Read

PUT
 ↓
Update

DELETE
 ↓
Delete
```

For a specific ID:

```text
/journal/id/2
          ↑
       ID from URL
          ↑
    @PathVariable
```

For data being sent in JSON:

```text
JSON Request Body
       ↓
   @RequestBody
       ↓
 JournalEntry object
```

So:

```text
@PathVariable
    ↓
Which resource?

@RequestBody
    ↓
What data?

@PutMapping
    ↓
Update

@DeleteMapping
    ↓
Delete

@GetMapping
    ↓
Read
```

---

# 28. Final Controller — Clean Version

A cleaner version of your current controller would be:

```java
package com.barbighaiya.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbighaiya.journalApp.entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    // GET - Get all journal entries
    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    // POST - Create a journal entry
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    // GET - Get journal entry by ID
    @GetMapping("id/{myId}")
    public JournalEntry journalEntryByID(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    // DELETE - Delete journal entry by ID
    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntryByID(@PathVariable Long myId) {
        return journalEntries.remove(myId);
    }

    // PUT - Update journal entry by ID
    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(
            @PathVariable Long id,
            @RequestBody JournalEntry myEntry) {

        journalEntries.put(id, myEntry);
        return myEntry;
    }
}
```

---

# 29. One-Minute Interview Explanation

If the interviewer asks:

> "Explain how you implemented GET by ID, DELETE and PUT in your JournalApp."

You can answer:

> "I created a REST controller with `/journal` as the base path using `@RequestMapping`. For retrieving a journal entry by ID, I used `@GetMapping("id/{myId}")` and `@PathVariable` to extract the ID from the URL. I then used the ID as the key to retrieve the corresponding `JournalEntry` from the `HashMap`.
>
> For deletion, I used `@DeleteMapping("id/{myId}")`. The ID is again extracted using `@PathVariable`, and I use `Map.remove()` to remove the corresponding entry.
>
> For updating an entry, I used `@PutMapping("id/{id}")`. The `@PathVariable` identifies which entry needs to be updated, while `@RequestBody` converts the incoming JSON into a `JournalEntry` object. I then replace the existing value in the map using `put()`.
>
> So, in short, the path variable identifies the resource, the request body contains the data, GET reads the resource, PUT updates it, and DELETE removes it."

---

# 30. Quick Revision

```text
@RequestMapping("/journal")
        ↓
Common base URL
        ↓
---------------------------------

GET /journal
        ↓
@GetMapping
        ↓
Get all entries

---------------------------------

GET /journal/id/2
        ↓
@GetMapping("id/{myId}")
        ↓
@PathVariable Long myId
        ↓
journalEntries.get(myId)
        ↓
Get entry 2

---------------------------------

DELETE /journal/id/3
        ↓
@DeleteMapping("id/{myId}")
        ↓
@PathVariable Long myId
        ↓
journalEntries.remove(myId)
        ↓
Delete entry 3

---------------------------------

PUT /journal/id/1
        +
JSON body
        ↓
@PutMapping("id/{id}")
        ↓
@PathVariable Long id
        +
@RequestBody JournalEntry myEntry
        ↓
journalEntries.put(id, myEntry)
        ↓
Update entry 1
```

## Key Annotations to Remember

| Annotation | Purpose |
|---|---|
| `@RestController` | Marks the class as a REST controller |
| `@RequestMapping` | Defines a base URL/path |
| `@GetMapping` | Handles HTTP GET requests |
| `@PostMapping` | Handles HTTP POST requests |
| `@PutMapping` | Handles HTTP PUT requests |
| `@DeleteMapping` | Handles HTTP DELETE requests |
| `@PathVariable` | Reads a value from the URL path |
| `@RequestParam` | Reads a value from the query parameter |
| `@RequestBody` | Converts request-body JSON into a Java object |

---

## Most Important Concepts from This Topic

1. **`@PathVariable`** → extracts a value from the URL path.
2. **`@RequestParam`** → extracts a value from a query parameter.
3. **`@RequestBody`** → converts request JSON into a Java object.
4. **`@GetMapping`** → reads data.
5. **`@DeleteMapping`** → deletes data.
6. **`@PutMapping`** → updates data.
7. **`Map.get()`** → retrieves a value.
8. **`Map.put()`** → inserts/replaces a value and returns the previous value.
9. **`Map.remove()`** → removes a value and returns the removed value.
10. **`@PathVariable` + `@RequestBody`** are commonly used together when updating a specific resource.

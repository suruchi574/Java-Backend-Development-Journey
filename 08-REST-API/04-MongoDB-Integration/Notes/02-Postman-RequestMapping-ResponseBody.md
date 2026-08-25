# Spring Boot REST API --- Postman, Request Mapping, GET, POST & Request Body

## 1. What is a REST API?

A **REST API** allows a client and a server to communicate using HTTP
requests.

``` text
Client → HTTP Request → Spring Boot Application
Client ← HTTP Response ← Spring Boot Application
```

The client can be:

-   Postman
-   Browser
-   Frontend application
-   Mobile application
-   Another backend service

Example:

``` text
POST http://localhost:8080/journal
```

The client sends a request to the Spring Boot application, and Spring
processes that request and returns a response.

------------------------------------------------------------------------

# 2. What is Postman?

**Postman** is a tool used to send HTTP requests to APIs and test their
responses.

It is useful while developing REST APIs because we can test our backend
without creating a frontend application.

For example:

``` text
GET  http://localhost:8080/journal
POST http://localhost:8080/journal
```

## Common HTTP Methods

  Method   Purpose
  -------- ---------------------------------------
  GET      Retrieve data
  POST     Create/send new data
  PUT      Update an existing resource
  PATCH    Partially update an existing resource
  DELETE   Delete a resource

------------------------------------------------------------------------

# 3. GET Request

A **GET request** is generally used to retrieve data from the server.

Example:

``` text
GET http://localhost:8080/journal
```

This means:

> "Give me the journal entries."

In Spring Boot, we can handle this request using `@GetMapping`.

``` java
@GetMapping
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

When the client sends:

``` text
GET /journal
```

Spring finds the method mapped to this request and executes it.

The returned `List<JournalEntry>` is then converted into a response,
normally JSON.

------------------------------------------------------------------------

# 4. `@GetMapping`

`@GetMapping` is a Spring MVC annotation used to map an **HTTP GET
request** to a controller method.

Example:

``` java
@GetMapping("/all")
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

Request:

``` text
GET /journal/all
```

Spring finds the corresponding method and executes it.

### Definition

> `@GetMapping` maps an HTTP GET request to a controller method.

------------------------------------------------------------------------

# 5. POST Request

A **POST request** is generally used to send data to the server,
commonly to create a new resource.

Example:

``` text
POST http://localhost:8080/journal
```

The client can send a `JournalEntry` in the request body:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Today I solved 3 String problems from LeetCode"
}
```

Spring Boot receives this data and can convert it into a `JournalEntry`
object.

------------------------------------------------------------------------

# 6. `@PostMapping`

`@PostMapping` is a Spring MVC annotation used to map an **HTTP POST
request** to a controller method.

Example:

``` java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {
    journalEntries.put(myEntry.getId(), myEntry);
    return true;
}
```

If the controller has:

``` java
@RequestMapping("/journal")
```

then this method handles:

``` text
POST /journal
```

### Definition

> `@PostMapping` maps an HTTP POST request to a controller method.

------------------------------------------------------------------------

# 7. `@RequestMapping`

`@RequestMapping` is used to map an HTTP request URL to a controller
class or method.

It can be used at the **class level** or **method level**.

Example:

``` java
@RestController
@RequestMapping("/journal")
public class JournalEntryController {
```

Here:

``` java
@RequestMapping("/journal")
```

sets the common/base URL for the controller.

If we then have:

``` java
@GetMapping
public List<JournalEntry> getAll() {
    ...
}
```

the complete URL becomes:

``` text
GET /journal
```

If we write:

``` java
@GetMapping("/all")
```

the complete URL becomes:

``` text
GET /journal/all
```

------------------------------------------------------------------------

# 8. Class-Level and Method-Level Mapping

Consider:

``` java
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @GetMapping("/all")
    public List<JournalEntry> getAll() {
        ...
    }

    @PostMapping("/create")
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        ...
    }
}
```

Spring combines the class-level and method-level mappings.

### GET

``` text
/journal + /all
       ↓
/journal/all
```

### POST

``` text
/journal + /create
       ↓
/journal/create
```

Therefore:

``` text
GET  /journal/all
POST /journal/create
```

------------------------------------------------------------------------

# 9. Why Use `@RequestMapping` at Class Level?

Instead of repeatedly writing:

``` java
@GetMapping("/journal/all")
@PostMapping("/journal/create")
```

we can define the common part once:

``` java
@RequestMapping("/journal")
```

and then keep method mappings shorter:

``` java
@GetMapping("/all")
@PostMapping("/create")
```

This makes the controller cleaner and easier to maintain.

------------------------------------------------------------------------

# 10. `@RequestBody`

`@RequestBody` is used when we want Spring to take the **data from the
HTTP request body and convert it into a Java object**.

For example, Postman sends:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Today I solved 3 String problems from LeetCode"
}
```

Our controller has:

``` java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {
    ...
}
```

Here:

``` java
@RequestBody JournalEntry myEntry
```

basically tells Spring:

> "Take the JSON data from the request body and convert it into a
> `JournalEntry` object."

After conversion, we can use:

``` java
myEntry.getId();
myEntry.getTitle();
myEntry.getContent();
```

just like any normal Java object.

------------------------------------------------------------------------

# 11. How Does JSON Become a Java Object?

Suppose Postman sends:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Today I solved 3 String problems"
}
```

Spring receives the JSON and, with the help of its HTTP message
conversion mechanism, converts it into a `JournalEntry` object.

Conceptually, it is similar to:

``` java
JournalEntry myEntry = new JournalEntry();

myEntry.setId(2L);
myEntry.setTitle("DSA");
myEntry.setContent("Today I solved 3 String problems");
```

We do not manually write this conversion.

`@RequestBody` tells Spring to perform the request-body-to-object
conversion.

------------------------------------------------------------------------

# 12. Sending JSON Through Postman

When sending JSON using Postman:

1.  Select **POST**
2.  Enter the API URL
3.  Go to **Body**
4.  Select **raw**
5.  Select **JSON**
6.  Enter the JSON data
7.  Click **Send**

Example:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Today I solved 3 String problems from LeetCode"
}
```

Selecting **raw + JSON** tells Postman that the request body contains
JSON data.

Postman sends the appropriate content type:

``` text
Content-Type: application/json
```

This allows Spring to recognize the incoming request body as JSON and
convert it into the required Java object.

------------------------------------------------------------------------

# 13. Complete POST Request Flow

Suppose we send this from Postman:

``` text
POST http://localhost:8080/journal
```

Body:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Today I solved 3 String problems"
}
```

The flow is:

``` text
Postman
   |
   | POST /journal
   | JSON data
   ↓
Spring Boot
   |
   ↓
@RequestMapping("/journal")
   |
   ↓
@PostMapping
   |
   ↓
@RequestBody
   |
   ↓
JSON converted to JournalEntry object
   |
   ↓
createEntry(JournalEntry myEntry)
   |
   ↓
journalEntries.put(myEntry.getId(), myEntry)
   |
   ↓
JournalEntry stored in HashMap
   |
   ↓
Response returned to Postman
```

------------------------------------------------------------------------

# 14. Understanding `createEntry()`

Example:

``` java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {

    journalEntries.put(myEntry.getId(), myEntry);

    return true;
}
```

## `@PostMapping`

``` java
@PostMapping
```

Tells Spring:

> This method should handle a POST request.

## `@RequestBody`

``` java
@RequestBody JournalEntry myEntry
```

Tells Spring:

> Take the JSON request body and convert it into a `JournalEntry`
> object.

## `myEntry.getId()`

Suppose the JSON contains:

``` json
{
    "id": 2,
    "title": "DSA",
    "content": "Solved String problems"
}
```

Then:

``` java
myEntry.getId()
```

returns:

``` text
2
```

## `journalEntries.put()`

``` java
journalEntries.put(myEntry.getId(), myEntry);
```

becomes conceptually:

``` java
journalEntries.put(2L, myEntry);
```

So the Map becomes:

``` text
Key       Value

2L   →   JournalEntry object
```

------------------------------------------------------------------------

# 15. Understanding the GET Method

Your method is:

``` java
@GetMapping
public List<JournalEntry> getAll() {

    return new ArrayList<>(journalEntries.values());
}
```

Suppose the Map contains:

``` text
1L → JournalEntry #1
2L → JournalEntry #2
```

Calling:

``` java
journalEntries.values()
```

gets the values:

``` text
JournalEntry #1
JournalEntry #2
```

`values()` returns a:

``` java
Collection<JournalEntry>
```

Then:

``` java
new ArrayList<>(journalEntries.values())
```

creates a new `ArrayList` containing those values.

So the method returns:

``` text
List<JournalEntry>
```

Spring then converts the Java objects into JSON and sends them back to
the client.

------------------------------------------------------------------------

# 16. GET Response in Postman

For example, the server might return:

``` json
[
    {
        "id": 1,
        "title": "Development",
        "content": "Today I learned Post Mapping"
    },
    {
        "id": 2,
        "title": "DSA",
        "content": "Today I solved 3 String problems from LeetCode"
    }
]
```

The Java:

``` java
List<JournalEntry>
```

is converted into a JSON array in the HTTP response.

This conversion is handled by Spring's HTTP message conversion
mechanism, commonly using Jackson in a standard Spring Boot web
application.

------------------------------------------------------------------------

# 17. `@RestController`

Your controller contains:

``` java
@RestController
public class JournalEntryController {
```

`@RestController` is used to define a class as a REST controller.

It is effectively a combination of:

``` java
@Controller
@ResponseBody
```

It tells Spring that the class handles HTTP requests and that its
handler methods should normally return data directly in the HTTP
response body.

For example:

``` java
@GetMapping
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

The returned Java objects are converted into JSON and sent in the HTTP
response.

------------------------------------------------------------------------

# 18. Controller Method Visibility

Controller handler methods are commonly declared as:

``` java
public
```

Example:

``` java
@GetMapping
public List<JournalEntry> getAll() {
    ...
}
```

``` java
@PostMapping
public boolean createEntry(@RequestBody JournalEntry myEntry) {
    ...
}
```

`public` makes the method accessible and is the conventional style for
controller handler methods.

An important point:

> An HTTP request does not directly call the Java method from outside
> the application. The request reaches Spring, and Spring's
> request-mapping mechanism finds the appropriate handler method and
> invokes it.

------------------------------------------------------------------------

# 19. Understanding `Collection`

`Collection` is a Java interface that represents a group of objects.

A simplified hierarchy is:

``` text
Collection
   |
   +--- List
   |      |
   |      +--- ArrayList
   |      +--- LinkedList
   |
   +--- Set
          |
          +--- HashSet
          +--- TreeSet
```

Therefore:

-   `Collection` → general interface for a group of objects
-   `List` → ordered collection that supports index-based access
-   `Set` → collection that does not allow duplicate elements
-   `ArrayList` → implementation of `List`

------------------------------------------------------------------------

# 20. What Does `journalEntries.values()` Return?

Given:

``` java
Map<Long, JournalEntry> journalEntries = new HashMap<>();
```

when we call:

``` java
journalEntries.values()
```

it returns:

``` java
Collection<JournalEntry>
```

It gives us all the values stored in the Map.

For example:

``` text
Map

1L → JournalEntry #1
2L → JournalEntry #2
3L → JournalEntry #3
```

Then:

``` java
journalEntries.values()
```

gives:

``` text
JournalEntry #1
JournalEntry #2
JournalEntry #3
```

It does not give the keys.

------------------------------------------------------------------------

# 21. `keySet()`, `values()` and `entrySet()`

A Map provides three important methods:

``` java
journalEntries.keySet();
```

Returns all keys:

``` text
1L
2L
3L
```

------------------------------------------------------------------------

``` java
journalEntries.values();
```

Returns all values:

``` text
JournalEntry #1
JournalEntry #2
JournalEntry #3
```

------------------------------------------------------------------------

``` java
journalEntries.entrySet();
```

Returns key-value pairs:

``` text
1L → JournalEntry #1
2L → JournalEntry #2
3L → JournalEntry #3
```

### Easy way to remember

``` text
Map
 |
 +--- keySet()   → keys
 |
 +--- values()   → values
 |
 +--- entrySet() → key + value
```

------------------------------------------------------------------------

# 22. Why Use `new ArrayList<>(journalEntries.values())`?

This:

``` java
return new ArrayList<>(journalEntries.values());
```

means:

``` text
journalEntries.values()
        ↓
Collection<JournalEntry>
        ↓
Create a new ArrayList
        ↓
ArrayList<JournalEntry>
        ↓
Return it
```

In simple words:

> Get all the `JournalEntry` objects from the Map, put them into a new
> `ArrayList`, and return that list.

We can also write:

``` java
public Collection<JournalEntry> getAll() {
    return journalEntries.values();
}
```

This is valid.

But if the method is designed to return a `List<JournalEntry>`, we can
create an `ArrayList` from the collection:

``` java
public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
}
```

------------------------------------------------------------------------

# 23. Complete Controller Example

``` java
package com.barbighaiya.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbighaiya.journalApp.entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }
}
```

------------------------------------------------------------------------

# 24. Complete Request Flow

## POST --- Creating an Entry

``` text
Postman
   ↓
POST /journal
   ↓
@RequestMapping("/journal")
   ↓
@PostMapping
   ↓
@RequestBody
   ↓
JSON → JournalEntry object
   ↓
createEntry()
   ↓
journalEntries.put(id, object)
   ↓
Object stored in HashMap
   ↓
Response → Postman
```

## GET --- Fetching All Entries

``` text
Postman
   ↓
GET /journal
   ↓
@RequestMapping("/journal")
   ↓
@GetMapping
   ↓
getAll()
   ↓
journalEntries.values()
   ↓
Collection<JournalEntry>
   ↓
new ArrayList<>(...)
   ↓
List<JournalEntry>
   ↓
Java objects → JSON
   ↓
Response → Postman
```

------------------------------------------------------------------------

# 25. Quick Revision

  -----------------------------------------------------------------------
  Concept                             Meaning
  ----------------------------------- -----------------------------------
  `@RestController`                   Marks a class as a REST controller

  `@RequestMapping`                   Maps a URL/request to a controller
                                      class or method

  `@GetMapping`                       Handles HTTP GET requests

  `@PostMapping`                      Handles HTTP POST requests

  `@RequestBody`                      Converts request-body JSON into a
                                      Java object

  `@PutMapping`                       Handles HTTP PUT requests

  `@DeleteMapping`                    Handles HTTP DELETE requests

  Postman                             Tool used to send HTTP requests and
                                      test APIs

  `keySet()`                          Returns all keys of a Map

  `values()`                          Returns all values of a Map

  `entrySet()`                        Returns key-value pairs

  `Collection`                        General Java interface representing
                                      a group of objects

  `ArrayList`                         A List implementation

  `new ArrayList<>(collection)`       Creates a new ArrayList containing
                                      the collection's elements
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 26. One-Line Definitions

### Postman

> A tool used to send HTTP requests and test REST APIs.

### `@RestController`

> Marks a class as a REST controller whose methods typically return data
> directly in the HTTP response body.

### `@RequestMapping`

> Maps a URL/request to a controller class or method and can define a
> common base path.

### `@GetMapping`

> Maps an HTTP GET request to a controller method, generally for
> retrieving data.

### `@PostMapping`

> Maps an HTTP POST request to a controller method, generally for
> creating or sending data.

### `@RequestBody`

> Tells Spring to read the HTTP request body and convert incoming data
> such as JSON into a Java object.

### `journalEntries.values()`

> Returns a collection containing all values stored in the Map.

### `new ArrayList<>(journalEntries.values())`

> Creates a new ArrayList containing all the `JournalEntry` values from
> the Map.

------------------------------------------------------------------------

# 27. Easy Mental Model

Remember the complete process like this:

``` text
                  CLIENT
                    |
                  Postman
                    |
               HTTP Request
                    |
             GET / POST / ...
                    |
                    ↓
          @RequestMapping
                    |
             ┌──────┴──────┐
             ↓             ↓
        @GetMapping   @PostMapping
             |             |
             |        @RequestBody
             |             ↓
             |       JSON → Java Object
             |             |
             └──────┬──────┘
                    ↓
             Controller Method
                    ↓
              Application Logic
                    ↓
                 Response
                    ↓
                 Postman
```

The most important flow to remember is:

**Postman → HTTP Request → Spring Mapping → Controller Method →
Processing → HTTP Response**
s
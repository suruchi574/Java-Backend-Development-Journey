# Embedded Tomcat and First Servlet

## Introduction

A **Servlet** is a Java class used to create dynamic web applications. It receives a request from the client (browser), processes it, and sends a response back.

To execute a Servlet, we need a **Servlet Container**. One of the most popular Servlet Containers is **Apache Tomcat**.

In this project, instead of installing Tomcat separately, we are using **Embedded Tomcat**, which runs directly inside our Java application.

---

# Project Flow

```
main()
   │
   ▼
Create Tomcat Object
   │
   ▼
Create Web Application (Context)
   │
   ▼
Register Servlet
   │
   ▼
Map URL to Servlet
   │
   ▼
Create HTTP Connector
   │
   ▼
Start Tomcat
   │
   ▼
Wait for Client Requests
   │
   ▼
Browser → /hello
   │
   ▼
HelloServlet.service()
   │
   ▼
Response to Browser
```

---

# Imports

```java
import org.apache.catalina.Context;
```

### Context

- **Package:** `org.apache.catalina`
- **Type:** Interface

A **Context** represents a **Web Application** inside Tomcat.

Every Servlet must belong to a Context.

Example:

```
Tomcat
   │
   ├── ShoppingApp (Context)
   ├── StudentApp (Context)
   └── BankingApp (Context)
```

---

```java
import org.apache.catalina.LifecycleException;
```

### LifecycleException

- **Package:** `org.apache.catalina`
- **Type:** Class

This exception occurs when Tomcat fails during its lifecycle.

Example:

- Server cannot start
- Invalid configuration
- Invalid Context

Since `start()` may throw this exception, we use

```java
throws LifecycleException
```

---

```java
import org.apache.catalina.startup.Tomcat;
```

### Tomcat

- **Package:** `org.apache.catalina.startup`
- **Type:** Class

This class is used to create an **Embedded Tomcat Server**.

It provides methods to:

- Create a Web Application
- Register Servlets
- Start the Server
- Stop the Server

---

# main() Method

```java
public static void main(String[] args)
```

This is the entry point of every Java application.

Execution starts from this method.

---

# Creating Embedded Tomcat

```java
Tomcat tomcat = new Tomcat();
```

### What happens here?

- `Tomcat` is a class.
- `new` creates an object in memory.
- `tomcat` is a reference variable.

Now our application has its own Embedded Tomcat Server.

Without this object, we cannot host Servlets.

---

# setPort()

```java
tomcat.setPort(8080);
```

### Method Signature

```java
setPort(int port)
```

### Parameter

`8080` → Port number

The browser communicates with Tomcat through this port.

Example

```
http://localhost:8080
```

If this method is not called, Tomcat uses **8080** by default.

---

# System.getProperty()

```java
String webappDir = System.getProperty("user.dir");
```

### Purpose

Returns the current project directory.

Example

```
C:\Users\barbi\Desktop\ServletBasics
```

Tomcat needs this directory because it becomes the **Web Application Directory (docBase)**.

---

# addContext()

```java
Context context = tomcat.addContext("", webappDir);
```

### Method Signature

```java
Context addContext(String contextPath,
                   String docBase)
```

### Return Type

`Context`

### Parameters

### 1. contextPath

Represents the application name.

```
""
```

means Root Application.

Example

```
""        → http://localhost:8080/hello

"/student" → http://localhost:8080/student/hello
```

### 2. docBase

Represents the physical location of the Web Application.

Here we pass

```java
webappDir
```

which contains our project directory.

### Why is Context required?

Tomcat cannot directly execute a Servlet.

First, it needs a Web Application (Context), and then the Servlet is added to that Context.

---

# addServlet()

```java
Tomcat.addServlet(
        context,
        "HelloServlet",
        new HelloServlet());
```

### Method Signature

```java
addServlet(Context,
           String,
           Servlet)
```

### This is a Static Method

So we call it using

```java
Tomcat.addServlet(...)
```

instead of

```java
tomcat.addServlet(...)
```

### Parameters

### context

The Context in which the Servlet should be registered.

### "HelloServlet"

Logical name of the Servlet.

It can be any valid name.

### new HelloServlet()

Creates an object of our Servlet class.

### Why is addServlet() required?

It registers the Servlet with Tomcat.

Without registering, Tomcat does not know that the Servlet exists.

---

# addServletMappingDecoded()

```java
context.addServletMappingDecoded(
        "/hello",
        "HelloServlet");
```

### Purpose

Maps a URL to a registered Servlet.

### Parameters

### "/hello"

URL Pattern.

When the browser requests

```
http://localhost:8080/hello
```

Tomcat searches for this mapping.

### "HelloServlet"

Name of the registered Servlet.

It **must match** the name used in `addServlet()`.

---

# getConnector()

```java
tomcat.getConnector();
```

### What is a Connector?

A Connector connects

```
Browser
      │
      ▼
Tomcat
```

It is responsible for

- Opening the Port
- Accepting HTTP Requests
- Sending HTTP Responses

Without calling this method, Tomcat may not start listening on port **8080**.

---

# start()

```java
tomcat.start();
```

Starts the Embedded Tomcat Server.

Internally it

- Creates the Context
- Loads all Servlets
- Opens the Port
- Starts accepting requests

Since it may fail, it throws

```java
LifecycleException
```

---

# getServer()

```java
tomcat.getServer();
```

Returns the internal **Server** object of Tomcat.

The Server manages the complete Tomcat lifecycle.

---

# await()

```java
tomcat.getServer().await();
```

Keeps the server running.

Without this method

```
main()
```

finishes execution,

the JVM exits,

and Tomcat stops immediately.

Because of `await()`, the server keeps waiting for client requests until it is stopped manually.

---

# HelloServlet

```java
public class HelloServlet
        extends HttpServlet
```

### HttpServlet

- **Package:** `jakarta.servlet.http`
- **Type:** Class

It provides all HTTP-related features.

Instead of writing everything from scratch, we extend `HttpServlet`.

---

# Why extend HttpServlet?

By extending `HttpServlet`, our class becomes a Servlet and can handle HTTP requests.

```
HttpServlet
      ▲
      │
HelloServlet
```

---

# service()

```java
protected void service(
        HttpServletRequest req,
        HttpServletResponse res)
```

The `service()` method is automatically called by Tomcat whenever a client sends a request to the Servlet.

We never call this method manually.

Tomcat calls it for us.

---

# HttpServletRequest

Represents the **Client Request**.

It contains information like

- URL
- Parameters
- Headers
- Cookies

Example

```
Browser
      │
      ▼
Request
      │
      ▼
HttpServletRequest
```

---

# HttpServletResponse

Represents the **Response** sent back to the client.

Example

```java
res.getWriter().println("Hello World");
```

The text is sent to the browser.

---

# Complete Request Flow

```
Browser
      │
      ▼
http://localhost:8080/hello
      │
      ▼
Connector
      │
      ▼
Tomcat
      │
      ▼
Context
      │
      ▼
URL Mapping
      │
      ▼
HelloServlet
      │
      ▼
service()
      │
      ▼
HttpServletResponse
      │
      ▼
Browser
```

---

# Execution Flow

```
1. main() starts

2. Embedded Tomcat object is created

3. Current project directory is obtained

4. A Web Application (Context) is created

5. HelloServlet is registered

6. URL "/hello" is mapped to HelloServlet

7. Connector is created

8. Tomcat server starts

9. Server waits for client requests

10. Browser requests
    http://localhost:8080/hello

11. Tomcat finds the URL mapping

12. HelloServlet.service() is executed

13. Response is sent to the browser
```

---

# Key Points

- **Tomcat** → Embedded Servlet Container.
- **Context** → Represents one Web Application.
- **Servlet** → Java class that handles client requests.
- **addContext()** → Creates a Web Application.
- **addServlet()** → Registers a Servlet with Tomcat.
- **addServletMappingDecoded()** → Maps a URL to a Servlet.
- **getConnector()** → Opens the HTTP port.
- **start()** → Starts the Tomcat Server.
- **await()** → Keeps the server running.
- **HttpServletRequest** → Represents the client's request.
- **HttpServletResponse** → Represents the server's response.
- **service()** → Executes automatically for every request.

---

# Interview Questions

### 1. What is a Servlet?
A Java class that handles client requests and generates dynamic web content.

### 2. What is Embedded Tomcat?
A Tomcat server that runs inside a Java application without installing Tomcat separately.

### 3. Why is Context required?
It represents a Web Application. Every Servlet must belong to a Context.

### 4. Why do we call `getConnector()`?
To create the HTTP Connector so Tomcat starts listening for incoming requests.

### 5. Why is `await()` required?
It keeps the server running. Without it, the application exits immediately after starting Tomcat.
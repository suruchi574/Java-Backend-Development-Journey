# Spring Boot Web MVC – Controller, HttpServletRequest, HttpSession & JSP Notes

## What is a Controller?

A **Controller** is a Spring MVC component that receives HTTP requests from the client, processes them, and returns the appropriate response (such as a JSP page, HTML page, or JSON).

Spring recognizes a controller class using the `@Controller` annotation.

```java
@Controller
public class HomeController {

}
```

### Why do we use `@Controller`?

- Marks the class as a Spring MVC Controller.
- Handles incoming HTTP requests.
- Maps URLs to Java methods.
- Calls the business logic (Service layer).
- Returns a View (JSP, Thymeleaf, etc.) or Response.

---

## Request Flow in Spring MVC

```
Browser
    │
    ▼
DispatcherServlet
    │
    ▼
Controller
    │
    ▼
Business Logic (Service)
    │
    ▼
View (JSP)
    │
    ▼
Browser
```

### DispatcherServlet

The **DispatcherServlet** is the front controller of Spring MVC.

It is responsible for:

- Receiving every HTTP request.
- Finding the correct controller.
- Calling the appropriate method.
- Returning the response back to the client.

> Think of DispatcherServlet as the **manager** that decides which controller should handle the request.

---

# @RequestMapping

`@RequestMapping` is used to map a URL to a controller method.

Example:

```java
@RequestMapping("/")
public String home() {
    return "index.jsp";
}
```

When the user visits:

```
http://localhost:8080/
```

Spring executes:

```java
home()
```

and returns

```
index.jsp
```

---

## Mapping Multiple URLs

A single controller can contain multiple request mappings.

Example:

```java
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index.jsp";
    }

    @RequestMapping("add")
    public String add(...) {
        return "result.jsp";
    }

}
```

---

# Home Method

```java
@RequestMapping("/")
public String home()
{
    System.out.println("Home method called");
    return "index.jsp";
}
```

### Explanation

When the browser opens:

```
http://localhost:8080/
```

Spring executes:

```java
home()
```

The method returns

```
index.jsp
```

which becomes the home page.

---

# add() Method

```java
@RequestMapping("add")
public String add(HttpServletRequest req,
                  HttpSession session)
```

This method is executed when the user submits the form.

Example URL:

```
http://localhost:8080/add
```

---

# HttpServletRequest

## What is HttpServletRequest?

`HttpServletRequest` is a Servlet API interface that represents the **HTTP request sent by the client (browser)**.

It contains everything related to the request:

- Form data
- URL
- Query parameters
- Headers
- Cookies
- Client information

Spring can automatically inject it into controller methods.

Example:

```java
public String add(HttpServletRequest req)
```

---

## Why do we use HttpServletRequest?

To read data sent by the browser.

Example HTML form:

```html
<input type="text" name="num1">
<input type="text" name="num2">
```

Read values:

```java
String value = req.getParameter("num1");
```

Output:

```
"10"
```

Notice that the returned value is always a **String**.

---

## Converting String to Integer

Since `getParameter()` returns a String, we convert it into an integer.

```java
int num1 = Integer.parseInt(req.getParameter("num1"));
int num2 = Integer.parseInt(req.getParameter("num2"));
```

Now we can perform arithmetic operations.

```java
int result = num1 + num2;
```

---

## Common Methods of HttpServletRequest

| Method | Description |
|---------|-------------|
| getParameter() | Reads form data |
| getParameterValues() | Reads multiple values |
| getHeader() | Reads request headers |
| getCookies() | Returns cookies |
| getMethod() | GET or POST |
| getRequestURI() | Returns requested URL |

---

# HttpSession

## What is HttpSession?

`HttpSession` is an interface provided by the Servlet API to store data for a particular user across multiple HTTP requests.

Normally, every HTTP request is independent.

A session allows the server to remember information about the same user while they navigate the application.

Spring automatically injects it.

Example:

```java
public String add(HttpServletRequest req,
                  HttpSession session)
```

---

## Why do we need HttpSession?

Suppose the user calculates:

```
10 + 20
```

The controller computes:

```
30
```

If we immediately move to another JSP page, that value would normally be lost.

Using Session:

```
Browser
      │
      ▼
Controller
      │
session.setAttribute()
      │
      ▼
JSP can access it
```

---

## setAttribute()

Stores data in the session.

Syntax:

```java
session.setAttribute(String name, Object value);
```

Example:

```java
session.setAttribute("result", result);
```

Here,

Key:

```
result
```

Value:

```
30
```

---

## getAttribute()

Reads data from the session.

Example:

```java
session.getAttribute("result");
```

Output:

```
30
```

---

## invalidate()

Removes the entire session.

```java
session.invalidate();
```

Useful during logout.

---

## Session Life Cycle

```
User visits website
        │
        ▼
Session Created
        │
        ▼
Store Attributes
        │
        ▼
Read Attributes
        │
        ▼
Logout / Timeout
        │
        ▼
Session Destroyed
```

---

## Common HttpSession Methods

| Method | Description |
|---------|-------------|
| setAttribute() | Store data |
| getAttribute() | Retrieve data |
| removeAttribute() | Remove one attribute |
| invalidate() | Destroy session |
| getId() | Returns Session ID |

---

# Controller Code Explanation

```java
@RequestMapping("add")
public String add(HttpServletRequest req,
                  HttpSession session)
{
```

Spring receives the request.

---

Read first number:

```java
int num1 =
Integer.parseInt(req.getParameter("num1"));
```

Reads:

```
num1=10
```

Converts:

```
"10"
```

into

```
10
```

---

Read second number:

```java
int num2 =
Integer.parseInt(req.getParameter("num2"));
```

---

Calculate:

```java
int result = num1 + num2;
```

Example:

```
10 + 20 = 30
```

---

Store inside session:

```java
session.setAttribute("result", result);
```

Stores

```
result = 30
```

---

Return:

```java
return "result.jsp";
```

Spring forwards the request to

```
result.jsp
```

---

# JSP (Java Server Pages)

JSP is a server-side technology used to create dynamic web pages using Java.

Instead of writing HTML inside Servlets, we write HTML in JSP pages and insert Java where needed.

Example:

```jsp
<h2>Hello User</h2>
```

---

# Old JSP Scriptlet (Not Recommended)

Earlier, Java code was written directly inside JSP using scriptlets.

Example:

```jsp
<%= session.getAttribute("result") %>
```

This mixes Java code with HTML.

Example:

```jsp
<h2>
Output:
<%= session.getAttribute("result") %>
</h2>
```

Although it works, it makes JSP harder to read and maintain.

---

# JSTL (JavaServer Pages Standard Tag Library)

JSTL is a collection of standard tags that simplify JSP development.

It helps reduce Java code in JSP and provides tags for common tasks.

### Advantages

- Reduces Java code inside JSP.
- Makes JSP cleaner and easier to maintain.
- Provides tags for loops, conditions, formatting, and more.
- Encourages separation of business logic from presentation.

Common JSTL tags include:

- `<c:if>` – Conditional statements
- `<c:forEach>` – Loop through collections
- `<c:choose>` – Switch-like behavior
- `<fmt:formatDate>` – Date formatting

> JSTL is usually used together with Expression Language (EL).

---

# Expression Language (EL)

Expression Language is a simple way to access data in JSP without writing Java code.

Syntax:

```jsp
${attributeName}
```

Example:

```jsp
${result}
```

Spring automatically searches for `result` in different scopes (page, request, session, application).

Since we stored:

```java
session.setAttribute("result", result);
```

we can display it simply as:

```jsp
<h2>Output is: ${result}</h2>
```

instead of

```jsp
<%= session.getAttribute("result") %>
```

---

# result.jsp

```jsp
<%@ page language="java" %>

<html>

<head>

<link rel="stylesheet"
type="text/css"
href="style.css">

</head>

<body>

<h2>Output is: ${result}</h2>

</body>

</html>
```

### Explanation

The JSP page:

- Loads the stylesheet (`style.css`).
- Reads the `result` attribute using EL (`${result}`).
- Displays the calculated sum.
- Does not contain Java code, making it cleaner and more maintainable.

---

# Required Dependency for JSP

To render JSP pages in Spring Boot, add the Jasper dependency to `pom.xml`.

```xml
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
</dependency>
```

### Why is it needed?

Spring Boot does not support JSP rendering out of the box.

The Jasper engine compiles and renders JSP pages.

> This dependency enables JSP support; it does **not** make a class a controller. A class becomes a controller only when it is annotated with `@Controller`.

---

# Complete Request Flow

```
User enters:

10
20

        │

        ▼

Click Submit

        │

        ▼

DispatcherServlet

        │

        ▼

HomeController.add()

        │

        ▼

HttpServletRequest

Reads:

num1 = 10

num2 = 20

        │

        ▼

Calculates

30

        │

        ▼

HttpSession

Stores

result = 30

        │

        ▼

Returns

result.jsp

        │

        ▼

JSP

Displays

Output is: 30
```

---

# Key Interview Questions

### 1. What is a Controller?
A Spring MVC component that handles HTTP requests, processes them, and returns a view or response.

### 2. What is DispatcherServlet?
The front controller of Spring MVC that receives all requests, finds the correct controller, and dispatches the request.

### 3. What is HttpServletRequest?
An interface that represents the client's HTTP request and allows access to request data such as form fields, headers, cookies, and URLs.

### 4. Why does `getParameter()` return a String?
HTTP request parameters are transmitted as text, so `getParameter()` always returns a `String`.

### 5. What is HttpSession?
An interface that stores user-specific data across multiple requests, allowing the server to remember information between requests.

### 6. Difference between HttpServletRequest and HttpSession?

| HttpServletRequest | HttpSession |
|--------------------|------------|
| Exists for a single request | Exists across multiple requests |
| Stores request data | Stores user session data |
| Used to read client input | Used to remember user information |

### 7. What is JSP?
A server-side technology used to create dynamic web pages by combining HTML with Java-based technologies.

### 8. What is JSTL?
The JavaServer Pages Standard Tag Library, which provides reusable tags for common tasks like loops, conditions, and formatting.

### 9. What is Expression Language (EL)?
A simple syntax (`${...}`) used in JSP to access data from page, request, session, or application scopes without writing Java code.

### 10. Why use EL instead of scriptlets?
EL keeps JSP pages cleaner, easier to read, and easier to maintain by avoiding embedded Java code.
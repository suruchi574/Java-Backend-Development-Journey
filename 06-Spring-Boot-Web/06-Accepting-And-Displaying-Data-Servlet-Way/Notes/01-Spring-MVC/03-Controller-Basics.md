# Spring MVC Controller

## What is a Controller?

A **Controller** is a Spring MVC component that **handles incoming HTTP requests**, processes them (usually by calling the Service layer), and returns an appropriate response such as a **JSP page**, **JSON**, or **plain text**.

It acts as a **bridge between the client (Browser) and the application's business logic**.

> **Remember:** A Controller controls the flow of the web application.

---

## Why do we need a Controller?

Without a Controller, the client would directly access the view (JSP), making it difficult to perform validation, business logic, or database operations.

A Controller helps us:
- Handle HTTP requests.
- Validate user input.
- Call the Service layer.
- Return the appropriate View or Response.
- Control the application's request flow.

---

## @Controller Annotation

```java
@Controller
public class HomeController {

}
```

`@Controller` is a **Spring Stereotype Annotation** that marks a class as a **Spring MVC Controller**.

When the application starts, Spring scans the project, finds classes annotated with `@Controller`, creates their objects (Beans), and registers them in the Spring IoC Container.

---

## Responsibilities of a Controller

- Receives HTTP requests.
- Processes client requests.
- Calls the Service layer.
- Returns a View (JSP/Thymeleaf) or data.
- Controls the application's flow.

---

## How a Controller Works

```text
            Browser
               │
        HTTP Request
               │
               ▼
      DispatcherServlet
               │
 Finds Matching Controller
               │
               ▼
      HomeController
               │
      Calls Service Layer
               │
               ▼
          Business Logic
               │
               ▼
       Returns Result
               │
               ▼
      HomeController
               │
 Return "index.jsp"
               │
               ▼
        View Resolver
               │
               ▼
          index.jsp
               │
               ▼
            Browser
```

---

## Example

```java
package com.barbighaiya.SpringBootWeb1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {

        System.out.println("Home method called");

        return "index.jsp";
    }
}
```

### Explanation

- `@Controller` tells Spring that this class handles web requests.
- `@RequestMapping("/")` maps the home URL.
- The `home()` method executes when the user visits `/`.
- `"index.jsp"` is returned as the View.

---

## Controller in MVC Architecture

```text
        Browser
           │
           ▼
     Controller
           │
           ▼
       Service
           │
           ▼
      Repository
           │
           ▼
       Database
```

---

## @Controller vs @RestController

| @Controller | @RestController |
|-------------|-----------------|
| Returns a View (JSP, Thymeleaf) | Returns Data (JSON/XML) |
| Used in MVC Applications | Used in REST APIs |
| Requires View Resolver | No View Resolver Required |

---

## Real-Life Example

Imagine ordering food online.

- **Customer** → Browser
- **Waiter** → Controller
- **Chef** → Service
- **Kitchen Storage** → Database

The customer tells the waiter what they want.
The waiter takes the request to the chef.
The chef prepares the food.
The waiter brings the food back to the customer.

Similarly, the Controller receives the request, gets the required data, and returns the response.

---

# Interview Questions

### What is a Controller?

A Controller is a Spring MVC component that receives HTTP requests, processes them (usually through the Service layer), and returns a View or data to the client.

---

### What is the purpose of the `@Controller` annotation?

`@Controller` tells Spring that the class is responsible for handling web requests. Spring automatically detects it during component scanning and creates its Bean.

---

### Can a Controller directly access the Database?

Technically yes, but **it is not recommended**.

A Controller should call the **Service layer**, and the Service layer should interact with the **Repository/DAO layer**.

---

### What does a Controller return?

A Controller can return:
- A View (JSP/Thymeleaf)
- JSON
- XML
- Plain Text

---

## Quick Revision

- Controller handles HTTP requests.
- Marked using `@Controller`.
- Acts as a bridge between Browser and Business Logic.
- Calls the Service layer.
- Returns a View or Response.
- Spring automatically creates its Bean during Component Scanning.
- In Spring MVC, every request eventually reaches a Controller through the DispatcherServlet.
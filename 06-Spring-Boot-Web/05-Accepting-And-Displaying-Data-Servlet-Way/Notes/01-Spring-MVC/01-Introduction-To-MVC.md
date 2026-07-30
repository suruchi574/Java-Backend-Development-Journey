# Spring MVC - Introduction to MVC

## What is MVC?

**MVC (Model-View-Controller)** is a **design pattern** used to build web applications by separating different responsibilities into different components.

Instead of writing all the logic in one place, MVC divides the application into:

- **Model** → Handles data and business logic.
- **View** → Displays data to the user (UI).
- **Controller** → Receives requests, processes them, and connects the Model with the View.

This separation makes the application easier to understand, develop, test, and maintain.

---

# MVC Architecture

```text
             User (Client)
                   │
          HTTP Request
                   │
                   ▼
            Controller
                   │
      Calls business logic
                   │
                   ▼
               Model (POJO)
                   │
          Read/Write Data
                   │
                   ▼
               Database
                   │
             Returns Data
                   │
                   ▼
            Controller
                   │
        Sends data to View
                   │
                   ▼
            View (JSP)
                   │
      Generates HTML Response
                   │
                   ▼
             User (Client)
```

---

# Components of MVC

## 1. Model

The **Model** represents the application's **data and business logic**.

### Responsibilities

- Stores application data.
- Performs business logic.
- Communicates with the database.
- Returns data to the controller.

The model is usually created using a **POJO (Plain Old Java Object)**.

### Example

```java
public class Student {

    private int id;
    private String name;

    // Getters and Setters
}
```

---

## What is a POJO?

**POJO** stands for **Plain Old Java Object**.

It is simply a normal Java class that contains data.

### Characteristics

- No special framework dependency.
- Contains fields (variables).
- Contains constructors.
- Contains getters and setters.
- Represents application data as Java objects.

### Example

```java
public class Employee {

    private int id;
    private String name;

    // Constructors

    // Getters

    // Setters
}
```

Almost every entity (Student, Employee, Product, User, etc.) in a Spring application is represented using a POJO.

---

## 2. View

The **View** is responsible for displaying information to the user.

It creates the frontend page that the browser can understand.

### Common View Technologies in Spring MVC

- JSP (JavaServer Pages)
- Thymeleaf
- FreeMarker
- Groovy Markup
- Script Views
- JSP with JSTL

---

## What is JSP?

**JSP (JavaServer Pages)** is a **View Technology** used to create dynamic web pages.

A JSP page can contain:

- HTML
- CSS
- JavaScript
- JSP Tags
- Expression Language (EL)
- (Earlier versions also allowed Java code inside JSP)

### Example

```jsp
<h2>Welcome ${name}</h2>
```

### How JSP Works

The browser never receives a JSP file directly.

```text
JSP
   ↓
Converted into Servlet
   ↓
Compiled
   ↓
Executed by Tomcat
   ↓
Generates HTML
   ↓
Sent to Browser
```

> **Important:** A JSP page is internally converted into a **Servlet**, because Tomcat is a **Servlet Container** and can execute only Servlets.

---

## 3. Controller

The **Controller** acts as the middle layer between the client, model, and view.

### Responsibilities

- Accepts user requests.
- Processes the request.
- Calls the required business logic.
- Gets data from the model.
- Sends the data to the view.
- Returns the response to the client.

In:

- **Traditional Java Web Applications** → **Servlet acts as the Controller**
- **Spring MVC** → **Classes annotated with `@Controller` act as the Controller**

---

# Request Flow

```text
Client
   │
HTTP Request
   │
   ▼
Controller
   │
Creates/Fetches Object
   │
   ▼
Model (POJO)
   │
Database Operations
   │
   ▼
Controller
   │
Passes Object
   │
   ▼
JSP (View)
   │
Converts Object into HTML
   │
   ▼
Client
```

---

# Object Flow

Internally, data moves in the form of **Java Objects**.

```text
Database
      │
      ▼
Java Object (POJO)
      │
      ▼
Controller
      │
      ▼
JSP
      │
      ▼
HTML
      │
      ▼
Browser
```

Everything inside the Java application is represented as **objects**, while the browser understands only **HTML, CSS, and JavaScript**.

---

# Servlet vs JSP

| Servlet | JSP |
|----------|-----|
| Java class | View page |
| Handles requests | Displays UI |
| Processes business logic | Generates HTML |
| Acts as Controller | Acts as View |

---

# Traditional Java Web Application

```text
Client
   │
   ▼
Servlet
   │
Business Logic
   │
   ▼
POJO
   │
Database
   │
   ▼
Servlet
   │
   ▼
JSP
   │
   ▼
HTML
   │
   ▼
Client
```

---

# Spring MVC

Spring MVC follows the same MVC design pattern, but instead of writing Servlets manually, Spring provides powerful components to simplify development.

```text
Client
   │
HTTP Request
   │
   ▼
Spring Controller (@Controller)
   │
   ▼
Model (POJO)
   │
   ▼
Database
   │
   ▼
Spring Controller
   │
   ▼
View (JSP / Thymeleaf)
   │
   ▼
HTML Response
   │
   ▼
Client
```

### Mapping in Spring MVC

| MVC Component | Spring MVC Implementation |
|--------------|----------------------------|
| Controller | `@Controller` Class |
| Model | POJO / Entity Class |
| View | JSP, Thymeleaf, FreeMarker, etc. |

---

# Why Spring MVC Instead of Servlets?

Writing applications directly using Servlets requires handling many low-level tasks manually.

Spring MVC simplifies development by providing:

- Annotation-based controllers (`@Controller`)
- Automatic request mapping
- Easy data binding
- Better separation of concerns
- Cleaner and more maintainable code
- Easy integration with the Spring ecosystem

---

# Key Points to Remember

- **MVC** stands for **Model-View-Controller**.
- **Model** contains data and business logic.
- **View** displays data to the user.
- **Controller** handles requests and coordinates between the Model and View.
- **POJO** is a simple Java class used to represent application data.
- **JSP** is a View technology used to generate dynamic web pages.
- A **JSP is internally converted into a Servlet**, because Tomcat executes Servlets.
- In traditional web applications, the **Servlet acts as the Controller**.
- In Spring MVC, classes annotated with **`@Controller`** replace Servlets as controllers.
- Spring MVC makes web application development simpler, cleaner, and easier to maintain.
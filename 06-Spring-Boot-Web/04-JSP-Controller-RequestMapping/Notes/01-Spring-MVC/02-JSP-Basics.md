# JSP (JavaServer Pages)

## What is JSP?

JSP (JavaServer Pages) is a **server-side view technology** used to create **dynamic web pages** in Java web applications.

It allows us to write **HTML along with Java code**. When a client requests a JSP page, the server executes the JSP and sends only the generated HTML to the browser.

> **Remember:** JSP is used only for the **View** layer in the MVC architecture.

---

## Why do we use JSP?

- To create dynamic web pages.
- To display data received from the Controller.
- To separate presentation from business logic.
- Easier to write HTML than writing HTML inside Servlets.

---

## Why should JSP be placed inside the `webapp` folder?

JSP files should be placed inside the **`src/main/webapp`** folder because it is the **Web Content Root** of a Java web application.

When the application is deployed, the web server (Tomcat) serves web resources such as **JSP, HTML, CSS, JavaScript, and images** from this directory.

Spring MVC looks for JSP pages inside the `webapp` folder (or its subfolders like `WEB-INF`) when rendering a View.

### Project Structure

```text
SpringBootWeb1
│
├── src
│   └── main
│       ├── java
│       │     └── com.barbighaiya.SpringBootWeb1
│       │            ├── SpringBootWeb1Application.java
│       │            └── HomeController.java
│       │
│       ├── resources
│       │     └── application.properties
│       │
│       └── webapp
│             ├── index.jsp
│             ├── about.jsp
│             └── WEB-INF
│
└── pom.xml
```

### Why not place JSP inside `resources`?

The `resources` folder is meant for configuration files like:

- `application.properties`
- `application.yml`
- Static configuration resources

Tomcat does **not** treat `resources` as the web content directory, so JSP files placed there won't be rendered as Views.

---

## How JSP Works

```text
             Browser
                │
      Request index.jsp
                │
                ▼
          Apache Tomcat
                │
     Converts JSP → Servlet
                │
     Compiles Servlet (.class)
                │
       Executes Servlet
                │
      Generates HTML Output
                │
                ▼
            Browser
```

---

## JSP Life Cycle

```text
JSP File (.jsp)
       │
       ▼
Translation
(JSP → Servlet)
       │
       ▼
Compilation
(Servlet → .class)
       │
       ▼
Loading
       │
       ▼
Initialization
       │
       ▼
Request Processing
       │
       ▼
Destroy
```

---

## Basic JSP Example

```jsp
<%@ page language="java" %>

<html>
<body>
    <h2>Hello World</h2>
</body>
</html>
```

---

## JSP Directive

A **JSP Directive** provides instructions to the JSP container about how the JSP page should be processed.

The most commonly used directive is the **page directive**.

```jsp
<%@ page language="java" %>
```

Example:

```jsp
<%@ page language="java"
         contentType="text/html"
         pageEncoding="UTF-8"%>
```

---

## Advantages of JSP

- Easy to write HTML.
- Supports Java code.
- Separates presentation from business logic.
- Reusable.
- Integrates easily with Spring MVC.
- Automatically converted into a Servlet by Tomcat.

---

## Disadvantages of JSP

- Mixing Java and HTML can make code difficult to maintain.
- Not recommended for modern Spring Boot applications.
- Mostly replaced by Thymeleaf in new Spring Boot projects.

---

# Interview Questions

### What is JSP?

JSP (JavaServer Pages) is a server-side view technology used to create dynamic web pages. It is converted into a Servlet before execution, and only the generated HTML is sent to the browser.

---

### Why is JSP called a server-side technology?

Because the JSP code executes on the server. The browser only receives the generated HTML.

---

### Why should JSP be placed inside the `webapp` folder?

Because `src/main/webapp` is the **Web Content Root** of a Java web application. Tomcat serves JSP files from this location, allowing Spring MVC to locate and render them as Views.

---

### Is JSP a programming language?

No.

JSP is a **view technology**, not a programming language. It combines HTML with Java to generate dynamic web pages.

---

### Can the browser see JSP code?

No.

The browser only receives the generated HTML. The JSP source code remains on the server.

---

## Quick Revision

- JSP = JavaServer Pages
- Server-side View technology
- Used to create dynamic web pages
- Runs on the server
- Converted into a Servlet before execution
- Browser receives only HTML
- Stored inside `src/main/webapp`
- Used as the View layer in Spring MVC
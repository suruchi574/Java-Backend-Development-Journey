# Sending Data to Controller using JSP Form

## What is Sending Data to the Controller?

In Spring MVC, the client sends data to the Controller using an HTML form.

The Controller receives the request, processes the data (business logic), and returns a response or another View.

---

## Project Structure

Create the following files inside the **webapp** folder.

```text
src
└── main
    └── webapp
        ├── index.jsp
        └── style.css
```

> **Why inside `webapp`?**
>
> The `webapp` folder is the **Web Content Root** of the application. Tomcat serves JSP pages, CSS, JavaScript, and images from this folder so that they can be accessed by the browser.

---

## Step 1: Create `index.jsp`

```jsp
<%@ page language="java" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>

<body>

    <h2>Suruchi's Calculator</h2>

    <form action="add">

        <label for="num1">Enter 1st Number</label>
        <input type="text" id="num1" name="num1"><br>

        <label for="num2">Enter 2nd Number</label>
        <input type="text" id="num2" name="num2"><br>

        <input type="submit" value="Submit">

    </form>

</body>
</html>
```

---

# Understanding the Form

## `<form>`

```html
<form action="add">
```

The `<form>` tag collects user input and sends it to the server.

Since no method is specified, the default HTTP method is **GET**.

---

## `action` Attribute

```html
<form action="add">
```

The **action** attribute specifies where the form data should be sent.

When the user clicks **Submit**, the browser sends the request to:

```text
/add
```

Example URL:

```text
http://localhost:8080/add?num1=23&num2=32
```

---

## `name` Attribute

```html
<input type="text" name="num1">
```

The **name** attribute is used to send data to the server.

Example:

```html
<input name="num1">
<input name="num2">
```

Browser sends:

```text
num1=23
num2=32
```

> **Without the `name` attribute, the Controller will not receive the data.**

---

## `id` Attribute

```html
<input id="num1">
```

The **id** uniquely identifies an HTML element.

It is mainly used by:

- Labels
- CSS
- JavaScript

It is **not sent** to the server.

---

## `<label>`

```html
<label for="num1">
```

The `for` attribute connects the label with the input field having the same `id`.

Clicking the label automatically focuses the corresponding input box.

---

# Adding CSS

Create a file named:

```text
style.css
```

inside the **webapp** folder.

Link it inside the JSP page.

```html
<link rel="stylesheet"
      type="text/css"
      href="style.css">
```

This applies custom styling to the page.

---

# Output (UI)

```text
                    Browser (localhost:8080)

            +--------------------------------------+
            |      Suruchi's Calculator            |
            |--------------------------------------|
            |                                      |
            |  Enter 1st Number                    |
            |  [                      ]            |
            |                                      |
            |  Enter 2nd Number                    |
            |  [                      ]            |
            |                                      |
            |         [   Submit   ]               |
            +--------------------------------------+
```

---

# Browser Request

Suppose the user enters:

```text
num1 = 23
num2 = 32
```

After clicking **Submit**, the browser sends:

```text
GET /add?num1=23&num2=32
```

Complete URL:

```text
http://localhost:8080/add?num1=23&num2=32
```

---

# Complete Request Flow

```text
                 User fills the form
                        │
                        ▼
                 index.jsp (View)
                        │
       <form action="add" method="GET">
                        │
                        ▼
      Browser Generates URL

http://localhost:8080/add?num1=23&num2=32
                        │
                        ▼
             DispatcherServlet
                        │
                        ▼
     Looks for @RequestMapping("/add")
                        │
              ┌─────────┴─────────┐
              │                   │
              ▼                   ▼
      Mapping Found         Mapping Not Found
              │                   │
              ▼                   ▼
      Calls Controller      404 Whitelabel Error
              │
              ▼
     Executes Business Logic
              │
              ▼
      Returns View/Response
              │
              ▼
         Browser Displays Result
```

---

# Why do we get a 404 Whitelabel Error?

The browser sends the request to:

```text
/add
```

But Spring Boot cannot find any Controller mapped to that URL.

Therefore, it returns:

```text
HTTP Status 404 - Not Found
```

Flow:

```text
index.jsp
    │
    ▼
<form action="add">
    │
    ▼
URL: /add?num1=23&num2=32
    │
    ▼
DispatcherServlet
    │
    ▼
No @RequestMapping("/add")
    │
    ▼
404 Whitelabel Error Page
```

> **Reason:** The form sends the request to `/add`, but no Controller method is mapped to `/add`, so Spring Boot returns a **404 Not Found** error.

---

# Next Step

Create a Controller to handle the request.

```java
@Controller
public class HomeController {

    @RequestMapping("/add")
    public String add() {

        return "result.jsp";

    }

}
```

Now Spring Boot knows how to process requests coming to `/add`.

---

# Interview Questions

### What is the purpose of the `<form>` tag?

The `<form>` tag collects user input and sends it to the server for processing.

---

### What is the purpose of the `action` attribute?

The `action` attribute specifies the URL where the form data should be sent.

Example:

```html
<form action="add">
```

The browser sends the request to:

```text
/add
```

---

### What is the difference between `id` and `name`?

| id | name |
|----|------|
| Identifies an HTML element uniquely | Used to send data to the server |
| Used by CSS, Labels and JavaScript | Used as the request parameter |
| Not sent to the server | Sent to the Controller |

---

### Why is the `name` attribute important?

The `name` attribute becomes the request parameter.

Without it, the Controller cannot receive the input values.

---

### Why do we get a 404 Whitelabel Error?

Because the browser sends the request to `/add`, but no Controller method is mapped to handle that URL.

---

### Why is `style.css` placed inside the `webapp` folder?

Because the `webapp` folder is the Web Content Root, and Tomcat serves static resources like CSS, JSP, JavaScript, and images from this location.

---

# Quick Revision

- Create `index.jsp` inside `src/main/webapp`.
- Create `style.css` inside `webapp`.
- Link CSS using the `<link>` tag.
- Use `<form action="add">` to send data.
- Default HTTP method is **GET**.
- `action` specifies the request URL.
- `name` sends data to the Controller.
- `id` identifies HTML elements.
- `label` improves usability by linking text with input fields.
- Clicking **Submit** generates:

```text
http://localhost:8080/add?num1=23&num2=32
```

- The request reaches the **DispatcherServlet**.
- DispatcherServlet searches for `@RequestMapping("/add")`.
- If no mapping is found, Spring Boot returns a **404 Whitelabel Error**.
- Creating a Controller with `@RequestMapping("/add")` handles the request successfully.
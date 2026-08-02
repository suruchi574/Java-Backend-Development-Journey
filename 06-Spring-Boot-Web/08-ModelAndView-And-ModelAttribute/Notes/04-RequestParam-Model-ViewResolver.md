# Spring MVC - @RequestParam, Model Object & ViewResolver

## 1. @RequestParam Annotation

### What is `@RequestParam`?

`@RequestParam` is used to retrieve data sent from the client (HTML form, URL parameters, etc.) and bind it to a method parameter in a Spring MVC controller.

**Package**

```java
import org.springframework.web.bind.annotation.RequestParam;
```

---

## Why do we use it?

Suppose a form sends the following request:

```
http://localhost:8080/add?num1=10&num2=20
```

Spring automatically maps `num1` and `num2` to the controller method parameters.

---

## Without @RequestParam

If the method parameter names are exactly the same as the request parameter names, Spring can bind them automatically.

```java
@RequestMapping("add")
public String add(int num1, int num2, Model model) {

    int result = num1 + num2;
    model.addAttribute("result", result);

    return "result";
}
```

Here,

- URL parameter `num1` → variable `num1`
- URL parameter `num2` → variable `num2`

So Spring maps them automatically.

---

## When is @RequestParam Required?

If your Java variable names are different from the request parameter names, use `@RequestParam`.

Example:

URL

```
http://localhost:8080/add?num1=10&num2=20
```

Controller

```java
@RequestMapping("add")
public String add(@RequestParam("num1") int num3,
                  @RequestParam("num2") int num4,
                  Model model) {

    int result = num3 + num4;
    model.addAttribute("result", result);

    return "result";
}
```

Mapping:

| URL Parameter | Java Variable |
|---------------|---------------|
| num1 | num3 |
| num2 | num4 |

---

## Syntax

```java
@RequestParam("requestParameterName")
```

Example

```java
@RequestParam("num1") int num3
```

---

## Summary

- Used to receive request parameters.
- Converts request parameters into Java variables.
- Required when variable names differ from request parameter names.
- Makes code more readable and explicit.

---

# 2. Model Object

## What is Model?

The `Model` is an interface provided by Spring MVC.

**Package**

```java
import org.springframework.ui.Model;
```

It is used to transfer data from the Controller to the View (JSP, Thymeleaf, etc.).

---

## Why do we use Model?

After performing some business logic inside the controller, we usually need to send the result to the JSP page.

Instead of storing data in `HttpSession`, Spring provides the `Model` object.

---

## Using HttpSession

```java
@RequestMapping("add")
public String add(int num1, int num2, HttpSession session) {

    int result = num1 + num2;

    session.setAttribute("result", result);

    return "result";
}
```

---

## Using Model (Recommended)

```java
@RequestMapping("add")
public String add(int num1, int num2, Model model) {

    int result = num1 + num2;

    model.addAttribute("result", result);

    return "result";
}
```

---

## addAttribute()

Syntax

```java
model.addAttribute("key", value);
```

Example

```java
model.addAttribute("result", result);
```

In JSP

```jsp
${result}
```

Spring sends the value stored with the key `"result"` to the JSP page.

---

## Advantages of Model

- Cleaner than using `HttpSession`
- Used for request-level data
- Automatically available in the View
- Recommended in Spring MVC

---

## Model Flow

```
Client
   │
   ▼
Controller
   │
   ▼
Business Logic
   │
   ▼
Model.addAttribute()
   │
   ▼
JSP / View
```

---

# 3. ViewResolver

## What is ViewResolver?

ViewResolver is a Spring MVC component that converts the logical view name returned by a controller into the actual physical view (JSP, Thymeleaf, etc.).

Instead of writing the full file path in every controller, we simply return the logical name, and ViewResolver automatically locates the correct file.

---

## Without ViewResolver

```java
@RequestMapping("/")
public String home() {

    return "index.jsp";
}
```

Problem:

- Controller becomes tightly coupled with JSP.
- If view technology changes, every controller must be updated.

---

## Using ViewResolver

Controller

```java
@RequestMapping("/")
public String home() {

    return "index";
}
```

Spring automatically converts

```
index
```

into

```
/view/index.jsp
```

using the prefix and suffix.

---

## Configure ViewResolver

In `application.properties`

```properties
spring.mvc.view.prefix=/view/
spring.mvc.view.suffix=.jsp
```
## Why Do We Use Prefix and Suffix?

Without ViewResolver

```java
return "/view/result.jsp";
```

With ViewResolver

```java
return "result";
```

Advantages:

- Cleaner controller code.
- No need to write the folder path repeatedly.
- No need to write `.jsp` every time.
- Easy to switch to another view technology (such as Thymeleaf) by changing only the configuration.

---

---

## Folder Structure

```
src
└── main
    ├── java
    ├── resources
    │      └── application.properties
    └── webapp
           └── view
                ├── index.jsp
                └── result.jsp
```

---

## Controller Example

```java
@RequestMapping("add")
public String add(@RequestParam("num1") int num3,
                  @RequestParam("num2") int num4,
                  Model model) {

    int result = num3 + num4;

    model.addAttribute("result", result);

    return "result";
}
```

Spring internally resolves

```
result
```

to

```
/view/result.jsp
```

---

## Advantages of ViewResolver

- No need to write `.jsp` in every controller.
- No need to write the folder path repeatedly.
- Easy to switch to another view technology (e.g., Thymeleaf).
- Cleaner and more maintainable code.

---

# Quick Revision

## @RequestParam

- Used to receive request parameters.
- Maps request data to Java variables.
- Needed when variable names differ.

```java
@RequestParam("num1") int num3
```

---

## Model

- Used to send data from Controller to View.
- Replaces `HttpSession` for request data.

```java
model.addAttribute("result", result);
```

---

## ViewResolver

Configured in `application.properties`

```properties
spring.mvc.view.prefix=/view/
spring.mvc.view.suffix=.jsp
```

Controller

```java
return "result";
```

Spring resolves it to

```
/view/result.jsp
```
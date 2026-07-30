# @RequestMapping in Spring MVC

## What is `@RequestMapping`?

`@RequestMapping` is a Spring MVC annotation used to **map a URL (request path) to a controller method**.

When a client sends an HTTP request, Spring checks all the `@RequestMapping` annotations and executes the method whose URL matches the request.

> **Remember:** `@RequestMapping` connects a URL with a Java method.

---

## Why do we use `@RequestMapping`?

- To map URLs to controller methods.
- To handle client requests.
- To decide which method should execute for a particular URL.
- To return the appropriate View or Response.

---

## Basic Syntax

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

The `home()` method is executed.

---

## How `@RequestMapping` Works

```text
            Browser
               │
     GET http://localhost:8080/
               │
               ▼
      DispatcherServlet
               │
 Searches for Matching URL
               │
               ▼
 @RequestMapping("/")
               │
               ▼
      home() Method
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
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index.jsp";
    }

}
```

### Explanation

- `@Controller` marks the class as a Spring MVC Controller.
- `@RequestMapping("/")` maps the home URL.
- When the user requests `/`, Spring executes the `home()` method.
- The method returns `index.jsp`, which is displayed to the user.

---

## Multiple URL Mappings

```java
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "index.jsp";
    }

    @RequestMapping("/about")
    public String about() {
        return "about.jsp";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact.jsp";
    }
}
```

| URL | Method Called | View Returned |
|------|---------------|---------------|
| `/` | `home()` | `index.jsp` |
| `/about` | `about()` | `about.jsp` |
| `/contact` | `contact()` | `contact.jsp` |

---

## Class-Level `@RequestMapping`

`@RequestMapping` can also be placed at the class level to define a common URL prefix.

```java
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/home")
    public String home() {
        return "home.jsp";
    }

}
```

URL:

```
http://localhost:8080/user/home
```

---

## HTTP Methods with `@RequestMapping`

By default, `@RequestMapping` can handle **all HTTP methods**.

You can specify a particular method like this:

```java
@RequestMapping(value="/login", method=RequestMethod.GET)
```

```java
@RequestMapping(value="/login", method=RequestMethod.POST)
```

---

## Modern Alternative

Instead of writing:

```java
@RequestMapping(value="/home", method=RequestMethod.GET)
```

Spring provides shorter annotations:

```java
@GetMapping("/home")
```

Other mapping annotations:

- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@DeleteMapping`
- `@PatchMapping`

These are more readable and are preferred in modern Spring Boot applications.

---

## `@RequestMapping` Flow in Spring MVC

```text
Browser
   │
HTTP Request
   │
   ▼
DispatcherServlet
   │
Find Matching @RequestMapping
   │
   ▼
Controller Method
   │
Business Logic (Service)
   │
   ▼
Return View Name
   │
   ▼
View Resolver
   │
   ▼
JSP Page
   │
   ▼
HTML Response
   │
   ▼
Browser
```

---

## Interview Questions

### What is `@RequestMapping`?

`@RequestMapping` is a Spring MVC annotation used to map a URL to a controller method. When a request matches the specified URL, Spring executes the corresponding method.

---

### Can `@RequestMapping` be used at both class and method level?

Yes.

- At the **class level**, it defines a common URL prefix.
- At the **method level**, it maps a specific URL to that method.

---

### What is the difference between `@RequestMapping` and `@GetMapping`?

| `@RequestMapping` | `@GetMapping` |
|-------------------|---------------|
| Can handle all HTTP methods | Handles only GET requests |
| More generic | More specific and readable |

---

### What happens when a URL is requested?

1. The request reaches the **DispatcherServlet**.
2. Spring searches for a matching `@RequestMapping`.
3. The corresponding controller method is executed.
4. The method returns a View or Response.
5. Spring sends the response back to the browser.

---

## Quick Revision

- `@RequestMapping` maps a URL to a controller method.
- Used to handle incoming HTTP requests.
- Can be applied at both **class** and **method** levels.
- Supports all HTTP methods (GET, POST, PUT, DELETE, etc.).
- `@GetMapping`, `@PostMapping`, etc., are specialized versions of `@RequestMapping`.
- DispatcherServlet uses `@RequestMapping` to locate and execute the correct controller method.
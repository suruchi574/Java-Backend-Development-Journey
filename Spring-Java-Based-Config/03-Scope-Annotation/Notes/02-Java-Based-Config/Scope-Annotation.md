# 09. Scope Annotation

## What is Bean Scope?

Bean Scope defines **how many objects (beans)** Spring creates for a particular bean definition.

By default, Spring creates only **one bean** for the entire IoC Container.

The `@Scope` annotation is used to change this default behavior.

---

# Default Bean Scope

If no scope is specified, Spring uses **Singleton Scope**.

Example:

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}
```

Equivalent to:

```java
@Bean
@Scope("singleton")
public Desktop desktop() {
    return new Desktop();
}
```

Spring creates only **one object**, regardless of how many times `getBean()` is called.

---

# Prototype Scope

Prototype Scope tells Spring to create a **new object every time** the bean is requested.

Example:

```java
@Bean
@Scope("prototype")
public Desktop desktop() {
    return new Desktop();
}
```

Whenever `getBean()` is called, Spring creates a new `Desktop` object.

---

# Using Singleton Scope

```java
Desktop dt = context.getBean("desktop", Desktop.class);
dt.compile();

Desktop dt1 = context.getBean("desktop", Desktop.class);
dt1.compile();
```

Output:

```
Desktop Object Created
Compiling from Desktop
Compiling from Desktop
```

### Explanation

- Spring creates the `Desktop` object only once.
- Both `dt` and `dt1` refer to the same object.

Flow:

```
getBean()
      │
      ▼
Desktop Object
      ▲
      │
getBean()

dt  ───────┐
           │
dt1 ───────┘
```

---

# Using Prototype Scope

```java
@Bean
@Scope("prototype")
public Desktop desktop() {
    return new Desktop();
}
```

```java
Desktop dt = context.getBean("desktop", Desktop.class);
dt.compile();

Desktop dt1 = context.getBean("desktop", Desktop.class);
dt1.compile();
```

Output:

```
Desktop Object Created
Compiling from Desktop

Desktop Object Created
Compiling from Desktop
```

### Explanation

- Spring creates a new object for every `getBean()` call.
- `dt` and `dt1` refer to different objects.

Flow:

```
getBean()
      │
      ▼
Desktop Object 1
      │
      ▼
     dt

getBean()
      │
      ▼
Desktop Object 2
      │
      ▼
     dt1
```

---

# @Scope Annotation

Syntax:

```java
@Bean
@Scope("prototype")
public Desktop desktop() {
    return new Desktop();
}
```

### Purpose

- Changes the default bean scope.
- Controls how many bean objects Spring creates.

---

# Supported Scope Values

| Scope | Description |
|--------|-------------|
| `singleton` | Creates only one bean for the IoC Container (Default). |
| `prototype` | Creates a new bean every time `getBean()` is called. |

---

# Singleton vs Prototype

| Singleton Scope | Prototype Scope |
|-----------------|-----------------|
| Default scope | Must be specified using `@Scope("prototype")` |
| One object is created | A new object is created for every request |
| Same object is returned every time | Different object is returned every time |
| Memory efficient | Uses more memory because multiple objects are created |

---

# Internal Working

### Singleton

```
IoC Container
      │
      ▼
Creates One Desktop Bean
      │
      ▼
getBean()
      │
      ▼
Returns Same Object
```

---

### Prototype

```
IoC Container
      │
      ▼
getBean()
      │
      ▼
Creates New Desktop Object

getBean()
      │
      ▼
Creates Another Desktop Object
```

---

# Example Configuration

```java
@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Desktop desktop() {
        return new Desktop();
    }

}
```

---

# Example Program

```java
Desktop dt = context.getBean("desktop", Desktop.class);
dt.compile();

Desktop dt1 = context.getBean("desktop", Desktop.class);
dt1.compile();
```

---

# Key Points

- Bean Scope determines how many bean objects Spring creates.
- The default scope in Spring is **Singleton**.
- `@Scope` is used to change the default bean scope.
- `@Scope("singleton")` creates only one bean.
- `@Scope("prototype")` creates a new bean for every `getBean()` call.
- In Singleton Scope, all references point to the same object.
- In Prototype Scope, each reference points to a different object.
- Singleton Scope is memory efficient because only one object is created.
- Prototype Scope is useful when each request requires a separate object.
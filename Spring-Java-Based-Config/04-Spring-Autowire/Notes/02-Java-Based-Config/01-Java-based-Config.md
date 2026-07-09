# 07. Java-Based Configuration in Spring

## What is Java-Based Configuration?

Java-Based Configuration is a way of configuring the Spring IoC Container using Java classes and annotations instead of XML files.

Instead of writing bean definitions in `spring.xml`, we create a configuration class and define beans using annotations.

---

# Required Annotations

## 1. @Configuration

```java
@Configuration
public class AppConfig {

}
```

### Purpose

- Marks a class as a **Configuration Class**.
- Tells Spring that this class contains bean definitions.
- Spring reads this class when the IoC Container starts.

---

## 2. @Bean

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}
```

### Purpose

- Tells Spring to create and manage the object returned by this method.
- The returned object is stored as a Bean inside the IoC Container.
- By default, the bean name is the same as the method name.

In this example:

```java
@Bean
public Desktop desktop()
```

Bean Name:

```
desktop
```

Spring internally performs:

```java
Desktop obj = new Desktop();
```

and stores it inside the IoC Container.

---

# Configuration Class

```java
@Configuration
public class AppConfig {

    @Bean
    public Desktop desktop() {
        return new Desktop();
    }

}
```

---

# Creating the IoC Container

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

## Explanation

`AnnotationConfigApplicationContext` is a concrete class that creates the Spring IoC Container using Java Configuration.

`AppConfig.class` is passed to the constructor so Spring knows which configuration class to read.

---

# What Happens Internally?

When the following statement executes:

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

Spring performs these steps:

1. Reads `AppConfig.class`.
2. Finds the `@Configuration` annotation.
3. Searches for methods annotated with `@Bean`.
4. Calls the `desktop()` method.
5. Creates the `Desktop` object.
6. Stores the object inside the Spring IoC Container.

Flow Diagram:

```
AppConfig.class
        │
        ▼
@Configuration
        │
        ▼
@Bean Method
        │
        ▼
desktop()
        │
        ▼
new Desktop()
        │
        ▼
IoC Container
```

---

# Retrieving the Bean

```java
Desktop dt = context.getBean(Desktop.class);
```

## What Happens Internally?

Spring:

1. Searches the IoC Container.
2. Looks for a bean of type `Desktop`.
3. Finds the bean created by the `desktop()` method.
4. Returns the bean.
5. Stores the reference in `dt`.

Flow Diagram:

```
IoC Container
-----------------------
Desktop Bean
-----------------------
        │
        │ getBean(Desktop.class)
        ▼
Desktop dt
```

---

# Calling the Method

```java
dt.compile();
```

Output:

```
Compiling from Desktop
```

---

# Complete Flow of Java-Based Configuration

```
@Configuration
        │
        ▼
@Bean Method
        │
        ▼
Creates Desktop Object
        │
        ▼
Stores Object in IoC Container
        │
        ▼
context.getBean(Desktop.class)
        │
        ▼
Returns Desktop Bean
        │
        ▼
dt.compile()
```

---

# Output

```
Desktop Object Created
Compiling from Desktop
```

---

# XML Configuration vs Java-Based Configuration

| XML Configuration | Java-Based Configuration |
|-------------------|--------------------------|
| Uses `spring.xml` | Uses Java Configuration Class |
| Uses `<bean>` tag | Uses `@Bean` annotation |
| Uses `ClassPathXmlApplicationContext` | Uses `AnnotationConfigApplicationContext` |
| Configuration written in XML | Configuration written in Java |

---

# Important Interview Questions

### What is `AnnotationConfigApplicationContext`?

- It is a concrete class.
- It creates the Spring IoC Container using Java-Based Configuration.
- It reads classes annotated with `@Configuration`.

---

### What is `@Configuration`?

- It marks a class as a Spring Configuration Class.
- Spring reads this class during container initialization.

---

### What is `@Bean`?

- It tells Spring to create and manage the returned object as a bean.
- The bean is stored in the Spring IoC Container.

---

### Why do we use `context.getBean(Desktop.class)`?

It retrieves the `Desktop` bean from the Spring IoC Container by searching its type.

---

# Key Points

- Java-Based Configuration replaces XML configuration.
- `@Configuration` marks a configuration class.
- `@Bean` creates and registers beans.
- `AnnotationConfigApplicationContext` creates the IoC Container using Java configuration.
- `AppConfig.class` tells Spring which configuration class to load.
- `getBean(Class)` retrieves a bean by its type.
- By default, the bean name is the same as the `@Bean` method name.
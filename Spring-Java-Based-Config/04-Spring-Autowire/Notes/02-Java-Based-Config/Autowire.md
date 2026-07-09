# 10. Autowiring in Java-Based Configuration

## What is Autowiring?

Autowiring is a feature of Spring that automatically injects the required dependency into a bean.

Instead of creating dependent objects manually using `new`, Spring searches the IoC Container for a suitable bean and injects it automatically.

---

# Project Example

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}

@Bean
public Alien alien(Computer comp) {
    Alien obj = new Alien();
    obj.setAge(23);
    obj.setComp(comp);
    return obj;
}
```

Here,

- `Alien` depends on `Computer`.
- `Desktop` implements the `Computer` interface.

---

# How Autowiring Works

When the IoC Container starts, Spring performs the following steps:

### Step 1

Reads the configuration class.

```java
@Configuration
public class AppConfig
```

---

### Step 2

Finds all methods annotated with `@Bean`.

```
desktop()

alien(Computer comp)
```

---

### Step 3

Creates the `Desktop` bean.

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}
```

Spring internally executes:

```java
Desktop obj = new Desktop();
```

and stores it in the IoC Container.

---

### Step 4

Now Spring wants to create the `Alien` bean.

```java
@Bean
public Alien alien(Computer comp)
```

Spring notices that the method requires a `Computer` object.

---

### Step 5

Spring searches the IoC Container for a bean of type `Computer`.

```
Computer
      ▲
      │
Desktop Bean
```

Since `Desktop` implements `Computer`, Spring injects the `Desktop` bean automatically.

This is called **Autowiring by Type**.

---

### Step 6

Spring executes the bean method.

```java
Alien obj = new Alien();

obj.setAge(23);

obj.setComp(comp);

return obj;
```

Here,

- `comp` already contains the `Desktop` bean.
- `setComp(comp)` injects the dependency into the `Alien` object.

---

### Step 7

Spring stores the fully initialized `Alien` object in the IoC Container.

```
IoC Container

Desktop Bean

Alien Bean
```

---

# Bean Retrieval

```java
Alien obj = context.getBean(Alien.class);
```

Spring returns the `Alien` bean from the IoC Container.

---

# Calling the Method

```java
obj.code();
```

Inside the `code()` method,

```java
comp.compile();
```

Since `comp` contains the `Desktop` bean,

Spring executes

```java
Desktop.compile();
```

Output

```
Coding....
Compiling from Desktop
```

---

# Why Do We Create the Alien Object Manually?

```java
@Bean
public Alien alien(Computer comp) {

    Alien obj = new Alien();

    obj.setAge(23);

    obj.setComp(comp);

    return obj;
}
```

The `@Bean` method is responsible for creating the bean.

Spring calls this method, but **the developer defines how the object should be created and initialized**.

Therefore we create the object using

```java
new Alien()
```

configure it, and return it to Spring.

Spring then stores the returned object as a bean.

---

# Why Isn't the Computer Object Created Manually?

We do not write

```java
Computer comp = new Desktop();
```

because Spring already manages the `Desktop` bean.

Spring automatically injects the existing bean into the method parameter.

```java
public Alien alien(Computer comp)
```

Here,

```text
Computer comp
```

contains the `Desktop` bean supplied by Spring.

---

# Internal Flow

```
Application Starts
        │
        ▼
Reads AppConfig
        │
        ▼
Finds @Bean Methods
        │
        ├───────────────┐
        ▼               ▼
desktop()         alien(Computer)
        │               ▲
        ▼               │
Creates Desktop Bean────┘
        │
        ▼
Injects Desktop Bean
into Computer parameter
        │
        ▼
Creates Alien Object
        │
        ▼
setComp(Desktop Bean)
        │
        ▼
Stores Alien Bean
        │
        ▼
context.getBean(Alien.class)
        │
        ▼
obj.code()
        │
        ▼
Desktop.compile()
```

---

# Key Points

- Autowiring automatically injects dependencies into a bean.
- Spring performs autowiring using the beans available in the IoC Container.
- Method parameters of a `@Bean` method are automatically autowired by Spring.
- `Computer` is an interface, while `Desktop` is its implementation.
- Spring injects the `Desktop` bean because it matches the required type.
- The `Alien` object is created manually because the `@Bean` method defines how the bean should be created.
- Spring manages the lifecycle of both `Desktop` and `Alien` after they are returned from the `@Bean` methods.
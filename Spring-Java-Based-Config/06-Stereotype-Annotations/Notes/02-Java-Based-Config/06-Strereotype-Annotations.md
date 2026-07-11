# Spring Stereotype Annotations

## What are Stereotype Annotations?

Stereotype Annotations tell Spring that a class should be managed by the **Spring IoC Container**.

Spring automatically creates and manages the objects of these classes.

Some commonly used stereotype annotations are:

- `@Component`
- `@Service`
- `@Repository`
- `@Controller`
- `@RestController`

> **Note:** In this project, we are using `@Component`.

---

# @Component

`@Component` marks a class as a **Spring Bean**.

When Spring scans the package, it creates an object of every class marked with `@Component` and stores it in the IoC Container.

### Example

```java
@Component
public class Laptop implements Computer {

}

@Component
public class Desktop implements Computer {

}
```

**Result:** Spring automatically creates objects of `Laptop` and `Desktop`.

> **Note:** `@Component` should be used on classes, not interfaces.

---

# @ComponentScan

`@ComponentScan` tells Spring **which package to scan** for classes marked with `@Component`.

### Example

```java
@Configuration
@ComponentScan("com.barbighaiya.Spring_First_Project")
public class AppConfig {

}
```

**Result:** Spring scans the package and creates beans for every class marked with `@Component`.

Without `@ComponentScan`, Spring will not know where to find the components.

---

# @Autowired

`@Autowired` tells Spring to automatically inject the required dependency.

### Example

```java
@Component
public class Alien {

    @Autowired
    private Computer comp;

}
```

**Result:** Spring searches the IoC Container for a bean of type `Computer` and injects it automatically.

---

# Why do we need @Primary and @Qualifier?

When multiple beans of the **same type** are present in the Spring IoC Container, Spring cannot decide which bean to inject.

### Example

```java
@Component
public class Laptop implements Computer {

}

@Component
public class Desktop implements Computer {

}
```

Spring throws:

```
No qualifying bean of type 'Computer'
expected single matching bean but found 2
```

To solve this ambiguity, Spring provides **@Primary** and **@Qualifier**.

---

# @Primary

`@Primary` marks one bean as the **default bean**.

### Example

```java
@Component
@Primary
public class Desktop implements Computer {

}
```

**Result:** Spring injects the `Desktop` bean by default.

---

# @Qualifier

`@Qualifier` tells Spring exactly which bean to inject.

### Example

```java
@Autowired
@Qualifier("laptop")
public void setComp(Computer comp) {
    this.comp = comp;
}
```

**Result:** Spring injects the `Laptop` bean.

> **Note:** If both `@Primary` and `@Qualifier` are used, **`@Qualifier` has higher priority**.

---

# @Value

`@Value` injects a fixed value into a field.

### Example

```java
@Component
public class Alien {

    @Value("23")
    private int age;

}
```

**Result:** Spring automatically sets

```
age = 23
```

No need to call

```java
obj.setAge(23);
```

---

# @Scope

By default, every Spring Bean has **Singleton** scope.

### Singleton Example

```java
@Component
public class Laptop {

}
```

**Result:** Every call to `getBean()` returns the same object.

### Prototype Example

```java
@Component
@Scope("prototype")
public class Laptop {

}
```

**Result:** Every call to `getBean()` returns a new object.

> **Note:** `@Scope` can be used on a `@Component` class or on a `@Bean` method.

---

# Bean Creation Flow

```text
Application Starts
        │
        ▼
Read AppConfig
        │
        ▼
Find @Configuration
        │
        ▼
Find @ComponentScan
        │
        ▼
Scan Package
        │
        ▼
Find @Component Classes
        │
        ▼
Create Singleton Beans
        │
        ▼
Inject Dependencies
        │
        ▼
IoC Container Ready
```

---

# Output Flow

```
Alien Object Created
Laptop Object Created
Desktop Object Created
23
Coding....
Compiling from Laptop
```

### Why?

- Spring starts the IoC Container.
- Reads `AppConfig`.
- Finds `@Configuration`.
- Finds `@ComponentScan`.
- Scans the specified package.
- Creates Singleton beans.
- Injects `@Value`.
- Performs dependency injection using `@Autowired`.
- Resolves conflicts using `@Primary` or `@Qualifier`.
- Returns the required bean when `getBean()` is called.

---

# Key Points

- `@Component` → Marks a class as a Spring Bean.
- `@ComponentScan` → Tells Spring which package to scan.
- `@Autowired` → Automatically injects dependencies.
- `@Primary` → Makes one bean the default choice.
- `@Qualifier` → Injects a specific bean.
- `@Value` → Injects a fixed value into a field.
- `@Scope("prototype")` → Creates a new object every time the bean is requested.
- Singleton beans are created when the IoC Container starts.
- `@Component` should not be used on interfaces.
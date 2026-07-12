# Spring Boot Basics

## What is Spring Boot?

Spring Boot is built on top of the Spring Framework.

It makes Spring development easier by reducing configuration and providing many things automatically.

Instead of writing lots of XML files or configuration code, Spring Boot does most of the setup for us.

---

## Why Spring Boot?

- Less configuration
- Faster development
- Auto Configuration
- Embedded Tomcat Server
- Standalone application
- Easy dependency management using Starter Projects

---

# @SpringBootApplication

`@SpringBootApplication` is the main annotation of every Spring Boot application.

It tells Spring Boot to start the application and configure everything required.

```java
@SpringBootApplication
public class SpringBootBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicsApplication.class, args);
    }

}
```

This single annotation combines three important annotations:

- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

---

## @Configuration

Marks the class as a configuration class.

It tells Spring that this class can contain bean definitions.

Example:

```java
@Configuration
public class AppConfig {

}
```

---

## @EnableAutoConfiguration

This is one of the best features of Spring Boot.

It automatically configures the application based on the dependencies present in the project.

Example:

If we add the **Spring Web** dependency, Spring Boot automatically configures:

- Embedded Tomcat Server
- Spring MVC
- DispatcherServlet

No manual configuration is needed.

---

## @ComponentScan

It tells Spring to scan the current package and all its sub-packages.

Whenever Spring finds classes annotated with:

- `@Component`
- `@Service`
- `@Repository`
- `@Controller`

it automatically creates their objects (beans) and stores them inside the IoC Container.

Example:

```java
@Component
public class Alien {

}
```

Spring automatically creates the `Alien` object.

---

# SpringApplication.run()

```java
ApplicationContext context =
        SpringApplication.run(SpringBootBasicsApplication.class, args);
```

This method starts the Spring Boot application.

It performs several tasks:

- Starts the Spring application.
- Creates the IoC Container.
- Creates all required beans.
- Performs Dependency Injection.
- Returns the `ApplicationContext`.

---

# ApplicationContext

`ApplicationContext` is the Spring IoC Container.

It is responsible for:

- Creating beans
- Managing beans
- Injecting dependencies

To get a bean:

```java
Alien alien = context.getBean(Alien.class);
```

---

# @Component

`@Component` tells Spring that this class should be managed as a Bean.

```java
@Component
public class Laptop implements Computer {

}
```

Spring automatically creates its object.

---

# @Autowired

`@Autowired` is used for Dependency Injection.

Spring automatically injects the required object.

Example:

```java
@Autowired
private Computer comp;
```

or

```java
@Autowired
public void setComp(Computer comp) {
    this.comp = comp;
}
```

No need to create the object using `new`.

---

# @Qualifier

When multiple beans of the same type are available, Spring gets confused about which one to inject.

`@Qualifier` tells Spring exactly which bean to use.

```java
@Autowired
@Qualifier("laptop")
private Computer comp;
```

Now Spring injects the `Laptop` bean.

---

# @Primary

`@Primary` is used to set the default bean.

If multiple beans of the same type exist and `@Qualifier` is not used, Spring injects the bean marked with `@Primary`.

```java
@Component
@Primary
public class Desktop implements Computer {

}
```

---

# @Value

`@Value` is used to inject simple values into variables.

```java
@Value("23")
private int age;
```

Output:

```
23
```

---

# Interface Injection

Instead of depending on a specific class, we depend on an interface.

```java
public interface Computer {
    void compile();
}
```

Implementations:

- Laptop
- Desktop

This makes the code flexible because we can switch between implementations without changing the `Alien` class.

---

# Execution Flow

```
main()
      │
      ▼
SpringApplication.run()
      │
      ▼
IoC Container Created
      │
      ▼
Component Scan
      │
      ▼
Beans Created
      │
      ▼
Dependencies Injected
      │
      ▼
ApplicationContext Returned
      │
      ▼
getBean(Alien.class)
      │
      ▼
alien.code()
      │
      ▼
compile()
```

---

# Output

```
Coding
Compiling from Laptop
23
```

---

# What I Learned

- Introduction to Spring Boot
- Why Spring Boot is used
- `@SpringBootApplication`
- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`
- `SpringApplication.run()`
- `ApplicationContext`
- `@Component`
- `@Autowired`
- `@Qualifier`
- `@Primary`
- `@Value`
- Interface-based Dependency Injection
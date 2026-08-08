# Spring Boot Internal Working

## 1. Introduction

Before understanding Spring Boot internal working, we need to understand
one basic problem:

> **Who creates and manages the objects used by our application?**

In a normal Java application, we create objects ourselves using the
`new` keyword.

``` java
Car car = new Car();
```

In Spring, we can give this responsibility to the Spring framework.

``` text
Normal Java
Developer → creates and manages objects

Spring
Spring → creates and manages objects
```

This change in responsibility is called **Inversion of Control (IoC)**.

------------------------------------------------------------------------

# 2. Inversion of Control (IoC)

## Definition

**Inversion of Control (IoC)** means giving the control of object
creation and object management to the Spring framework instead of
creating and managing those objects ourselves.

## Before Spring

``` java
Car car = new Car();
```

Here, we are responsible for:

-   Creating the object
-   Deciding when to create it
-   Managing the object
-   Creating its dependencies

## With Spring

We tell Spring which classes should be managed, and Spring creates and
manages their objects.

``` text
Developer
   ↓
Defines classes and tells Spring what to manage
   ↓
Spring Container
   ↓
Creates and manages objects
```

### Simple Example

Think of the Spring IoC Container as a box:

``` text
          Spring IoC Container
       ┌────────────────────────┐
       │                        │
       │   MyCar Object         │
       │   MyBike Object        │
       │   Service Object       │
       │   Repository Object    │
       │                        │
       └────────────────────────┘
```

Whenever our application needs a Spring-managed object, Spring can
provide it.

### Remember

> **IoC = Control of object creation and management is given to
> Spring.**

------------------------------------------------------------------------

# 3. IoC Container

## Definition

The **IoC Container** is the part of Spring responsible for creating,
configuring, storing, and managing Spring objects.

The objects managed by the Spring container are called **Spring Beans**.

## Why do we need an IoC Container?

Without Spring:

``` java
MyBike bike = new MyBike();
MyCar car = new MyCar();
```

The developer has to create and manage every object.

With Spring:

``` text
Spring Container
      ↓
Creates MyBike Bean
      ↓
Creates MyCar Bean
      ↓
Manages both objects
      ↓
Provides them when required
```

This reduces manual object creation and makes it easier to manage
dependencies between classes.

------------------------------------------------------------------------

# 4. Spring Bean

## Definition

A **Spring Bean** is an object that is created and managed by the Spring
IoC Container.

For example:

``` java
@Component
public class MyBike {

}
```

Because `MyBike` is marked with `@Component`, Spring can detect the
class during component scanning and create a bean for it.

``` text
MyBike class
     ↓
@Component
     ↓
Component Scan
     ↓
Spring creates MyBike object
     ↓
MyBike object becomes a Spring Bean
```

## Important Difference

This:

``` java
MyBike bike = new MyBike();
```

creates a normal Java object.

Whereas:

``` java
@Component
public class MyBike {
}
```

allows Spring to create and manage an object of `MyBike` as a Spring
Bean.

> **Object = general Java object**
>
> **Spring Bean = object managed by Spring**

------------------------------------------------------------------------

# 5. ApplicationContext

## Definition

`ApplicationContext` is a Spring IoC container interface used to manage
and provide Spring Beans.

In a Spring Boot application, the ApplicationContext is created and
initialized for us when the application starts.

Conceptually:

``` text
Spring Boot Application
        ↓
ApplicationContext
        ↓
IoC Container
        ↓
Spring Beans
```

## Why is ApplicationContext required?

Our application may contain many objects:

``` text
MyCar
MyBike
UserService
UserRepository
Database configuration
Controller
```

Spring needs a container to:

-   Create these objects
-   Manage their lifecycle
-   Store the Beans
-   Find required Beans
-   Inject dependencies
-   Apply configuration

The `ApplicationContext` provides this container functionality.

## In simple words

> **ApplicationContext is the environment/container through which Spring
> manages application Beans.**

------------------------------------------------------------------------

# 6. Starting a Spring Boot Application

A typical Spring Boot application has a main class:

``` java
package com.barbighaiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyFirstSpringBootProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            MyFirstSpringBootProjectApplication.class,
            args
        );

        System.out.println("Hello From Spring Boot!");
    }
}
```

There are two important things here:

``` java
@SpringBootApplication
```

and:

``` java
SpringApplication.run(
    MyFirstSpringBootProjectApplication.class,
    args
);
```

First, let's understand `@SpringBootApplication`.

------------------------------------------------------------------------

# 7. @SpringBootApplication

## Definition

`@SpringBootApplication` is the main annotation used on the main class
of a Spring Boot application.

It tells Spring Boot that this class is the starting point of the
application and enables important Spring Boot features.

Example:

``` java
@SpringBootApplication
public class MyFirstSpringBootProjectApplication {

}
```

## Why is @SpringBootApplication required?

A Spring Boot application needs to know:

1.  Which class should start the application?
2.  Which classes should Spring scan?
3.  Which automatic configurations should be enabled?
4.  Which configuration should be used to create the application
    context?

`@SpringBootApplication` helps provide these features.

------------------------------------------------------------------------

# 8. What is Inside @SpringBootApplication?

`@SpringBootApplication` is a combination of three important
annotations:

``` text
@SpringBootApplication
        │
        ├── @Configuration
        │
        ├── @EnableAutoConfiguration
        │
        └── @ComponentScan
```

So, instead of writing all three separately:

``` java
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MyFirstSpringBootProjectApplication {

}
```

we normally use:

``` java
@SpringBootApplication
public class MyFirstSpringBootProjectApplication {

}
```

This makes Spring Boot configuration simpler.

------------------------------------------------------------------------

# 9. @Configuration

## Definition

`@Configuration` tells Spring that a class can contain configuration
information and Bean definitions.

Example:

``` java
@Configuration
public class AppConfig {

}
```

## Why is @Configuration required?

Sometimes we want to explicitly tell Spring how an object should be
created.

For example:

``` java
@Configuration
public class AppConfig {

    @Bean
    public Car car() {
        return new Car();
    }
}
```

Here, `@Configuration` identifies `AppConfig` as a configuration class.

The `@Bean` method tells Spring to manage the returned object as a Bean.

## Important

`@Configuration` does not mean that every method in the class
automatically becomes a Bean.

We normally use:

``` java
@Bean
```

to explicitly define a Bean.

------------------------------------------------------------------------

# 10. @Bean

## Definition

`@Bean` is used on a method to tell Spring that the object returned by
that method should be managed as a Spring Bean.

Example:

``` java
@Configuration
public class AppConfig {

    @Bean
    public Car car() {
        return new Car();
    }
}
```

The flow is:

``` text
@Bean method
    ↓
car() is called by Spring
    ↓
new Car() is created
    ↓
Spring manages the returned Car object
    ↓
Car becomes a Spring Bean
```

## Two Common Ways to Create Beans

### Method 1: @Component

``` java
@Component
public class MyBike {

}
```

### Method 2: @Bean

``` java
@Configuration
public class AppConfig {

    @Bean
    public MyBike myBike() {
        return new MyBike();
    }
}
```

For now, remember:

> `@Component` → Spring discovers the class during component scanning.

> `@Bean` → We explicitly define how Spring should create the Bean.

------------------------------------------------------------------------

# 11. @Component

## Definition

`@Component` is an annotation used to mark a class as a Spring-managed
component.

Example:

``` java
@Component
public class MyBike {

    public String fun() {
        return "Riding...";
    }
}
```

## Why is @Component required?

Spring needs to know which classes should be managed by the IoC
Container.

When Spring finds:

``` java
@Component
```

during component scanning, it can create and manage an object of that
class.

## Flow

``` text
@Component
     ↓
Component Scan finds class
     ↓
Spring creates object
     ↓
Object becomes Bean
     ↓
Bean is managed by IoC Container
```

------------------------------------------------------------------------

# 12. Component Scanning

## Definition

**Component Scanning** is the process through which Spring searches
packages for classes that are marked as Spring components.

Common component stereotypes include:

``` java
@Component
@Service
@Repository
@Controller
@RestController
```

Spring detects these classes and registers suitable objects as Beans.

------------------------------------------------------------------------

# 13. @ComponentScan

## Definition

`@ComponentScan` tells Spring which package should be scanned for Spring
components.

Example:

``` java
@ComponentScan
```

When used without an explicit package, Spring Boot normally uses the
package of the main application class as the starting point for
scanning.

------------------------------------------------------------------------

# 14. Base Package

Suppose our main class is:

``` java
package com.barbighaiya;
```

Then:

``` text
com.barbighaiya
```

is the base package used for component scanning by default.

Example:

``` text
src/main/java
└── com.barbighaiya
    │
    ├── MyFirstSpringBootProjectApplication.java
    ├── MyCar.java
    ├── MyBike.java
    │
    └── service
        └── MyService.java
```

Spring can scan:

``` text
com.barbighaiya
```

and its subpackages.

------------------------------------------------------------------------

# 15. Why Package Structure Matters

Suppose the main class is:

``` java
package com.barbighaiya;
```

and we create:

``` java
package newpackage;
```

with:

``` java
@Component
public class MyBike {

}
```

`newpackage` is not a subpackage of `com.barbighaiya`.

Therefore, the default component scan will not normally find it.

As a result, Spring will not automatically register `MyBike` as a Bean.

If another class has:

``` java
@Autowired
private MyBike bike;
```

Spring may fail because it cannot find a suitable `MyBike` Bean.

## Recommended Project Structure

Keep the main application class in the root package:

``` text
com.barbighaiya
│
├── MyFirstSpringBootProjectApplication.java
│
├── controller
│   └── MyCar.java
│
├── service
│   └── MyService.java
│
├── repository
│   └── MyRepository.java
│
└── model
    └── Student.java
```

This allows component scanning to cover the application packages
naturally.

------------------------------------------------------------------------

# 16. @EnableAutoConfiguration

## Definition

`@EnableAutoConfiguration` enables Spring Boot's automatic configuration
mechanism.

## Why is it required?

Traditional Spring applications can require a lot of manual
configuration.

Spring Boot tries to reduce this configuration by looking at:

-   Dependencies available in the project
-   Application properties
-   Classes available on the classpath
-   Other configuration information

Based on this information, Spring Boot can automatically configure many
parts of the application.

## Example

Suppose we add a MySQL dependency:

``` xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

Spring Boot can detect the database-related libraries and apply relevant
auto-configuration when the required database configuration is provided.

So conceptually:

``` text
Dependencies + Configuration
          ↓
@EnableAutoConfiguration
          ↓
Spring Boot detects available features
          ↓
Applies suitable automatic configuration
```

> Auto-configuration does not mean "everything works without
> configuration." Required application-specific properties, such as
> database connection details, may still need to be provided.

------------------------------------------------------------------------

# 17. SpringApplication.run()

Now we understand `@SpringBootApplication`. Let's understand:

``` java
SpringApplication.run(
    MyFirstSpringBootProjectApplication.class,
    args
);
```

## Definition

`SpringApplication.run()` is used to bootstrap/start a Spring Boot
application.

## Why is it required?

It starts the Spring Boot application and performs the necessary startup
work, including creating the ApplicationContext and initializing the
Spring environment.

For a web application, it also starts the embedded web server when web
dependencies are present.

## Simplified Flow

``` text
SpringApplication.run()
        ↓
Spring Boot starts
        ↓
ApplicationContext is created
        ↓
Configuration is processed
        ↓
Component scanning happens
        ↓
Beans are created
        ↓
Dependencies are injected
        ↓
Auto-configuration is applied
        ↓
Web server starts
        ↓
Application is ready
```

------------------------------------------------------------------------

# 18. @RestController

Now let's look at the controller from our example.

``` java
@RestController
public class MyCar {

}
```

## Definition

`@RestController` is a Spring MVC annotation used to create a REST
controller.

It allows the class to handle HTTP requests and return data directly as
the HTTP response.

It is a specialized form of:

``` text
@Controller
+
@ResponseBody
```

It is also recognized as a Spring component, so Spring can manage the
controller as a Bean.

## Why is @RestController required?

Our application needs a class that can receive HTTP requests.

For example:

``` text
Browser
   ↓
GET /enjoy
   ↓
MyCar Controller
   ↓
enjoy() method
   ↓
Response
```

`@RestController` tells Spring that this class is responsible for
handling such web requests.

------------------------------------------------------------------------

# 19. @GetMapping

## Definition

`@GetMapping` maps an HTTP GET request to a specific Java method.

Example:

``` java
@GetMapping("/enjoy")
public String enjoy() {
    return bike.fun();
}
```

## Why is @GetMapping required?

Spring needs to know:

> "When a user requests `/enjoy`, which method should be executed?"

This annotation provides that mapping.

``` text
GET /enjoy
     ↓
@GetMapping("/enjoy")
     ↓
enjoy()
     ↓
Response
```

------------------------------------------------------------------------

# 20. Dependency

A dependency means that one class requires another class to perform its
work.

In our example:

``` text
MyCar
  ↓
needs
  ↓
MyBike
```

Therefore:

> **MyBike is a dependency of MyCar.**

Our `MyCar` code needs the `MyBike` object because it calls:

``` java
bike.fun();
```

------------------------------------------------------------------------

# 21. Dependency Injection (DI)

## Definition

**Dependency Injection** means providing a class with the
objects/dependencies it requires instead of making the class create
those objects itself.

Without Dependency Injection:

``` java
public class MyCar {

    private MyBike bike = new MyBike();

}
```

Here, `MyCar` creates its own dependency.

With Spring Dependency Injection:

``` java
public class MyCar {

    @Autowired
    private MyBike bike;

}
```

Spring provides the `MyBike` Bean.

## Why is Dependency Injection required?

It reduces direct dependency between classes and allows Spring to manage
object creation.

Instead of:

``` text
MyCar
  ↓
creates MyBike
```

we have:

``` text
Spring Container
      ↓
creates MyBike
      ↓
injects MyBike into MyCar
```

This is one of the main benefits of IoC.

------------------------------------------------------------------------

# 22. @Autowired

## Definition

`@Autowired` tells Spring to inject a suitable Spring-managed Bean into
a dependency.

Example:

``` java
@Autowired
private MyBike bike;
```

## Why is @Autowired required?

`MyCar` requires `MyBike`.

Spring has already created a `MyBike` Bean.

`@Autowired` tells Spring:

> "Find the required `MyBike` Bean and provide it here."

Conceptually:

``` text
IoC Container
     │
     │ MyBike Bean
     ↓
@Autowired
     ↓
MyCar.bike
```

## Important

Your example uses **field injection**:

``` java
@Autowired
private MyBike bike;
```

For learning this is easy to understand.

In production applications, **constructor injection is generally
preferred**.

Example:

``` java
@RestController
public class MyCar {

    private final MyBike bike;

    public MyCar(MyBike bike) {
        this.bike = bike;
    }

    @GetMapping("/enjoy")
    public String enjoy() {
        return bike.fun();
    }
}
```

Spring automatically provides the `MyBike` Bean through the constructor.

------------------------------------------------------------------------

# 23. Complete Example

## MyBike.java

``` java
package com.barbighaiya;

import org.springframework.stereotype.Component;

@Component
public class MyBike {

    public String fun() {
        return "Riding...";
    }
}
```

### What happens here?

1.  Spring scans `com.barbighaiya`.
2.  It finds `@Component`.
3.  Spring creates a `MyBike` Bean.
4.  The Bean is managed by the IoC Container.

------------------------------------------------------------------------

## MyCar.java

``` java
package com.barbighaiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyCar {

    @Autowired
    private MyBike bike;

    @GetMapping("/enjoy")
    public String enjoy() {
        return bike.fun();
    }
}
```

### What happens here?

1.  `@RestController` makes `MyCar` a Spring-managed controller.
2.  Spring creates a `MyCar` Bean.
3.  `@Autowired` tells Spring that `MyCar` needs a `MyBike`.
4.  Spring finds the `MyBike` Bean.
5.  Spring injects it into `bike`.
6.  `/enjoy` is mapped to `enjoy()`.
7.  `bike.fun()` returns `"Riding..."`.

------------------------------------------------------------------------

## MyFirstSpringBootProjectApplication.java

``` java
package com.barbighaiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyFirstSpringBootProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            MyFirstSpringBootProjectApplication.class,
            args
        );

        System.out.println("Hello From Spring Boot!");
    }
}
```

------------------------------------------------------------------------

# 24. Complete Internal Working of This Application

When we run the main class, the following simplified sequence happens.

## Step 1 --- Main Method Starts

Execution starts from:

``` java
public static void main(String[] args)
```

------------------------------------------------------------------------

## Step 2 --- SpringApplication.run() Executes

``` java
SpringApplication.run(
    MyFirstSpringBootProjectApplication.class,
    args
);
```

Spring Boot starts the application.

------------------------------------------------------------------------

## Step 3 --- ApplicationContext Is Created

Spring creates the ApplicationContext.

``` text
SpringApplication.run()
        ↓
ApplicationContext
        ↓
IoC Container
```

------------------------------------------------------------------------

## Step 4 --- @SpringBootApplication Is Processed

Spring sees:

``` java
@SpringBootApplication
```

which enables:

``` text
@Configuration
@EnableAutoConfiguration
@ComponentScan
```

------------------------------------------------------------------------

## Step 5 --- Component Scanning

Spring scans the base package:

``` text
com.barbighaiya
```

It finds:

``` text
MyBike
MyCar
```

------------------------------------------------------------------------

## Step 6 --- MyBike Bean Is Created

Spring finds:

``` java
@Component
public class MyBike
```

So Spring creates and manages a `MyBike` Bean.

``` text
MyBike
  ↓
@Component
  ↓
MyBike Bean
```

------------------------------------------------------------------------

## Step 7 --- MyCar Bean Is Created

Spring finds:

``` java
@RestController
public class MyCar
```

`@RestController` is a component stereotype, so Spring creates and
manages a `MyCar` Bean.

``` text
MyCar
  ↓
@RestController
  ↓
MyCar Bean
```

------------------------------------------------------------------------

## Step 8 --- Dependency Injection

Spring sees:

``` java
@Autowired
private MyBike bike;
```

It knows that `MyCar` requires `MyBike`.

Spring finds the existing `MyBike` Bean and injects it into `MyCar`.

``` text
MyBike Bean
     ↓
Dependency Injection
     ↓
MyCar.bike
```

------------------------------------------------------------------------

## Step 9 --- Web Server Starts

Because this is a Spring Boot web application, the embedded web server
starts.

Your application is available on:

``` text
http://localhost:8080
```

------------------------------------------------------------------------

## Step 10 --- Browser Sends Request

When you open:

``` text
http://localhost:8080/enjoy
```

Spring receives:

``` text
GET /enjoy
```

------------------------------------------------------------------------

## Step 11 --- Spring Finds the Controller Method

Spring sees:

``` java
@GetMapping("/enjoy")
```

and calls:

``` java
public String enjoy()
```

------------------------------------------------------------------------

## Step 12 --- MyBike Method Executes

The method contains:

``` java
return bike.fun();
```

`bike` is the `MyBike` Bean injected by Spring.

Therefore:

``` java
bike.fun();
```

returns:

``` text
Riding...
```

------------------------------------------------------------------------

## Step 13 --- Response Goes to Browser

Because `MyCar` is a `@RestController`, the returned String is written
directly to the HTTP response.

### Browser Output

``` text
Riding...
```

------------------------------------------------------------------------

# 25. Complete Application Flow

``` text
                    APPLICATION START
                           │
                           ↓
             MyFirstSpringBootProjectApplication
                           │
                           ↓
                 @SpringBootApplication
                           │
              ┌────────────┼────────────┐
              ↓            ↓            ↓
       @Configuration  @EnableAuto-  @ComponentScan
                      Configuration
                           │
                           ↓
                  ApplicationContext
                           │
                           ↓
                     IoC Container
                           │
                    Component Scan
                           │
              ┌────────────┴────────────┐
              ↓                         ↓
        MyBike.java                 MyCar.java
        @Component               @RestController
              │                         │
              ↓                         ↓
        MyBike Bean                MyCar Bean
              │                         │
              └──────────┐   ┌─────────┘
                         ↓   ↓
                       @Autowired
                         │
                         ↓
                   MyBike injected
                    into MyCar
                         │
                         ↓
                  Web Server Starts
                         │
                         ↓
                http://localhost:8080
                         │
                         ↓
                 GET /enjoy request
                         │
                         ↓
                @GetMapping("/enjoy")
                         │
                         ↓
                    enjoy() method
                         │
                         ↓
                     bike.fun()
                         │
                         ↓
                    "Riding..."
                         │
                         ↓
                      Browser
```

------------------------------------------------------------------------

# 26. Why Spring Is Useful Here

Without Spring:

``` java
MyBike bike = new MyBike();
MyCar car = new MyCar();
```

We manually create and connect objects.

With Spring:

``` text
Spring
  ↓
Creates MyBike
  ↓
Creates MyCar
  ↓
Finds MyCar's dependency
  ↓
Injects MyBike into MyCar
  ↓
Manages both objects
```

This becomes especially useful when an application has hundreds of
classes and many dependencies.

------------------------------------------------------------------------

# 27. Important Annotations --- Quick Revision

  ----------------------------------------------------------------------------
  Annotation                   What it means           Why we use it
  ---------------------------- ----------------------- -----------------------
  `@SpringBootApplication`     Main Spring Boot        Starts/configures the
                               annotation              application and
                                                       combines three
                                                       important features

  `@Configuration`             Configuration class     Defines application
                                                       configuration and can
                                                       contain `@Bean` methods

  `@EnableAutoConfiguration`   Enables                 Reduces manual
                               auto-configuration      configuration

  `@ComponentScan`             Scans packages          Finds Spring components

  `@Component`                 Spring component        Allows Spring to manage
                                                       an object as a Bean

  `@Bean`                      Bean definition method  Explicitly tells Spring
                                                       to manage the returned
                                                       object

  `@RestController`            REST controller         Handles web requests
                                                       and returns response
                                                       data

  `@GetMapping`                GET request mapping     Maps a URL to a Java
                                                       method

  `@Autowired`                 Dependency injection    Tells Spring to provide
                                                       a required Bean
  ----------------------------------------------------------------------------

------------------------------------------------------------------------

# 28. Important Concepts --- One-Line Definitions

### IoC

> Giving object creation and management responsibility to Spring.

### IoC Container

> The Spring container responsible for creating and managing Beans.

### ApplicationContext

> A Spring IoC container interface used to manage and provide Beans.

### Bean

> An object managed by the Spring container.

### Component

> A class identified as a Spring-managed component.

### Component Scanning

> The process of finding Spring components in configured packages.

### Dependency

> An object/class that another class requires to perform its work.

### Dependency Injection

> Providing required dependencies to a class through Spring instead of
> creating them manually.

### Auto-Configuration

> Spring Boot's mechanism for automatically configuring parts of an
> application based on available dependencies and configuration.

------------------------------------------------------------------------

# 29. The Most Important Chain to Remember

Do not memorize all the annotations separately. First understand this
complete chain:

``` text
@SpringBootApplication
        ↓
@ComponentScan
        ↓
Find Spring Components
        ↓
Create Spring Beans
        ↓
Store/Manage Beans in IoC Container
        ↓
Find Dependencies
        ↓
@Autowired / Constructor Injection
        ↓
Inject Required Beans
        ↓
Start Application
        ↓
Handle Requests
```

And remember the three major features behind `@SpringBootApplication`:

``` text
@SpringBootApplication
        │
        ├── @Configuration
        │       ↓
        │   Application configuration
        │
        ├── @EnableAutoConfiguration
        │       ↓
        │   Automatic configuration
        │
        └── @ComponentScan
                ↓
            Find components
```

------------------------------------------------------------------------

# 30. Final Example --- Everything Together

``` java
package com.barbighaiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyFirstSpringBootProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            MyFirstSpringBootProjectApplication.class,
            args
        );
    }
}
```

``` java
package com.barbighaiya;

import org.springframework.stereotype.Component;

@Component
public class MyBike {

    public String fun() {
        return "Riding...";
    }
}
```

``` java
package com.barbighaiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyCar {

    @Autowired
    private MyBike bike;

    @GetMapping("/enjoy")
    public String enjoy() {
        return bike.fun();
    }
}
```

### Result

Open:

``` text
http://localhost:8080/enjoy
```

### Output

``` text
Riding...
```

### What happened internally?

``` text
@SpringBootApplication
        ↓
Spring Boot starts
        ↓
ApplicationContext created
        ↓
Component scanning
        ↓
MyBike found
        ↓
MyBike Bean created
        ↓
MyCar found
        ↓
MyCar Bean created
        ↓
@Autowired detected
        ↓
MyBike Bean injected into MyCar
        ↓
Web server starts on port 8080
        ↓
GET /enjoy
        ↓
enjoy()
        ↓
bike.fun()
        ↓
"Riding..."
        ↓
Browser
```

> **Core idea:** Spring Boot's job is not simply to "run Java code." It
> creates the Spring environment, discovers and manages Beans, connects
> their dependencies, applies configuration, starts the application, and
> handles requests through the configured web stack.

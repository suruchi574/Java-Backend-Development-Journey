# Spring Boot Layers

Spring Boot applications are divided into different layers. Each layer has a specific responsibility. This separation makes the application clean, easy to understand, and easier to maintain.

In this project, we have used the following layers:

- Model Layer
- Service Layer
- Repository Layer

---

## Project Structure

```
src/main/java
│
├── com.barbighaiya
│   └── SpringBootBasicsApplication.java
│
├── model
│   ├── Computer.java
│   └── Laptop.java
│
├── service
│   └── LaptopService.java
│
└── repo
    └── LaptopRepository.java
```

---

## 1. Model Layer

The Model Layer contains the classes that represent the objects (entities) of our application.

In this project, `Laptop` is a model class.

```java
@Component
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling from Laptop");
    }
}
```

### @Component

`@Component` is a stereotype annotation.

It tells Spring to automatically create and manage an object (Bean) of this class.

Without Spring:

```java
Laptop lap = new Laptop();
```

With Spring:

```java
Laptop lap = context.getBean(Laptop.class);
```

Instead of creating the object manually, Spring creates and manages it inside the IoC Container.

---

## 2. Service Layer

The Service Layer contains the business logic of the application.

It acts as a bridge between the Controller and Repository layers.

```
Controller
     ↓
Service
     ↓
Repository
```

Example:

```java
@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laprepo;

    public void addLaptop(Laptop lap) {
        laprepo.save(lap);
    }

    public boolean isGoodForProg(Laptop lap) {
        return true;
    }
}
```

### Responsibilities of Service Layer

- Contains business logic.
- Processes application data.
- Acts as an intermediary between Controller and Repository.
- Calls Repository methods whenever database operations are required.

### @Service

`@Service` is a stereotype annotation.

It tells Spring that this class contains business logic and should be managed as a Spring Bean.

### @Autowired

```java
@Autowired
private LaptopRepository laprepo;
```

`@Autowired` performs **Dependency Injection**.

Instead of writing:

```java
LaptopRepository repo = new LaptopRepository();
```

Spring automatically creates and injects the required object.

### addLaptop()

```java
public void addLaptop(Laptop lap) {
    laprepo.save(lap);
}
```

This method receives a `Laptop` object and sends it to the Repository Layer to save it.

### isGoodForProg()

```java
public boolean isGoodForProg(Laptop lap) {
    return true;
}
```

This method represents business logic.

Currently, it always returns `true`, but in a real application it could check properties such as RAM, Processor, Storage, etc.

---

## 3. Repository Layer

The Repository Layer is responsible for interacting with the database.

It performs operations such as:

- Save data
- Fetch data
- Update data
- Delete data

Example:

```java
@Repository
public class LaptopRepository {

    public void save(Laptop lap) {
        System.out.println("Saved in Database ✌");
    }
}
```

### @Repository

`@Repository` is a stereotype annotation.

It tells Spring that this class performs database-related operations.

Spring creates and manages it as a Bean and also provides automatic database exception handling.

### save()

```java
public void save(Laptop lap) {
    System.out.println("Saved in Database ✌");
}
```

Currently, this method only prints a message.

In real-world applications, it would save the object into databases such as MySQL, PostgreSQL, Oracle, MongoDB, etc.

---

## 4. Main Class

```java
@SpringBootApplication
public class SpringBootBasicsApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(SpringBootBasicsApplication.class, args);

        LaptopService service = context.getBean(LaptopService.class);

        Laptop lap = context.getBean(Laptop.class);

        service.addLaptop(lap);
    }
}
```

### @SpringBootApplication

`@SpringBootApplication` is the main annotation used in every Spring Boot application.

It combines three annotations:

- `@Configuration`
- `@EnableAutoConfiguration`
- `@ComponentScan`

It tells Spring Boot to:

- Start the application.
- Scan all components.
- Create and manage Beans.
- Configure the application automatically.

### SpringApplication.run()

```java
ApplicationContext context =
SpringApplication.run(SpringBootBasicsApplication.class, args);
```

This method:

- Starts the Spring Boot application.
- Creates the IoC Container.
- Creates all Spring Beans.
- Returns the `ApplicationContext`.

### ApplicationContext

`ApplicationContext` is Spring's IoC Container.

It stores all Spring-managed Beans.

Beans can be obtained using:

```java
LaptopService service = context.getBean(LaptopService.class);

Laptop lap = context.getBean(Laptop.class);
```

---

## Flow of Execution

```
Application Starts
        │
        ▼
@SpringBootApplication
        │
        ▼
SpringApplication.run()
        │
        ▼
ApplicationContext (IoC Container)
        │
        ▼
Creates all Beans
        │
        ▼
LaptopService Bean
        │
        ▼
Laptop Bean
        │
        ▼
service.addLaptop(lap)
        │
        ▼
LaptopRepository.save(lap)
        │
        ▼
Saved in Database ✌
```

---

## Output

```
Saved in Database ✌
```

---

## Difference Between Layers

| Layer | Annotation | Responsibility |
|--------|------------|----------------|
| Model | `@Component` | Represents application objects (entities). |
| Service | `@Service` | Contains business logic and communicates with the Repository. |
| Repository | `@Repository` | Performs database operations like Save, Update, Delete, and Fetch. |

---

## Key Points

- `@Component` creates a Spring-managed Bean.
- `@Service` is used for business logic.
- `@Repository` is used for database operations.
- `@Autowired` automatically injects required dependencies (Dependency Injection).
- `ApplicationContext` stores and manages all Spring Beans.
- `SpringApplication.run()` starts the application and creates the IoC Container.
- Separating the application into layers makes the code clean, reusable, maintainable, and easier to test.
# 08. Bean Name and Aliases

## What is a Bean Name?

A Bean Name is the unique identifier used by the Spring IoC Container to identify and retrieve a bean.

A bean can be retrieved in two ways:

- By Name
- By Type

---

# Default Bean Name

If no name is specified in the `@Bean` annotation, Spring automatically uses the **method name** as the bean name.

Example:

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}
```

Bean Name

```
desktop
```

Retrieving the bean:

```java
Desktop dt = context.getBean("desktop", Desktop.class);
```

or

```java
Desktop dt = context.getBean(Desktop.class);
```

---

# Custom Bean Name

We can specify our own bean name using the `name` attribute of the `@Bean` annotation.

Example:

```java
@Bean(name = "computer")
public Desktop desktop() {
    return new Desktop();
}
```

Bean Name

```
computer
```

Retrieving the bean:

```java
Desktop dt = context.getBean("computer", Desktop.class);
```

---

# Multiple Bean Names (Aliases)

Spring allows a bean to have multiple names.

Example:

```java
@Bean(name = {"suruchi", "barbighaiya", "desktop"})
public Desktop desktop() {
    return new Desktop();
}
```

Bean Names

```
desktop
suruchi
barbighaiya
```

All three names point to the same bean.

---

# Retrieving Bean Using Different Names

```java
Desktop dt1 = context.getBean("desktop", Desktop.class);

Desktop dt2 = context.getBean("suruchi", Desktop.class);

Desktop dt3 = context.getBean("barbighaiya", Desktop.class);
```

Each statement returns the same `Desktop` bean.

---

# Default Bean Name vs Custom Bean Name

### Without `name` Attribute

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}
```

Bean Name

```
desktop
```

Spring automatically uses the method name.

---

### With `name` Attribute

```java
@Bean(name = {"suruchi", "barbighaiya"})
public Desktop desktop() {
    return new Desktop();
}
```

Bean Names

```
suruchi
barbighaiya
```

The method name (`desktop`) is **not automatically used**.

Therefore,

```java
context.getBean("desktop", Desktop.class);
```

throws an exception because no bean with the name `desktop` exists.

To use the method name as well, include it explicitly.

```java
@Bean(name = {"desktop", "suruchi", "barbighaiya"})
public Desktop desktop() {
    return new Desktop();
}
```

Now all three names are valid.

---

# Retrieving Bean by Type

A bean can also be retrieved by its type.

```java
Desktop dt = context.getBean(Desktop.class);
```

Spring searches the IoC Container for a bean of type `Desktop`.

This works only when there is a single bean of that type.

---

# Internal Working

```
@Bean(name={"desktop","suruchi","barbighaiya"})
                    │
                    ▼
         Creates Desktop Bean
                    │
                    ▼
      Stores Bean in IoC Container
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
    desktop     suruchi    barbighaiya
        │           │           │
        └───────────┴───────────┘
                    │
                    ▼
            Same Desktop Bean
```

---

# Example

Configuration Class

```java
@Configuration
public class AppConfig {

    @Bean(name = {"desktop", "suruchi", "barbighaiya"})
    public Desktop desktop() {
        return new Desktop();
    }

}
```

Bean Retrieval

```java
Desktop dt1 = context.getBean("desktop", Desktop.class);

Desktop dt2 = context.getBean("suruchi", Desktop.class);

Desktop dt3 = context.getBean("barbighaiya", Desktop.class);

Desktop dt4 = context.getBean(Desktop.class);
```

All four statements return the same bean.

---

# Output

```
Desktop Object Created
Compiling from Desktop
```

Since the default bean scope is **Singleton**, Spring creates only one `Desktop` object.

---

# Key Points

- A Bean Name uniquely identifies a bean inside the Spring IoC Container.
- By default, the method name becomes the bean name.
- The `name` attribute of `@Bean` is used to specify custom bean names.
- A bean can have multiple names (aliases).
- Once the `name` attribute is specified, Spring no longer uses the method name automatically.
- To keep the method name as a bean name, include it explicitly in the `name` array.
- `getBean("beanName", Class)` retrieves a bean by its name.
- `getBean(Class)` retrieves a bean by its type.
- Retrieving by type works only when exactly one bean of that type exists.
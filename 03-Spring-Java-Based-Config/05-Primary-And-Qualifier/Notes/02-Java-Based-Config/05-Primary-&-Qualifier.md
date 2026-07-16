# @Primary & @Qualifier

## Why are they required?

When multiple beans of the **same type** are present in the Spring IoC Container, Spring cannot decide which bean to inject during autowiring.

### Example

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}

@Bean
public Laptop laptop() {
    return new Laptop();
}
```

```java
@Bean
public Alien alien(Computer comp) {
    return new Alien();
}
```

Here, both `Desktop` and `Laptop` implement `Computer`.

Spring throws:

```
No qualifying bean of type 'Computer'
expected single matching bean but found 2
```

To resolve this ambiguity, Spring provides **@Primary** and **@Qualifier**.

---

# @Primary

`@Primary` marks a bean as the **default bean**.

If Spring finds multiple beans of the same type, it injects the bean marked with `@Primary`.

### Example

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}

@Bean
@Primary
public Laptop laptop() {
    return new Laptop();
}

@Bean
public Alien alien(Computer comp) {
    return new Alien(comp);
}
```

**Result:** Spring injects the `Laptop` bean automatically.

---

# @Qualifier

`@Qualifier` tells Spring **exactly which bean** to inject.

It is used at the autowiring point.

### Example

```java
@Bean
public Desktop desktop() {
    return new Desktop();
}

@Bean
public Laptop laptop() {
    return new Laptop();
}

@Bean
public Alien alien(@Qualifier("desktop") Computer comp) {
    return new Alien(comp);
}
```

**Result:** Spring injects the `Desktop` bean.

---

# Difference

| @Primary | @Qualifier |
|----------|------------|
| Makes one bean the default. | Selects a specific bean. |
| Used on the bean definition. | Used at the injection point. |
| Used when no specific bean is requested. | Used when a particular bean is required. |

> **Note:** If both `@Primary` and `@Qualifier` are used, **`@Qualifier` takes higher priority**.
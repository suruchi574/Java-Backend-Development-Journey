# Spring Lazy Initialization (`lazy-init`) Notes

## What is `lazy-init`?

By default, Spring creates all **singleton beans** as soon as the
`spring.xml` configuration file is loaded.

This behavior is called **Eager Initialization**.

If we don't want Spring to create a bean immediately, we can use:

```xml
<bean id="comp2"
      class="com.barbighaiya.Spring_First_Project.Desktop"
      lazy-init="true"/>
```

`lazy-init="true"` tells Spring:

> "Don't create this bean when the container starts.
> Create it only when someone actually needs it."

---

# Default Behavior (Without lazy-init)

```
Application Starts
        │
        ▼
Spring Loads spring.xml
        │
        ▼
Creates All Singleton Beans
        │
        ├────────► Alien Object
        │
        └────────► Desktop Object
```

Output

```
Desktop Object Created
```

Even if we never use the Desktop object,
Spring still creates it.

---

# Behavior with lazy-init="true"

```
Application Starts
        │
        ▼
Spring Loads spring.xml
        │
        ▼
Desktop Bean NOT Created
        │
        ▼
Waits...
        │
        ▼
getBean("comp2")
        │
        ▼
Desktop Object Created
```

Now the bean is created only when required.

---

# Example

```xml
<bean id="comp2"
      class="com.barbighaiya.Spring_First_Project.Desktop"
      lazy-init="true"/>
```

Java

```java
Desktop desk = (Desktop) context.getBean("comp2");
```

Output

```
Desktop Object Created
```

Notice that the object is created only after `getBean()` is called.

---

# Important Rule

`lazy-init="true"` works only if no non-lazy bean depends on it.

---

## Case 1

Lazy bean is never used.

```
Desktop (Lazy)
```

Spring does not create it.

Output

```
(No Output)
```

---

## Case 2

Lazy bean is requested manually.

```java
Desktop desk = (Desktop) context.getBean("comp2");
```

Output

```
Desktop Object Created
```

---

## Case 3

Non-lazy bean depends on lazy bean.

```xml
<bean id="comp2"
      class="Desktop"
      lazy-init="true"/>

<bean id="alien1"
      class="Alien">

    <property name="comp" ref="comp2"/>

</bean>
```

Here,

```
Alien
   │
   │ depends on
   ▼
Desktop (Lazy)
```

When Spring creates `Alien`, it needs the `Desktop` object.

Therefore Spring ignores the lazy initialization for that dependency and
creates the `Desktop` bean automatically.

Output

```
Desktop Object Created
23
Coding....
Compiling from Desktop
```

---

# Why does this happen?

Spring cannot create the `Alien` object unless all of its required
dependencies are available.

Since `Alien` needs `Desktop`, Spring first creates the `Desktop` bean,
then injects it into `Alien`.

Flow

```
Create Alien
      │
      ▼
Needs Desktop?
      │
     Yes
      │
      ▼
Create Desktop
      │
      ▼
Inject Desktop into Alien
      │
      ▼
Alien Ready
```

---

# Advantages of lazy-init

✅ Improves application startup time.

✅ Saves memory because unused beans are not created.

✅ Useful for heavy objects like:

- Database connections
- Report generators
- File processors
- Large caches
- Expensive objects

---

# Disadvantages of lazy-init

❌ First access is slightly slower because the object is created at that moment.

❌ If a non-lazy bean depends on the lazy bean, Spring creates it immediately anyway.

---

# Summary

| Without `lazy-init` | With `lazy-init="true"` |
|----------------------|-------------------------|
| Bean created during Spring startup | Bean created only when required |
| Faster first access | Slight delay during first access |
| Higher startup time | Faster startup time |
| More memory used initially | Memory used only when bean is needed |

---

# Interview Questions

### 1. What is lazy initialization in Spring?

Lazy initialization delays bean creation until the bean is actually required.

---

### 2. What is the default initialization behavior of singleton beans?

Singleton beans are eagerly initialized when the Spring container starts.

---

### 3. How do you make a bean lazy?

```xml
<bean id="comp2"
      class="Desktop"
      lazy-init="true"/>
```

---

### 4. When is a lazy bean created?

- When `getBean()` is called.
- When a non-lazy bean depends on it.

---

### 5. Does `lazy-init` work if another bean depends on it?

No.

If a non-lazy bean depends on a lazy bean, Spring creates the lazy bean automatically to satisfy the dependency.

---

# Key Points to Remember

- Default singleton beans are eagerly initialized.
- `lazy-init="true"` delays bean creation.
- Bean is created on first use.
- `getBean()` triggers creation.
- Dependency injection can also trigger creation.
- Non-lazy beans force creation of required lazy beans.
- Useful for expensive or rarely used beans.
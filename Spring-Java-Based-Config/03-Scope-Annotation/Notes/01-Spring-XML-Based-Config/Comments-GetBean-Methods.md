Since you're maintaining Spring notes alongside your project, you can create a separate notes file for this topic.

### 📄 Suggested File Name

```
06_getBean_Method_Notes.txt
```

or, if you want all your notes in Markdown:

```
06_getBean_Method_Notes.md
```

---

# Spring `getBean()` Method Notes

## 1. getBean() by Bean ID

```java
Alien obj = (Alien) context.getBean("alien1");
```

### Notes

* `getBean("beanId")` searches the bean using its **ID (name)**.
* It returns an object of type **Object**.
* Therefore, **manual typecasting** is required.

Example:

```java
Alien obj = (Alien) context.getBean("alien1");
```

---

## 2. getBean() by Bean ID and Class Type

```java
Alien obj = context.getBean("alien1", Alien.class);
```

### Notes

* This method searches the bean using its **ID**.
* Since the class type is provided, Spring returns the required type directly.
* **No manual typecasting is required.**
* This is the **recommended way**.

---

## 3. getBean() by Type

```java
Desktop desk = context.getBean(Desktop.class);
```

### Notes

* Searches the bean by its **class type**.
* No need to specify the bean ID.
* No typecasting is required.
* Works only when **one bean** of that type exists.

---

## 4. Multiple Beans of the Same Type

Example:

```xml
<bean id="desktop" class="Desktop"/>
<bean id="laptop" class="Laptop"/>
```

Both implement the `Computer` interface.

```java
Computer comp = context.getBean(Computer.class);
```

### Problem

Spring becomes confused because multiple beans have the same type.

### Solution

Use the `primary="true"` attribute.

```xml
<bean id="desktop"
      class="Desktop"
      primary="true"/>
```

Now Spring automatically returns the Desktop bean.

---

## 5. Setter Injection

Instead of assigning values manually,

```java
obj.setAge(21);
```

we can configure the value in `spring.xml`.

Example:

```xml
<property name="age" value="21"/>
```

Spring automatically injects the value while creating the bean.

---

## 6. Singleton Scope

```java
Alien obj1 = context.getBean("alien1", Alien.class);
Alien obj2 = context.getBean("alien1", Alien.class);
```

### Notes

* By default, Spring beans use **Singleton Scope**.
* Calling `getBean()` multiple times returns the **same object**.
* Spring does **not** create a new object every time.

```
obj1 == obj2   → true
```

---

## 7. Lazy Initialization

Suppose a bean is configured as:

```xml
<bean id="comp2"
      class="Desktop"
      lazy-init="true"/>
```

The bean is **not created** when the container starts.

It is created only when:

```java
Desktop desk = context.getBean("comp2", Desktop.class);
```

is executed.

---

# Summary

| Method                 | Searches By     | Typecasting Required |
| ---------------------- | --------------- | -------------------- |
| `getBean("id")`        | Bean ID         | ✅ Yes                |
| `getBean("id", Class)` | Bean ID + Class | ❌ No                 |
| `getBean(Class)`       | Bean Type       | ❌ No                 |

### Key Points

* `getBean("id")` returns an `Object`, so typecasting is required.
* `getBean("id", Class)` is the recommended approach.
* `getBean(Class)` searches by type.
* If multiple beans of the same type exist, use `primary="true"` or another disambiguation method.
* Setter Injection lets Spring assign values automatically.
* Default bean scope is **Singleton**.
* A bean with `lazy-init="true"` is created only when requested using `getBean()`.

This file fits well after your earlier notes on Bean Creation, Scopes, and Dependency Injection, making `06_getBean_Method_Notes.md` a logical next chapter in your Spring learning journey.

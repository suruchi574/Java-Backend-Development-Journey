# Spring Autowiring Notes

## What is Autowiring?

**Autowiring** is a feature provided by Spring that automatically injects dependent objects into a bean.

Instead of writing the `<property>` tag manually, Spring can automatically find and inject the required bean.

---

# Why Do We Use Autowiring?

Without autowiring, we manually inject dependencies.

Example:

```xml
<bean id="comp1" class="com.barbighaiya.Spring_First_Project.Laptop"/>

<bean id="alien1" class="com.barbighaiya.Spring_First_Project.Alien">
    <property name="comp" ref="comp1"/>
</bean>
```

Here, Spring calls:

```java
alien.setComp(comp1);
```

With autowiring, we don't need to write the `<property>` tag. Spring performs the injection automatically.

---

# How Does Spring Perform Autowiring?

Spring looks for a suitable bean in the IoC Container and injects it into the dependent bean based on the selected autowiring mode.

---

# Autowiring Modes

Spring provides four autowiring modes.

## 1. byName

Spring searches for a bean whose **id matches the property name**.

Example:

```java
private Computer comp;
```

If the bean id is:

```xml
<bean id="comp" class="com.barbighaiya.Spring_First_Project.Laptop"/>
```

and

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien"
      autowire="byName"/>
```

Spring automatically executes:

```java
alien.setComp(comp);
```

### Important Rule

For **byName** to work:

* Property name and bean id must be exactly the same.
* No `<property>` tag is required.

Example:

```java
private Computer comp;
```

```xml
<bean id="comp" class="Laptop"/>
```

✅ Works

---

## 2. byType

Spring searches for a bean whose **type matches the property type**.

Example:

```java
private Computer comp;
```

Bean:

```xml
<bean id="comp1"
      class="com.barbighaiya.Spring_First_Project.Laptop"/>
```

Configuration:

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien"
      autowire="byType"/>
```

Spring checks:

```text
Property Type  → Computer

Available Bean → Laptop implements Computer
```

Since `Laptop` implements the `Computer` interface, Spring injects the `Laptop` object automatically.

---

## 3. constructor

Spring performs dependency injection through the constructor instead of setter methods.

Example:

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien"
      autowire="constructor"/>
```

Spring automatically calls the matching constructor and injects the required dependencies.

---

## 4. autodetect (Deprecated)

Spring first tries constructor injection.

If it cannot find a suitable constructor, it falls back to byType injection.

> **Note:** `autodetect` is deprecated and should not be used in modern Spring applications.

---

# byName vs byType

## byName

Matches using:

* Bean **id**
* Property **name**

Example:

```java
private Computer comp;
```

```xml
<bean id="comp" class="Laptop"/>
```

✔ Injection succeeds.

---

## byType

Matches using:

* Property type
* Bean type

Example:

```java
private Computer comp;
```

```xml
<bean id="comp1" class="Laptop"/>
```

The bean id is different, but the type matches.

✔ Injection succeeds.

---

# What Happens If Both Autowiring and `<property>` Are Used?

Example:

```xml
<bean id="alien1"
      class="Alien"
      autowire="byType">

    <property name="comp" ref="comp1"/>

</bean>
```

In this case, Spring **gives priority to the `<property>` tag**.

Manual configuration always overrides autowiring.

---

# Problem with byType

Suppose we have:

```xml
<bean id="laptop" class="Laptop"/>

<bean id="desktop" class="Desktop"/>
```

Both implement:

```java
Computer
```

Now Spring sees:

```text
Laptop  → Computer

Desktop → Computer
```

When using:

```xml
autowire="byType"
```

Spring becomes confused because it finds **multiple beans of the same type**.

As a result, Spring throws an exception similar to:

```text
No qualifying bean of type 'Computer'
expected single matching bean but found 2
```

---

# How to Resolve byType Conflicts

When multiple beans have the same type, you can:

* Use **byName** autowiring.
* Use the `<property>` tag with `ref`.
* Mark one bean as **primary** (used in annotation-based configuration).

---

# Polymorphism in Spring

Your `Alien` class contains:

```java
private Computer comp;
```

`Computer` is an interface.

Both classes implement it:

```java
Laptop implements Computer
Desktop implements Computer
```

Therefore, the setter can accept any implementation.

```java
public void setComp(Computer comp) {
    this.comp = comp;
}
```

Spring can inject either:

* Laptop
* Desktop

depending on the bean configuration.

This is an example of **runtime polymorphism**.

---

# How `code()` Works

```java
public void code() {
    System.out.println("Coding....");
    comp.compile();
}
```

If Spring injects:

```text
Laptop
```

Output:

```text
Coding....
Compiling from Laptop
```

If Spring injects:

```text
Desktop
```

Output:

```text
Coding....
Compiling from Desktop
```

The `Alien` class never needs to know which implementation it receives.

---

# Summary

| Autowiring Mode | Matches By                     | Requirement                                       |
| --------------- | ------------------------------ | ------------------------------------------------- |
| **byName**      | Bean id                        | Bean id must match the property name              |
| **byType**      | Bean type                      | Only one matching bean of that type should exist  |
| **constructor** | Constructor parameters         | Dependencies are injected through the constructor |
| **autodetect**  | Constructor first, then byType | Deprecated                                        |

---

# Key Points to Remember

* Autowiring automatically injects dependencies.
* `byName` matches the **bean id** with the **property name**.
* `byType` matches the **bean type** with the **property type**.
* Manual `<property>` injection has higher priority than autowiring.
* If multiple beans have the same type, `byType` throws an exception.
* An interface can hold objects of any class that implements it.
* Spring uses this feature to achieve loose coupling and runtime polymorphism.

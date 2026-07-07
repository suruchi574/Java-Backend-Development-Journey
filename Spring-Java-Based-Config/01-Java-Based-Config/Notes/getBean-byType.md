# Spring `getBean()` Method Notes

## What is `getBean()`?

`getBean()` is a method provided by the Spring IoC Container that is used to retrieve a bean (object) from the Spring container.

Instead of creating objects using the `new` keyword, we ask Spring to provide the required object.

---

# Syntax

### 1. Get Bean by ID

```java
Object obj = context.getBean("beanId");
```

Since this method returns an object of type `Object`, we need to manually typecast it.

Example:

```java
Alien obj = (Alien) context.getBean("alien1");
```

---

# Why Typecasting is Required?

The method

```java
context.getBean("alien1");
```

returns an object of the `Object` class because Spring does not know which type we want at compile time.

Therefore, we need to convert (typecast) it into the required class.

```
getBean("alien1")
        │
        ▼
Returns Object
        │
        ▼
Typecast to Alien
        │
        ▼
Alien Object
```

Example

```java
Alien obj = (Alien) context.getBean("alien1");
```

---

# Better Way (No Typecasting)

Spring provides an overloaded version of `getBean()` where we can mention the class type.

Syntax

```java
ClassName obj = context.getBean("beanId", ClassName.class);
```

Example

```java
Alien obj = context.getBean("alien1", Alien.class);
```

Advantages

- No manual typecasting.
- Type-safe.
- Cleaner and more readable code.
- Compile-time type checking.

---

# Get Bean by Type

Instead of passing the bean ID, we can directly pass the class.

Syntax

```java
ClassName obj = context.getBean(ClassName.class);
```

Example

```java
Desktop desk = context.getBean(Desktop.class);
```

Spring searches for a bean whose type is `Desktop` and returns it.

Flow

```
getBean(Desktop.class)
        │
        ▼
Search Bean by Type
        │
        ▼
Desktop Bean Found
        │
        ▼
Returns Desktop Object
```

---

# What Happens if Bean ID is Not Given?

When only the class is provided,

```java
Desktop desk = context.getBean(Desktop.class);
```

Spring searches the container **by type** instead of by bean ID.

If exactly one bean of that type exists, it returns that bean.

---

# What if Multiple Beans Have the Same Type?

Example

```xml
<bean id="comp1"
      class="Laptop"
      primary="true"/>

<bean id="comp2"
      class="Desktop"/>
```

Both `Laptop` and `Desktop` implement the `Computer` interface.

```
Computer
   ▲
   │
──────────────
│            │
Laptop    Desktop
```

Now,

```java
Computer comp = context.getBean(Computer.class);
```

Spring finds two beans of type `Computer`.

Normally, this creates confusion because Spring does not know which bean to inject.

To solve this, we can use:

```xml
primary="true"
```

Example

```xml
<bean id="comp1"
      class="Laptop"
      primary="true"/>
```

Now Spring gives priority to the `Laptop` bean.

```
Computer.class
       │
       ▼
Two Beans Found
       │
       ├────────► Laptop (Primary ✅)
       │
       └────────► Desktop
               │
               ▼
Returns Laptop Object
```

---

# Output

```java
Computer comp = context.getBean(Computer.class);
```

Output

```
Desktop Object Created
23
Coding....
Compiling from Laptop
```

Explanation

- `Desktop` bean is created because it is required by another bean or explicitly requested.
- `Computer` reference points to the `Laptop` object because it is marked with `primary="true"`.

---

# Different Ways to Retrieve a Bean

| Method | Searches By | Typecasting Required |
|----------|-------------|----------------------|
| `getBean("beanId")` | Bean ID | ✅ Yes |
| `getBean("beanId", Alien.class)` | Bean ID | ❌ No |
| `getBean(Alien.class)` | Bean Type | ❌ No |

---

# Advantages of `getBean(Class)`

✅ No manual typecasting.

✅ More readable.

✅ Compile-time type checking.

✅ Less chance of `ClassCastException`.

---

# Advantages of `getBean(beanId, Class)`

✅ Searches by bean ID.

✅ No typecasting.

✅ Type-safe.

✅ Cleaner syntax.

---

# Practice Questions

### 1. What is `getBean()` in Spring?

`getBean()` is used to retrieve a bean (object) from the Spring IoC container.

---

### 2. Why is typecasting required with `getBean("beanId")`?

Because the method returns an object of type `Object`.

---

### 3. How can we avoid typecasting?

By using the overloaded method:

```java
Alien obj = context.getBean("alien1", Alien.class);
```

---

### 4. How do we retrieve a bean by type?

```java
Desktop desk = context.getBean(Desktop.class);
```

---

### 5. What happens if multiple beans have the same type?

Spring throws an exception unless one bean is marked with:

```xml
primary="true"
```

or another mechanism such as qualifiers is used.

---

# Key Points to Remember

- `getBean("beanId")` returns an `Object`.
- Manual typecasting is required with `getBean("beanId")`.
- `getBean("beanId", ClassName.class)` avoids typecasting.
- `getBean(ClassName.class)` searches by bean type.
- If multiple beans of the same type exist, Spring uses the bean marked with `primary="true"`.
- Retrieving beans through Spring promotes loose coupling and avoids using the `new` keyword.
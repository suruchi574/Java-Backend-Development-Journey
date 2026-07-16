# Spring Inner Bean Notes

## What is an Inner Bean?

An **Inner Bean** is a bean that is declared **inside another bean**.

It is used when a bean is required by **only one parent bean** and does not need to be accessed from anywhere else in the application.

Instead of creating the bean separately, we create it directly inside the `<property>` tag of the parent bean.

---

# Why Do We Need an Inner Bean?

Consider the following bean:

```xml
<bean id="comp1"
      class="com.barbighaiya.Spring_First_Project.Laptop"/>
```

Suppose this `Laptop` bean is used **only by the `Alien` bean**.

Even though only `Alien` needs it, the bean is declared outside, making it accessible throughout the entire Spring container.

```
Spring Container
│
├── Alien Bean
│
└── Laptop Bean
      ▲
      │
Accessible by everyone
```

This is unnecessary if no other bean uses `Laptop`.

To solve this problem, Spring provides **Inner Beans**.

---

# Syntax of Inner Bean

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien">

    <property name="comp">

        <bean class="com.barbighaiya.Spring_First_Project.Laptop"/>

    </property>

</bean>
```

Notice that the `Laptop` bean is declared inside the `comp` property.

---

# How Does an Inner Bean Work?

```
Alien Bean
     │
     ▼
Property : comp
     │
     ▼
Creates Laptop Object
     │
     ▼
Injects into Alien
```

Spring creates the inner bean and immediately injects it into the parent bean.

---

# No Need for Bean ID

Unlike normal beans, an Inner Bean **does not require an `id`**.

Example

```xml
<property name="comp">

    <bean class="com.barbighaiya.Spring_First_Project.Laptop"/>

</property>
```

Since the bean cannot be accessed separately, there is no need to assign it an ID.

---

# No Need for `ref`

For normal beans:

```xml
<bean id="comp1"
      class="com.barbighaiya.Spring_First_Project.Laptop"/>

<property name="comp" ref="comp1"/>
```

For an Inner Bean:

```xml
<property name="comp">

    <bean class="com.barbighaiya.Spring_First_Project.Laptop"/>

</property>
```

Since the bean is created directly inside the property, the `ref` attribute is not required.

---

# Lifecycle of an Inner Bean

```
Spring Starts
      │
      ▼
Creates Alien Bean
      │
      ▼
Creates Inner Laptop Bean
      │
      ▼
Injects Laptop into Alien
      │
      ▼
Alien Ready
```

The Inner Bean is created only when the parent bean is created.

---

# Output

```text
23
Coding....
Compiling from Laptop
```

Explanation

- Spring creates the `Alien` bean.
- While creating `Alien`, Spring creates the Inner `Laptop` bean.
- The `Laptop` object is injected into the `comp` property.
- Finally, the `code()` method is executed.

---

# Advantages of Inner Bean

✅ Used only when a bean belongs to a single parent bean.

✅ Reduces unnecessary beans in the Spring container.

✅ No need to provide a bean ID.

✅ No need to use the `ref` attribute.

✅ Improves readability by keeping dependent beans together.

---

# Limitations of Inner Bean

❌ Cannot be accessed using `getBean()`.

❌ Cannot be shared with other beans.

❌ Exists only as part of its parent bean.

---

# Outer Bean vs Inner Bean

| Outer Bean | Inner Bean |
|------------|------------|
| Declared separately | Declared inside another bean |
| Can have an `id` | No `id` required |
| Can be shared by multiple beans | Used by only one parent bean |
| Can be retrieved using `getBean()` | Cannot be retrieved using `getBean()` |
| Uses `ref` for injection | No `ref` required |

---

# Practice Questions

### 1. What is an Inner Bean in Spring?

An Inner Bean is a bean declared inside another bean. It is used when the bean is required only by one parent bean.

---

### 2. Why do we use an Inner Bean?

To avoid creating a separate bean in the Spring container when it is used by only one parent bean.

---

### 3. Does an Inner Bean require an `id`?

No. Since it cannot be accessed separately, an `id` is not required.

---

### 4. Can we use the `ref` attribute with an Inner Bean?

No. The bean is created directly inside the `<property>` tag, so `ref` is not needed.

---

### 5. Can we retrieve an Inner Bean using `getBean()`?

No. Inner Beans are not registered as separate beans in the Spring container.

---

### 6. When is an Inner Bean created?

It is created when its parent bean is created.

---

# Key Points to Remember

- An Inner Bean is declared inside another bean.
- It is used when a bean is needed by only one parent bean.
- No `id` is required.
- No `ref` attribute is required.
- It cannot be accessed using `getBean()`.
- It cannot be shared with other beans.
- It is created along with its parent bean.
- It helps keep the Spring configuration clean and organized.
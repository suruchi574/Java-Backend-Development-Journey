# Spring XML Configuration Notes

## 1. What is a Bean?

A **Bean** is an object that is created, managed, and maintained by the **Spring IoC (Inversion of Control) Container**.

A bean is defined using the `<bean>` tag in the `spring.xml` file.

### Example

```xml
<bean id="alien1" class="com.barbighaiya.Spring_First_Project.Alien"/>
```

### Attributes

* **id** → A unique identifier for the bean.
* **class** → Fully qualified class name whose object Spring will create.

---

# 2. Bean Scope

Bean scope defines **how many objects Spring should create** for a bean.

The two most commonly used scopes are:

## Singleton (Default Scope)

* Singleton is the default scope in Spring.
* If no `scope` attribute is specified, Spring treats the bean as a Singleton.
* Only **one object** is created for the entire Spring container.
* Every call to `getBean()` returns the same object.
* Singleton beans are created when the Spring container loads the `spring.xml` file.

### Example

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien"/>
```

---

## Prototype

* A new object is created every time `getBean()` is called.
* Spring does not reuse previously created objects.
* The constructor executes every time a new object is requested.

### Example

```xml
<bean id="alien1"
      class="com.barbighaiya.Spring_First_Project.Alien"
      scope="prototype"/>
```

---

# 3. Setter Injection

Setter Injection is used to inject values or objects into a bean using **setter methods**.

Spring automatically calls the appropriate setter method.

### Injecting Primitive Values

```xml
<property name="age" value="23"/>
```

Spring internally executes:

```java
alien.setAge(23);
```

---

# 4. Reference Injection (Injecting Objects)

Reference Injection is used when one bean depends on another bean.

Instead of using `value`, we use the **ref** attribute.

### Example

```xml
<property name="lap" ref="lap1"/>
```

Spring internally executes:

```java
alien.setLap(lap1);
```

where `lap1` is another bean managed by the Spring container.

---

# 5. Important Rule

The `<property>` tag **always goes inside the bean that owns the variable**.

Example:

```java
class Alien {

    private Laptop lap;

}
```

Since the variable `lap` belongs to the `Alien` class, the property must be written inside the `Alien` bean.

### Correct

```xml
<bean id="alien1" class="Alien">

    <property name="lap" ref="lap1"/>

</bean>
```

### Incorrect

```xml
<bean id="lap1" class="Laptop">

    <property name="lap" ref="alien1"/>

</bean>
```

Reason:

The `Laptop` class does not contain a `lap` property.

---

# 6. Constructor Injection

Constructor Injection is used to inject values or objects through a **parameterized constructor** instead of setter methods.

Spring automatically calls the required constructor.

### Example

```xml
<constructor-arg value="23"/>
```

Spring internally executes:

```java
new Alien(23);
```

---

# 7. Injecting Objects using Constructor Injection

To inject another bean through the constructor, use the `ref` attribute.

### Example

```xml
<constructor-arg ref="lap1"/>
```

Spring internally executes:

```java
new Alien(lap1);
```

If the constructor contains multiple parameters:

```java
new Alien(23, lap1);
```

---

# 8. `<constructor-arg>` Tag

The `<constructor-arg>` tag is used to call a parameterized constructor.

It can inject:

* Primitive values
* String values
* Object references

It can be written in two ways:

### Self-closing tag

```xml
<constructor-arg value="23"/>
```

### Opening and closing tag

```xml
<constructor-arg value="23"></constructor-arg>
```

Both are exactly the same.

---

# 9. Ways to Specify `<constructor-arg>`

Spring provides three ways to identify constructor parameters.

---

## Method 1: Using `type`

Spring matches the constructor argument using its data type.

### Example

```xml
<constructor-arg type="int" value="23"/>
<constructor-arg type="com.barbighaiya.Spring_First_Project.Laptop" ref="lap1"/>
```

### Advantages

* Easy to understand.
* Useful when all constructor parameter types are different.

### Limitation

If multiple constructor parameters have the same data type, Spring may become confused while choosing the correct parameter.

---

## Method 2: Using `name`

Spring matches the constructor argument using the constructor parameter name.

### Example

```xml
<constructor-arg name="age" value="23"/>
<constructor-arg name="lap" ref="lap1"/>
```

### Advantages

* Makes the XML more readable.
* The order of arguments becomes less important.

### Note

Always use **ref** for object injection.

---

## Method 3: Using `index`

Spring matches the constructor argument using its position.

Index numbering starts from **0**.

### Example

```xml
<constructor-arg index="0" value="23"/>
<constructor-arg index="1" ref="lap1"/>
```

### Advantages

* Most explicit method.
* Avoids ambiguity.
* Recommended when multiple constructor parameters have the same data type.

---

# 10. Comparison

| Method  | Matches By                     | Best Used When                                                                                      |
| ------- | ------------------------------ | --------------------------------------------------------------------------------------------------- |
| `type`  | Parameter data type            | Constructor parameters have different data types.                                                   |
| `name`  | Constructor parameter name     | XML should be more readable.                                                                        |
| `index` | Constructor parameter position | Multiple constructor parameters have the same data type or exact parameter positions are preferred. |

---

# 11. `value` vs `ref`

| Attribute | Used For                         |
| --------- | -------------------------------- |
| `value`   | Primitive data types and Strings |
| `ref`     | Injecting another Spring bean    |

### Examples

Primitive value:

```xml
<constructor-arg value="23"/>
```

Object reference:

```xml
<constructor-arg ref="lap1"/>
```

---

# 12. Why is the Laptop Bean Created Separately?

```xml
<bean id="lap1"
      class="com.barbighaiya.Spring_First_Project.Laptop"/>
```

Before Spring can inject a `Laptop` object into the `Alien` object, it must first create and manage the `Laptop` bean.

Only objects managed by the Spring IoC Container can be injected into other beans.

---

# 13. Multiple Beans of the Same Class

Spring allows us to create multiple objects of the same class by defining multiple bean tags with different IDs.

### Example

```xml
<bean id="alien1" class="Alien"/>

<bean id="alien2" class="Alien"/>
```

Each bean has a unique ID and represents a separate bean managed by the Spring container.

---

# Key Points to Remember

* A Bean is an object managed by the Spring IoC Container.
* Singleton is the default bean scope.
* Prototype creates a new object every time `getBean()` is called.
* Setter Injection uses `<property>`.
* Constructor Injection uses `<constructor-arg>`.
* Use `value` for primitive values and Strings.
* Use `ref` for injecting another bean.
* The `<property>` tag always belongs to the bean that owns the variable.
* The `Laptop` bean must be created before it can be injected into the `Alien` bean.
* Constructor arguments can be identified using `type`, `name`, or `index`.
* `index` is the most explicit approach when constructor parameters have similar data types.

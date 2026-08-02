# Spring MVC – ModelAndView & Need of `@ModelAttribute`

---

# Model

`Model` is used to pass data from the **Controller** to the **View (JSP)**.

### Flow

```text
Browser
   │
   ▼
Controller
   │
   │  Add Data to Model
   ▼
Model
   │
   ▼
JSP (View)
```

The controller adds data to the `Model` object, and that data becomes available in the JSP page.

### Example

```java
@RequestMapping("add")
public String add(@RequestParam int num1,
                  @RequestParam int num2,
                  Model model) {

    int result = num1 + num2;

    model.addAttribute("result", result);

    return "result";
}
```

Accessing the data in JSP:

```jsp
<h2>Result : ${result}</h2>
```

---

# Good Project Structure

```
src
 └── main
      ├── java
      ├── resources
      │      └── static
      │             ├── css
      │             ├── images
      │             └── js
      │
      └── webapp
             └── views
                    ├── index.jsp
                    └── result.jsp
```

### Best Practices

- Keep only JSP files inside the `views` folder.
- Store CSS, JavaScript, and Images inside `src/main/resources/static`.
- This keeps the project clean and organized.

---

# ModelAndView

`ModelAndView` is a Spring MVC class that combines **Model** and **View** into a single object.

Instead of returning only the view name and using a separate `Model`, we return one object containing:

- Model (Data)
- View Name

---

## Why do we use ModelAndView?

Suppose we want to pass an object to the JSP.

Example:

```java
Alien
{
    aid
    aname
}
```

We need to:

- Create the object
- Set its values
- Add it to the Model
- Specify the View name

`ModelAndView` allows us to perform all these tasks using a single object.

---

## Important Methods

### `addObject()`

Adds data to the Model.

```java
mv.addObject("alien", alien);
```

---

### `setViewName()`

Specifies which JSP page should be displayed.

```java
mv.setViewName("result");
```

---

## Example

```java
@RequestMapping("addAlien")
public ModelAndView add(@RequestParam("aid") int aid,
                        @RequestParam("aname") String aname,
                        ModelAndView mv) {

    Alien alien = new Alien();

    alien.setAid(aid);
    alien.setAname(aname);

    mv.addObject("alien", alien);
    mv.setViewName("result");

    return mv;
}
```

### JSP

```jsp
<p>${alien}</p>
```

### Output

```
Alien [aid=101, aname=Suruchi]
```

---

# Need of `@ModelAttribute`

The above approach works perfectly.

However, notice that we are using `@RequestParam` for every field.

For a form with many fields, the controller becomes lengthy.

Example:

```java
@RequestMapping("addAlien")
public ModelAndView add(

    @RequestParam int aid,
    @RequestParam String aname,
    @RequestParam String city,
    @RequestParam String email,
    @RequestParam long phone,
    @RequestParam int age,
    @RequestParam double salary,
    @RequestParam String department,

    ModelAndView mv) {

    ...
}
```

### Problems

- Too many `@RequestParam`
- Code becomes lengthy
- Difficult to read
- Difficult to maintain
- More chances of mistakes

To solve this problem, Spring provides **`@ModelAttribute`**.

---

# What is `@ModelAttribute`?

`@ModelAttribute` automatically binds the form data to a Java object (POJO).

Instead of receiving every field separately, Spring:

- Creates the object
- Calls the setter methods
- Stores all the submitted values automatically

This process is called **Data Binding**.

---

# Before `@ModelAttribute`

```java
@RequestMapping("addAlien")
public ModelAndView add(@RequestParam("aid") int aid,
                        @RequestParam("aname") String aname,
                        ModelAndView mv) {

    Alien alien = new Alien();

    alien.setAid(aid);
    alien.setAname(aname);

    mv.addObject("alien", alien);
    mv.setViewName("result");

    return mv;
}
```

---

# After `@ModelAttribute`

```java
@RequestMapping("addAlien")
public String addAlien(@ModelAttribute Alien alien) {

    return "result";
}
```

Notice how much simpler the code becomes.

No object creation.

No setter methods.

No multiple `@RequestParam`.

Spring automatically performs all these tasks.

---

# How Spring Performs Data Binding

Suppose the form is

```html
<form action="addAlien">

    <input type="text" name="aid">

    <input type="text" name="aname">

</form>
```

The submitted request is

```
addAlien?aid=101&aname=Suruchi
```

Spring internally performs

```java
Alien alien = new Alien();

alien.setAid(101);
alien.setAname("Suruchi");
```

So the controller simply receives

```java
Alien alien
```

---

# Using `@ModelAttribute`

```java
@RequestMapping("addAlien")
public String addAlien(@ModelAttribute Alien alien) {

    return "result";
}
```

Spring automatically

- Creates the object
- Calls the setter methods
- Binds request parameters
- Adds the object to the Model

---

# Is `@ModelAttribute` Mandatory?

No.

When the method parameter is a POJO, Spring automatically treats it as a Model Attribute.

Therefore, both methods work.

With annotation

```java
public String addAlien(@ModelAttribute Alien alien)
```

Without annotation

```java
public String addAlien(Alien alien)
```

Both perform data binding.

---

# Changing the Model Attribute Name

By default, Spring stores the object using the class name.

Example

```java
public String addAlien(Alien alien)
```

Access it in JSP

```jsp
${alien}
```

If you want a different name

```java
public String addAlien(@ModelAttribute("alien1") Alien alien)
```

Now access it using

```jsp
${alien1}
```

---

# `@ModelAttribute` on a Method

`@ModelAttribute` can also be applied to a method.

The returned value is automatically added to the Model before every controller request.

Example

```java
@ModelAttribute("course")
public String courseName() {

    return "Java";
}
```

Now every JSP can access

```jsp
<h2>Welcome to ${course} World!</h2>
```

Output

```
Welcome to Java World!
```

No need to add this attribute manually in every controller method.

---

# Complete Example

## Controller

```java
@ModelAttribute("course")
public String courseName() {

    return "Java";
}

@RequestMapping("addAlien")
public String addAlien(Alien alien) {

    return "result";
}
```

---

## index.jsp

```jsp
<form action="addAlien">

    <label>Alien ID</label>
    <input type="text" name="aid"><br>

    <label>Alien Name</label>
    <input type="text" name="aname"><br>

    <input type="submit">

</form>
```

---

## result.jsp

```jsp
<h2>Alien Object</h2>

<p>${alien}</p>

<h2>Welcome to ${course} World!</h2>
```

---

# Summary

| Feature | Model | ModelAndView | `@ModelAttribute` |
|----------|-------|--------------|-------------------|
| Purpose | Pass data to View | Pass Model and View together | Automatically bind form data to an object |
| Returns | View Name | ModelAndView Object | View Name |
| Object Creation | Manual | Manual | Automatic |
| Uses `@RequestParam` | Yes | Yes | No (for POJOs) |
| Best Use Case | Passing simple data | Returning both model and view together | Handling forms with multiple fields |

---

# Key Interview Points

- `Model` is used to pass data from the Controller to the View.
- `ModelAndView` combines Model and View into a single object.
- `addObject()` adds data to the Model.
- `setViewName()` specifies which view should be rendered.
- `@ModelAttribute` automatically binds request parameters to a Java object (POJO).
- It removes the need to manually create objects and call setter methods.
- `@ModelAttribute` can also be used on a method to add common data to the Model before every request.
- The `@ModelAttribute` annotation on a POJO parameter is optional because Spring performs automatic data binding by default.
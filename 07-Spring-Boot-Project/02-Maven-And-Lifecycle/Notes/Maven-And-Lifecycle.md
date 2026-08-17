# Maven & Maven Build Lifecycle

## 1. What is Maven?

**Maven** is a build automation and dependency management tool used mainly for Java projects.

Maven helps us to:

- Manage project dependencies
- Compile Java source code
- Run test cases
- Package the application
- Create JAR/WAR files
- Manage the project build process

Maven uses a file called **`pom.xml`** to understand the project configuration.

---

# 2. What is `pom.xml`?

`pom.xml` stands for:

**Project Object Model**

It is the main configuration file of a Maven project.

It contains information such as:

- Project name
- Group ID
- Artifact ID
- Version
- Dependencies
- Plugins
- Java version
- Build configuration

Example project structure:

```text
MyFirstSpringBootProject
│
├── pom.xml
│
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   │
│   └── test
│       └── java
│
└── target
```

---

# 3. Basic Terminal Commands

Before running Maven commands, we need to be inside the project directory containing `pom.xml`.

## `cd`

`cd` = **Change Directory**

Used to move from one directory to another.

Example:

```bash
cd "/c/Users/barbi/OneDrive/Desktop/Development Workspace/MyFirstSpringBootProject"
```

### Why use it?

To move into the Maven project directory.

---

## `pwd`

`pwd` = **Print Working Directory**

Shows the current directory.

```bash
pwd
```

Example output:

```text
/c/Users/barbi/OneDrive/Desktop/Development Workspace/MyFirstSpringBootProject
```

### Why use it?

To check where the terminal is currently located.

---

## `ls`

`ls` = **List**

Shows the files and folders present in the current directory.

```bash
ls
```

Example:

```text
pom.xml
src
target
```

### Why use it?

To check whether we are in the correct directory and to see the project files.

---

## `cd ..`

Moves one level back to the parent directory.

```bash
cd ..
```

Example:

```text
MyFirstSpringBootProject
        │
        └── target
```

If we are inside `target`:

```bash
cd ..
```

takes us back to:

```text
MyFirstSpringBootProject
```

---

# 4. Maven Command

Maven commands generally start with:

```bash
mvn
```

Example:

```bash
mvn compile
```

Here:

- `mvn` → Maven command
- `compile` → Maven lifecycle phase

---

# 5. Maven Build Lifecycle

A **Maven Build Lifecycle** is a predefined sequence of phases that Maven follows to build a project.

The important Default/Build Lifecycle phases are:

```text
validate
    ↓
compile
    ↓
test
    ↓
package
    ↓
verify
    ↓
install
    ↓
deploy
```

We normally do not execute all phases manually.

If we execute a later phase, Maven automatically executes the required earlier phases.

---

# 6. `mvn validate`

Command:

```bash
mvn validate
```

### Purpose

Checks whether the Maven project is valid and properly configured.

Maven reads the project's `pom.xml`.

### In simple words:

> "Is my Maven project properly configured?"

Example result:

```text
BUILD SUCCESS
```

---

# 7. `mvn compile`

Command:

```bash
mvn compile
```

### Purpose

Compiles the main Java source code.

Java source files:

```text
src/main/java
```

are compiled into bytecode:

```text
target/classes
```

Flow:

```text
.java files
    ↓
compile
    ↓
.class files
```

### In simple words:

> "Can my Java code be compiled successfully?"

If there is a Java compilation error, `mvn compile` will fail.

---

# 8. `mvn test`

Command:

```bash
mvn test
```

### Purpose

Runs the project's test cases.

Test source code is normally present in:

```text
src/test/java
```

Flow:

```text
Compile application
       ↓
Compile test code
       ↓
Run tests
       ↓
Test result
```

Example:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

### Meaning:

- Tests run: `1` → One test was executed
- Failures: `0` → No test failed
- Errors: `0` → No test produced an error
- Skipped: `0` → No test was skipped

### In simple words:

> "Does my application pass its automated tests?"

---

# 9. `mvn package`

Command:

```bash
mvn package
```

### Purpose

Packages the application into a distributable file.

For a Spring Boot Maven project, this generally produces a **JAR** file.

Example:

```text
target/
└── MyFirstSpringBootProject-0.0.1-SNAPSHOT.jar
```

### In simple words:

> "Create the final packaged application."

---

# 10. Important Concept: Later Phases Include Earlier Phases

Suppose we run:

```bash
mvn package
```

Maven does not only perform `package`.

It goes through the required earlier phases:

```text
validate
    ↓
compile
    ↓
test
    ↓
package
```

Similarly:

```bash
mvn test
```

runs:

```text
validate
    ↓
compile
    ↓
test
```

Therefore, we don't normally need to run:

```bash
mvn validate
mvn compile
mvn test
mvn package
```

one after another.

We can simply run:

```bash
mvn package
```

when we want to package the application.

---

# 11. `verify`

Command:

```bash
mvn verify
```

### Purpose

Performs additional checks on the project after testing and packaging-related steps.

### In simple words:

> "Perform additional verification checks on my project."

This is not something you need to focus on initially.

---

# 12. `install`

Command:

```bash
mvn install
```

### Purpose

Builds the project and installs the generated artifact into the **local Maven repository**.

The local Maven repository is normally located at:

```text
C:\Users\<username>\.m2\repository
```

Flow:

```text
Project
   ↓
Build
   ↓
JAR
   ↓
Local Maven Repository
```

### In simple words:

> "Build my project and make its artifact available locally."

This becomes more useful when working with multiple Maven projects.

---

# 13. `deploy`

Command:

```bash
mvn deploy
```

### Purpose

Builds the project and uploads the generated artifact to a **remote Maven repository**.

This is commonly used in professional/company environments.

### In simple words:

> "Build my project and publish it to a remote Maven repository."

You don't need to focus on this command right now.

---

# 14. Clean Lifecycle

Maven also has a separate **Clean Lifecycle**.

The important command is:

```bash
mvn clean
```

### Purpose

Removes previously generated build files, mainly the `target` directory.

Before:

```text
MyFirstSpringBootProject
│
├── pom.xml
├── src
└── target
```

After:

```bash
mvn clean
```

the generated `target` directory is removed.

### In simple words:

> "Remove the previous build output."

---

# 15. `mvn clean package`

This is a very useful command.

```bash
mvn clean package
```

It means:

```text
Delete old build
       ↓
Validate
       ↓
Compile
       ↓
Test
       ↓
Package
       ↓
Create new JAR
```

This is useful when you want to perform a **fresh build**.

---

# 16. Maven Lifecycle vs Phase

### Lifecycle

A complete sequence of build processes.

Example:

```text
Default Lifecycle
```

### Phase

An individual step inside a lifecycle.

Examples:

```text
validate
compile
test
package
verify
install
deploy
```

Relationship:

```text
Lifecycle
    ↓
  Phase
```

---

# 17. Maven Plugins and Goals

Maven uses **plugins** to perform actual tasks.

For example, the compiler plugin is responsible for compiling Java code.

A **goal** is a specific task provided by a plugin.

So remember:

```text
Maven Lifecycle
       ↓
     Phase
       ↓
     Plugin
       ↓
      Goal
```

You don't need to memorize plugin goals at this stage. Just understand that Maven phases are implemented using plugins.

---

# 18. Important Maven Project Directory

```text
MyFirstSpringBootProject
│
├── pom.xml
│
├── src
│   │
│   ├── main
│   │   ├── java
│   │   └── resources
│   │
│   └── test
│       └── java
│
└── target
```

### `pom.xml`

Maven project configuration.

### `src/main/java`

Main Java application code.

### `src/main/resources`

Application resources such as:

```text
application.properties
```

### `src/test/java`

Test code.

### `target`

Generated build files.

Examples:

```text
target/classes
target/test-classes
target/*.jar
```

---

# 19. Important Rule About `pom.xml`

Run Maven commands from the **project root directory**, where `pom.xml` exists.

Correct:

```text
MyFirstSpringBootProject>
```

because:

```text
MyFirstSpringBootProject
└── pom.xml
```

Incorrect:

```text
MyFirstSpringBootProject\target>
```

because `target` normally does not contain `pom.xml`.

If you accidentally enter `target`, go back:

```bash
cd ..
```

Then run your Maven command.

---

# 20. Maven Commands – Quick Revision

| Command | Purpose |
|---|---|
| `mvn validate` | Check project configuration |
| `mvn compile` | Compile main Java code |
| `mvn test` | Run test cases |
| `mvn package` | Create JAR/WAR package |
| `mvn verify` | Perform additional verification |
| `mvn install` | Install artifact into local Maven repository |
| `mvn deploy` | Upload artifact to remote repository |
| `mvn clean` | Remove previous build output |
| `mvn clean package` | Clean and create a fresh package |

---

# 21. Easy Way to Remember the Lifecycle

Remember it as:

```text
VALIDATE
   ↓
"Is my project valid?"

COMPILE
   ↓
"Can my Java code compile?"

TEST
   ↓
"Does my code pass tests?"

PACKAGE
   ↓
"Can I create my JAR/WAR?"

VERIFY
   ↓
"Are additional checks successful?"

INSTALL
   ↓
"Put it in my local Maven repository."

DEPLOY
   ↓
"Publish it to a remote repository."
```

---

# 22. Most Important Commands for Spring Boot Beginners

For now, focus mainly on:

```bash
mvn clean
mvn compile
mvn test
mvn package
```

Especially remember:

```bash
mvn clean package
```

This performs a clean build and creates the packaged application.

---

# Final Revision

```text
                MAVEN
                  │
                  ↓
             pom.xml
                  │
                  ↓
        Maven Build Lifecycle
                  │
     ┌────────────┼────────────┐
     ↓            ↓            ↓
 validate      compile       test
     │            │            │
     └────────────┼────────────┘
                  ↓
               package
                  ↓
                JAR
                  ↓
               target/
```

## One-line Definitions

```text
Maven       → Build & dependency management tool
pom.xml     → Maven project configuration file
validate    → Validate project
compile     → Compile Java code
test        → Run tests
package     → Create JAR/WAR
verify      → Perform additional checks
install     → Install artifact locally
deploy      → Publish artifact remotely
clean       → Remove previous build output
target      → Directory containing generated build files
```

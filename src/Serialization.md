# Java Serialization and Deserialization

## 1. What is Serialization in Java?

**Serialization** is the process of converting an object's state into a byte stream so that it can be stored or transferred.

In simple terms, serialization converts a Java object into a sequence of bytes.

These bytes can be:

1. Stored in a file.
2. Transferred over a network.
3. Sent to another application.
4. Used for later reconstruction of the object.

### Real-Life Example

Suppose we have a Student object:

```java
class Student {

    int id = 101;
    String name = "Jasraj";

}
```

The object contains data:

```text
Student Object
    |
    |---- id = 101
    |
    |---- name = "Jasraj"
```

When we serialize this object, Java converts its serializable object state into a byte stream.

```text
Student Object
      |
      | Serialization
      v
   Byte Stream
      |
      v
   File / Network
```

The byte stream can later be used to reconstruct the object.

## 2. What is Deserialization in Java?

**Deserialization** is the reverse process of serialization.

It converts a previously serialized byte stream back into a Java object.

For example:

```text
   Byte Stream
       |
       | Deserialization
       v
   Student Object
       |
       |---- id = 101
       |
       |---- name = "Jasraj"
```

### Serialization vs Deserialization

| Serialization                          | Deserialization                       |
|----------------------------------------|---------------------------------------|
| Converts an object into a byte stream  | Converts a byte stream into an object |
| Used to store or transfer object state | Used to reconstruct object state      |
| Uses `ObjectOutputStream`              | Uses `ObjectInputStream`              |
| Uses `writeObject()`                   | Uses `readObject()`                   |

## 3. What is the Serializable Interface?

`Serializable` is a marker interface available in the `java.io` package.

It is used to indicate that objects of a class are eligible for Java's default serialization mechanism.

**Syntax:**

```java
import java.io.Serializable;

class Student implements Serializable {

    int id;
    String name;

}
```

### Why is Serializable Called a Marker Interface?

A marker interface is an interface that does not contain abstract methods.

It marks a class as having a particular property or capability.

`Serializable` does not provide serialization methods that we must implement.

Instead, it tells Java's serialization mechanism that objects of this class are eligible for serialization.

**Important points:**

1. `Serializable` belongs to the `java.io` package.
2. It is a marker interface.
3. It does not contain abstract methods.
4. A class implements it using the `implements` keyword.
5. Its implementation enables the default serialization mechanism for eligible object state.
6. If an object is not serializable, attempting to serialize it using `ObjectOutputStream` can result in `NotSerializableException`.

## 4. Classes Used in Serialization and Deserialization

Java provides classes in the `java.io` package to perform serialization and deserialization.

The two most important classes are:

### 4.1 ObjectOutputStream

`ObjectOutputStream` is used to write objects into an output stream.

It converts the serializable object graph into a byte stream.

Important method:

```java
writeObject(Object obj);
```

Example:

````
ObjectOutputStream out =
        new ObjectOutputStream(new FileOutputStream("student.ser"));

out.writeObject(student);
````

Here:

- `ObjectOutputStream` writes objects.
- `writeObject()` performs the serialization operation.
- `student.ser` is the file where the serialized data is stored.

### 4.2 ObjectInputStream

`ObjectInputStream` is used to read serialized objects from an input stream.

Important method:

```java
readObject();
```

Example:

```java
ObjectInputStream in =
        new ObjectInputStream(new FileInputStream("student.ser"));

Student student = (Student) in.readObject();
```

Here:

- `ObjectInputStream` reads serialized data.
- `readObject()` reconstructs the object.
- Typecasting is required because `readObject()` returns `Object`.

### Important Comparison

| Class                | Purpose                  | Important Method |
|----------------------|--------------------------|------------------|
| `ObjectOutputStream` | Writes objects           | `writeObject()`  |
| `ObjectInputStream`  | Reads objects            | `readObject()`   |
| `FileOutputStream`   | Writes bytes into a file | `write()`        |
| `FileInputStream`    | Reads bytes from a file  | `read()`         |

## 5. Complete Example of Serialization and Deserialization

Let's create a complete working program.

### Step 1: Create a Serializable Class

```java
import java.io.Serializable;

class Student implements Serializable {

    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {

        this.id = id;
        this.name = name;
        this.marks = marks;

    }

    void display() {

        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks);

    }
}
```

### Step 2: Serialize and Deserialize the Object

```java
import java.io.*;

public class Driver {

    public static void main(String[] args) {

        Student student = new Student(101, "Jasraj", 85.5);

        // Serialization

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("student.ser"))) {

            out.writeObject(student);

            System.out.println("Serialization completed");

        }
        catch (IOException e) {

            e.printStackTrace();

        }

        // Deserialization

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("student.ser"))) {

            Student obj = (Student) in.readObject();

            System.out.println("Deserialization completed");

            obj.display();

        }
        catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }
}
```

**Output:**

```text
Serialization completed
Deserialization completed
ID: 101
Name: Jasraj
Marks: 85.5
```

### Understanding the Flow

**During Serialization:**

1. We create a Student object.
2. We create a `FileOutputStream` to write bytes into a file.
3. We wrap it inside `ObjectOutputStream`.
4. We call `writeObject(student)`.
5. Java serializes the eligible object state.
6. The byte stream is written into `student.ser`.

**During Deserialization:**

1. We create a `FileInputStream` to read the file.
2. We wrap it inside `ObjectInputStream`.
3. We call `readObject()`.
4. Java reconstructs the serialized object.
5. We typecast the returned Object into Student.
6. We access the reconstructed object's data.

## 6. Understanding serialVersionUID

`serialVersionUID` is a version identifier used during Java serialization and deserialization.

It helps Java verify whether the serialized class and the currently available class are compatible.

It is declared as a static final long variable.

**Syntax:**

```java
private static final long serialVersionUID = 1L;
```

### Example

```java
import java.io.Serializable;

class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String name;

}
```

### Why Do We Need serialVersionUID?

Suppose:

1. We create a Student class.
2. We serialize a Student object.
3. Later, we modify the Student class.
4. We try to deserialize the old serialized object using the modified class.

Java checks the class version identifier.

If the serialized object's `serialVersionUID` does not match the current class's `serialVersionUID`, deserialization can fail with `InvalidClassException`.

### Example Scenario

**Original class:**

```java
class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String name;

}
```

We serialize an object using this class.

Later, we change the class:

```java
class Student implements Serializable {

    private static final long serialVersionUID = 2L;

    int id;
    String name;

}
```

Now, when we attempt to deserialize the old object, Java detects the mismatch and throws `InvalidClassException`.

### Important Points

1. `serialVersionUID` is used for version compatibility.
2. It must be declared as `static final long`.
3. It is recommended to declare it explicitly in serializable classes.
4. If it is not declared, Java can generate one automatically based on class details.
5. Changes to class structure can cause the automatically generated identifier to change.
6. A matching `serialVersionUID` does not guarantee that every class change is compatible; the serialized form and field changes must also be considered.

## 7. What is the Transient Keyword?

`transient` is a Java keyword used with instance variables.

It tells the default serialization mechanism to exclude that variable from serialization.

This is useful when we do not want certain object fields to be persisted in the serialized form.

### Example

```java
import java.io.Serializable;

class Student implements Serializable {

    int id;
    String name;
    transient String password;

    Student(int id, String name, String password) {

        this.id = id;
        this.name = name;
        this.password = password;

    }

    void display() {

        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Password: " + password);

    }
}
```

Driver class:

```java
import java.io.*;

public class Driver {

    public static void main(String[] args) {

        Student student =
                new Student(101, "Jasraj", "abc123");

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("student.ser"))) {

            out.writeObject(student);

        }
        catch (IOException e) {

            e.printStackTrace();

        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("student.ser"))) {

            Student obj = (Student) in.readObject();

            obj.display();

        }
        catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }
}
```

**Output:**

```text
ID: 101
Name: Jasraj
Password: null
```

### Why is Password null?

1. The password variable is declared as `transient`.
2. Java excludes it from default serialization.
3. During deserialization, the field receives its default value.
4. Since password is a String reference, its default value is `null`.

### Default Values After Deserialization

| Data Type        | Default Value |
|------------------|---------------|
| `int`            | 0             |
| `long`           | 0L            |
| `double`         | 0.0           |
| `float`          | 0.0f          |
| `boolean`        | false         |
| `char`           | `'\u0000'`    |
| Object reference | null          |

**Important:** Transient fields can be assigned values again using custom deserialization logic, such as a custom `readObject()` method.

## 8. What Happens to Static Variables During Serialization?

Static variables belong to the class, not to individual objects.

Therefore, static variables are not part of the default serialized object state.

Example:

```java
import java.io.Serializable;

class Student implements Serializable {

    int id;

    String name;

    static String college = "ABC College";

}
```

Suppose:

```java
Student student = new Student();

student.id = 101;
student.name = "Jasraj";
```

When the object is serialized:

- `id` is serialized.
- `name` is serialized.
- `college` is not serialized as part of the object state because it is static.

During deserialization, the static variable is accessed from the currently loaded class.

**Important:** If the static variable changes before deserialization, the deserialized object observes the current static value, not the value that existed when the object was serialized.

## 9. Serialization and Inheritance

Serialization becomes particularly interesting when inheritance is involved.

Let's understand the different cases.

### Case 1: Parent and Child Both Implement Serializable

```java
import java.io.Serializable;

class Parent implements Serializable {

    int parentId = 100;

}

class Child extends Parent {

    int childId = 200;

}
```

Here, Child inherits Serializable from Parent.

Therefore, objects of Child are serializable.

Both parent and child serializable instance fields are included in the serialized state.

### Case 2: Parent Does Not Implement Serializable, but Child Does

```java
import java.io.Serializable;

class Parent {

    int parentId = 100;

    Parent() {

        System.out.println("Parent constructor");

    }

}

class Child extends Parent implements Serializable {

    int childId = 200;

    Child() {

        System.out.println("Child constructor");

    }

}
```

Driver:

```java
import java.io.*;

public class Driver {

    public static void main(String[] args) {

        Child child = new Child();

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("child.ser"))) {

            out.writeObject(child);

        }
        catch (IOException e) {

            e.printStackTrace();

        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("child.ser"))) {

            Child obj = (Child) in.readObject();

            System.out.println(obj.parentId);
            System.out.println(obj.childId);

        }
        catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }
}
```

**Output:**

```text
Parent constructor
Child constructor
Parent constructor
100
200
```

### Why Does the Parent Constructor Execute Again?

This is an important concept.

When an object is deserialized:

1. The constructors and field initializers of serializable classes are not executed through normal object construction.
2. The no-argument constructor of the first non-serializable superclass is invoked.
3. The serializable class fields are restored from the serialized data.
4. The non-serializable superclass's instance fields retain the values assigned during its constructor and field initialization.

In this example:

- `Child` implements Serializable.
- `Parent` does not implement Serializable.
- During deserialization, the no-argument constructor of Parent executes.
- The Parent constructor initializes `parentId`.
- The serialized value of `childId` is restored.

**Important rule:** The first non-serializable superclass must have an accessible no-argument constructor for ordinary Java deserialization to succeed.

### Case 3: Parent Does Not Have a No-Argument Constructor

If the first non-serializable superclass does not have an accessible no-argument constructor, deserialization fails.

Example:

```java
import java.io.Serializable;

class Parent {

    Parent(int id) {

        System.out.println("Parent constructor");

    }

}

class Child extends Parent implements Serializable {

    Child() {

        super(100);

    }

}
```

The Child object can be serialized, but ordinary deserialization fails because Parent has no accessible no-argument constructor.

## 10. Object Graph Serialization

Serialization is not limited to one object.

An object may contain references to other objects.

This is called an object graph.

Example:

```java
import java.io.Serializable;

class Address implements Serializable {

    String city;

    Address(String city) {

        this.city = city;

    }

}

class Student implements Serializable {

    int id;

    Address address;

    Student(int id, Address address) {

        this.id = id;
        this.address = address;

    }

}
```

Here:

```text
Student Object
     |
     |---- id
     |
     |---- address
                |
                |---- city
```

When the Student object is serialized, Java also serializes the referenced Address object.

**Important rule:** Every object reachable through the serialized object graph must be serializable, unless that reference is excluded or handled through custom serialization.

Otherwise, serialization can throw `NotSerializableException`.

## 11. What Happens If an Object Contains a Non-Serializable Object?

Consider this example:

```java
import java.io.Serializable;

class Address {

    String city = "Bengaluru";

}

class Student implements Serializable {

    int id = 101;

    Address address = new Address();

}
```

Here:

- Student implements Serializable.
- Address does not implement Serializable.
- Student contains an Address reference.

When we try to serialize the Student object, Java attempts to serialize the referenced Address object too.

Since Address is not serializable, Java throws:

```text
java.io.NotSerializableException: Address
```

### How Can We Solve This?

Possible solutions:

1. Make Address implement Serializable.
2. Mark the Address reference as transient if it should not be serialized.
3. Implement custom serialization logic to control how the Address data is stored.

## 12. Custom Serialization

Java provides a default serialization mechanism.

However, sometimes we need more control over which fields are serialized or how they are restored.

For this purpose, we can define special private methods inside the serializable class.

The most commonly used methods are:

1. `writeObject()`
2. `readObject()`

### 12.1 Custom writeObject()

The `writeObject()` method allows us to customize serialization.

Its conventional signature is:

```java
private void writeObject(ObjectOutputStream out)
        throws IOException
```

It is called by Java's serialization mechanism when serializing an object of the class.

### 12.2 Custom readObject()

The `readObject()` method allows us to customize deserialization.

Its conventional signature is:

```java
private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
```

It is called by Java's serialization mechanism when deserializing an object of the class.

### Complete Example

```java
import java.io.*;

class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;

    String name;

    transient String password;

    Student(int id, String name, String password) {

        this.id = id;
        this.name = name;
        this.password = password;

    }

    private void writeObject(ObjectOutputStream out)
            throws IOException {

        out.defaultWriteObject();

        out.writeObject(password);

    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        in.defaultReadObject();

        password = (String) in.readObject();

    }

    void display() {

        System.out.println(id);
        System.out.println(name);
        System.out.println(password);

    }
}
```

Driver:

```java
import java.io.*;

public class Driver {

    public static void main(String[] args) {

        Student student =
                new Student(101, "Jasraj", "abc123");

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("student.ser"))) {

            out.writeObject(student);

        }
        catch (IOException e) {

            e.printStackTrace();

        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("student.ser"))) {

            Student obj = (Student) in.readObject();

            obj.display();

        }
        catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }
}
```

**Output:**

```text
101
Jasraj
abc123
```

### Understanding defaultWriteObject() and defaultReadObject()

- `defaultWriteObject()` writes the normal serializable fields using Java's default serialization mechanism.
- `defaultReadObject()` restores those fields during deserialization.
- We can then explicitly write or restore additional data using the stream.

**Important:** Custom serialization methods must follow the expected signatures and visibility for Java's serialization mechanism to recognize them.

**Security note:** Although this example demonstrates custom serialization, real passwords and authentication secrets should not be stored in serialized form as plain text.

## 13. Externalizable Interface

`Externalizable` is another interface used for custom serialization.

It belongs to the `java.io` package.

Unlike Serializable, Externalizable requires the developer to explicitly implement two methods:

1. `writeExternal()`
2. `readExternal()`

### Example

```java
import java.io.*;

class Student implements Externalizable {

    int id;

    String name;

    public Student() {
        // Required public no-argument constructor
    }

    Student(int id, String name) {

        this.id = id;
        this.name = name;

    }

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {

        out.writeInt(id);

        out.writeObject(name);

    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {

        id = in.readInt();

        name = (String) in.readObject();

    }
}
```

### Difference Between Serializable and Externalizable

| Feature       | Serializable                                                         | Externalizable                                                   |
|---------------|----------------------------------------------------------------------|------------------------------------------------------------------|
| Type          | Marker interface                                                     | Interface with two abstract methods                              |
| Package       | `java.io`                                                            | `java.io`                                                        |
| Serialization | Default mechanism provided                                           | Developer controls the serialized form                           |
| Methods       | No methods to implement                                              | `writeExternal()` and `readExternal()`                           |
| Constructor   | Normal serialization does not invoke serializable-class constructors | Public no-argument constructor is invoked during deserialization |
| Control       | Less direct control                                                  | Explicit control over serialization                              |

**Important:** Externalizable requires a public no-argument constructor because Java creates the object using that constructor during deserialization.

## 14. writeReplace() and readResolve()

Java serialization provides two additional special methods.

### 14.1 writeReplace()

`writeReplace()` allows a class to substitute another object for itself during serialization.

Example signature:

```java
private Object writeReplace() throws ObjectStreamException
```

It is useful when we want to serialize a replacement object instead of the original object.

### 14.2 readResolve()

`readResolve()` allows a class to replace the newly deserialized object with another object.

Example signature:

```java
private Object readResolve() throws ObjectStreamException
```

It is commonly used with Singleton classes to preserve a single instance after deserialization.

### Singleton Example

```java
import java.io.*;

class Singleton implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {

        return INSTANCE;

    }

    private Object readResolve() throws ObjectStreamException {

        return INSTANCE;

    }
}
```

**Explanation:**

During deserialization, Java normally creates a new object.

The `readResolve()` method replaces that newly deserialized object with the existing Singleton instance.

This helps preserve the Singleton property during deserialization.

## 15. What is NotSerializableException?

`NotSerializableException` is an exception in Java's serialization mechanism.

It occurs when we attempt to serialize an object that is not serializable, including a non-serializable object encountered through the serialized object graph.

It belongs to the `java.io` package.

Example:

```java
import java.io.*;

class Student {

    int id = 101;

}

public class Driver {

    public static void main(String[] args) throws IOException {

        Student student = new Student();

        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream("student.ser"));

        out.writeObject(student);

        out.close();

    }
}
```

**Result:**

The program throws `NotSerializableException` because Student does not implement Serializable.

## 16. What is InvalidClassException?

`InvalidClassException` is a checked exception in Java's serialization mechanism.

It can occur when a serialized class is incompatible with the current class definition.

One common reason is a `serialVersionUID` mismatch.

Other serialization compatibility problems can also cause this exception.

Example:

```text
java.io.InvalidClassException: Student;
local class incompatible:
stream classdesc serialVersionUID = 1,
local class serialVersionUID = 2
```

**Important:** It is a subclass of `ObjectStreamException`, which is a subclass of `IOException`.

## 17. What Happens to Constructors During Deserialization?

This is an important Java interview concept.

For ordinary Serializable classes:

1. The constructor of the serializable class is not invoked during deserialization.
2. The instance field initializers of the serializable class are not executed through normal construction.
3. The no-argument constructor of the first non-serializable superclass is invoked.
4. The serializable object's state is restored from the stream.
5. Custom `readObject()` logic can further modify restored state.

Example:

```java
import java.io.Serializable;

class Student implements Serializable {

    int id;

    Student() {

        System.out.println("Student constructor");

    }

}
```

If we create an object normally, the constructor executes.

If we deserialize the object, the Student constructor does not execute through normal object construction.

**Important:** This rule applies to ordinary Serializable classes. Externalizable classes follow different constructor rules.

## 18. Can We Serialize Multiple Objects?

Yes.

We can serialize multiple objects using the same `ObjectOutputStream`.

Example:

```
ObjectOutputStream out =
        new ObjectOutputStream(
                new FileOutputStream("data.ser"));

out.writeObject(student1);

out.writeObject(student2);

out.writeObject(student3);

out.close();
```

During deserialization, read them in the same order:

```java
ObjectInputStream in =
        new ObjectInputStream(
                new FileInputStream("data.ser"));

Student s1 = (Student) in.readObject();

Student s2 = (Student) in.readObject();

Student s3 = (Student) in.readObject();

in.close();
```

**Important:** The objects must be read in the same sequence in which they were written.

If the number of serialized objects is unknown, the stream must be read using an appropriate framing or end-of-data strategy. Do not simply catch every IOException and treat it as normal end-of-file.

## 19. Object References and Circular References During Serialization

Java serialization supports object graphs containing repeated references and circular references.

Example:

```java
import java.io.Serializable;

class Employee implements Serializable {

    String name;

    Employee manager;

}
```

Suppose two Employee objects refer to each other.

```text
Employee A
    |
    v
Employee B
    |
    v
Employee A
```

Java serialization maintains object identity information through stream handles.

It does not endlessly serialize the same object whenever it encounters another reference to it.

During deserialization, the references can be reconstructed while preserving the object graph's relationships.

This is an important reason why Java serialization can handle circular references.

## 20. Security Considerations of Serialization

Serialization has important security considerations.

1. Do not deserialize untrusted data.
2. A malicious serialized stream can exploit unsafe deserialization behaviour.
3. Deserialization can execute class-specific deserialization logic.
4. Avoid serializing passwords, authentication tokens, or sensitive information in plain text.
5. Use appropriate input validation and deserialization filters where applicable.
6. Prefer safer data formats and explicit data-transfer objects when exchanging data with untrusted systems.

**Important:** Java native serialization should not automatically be treated as a secure communication format.

## 21. Important Serialization Exceptions

| Exception                  | Meaning                                                                                                           |
|----------------------------|-------------------------------------------------------------------------------------------------------------------|
| `NotSerializableException` | An object that is not serializable was encountered during serialization.                                          |
| `InvalidClassException`    | The serialized class is incompatible with the local class definition.                                             |
| `StreamCorruptedException` | The serialization stream has invalid or inconsistent control information.                                         |
| `OptionalDataException`    | Primitive data was encountered when object data was expected, or the stream ended in a particular unexpected way. |
| `WriteAbortedException`    | Serialization was aborted due to an exception during writing.                                                     |
| `EOFException`             | The input stream unexpectedly reached its end.                                                                    |

## 22. Serialization vs Deserialization vs Cloning

These concepts are different.

| Feature               | Serialization                            | Deserialization                           | Cloning                               |
|-----------------------|------------------------------------------|-------------------------------------------|---------------------------------------|
| Purpose               | Converts object state into a byte stream | Reconstructs an object from a byte stream | Creates another object                |
| Main mechanism        | `ObjectOutputStream`                     | `ObjectInputStream`                       | `clone()` or another copying strategy |
| Main use              | Persistence and transfer                 | Restoration                               | Object copying                        |
| Requires Serializable | For default Java object serialization    | For default Java object deserialization   | No                                    |

## 23. Important Interview Questions

**Q1. What is serialization?**

Serialization is the process of converting an object's state into a byte stream.

**Q2. What is deserialization?**

Deserialization is the process of reconstructing an object from a serialized byte stream.

**Q3. What is Serializable?**

Serializable is a marker interface in the `java.io` package that enables default Java serialization for eligible object state.

**Q4. Why is Serializable called a marker interface?**

Because it does not contain abstract methods. It marks a class as eligible for serialization.

**Q5. Which classes are used for serialization and deserialization?**

- Serialization: `ObjectOutputStream`
- Deserialization: `ObjectInputStream`

**Q6. What is serialVersionUID?**

It is a version identifier used to check compatibility between the serialized class and the current class definition.

**Q7. What is transient?**

It is a keyword that excludes an instance variable from default serialization.

**Q8. Are static variables serialized?**

No. Static variables belong to the class and are not part of the default serialized object state.

**Q9. Can we serialize an object without implementing Serializable?**

Not through the ordinary default serialization mechanism. Attempting it results in `NotSerializableException`, unless another supported mechanism is used.

**Q10. What happens if an object contains a reference to a non-serializable object?**

Serialization can fail with `NotSerializableException`, unless that reference is excluded or handled appropriately.

**Q11. Does a constructor execute during deserialization?**

The constructor of an ordinary Serializable class is not executed through normal object construction. The no-argument constructor of its first non-serializable superclass is invoked.

**Q12. What happens if serialVersionUID does not match?**

Deserialization can fail with `InvalidClassException`.

**Q13. What is Externalizable?**

It is an interface that provides explicit control over serialization through `writeExternal()` and `readExternal()`.

**Q14. What is the difference between Serializable and Externalizable?**

Serializable uses Java's default serialization mechanism unless customized. Externalizable requires the developer to implement the serialization and deserialization logic explicitly.

**Q15. Can we serialize multiple objects into one file?**

Yes. We can write multiple objects using the same ObjectOutputStream and read them in the same order.

**Q16. Can serialization handle circular references?**

Yes. Java serialization maintains object references and handles circular object graphs.

**Q17. What is NotSerializableException?**

It is an IOException subclass thrown when a non-serializable object is encountered during serialization.

**Q18. What is readResolve()?**

It is a special serialization method that allows a deserialized object to be replaced with another object.

**Q19. What is writeReplace()?**

It allows an object to substitute another object for itself during serialization.

**Q20. Is Java serialization secure?**

Not automatically. Deserializing untrusted data can introduce serious security risks.

## 24. Quick Revision Table

| Concept                       | Meaning                                          |
|-------------------------------|--------------------------------------------------|
| Serialization                 | Object to byte stream                            |
| Deserialization               | Byte stream to object                            |
| Serializable                  | Marker interface                                 |
| ObjectOutputStream            | Writes serialized objects                        |
| ObjectInputStream             | Reads serialized objects                         |
| `writeObject()`               | Serializes an object                             |
| `readObject()`                | Deserializes an object                           |
| `serialVersionUID`            | Serialization version identifier                 |
| `transient`                   | Excludes a field from default serialization      |
| Static variable               | Not part of default serialized object state      |
| Externalizable                | Provides explicit serialization control          |
| `writeExternal()`             | Custom serialization method for Externalizable   |
| `readExternal()`              | Custom deserialization method for Externalizable |
| `writeObject()` custom method | Customizes default serialization                 |
| `readObject()` custom method  | Customizes default deserialization               |
| `writeReplace()`              | Substitutes an object during serialization       |
| `readResolve()`               | Substitutes an object after deserialization      |
| NotSerializableException      | Non-serializable object encountered              |
| InvalidClassException         | Class compatibility problem                      |

## 25. Conclusion

Serialization and deserialization are important Java concepts used for object persistence and data transfer.

Serialization converts an object's state into a byte stream, while deserialization reconstructs the object from that stream.

The `Serializable` interface enables Java's default serialization mechanism. `ObjectOutputStream` and `ObjectInputStream` perform the actual writing and reading operations.

Concepts such as `serialVersionUID`, `transient`, static variables, inheritance, custom serialization, and Externalizable are important for understanding serialization in depth.

These concepts are also useful when studying Java networking, distributed applications, caching, and backend development.
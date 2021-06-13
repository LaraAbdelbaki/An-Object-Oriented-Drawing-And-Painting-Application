Available features
(1) Undo/Redo any action taken by the user such as changing the fill color, resizing,
cloning, moving or even drawing an object.
(2) Drawing tools: Square, Rectangle, Line, Triangle, Circle, Ellipse(3) Resize tool: The user can use it to resize any object he desires by clicking on any edge
of this object, in case of the circle he just drags the outer bounds.
(4) Color fill: fill any object with the desired color
(5) Clone: Clone any selected object with an identical shifted version
(6) Copy/Paste: Copy the selected object in any desired place on the screen
(7) Move: move selected objects anywhere desired on the screen
(8) Clear all: delete everything on the canvas, this option can be undoed and all the shapes
will be visible again
(9) Delete item: only selects a single item for deletion
(10) Palette/brush tool: choose the outline color for the next object to be drawn
(11) Save file from the File menu in order to export the canvas drawn into a png file
saved in the same directory as the project.
(12) New File from file menu in order to start a new File.


Design Patterns
In our implementation, we used 6 design patterns:
1. Singleton Design Pattern
This pattern involves a single class which is responsible for creating an object while
making sure that only a single object gets created. This class provides a way to access
its only object which can be accessed directly without need to instantiate the object of
the class.
Implementation
Class Canvas.java is where all graphics actions are made, it acts like
our drawing board and is used using a single instance throughout the
whole program. Its constructor is private so that it only has access to
use it. It contains, along with the private constructor, a static public
method to get the new constructed shape. MainGui class will use the
Canvas class in order to get a Canvas object, created in the Canvas
class.
2. Factory Pattern
This pattern is the best creational pattern to create objects. In Factory pattern, we
create objects without exposing the creation logic to the client and refer to newly
created objects using a common interface.
Implementation
Shape is an abstract class that is inherited by all the separate shapes classes : Circle, Ellipse,
Rectangle, Square, Line and Triangle. In order to create a new shape, our Canvas class will
use the ShapeFactory abstract class to get an object Shape created. It will pass information of
the previous shapes to ShapeFactory to get the type of object needed.
This UML diagram shows exactly how it works:3. Prototype Pattern
Prototype pattern is a creational pattern that permits duplicating objects.
This pattern involves implementing a prototype interface which tells it to create a clone
of the current object. The prototype lets you clone objects without coupling the code to
their classes.
Implementation
Shape is an abstract class that implements Cloneable interface, it contains an abstract method
clone() with a shape return type. The Shape class also has a constructor within it with a
parameter passed of the same type Shape. Each shape gets to have the same constructor with
the parameter of its type, an overridden clone method and equals method.4. Iterator Pattern
This pattern is used to get a way to access the elements of a collection object in
sequential manner without any need to know its underlying representation. Iterator
pattern falls under the behavioral pattern category.
Implementation
We created an Iterator interface and a Container interface returning the Iterator. We also
create a Concrete class implementing the Container interface named Looper. And the Looper
class is responsible for implementing the Iterator interface and using it. This is because Looper
has an inner class ShapeIterator implementing the Iterator interface.
Canvas class uses Looper, to loop on an ArrayList shapes stored as a collection in Looper.
5. Template Pattern
In Template pattern, an abstract class exposes a defined way/ways to execute its
methods. Its subclasses can override the method implementation as per need but the
invocation is to be in the same way as defined by an abstract class. This pattern comes
under the behavior pattern category.
Implementation
Our implementation resides in the Shape abstract class, from which the Circle, Rectangle,
Line, Ellipse, Triangle, Square classes inherit. All these classes override the abstract
methods defined in the Shapes abstract class. A certain method created(); is defined as
abstract in the Shape class and overridden in each subclass, printing to the output stream the
specific shape created.The void template(); method in Shape is final so it can’t be overridden. In the template
method, the created method is called. And thus, every time a new shape is created, the
specific shape’s created method is called and the shape name is printed in the output stream.
6. Composite Pattern
Composite pattern is used where we need to treat a group of objects in a similar way
as a single object. This pattern creates a class that contains a group of its own objects.
This class provides ways to modify its group of the same objects.
Implementation
Our implementation resides in the Action class, which contains two static stacks of Action
accessed by both maingGui and Canvas class. Thus, we can modify the Action stacks by
methods in the same Action class, and this way, we have achieved a Composite pattern





Credits to Lara Abdelbaki and Nour Hesham

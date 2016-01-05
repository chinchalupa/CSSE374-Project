<h1>CSSE 374 Project</h1>
-Milestone 1-

<h2>General</h2>

To run our project, run DesignParser with no arguments. You will be prompted for some inputs. Once you have read and entered in the inputs, take the dot file and run it using dot.exe

Milestone 1 progress

Design
Our design for this was built by analyzing the structure of graph viz and developing a pattern based off of it. The core design revolves around building the nodes first and then building the edges afterwards.
Since graph viz appears to parse sequentially, this would be the best approach to ensure that you do not try to route to a class that has not yet been created.

DesignParser
The class to be ran.

iShape
Nearly everything that is written within the .dot file extends the iShape. This allows each class to be placed into the .dot file.

Node
A node is used to add more parameters onto an iShape where a name is needed.

DotClass
The dotClass class allows us to create a class with the necessary fields, methods, and superclasses that need to be created.

DotField
A class to be used by dotClass to hold all of the fields that are created.

DotMethod
A class to be used by dotClass to hold all the metohds that are created.

DotAbstractMethod
A class to be used by dotClass to hold abstract methods.

dotEdge
An abstract class used for edge-based classes.

dotImplements / dotExtends / dotAbstractClass
Concrete classes that are types of edge cases.

dotBlurb / dotAssignment
basic utility classes for formatting.

FirstASM / Creator
Barebones testing code. Mostly can be ignored.

UMLBoxNode
A node that is a UML Box for the .dot file.

plainText
Placeholder for later use.
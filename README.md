<h1>CSSE 374 Project</h1>
<strike>Milestone 1</strike>

<h2>General</h2>

To run our project, run DesignParser with no arguments. You will be prompted for some inputs. Once you have read and entered in the inputs, take the dot file and run it using dot.exe.


<h3>Milestone 1 Log</h3>

<strong>Design</strong>
<p>Our design for this was built by analyzing the structure of graph viz and developing a pattern based off of it. The core design revolves around building the nodes first and then building the edges afterwards.
Since graph viz appears to parse sequentially, this would be the best approach to ensure that you do not try to route to a class that has not yet been created.</p>

<strong>DesignParser</strong>
<p>The class to be ran.</p>

<strong>iShape</strong>
<p>Nearly everything that is written within the .dot file extends the iShape. This allows each class to be placed into the .dot file.</p>

<strong>Node</strong>
<p>A node is used to add more parameters onto an iShape where a name is needed.</p>

<strong>DotClass</strong>
<p>The dotClass class allows us to create a class with the necessary fields, methods, and superclasses that need to be created.</p>

<strong>DotField</strong>
<p>A class to be used by dotClass to hold all of the fields that are created.</p>

<strong>DotMethod</strong>
<p>A class to be used by dotClass to hold all the metohds that are created.</p>

<strong>DotAbstractMethod</strong>
<p>A class to be used by dotClass to hold abstract methods.</p>

<strong>dotEdge</strong>
<p>An abstract class used for edge-based classes.</p>

<strong>dotImplements / dotExtends / dotAbstractClass</strong>
<p>Concrete classes that are types of edge cases.</p>

<strong>dotBlurb / dotAssignment</strong>
<p>Basic utility classes for formatting.</p>

<strong>FirstASM / Creator</strong>
<p>Barebones testing code. Mostly can be ignored.</p>

<strong>UMLBoxNode</strong>
<p>A node that is a UML Box for the .dot file.</p>

<strong>plainText</strong>
<p>Placeholder for later use.</p>
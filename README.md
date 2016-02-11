<h1>CSSE 374 Project</h1>
<p><strike>Milestone 1</strike></p>
<p><strike>Milestone 2</strike></p>
<p><strike>Milestone 3</strike></p>
<p><strike>Milestone 4</strike></p>
<p><strike>Milestone 5</strike></p>
<p>Milestone 6</p>
<p>Milestone 7</p>

<h2>Our UML Design</h2>
![alt tag](https://raw.githubusercontent.com/chinchalupa/CSSE374-Project/master/UmlDesigns/UML%20Project.png)

<h2>General</h2>

To run our project, run DesignParser with no arguments. You will be prompted for some inputs, including the input file and the output file path. Once you have read and entered in the inputs, take the dot file and run it using dot.exe.


<h3>Milestone 1 Log</h3>

<strong>Design</strong>
<p>Our design for this was built by analyzing the structure of graph viz and developing a pattern based off of it. The core design revolves around building the nodes first and then building the edges afterwards.
Since graph viz appears to parse sequentially, this would be the best approach to ensure that you do not try to route to a class that has not yet been created.</p>

<strong>DesignParser</strong>
<p>The class to be ran.</p>

<strong>iShape</strong>
<p>Nearly everything that is written within the .dot file extends the iShape. This allows each class to be placed into the .dot file.</p>

<strong>node</strong>
<p>A node is used to add more parameters onto an iShape where a name is needed.</p>

<strong>dotClass</strong>
<p>The dotClass class allows us to create a class with the necessary fields, methods, and superclasses that need to be created.</p>

<strong>dotField</strong>
<p>A class to be used by dotClass to hold all of the fields that are created.</p>

<strong>dotMethod</strong>
<p>A class to be used by dotClass to hold all the metohds that are created.</p>

<strong>dotAbstractMethod</strong>
<p>A class to be used by dotClass to hold abstract methods.</p>

<strong>dotEdge</strong>
<p>An abstract class used for edge-based classes.</p>

<strong>dotImplements / dotExtends / dotAssociates / dotUses</strong>
<p>Concrete classes that are types of edge cases; <br> it should be noted that dotAssociates and dotImplements use static data from dotExtends, <br> and that dotUses uses static data from dotAssociates; <br> this is by design.</p>

<strong>dotBlurb / dotAssignment</strong>
<p>Basic utility classes for formatting.</p>

<strong>FirstASM / Creator</strong>
<p>Barebones testing code. Mostly can be ignored.</p>

<strong>UMLBoxNode</strong>
<p>A node that is a UML Box for the .dot file.</p>

<strong>plainText</strong>
<p>Placeholder for later use.</p>

<h3> Milestone 3 Log</h3>

<p>Very difficult milestone. Will be revisited at a later point to ensure code quality.</p>

<h3> Milestone 4 Log</h3>
<h5>Evolution of design</h5>
<ul>
<li>There was a need for a decorator pattern and a class more designed for UML Generation. Thus we made the UML Generator tab.</li>
<li>We needed to make a decorator so we made the UML Decorator.</li>
<li>The decorator has access to all the nodes and can manipulate them as necessary.</li>
<li>A decorator was made for each type of decoration needed on the UML Diagram.</li>
</ul>

<h3> Milestone 5 Log</h3>
<h5>Evolution of design</h5>
<ul>
<li>To meet requirements, one of the first things we did was allow our decorators to function as visitors.</li>
<li>More decorators were needed, so we added those decorators in.</li>
<li>We included a config file to make getting packages and classes out much easier.</li>
<li>Checking for packages that was once scatttered among multiple classes now localized in the Config as a static method.</li>
</ul>

<h3>Milestone 6 Log</h3>
<h5>Evolution of design</h5>
<ul>
<li>Need to differentiate between a decorator and a composite pattern.</li>
<li>A leaf node has every method that the component has and is used for implementation.</li>
<li>A composite node has every method that the component has and a list of components.</li>
<li>Modified field nodes to include whether it is a list, set, etc.</li>
<li>Added adapter minimum method detector.</li>
<li>Detects composites, components, and leaves.</li>
</ul>

<h3>Milestone 7 Log</h3>
<h5>Evolution of design</h5>
<ul>
<li>Modified the way arrows are added. The ItemHandler class now handles the addition of arrows and
the logic for how to handle duplicates. This localizes the logic of adding arrows and allows the user
to build his/her own class to determine how arrows are produced for different circumstances.</li>
<li>ItemHandler is now passed into all the visitors to simplify the design.</li>
</ul>

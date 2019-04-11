### Just A Rather Very Simple Graph Cloner

This is a small utility program to clone a sub graph of a directed graph 
where Entities are the vertices and the Links are edges.

An Entity is defined as a structure with the following three fields:
```
- ID
- Name
- Description (optional)
```

Related Entities are represented as links from one Entity to another. 
A link entry has the following fields:
```
- From Entity ID
- To Entity ID
```

### How to Run
To run this program you will need to install **Java Runtime Environment (version 8+)**
on your local machine. 

The program takes in as an input a JSON file representing the entities 
and the links in the graph and the id of the entity that needs to be cloned. 
These inputs should be taken in on the command line as follows:
```
<programName> <inputFilePath> <entityId>
```

### How to Build
To build this program you will need to install **Java SE Development Kit (version 8+)** 
and **Maven Build System (version 3+)** on your local machine.

To build, enter the project's root directory, and run the following command:
```
mvn clean install
```

After running this command **jarvs-graph-cloner.zip** archive will be created 
in the target directory. Copy it to the desktop and run it on the command line as follows:
```
java -jar jarvs-graph-cloner/app.jar /path/to/the/file.json entityId
```
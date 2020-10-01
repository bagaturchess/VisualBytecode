
# Overview

In the modern java applications, frameworks, libraries and tools there are a lot of archive files (*.jar, *.zip, etc.) with java classes.
So, it is difficult to:
  -  check if given class file is presented in the archives and where it is presented
  -  check if given class has given constructor, method or field
  -  check the metadata annotations assigned to a given class, constructor, field, method or method's argument
  -  search for metadata annotations to check which classes, class members or methods' arguments are annotated with given annotation

This project aims to resolve these difficulties by preventing people to search in archives and decompile java bytecode with java decompilers.
The project provides visual environment (GUI) where one can import files, directories and archives.
Than the program parses the bytecode of all classes and visualizes the metadata in a tree on the screen.

![GitHub Logo](/Logo.png)

Also, there is an advanced feature for exporting the classes' metadata as XML file and optionally to use XSL transformation to extract specific data.

The part of the project, which parses the java bytecode is still closed source and hence is used as an obfuscated library (bytecode-parser.jar).
The rest of the project is open source and you are welcome to contribute.

The very initial versions of this project were handed over as a diploma work in the university.
That is why I have decided to use MIT License for the project.

# Running the program
The project structure is an eclipse based project, which can be directly imported into the Eclipse IDE. The main class is com.fmi.bytecode.annotations.gui.graphical.mainframe.AnnotationToolGUI.

You can download also the standalone version from releases section, which needs only Java installed to run.

# Author

The author of the project is <a href="https://www.linkedin.com/in/topchiyski/">Krasimir Topchiyski</a>.



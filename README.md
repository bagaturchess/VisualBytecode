
# Overview

In the modern java applications, frameworks, libraries and tools there are a lot of archive files (*.jar, *.zip, *.war, etc.) with java classes.
So, it is difficult to:
  -  check if given class file is presented in the archives and where it is presented
  -  check if given class has given constructor, method or field
  -  check the metadata annotations assigned to a given class, constructor, field, method or method's argument
  -  search for metadata annotations to check which classes, class members or methods' arguments are annotated with given annotation

This project aims to resolve these difficulties by preventing people to search in archives and decompile java bytecode with java decompilers.
The project provides visual environment (GUI) where one can import files, directories and archives.
Than the program parses the bytecode of all classes and visualizes the metadata in a tree on the screen.

Also, there is an advanced feature for exporting the classes' metadata as XML file and optinally to use XSL transformation to extract specific data.

The part of the project, which parses the java bytecode is still closed source and hence is used as an obfuscated library (bytecode-parser.jar).
The rest of the project is open source and you are welcome to contribute.

The very initial versions of this project were handed over as a diploma work in the university.
That is why I have decided to use MIT License for the project.

# Author

The author of the project is <a href="https://www.linkedin.com/in/topchiyski/">Krasimir Topchiyski</a>.



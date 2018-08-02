Simple search engine
=============================

[![Java 8+](https://img.shields.io/badge/java-8%2B-blue.svg)](http://java.oracle.com)

Search engine service works with small documents where each document contains a series of tokens (words) separated by spaces. 
To keep things simple document is represented as String.

The usage model of service:
1. Put documents into the search engine by key
2. Get document by key
3. Search on a string of tokens (words) to return keys of all documents that contain all tokens in the set

For index persistence service stores documents in server's memory.
To keep things simple service allow no overwrites of a key with a new document.

INSTALLATION
------------
You shall see the following files and directories:

      src/                  project sources
      pom.xml/              maven build file
      README.md             this file
      
REQUIREMENTS
------------
The requirements to build project:

      JDK8+
      Maven 3.5.3+
      
BUILD INSTRUCTIONS
-----------
On command line, type in the following commands:

        $ cd http-server
        $ mvn clean package
        
RUN INSTRUCTIONS
-----------
After previous step, type following commands:

        $ cd target
        $ java -jar test-http-server-1.0-SNAPSHOT.jar

By default server will listen to 8080 port. You could override listening port specifying vm arg:

        $ java -jar test-http-server-1.0-SNAPSHOT.jar -Dserver.port=8090
        
WHAT'S NEXT
-----------
Please visit the project http-client
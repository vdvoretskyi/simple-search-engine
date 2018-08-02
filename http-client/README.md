Simple search engine client
=============================

[![Java 8+](https://img.shields.io/badge/java-8%2B-blue.svg)](http://java.oracle.com)

Search engine service works with small documents where each document contains a series of tokens (words) separated by spaces. 
To keep things simple document is represented as String.

The usage model of service client:
1. Put documents into the search engine by key
2. Get document by key
3. Search on a string of tokens (words) to return keys of all documents that contain all tokens in the set

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
      
The requirements to run project:

      Java8 and higher
      
BUILD INSTRUCTIONS
-----------
On command line, type in the following commands:

        $ cd http-client
        $ mvn clean package
        
RUN INSTRUCTIONS
-----------
After previous step, type following commands:

        $ cd target
        $ java -jar test-http-client-1.0-SNAPSHOT.jar
        
USAGE
-----------
After running client application you can use shell and type commands:

        $ shell:>

There are three commands:

* upload

        $ shell:>shell:>upload --key 1 --document "word1 word2"
        or
        $ shell:>shell:>upload  1 "word1 word2"
        Success. true
        
Put document (string of words separated by whitespaces) into the search engine by key

* get

        $ shell:>get --key 1
        or
        $ shell:>get 1
        Success. word1 word2
        
Get document by key

* search

        $ shell:>search --tokens "word1 word2"
        or
        $ shell:>search "word1 word2"
        Success. 1
        
Search on a string of tokens (words) to return keys of all documents that contain all tokens in the set

Help is available:

        $ shell:>help

Help is available for each shell command:

        $ shell:>help search

SETTINGS
-----------
By default client application will be sending requests to _http://localhost:8080_.
It's still possible to specify required host by using --url or -U for each of the three shell commands described above:

        $ shell:>upload 1 "word1 word2" http://localhost:8081

WHAT'S NEXT
-----------
Please visit the project http-server
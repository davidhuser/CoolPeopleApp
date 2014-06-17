CoolPeopleApp
=============

> This application generates a Gephi network graph file of persons which are found in pre-specified english input texts.
> Two persons are in the same network if they are being found in the same document.
> It reads resources as text files, reads them in to a String, extracts names with [Named Entity Extraction](http://opennlp.sourceforge.net/models-1.5/) and a large list of prenames
> and generates based on these found names a [GEXF file (Graph Exchange XML Format)](http://gexf.net/format/).

##configuration
Edit Config.java:

* input text files - texts should be in english and plain text  
* export path of graph file – keep .gexf extension in file path

##testing
`mvn test`

##building
assemble .jar with dependencies with `mvn clean compile assembly:single`

##extending
Since it's based reading a String, more input data types can be added later on (web-fetching, databases, ...)

##Java Archive

### exporting JAR for Java 6 and with Command Line Arguments
switch branch, assemble .jar with dependencies with `mvn clean compile assembly:single`

### command line arguments for JAR

Example start:

`java -jar CoolPeopleApp-0.1-jar-with-dependencies.jar /Users/david/Desktop/ larryellison.txt billgates.txt`

* First argument is the output folder of the output gexf file
* All other arguments are text files

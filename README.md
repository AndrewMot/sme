Technical Test
========

Process a list of websites finding matches with different patterns. Elaborated by Andrés Felipe Motavita Medellín


Run
---------------

In order to run the project use:
  
		mvn install -DskipTests
		mvn test -DinputFile = <inputFile>
  
This will execute the program showing Unit test set progress and log.
DinputFile is the absolute path for input file location. The file must be a text file (.txt)


If input File is not specified, use:

		mvn install -DskipTests
		mvn test
		
Input file must be located in <root project>/src/test/resources for this case. A sample input file was located in the previous folder for test purposes.


Add a Pattern
--------------

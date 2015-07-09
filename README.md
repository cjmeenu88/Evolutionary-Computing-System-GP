# Evolutionary-Computing-System-GP


###Project is mavenized and builds a uber jar using maven shade plugin.


Go to Project folder and execute
mvn clean install

This should compile and build jar and also run all Unit test cases.

mvn sonar:sonar will run sonar report for this project 

####Below is command to run the program from command prompt:

java -cp target\Evolutionary-Computing-System-GP-1.0-SNAPSHOT-shaded.jar GeneticProgrammingMain

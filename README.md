## Simple license detector
This console application detects which licenses are used in a software project and outputs them.

### Requirements
- JDK 8

### How to build
In order to build the project, run the command: ```gradlew shadowJar```.

As a result of the command, the folder ```/build/libs``` will contain a jar file ```Simple-license-detector-1.0-all.jar```.

### How to run
In order to run the application, you need to run the command: ```java -jar Simple-license-detector-1.0-all.jar```.

After starting, you must specify the full path to the project folder that you want to scan.

After that, the scanning process begins, upon its completion, all found licenses of the specified project will be displayed.
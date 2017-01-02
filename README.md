# java-4d

A 3D model editor for Codename Panzers game. 
This is my first "complex" Java project, it dates back to 2009. At that time, writing this helped me a lot to learn Java. The reverse engineering of 4D file structure was a funny and exciting experience (I started from scratch, by changing some bytes around in the files and checking in-game effects), you can find some txt files with my notes (in Italian).

## Build & run

I used Netbeans to build this project, but everything supports maven should work (of course, command line too). With a `mvn clean install` you should be able to build a jar in the target directory. It contains all dependencies so you can run it with `java -jar java-4d-editor-<<version here>>.jar`

## Status & License

This project is not maintained anymore, if you want to expand it feel free to fork the repo.

The code is released under MIT license.

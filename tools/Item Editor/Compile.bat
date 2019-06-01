@echo off
title Compiler
echo starting...
"C:/Program Files/Java/jdk1.8.0_111/bin/javac.exe" -d bin -cp lib/*; -sourcepath src src/Kjs/*.java                                                                                                                                         
pause
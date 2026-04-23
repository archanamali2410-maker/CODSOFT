#!/bin/bash
echo "Compiling StudentGradeCalculator..."
cd src
javac StudentGradeCalculator.java
if [ $? -eq 0 ]; then
    echo "Compilation successful! Running..."
    echo ""
    java StudentGradeCalculator
else
    echo "Compilation failed. Please check your Java installation."
fi

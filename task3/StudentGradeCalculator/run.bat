@echo off
echo Compiling StudentGradeCalculator...
cd src
javac StudentGradeCalculator.java
if %errorlevel% == 0 (
    echo Compilation successful! Running...
    echo.
    java StudentGradeCalculator
) else (
    echo Compilation failed. Please check your Java installation.
)
pause

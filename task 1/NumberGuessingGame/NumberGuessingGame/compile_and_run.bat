@echo off
REM ──────────────────────────────────────────
REM  compile_and_run.bat
REM  Compiles and runs the Number Guessing Game
REM  (Windows version)
REM ──────────────────────────────────────────

echo Compiling NumberGuessingGame.java...
if not exist out mkdir out
javac src\NumberGuessingGame.java -d out

if %errorlevel% == 0 (
    echo Compilation successful! Starting game...
    echo ------------------------------------------
    java -cp out NumberGuessingGame
) else (
    echo Compilation failed. Please check your Java installation.
)
pause

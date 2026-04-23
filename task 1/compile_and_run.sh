#!/bin/bash
# ──────────────────────────────────────────
#  compile_and_run.sh
#  Compiles and runs the Number Guessing Game
# ──────────────────────────────────────────

echo "Compiling NumberGuessingGame.java..."
javac src/NumberGuessingGame.java -d out/

if [ $? -eq 0 ]; then
    echo "Compilation successful! Starting game..."
    echo "──────────────────────────────────────────"
    java -cp out NumberGuessingGame
else
    echo "Compilation failed. Please check your Java installation."
fi

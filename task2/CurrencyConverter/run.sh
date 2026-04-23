#!/bin/bash
echo "========================================"
echo "  Currency Converter - Java Swing App"
echo "========================================"
echo ""

# Check if compiled
if [ ! -f "out/currencyconverter/CurrencyConverter.class" ]; then
    echo "[Step 1/2] Compiling source code..."
    mkdir -p out
    javac -cp "lib/json-20231013.jar" -d out src/currencyconverter/CurrencyConverter.java
    if [ $? -ne 0 ]; then
        echo ""
        echo "ERROR: Compilation failed!"
        echo "Make sure Java JDK is installed and lib/json-20231013.jar exists."
        echo "See README.md for setup instructions."
        exit 1
    fi
    echo "Compilation successful!"
    echo ""
fi

echo "[Step 2/2] Launching Currency Converter..."
java -cp "out:lib/json-20231013.jar" currencyconverter.CurrencyConverter

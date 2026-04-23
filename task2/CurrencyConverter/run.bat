@echo off
echo ========================================
echo   Currency Converter - Java Swing App
echo ========================================
echo.

REM Check if out folder exists (compiled)
IF NOT EXIST "out\currencyconverter\CurrencyConverter.class" (
    echo [Step 1/2] Compiling source code...
    if not exist out mkdir out
    javac -cp "lib\json-20231013.jar" -d out src\currencyconverter\CurrencyConverter.java
    IF %ERRORLEVEL% NEQ 0 (
        echo.
        echo ERROR: Compilation failed!
        echo Make sure Java JDK is installed and lib\json-20231013.jar exists.
        echo See README.md for setup instructions.
        pause
        exit /b 1
    )
    echo Compilation successful!
    echo.
)

echo [Step 2/2] Launching Currency Converter...
java -cp "out;lib\json-20231013.jar" currencyconverter.CurrencyConverter

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Failed to run. Check README.md for help.
    pause
)

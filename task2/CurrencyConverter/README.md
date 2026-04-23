# 💱 Currency Converter — Java Swing App

A modern, real-time currency converter built with Java Swing.
Supports 30 currencies, live rates via ExchangeRate-API, and offline fallback rates.

---

## 📁 Project Structure

```
CurrencyConverter/
├── src/
│   └── currencyconverter/
│       └── CurrencyConverter.java   ← Main application source
├── lib/
│   └── json-20231013.jar            ← JSON parsing library (download below)
├── out/                             ← Compiled .class files go here
├── run.bat                          ← Windows one-click launcher
├── run.sh                           ← Mac/Linux one-click launcher
└── README.md                        ← This file
```

---

## ⚙️ Requirements

| Tool         | Version  | Download                          |
|--------------|----------|-----------------------------------|
| Java JDK     | 11+      | https://adoptium.net              |
| JSON Library | 20231013 | See Step 2 below                  |

---

## 🚀 Setup & Run (Step by Step)

### Step 1 — Install Java JDK 11+
Download from: https://adoptium.net  
Verify install: open terminal/cmd and run:
```
java -version
javac -version
```

---

### Step 2 — Download the JSON Library
Download **json-20231013.jar** from:
👉 https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar

Place the downloaded file in the `lib/` folder inside this project.

---

### Step 3 — Get a FREE API Key (Optional but recommended)
1. Go to: https://app.exchangerate-api.com/sign-up
2. Sign up with your email (free, no credit card)
3. Copy your API key from the dashboard
4. Open `src/currencyconverter/CurrencyConverter.java`
5. Find line: `private static final String API_KEY = "YOUR_API_KEY_HERE";`
6. Replace `YOUR_API_KEY_HERE` with your key

> **Without an API key**, the app still works using built-in offline rates!
> The status bar will show "● Offline Rate" instead of "● Live Rate".

---

### Step 4 — Compile

**Windows:**
```cmd
javac -cp "lib\json-20231013.jar" -d out src\currencyconverter\CurrencyConverter.java
```

**Mac / Linux:**
```bash
javac -cp "lib/json-20231013.jar" -d out src/currencyconverter/CurrencyConverter.java
```

---

### Step 5 — Run

**Windows:**
```cmd
java -cp "out;lib\json-20231013.jar" currencyconverter.CurrencyConverter
```

**Mac / Linux:**
```bash
java -cp "out:lib/json-20231013.jar" currencyconverter.CurrencyConverter
```

---

## 🖱️ One-Click Launch

After compiling, just double-click:
- **Windows:** `run.bat`
- **Mac/Linux:** `run.sh` (first run: `chmod +x run.sh`)

---

## 🌟 Features

| Feature               | Details                                      |
|-----------------------|----------------------------------------------|
| 💱 30 Currencies       | USD, EUR, GBP, INR, JPY, AUD, CAD, AED + more|
| 🌐 Real-Time Rates     | Fetches live data via ExchangeRate-API        |
| 📡 Offline Fallback    | Works without internet using built-in rates  |
| 🔄 Swap Button         | Instantly swap From/To currencies            |
| 🎨 Dark UI             | Modern dark-themed Swing interface           |
| ⚡ Async Fetching      | Non-blocking API calls (UI stays responsive) |
| 🔍 Dual Combo Boxes    | Select by currency code OR full name         |

---

## 📋 Supported Currencies

USD, EUR, GBP, INR, JPY, AUD, CAD, CHF, CNY, AED,
SGD, HKD, SEK, NOK, DKK, NZD, ZAR, BRL, MXN, KRW,
TRY, SAR, THB, IDR, MYR, PHP, PKR, EGP, NGN, BDT

---

## 🔧 Troubleshooting

| Problem                        | Solution                                      |
|--------------------------------|-----------------------------------------------|
| `javac not found`              | Install JDK and add to PATH                   |
| `ClassNotFoundException: JSONObject` | Make sure `lib/json-20231013.jar` exists |
| `API error` in status bar      | Check API key or internet connection          |
| App shows "Offline Rate"       | Normal! Add API key for live rates            |

---

## 📄 License
Free to use and modify for personal/educational use.

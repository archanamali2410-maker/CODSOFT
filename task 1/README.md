# Number Guessing Game — Java

A fully featured console-based number guessing game written in Java.

---

## Folder Structure

```
NumberGuessingGame/
├── src/
│   └── NumberGuessingGame.java   ← Full game source code
├── out/                          ← Compiled .class files go here (auto-created)
├── compile_and_run.sh            ← Run on Mac / Linux
├── compile_and_run.bat           ← Run on Windows
└── README.md                     ← This file
```

---

## Requirements

- Java JDK 8 or higher installed
- Check with: `java -version`
- Download from: https://www.oracle.com/java/technologies/downloads/

---

## How to Run

### Option 1 — Script (easiest)

**Mac / Linux:**
```bash
chmod +x compile_and_run.sh
./compile_and_run.sh
```

**Windows:**
```
Double-click compile_and_run.bat
```

### Option 2 — Manual (terminal)

```bash
# 1. Create output folder
mkdir out

# 2. Compile
javac src/NumberGuessingGame.java -d out

# 3. Run
java -cp out NumberGuessingGame
```

---

## How to Play

```
╔══════════════════════════════════════╗
║       NUMBER  GUESSING  GAME         ║
║   Guess the secret number to win!    ║
╚══════════════════════════════════════╝

Enter range minimum   (default 1):
Enter range maximum   (default 100):
Enter max attempts    (default 7):
```

1. Set your range and max attempts (or press Enter for defaults).
2. Type a number and press Enter to guess.
3. Follow the hints:
   - `↑  Too low`  — guess higher
   - `↓  Too high` — guess lower
   - `✔  Correct!` — you win the round
4. If attempts run out, the answer is revealed.
5. After each round, choose to play again or quit.

---

## Features

| Feature | Detail |
|---|---|
| Random number | Fresh secret each round |
| Custom range | Set any min–max |
| Attempt limit | 1–20 attempts |
| Visual dot bar | `● ● ● ○ ○` tracks remaining attempts |
| Guess history | Logged with ↑ ↓ ✓ ✗ symbols |
| Feedback | Too high / Too low / Correct / Out of attempts |
| Multi-round | Play as many rounds as you want |
| Score tracking | Rounds won + best guess count |

---

## Game Logic Overview

| Method | Purpose |
|---|---|
| `main()` | Entry point — reads settings, loops rounds |
| `playRound()` | Runs one full round |
| `dotsBar()` | Renders the attempt progress dots |
| `printHistory()` | Shows all guesses made this round |
| `printStats()` | Shows running score after each round |
| `readInt()` | Safe integer input with default fallback |
| `askYesNo()` | Handles play-again prompt |
| `printBanner()` | Prints the game title banner |

---

## Customization

- Change defaults → edit the constants at the top of `NumberGuessingGame.java`:
  ```java
  static final int DEFAULT_MIN     = 1;
  static final int DEFAULT_MAX     = 100;
  static final int DEFAULT_MAX_ATT = 7;
  ```

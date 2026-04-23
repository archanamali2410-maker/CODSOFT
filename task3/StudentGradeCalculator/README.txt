╔══════════════════════════════════════════════════════════════╗
║          STUDENT GRADE CALCULATOR — Java Project             ║
╚══════════════════════════════════════════════════════════════╝

📁 Project Structure
─────────────────────────────────────────────
StudentGradeCalculator/
│
├── src/
│     └── StudentGradeCalculator.java   ← Main source file
│
├── README.txt                          ← This file
└── run.bat / run.sh                    ← Quick run scripts


📌 Features
─────────────────────────────────────────────
  ✔ Enter student name & roll number
  ✔ Enter any number of subjects
  ✔ Input marks (0–100) with validation
  ✔ Calculates total marks & average percentage
  ✔ Auto assigns Letter Grade (A+, A, B+, B, C+, C, F)
  ✔ Displays Remark (Outstanding, Excellent, etc.)
  ✔ Shows PASS / FAIL result
  ✔ Clean formatted Result Card on console


📊 Grade Chart
─────────────────────────────────────────────
  Average %       │  Grade  │  Remark
  ────────────────┼─────────┼──────────────────
  90% – 100%      │  A+     │  Outstanding
  80% – 89%       │  A      │  Excellent
  70% – 79%       │  B+     │  Very Good
  60% – 69%       │  B      │  Good
  50% – 59%       │  C+     │  Average
  40% – 49%       │  C      │  Pass
  Below 40%       │  F      │  Fail


▶ How to Compile & Run
─────────────────────────────────────────────

  Step 1 — Open Terminal / Command Prompt

  Step 2 — Navigate to the src folder:
    cd StudentGradeCalculator/src

  Step 3 — Compile:
    javac StudentGradeCalculator.java

  Step 4 — Run:
    java StudentGradeCalculator

  ─── OR use the scripts ───

  Windows  →  Double-click  run.bat
  Linux/Mac →  ./run.sh


⚙ Requirements
─────────────────────────────────────────────
  • Java JDK 8 or above
  • Command Prompt / Terminal


📝 Sample Output
─────────────────────────────────────────────

  ╔═══════════════════════════════════════════════╗
  ║       STUDENT GRADE CALCULATOR  v1.0          ║
  ╚═══════════════════════════════════════════════╝

  Enter Student Name  : Rahul Sharma
  Enter Roll Number   : 101
  How many subjects?  : 3

  Subject 1 Name  : Maths
  Marks Obtained  : 88

  Subject 2 Name  : Science
  Marks Obtained  : 76

  Subject 3 Name  : English
  Marks Obtained  : 92

  ┌──────────────────────────────────────────────┐
  │            ★  RESULT CARD  ★                 │
  ├──────────────────────────────────────────────┤
  │  Student Name  : Rahul Sharma                │
  │  Roll Number   : 101                         │
  ├──────────────────────────────────────────────┤
  │  Subject             Max Marks   Obtained    │
  │  ─────────────────────────────────────────── │
  │  Maths               100         88.0        │
  │  Science             100         76.0        │
  │  English             100         92.0        │
  ├──────────────────────────────────────────────┤
  │  Total Marks      : 256.0 / 300              │
  │  Average %        :  85.3%                   │
  │  Grade            : A                        │
  │  Remark           : Excellent    ⭐           │
  │  Result           : PASS ✅                  │
  └──────────────────────────────────────────────┘

─────────────────────────────────────────────

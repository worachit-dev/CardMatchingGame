data/
└── FileManager.java

data = File I/O
อันนี้คือ ส่วนของปลื้มกำลังจะทำ

Game
 ↓
FileManager
 ↓
data/highscore.txt

ดังนั้นโครงสร้างของคุณตอนนี้ผมแนะนำเป็น
CardMatchingGame/
│
├── data/
│   └── highscore.txt
│
├── resources/
│   ├── cards/
│   ├── card_back.png
│   └── heart.png
│
├── src/
│   ├── data/
│   │   └── FileManager.java
│   │
│   ├── game/
│   │   ├── Game.java
│   │   ├── GameBoard.java
│   │   ├── ScoreManager.java
│   │   └── GameState.java
│   │
│   ├── gui/
│   │   ├── MainFrame.java
│   │   ├── MainMenuPanel.java
│   │   ├── GamePanel.java
│   │   └── ResultPanel.java
│   │
│   └── model/
│       ├── Card.java
│       └── GameResult.java
│
└── README.md

⚠️ แต่มีข้อสังเกตนิดหนึ่ง

ก่อนหน้านี้ผมวาง Card.java ไว้ใน game/ แต่ถ้าพวกคุณ มี model/ อยู่แล้ว ผมเปลี่ยนให้ Card อยู่ model/ ดีกว่า เพราะมันเป็นข้อมูลของการ์ด ไม่ใช่ตัวควบคุมเกม

ดังนั้นตอนนี้ อย่าเพิ่งลบหรือย้ายอะไร ครับ

ถ้า src มี data, game, gui, model อยู่แล้ว → เก็บทั้ง 4 ไว้ก่อน แล้วเราค่อยตรวจว่าเพื่อนสร้าง Class อะไรไว้ในแต่ละอันแล้วบ้าง ก่อนที่คุณจะสร้าง FileManager.java เพื่อไม่ให้ชนกับงานเพื่อนครับ
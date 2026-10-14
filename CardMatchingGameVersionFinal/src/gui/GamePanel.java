package gui; 

import game.GameManager; 
import game.GameManager.ClickResult; 
import model.CardSetType; 

import javax.swing.*;
import java.awt.*; 
public class GamePanel extends JPanel { 

    private final MainFrame mainFrame; // ตัวแปรเก็บออบเจ็กต์หน้าต่างหลักเพื่อใช้สั่งเปลี่ยนหน้าจอ
    private final GameManager gameManager; // ตัวแปรจัดการตรรกะและกฎของเกม

    private final JLabel timeLabel;
    private final JLabel scoreLabel;
    private final JLabel comboLabel;

    private final JPanel boardPanel;      // จัดวางปุ่มการ์ดบนกระดาน
    private CardButton[] cardButtons;     // เก็บปุ่มการ์ดทั้งหมดในเกม

    private Timer gameTimer = null; // ตัวจับเวลาของหน้าจอเพื่ออัปเดตเวลาบน UI ทุก ๆ ระยะเวลาที่กำหนด

    // ตัวแปรเก็บขนาดบอร์ดปัจจุบัน
    private int currentRow = 4;
    private int currentCol = 4;

    private static final String CARD_IMAGE_FOLDER = "resources/picture/"; 
    private static final int MISMATCH_DELAY_MS = 900; // ค่าคงที่กำหนดเวลาหน่วง (900 มิลลิวินาที) ตอนเปิดการ์ดผิดก่อนจะคว่ำกลับ
    
    // ------ Constructer ------
    // สร้างหน้าจอเกมโดยรับ MainFrame เข้ามา
    public GamePanel(MainFrame mainFrame) {  

        this.mainFrame = mainFrame; 
        this.gameManager = new GameManager(); 
        
        // สร้าง Timer ให้ทำงานทุก ๆ 200 มิลลิวินาที เพื่อเรียกฟังก์ชันอัปเดตเวลาบนหน้าจอ
        gameTimer = new Timer(200, e -> updateTimeLabel());

        setLayout(new BorderLayout()); 
        setBackground(new Color(245, 235, 215)); 

        // =========================
        // TOP BAR (แถบด้านบนสุด)
        // =========================

        JPanel topPanel = new JPanel(new BorderLayout()); 
        topPanel.setOpaque(false); // ตั้งค่าให้โปร่งใส
        topPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 10, 30) // กำหนดระยะห่างขอบรอบด้าน
        );

        JLabel titleLabel = new JLabel("CARD MATCHING");
        titleLabel.setFont(
                FontManager.getFont(30) 
        );

        timeLabel = new JLabel("TIME: 00:00"); 
        timeLabel.setFont(
                FontManager.getFont(24) 
        );
        timeLabel.setForeground(new Color(180, 50, 50)); // กำหนดสีตัวอักษรเป็นสีแดงเข้ม

        topPanel.add(titleLabel, BorderLayout.WEST); 
        topPanel.add(timeLabel, BorderLayout.EAST); 

        // =========================
        // SCORE BAR (แถบแสดงคะแนนและคอมโบ)
        // =========================

        JPanel scorePanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 50, 5) // จัดเรียงกึ่งกลางเว้นระยะห่าง
        );

        scorePanel.setOpaque(false);

        scoreLabel = new JLabel();
        comboLabel = new JLabel();

        scoreLabel.setFont(
                FontManager.getFont(18) 
        );

        comboLabel.setFont(
                FontManager.getFont(18) 
        );

        scorePanel.add(scoreLabel); 
        scorePanel.add(comboLabel); 

        // =========================
        // CARD BOARD (กระดานการ์ด)
        // =========================

        boardPanel = new JPanel(); 

        boardPanel.setOpaque(false); 

        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        50,
                        15,
                        50
                ) // กำหนดขอบรอบกระดานการ์ด
        );

        cardButtons = new CardButton[16]; // กำหนดขนาดเริ่มต้น(โหมด 4x4)

        for (int i = 0; i < 16; i++) { // สร้างปุ่มการ์ด

            final int index = i;

            CardButton cardButton = new CardButton(index); // สร้างปุ่มการ์ดตามอินเด็กซ์

            cardButton.addActionListener(e -> handleCardClick(index)); // เชื่อมอีเวนต์คลิกปุ่มเพื่อเรียกฟังก์ชันจัดการการคลิก

            cardButtons[index] = cardButton; // เก็บปุ่มลงในอาร์เรย์

            boardPanel.add(cardButton); // เพิ่มปุ่มลงในกระดาน
        }

        // =========================
        // BOTTOM BAR (แถบด้านล่าง)
        // =========================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER) 
        );

        bottomPanel.setOpaque(false); 

        JButton backButton =
        new JButton("BACK TO MENU"); 

        backButton.setFont(
            FontManager.getFont(18) 
        );

        backButton.setForeground(Color.WHITE); // กำหนดสีตัวอักษรเป็นสีขาว

        backButton.setBackground(
                new Color(80, 55, 40) 
        );

        backButton.setFocusPainted(false); 

        backButton.setPreferredSize(
                new Dimension(260, 55)
        );

        backButton.addActionListener(e -> {
            gameTimer.stop();           // หากกดปุ่ม ให้หยุดเวลาของหน้าจอ
            gameManager.stopTimer();    // หยุดตัวจับเวลาในระบบเกม
            mainFrame.showMenu();       // สลับกลับไปหน้าเมนูหลัก
        });

        bottomPanel.add(backButton); // เพิ่มปุ่มลงในแถบด้านล่าง

        // =========================
        // ADD COMPONENTS (ประกอบร่างคอมโพเนนต์เข้าด้วยกัน)
        // =========================

        JPanel centerPanel =
            new JPanel(new BorderLayout()); // สร้างพาเนลตรงกลางสำหรับเก็บคะแนนและกระดานการ์ด

        centerPanel.setOpaque(false); 

        centerPanel.add(scorePanel, BorderLayout.NORTH); 
        centerPanel.add(boardPanel, BorderLayout.CENTER); 

        add(topPanel, BorderLayout.NORTH); 
        add(centerPanel, BorderLayout.CENTER); 
        add(bottomPanel, BorderLayout.SOUTH); 

        updateLabels(); // อัปเดตข้อความป้ายคะแนนและคอมโบเริ่มต้น
    }
    // ------  Medthod ------
    // เริ่มต้นเกมผ่าน GameManager + เริ่มจับเวลา 
    public boolean startNewGame(CardSetType type, int rows, int cols) {
        this.currentRow = rows;
        this.currentCol = cols; 
 
        boolean ok = gameManager.startNewGame(type, rows, cols); // สั่งเริ่มเกมใหม่ในระบบจัดการเกม
 
        if (!ok) {
            return false;
        }
 
        buildBoard(rows, cols);
        updateLabels(); 
        gameTimer.start(); // เริ่มต้นตัวจับเวลาหน้าจอ
 
        return true;
    }

    // สร้างและจัดวางปุ่มการ์ดบนกระดานใหม่
    private void buildBoard(int rows, int cols) {
 
        boardPanel.removeAll(); 
        boardPanel.setLayout(new GridLayout(rows, cols, 12, 12)); // จัดเรียงแบบตารางกริดเว้นระยะห่าง 12 พิกเซล
 
        cardButtons = new CardButton[rows * cols]; // สร้างปุ่มการ์ดขนาดใหม่
 
        for (int i = 0; i < cardButtons.length; i++) { // วนลูปสร้างปุ่มการ์ดตามจำนวนช่องทั้งหมด
 
            final int index = i;
 
            CardButton cardButton = new CardButton(index); 
 
            cardButton.addActionListener(e -> handleCardClick(index)); 
 
            cardButtons[index] = cardButton; 
 
            boardPanel.add(cardButton); 
        }
 
        boardPanel.revalidate(); // สั่งรีเฟรชโครงสร้าง
        boardPanel.repaint(); // สั่งวาดหน้าจอใหม่ให้แสดงผลถูกต้อง
    }
    // จัดการผู้เล่นที่คลิกที่ปุ่มการ์ดตำแหน่ง index
    private void handleCardClick(int index) {

        ClickResult result = gameManager.selectCard(index); // ส่งตำแหน่งที่คลิกให้ GameManager ตรวจสอบ

        switch (result) {

            case IGNORED:
                break; // คลิกการ์ดที่เปิดอยู่แล้วหรือจับคู่ไปแล้วให้ไม่ทำอะไร

            case FIRST_SELECTED:
                revealCard(index); // ถ้าเป็นการเลือกการ์ดใบแรก ให้เปิดภาพการ์ดใบนั้นขึ้นมา
                break;

            case MATCH: { // กรณีจับคู่สำเร็จ
                int firstIndex = gameManager.getFirstIndex(); // ดึงอินเด็กซ์ของการ์ดใบแรกที่เปิดไว้

                revealCard(index); // เปิดภาพการ์ดใบที่สอง
                revealCard(firstIndex); // เปิดภาพการ์ดใบแรกย้ำความถูกต้อง

                cardButtons[index].setEnabled(false); // ปิดการใช้งานปุ่มการ์ดใบที่สอง (กดซ้ำไม่ได้)
                cardButtons[firstIndex].setEnabled(false); // ปิดการใช้งานปุ่มการ์ดใบแรก

                gameManager.clearSelection(); // ล้างค่าสถานะการเลือกการ์ดในระบบเกม
                updateLabels(); // อัปเดตคะแนนและคอมโบใหม่
                checkGameEnd(); // ตรวจสอบว่าเกมจบหรือยัง(เปิดครบทุกคู่ยัง)
                break;
            }

            case MISMATCH: {
                final int firstIndex = gameManager.getFirstIndex(); // ดึงอินเด็กซ์การ์ดใบแรก

                revealCard(index); // เปิดภาพการ์ดใบที่สองให้ผู้เล่นเห็นชั่วครู่
                updateLabels(); // บวกเวลาเพิ่ม 10 วินาทีกรณีจับคู่ผิด

                // สร้าง Timer หน่วงเวลา เพื่อแสดงการ์ดค้างไว้ก่อนจะคว่ำกลับลงไป
                Timer timer = new Timer(MISMATCH_DELAY_MS, e -> {
                    gameManager.resolveMismatch(index); // สั่งระบบเคลียร์สถานะการจับคู่ผิด
                    cardButtons[firstIndex].hideCard(); // คว่ำการ์ดใบแรกกลับด้านหลัง
                    cardButtons[index].hideCard(); // คว่ำการ์ดใบที่สองกลับด้านหลัง
                });
                timer.setRepeats(false); // ตั้งให้ทำงานครั้งเดียวแล้วหยุด
                timer.start(); // เริ่มต้นตัวหน่วงเวลา
                break;
            }
        }
    }

    // สั่งเปิดหน้าการ์ดโดยดึงชื่อรูปภาพจาก GameBoard มาแสดงบนปุ่ม
    private void revealCard(int index) {
        String imageId = gameManager.getGameBoard().getCard(index).getImageId(); // ดึงImageIdของการ์ดที่ตำแหน่งนั้น
        cardButtons[index].showCard(CARD_IMAGE_FOLDER + imageId + ".png"); // สั่งปุ่มแสดงรูปภาพตามพาธไฟล์
    }

    // แปลงวินาทีเป็นข้อความรูปแบบ mm:ss
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60; // คำนวณนาที
        int seconds = totalSeconds % 60; // คำนวณวินาทีส่วนที่เหลือ
        return String.format("%02d:%02d", minutes, seconds); // จัดรูปแบบให้แสดงเลข 2 หลักเสมอ
    }

    // อัปเดตข้อความแสดงเวลาบน timeLabel
    private void updateTimeLabel() {
        timeLabel.setText("TIME: " + formatTime(gameManager.getElapsedSeconds())); // ดึงเวลาที่ผ่านไปจาก GameManager มาแปลงแล้วแสดงผล
    }

    //อัปเดตข้อความคะแนน คอมโบ และเวลาพร้อมกัน
    private void updateLabels() {
        scoreLabel.setText("SCORE: " + gameManager.getScoreManager().getScore()); // ดึงคะแนนปัจจุบันมาแสดง
        comboLabel.setText("COMBO: " + gameManager.getCombo()); // ดึงคอมโบปัจจุบันมาแสดง
        updateTimeLabel(); // อัปเดตเวลา
    }

    // ตรวจสอบว่าเกมจบหรือยัง
    private void checkGameEnd() {
        if (gameManager.isGameWon()) { 
            gameTimer.stop();          // หยุดตัวจับเวลาหน้าจอ
            gameManager.stopTimer();   // หยุดตัวจับเวลาในระบบเกม

            String currentUser = mainFrame.getCurrentUsername(); // ดึงชื่อผู้ใช้ปัจจุบัน
            int finalScore = gameManager.getScoreManager().getScore(); // ดึงคะแนนรวมสุดท้าย
            int finalTime = gameManager.getElapsedSeconds(); // ดึงเวลาที่ใช้ทั้งหมด
            
            String currentMode = currentRow + "x" + currentCol; 

            data.FileManager fileManager = new data.FileManager(); // สร้างออบเจ็กต์ FileManager สำหรับบันทึกไฟล์สถิติ
            fileManager.saveScoreRecord(currentUser, finalTime, finalScore, currentMode); // บันทึกลงไฟล์ CSV

            mainFrame.showResult(
                    finalScore,
                    finalTime,
                    currentMode // ส่งข้อมูลผลการเล่นไปแสดงที่หน้า ResultPanel
            );
        }
    }
}
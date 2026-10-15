package gui;

import java.awt.*;
import javax.swing.*;

import data.UserManager;
import model.CardSetType;

public class MainFrame extends JFrame {

    // ตัวแปรสำหรับควบคุมการสลับหน้าจอ (CardLayout) และพาเนลหลักที่เก็บทุกหน้าจอไว้
    private final CardLayout cardLayout; // ตัวจัดการสลับหน้าจอ ทำหน้าที่สลับ Panel ไปมาเหมือนเปิดไพ่
    private final JPanel container;      

    private final GamePanel gamePanel;   // หน้าจอสำหรับเล่นเกมหลัก
    private ResultPanel resultPanel;     // หน้าจอแสดงผลลัพธ์และคะแนนหลังเล่นจบ

    private LoginPanel loginPanel;       
    private SignUpPanel signUpPanel;     

    private final UserManager userManager = new UserManager(); // สร้างตัวจัดการระบบผู้ใช้กลาง เพื่อส่งต่อให้หน้า Login และ SignUp ใช้งานร่วมกัน

    private String currentUsername = ""; // ตัวแปรเก็บชื่อของคนที่กำลังเข้าสู่ระบบอยู่ตอนนี้

    public void setCurrentUsername(String username) {
        this.currentUsername = username;} // บันทึกชื่อผู้ใช้เมื่อล็อกอินสำเร็จ

    public String getCurrentUsername() {
        return currentUsername;} // ดึงชื่อผู้ใช้ไปใช้ เช่น ตอนบันทึกคะแนนลงไฟล์

    private CardSetType lastType = CardSetType.FRUITS; // จำค่าตัวเลือกเกมล่าสุดไว้ เพื่อให้พอกดเล่นซ้ำ (Play Again) จะได้ใช้ธีมและความยากเดิม
    private int lastRows = 4;
    private int lastCols = 4;

    // Constructer

    public MainFrame() {  // ตั้งค่าหน้าต่างหลักของโปรแกรม

        setTitle("Card Matching Game");
        setSize(800, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ตั้งค่าแบบ CardLayout เพื่อให้สลับหน้าจอไปมาได้เหมือนเปิดไพ่ทีละใบ
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // สร้างหน้าจอต่าง ๆ ของโปรแกรมโดยส่ง MainFrame นี้เข้าไปด้วย
        MainMenuPanel mainMenu = new MainMenuPanel(this);
        GameSetupPanel setupPanel = new GameSetupPanel(this);

        gamePanel = new GamePanel(this);

        loginPanel = new LoginPanel(this, userManager);
        signUpPanel = new SignUpPanel(this, userManager);

        // นำทุกอย่างใส่ลงใน container พร้อมตั้งชื่อระบุแต่ละอย่าง
        container.add(mainMenu,"MENU");
        container.add(setupPanel,"SETUP");
        container.add(gamePanel,"GAME");
        container.add(loginPanel,"LOGIN");
        container.add(signUpPanel,"SIGNUP");

        add(container);

        // เมื่อเปิดโปรแกรมมาจะให้หน้า login ขึ้นก่อนเป็นหน้าแรกสุด
        cardLayout.show(container,"LOGIN");
    }
    // Medthod
    public void showLogin() {
        cardLayout.show(
                container,
                "LOGIN"
        );
    }

    public void showSignUp() {
        cardLayout.show(
                container,
                "SIGNUP"
        );
    }

    public void showMenu() {
        cardLayout.show(
                container,
                "MENU"
        );
    }

    public void showSetup() {
        cardLayout.show(
                container,
                "SETUP"
        );
    }

    // สั่งเริ่มเกม โดยส่งประเภทชุดการ์ดและขนาดตารางเข้าไป
    public void showGame(CardSetType type, int rows, int cols) {
 
        gamePanel.startNewGame(type, rows, cols);
 
        lastType = type;
        lastRows = rows;
        lastCols = cols;
 
        // สลับไปหน้าจอเล่นเกม
        cardLayout.show(container, "GAME");
    }

    public void showGame() {
        showGame(lastType, lastRows, lastCols);
    }

    public void GameOver(){
        showMenu();
    }

    public void showResult(int score, int timeSeconds, String mode) {
    // ถ้าเคยมีหน้าจอผลลัพธ์เก่าค้างอยู่ ให้ลบออกก่อน เพื่อจะได้สร้างใหม่และอัปเดตคะแนนล่าสุด
    if (resultPanel != null) {
        container.remove(resultPanel);
    }
    // สร้างหน้าจอ ResultPanel ใหม่ พร้อมส่งข้อมูลคะแนน เวลา และโหมดเกมเข้าไป
    resultPanel = new ResultPanel(this, score, timeSeconds, mode);
    // เพิ่มหน้าจอผลลัพธ์ใหม่เข้าไปในพาเนลหลักโดยใช้ชื่อกำกับว่า "RESULT"
    container.add(resultPanel, "RESULT");
    // สลับหน้าจอหลักให้แสดงผลหน้า "RESULT" ขึ้นมาทันที
    cardLayout.show(container, "RESULT");
    }
}
package gui;

import java.awt.*;
import javax.swing.*;

import data.UserManager;
import model.CardSetType;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel container;

    private final GamePanel gamePanel;
    private ResultPanel resultPanel;

    private LoginPanel loginPanel;
    private SignUpPanel signUpPanel;

    private final UserManager userManager = new UserManager();

    // =========================================
    // 1. เพิ่มตัวแปรสำหรับเก็บชื่อผู้ใช้ที่กำลังล็อกอินอยู่
    // =========================================
    private String currentUsername = "Guest";

    // เมธอดสำหรับเซ็ตชื่อผู้ใช้ (เรียกใช้ตอน Login สำเร็จ)
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    // เมธอดสำหรับดึงชื่อผู้ใช้ไปใช้งาน (เรียกใช้ตอนบันทึก High Score ใน GamePanel)
    public String getCurrentUsername() {
        return currentUsername;
    }
    // =========================================

    // จำค่าที่เลือกไว้ล่าสุด เพื่อให้ปุ่ม PLAY AGAIN เล่นซ้ำด้วยธีม/ความยากเดิม
    private CardSetType lastType = CardSetType.FRUITS;
    private int lastRows = 4;
    private int lastCols = 4;

    public MainFrame() {

        setTitle("Card Matching Game");
        setSize(800, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // =========================
        // CARD LAYOUT
        // =========================

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // =========================
        // PANELS
        // =========================

        MainMenuPanel mainMenu = new MainMenuPanel(this);
        GameSetupPanel setupPanel = new GameSetupPanel(this);

        gamePanel = new GamePanel(this);

        loginPanel = new LoginPanel(this, userManager);
        signUpPanel = new SignUpPanel(this, userManager);

        // =========================
        // ADD PANELS
        // =========================

        container.add(mainMenu,"MENU");
        container.add(setupPanel,"SETUP");
        container.add(gamePanel,"GAME");
        container.add(loginPanel,"LOGIN");
        container.add(signUpPanel,"SIGNUP");

        // =========================
        // SHOW CONTAINER
        // =========================

        add(container);

        cardLayout.show(container,"LOGIN");
    }

    // =========================
    // SHOW LOGIN
    // =========================

    public void showLogin() {
        cardLayout.show(
                container,
                "LOGIN"
        );
    }

    // =========================
    // SHOW SIGN UP
    // =========================

    public void showSignUp() {
        cardLayout.show(
                container,
                "SIGNUP"
        );
    }

    // =========================
    // SHOW MENU
    // =========================

    public void showMenu() {
        cardLayout.show(
                container,
                "MENU"
        );
    }

    // =========================
    // SHOW SETUP
    // =========================

    public void showSetup() {
        cardLayout.show(
                container,
                "SETUP"
        );
    }

    // =========================
    // SHOW GAME
    // =========================

    public void showGame(CardSetType type, int rows, int cols) {
 
        boolean ok = gamePanel.startNewGame(type, rows, cols);
 
        if (!ok) {
            JOptionPane.showMessageDialog(
                    this,
                    "จำนวนรูปการ์ดของธีมนี้ไม่พอสำหรับระดับความยากที่เลือก",
                    "รูปการ์ดไม่พอ",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
 
        lastType = type;
        lastRows = rows;
        lastCols = cols;
 
        cardLayout.show(container, "GAME");
    }

    public void showGame() {
        showGame(lastType, lastRows, lastCols);
    }

    // =========================
    // SHOW RESULT
    // =========================

    public void showResult(int score, int timeSeconds, String mode) {
    if (resultPanel != null) {
        container.remove(resultPanel);
    }
    resultPanel = new ResultPanel(this, score, timeSeconds, mode);
    container.add(resultPanel, "RESULT");
    cardLayout.show(container, "RESULT");
}
}
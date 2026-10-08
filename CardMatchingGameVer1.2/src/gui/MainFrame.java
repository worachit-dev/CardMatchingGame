package gui;

import java.awt.*;
import javax.swing.*;

import data.UserManager;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel container;

    private final GamePanel gamePanel;
    private ResultPanel resultPanel;

    private LoginPanel loginPanel;
    private SignUpPanel signUpPanel;

    private final UserManager userManager = new UserManager();

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

        // แก้ตรงนี้: ต้องส่ง userManager เข้าไปด้วย ไม่งั้น compile ไม่ผ่าน
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

    public void showGame() {

        gamePanel.startNewGame();

        cardLayout.show(
                container,
                "GAME"
        );
    }

    // =========================
    // SHOW RESULT
    // =========================

    public void showResult(int score, int TImeSeconds) {

    if (resultPanel != null) {
        container.remove(resultPanel);
    }

    resultPanel = new ResultPanel(this, score, TImeSeconds);

    container.add(resultPanel, "RESULT");

    cardLayout.show(container, "RESULT");
}
}
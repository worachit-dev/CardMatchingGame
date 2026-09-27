package gui;
import java.awt.*;
import javax.swing.*;


public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel container;

    private final GamePanel gamePanel;
    private ResultPanel resultPanel;

    public MainFrame() {
        setTitle("Card Matching Game");
        setSize(800, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        MainMenuPanel mainMenu = new MainMenuPanel(this);
        gamePanel = new GamePanel(this);

        container.add(mainMenu, "MENU");
        container.add(gamePanel, "GAME");

        add(container);
    }

    public void showMenu() {
        cardLayout.show(container, "MENU");
    }

    public void showGame() {
        // เดิม showGame() แค่สลับหน้าจอ ไม่เคยสั่งเริ่มเกมใหม่เลย
        // เรียก startNewGame() ทุกครั้งที่เข้าหน้าเกม (ทั้งตอนกด START GAME และ PLAY AGAIN)
        gamePanel.startNewGame();
        cardLayout.show(container, "GAME");
    }

    /**
     * เรียกจาก GamePanel เมื่อเกมจบ (ชนะ/แพ้) เพื่อสร้างหน้า ResultPanel
     * พร้อมคะแนนจริงของรอบนั้น แล้วสลับไปแสดง
     */
    public void showResult(int score, boolean win) {

        if (resultPanel != null) {
            container.remove(resultPanel);
        }

        resultPanel = new ResultPanel(this, score, win);
        container.add(resultPanel, "RESULT");

        cardLayout.show(container, "RESULT");
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }/* */
}
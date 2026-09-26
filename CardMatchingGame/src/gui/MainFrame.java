package gui;
import java.awt.*;
import javax.swing.*;


public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel container;

    public MainFrame() {
        setTitle("Card Matching Game");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        MainMenuPanel mainMenu = new MainMenuPanel(this);
        GamePanel gamePanel = new GamePanel(this);

        container.add(mainMenu, "MENU");
        container.add(gamePanel, "GAME");

        add(container);
    }

    public void showMenu() {
        cardLayout.show(container, "MENU");
    }

    public void showGame() {
        cardLayout.show(container, "GAME");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
/**public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Card Matching Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showMainMenu();

        setVisible(true);
    }

    public void showMainMenu() {
        setContentPane(new MainMenuPanel(this));
        revalidate();
        repaint();
    }

    public void startGame() {
        setContentPane(new GamePanel(this));
        revalidate();
        repaint();
    }

    public void showResult() {
        setContentPane(new ResultPanel(this));
        revalidate();
        repaint();
    }
}
*/
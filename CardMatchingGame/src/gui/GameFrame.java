package gui; // 1. ต้องบอกว่าไฟล์นี้อยู่แพ็กเกจไหน
import game.GameManager; // 2. ต้องนำเข้าคลาส GameManager จากแพ็กเกจ game มาใช้งาน
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {
    private GameManager gameManager;

    private  JButton[] buttons = new JButton[16];

    public GameFrame(){
        gameManager = new GameManager();

        setTitle("Memory Card Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBoard();

        setVisible(true);
    }

    private void createBoard() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {

            buttons[i] = new JButton();

            buttons[i].setIcon(
                new ImageIcon("resources/back.png")
            );

            panel.add(buttons[i]);

        }

        add(panel);
    }
}

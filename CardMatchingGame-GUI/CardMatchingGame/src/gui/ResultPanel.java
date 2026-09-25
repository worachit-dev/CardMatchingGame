package gui;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {

    public ResultPanel(MainFrame mainFrame) {

        setLayout(new GridBagLayout());

        JPanel resultPanel =
                new JPanel();

        resultPanel.setLayout(
                new BoxLayout(
                        resultPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =========================
        // RESULT TITLE
        // =========================

        JLabel resultLabel =
                new JLabel("YOU WIN!");

        resultLabel.setFont(
                FontManager.getFont(42)
        );

        resultLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // SCORE
        // =========================

        JLabel scoreLabel =
                new JLabel("SCORE: 1200");

        scoreLabel.setFont(
                FontManager.getFont(24)
        );

        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // BUTTONS
        // =========================

        JButton playAgainButton =
                new JButton("PLAY AGAIN");

        JButton menuButton =
                new JButton("MAIN MENU");

        playAgainButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        menuButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // ACTION
        // =========================

        playAgainButton.addActionListener(e -> {
            mainFrame.showGame();
        });

        menuButton.addActionListener(e -> {
            mainFrame.showMenu();
        });

        // =========================
        // ADD
        // =========================

        resultPanel.add(resultLabel);

        resultPanel.add(
                Box.createVerticalStrut(20)
        );

        resultPanel.add(scoreLabel);

        resultPanel.add(
                Box.createVerticalStrut(40)
        );

        resultPanel.add(playAgainButton);

        resultPanel.add(
                Box.createVerticalStrut(15)
        );

        resultPanel.add(menuButton);

        add(resultPanel);
    }
}
package gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final MainFrame mainFrame;

    private final JLabel heartsLabel;
    private final JLabel scoreLabel;
    private final JLabel comboLabel;

    private final CardButton[] cardButtons;

    public GamePanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 235, 215));

        // =========================
        // TOP BAR
        // =========================

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 10, 30)
        );

        JLabel titleLabel = new JLabel("CARD MATCHING");
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        heartsLabel = new JLabel("♥  ♥  ♥");
        heartsLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(heartsLabel, BorderLayout.EAST);

        // =========================
        // SCORE BAR
        // =========================

        JPanel scorePanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 50, 5)
        );

        scorePanel.setOpaque(false);

        scoreLabel = new JLabel("SCORE: 0");
        comboLabel = new JLabel("COMBO: 0");

        scoreLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        comboLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        scorePanel.add(scoreLabel);
        scorePanel.add(comboLabel);

        // =========================
        // CARD BOARD
        // =========================

        JPanel boardPanel = new JPanel(
                new GridLayout(4, 4, 12, 12)
        );

        boardPanel.setOpaque(false);

        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        50,
                        15,
                        50
                )
        );

        cardButtons = new CardButton[16];

        for (int i = 0; i < 16; i++) {

            CardButton cardButton =
                    new CardButton(i);

            cardButtons[i] = cardButton;

            boardPanel.add(cardButton);
        }

        // =========================
        // BOTTOM BAR
        // =========================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        bottomPanel.setOpaque(false);

        JButton backButton =
                new JButton("BACK TO MENU");

        backButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            mainFrame.showMenu();
        });

        bottomPanel.add(backButton);

        // =========================
        // ADD COMPONENTS
        // =========================

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setOpaque(false);

        centerPanel.add(scorePanel, BorderLayout.NORTH);
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
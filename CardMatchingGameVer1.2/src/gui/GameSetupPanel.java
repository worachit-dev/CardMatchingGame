package gui;

import javax.swing.*;
import java.awt.*;

public class GameSetupPanel extends JPanel {

    private final MainFrame mainFrame;

    private JButton fruitsButton;
    private JButton animalButton;

    private JButton normalButton;
    private JButton hardButton;

    public GameSetupPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =========================
        // Main Container
        // =========================

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setOpaque(false);

        // =========================
        // Title
        // =========================

        JLabel title =
                new JLabel("CARD MATCHING");

        title.setFont(
                FontManager.getFont(40)
        );

        title.setForeground(
                new Color(70, 45, 30)
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // Theme Label
        // =========================

        JLabel themeLabel =
                new JLabel("CHOOSE CARD THEME");

        themeLabel.setFont(
                FontManager.getFont(22)
        );

        themeLabel.setForeground(
                new Color(70, 45, 30)
        );

        themeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // Theme Buttons
        // =========================

        JPanel themePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                5
                        )
                );

        themePanel.setOpaque(false);

        fruitsButton =
                createOptionButton("FRUITS");

        animalButton =
                createOptionButton("ANIMAL");

        themePanel.add(fruitsButton);
        themePanel.add(animalButton);

        // =========================
        // Difficulty Label
        // =========================

        JLabel difficultyLabel =
                new JLabel("CHOOSE DIFFICULTY");

        difficultyLabel.setFont(
                FontManager.getFont(22)
        );

        difficultyLabel.setForeground(
                new Color(70, 45, 30)
        );

        difficultyLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // Difficulty Buttons
        // =========================

        JPanel difficultyPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                5
                        )
                );

        difficultyPanel.setOpaque(false);

        normalButton =
                createOptionButton(
                        "<html><center>NORMAL<br>4 × 4</center></html>"
                );

        hardButton =
                createOptionButton(
                        "<html><center>HARD<br>5 × 6</center></html>"
                );

        difficultyPanel.add(normalButton);
        difficultyPanel.add(hardButton);

        // =========================
        // Start Button
        // =========================

        JButton startButton =
                createMainButton("START GAME");

        startButton.addActionListener(e -> {

            // ตอนนี้ยังไม่ส่งค่าไป GamePanel
            // เอาเฉพาะ GUI ก่อน

            mainFrame.showGame();
        });

        // =========================
        // Back Button
        // =========================

        JButton backButton =
                createMainButton("BACK TO MENU");

        backButton.addActionListener(e -> {

            mainFrame.showMenu();

        });

        // =========================
        // Add Everything
        // =========================

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(30)
        );

        panel.add(themeLabel);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(themePanel);

        panel.add(
                Box.createVerticalStrut(25)
        );

        panel.add(difficultyLabel);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(difficultyPanel);

        panel.add(
                Box.createVerticalStrut(30)
        );

        panel.add(startButton);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(backButton);

        // =========================
        // Put panel in center
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(panel, gbc);
    }

    // ==================================================
    // Option Button
    // ==================================================

    private JButton createOptionButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                FontManager.getFont(17)
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                new Color(120, 85, 60)
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(150, 60)
        );

        return button;
    }

    // ==================================================
    // Main Button
    // ==================================================

    private JButton createMainButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                FontManager.getFont(18)
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                new Color(80, 55, 40)
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(260, 50)
        );

        return button;
    }
}
package gui;

import javax.swing.*;

import model.CardSetType;

import java.awt.*;

public class GameSetupPanel extends JPanel {

    private final MainFrame mainFrame;

    private JButton fruitsButton;
    private JButton animalButton;

    private JButton normalButton;
    private JButton hardButton;

    // เก็บค่าที่ผู้เล่นเลือก (ค่าเริ่มต้น FRUITS / NORMAL)
    private CardSetType selectedType = CardSetType.FRUITS;
    private boolean hardMode = false;
 
    // สีปุ่มปกติ / สีปุ่มที่ถูกเลือก
    private static final Color NORMAL_COLOR = new Color(120, 85, 60);
    private static final Color SELECTED_COLOR = new Color(200, 120, 40);

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

        fruitsButton.addActionListener(e -> {
            selectedType = CardSetType.FRUITS;
            updateButtonColors();
        });
 
        animalButton.addActionListener(e -> {
            selectedType = CardSetType.ANIMALS;
            updateButtonColors();
        });

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

        normalButton.addActionListener(e -> {
                hardMode = false;
                updateButtonColors();
        });

        hardButton.addActionListener(e -> {
                hardMode = true;
                updateButtonColors();
        });

        difficultyPanel.add(normalButton);
        difficultyPanel.add(hardButton);

        // =========================
        // Start Button
        // =========================

        JButton startButton =
                createMainButton("START GAME");

        startButton.addActionListener(e -> {

        int rows;
        int cols;
        if (hardMode) {
                rows = 5;
                cols = 6;
        } else {
                rows = 4;
                cols = 4;
        }

                mainFrame.showGame(selectedType, rows, cols);
        });
        // =========================
        // Leaderboard Button (สร้าง Action ตรงนี้)
        // =========================
        JButton leaderboardButton =
                createMainButton("LEADBOARD");

        leaderboardButton.addActionListener(e -> {
            LeaderboardDialog dialog = new LeaderboardDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);
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
        panel.add(Box.createVerticalStrut(30));

        panel.add(themeLabel);
        panel.add(Box.createVerticalStrut(10));

        panel.add(themePanel);
        panel.add(Box.createVerticalStrut(25));

        panel.add(difficultyLabel);
        panel.add(Box.createVerticalStrut(10));

        panel.add(difficultyPanel);
        panel.add(Box.createVerticalStrut(30));

        panel.add(startButton);
        panel.add(Box.createVerticalStrut(10));

        panel.add(leaderboardButton); // <-- วางไว้ตรงกลางระหว่าง Start และ Back
        panel.add(Box.createVerticalStrut(10));

        panel.add(backButton);

        // =========================
        // Put panel in center
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(panel, gbc);
    }

    private void updateButtonColors() {
 
        fruitsButton.setBackground(
                selectedType == CardSetType.FRUITS ? SELECTED_COLOR : NORMAL_COLOR);
 
        animalButton.setBackground(
                selectedType == CardSetType.ANIMALS ? SELECTED_COLOR : NORMAL_COLOR);
 
        normalButton.setBackground(
                !hardMode ? SELECTED_COLOR : NORMAL_COLOR);
 
        hardButton.setBackground(
                hardMode ? SELECTED_COLOR : NORMAL_COLOR);
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
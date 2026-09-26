package gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private Image backgroundImage;

    public MainMenuPanel(MainFrame mainFrame) {

        backgroundImage =
                new ImageIcon("resources/menu_bg.png")
                        .getImage();

        setLayout(new GridBagLayout());

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setOpaque(false);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("CARD MATCHING");

        titleLabel.setFont(
                loadGameFont(64)
        );

        titleLabel.setForeground(
                new Color(153, 0, 0)
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // BUTTONS
        // =========================

        JButton startButton =
                createMenuButton("START GAME");

        JButton howToPlayButton =
                createMenuButton("HOW TO PLAY");

        JButton exitButton =
                createMenuButton("EXIT");

        // =========================
        // ACTIONS
        // =========================

        startButton.addActionListener(e -> {
            mainFrame.showGame();
        });

        howToPlayButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                this,
                """
                HOW TO PLAY

                Match all the identical cards.\n\n
                Press two cards to reveal\n
                If the cards match, they remain face-up\n
                If the cards don't match,\n
                they will be flipped back over.\n
                you can get bonus points for quick matches!\n",
                """
                ,
                "How To Play",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // =========================
        // LAYOUT
        // =========================

        menuPanel.add(titleLabel);

        menuPanel.add(
                Box.createVerticalStrut(50)
        );

        menuPanel.add(startButton);

        menuPanel.add(
                Box.createVerticalStrut(15)
        );

        menuPanel.add(howToPlayButton);

        menuPanel.add(
                Box.createVerticalStrut(15)
        );

        menuPanel.add(exitButton);

        add(menuPanel);
    }

    // =========================
    // CREATE BUTTON
    // =========================

    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(
                new Color(80, 55, 40)
        );

        button.setFocusPainted(false);

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setPreferredSize(
                new Dimension(260, 55)
        );

        button.setMaximumSize(
                new Dimension(260, 55)
        );

        return button;
    }

    // =========================
    // GAME FONT
    // =========================

    private Font loadGameFont(float size) {

        try {

            Font font =
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            new java.io.File(
                                    "resources/fonts/Taste Bread.otf"
                            )
                    );

            return font.deriveFont(size);

        } catch (Exception e) {

            return new Font(
                    "Arial",
                    Font.BOLD,
                    (int) size
            );
        }
    }

    // =========================
    // BACKGROUND
    // =========================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (backgroundImage != null) {

            g.drawImage(
                    backgroundImage,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}
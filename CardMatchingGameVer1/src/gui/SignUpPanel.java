package gui;

import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends JPanel {

    private final MainFrame mainFrame;

    public SignUpPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());

        setOpaque(false);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel signUpPanel = new JPanel();

        signUpPanel.setLayout(
                new BoxLayout(
                        signUpPanel,
                        BoxLayout.Y_AXIS
                )
        );

        signUpPanel.setOpaque(false);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("SIGN UP");

        titleLabel.setFont(
                FontManager.getFont(50)
        );

        titleLabel.setForeground(
                new Color(153, 0, 0)
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // USERNAME
        // =========================

        JLabel usernameLabel =
                new JLabel("USERNAME");

        usernameLabel.setFont(
                FontManager.getFont(18)
        );

        usernameLabel.setForeground(
                new Color(80, 55, 40)
        );

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JTextField usernameField =
                new JTextField();

        usernameField.setFont(
                FontManager.getFont(18)
        );

        usernameField.setMaximumSize(
                new Dimension(300, 45)
        );

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel =
                new JLabel("PASSWORD");

        passwordLabel.setFont(
                FontManager.getFont(18)
        );

        passwordLabel.setForeground(
                new Color(80, 55, 40)
        );

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JPasswordField passwordField =
                new JPasswordField();

        passwordField.setFont(
                FontManager.getFont(18)
        );

        passwordField.setMaximumSize(
                new Dimension(300, 45)
        );

        // =========================
        // CONFIRM PASSWORD
        // =========================

        JLabel confirmPasswordLabel =
                new JLabel("CONFIRM PASSWORD");

        confirmPasswordLabel.setFont(
                FontManager.getFont(18)
        );

        confirmPasswordLabel.setForeground(
                new Color(80, 55, 40)
        );

        confirmPasswordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JPasswordField confirmPasswordField =
                new JPasswordField();

        confirmPasswordField.setFont(
                FontManager.getFont(18)
        );

        confirmPasswordField.setMaximumSize(
                new Dimension(300, 45)
        );

        // =========================
        // SIGN UP BUTTON
        // =========================

        JButton signUpButton =
                new JButton("SIGN UP");

        signUpButton.setFont(
                FontManager.getFont(18)
        );

        signUpButton.setForeground(Color.WHITE);

        signUpButton.setBackground(
                new Color(80, 55, 40)
        );

        signUpButton.setFocusPainted(false);

        signUpButton.setBorderPainted(false);

        signUpButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        signUpButton.setPreferredSize(
                new Dimension(300, 50)
        );

        signUpButton.setMaximumSize(
                new Dimension(300, 50)
        );

        // =========================
        // SIGN UP ACTION
        // =========================
        
        signUpButton.addActionListener(e -> {mainFrame.showMenu();});

        // =========================
        // LOGIN LINK
        // =========================

        JLabel loginLabel =
                new JLabel(
                        "<html>You have an account? "
                        + "<u>LOGIN</u></html>"
                );

        loginLabel.setFont(
                FontManager.getFont(15)
        );

        loginLabel.setForeground(
                new Color(80, 55, 40)
        );

        loginLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginLabel.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // =========================
        // LOGIN ACTION
        // =========================

        loginLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        mainFrame.showLogin();
                    }
                }
        );

        // =========================
        // ADD COMPONENTS
        // =========================

        signUpPanel.add(titleLabel);

        signUpPanel.add(
                Box.createVerticalStrut(25)
        );

        signUpPanel.add(usernameLabel);

        signUpPanel.add(
                Box.createVerticalStrut(5)
        );

        signUpPanel.add(usernameField);

        signUpPanel.add(
                Box.createVerticalStrut(12)
        );

        signUpPanel.add(passwordLabel);

        signUpPanel.add(
                Box.createVerticalStrut(5)
        );

        signUpPanel.add(passwordField);

        signUpPanel.add(
                Box.createVerticalStrut(12)
        );

        signUpPanel.add(confirmPasswordLabel);

        signUpPanel.add(
                Box.createVerticalStrut(5)
        );

        signUpPanel.add(confirmPasswordField);

        signUpPanel.add(
                Box.createVerticalStrut(20)
        );

        signUpPanel.add(signUpButton);

        signUpPanel.add(
                Box.createVerticalStrut(15)
        );

        signUpPanel.add(loginLabel);

        add(signUpPanel);
    }
}
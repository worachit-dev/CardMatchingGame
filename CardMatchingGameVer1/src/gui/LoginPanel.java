package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());

        // =========================
        // BACKGROUND
        // =========================

        setOpaque(false);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel loginPanel = new JPanel();

        loginPanel.setLayout(
                new BoxLayout(
                        loginPanel,
                        BoxLayout.Y_AXIS
                )
        );

        loginPanel.setOpaque(false);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("LOGIN");

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
        // LOGIN BUTTON
        // =========================

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                FontManager.getFont(18)
        );

        loginButton.setForeground(Color.WHITE);

        loginButton.setBackground(
                new Color(80, 55, 40)
        );

        loginButton.setFocusPainted(false);

        loginButton.setBorderPainted(false);

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setPreferredSize(
                new Dimension(300, 50)
        );

        loginButton.setMaximumSize(
                new Dimension(300, 50)
        );

        // =========================
        // SIGN UP LINK
        // =========================

        JLabel signUpLabel =
                new JLabel(
                        "<html>Don't have an account? "
                        + "<u>SIGN UP</u></html>"
                );

        signUpLabel.setFont(
                FontManager.getFont(15)
        );

        signUpLabel.setForeground(
                new Color(80, 55, 40)
        );

        signUpLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =========================
        // SIGN UP ACTION
        // =========================

        signUpLabel.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        signUpLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        mainFrame.showSignUp();
                    }
                }
        );

        // =========================
        // ADD COMPONENTS
        // =========================

        loginPanel.add(titleLabel);

        loginPanel.add(
                Box.createVerticalStrut(30)
        );

        loginPanel.add(usernameLabel);

        loginPanel.add(
                Box.createVerticalStrut(5)
        );

        loginPanel.add(usernameField);

        loginPanel.add(
                Box.createVerticalStrut(15)
        );

        loginPanel.add(passwordLabel);

        loginPanel.add(
                Box.createVerticalStrut(5)
        );

        loginPanel.add(passwordField);

        loginPanel.add(
                Box.createVerticalStrut(25)
        );

        loginPanel.add(loginButton);

        loginPanel.add(
                Box.createVerticalStrut(20)
        );

        loginPanel.add(signUpLabel);

        add(loginPanel);
    }
}
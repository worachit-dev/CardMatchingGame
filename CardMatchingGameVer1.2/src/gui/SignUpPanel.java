package gui;

import data.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SignUpPanel extends JPanel {

    private final MainFrame mainFrame;
    private final UserManager userManager;

    public SignUpPanel(MainFrame mainFrame, UserManager userManager) {

        this.mainFrame = mainFrame;
        this.userManager = userManager;

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

        usernameField.setPreferredSize(
                new Dimension(300, 30)
        );

        // จำกัดให้พิมพ์ได้แค่ตัวอักษร a-z, A-Z และตัวเลข 0-9 เท่านั้น ห้ามอักขระพิเศษ
        usernameField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

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

        passwordField.setPreferredSize(
                new Dimension(300, 30)
        );


        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

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


        confirmPasswordField.setPreferredSize(
                new Dimension(300, 30)
        );


        // จำกัดให้พิมพ์ได้แค่ตัวอักษร a-z, A-Z และตัวเลข 0-9 เท่านั้น ห้ามอักขระพิเศษ
        confirmPasswordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

        // =========================
        // ERROR MESSAGE
        // =========================

        JLabel errorLabel = new JLabel(" ");

        errorLabel.setFont(
                FontManager.getFont(14)
        );

        errorLabel.setForeground(
                new Color(180, 0, 0)
        );

        errorLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
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

        // ตรวจรูปแบบ + รหัสผ่านตรงกัน + ชื่อไม่ซ้ำ 
        signUpButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            if (username.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                errorLabel.setText("กรุณากรอกข้อมูลให้ครบทุกช่อง");
                return;
            }

            if (!userManager.isValidFormat(username) || !userManager.isValidFormat(password)) {
                errorLabel.setText("ใช้ได้เฉพาะตัวอักษร a-z, A-Z และตัวเลข 0-9 เท่านั้น");
                return;
            }

            if (!password.equals(confirm)) {
                errorLabel.setText("รหัสผ่านและยืนยันรหัสผ่านไม่ตรงกัน");
                return;
            }

            if (userManager.isUsernameTaken(username)) {
                errorLabel.setText("ชื่อผู้ใช้นี้มีคนใช้แล้ว");
                return;
            }

            boolean success = userManager.register(username, password);

            if (success) {

                errorLabel.setText(" ");

                JOptionPane.showMessageDialog(
                        this,
                        "สมัครสมาชิกสำเร็จ กรุณาเข้าสู่ระบบ",
                        "สมัครสำเร็จ",
                        JOptionPane.INFORMATION_MESSAGE
                );

                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");

                mainFrame.showLogin();

            } else {
                errorLabel.setText("สมัครสมาชิกไม่สำเร็จ กรุณาลองใหม่");
            }
        });

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
                Box.createVerticalStrut(10)
        );

        signUpPanel.add(errorLabel);

        signUpPanel.add(
                Box.createVerticalStrut(10)
        );

        signUpPanel.add(signUpButton);

        signUpPanel.add(
                Box.createVerticalStrut(15)
        );

        signUpPanel.add(loginLabel);

        add(signUpPanel);
    }
}
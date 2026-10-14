package gui;

import data.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SignUpPanel extends JPanel {

    private final MainFrame mainFrame;
    private final UserManager userManager;
    // Constructer
    // สร้างหน้าจอสมัครสมาชิก โดยรับ MainFrame และ UserManager เข้ามาใช้งานร่วมกัน
    public SignUpPanel(MainFrame mainFrame, UserManager userManager) {

        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());

        setOpaque(false);

        JPanel signUpPanel = new JPanel();

        signUpPanel.setLayout(
                new BoxLayout(
                        signUpPanel,
                        BoxLayout.Y_AXIS
                )
        );

        signUpPanel.setOpaque(false);

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

        // อนุญาตให้พิมพ์เฉพาะตัวอักษรภาษาอังกฤษ (a-z, A-Z) และตัวเลข (0-9) เท่านั้น
        usernameField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume(); // ตัดอักขระพิเศษทิ้งทันที
                }
            }
        });

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

        // ช่องรหัสผ่านจำกัดให้พิมพ์เฉพาะภาษาอังกฤษและตัวเลข
        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

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

        // ช่องยืนยันรหัสผ่านก็จำกัดรูปแบบตัวอักษร
        confirmPasswordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

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

        // ปุ่มกดสมัครสมาชิก มีการตรวจสอบความถูกต้องก่อนบันทึกข้อมูล
        signUpButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            // เช็คว่ากรอกข้อมูลครบทุกช่องหรือยัง
            if (username.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                JOptionPane.showMessageDialog(
                this,
                "Please compelete all fields",
                "Sign up failed",
                JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ตรวจสอบรูปแบบตัวอักษรว่าถูกต้องตามเงื่อนไขไหม
            if (!userManager.isValidFormat(username) || !userManager.isValidFormat(password)) {
                JOptionPane.showMessageDialog(
                this,
                "Please use only letters (a-z, A-Z) and numbers (0-9)",
                "Sign up failed",
                JOptionPane.WARNING_MESSAGE);
                return;
            }

            // เช็คว่ารหัสผ่านทั้งสองช่องตรงกันไหม
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(
                this,
                "Password don't match",
                "Sign up failed",
                JOptionPane.WARNING_MESSAGE);
                return;
            }

            // เช็คว่าชื่อผู้ใช้นี้ถูกคนอื่นสมัครไปแล้วหรือยัง
            if (userManager.isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(
                this,
                "Username is already taken",
                "Sign up failed",
                JOptionPane.WARNING_MESSAGE);
                return;
            }

            // สั่งบันทึกข้อมูลผู้ใช้ใหม่ผ่านระบบ UserManager
            boolean success = userManager.register(username, password);

            if (success) {
                errorLabel.setText(" ");

                // ถ้าสมัครสำเร็จ แจ้งเตือนแล้วพาผู้เล่นกลับไปหน้า Login
                JOptionPane.showMessageDialog(
                        this,
                        "Sign up succeses Please Login",
                        "Sign Up Succeses",
                        JOptionPane.INFORMATION_MESSAGE
                );

                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");

                mainFrame.showLogin();

            } else {
                JOptionPane.showMessageDialog(
                this,
                "Registration failed. Please try again.",
                "Sign up failed",
                JOptionPane.WARNING_MESSAGE);
                return;
            }
        });

        JLabel loginLabel =
                new JLabel(
                        "<html>You have an account? "
                        + "<b><u>LOGIN</u></b></html>"
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

        // คลิกที่ข้อความ Login เพื่อสลับกลับไปหน้าจอเข้าสู่ระบบ
        loginLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        mainFrame.showLogin();
                    }
                }
        );

        signUpPanel.add(titleLabel);
        signUpPanel.add(Box.createVerticalStrut(25));
        signUpPanel.add(usernameLabel);
        signUpPanel.add(Box.createVerticalStrut(5));
        signUpPanel.add(usernameField);
        signUpPanel.add(Box.createVerticalStrut(12));
        signUpPanel.add(passwordLabel);
        signUpPanel.add(Box.createVerticalStrut(5));
        signUpPanel.add(passwordField);
        signUpPanel.add(Box.createVerticalStrut(12));
        signUpPanel.add(confirmPasswordLabel);
        signUpPanel.add(Box.createVerticalStrut(5));
        signUpPanel.add(confirmPasswordField);
        signUpPanel.add(Box.createVerticalStrut(10));
        signUpPanel.add(errorLabel);
        signUpPanel.add(Box.createVerticalStrut(10));
        signUpPanel.add(signUpButton);
        signUpPanel.add(Box.createVerticalStrut(15));
        signUpPanel.add(loginLabel);

        add(signUpPanel);
    }
}
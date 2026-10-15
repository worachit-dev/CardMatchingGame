package gui;

import data.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private final UserManager userManager;

    // รับ UserManager ตัวเดียวกับตอนสมัครเข้ามา เพื่อให้เช็คข้อมูลในระบบได้
    public LoginPanel(MainFrame mainFrame, UserManager userManager) {

        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());

        setOpaque(false);

        // พาเนลหลักสำหรับจัดวางส่วนประกอบทั้งหมดในหน้า Login แบบเรียงลงมาด้านล่าง
        JPanel loginPanel = new JPanel();

        loginPanel.setLayout(
                new BoxLayout(
                        loginPanel,
                        BoxLayout.Y_AXIS
                )
        );

        loginPanel.setOpaque(false);

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

        // ยอมให้พิมพ์ได้เฉพาะภาษาอังกฤษ(a-z, A-Z)และตัวเลข(0-9)เท่านั้นห้ามพิมพ์อักขระพิเศษ
        usernameField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume(); // ตัดตัวอักษรพิเศษทิ้ง ไม่ให้แสดงบนช่องกรอก
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

        // ช่องรหัสผ่านให้พิมพ์ได้เฉพาะตัวอักษรภาษาอังกฤษและตัวเลข
        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

        JLabel errorLabel = new JLabel("");

        errorLabel.setFont(
                FontManager.getFont(14)
        );

        errorLabel.setForeground(
                new Color(180, 0, 0)
        );

        errorLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                FontManager.getFont(18)
        );

        loginButton.setForeground(Color.WHITE);

        loginButton.setBackground(
                new Color(80, 55, 40)
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setPreferredSize(
                new Dimension(300, 50)
        );

        loginButton.setMaximumSize(
                new Dimension(300, 50)
        );

        // ปุ่มกดเข้าสู่ระบบ: เช็คข้อมูลที่พิมพ์กับระบบฐานข้อมูล
        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            // เช็คว่ากรอกข้อมูลครบหรือยัง ถ้าเว้นว่างไว้ให้เด้งเตือน
            if (user.isEmpty() || pass.isEmpty()) { 
                JOptionPane.showMessageDialog(
                    this,
                    "Username or password not entered",
                    "Login failed",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // ตรวจสอบชื่อผู้ใช้และรหัสผ่านในระบบว่าถูกต้องไหม
            if (userManager.login(user, pass)) {

                // ถ้าล็อกอินผ่าน บันทึกชื่อผู้ใช้เก็บไว้ใน MainFrame
                mainFrame.setCurrentUsername(user);
                
                // ล้างค่าช่องกรอกให้ว่างเตรียมไว้รอบหน้า
                usernameField.setText("");
                passwordField.setText("");
                
                mainFrame.showMenu();
                
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Username or password is wrong",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE
                );
                passwordField.setText("");
            }
        });

        JLabel signUpLabel =
                new JLabel(
                        "<html>Don't have an account? "
                        + "<b><u>SIGN UP</u></b></html>"
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

        signUpLabel.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // คลิกที่ข้อความ Sign Up เพื่อสลับไปหน้าจอสมัครสมาชิก
        signUpLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        mainFrame.showSignUp();
                    }
                }
        );

        // นำคอมโพเนนต์ทั้งหมดมารวมใส่ใน loginPanel พร้อมจัดระยะห่างแนวตั้ง
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(usernameLabel);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(errorLabel);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(signUpLabel);

        add(loginPanel);
    }
}
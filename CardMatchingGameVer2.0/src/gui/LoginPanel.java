package gui;

import data.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private final UserManager userManager;

    // รับ UserManager ตัวเดียวกับที่ SignUpPanel ใช้ เข้ามาทาง constructor
    // แทนที่จะสร้าง new UserManager() เองแยกต่างหาก (ไม่งั้นข้อมูลจะไม่ตรงกัน)
    public LoginPanel(MainFrame mainFrame, UserManager userManager) {

        this.mainFrame = mainFrame;
        this.userManager = userManager;

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

        usernameField.setPreferredSize(
                new Dimension(300, 30)
        );

        // พิมพ์ได้แค่ตัวอักษร a-z, A-Z และตัวเลข 0-9 เท่านั้น ห้ามอักขระพิเศษ

        usernameField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                // อนุญาตแค่ a-z, A-Z, 0-9 และปุ่มควบคุม (Backspace, Delete, Enter)
                if (!Character.isLetterOrDigit(c) && !Character.isISOControl(c)) {
                    e.consume(); // ยกเลิกตัวอักษรนี้ ไม่ให้ขึ้นในช่อง
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

        // จำกัดให้พิมพ์ได้แค่ตัวอักษร a-z, A-Z และตัวเลข 0-9 เท่านั้น ห้ามอักขระพิเศษ
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
        // ERROR MESSAGE
        // =========================

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

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setPreferredSize(
                new Dimension(300, 50)
        );

        loginButton.setMaximumSize(
                new Dimension(300, 50)
        );

        // ตรวจสอบ username/password กับข้อมูลที่เคยสมัครไว้จริง ก่อนจะยอมเข้าเมนู

        loginButton.addActionListener(e -> {
    String user = usernameField.getText().trim();
    String pass = new String(passwordField.getPassword());

    // 1. เช็คก่อนว่าเว้นว่างไว้หรือเปล่า
    if (user.isEmpty() || pass.isEmpty()) { 
        JOptionPane.showMessageDialog(
            this,
            "Username or password not entered",
            "Login failed",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 2. ตรวจสอบข้อมูลรหัสผ่านกับระบบ
    if (userManager.login(user, pass)) {
        // บันทึกชื่อผู้ใช้ปัจจุบันลงใน MainFrame
        mainFrame.setCurrentUsername(user);
        
        // ล้างค่าในช่องกรอก
        usernameField.setText("");
        passwordField.setText("");
        
        // สลับไปหน้า Menu หลักของเกม (ตรงนี้สำคัญมากที่ต้องมีครับ!)
        mainFrame.showMenu();
        
        } else {
        // ถ้าชื่อหรือรหัสผ่านผิด
        JOptionPane.showMessageDialog(
            this,
            "Username or password is wrong",
            "Login failed",
            JOptionPane.ERROR_MESSAGE
                );
        passwordField.setText("");
        }
        });



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
                Box.createVerticalStrut(10)
        );

        loginPanel.add(errorLabel);

        loginPanel.add(
                Box.createVerticalStrut(10)
        );

        loginPanel.add(loginButton);

        loginPanel.add(
                Box.createVerticalStrut(20)
        );

        loginPanel.add(signUpLabel);

        add(loginPanel);
    }
}
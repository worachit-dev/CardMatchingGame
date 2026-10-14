package data;

import java.io.*;
import java.util.ArrayList;


public class UserManager {

    private static final String USER_FILE = "data/users.csv";

    private ArrayList<String> usernames;
    private ArrayList<String> passwords;

    public UserManager() {
        usernames = new ArrayList<>();
        passwords = new ArrayList<>();
        loadUsers();
    }

    /**
     * ตรวจว่าข้อความมีแต่ตัวอักษร a-z, A-Z หรือตัวเลข 0-9 เท่านั้น
     */
    public boolean isValidFormat(String text) {

        if (text == null || text.length() == 0) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /** หาตำแหน่ง (index) ของ username ใน ArrayList ถ้าไม่เจอคืน -1 */
    private int findUserIndex(String username) {

        for (int i = 0; i < usernames.size(); i++) {

            if (usernames.get(i).equals(username)) {
                return i;
            }
        }

        return -1;
    }

    /** เช็คว่าชื่อผู้ใช้นี้มีคนใช้ไปแล้วหรือยัง */
    public boolean isUsernameTaken(String username) {
        return findUserIndex(username) != -1;
    }

    /** สมัครสมาชิกใหม่ คืนค่า true ถ้าสำเร็จ, false ถ้าไม่สำเร็จ (รูปแบบผิด/ชื่อซ้ำ) */
    public boolean register(String username, String password) {

        if (!isValidFormat(username) || !isValidFormat(password)) {
            return false;
        }

        if (isUsernameTaken(username)) {
            return false;
        }

        usernames.add(username);
        passwords.add(password);

        saveUsers();

        return true;
    }

    /** ตรวจสอบ username/password ตอนล็อกอิน คืนค่า true ถ้าตรงกัน */
    public boolean login(String username, String password) {

        int index = findUserIndex(username);

        if (index == -1) {
            return false;
        }

        return passwords.get(index).equals(password);
    }

    /** อ่านไฟล์ data/users.csv เข้ามาเก็บใน ArrayList ตอนเปิดโปรแกรม */
    private void loadUsers() {

        File file = new File(USER_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().length() == 0) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 2) {
                    usernames.add(parts[0]);
                    passwords.add(parts[1]);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("อ่านไฟล์ users.csv ไม่สำเร็จ: " + e.getMessage());
        }
    }

    /** เขียน ArrayList ทั้งหมดทับลงไฟล์ data/users.csv ใหม่ทุกครั้งที่มีการสมัครสมาชิก */
    private void saveUsers() {

        File file = new File(USER_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < usernames.size(); i++) {
                writer.write(usernames.get(i) + "," + passwords.get(i));
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("บันทึกไฟล์ users.csv ไม่สำเร็จ: " + e.getMessage());
        }
    }
}

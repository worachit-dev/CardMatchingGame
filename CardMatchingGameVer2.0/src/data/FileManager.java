package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    // กำหนดตำแหน่งไฟล์สำหรับเก็บคะแนนสูงสุด (อยู่ข้างนอก src)
    private static final String HIGH_SCORE_FILE = "data/highscore.txt";

    // อ่านไฟล์ (Load High Score)
    public int loadHighScore() {
        File file = new File(HIGH_SCORE_FILE);
        
        // ถ้ายังไม่มีไฟล์ ให้คืนค่า 0
        if (!file.exists()) {
            return 0; 
        }

        // เปิดไฟล์เพื่ออ่านตัวเลขคะแนน
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    // เขียนไฟล์ (Save High Score)
    public void saveHighScore(int score) {
        // เช็คก่อนว่ามีโฟลเดอร์ data หรือยัง ถ้ายังไม่มีให้สร้างขึ้นมาใหม่
        File parentDir = new File("data");
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        // เขียนคะแนนลงไปในไฟล์ highscore.txt
        try (FileWriter writer = new FileWriter(HIGH_SCORE_FILE)) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
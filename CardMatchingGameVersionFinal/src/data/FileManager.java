package data;

import model.ScoreRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager {
    private static final String HIGH_SCORE_FILE = "data/highscore.csv";

    // Medthod
    // บันทึกผลการเล่น โดยแยกตามโหมดและตรวจสอบสถิติที่ดีกว่า
    public void saveScoreRecord(String username, int timeSeconds, int score, String mode) {
        File parentDir = new File("data");
        if (!parentDir.exists()) {
            parentDir.mkdirs(); // ถ้ายังไม่มีโฟลเดอร์ data ให้สร้างขึ้นมาใหม่
        }

        List<ScoreRecord> records = loadAllScores(); // โหลดข้อมูลคะแนนเดิมทั้งหมด
        boolean found = false; // เช็คว่าเคยมีประวัติการเล่นในโหมดนี้ไหม

        for (int i = 0; i < records.size(); i++) {
            ScoreRecord r = records.get(i);
            // เช็คว่าชื่อและโหมดเกมตรงกันไหม
            if (r.getUsername().trim().equalsIgnoreCase(username.trim()) && r.getMode().equalsIgnoreCase(mode)) {
                found = true;
                
                // ถ้าทำเวลาได้น้อยกว่าเดิม หรือเวลาเท่ากันแต่ได้คะแนนมากกว่า ให้ทำการอัปเดตสถิติใหม่
                if (timeSeconds < r.getTimeSeconds() || (timeSeconds == r.getTimeSeconds() && score > r.getScore())) {
                    records.set(i, new ScoreRecord(username, timeSeconds, score, mode));
                }
                break;
            }
        }

        // ถ้ายังไม่เคยมีประวัติในโหมดนี้ ให้เพิ่มเรคอร์ดใหม่เข้าไปต่อท้าย
        if (!found) {
            records.add(new ScoreRecord(username, timeSeconds, score, mode));
        }

        // เขียนข้อมูลทั้งหมดทับลงในไฟล์ CSV ด้วยรูปแบบ username,time,score,mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, false))) {
            for (ScoreRecord r : records) {
                writer.write(r.getUsername() + "," + r.getTimeSeconds() + "," + r.getScore() + "," + r.getMode());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Medthod
    public List<ScoreRecord> loadAllScores() {
        List<ScoreRecord> records = new ArrayList<>();
        File file = new File(HIGH_SCORE_FILE);

        if (!file.exists()) {
            return records; // ถ้ายังไม่มีไฟล์ ให้คืนค่าลิสต์ว่าง
        }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    String line;
    // วนลูปอ่านข้อมูลในไฟล์ CSV ทีละบรรทัดจนกว่าจะหมดไฟล์
    while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue; // ถ้าเจอบรรทัดที่ไม่มีข้อมูลให้ข้ามไป
        String[] parts = line.split(","); // ตัดแบ่งข้อมูลในบรรทัดด้วยเครื่องหมายคอมมา (,)
        
        if (parts.length >= 4) {
            // กรณีเป็นข้อมูลรูปแบบใหม่ (มี 4 ค่า: ชื่อ, เวลา, คะแนน, โหมด)
            String user = parts[0];
            int time = Integer.parseInt(parts[1].trim());
            int score = Integer.parseInt(parts[2].trim());
            String mode = parts[3].trim();
            records.add(new ScoreRecord(user, time, score, mode));  
          } 
        }
    } catch (IOException e) {
    e.printStackTrace(); // ดักจับExceptionประเภทinput/outputกรณีอ่านไฟล์ไม่ได้
    }
        Collections.sort(records); // เรียงลำดับคะแนนและเวลาตามเงื่อนไขในคลาส ScoreRecord
        return records;
    }

    // Medthod
    // ดึงรายการคะแนนเฉพาะโหมดที่ผู้ใช้เลือก
    public List<ScoreRecord> getScoresByMode(String mode) {
        List<ScoreRecord> allScores = loadAllScores(); // โหลดประวัติคะแนนทั้งหมด
        List<ScoreRecord> filtered = new ArrayList<>(); // สร้างลิสต์เปล่าสำหรับเก็บข้อมูลที่กรองแล้ว
        for (ScoreRecord r : allScores) {
            if (r.getMode().equalsIgnoreCase(mode)) { // ถ้าโหมดของเรคอร์ดตรงกับโหมดที่ระบุ
                filtered.add(r); // นำไป ใส่ลิสต์
            }
        }
        return filtered;
    }

    // คำนวณหาอันดับ Rank ในโหมดที่ระบุ
    public int getPlayerRank(String username, String mode) {
        List<ScoreRecord> allScores = loadAllScores();
        
        // กรองเฉพาะคะแนนในโหมดนั้น ๆ
        List<ScoreRecord> modeScores = new ArrayList<>();
        for (ScoreRecord r : allScores) {
            if (r.getMode().equalsIgnoreCase(mode)) {
                modeScores.add(r);
            }
        }

        Collections.sort(modeScores); // จัดเรียงอันดับใหม่

        for (int i = 0; i < modeScores.size(); i++) {
            if (modeScores.get(i).getUsername().equalsIgnoreCase(username)) {
                return i + 1; // คืนค่าอันดับ เริ่มนับจาก 1
            }
        }
        return -1; // ถ้าไม่พบชื่อในโหมดที่เลือก
    }

    // ค้นหาสถิติที่ดีที่สุดของผู้เล่นคนนั้นในโหมดที่เลือก
    public ScoreRecord getPlayerBestRecord(String username, String mode) {
        List<ScoreRecord> allScores = loadAllScores();
        for (ScoreRecord r : allScores) {
            if (r.getUsername().trim().equalsIgnoreCase(username.trim()) && r.getMode().equalsIgnoreCase(mode)) {
                return r; // คืนค่าเรคอร์ดที่ดีที่สุด
            }
        }
        return null; // ถ้ายังไม่เคยมีสถิติในโหมดที่เลือก
    }
}
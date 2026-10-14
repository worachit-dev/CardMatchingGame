package data;

import model.ScoreRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager {
    private static final String HIGH_SCORE_FILE = "data/highscore.csv";

    // บันทึกผลการเล่น โดยแยกตามโหมด (username, timeSeconds, score, mode)
    public void saveScoreRecord(String username, int timeSeconds, int score, String mode) {
        File parentDir = new File("data");
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        List<ScoreRecord> records = loadAllScores();
        boolean found = false;

        for (int i = 0; i < records.size(); i++) {
            ScoreRecord r = records.get(i);
            // เช็คทั้งชื่อผู้ใช้และโหมดเดียวกัน
            if (r.getUsername().trim().equalsIgnoreCase(username.trim()) && r.getMode().equalsIgnoreCase(mode)) {
                found = true;
                
                // ถ้าเวลาใหม่น้อยกว่า หรือเวลาเท่ากันแต่คะแนนมากกว่า ให้อัปเดต
                if (timeSeconds < r.getTimeSeconds() || (timeSeconds == r.getTimeSeconds() && score > r.getScore())) {
                    records.set(i, new ScoreRecord(username, timeSeconds, score, mode));
                }
                break;
            }
        }

        if (!found) {
            records.add(new ScoreRecord(username, timeSeconds, score, mode));
        }

        // เขียนทับไฟล์ด้วยรูปแบบ 4 คอลัมน์: username,time,score,mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, false))) {
            for (ScoreRecord r : records) {
                writer.write(r.getUsername() + "," + r.getTimeSeconds() + "," + r.getScore() + "," + r.getMode());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // โหลดข้อมูลทั้งหมด (รองรับรูปแบบเก่า 3 ค่า และรูปแบบใหม่ 4 ค่าเพื่อความปลอดภัย)
    public List<ScoreRecord> loadAllScores() {
        List<ScoreRecord> records = new ArrayList<>();
        File file = new File(HIGH_SCORE_FILE);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String user = parts[0];
                    int time = Integer.parseInt(parts[1].trim());
                    int score = Integer.parseInt(parts[2].trim());
                    String mode = parts[3].trim();
                    records.add(new ScoreRecord(user, time, score, mode));
                } else if (parts.length == 3) {
                    // เผื่อไฟล์เก่าที่ยังไม่มีโหมด ให้ตีเป็น "4x4" ไปก่อน
                    String user = parts[0];
                    int time = Integer.parseInt(parts[1].trim());
                    int score = Integer.parseInt(parts[2].trim());
                    records.add(new ScoreRecord(user, time, score, "4x4"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(records);
        return records;
    }

    // ดึงรายการ ScoreRecord เฉพาะโหมดที่ระบุ
    public List<ScoreRecord> getScoresByMode(String mode) {
        List<ScoreRecord> allScores = loadAllScores();
        List<ScoreRecord> filtered = new ArrayList<>();
        for (ScoreRecord r : allScores) {
            if (r.getMode().equalsIgnoreCase(mode)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    // หาอันดับของผู้เล่นเฉพาะในโหมดนั้นๆ
    public int getPlayerRank(String username, String mode) {
        List<ScoreRecord> allScores = loadAllScores();
        
        // กรองเฉพาะคะแนนในโหมดที่เลือก
        List<ScoreRecord> modeScores = new ArrayList<>();
        for (ScoreRecord r : allScores) {
            if (r.getMode().equalsIgnoreCase(mode)) {
                modeScores.add(r);
            }
        }

        // เรียงลำดับใหม่ (เพราะ compareTo เรียงเวลาไวที่สุดไว้อยู่แล้ว)
        Collections.sort(modeScores);

        for (int i = 0; i < modeScores.size(); i++) {
            if (modeScores.get(i).getUsername().equalsIgnoreCase(username)) {
                return i + 1; // อันดับเริ่มต้นที่ 1
            }
        }
        return -1; // ถ้าไม่พบ
    }

    // ค้นหาสถิติที่ดีที่สุดของผู้เล่นคนนั้นเฉพาะในโหมดที่เลือก
    public ScoreRecord getPlayerBestRecord(String username, String mode) {
        List<ScoreRecord> allScores = loadAllScores();
        for (ScoreRecord r : allScores) {
            if (r.getUsername().trim().equalsIgnoreCase(username.trim()) && r.getMode().equalsIgnoreCase(mode)) {
                return r; // เจอข้อมูลที่ดีที่สุดแล้ว (เพราะไฟล์ถูกจัดเรียงตามเวลาที่ไวที่สุดไว้แล้ว)
            }
        }
        return null; // ถ้ายังไม่เคยมีสถิติในโหมดนี้
    }

}
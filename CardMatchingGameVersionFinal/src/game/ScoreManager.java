package game;

import data.FileManager;
import model.ScoreRecord;
import java.util.List;

public class ScoreManager {

    private int score; // คะแนนปัจจุบันของผู้เล่นในเกมรอบนี้
    private int highScore; // คะแนนสูงสุดที่เคยทำได้
    private final FileManager fileManager;

    // Constucter
    // เริ่มต้นระบบคะแนน และดึงสถิติคะแนนสูงสุดเดิมขึ้นมาเตรียมไว้
    public ScoreManager() {
        fileManager = new FileManager();
        score = 0;
        highScore = loadMaxScoreFromFile(); 
    }

    // Medthod
    //ช่วยอ่านไฟล์ข้อมูลเพื่อค้นหาคะแนนสูงสุดที่เคยบันทึกไว้ทั้งหมด
    private int loadMaxScoreFromFile() {
        List<ScoreRecord> records = fileManager.loadAllScores();
        if (records.isEmpty()) { 
            return 0; // ถ้ายังไม่มีประวัติการเล่น ให้ return 0
        }
        int max = 0;
        for (ScoreRecord r : records) { // วนลูปอ่านคะแนนทั้งหมดในไฟล์highscore.csv
            if (r.getScore() > max) {
                max = r.getScore();
            }
        }
        return max;
    }

    // ตอนเริ่มเกมใหม่ะจรีเซ็ตคะแนนเป็น 0
    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    // ตรวจสอบว่าคะแนนปัจจุบันแซงสถิติสูงสุดเดิมหรือยัง ถ้าแซงให้ อัปเดตค่า highScore
    public void checkHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    // เพิ่มคะแนนตามจำนวนแต้มที่ส่งเข้ามา เช่น ได้คะแนนพิเศษจากระบบคอมโบ
    public void addScore(int points) {
        score += points;   
    }
}
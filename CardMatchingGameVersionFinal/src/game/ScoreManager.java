package game;

import data.FileManager;
import model.ScoreRecord;
import java.util.List;

public class ScoreManager {

    private int score;
    private int highScore;
    private final FileManager fileManager;

    public ScoreManager() {
        fileManager = new FileManager();
        score = 0;
        highScore = loadMaxScoreFromFile(); // ดึงคะแนนสูงสุดจากข้อมูลเดิมในไฟล์ highscore.csv
    }

    // ฟังก์ชันช่วยหาคะแนนสูงสุดจากรายการทั้งหมดใน highscore.csv
    private int loadMaxScoreFromFile() {
        List<ScoreRecord> records = fileManager.loadAllScores();
        if (records.isEmpty()) {
            return 0;
        }
        int max = 0;
        for (ScoreRecord r : records) {
            if (r.getScore() > max) {
                max = r.getScore();
            }
        }
        return max;
    }

    public void resetScore() {
        score = 0;
    }

    public void correctMatch() {
        score += 1;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void checkHighScore() {
        if (score > highScore) {
            highScore = score;
            // หมายเหตุ: การบันทึกสถิติแบบเต็มรูปแบบ (user, time, score) 
            // เรียกบันทึกตอนจบเกมใน GamePanel
        }
    }

    public void addScore(int points) {
        score += points;   
    }
}
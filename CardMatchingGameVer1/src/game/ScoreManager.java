package game;

import data.FileManager;

public class ScoreManager {

    private int score;
    private int highScore;
    private final FileManager fileManager;

    public ScoreManager() {
        fileManager = new FileManager();
        score = 0;
        highScore = fileManager.loadHighScore(); // โหลดคะแนนสูงสุดจากไฟล์ตอนเริ่มโปรแกรม
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
            fileManager.saveHighScore(highScore); // มีสถิติใหม่ -> บันทึกลงไฟล์ทันที
        }
    }
}

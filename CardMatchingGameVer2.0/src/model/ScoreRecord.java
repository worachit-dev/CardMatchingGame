package model;

public class ScoreRecord implements Comparable<ScoreRecord> {
    private String username ;
    private int timeSeconds ; 
    private int score ;       
    private String mode ; // เพิ่มตัวแปรเก็บโหมด เช่น "4x4" หรือ "5x6"

    public ScoreRecord(String username, int timeSeconds, int score, String mode) {
        this.username = username;
        this.timeSeconds = timeSeconds;
        this.score = score;
        this.mode = mode;
    }

    public String getUsername() { return username; }
    public int getTimeSeconds() { return timeSeconds; }
    public int getScore() { return score; }
    public String getMode() { return mode; }

    @Override
    public int compareTo(ScoreRecord other) {
        // เปลี่ยนเกณฑ์: เรียงตามเวลา (Time) จากน้อยไปมาก (เวลาน้อย/ไวที่สุด มาก่อน)
        if (this.timeSeconds != other.timeSeconds) {
            return Integer.compare(this.timeSeconds, other.timeSeconds);
        }
        // ถ้าเวลาเท่ากัน ให้เทียบตามคะแนนจากมากไปน้อย
        return Integer.compare(other.score, this.score);
    }
}
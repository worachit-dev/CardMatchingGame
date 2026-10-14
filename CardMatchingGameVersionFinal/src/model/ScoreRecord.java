package model ; 

// ประกาศคลาส ScoreRecord พร้อม implements Interface Comparable เวลำดับข้อมูลได้
public class ScoreRecord implements Comparable<ScoreRecord> {
    private String username ; 
    private int timeSeconds ; 
    private int score ; 
    private String mode ;

    // ------ Constructor ------

    public ScoreRecord(String username, int timeSeconds, int score, String mode) {
        this.username = username; // กำหนดชื่อผู้ใช้
        this.timeSeconds = timeSeconds; // กำหนดเวลาที่ใช้
        this.score = score; // กำหนดคะแนน
        this.mode = mode; // กำหนดโหมดเกม
    }

    // ------ Medthod ------

    public String getUsername() { return username; }
    public int getTimeSeconds() { return timeSeconds; }
    public int getScore() { return score; }
    public String getMode() { return mode; }

    // เปรียบเทียบระหว่าง ScoreRecord เพื่อใช้ในการจัดเรียงลำดับ
    @Override
    public int compareTo(ScoreRecord other) {
        // เรียงลำดับตามเวลา(Time) จากน้อยไปมาก(เวลาน้อยกว่าหรือไวที่สุด จะถูกจัดให้อยู่ลำดับแรก)
        if (this.timeSeconds != other.timeSeconds) {
            return Integer.compare(this.timeSeconds, other.timeSeconds);
        }
        // ถ้าเวลาที่ใช้เท่ากัน ให้เทียบเกณฑ์รองโดยเรียงตามคะแนน (Score) จากมากไปน้อย (คะแนนเยอะกว่าอยู่ก่อน)
        return Integer.compare(other.score, this.score);
    }
}
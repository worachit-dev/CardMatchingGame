package game;

import model.*;

public class GameManager {

    // กำหนดสถานะผลลัพธ์ของการคลิกการ์ดแต่ละครั้ง
    public enum ClickResult {
        IGNORED,   
        FIRST_SELECTED,
        MATCH,
        MISMATCH  
    }

    private static final int TIME_PENALTY = 10; // เวลา + 10 วิ
    private final GameBoard gameBoard;
    private final CardSetManager cardSetManager;
    private final ScoreManager scoreManager;

    private int firstIndex = -1; // เก็บตำแหน่งอินเด็กซ์ของการ์ดใบแรกที่ถูกคลิก
    private int lastPoints;      // คะแนนล่าสุดจากการเล่น(ในเกม)
    private int combo;
    private int miss;
    private boolean locked;      // สถานะล็อกกระดานชั่วคราว

    private long startTime;        // เวลาเริ่มต้นเล่นเกม
    private int penaltySeconds;    // จำนวนวินาทีรวมที่โดนบวกเพิ่มจากการจับคู่ผิด
    private boolean running;       // สถานะการจับเวลา true = กำลังจับเวลาอยู่
    private int finalSeconds;      // เวลาสุดท้ายตอนที่เกมจบลง

    public GameManager() {
        gameBoard = new GameBoard();
        cardSetManager = new CardSetManager();
        scoreManager = new ScoreManager();
    }

    // Medthod 
    // เริ่มเกมใหม่ ตั้งค่ากระดาน คะแนน คอมโบ และเริ่มต้นจับเวลา
    public boolean startNewGame(CardSetType type, int rows, int cols) {
 
        CardSet set = cardSetManager.getSet(type);
        int pairCount = rows * cols / 2; // คำนวณจำนวนคู่การ์ดตามขนาดตาราง
 
        gameBoard.createCards(set, pairCount);
        scoreManager.resetScore();
        combo = 0;
        miss = 0;
        firstIndex = -1;
        locked = false;
 
        // เริ่มต้นระบบจับเวลา
        startTime = System.currentTimeMillis();
        penaltySeconds = 0;
        finalSeconds = 0;
        running = true;
 
        return true;
    }

    // จัดการตรรกะเมื่อผู้เล่นคลิกเลือกการ์ดที่ตำแหน่ง index
    public ClickResult selectCard(int index) {

        // กำลังรอเวลาคว่ำการ์ดที่ผิด จะไม่ยอมให้คลิก
        if (locked) {
            return ClickResult.IGNORED;
        }

        Card card = gameBoard.getCard(index);

        // จับคู่แล้วจะทำอะไรไม่ได้
        if (card.isMatched() || card.isRevealed()) {
            return ClickResult.IGNORED;
        }

        card.setRevealed(true); // เปิดหน้าการ์ด

        // ถ้าเป็นการคลิกเปิดการ์ดใบแรกของตา
        if (firstIndex == -1) {
            firstIndex = index;
            return ClickResult.FIRST_SELECTED;
        }

        // ถ้าเป็นการคลิกเปิดการ์ดใบที่สอง ให้ตรวจสอบว่ารูปตรงกันไหม
        boolean match = gameBoard.checkMatch(firstIndex, index);

        if (match) {
            // กรณีจับคู่สำเร็จ ล็อกการ์ดทั้งสองใบว่าจับคู่แล้ว
            gameBoard.getCard(firstIndex).setMatched();
            gameBoard.getCard(index).setMatched();
            
            // คำนวณคะแนนตามคอมโบ คูณคะแนนตามจำนวนคอมโบ
            lastPoints = 1 << Math.min(combo, 20); // <--- lastscore ตรงนี้ = คะแนนที่ + 1 ครั้ง ก่อน combo
            scoreManager.addScore(lastPoints);
            scoreManager.checkHighScore();
            combo++;
            
            return ClickResult.MATCH;
        } else {
            // กรณีจับคู่ผิด บวกเวลาโทษ 10 วินาที, รีเซ็ตคอมโบ, และล็อกกระดานชั่วคราว
            penaltySeconds += TIME_PENALTY;
            combo = 0;
            miss++;
            locked = true;
            return ClickResult.MISMATCH;
        }
    }

    // จัดการเมื่อเปิดการ์ดผิด สั่งคว่ำการ์ดทั้งสองใบกลับด้านหลัง
    public void resolveMismatch(int secondIndex) {
        gameBoard.getCard(firstIndex).setRevealed(false);
        gameBoard.getCard(secondIndex).setRevealed(false);
        firstIndex = -1;
        locked = false;
    }

    // ล้างค่าตำแหน่งการ์ดใบแรกที่เลือกไว้
    public void clearSelection() {
        firstIndex = -1;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getLastPoints() {
        return lastPoints;
    }

    // เมธอดตรวจสอบว่าผู้เล่นชนะเกมหรือยัง
    public boolean isGameWon() {
        for (Card c : gameBoard.getCards()) {
            if (!c.isMatched()) {
                return false;
            }
        }
        return true;
    }

    // ====================
    // TIMER (ระบบจัดการเวลา)
    // ====================

    // คำนวณเวลาที่ใช้ไปทั้งหมด(เวลาจริง + เวลาที่ถูกบวกโทษจากการเปิดผิด)
    public int getElapsedSeconds() {
        if (!running) {
            return finalSeconds;
        }
        int realSeconds = (int) ((System.currentTimeMillis() - startTime) / 1000);
        return realSeconds + penaltySeconds;
    }

    // หยุดจับเวลา เรียกใช้งานเมื่อเกมจบลง
    public void stopTimer() {
        if (running) {
            finalSeconds = getElapsedSeconds();
            running = false;
        }
    }

    public int getPenaltySeconds() {
        return penaltySeconds;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public int getCombo() {
        return combo;
    }
    public int getMiss() {
        return miss;
    }
}
package game;

import model.*;


public class GameManager {

    public enum ClickResult {
        IGNORED,
        FIRST_SELECTED,
        MATCH,
        MISMATCH
    }

    private static final int TIME_PENALTY = 10; // วินาทีที่บวกเพิ่มเมื่อจับคู่ผิด

    private final GameBoard gameBoard;
    private final CardSetManager cardSetManager;
    private final ScoreManager scoreManager;

    private int firstIndex = -1;
    private int lastPoints;   // คะแนนที่ได้จากการจับคู่ครั้งล่าสุด
    private int combo;
    private boolean locked;

    private long startTime;        // เวลาที่เริ่มเกม (มิลลิวินาที)
    private int penaltySeconds;    // เวลาที่ถูกบวกเพิ่มจากการจับคู่ผิด
    private boolean running;       // true = กำลังจับเวลาอยู่
    private int finalSeconds;      // เวลาสุดท้ายตอนเกมจบ

    public GameManager() {
        gameBoard = new GameBoard();
        cardSetManager = new CardSetManager();
        scoreManager = new ScoreManager();
    }

    /** เริ่มเกมใหม่ และเริ่มจับเวลา */
    public boolean startNewGame(CardSetType type, int rows, int cols) {
 
        CardSet set = cardSetManager.getSet(type);
        int pairCount = rows * cols / 2;
 
        if (set == null || set.getImages().size() < pairCount) {
            return false;
        }
 
        gameBoard.createCards(set, pairCount);
        scoreManager.resetScore();
        combo = 0;
        firstIndex = -1;
        locked = false;
 
        startTime = System.currentTimeMillis();
        penaltySeconds = 0;
        finalSeconds = 0;
        running = true;
 
        return true;
    }

    public ClickResult selectCard(int index) {

        if (locked) {
            return ClickResult.IGNORED;
        }

        Card card = gameBoard.getCard(index);

        if (card.isMatched() || card.isRevealed()) {
            return ClickResult.IGNORED;
        }

        card.setRevealed(true);

        if (firstIndex == -1) {
            firstIndex = index;
            return ClickResult.FIRST_SELECTED;
        }

        boolean match = gameBoard.checkMatch(firstIndex, index);

        if (match) {
            gameBoard.getCard(firstIndex).setMatched();
            gameBoard.getCard(index).setMatched();
            lastPoints = 1 << Math.min(combo, 20); // 2 ยกกำลัง combo (จำกัดไม่ให้ล้น)
            scoreManager.addScore(lastPoints);
            scoreManager.checkHighScore();
            combo++;                                // บวกหลังคำนวณคะแนน
            return ClickResult.MATCH;
        } else {
            penaltySeconds += TIME_PENALTY; // จับคู่ผิด บวกเวลาเพิ่ม 10 วินาที
            combo = 0;
            locked = true;
            return ClickResult.MISMATCH;
        }
    }

    public void resolveMismatch(int secondIndex) {
        gameBoard.getCard(firstIndex).setRevealed(false);
        gameBoard.getCard(secondIndex).setRevealed(false);
        firstIndex = -1;
        locked = false;
    }

    public void clearSelection() {
        firstIndex = -1;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getLastPoints() {
        return lastPoints;
    }

    public boolean isGameWon() {
        for (Card c : gameBoard.getCards()) {
            if (!c.isMatched()) {
                return false;
            }
        }
        return true;
    }

    // =========================
    // TIMER
    // =========================

    /** เวลาที่ใช้ไปทั้งหมด (วินาที) = เวลาจริง + เวลาที่ถูกบวกเพิ่ม */
    public int getElapsedSeconds() {
        if (!running) {
            return finalSeconds;
        }
        int realSeconds = (int) ((System.currentTimeMillis() - startTime) / 1000);
        return realSeconds + penaltySeconds;
    }

    /** หยุดจับเวลา (เรียกตอนเกมจบ) */
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
}
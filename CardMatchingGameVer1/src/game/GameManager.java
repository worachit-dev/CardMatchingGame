package game;  // 1. ต้องบอกว่าไฟล์นี้อยู่แพ็กเกจไหน

import model.Card;   // 2. ต้องนำเข้าคลาส Card จากแพ็กเกจ model มาใช้งาน
import model.CardSet;

/**
 * ตัวควบคุมหลักของเกม (Controller)
 * รวม GameBoard + CardSetManager + ScoreManager เข้าด้วยกัน
 * และเก็บ "สถานะการเลือกการ์ด" (ใบแรก/ใบสอง, จับคู่ได้ไหม, ล็อกอินพุตไหม)
 * เพื่อให้ GamePanel (UI) เรียกใช้งานได้ง่าย ๆ ผ่านเมธอด selectCard(index)
 */
public class GameManager {

    // ผลลัพธ์ของการคลิกการ์ด 1 ครั้ง ที่ UI ต้องนำไปแสดงผลต่อ
    public enum ClickResult {
        IGNORED,        // คลิกไม่มีผล (กำลังล็อกอยู่ / การ์ดใบนี้เปิดแล้ว หรือจับคู่ไปแล้ว)
        FIRST_SELECTED, // เพิ่งเปิดการ์ดใบแรก ยังไม่มีอะไรให้ตรวจ
        MATCH,          // เปิดใบที่สองแล้วตรงกัน
        MISMATCH        // เปิดใบที่สองแล้วไม่ตรงกัน
    }

    private static final int MAX_LIVES = 5;

    private final GameBoard gameBoard;
    private final CardSetManager cardSetManager;
    private final ScoreManager scoreManager;

    private int firstIndex = -1;
    private int lives;
    private int combo;
    private boolean locked; // true = กำลังโชว์คู่ที่ไม่ตรงกันอยู่ ห้ามคลิกใบอื่น

    public GameManager() {
        gameBoard = new GameBoard();
        cardSetManager = new CardSetManager();
        scoreManager = new ScoreManager();
        lives = MAX_LIVES;
    }

    /** เริ่มเกมใหม่: สุ่มชุดการ์ด, สับไพ่, รีเซ็ตคะแนน/ชีวิต */
    public void startNewGame() {
        CardSet set = cardSetManager.selectRandomSet();
        gameBoard.createCards(set);
        scoreManager.resetScore();
        lives = MAX_LIVES;
        combo = 0;
        firstIndex = -1;
        locked = false;
    }

    /**
     * เรียกทุกครั้งที่ผู้เล่นคลิกการ์ดช่องที่ index
     * คืนค่า ClickResult ให้ UI ไปตัดสินใจว่าจะแสดงผลยังไง
     */
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
            scoreManager.correctMatch();
            scoreManager.checkHighScore();
            combo++;
            return ClickResult.MATCH;
        } else {
            lives--;
            combo = 0;
            locked = true; // รอ UI หน่วงเวลาแล้วเรียก resolveMismatch(...)
            return ClickResult.MISMATCH;
        }
    }

    /** UI เรียกหลังจากหน่วงเวลาโชว์การ์ดที่ไม่ตรงกันเสร็จแล้ว เพื่อพลิกกลับทั้งคู่ */
    public void resolveMismatch(int secondIndex) {
        gameBoard.getCard(firstIndex).setRevealed(false);
        gameBoard.getCard(secondIndex).setRevealed(false);
        firstIndex = -1;
        locked = false;
    }

    /** UI เรียกหลังจับคู่ MATCH สำเร็จ เพื่อล้างค่าการ์ดใบแรกที่จำไว้ */
    public void clearSelection() {
        firstIndex = -1;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public boolean isGameWon() {
        for (Card c : gameBoard.getCards()) {
            if (!c.isMatched()) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public int getLives() {
        return lives;
    }

    public int getCombo() {
        return combo;
    }
}

package game;  // 1. ต้องบอกว่าไฟล์นี้อยู่แพ็กเกจไหน
import model.Card; // 2. ต้องนำเข้าคลาส Card จากแพ็กเกจ model มาใช้งาน
import java.util.*;

public class GameManager {
    private ArrayList<Card> cards;

    private Card firstCard;
    private Card secondCard;

    public GameManager(){
        cards = new ArrayList<>();

        // creatCard();
        // shuffleCard();
    }
}

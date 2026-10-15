package game;
import model.CardSet; 
import model.CardSetType; 
import java.util.ArrayList;
import java.util.List;

public class CardSetManager {

    private final List<CardSet> cardSets; // ลิสต์เก็บรายการชุดการ์ดทั้งหมดที่มีในระบบ
    private CardSet currentSet; // ชุดการ์ดที่กำลังถูกใช้งานอยู่ในเกมปัจจุบัน

    // Constructer
    // เตรียมข้อมูลชุดการ์ดเริ่มต้น
    public CardSetManager() {
 
        cardSets = new ArrayList<>();
 
        cardSets.add(createAnimalsSet()); 
        cardSets.add(createFruitsSet()); 
    }

    // Medthod
    // ค้นหาและดึงชุดการ์ดตามประเภทที่เลือก เช่น FRUITS หรือ ANIMALS
    public CardSet getSet(CardSetType type) {

        for (CardSet set : cardSets) {
            if (set.getType() == type) {
                currentSet = set; // บันทึกเก็บไว้เป็นชุดปัจจุบัน
                return set;
            }
        }

        return null; 
    }

    // ดึงชุดการ์ดปัจจุบันที่กำลังเล่นอยู่
    public CardSet getCurrentSet() {
        return currentSet;
    }

    private CardSet createAnimalsSet() {

        List<String> images = List.of(
           "bear","cat","chick","cow","dog","frog","hamster","koala","lion","octopus","panda"
           ,"rabbit","seal","sheep","whale"
        );

        return new CardSet(
            CardSetType.ANIMALS,
            images
        );
    }

    private CardSet createFruitsSet() {

        List<String> images = List.of(
            "apple","avocado","banana","blueberry","carrot","cherry","corn","grape","lemon"
            ,"mango","orange","pineapple","strawberry","tomato","watermelon"
        );

        return new CardSet(
            CardSetType.FRUITS,
            images
        );
    }
}
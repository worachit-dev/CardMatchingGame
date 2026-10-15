package game; 
import model.*; 
import java.util.*;

public class GameBoard {

    private final List<Card> cards; // ลิสต์เก็บการ์ดทั้งหมดบนกระดาน

    // Constructer
    // สร้างกระดานเกมและเตรียมพื้นที่เก็บการ์ด
    public GameBoard() {
        cards = new ArrayList<>();
    }

    // Medthod
    // สร้างการ์ดลงบนกระดาน โดยเลือกรูปภาพตามจำนวนคู่ที่กำหนด
    public void createCards(CardSet cardSet, int pairCount) {
 
        cards.clear(); // ล้างก่อนสร้างใหม่
 
        List<String> images = new ArrayList<>(cardSet.getImages()); // ดึงรายชื่อรูปภาพทั้งหมดจากชุดการ์ด
        Collections.shuffle(images); // สลับการ์ดแบบสุ่ม
 
        // วนลูปสร้างการ์ดเป็นคู่ ๆ
        for (int i = 0; i < pairCount; i++) {
 
            String image = images.get(i);
 
            cards.add(new Card(image)); // เพิ่มการ์ดใบที่หนึ่ง
            cards.add(new Card(image)); // เพิ่มการ์ดใบที่สอง
        }
 
        shuffleCards();
    }

    // สลับตำแหน่งการ์ดทั้งหมดบนกระดาน
    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    // ดึงออบเจ็กต์การ์ดตามตำแหน่ง Index ที่ระบุ
    public Card getCard(int index) {
        return cards.get(index);
    }

    // ดึงจำนวนการ์ดทั้งหมดบนกระดาน
    public int getCardCount() {
        return cards.size();
    }

    // ดึงลิสต์การ์ดทั้งหมดในรูปแบบที่ห้ามแก้ไขข้อมูลภายในโดยตรง
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    // ตรวจสอบว่าการ์ดสองใบที่เปิดขึ้นมามีรูปภาพตรงกันหรือไม่
    public boolean checkMatch(int firstIndex, int secondIndex) {

        Card firstCard = getCard(firstIndex);
        Card secondCard = getCard(secondIndex);

        // เปรียบเทียบรหัสรูปภาพ ImageId ของการ์ดทั้งสองใบ
        boolean match =
            firstCard.getImageId()
                .equals(secondCard.getImageId());
        return match;
    }
}
package model ; 

public class Card {
    private String imageId;  // ตัวแปรเก็บรหัสหรือชื่อรูปภาพของการ์ดแต่ละใบ
    private boolean revealed; // ตัวแปรสถานะบอกว่าการ์ดใบนี้กำลังเปิดหน้าการ์ดอยู่หรือไม่ (true = หงาย, false = คว่ำ)
    private boolean matched;  // ตัวแปรสถานะบอกว่าการ์ดใบนี้จับคู่สำเร็จไปแล้วหรือยัง (true = จับคู่แล้ว, false = ยังไม่ได้จับคู่)

    // Constructor สำหรับสร้างออบเจ็กต์ Card โดยรับค่า imageId
    public Card(String imageId){
        this.imageId = imageId ; // กำหนดimageIdให้กับตัวแปรของคลาส
        this.revealed = false;   // กำหนดค่าเริ่มต้นให้การ์ดคว่ำอยู่
        this.matched = false;    // กำหนดค่าเริ่มต้นให้ยังไม่ถูกจับคู่
    }
    //------ Medthod ------
    public String getImageId(){
        return imageId;
    }

    public boolean isRevealed(){
        return revealed;
    }

    public boolean isMatched(){
        return matched;
    }

    // เปลี่ยนสถานะการเปิด/ปิดของการ์ด
    public void setRevealed(boolean revealed){
        this.revealed = revealed;
    }

    // เปลี่ยนสถานะการจับคู่ของการ์ด
    public void setMatched(boolean matched){
        this.matched = matched;
    }

    // คว่ำการ์ดกลับลงมา (ซ่อนรูปภาพ)
    public void hide() {
        // คว่ำการ์ดเมื่อจับคู่ผิดเท่านั้น
        if (!matched) {
            revealed = false; // เปลี่ยนเป็นคว่ำการ์ด
        }
    }

    // กำหนดว่าการ์ดใบนี้จับคู่สำเร็จแล้ว
    public void setMatched() {
        matched = true;  // เปลี่ยนสถานะเป็นจับคู่สำเร็จแล้ว
        revealed = true; // ล็อคให้เปิดหน้าการ์ดค้างไว้
    }
}
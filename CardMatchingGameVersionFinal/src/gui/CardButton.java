package gui; 
import javax.swing.*; 
import java.awt.*; 
public class CardButton extends JButton { 

    private final int cardIndex; // เก็บตำแหน่งของการ์ดแต่ละใบแบบค่าคงที่
    private ImageIcon frontIcon;
    private ImageIcon backIcon; 

    // ------ Constructor ------ 
    // สร้างปุ่มการ์ดโดยรับตำแหน่งอินเด็กซ์เข้ามา
    public CardButton(int cardIndex) {

        this.cardIndex = cardIndex; // กำหนดค่าอินเด็กซ์ให้กับตัวแปรของคลาส

        setFocusPainted(false); 
        setBorderPainted(false); 
        setContentAreaFilled(false); 

        setPreferredSize(new Dimension(130, 130));

        loadBackImage();
        hideCard();
    }
    // ------ Medthod ------
    // โหลดและปรับขนาดภาพด้านหลังของการ์ด
    private void loadBackImage() {

        ImageIcon original = new ImageIcon("resources/picture/back.png");

        Image image = original.getImage().getScaledInstance(
                100,
                140,
                Image.SCALE_SMOOTH
        );

        backIcon = new ImageIcon(image); // แปลงภาพที่ย่อขนาดแล้วเก็บไว้
    }

    // ดึงค่าตำแหน่งอินเด็กซ์ของการ์ดใบนี้
    public int getCardIndex() {
        return cardIndex;
    }

    // เปิดหน้าการ์ด โดยรับรูปภาพเข้ามาแสดงผล
    public void showCard(String imagePath) {

        ImageIcon original = new ImageIcon(imagePath); // โหลดไฟล์รูปภาพหน้าการ์ดที่ส่งเข้ามา
        Image image = original.getImage().getScaledInstance(
                100,
                140,
                Image.SCALE_SMOOTH
        );

        frontIcon = new ImageIcon(image);

        setIcon(frontIcon);
        setText("");
    }

    // คว่ำการ์ดกลับมาแสดงด้านหลัง
    public void hideCard() {

        setIcon(backIcon); 
        setText("");
    }
}
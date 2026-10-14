package gui; 
import java.awt.Font; 
import java.io.File; 

public class FontManager {

    private static Font font;

    // บล็อกสแตติก จะทำงานทันทีเมื่อคลาสนี้ถูกโหลดครั้งแรก
    static {
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                new File("resources/fonts/Taste Bread.otf")
            );

        } catch (Exception e) {
            e.printStackTrace();

            // กำหนดฟอนต์สำรอง
            font = new Font(
                "Arial",
                Font.PLAIN,
                20
            );
        }
    }

    // เมธอดสแตติกสำหรับดึงฟอนต์ไปใช้งาน โดยระบุขนาด(size)เป็นพารามิเตอร์แบบทศนิยม(float)
    public static Font getFont(float size) {
        // คืนค่าฟอนต์ที่ปรับขนาดตามที่ต้องการด้วยเมธอด deriveFont
        return font.deriveFont(size);
    }
}
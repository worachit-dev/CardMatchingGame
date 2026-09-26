package gui;
import java.awt.Font;
import java.io.File;

public class FontManager {

    private static Font font;

    static {
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                new File("resources/fonts/Taste Bread.otf")
            );
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 20);
        }
    }

    public static Font getFont(float size) {
        return font.deriveFont(size);
    }
}

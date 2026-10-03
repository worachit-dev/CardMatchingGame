import gui.MainFrame;// ต้องนำเข้าคลาส MainFrame จากแพ็กเกจ gui มาใช้งาน
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

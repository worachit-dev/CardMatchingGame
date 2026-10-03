package gui;

import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {

    private final int cardIndex;

    private ImageIcon frontIcon;
    private ImageIcon backIcon;

    public CardButton(int cardIndex) {

        this.cardIndex = cardIndex;

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        setPreferredSize(new Dimension(130, 130));

        loadBackImage();
        hideCard();

    }

    private void loadBackImage() {

        // แก้จาก "resources/back.png" -> "resources/card_back.png"
        // ให้ตรงกับโครงสร้างไฟล์ resources/card_back.png ตามที่ตกลงกันไว้ใน README
        ImageIcon original = new ImageIcon("resources/picture/back.png");

        Image image = original.getImage().getScaledInstance(
                100,
                140,
                Image.SCALE_SMOOTH
        );

        backIcon = new ImageIcon(image);
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void showCard(String imagePath) {

        ImageIcon original = new ImageIcon(imagePath);

        Image image = original.getImage().getScaledInstance(
                100,
                140,
                Image.SCALE_SMOOTH
        );

        frontIcon = new ImageIcon(image);

        setIcon(frontIcon);
        setText("");
    }

    public void hideCard() {

        setIcon(backIcon);
        setText("");
    }
}
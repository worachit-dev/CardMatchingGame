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

        ImageIcon original = new ImageIcon("resources/back.png");

        Image image = original.getImage().getScaledInstance(
                120,
                120,
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
                120,
                120,
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
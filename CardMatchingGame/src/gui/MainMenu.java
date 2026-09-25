import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("CardMatcheing Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("MEMORY CARD GAME");
        title.setFont(new Font("Arial", Font.BOLD, 32));

        JButton playButton = new JButton("PLAY");
        JButton howToButton = new JButton("HOW TO PLAY");
        JButton exitButton = new JButton("EXIT");

        playButton.setPreferredSize(new Dimension(200, 50));
        howToButton.setPreferredSize(new Dimension(200, 50));
        exitButton.setPreferredSize(new Dimension(200, 50));

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // PLAY
        gbc.gridy = 1;
        panel.add(playButton, gbc);

        // HOW TO PLAY
        gbc.gridy = 2;
        panel.add(howToButton, gbc);

        // EXIT
        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        // กด PLAY
        playButton.addActionListener(e -> {

            dispose();

            new GameFrame();

        });

        // กด EXIT
        exitButton.addActionListener(e -> {

            System.exit(0);

        });

        // HOW TO PLAY
        howToButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                this,
                "Match all the identical cards.\n\n"
                + "Press two cards to reveal\n"
                + "If the cards match, they remain face-up\n"
                ,
                "How To Play",
                JOptionPane.INFORMATION_MESSAGE
            );

        });

        add(panel);

        setVisible(true);
    }
}
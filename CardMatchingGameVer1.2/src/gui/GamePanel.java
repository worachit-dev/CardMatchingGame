package gui;

import game.GameManager;
import game.GameManager.ClickResult;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final MainFrame mainFrame;
    private final GameManager gameManager;

    private final JLabel timeLabel;   // แทน heartsLabel
    private final JLabel scoreLabel;
    private final JLabel comboLabel;

    private final CardButton[] cardButtons;

    private Timer gameTimer = null;

    private static final String CARD_IMAGE_FOLDER = "resources/picture/";
    private static final int MISMATCH_DELAY_MS = 900;

    public GamePanel(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        this.gameManager = new GameManager();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 235, 215));

        // =========================
        // TOP BAR
        // =========================

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 10, 30)
        );

        JLabel titleLabel = new JLabel("CARD MATCHING");
        titleLabel.setFont(
                FontManager.getFont(30)
        );

        timeLabel = new JLabel("TIME: 00:00");
        timeLabel.setFont(
                FontManager.getFont(24)
        );
        timeLabel.setForeground(new Color(180, 50, 50));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(timeLabel, BorderLayout.EAST);

        // =========================
        // SCORE BAR
        // =========================

        JPanel scorePanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 50, 5)
        );

        scorePanel.setOpaque(false);

        scoreLabel = new JLabel();
        comboLabel = new JLabel();

        scoreLabel.setFont(
                FontManager.getFont(18)
        );

        comboLabel.setFont(
                FontManager.getFont(18)
        );

        scorePanel.add(scoreLabel);
        scorePanel.add(comboLabel);

        // =========================
        // CARD BOARD
        // =========================

        JPanel boardPanel = new JPanel(
                new GridLayout(4, 4, 12, 12)
        );

        boardPanel.setOpaque(false);

        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        50,
                        15,
                        50
                )
        );

        cardButtons = new CardButton[16];

        for (int i = 0; i < 16; i++) {

            final int index = i;

            CardButton cardButton = new CardButton(index);

            cardButton.addActionListener(e -> handleCardClick(index));

            cardButtons[index] = cardButton;

            boardPanel.add(cardButton);
        }

        // =========================
        // BOTTOM BAR
        // =========================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        bottomPanel.setOpaque(false);

        JButton backButton =
        new JButton("BACK TO MENU");

        backButton.setFont(
            FontManager.getFont(18)
        );

        backButton.setForeground(Color.WHITE);

        backButton.setBackground(
                new Color(80, 55, 40)
        );

        backButton.setFocusPainted(false);

        backButton.setPreferredSize(
                new Dimension(260, 55)
        );

        backButton.addActionListener(e -> {
            gameTimer.stop();           // ออกจากเกม ให้หยุดจับเวลา
            gameManager.stopTimer();
            mainFrame.showMenu();
        });

        bottomPanel.add(backButton);

        // =========================
        // ADD COMPONENTS
        // =========================

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setOpaque(false);

        centerPanel.add(scorePanel, BorderLayout.NORTH);
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Timer ของหน้าจอ: ทุก 200 ms ให้อัปเดตข้อความเวลา
        gameTimer = new Timer(200, e -> updateTimeLabel());

        updateLabels();
    }

    /** เรียกทุกครั้งที่เข้าหน้าเกม: เริ่มเกมใหม่ + เริ่มจับเวลา */
    public void startNewGame() {

        gameManager.startNewGame();

        for (CardButton button : cardButtons) {
            button.hideCard();
            button.setEnabled(true);
        }

        updateLabels();
        gameTimer.start();   // เริ่มจับเวลา
    }

    private void handleCardClick(int index) {

        ClickResult result = gameManager.selectCard(index);

        switch (result) {

            case IGNORED:
                break;

            case FIRST_SELECTED:
                revealCard(index);
                break;

            case MATCH: {
                int firstIndex = gameManager.getFirstIndex();

                revealCard(index);
                revealCard(firstIndex);

                cardButtons[index].setEnabled(false);
                cardButtons[firstIndex].setEnabled(false);

                gameManager.clearSelection();
                updateLabels();
                checkGameEnd();
                break;
            }

            case MISMATCH: {
                final int firstIndex = gameManager.getFirstIndex();

                revealCard(index);
                updateLabels(); // เวลาถูกบวก 10 วินาทีทันที

                Timer timer = new Timer(MISMATCH_DELAY_MS, e -> {
                    gameManager.resolveMismatch(index);
                    cardButtons[firstIndex].hideCard();
                    cardButtons[index].hideCard();
                });
                timer.setRepeats(false);
                timer.start();
                break;
            }
        }
    }

    private void revealCard(int index) {
        String imageId = gameManager.getGameBoard().getCard(index).getImageId();
        cardButtons[index].showCard(CARD_IMAGE_FOLDER + imageId + ".png");
    }

    /** แปลงวินาทีเป็นข้อความ mm:ss เช่น 75 -> 01:15 */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateTimeLabel() {
        timeLabel.setText("TIME: " + formatTime(gameManager.getElapsedSeconds()));
    }

    private void updateLabels() {
        scoreLabel.setText("SCORE: " + gameManager.getScoreManager().getScore());
        comboLabel.setText("COMBO: " + gameManager.getCombo());
        updateTimeLabel();
    }

    private void checkGameEnd() {
        if (gameManager.isGameWon()) {
            gameTimer.stop();          // หยุดอัปเดตหน้าจอ
            gameManager.stopTimer();   // หยุดจับเวลาจริง
            mainFrame.showResult(
                    gameManager.getScoreManager().getScore(),
                    gameManager.getElapsedSeconds()
            );
        }
    }
}
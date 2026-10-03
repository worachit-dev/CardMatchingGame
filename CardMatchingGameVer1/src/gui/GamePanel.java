package gui;

import game.GameManager;
import game.GameManager.ClickResult;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final MainFrame mainFrame;
    private final GameManager gameManager;

    private final JLabel heartsLabel;
    private final JLabel scoreLabel;
    private final JLabel comboLabel;

    private final CardButton[] cardButtons;

    // path ของรูปการ์ด: resources/cards/<imageId>.png (ตรงกับ imageId ใน CardSetManager เช่น "apple")
    private static final String CARD_IMAGE_FOLDER = "resources/picture/";
    private static final int MISMATCH_DELAY_MS = 900; // เวลาที่โชว์การ์ดผิดคู่ก่อนพลิกกลับ

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

        heartsLabel = new JLabel();
        heartsLabel.setFont(
                new Font("Segoe UI Symbol", Font.PLAIN, 24)
        );
        heartsLabel.setForeground(new Color(180, 50, 50));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(heartsLabel, BorderLayout.EAST);

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

            // เดิม CardButton ไม่มี action listener เลย -> คลิกแล้วไม่มีอะไรเกิดขึ้น
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

        updateLabels();
    }

    /**
     * MainFrame เรียกเมธอดนี้ทุกครั้งที่จะเข้าหน้าเกม (กด START GAME หรือ PLAY AGAIN)
     * เพื่อสุ่มชุดการ์ดใหม่ สับไพ่ใหม่ และรีเซ็ต UI ทั้งหมด
     */
    public void startNewGame() {

        gameManager.startNewGame();

        for (CardButton button : cardButtons) {
            button.hideCard();
            button.setEnabled(true);
        }

        updateLabels();
    }

    private void handleCardClick(int index) {

        ClickResult result = gameManager.selectCard(index);

        switch (result) {

            case IGNORED:
                // คลิกตอนกำลังล็อก หรือคลิกการ์ดที่เปิด/จับคู่ไปแล้ว -> ไม่ต้องทำอะไร
                break;

            case FIRST_SELECTED:
                revealCard(index);
                break;

            case MATCH: {
                int firstIndex = gameManager.getFirstIndex();

                revealCard(index);
                revealCard(firstIndex);

                // ปิดปุ่มการ์ดที่จับคู่แล้ว กันคลิกซ้ำ (ไม่จำเป็นเพราะ GameManager กันไว้แล้ว แต่ช่วยเรื่อง UX)
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
                updateLabels(); // อัปเดตหัวใจ (เสียชีวิตไป 1 ดวง) ทันที

                // หน่วงเวลาไว้ให้ผู้เล่นเห็นการ์ดทั้งคู่ก่อน ค่อยพลิกกลับ
                Timer timer = new Timer(MISMATCH_DELAY_MS, e -> {
                    gameManager.resolveMismatch(index);
                    cardButtons[firstIndex].hideCard();
                    cardButtons[index].hideCard();
                    checkGameEnd();
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

    private void updateLabels() {
        scoreLabel.setText("SCORE: " + gameManager.getScoreManager().getScore());
        comboLabel.setText("COMBO: " + gameManager.getCombo());

        int lives = Math.max(gameManager.getLives(), 0);
        heartsLabel.setText("♥ ".repeat(lives).trim());
    }

    private void checkGameEnd() {
        if (gameManager.isGameWon()) {
            mainFrame.showResult(gameManager.getScoreManager().getScore(), true);
        } else if (gameManager.isGameOver()) {
            mainFrame.showResult(gameManager.getScoreManager().getScore(), false);
        }
    }
}
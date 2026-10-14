package gui;

import data.FileManager;
import model.ScoreRecord;
import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {

    public ResultPanel(MainFrame mainFrame, int score, int timeSeconds, String mode) {

        setLayout(new GridBagLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setOpaque(false);

        // =========================
        // RESULT TITLE
        // =========================
        JLabel resultLabel = new JLabel("YOU WIN!");
        resultLabel.setFont(FontManager.getFont(42));
        resultLabel.setForeground(new Color(153, 0, 0));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // SCORE & TIME (รอบล่าสุด)
        // =========================
        JLabel scoreLabel = new JLabel("SCORE: " + score);
        scoreLabel.setFont(FontManager.getFont(22));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        JLabel timeLabel = new JLabel(String.format("TIME: %02d:%02d (%s)", minutes, seconds, mode));
        timeLabel.setFont(FontManager.getFont(22));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // RANK DISPLAY
        // =========================
        FileManager fileManager = new FileManager();
        String currentUser = mainFrame.getCurrentUsername();
        int rank = fileManager.getPlayerRank(currentUser, mode);

        JLabel rankLabel;
        if (rank > 0) {
            rankLabel = new JLabel("YOUR RANK (" + mode + "): #" + rank);
        } else {
            rankLabel = new JLabel("YOUR RANK: -");
        }
        rankLabel.setFont(FontManager.getFont(26));
        rankLabel.setForeground(new Color(180, 50, 50));
        rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // HIGHSCORE DISPLAY (เวลาและคะแนนที่ดีที่สุดของ Account นี้)
        // =========================
        ScoreRecord bestRecord = fileManager.getPlayerBestRecord(currentUser, mode);
        JLabel bestScoreLabel;
        
        if (bestRecord != null) {
            int bestMin = bestRecord.getTimeSeconds() / 60;
            int bestSec = bestRecord.getTimeSeconds() % 60;
            bestScoreLabel = new JLabel(String.format("YOUR BEST: %02d:%02d (Score: %d)", bestMin, bestSec, bestRecord.getScore()));
        } else {
            bestScoreLabel = new JLabel("YOUR BEST: -");
        }
        
        bestScoreLabel.setFont(FontManager.getFont(20));
        bestScoreLabel.setForeground(new Color(180, 50, 50));
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // BUTTONS
        // =========================
        JButton playAgainButton = new JButton("PLAY AGAIN");
        JButton menuButton = new JButton("MAIN MENU");

        playAgainButton.setFont(FontManager.getFont(18));
        menuButton.setFont(FontManager.getFont(18));

        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playAgainButton.addActionListener(e -> mainFrame.showGame());
        menuButton.addActionListener(e -> mainFrame.showMenu());

        // =========================
        // ADD COMPONENTS
        // =========================
        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(20));
        resultPanel.add(scoreLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(timeLabel);
        resultPanel.add(Box.createVerticalStrut(15));
        resultPanel.add(rankLabel); 
        resultPanel.add(Box.createVerticalStrut(8));
        resultPanel.add(bestScoreLabel); // เพิ่มบรรทัดแสดงสถิติที่ดีที่สุดตรงนี้
        resultPanel.add(Box.createVerticalStrut(30));
        resultPanel.add(playAgainButton);
        resultPanel.add(Box.createVerticalStrut(15));
        resultPanel.add(menuButton);

        add(resultPanel);
    }
}
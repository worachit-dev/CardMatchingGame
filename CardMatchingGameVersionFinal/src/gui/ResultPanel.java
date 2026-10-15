package gui;

import data.FileManager;
import model.ScoreRecord;
import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    
    // Constructer
    // สร้างหน้าจอสรุปผลลัพธ์หลังเล่นเกมจบ (รับคะแนน, เวลา, และโหมดเกมเข้ามา)
    public ResultPanel(MainFrame mainFrame, int score, int timeSeconds, String mode) {

        setLayout(new GridBagLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS)); // จัดเรียงคอมโพเนนต์ภายในแบบแนวตั้ง
        resultPanel.setOpaque(false);

        // RESULT TITLE หัวข้อแสดงผลว่าชนะเกมแล้ว
        JLabel resultLabel = new JLabel("YOU WIN!");
        resultLabel.setFont(FontManager.getFont(42));
        resultLabel.setForeground(new Color(153, 0, 0));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SCORE & TIME แสดงคะแนนและเวลาของรอบล่าสุดที่เพิ่งเล่นจบ
        JLabel scoreLabel = new JLabel("SCORE: " + score);
        scoreLabel.setFont(FontManager.getFont(22));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        JLabel timeLabel = new JLabel(String.format("TIME: %02d:%02d (%s)", minutes, seconds, mode));
        timeLabel.setFont(FontManager.getFont(22));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // RANK DISPLAY คำนวณและแสดงอันดับของผู้เล่นในโหมดนี้
        FileManager fileManager = new FileManager();
        String currentUser = mainFrame.getCurrentUsername();
        int rank = fileManager.getPlayerRank(currentUser, mode); // ดึงอันดับจากระบบจัดการไฟล์

        JLabel rankLabel;
        if (rank > 0) {
            rankLabel = new JLabel("YOUR RANK (" + mode + "): #" + rank); // ถ้ามีอันดับ ให้แสดงอันดับที่ทำได้
        } else {
            rankLabel = new JLabel("YOUR RANK: -");
        }
        rankLabel.setFont(FontManager.getFont(26));
        rankLabel.setForeground(new Color(180, 50, 50));
        rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // HIGHSCORE DISPLAY (ดึงสถิติเวลาและคะแนนที่ดีที่สุดของไอดีนี้มาแสดง)
        ScoreRecord bestRecord = fileManager.getPlayerBestRecord(currentUser, mode);
        JLabel bestScoreLabel;
        
        if (bestRecord != null) {
            int bestMin = bestRecord.getTimeSeconds() / 60;
            int bestSec = bestRecord.getTimeSeconds() % 60;
            bestScoreLabel = new JLabel(String.format("YOUR BEST: %02d:%02d (Score: %d)", bestMin, bestSec, bestRecord.getScore()));
        } else {
            bestScoreLabel = new JLabel("YOUR BEST: -"); // ถ้ายังไม่เคยมีสถิติ
        }
        
        bestScoreLabel.setFont(FontManager.getFont(20));
        bestScoreLabel.setForeground(new Color(180, 50, 50));
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =======================================
        // BUTTONS (ปุ่มสำหรับเล่นซ้ำ หรือกลับหน้าเมนูหลัก)
        // =======================================
        JButton playAgainButton = new JButton("PLAY AGAIN");
        JButton menuButton = new JButton("MAIN MENU");

        playAgainButton.setFont(FontManager.getFont(18));
        menuButton.setFont(FontManager.getFont(18));

        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playAgainButton.addActionListener(e -> mainFrame.showGame());
        menuButton.addActionListener(e -> mainFrame.showMenu());

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(20));
        resultPanel.add(scoreLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(timeLabel);
        resultPanel.add(Box.createVerticalStrut(15));
        resultPanel.add(rankLabel); 
        resultPanel.add(Box.createVerticalStrut(8));
        resultPanel.add(bestScoreLabel); 
        resultPanel.add(Box.createVerticalStrut(30));
        resultPanel.add(playAgainButton);
        resultPanel.add(Box.createVerticalStrut(15));
        resultPanel.add(menuButton);

        add(resultPanel);
    }
}
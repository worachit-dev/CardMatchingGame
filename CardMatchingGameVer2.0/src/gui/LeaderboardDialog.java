package gui;

import data.FileManager;
import model.ScoreRecord;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardDialog extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> modeComboBox;
    private final FileManager fileManager;

    public LeaderboardDialog(JFrame parent) {
        super(parent, "Leaderboard", true);
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        fileManager = new FileManager();

        // =========================
        // TOP PANEL (เลือกโหมด 4x4 / 5x6)
        // =========================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        JLabel label = new JLabel("SELECT MODE: ");
        label.setFont(FontManager.getFont(16));

        String[] modes = {"4x4", "5x6"};
        modeComboBox = new JComboBox<>(modes);
        modeComboBox.setFont(FontManager.getFont(16));
        modeComboBox.addActionListener(e -> loadTableData((String) modeComboBox.getSelectedItem()));

        topPanel.add(label);
        topPanel.add(modeComboBox);
        add(topPanel, BorderLayout.NORTH);

        // =========================
        // CENTER PANEL (ตารางแสดงอันดับ)
        // =========================
        String[] columnNames = {"Rank", "Username", "Time", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ป้องกันไม่ให้แก้ข้อมูลในตาราง
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // โหลดข้อมูลเริ่มต้น (ค่า 4x4)
        loadTableData("4x4");

        // =========================
        // BOTTOM PANEL (ปุ่มปิด)
        // =========================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("CLOSE");
        closeButton.setFont(FontManager.getFont(16));
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadTableData(String mode) {
        tableModel.setRowCount(0); // ล้างข้อมูลเก่า
        List<ScoreRecord> records = fileManager.getScoresByMode(mode);

        for (int i = 0; i < records.size(); i++) {
            ScoreRecord r = records.get(i);
            int minutes = r.getTimeSeconds() / 60;
            int seconds = r.getTimeSeconds() % 60;
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);

            Object[] rowData = {
                "#" + (i + 1),
                r.getUsername(),
                timeFormatted,
                r.getScore()
            };
            tableModel.addRow(rowData);
        }
    }
}
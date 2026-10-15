package gui; 

import data.FileManager; 
import model.ScoreRecord; 
import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 
import java.util.List; 

public class LeaderboardDialog extends JDialog { // สืบทอดมาจาก JDialog(หน้าต่างป๊อปอัป)

    private JTable table; // ตารางสำหรับแสดงผลอันดับคะแนน
    private DefaultTableModel tableModel; // แถวและคอลัมน์ของตาราง
    private JComboBox<String> modeComboBox; // คอมโบบ็อกซ์เอาไว้เลือกโหมดที่ต้องการจะดู 4x4, 5x6
    private final FileManager fileManager; // ตัวจัดการไฟล์สำหรับดึงข้อมูลคะแนน

    // Constructer
    public LeaderboardDialog(JFrame parent) {
        super(parent, "Leaderboard", true);
        setSize(450, 500); // กว้าง สูง
        setLocationRelativeTo(parent); // จัดตำแหน่งหน้าให้อยู่กึ่งกลางหน้าต่างหลัก
        setLayout(new BorderLayout(10, 10));

        fileManager = new FileManager();

        // ==========
        // TOP PANEL 
        // ==========
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        topPanel.setOpaque(false); 

        JLabel label = new JLabel("SELECT MODE: ");
        label.setFont(FontManager.getFont(16)); 

        String[] modes = {"4x4", "5x6"};
        modeComboBox = new JComboBox<>(modes);
        modeComboBox.setFont(FontManager.getFont(16));

        // เมื่อเลือกโหมดจะเรียกฟังก์ชันโหลดข้อมูลตามโหมดที่เลือกมา
        modeComboBox.addActionListener(e -> loadTableData((String) modeComboBox.getSelectedItem()));

        topPanel.add(label); 
        topPanel.add(modeComboBox);
        add(topPanel, BorderLayout.NORTH); 

        // ================================
        // CENTER PANEL(ตารางแสดงอันดับคะแนน)
        // ================================
        String[] columnNames = {"Rank", "Username", "Time", "Score"}; // กำหนดชื่อคอลัมน์
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ป้องกันไม่ให้แก้ไขข้อมูลในตารางโดยตรง
            }
        };

        table = new JTable(tableModel); 
        table.setFont(new Font("Arial", Font.PLAIN, 14)); 
        table.setRowHeight(25); 
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table); // ใส่ตารางลงใน JScrollPane เพื่อให้สามารถเลื่อนดูข้อมูลได้หากมีจำนวนมาก
        add(scrollPane, BorderLayout.CENTER); 

        // select default mode
        loadTableData("4x4");

        // ========================================
        // BOTTOM PANEL (แถบด้านล่างสำหรับปุ่มปิดหน้าต่าง)
        // ========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("CLOSE"); 
        closeButton.setFont(FontManager.getFont(16)); 
        closeButton.addActionListener(e -> dispose()); 
        bottomPanel.add(closeButton); 
        add(bottomPanel, BorderLayout.SOUTH); 
    }

    // โหลดและกรองข้อมูลคะแนนตามโหมดที่เลือก แล้วนำมาแสดงในตาราง
    private void loadTableData(String mode) {
        tableModel.setRowCount(0); 
        List<ScoreRecord> records = fileManager.getScoresByMode(mode); // ดึงคะแนนเฉพาะโหมดที่เลือกจาก FileManager

        // วนลูปแปลงข้อมูลคะแนนแต่ละรอบให้อยู่ในรูปแบบที่พร้อมแสดงผลในตาราง
        for (int i = 0; i < records.size(); i++) {
            ScoreRecord r = records.get(i);
            int minutes = r.getTimeSeconds() / 60; 
            int seconds = r.getTimeSeconds() % 60; 
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);

            // สร้างข้อมูลแถวใหม่ ประกอบด้วย อันดับ (#1, #2, ...), ชื่อผู้ใช้, เวลาที่ใช้, และคะแนน
            Object[] rowData = {
                "#" + (i + 1),
                r.getUsername(),
                timeFormatted,
                r.getScore()
            };
            tableModel.addRow(rowData); // เพิ่มแถวข้อมูลลงในตาราง
        }
    }
}
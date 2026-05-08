package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * ui.SmartStudentGUI.java
 * Swing-based GUI for SmartStudent Management System.
 * Visual desktop application with login and full CRUD.
 */
public class SmartStudentGUI extends JFrame {

    // Input fields
    private JTextField nameField, rollField, deptField;
    private JTextField emailField, phoneField, marksField;
    private JTextField searchField, idField;
    private JLabel statusLabel;

    // Table
    private JTable table;
    private DefaultTableModel tableModel;

    // DB connection
    private Connection con;

    // Colors
    final Color PRIMARY  = new Color(25, 60, 110);
    final Color SUCCESS  = new Color(40, 167, 69);
    final Color DANGER   = new Color(220, 53, 69);
    final Color WARNING  = new Color(255, 152, 0);
    final Color ACCENT   = new Color(46, 117, 182);
    final Color LIGHT_BG = new Color(245, 247, 255);
    final Color WHITE    = Color.WHITE;

    public SmartStudentGUI() {

        // Connect to DB
        try {
            con = DBConnection.getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "❌ DB Connection Failed!\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Window setup
        setTitle("🎓 SmartStudent Management System");
        setSize(1000, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setLocationRelativeTo(null);

        // ── TITLE BAR ──
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY);
        titlePanel.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel titleLabel = new JLabel("🎓 SmartStudent Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(WHITE);

        JLabel subLabel = new JLabel("Java + JDBC + MySQL | Internship Project");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(new Color(180, 200, 255));

        JPanel titleText = new JPanel(new GridLayout(2, 1));
        titleText.setBackground(PRIMARY);
        titleText.add(titleLabel);
        titleText.add(subLabel);
        titlePanel.add(titleText, BorderLayout.WEST);

        // ── LEFT FORM PANEL ──
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(WHITE);
        formPanel.setPreferredSize(new Dimension(240, 0));
        formPanel.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 4),
                new CompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 215, 255)),
                        new EmptyBorder(12, 12, 12, 12)
                )
        ));

        // Form title
        JLabel formTitle = new JLabel("➕ Add New model.Student");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formTitle.setForeground(PRIMARY);
        formTitle.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(formTitle);
        formPanel.add(Box.createVerticalStrut(10));

        // Input fields
        formPanel.add(makeLabel("👤 Name *"));
        nameField = makeField("Full name");
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("🔢 Roll No *"));
        rollField = makeField("e.g. ENTC001");
        formPanel.add(rollField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("🏢 Department *"));
        deptField = makeField("e.g. ENTC, CS");
        formPanel.add(deptField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("📧 Email *"));
        emailField = makeField("example@email.com");
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("📱 Phone *"));
        phoneField = makeField("10-digit number");
        formPanel.add(phoneField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("📊 Marks (0-100) *"));
        marksField = makeField("e.g. 85.5");
        formPanel.add(marksField);
        formPanel.add(Box.createVerticalStrut(14));

        // Add button
        JButton addBtn = makeButton("➕  Add model.Student", SUCCESS);
        formPanel.add(addBtn);
        formPanel.add(Box.createVerticalStrut(6));

        JButton clearBtn = makeButton("🔄  Clear Fields", new Color(108, 117, 125));
        formPanel.add(clearBtn);
        formPanel.add(Box.createVerticalStrut(16));

        // Divider
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(sep);
        formPanel.add(Box.createVerticalStrut(14));

        // Update/Delete section
        JLabel editTitle = new JLabel("✏️ Update / Delete");
        editTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editTitle.setForeground(PRIMARY);
        editTitle.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(editTitle);
        formPanel.add(Box.createVerticalStrut(8));

        formPanel.add(makeLabel("🔢 Roll No"));
        idField = makeField("Enter Roll No");
        formPanel.add(idField);
        formPanel.add(Box.createVerticalStrut(6));

        formPanel.add(makeLabel("📊 New Marks"));
        JTextField newMarksField = makeField("New marks value");
        formPanel.add(newMarksField);
        formPanel.add(Box.createVerticalStrut(12));

        JButton updateBtn = makeButton("✏️  Update Marks", WARNING);
        updateBtn.setForeground(Color.BLACK);
        formPanel.add(updateBtn);
        formPanel.add(Box.createVerticalStrut(6));

        JButton deleteBtn = makeButton("🗑️  Delete model.Student", DANGER);
        formPanel.add(deleteBtn);

        // ── RIGHT TABLE PANEL ──
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBackground(LIGHT_BG);
        tablePanel.setBorder(new EmptyBorder(8, 4, 8, 8));

        // Search + action bar
        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBackground(LIGHT_BG);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        searchField.setToolTipText("Search by name, roll no or department...");

        JButton searchBtn  = makeButton("🔍 Search",  ACCENT);
        JButton refreshBtn = makeButton("🔃 All",     new Color(23, 162, 184));
        JButton statsBtn   = makeButton("📊 Stats",   PRIMARY);
        JButton exportBtn  = makeButton("💾 Export",  new Color(102, 16, 242));

        searchBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.setPreferredSize(new Dimension(70, 32));
        statsBtn.setPreferredSize(new Dimension(80, 32));
        exportBtn.setPreferredSize(new Dimension(90, 32));

        JLabel searchLbl = new JLabel("🔍 Search: ");
        searchLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        btnBar.setBackground(LIGHT_BG);
        btnBar.add(searchBtn);
        btnBar.add(refreshBtn);
        btnBar.add(statsBtn);
        btnBar.add(exportBtn);

        topBar.add(searchLbl,  BorderLayout.WEST);
        topBar.add(searchField, BorderLayout.CENTER);
        topBar.add(btnBar,     BorderLayout.EAST);

        // Table
        String[] cols = {"ID", "Name", "Roll No", "Dept", "Email", "Phone", "Marks", "Grade"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(26);
        table.setGridColor(new Color(220, 230, 255));
        table.setSelectionBackground(new Color(190, 210, 255));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 30));

        // Column widths
        int[] widths = {40, 140, 90, 70, 180, 110, 60, 55};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Click row → fill roll no
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(tableModel.getValueAt(row, 2).toString());
                newMarksField.setText(tableModel.getValueAt(row, 6).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 255)));

        // Status bar
        statusLabel = new JLabel("  ✅ Connected to SmartStudent database");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(80, 80, 80));
        statusLabel.setBorder(new EmptyBorder(4, 4, 4, 4));

        tablePanel.add(topBar,     BorderLayout.NORTH);
        tablePanel.add(scroll,     BorderLayout.CENTER);
        tablePanel.add(statusLabel, BorderLayout.SOUTH);

        // ── ADD TO FRAME ──
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel,  BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        // ── BUTTON ACTIONS ──

        // ADD STUDENT
        addBtn.addActionListener(e -> {
            String name  = nameField.getText().trim();
            String roll  = rollField.getText().trim();
            String dept  = deptField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String marksText = marksField.getText().trim();

            // Validation
            if (name.isEmpty() || roll.isEmpty() || dept.isEmpty() ||
                    email.isEmpty() || phone.isEmpty() || marksText.isEmpty()) {
                showError("Please fill ALL fields!");
                return;
            }
            if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                showError("Invalid email format!");
                return;
            }
            if (!phone.matches("\\d{10}")) {
                showError("Phone must be exactly 10 digits!");
                return;
            }
            double marks;
            try {
                marks = Double.parseDouble(marksText);
                if (marks < 0 || marks > 100) { showError("Marks must be 0-100!"); return; }
            } catch (NumberFormatException ex) {
                showError("Marks must be a number!"); return;
            }

            // Insert to DB
            String sql = "INSERT INTO students (name,roll_no,department,email,phone,marks) VALUES(?,?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, roll.toUpperCase());
                ps.setString(3, dept.toUpperCase());
                ps.setString(4, email);
                ps.setString(5, phone);
                ps.setDouble(6, marks);
                ps.executeUpdate();
                showSuccess("model.Student added: " + name);
                clearForm();
                loadTable();
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        });

        // CLEAR
        clearBtn.addActionListener(e -> clearForm());

        // UPDATE MARKS
        updateBtn.addActionListener(e -> {
            String roll = idField.getText().trim();
            String newMarksText = newMarksField.getText().trim();
            if (roll.isEmpty() || newMarksText.isEmpty()) {
                showError("Enter Roll No and New Marks!"); return;
            }
            try {
                double newMarks = Double.parseDouble(newMarksText);
                if (newMarks < 0 || newMarks > 100) { showError("Marks must be 0-100!"); return; }
                String sql = "UPDATE students SET marks=? WHERE roll_no=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDouble(1, newMarks);
                ps.setString(2, roll.toUpperCase());
                int rows = ps.executeUpdate();
                if (rows > 0) { showSuccess("Marks updated for: " + roll); loadTable(); }
                else            showError("Roll No not found: " + roll);
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            String roll = idField.getText().trim();
            if (roll.isEmpty()) { showError("Enter Roll No to delete!"); return; }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete student with Roll No: " + roll + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String sql = "DELETE FROM students WHERE roll_no=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, roll.toUpperCase());
                    int rows = ps.executeUpdate();
                    if (rows > 0) { showSuccess("model.Student deleted: " + roll); loadTable(); }
                    else            showError("Roll No not found: " + roll);
                } catch (Exception ex) {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        // SEARCH
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) { loadTable(); return; }
            String sql = "SELECT * FROM students WHERE name LIKE ? OR roll_no LIKE ? OR department LIKE ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                String k = "%" + keyword + "%";
                ps.setString(1, k); ps.setString(2, k); ps.setString(3, k);
                ResultSet rs = ps.executeQuery();
                tableModel.setRowCount(0);
                int count = 0;
                while (rs.next()) { count++; addRowToTable(rs); }
                setStatus("🔍 Found " + count + " result(s) for: " + keyword);
            } catch (Exception ex) { showError("Search error: " + ex.getMessage()); }
        });

        // REFRESH
        refreshBtn.addActionListener(e -> { searchField.setText(""); loadTable(); });

        // STATISTICS
        statsBtn.addActionListener(e -> {
            String sql = "SELECT COUNT(*) AS total, MAX(marks) AS highest, MIN(marks) AS lowest, AVG(marks) AS avg FROM students";
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String msg = "📊 SmartStudent Statistics\n" +
                            "──────────────────────────\n" +
                            "Total Students  : " + rs.getInt("total") + "\n" +
                            String.format("Highest Marks   : %.1f%n", rs.getDouble("highest")) +
                            String.format("Lowest Marks    : %.1f%n", rs.getDouble("lowest")) +
                            String.format("Average Marks   : %.2f%n", rs.getDouble("avg"));
                    JOptionPane.showMessageDialog(this, msg, "Statistics", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) { showError("Stats error: " + ex.getMessage()); }
        });

        // EXPORT CSV
        exportBtn.addActionListener(e -> {
            try {
                java.util.List<Object[]> rows2 = new java.util.ArrayList<>();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                java.io.PrintWriter pw = new java.io.PrintWriter("students_export.csv");
                pw.println("ID,Name,Roll No,Department,Email,Phone,Marks,Grade");
                int count = 0;
                while (rs.next()) {
                    count++;
                    double m = rs.getDouble("marks");
                    String grade = m>=90?"A+":m>=80?"A":m>=70?"B":m>=60?"C":m>=50?"D":"F";
                    pw.printf("%d,%s,%s,%s,%s,%s,%.1f,%s%n",
                            rs.getInt("id"), rs.getString("name"),
                            rs.getString("roll_no"), rs.getString("department"),
                            rs.getString("email"), rs.getString("phone"), m, grade);
                }
                pw.close();
                showSuccess("Exported " + count + " students to students_export.csv!");
            } catch (Exception ex) { showError("Export error: " + ex.getMessage()); }
        });

        loadTable();
        setVisible(true);
    }

    // ── HELPER METHODS ──

    private void loadTable() {
        tableModel.setRowCount(0);
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY id")) {
            while (rs.next()) addRowToTable(rs);
            setStatus("  ✅ " + tableModel.getRowCount() + " students loaded");
        } catch (Exception e) { showError("Load error: " + e.getMessage()); }
    }

    private void addRowToTable(ResultSet rs) throws SQLException {
        double m = rs.getDouble("marks");
        String grade = m>=90?"A+":m>=80?"A":m>=70?"B":m>=60?"C":m>=50?"D":"F";
        tableModel.addRow(new Object[]{
                rs.getInt("id"), rs.getString("name"),
                rs.getString("roll_no"), rs.getString("department"),
                rs.getString("email"), rs.getString("phone"),
                rs.getDouble("marks"), grade
        });
    }

    private void clearForm() {
        nameField.setText(""); rollField.setText("");
        deptField.setText(""); emailField.setText("");
        phoneField.setText(""); marksField.setText("");
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(50, 50, 80));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JTextField makeField(String tip) {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        f.setAlignmentX(LEFT_ALIGNMENT);
        f.setToolTipText(tip);
        f.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 195, 230), 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        return f;
    }

    private JButton makeButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        b.setAlignmentX(LEFT_ALIGNMENT);
        return b;
    }

    private void showSuccess(String msg) {
        setStatus("  ✅ " + msg);
        JOptionPane.showMessageDialog(this, "✅ " + msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        setStatus("  ❌ " + msg);
        JOptionPane.showMessageDialog(this, "❌ " + msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void setStatus(String msg) { statusLabel.setText(msg); }

    // ── LOGIN SCREEN ──
    public static boolean showLoginDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        panel.add(new JLabel("Username:")); panel.add(userField);
        panel.add(new JLabel("Password:")); panel.add(passField);

        int attempts = 0;
        while (attempts < 3) {
            int result = JOptionPane.showConfirmDialog(null, panel,
                    "🔐 SmartStudent Admin Login",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) return false;

            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            // Check from database
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,
                            "✅ Welcome, " + username + "!",
                            "Login Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "❌ DB Error: " + e.getMessage());
                return false;
            }
            attempts++;
            int left = 3 - attempts;
            if (left > 0)
                JOptionPane.showMessageDialog(null,
                        "❌ Wrong credentials! " + left + " attempt(s) left.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "🔒 Too many attempts! Access blocked.");
        return false;
    }

    // ── MAIN ──
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}

            // Show login first
            if (showLoginDialog()) {
                new SmartStudentGUI();
            } else {
                System.out.println("Access denied!");
                System.exit(0);
            }
        });
    }
}
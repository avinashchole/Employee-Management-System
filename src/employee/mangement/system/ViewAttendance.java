package employee.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewAttendance extends JFrame implements ActionListener {

    JComboBox<String> empIdDropdown;
    JButton viewButton, backButton;
    JTable attendanceTable;
    DefaultTableModel tableModel;

    ViewAttendance() {
        setLayout(null);

        // Heading label
        JLabel heading = new JLabel("View Employee Attendance");
        heading.setBounds(100, 20, 300, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        add(heading);

        // Employee ID dropdown
        JLabel empIdLabel = new JLabel("Select Employee ID:");
        empIdLabel.setBounds(50, 80, 150, 30);
        add(empIdLabel);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setBounds(200, 80, 150, 30);
        add(empIdDropdown);

        populateEmployeeIds(); // Populate dropdown with employee IDs

        // View button
        viewButton = new JButton("View Attendance");
        viewButton.setBounds(100, 120, 150, 30);
        viewButton.addActionListener(this);
        add(viewButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(270, 120, 150, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Attendance Table
        String[] columns = {"Employee ID", "Date", "Attendance Status"};
        tableModel = new DefaultTableModel(columns, 0);
        attendanceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBounds(50, 170, 400, 200);
        add(scrollPane);

        setSize(500, 450);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Populate Employee ID dropdown
    private void populateEmployeeIds() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql:///employeemanagementsystems", "root", "Pranav@!03");
            String query = "SELECT empId FROM employee";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                empIdDropdown.addItem(rs.getString("empId"));
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching Employee IDs: " + e.getMessage());
        }
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewButton) {
            String empId = (String) empIdDropdown.getSelectedItem();
            viewAttendance(empId); // Display attendance for the selected employee
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home(); // Navigate back to Home screen
        }
    }

    // View attendance for the selected employee
    private void viewAttendance(String empId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql:///employeemanagementsystems", "root", "Pranav@!03");
            String query = "SELECT * FROM attendance WHERE empId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();

            // Clear existing rows in the table
            tableModel.setRowCount(0);

            // Add rows to the table based on the attendance records
            while (rs.next()) {
                String empIdResult = rs.getString("empId");
                String date = rs.getString("date");
                String status = rs.getString("attendance_status");
                tableModel.addRow(new Object[]{empIdResult, date, status});
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching attendance: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ViewAttendance();
    }
}

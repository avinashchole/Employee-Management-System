package employee.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MarkAttendance extends JFrame implements ActionListener {
    JComboBox<String> empIdDropdown;
    JTextField dateField;
    JComboBox<String> attendanceStatusDropdown;
    JButton markAttendanceButton, backButton;

    MarkAttendance() {
        setLayout(null);

        // Title Label
        JLabel heading = new JLabel("Mark Employee Attendance");
        heading.setBounds(100, 20, 300, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        add(heading);

        // Employee ID Selection
        JLabel empIdLabel = new JLabel("Select Employee ID:");
        empIdLabel.setBounds(50, 80, 150, 30);
        add(empIdLabel);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setBounds(200, 80, 150, 30);
        add(empIdDropdown);

        // Populate Employee IDs
        populateEmployeeIds();

        // Date Input (Auto-filled with today's date)
        JLabel dateLabel = new JLabel("Enter Date (YYYY-MM-DD):");
        dateLabel.setBounds(50, 120, 200, 30);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(200, 120, 150, 30);
        dateField.setEditable(false); // Make the date field non-editable
        dateField.setText(getCurrentDate()); // Set today's date
        add(dateField);

        // Attendance Status Dropdown
        JLabel statusLabel = new JLabel("Attendance Status:");
        statusLabel.setBounds(50, 160, 200, 30);
        add(statusLabel);

        String[] statusOptions = { "Present", "Absent" };
        attendanceStatusDropdown = new JComboBox<>(statusOptions);
        attendanceStatusDropdown.setBounds(200, 160, 150, 30);
        add(attendanceStatusDropdown);

        // Mark Attendance Button
        markAttendanceButton = new JButton("Mark Attendance");
        markAttendanceButton.setBounds(100, 220, 150, 30);
        markAttendanceButton.addActionListener(this);
        add(markAttendanceButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(250, 220, 120, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Frame settings
        setSize(500, 400);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to get today's date in the format YYYY-MM-DD
    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the format
        return currentDate.format(formatter); // Format the date
    }

    // Populate the Employee IDs from the database
    private void populateEmployeeIds() {
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/employeemanagementsystems", "root", "Pranav@!03");
            String query = "SELECT empId FROM employee";
            PreparedStatement stmt = c.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                empIdDropdown.addItem(rs.getString("empId"));
            }

            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching Employee IDs: " + e.getMessage());
        }
    }

    // Action listener for buttons
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == markAttendanceButton) {
            String empId = (String) empIdDropdown.getSelectedItem();
            String date = dateField.getText().trim();
            String attendanceStatus = (String) attendanceStatusDropdown.getSelectedItem();

            // No need to validate the date as it's auto-filled with today's date
            try {
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost/employeemanagementsystems", "root", "Pranav@!03");

                // Insert the attendance record into the database
                String query = "INSERT INTO attendance (empId, date, attendance_status) VALUES (?, ?, ?)";
                PreparedStatement stmt = c.prepareStatement(query);
                stmt.setString(1, empId);
                stmt.setString(2, date);
                stmt.setString(3, attendanceStatus);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error marking attendance: " + e.getMessage());
            }

        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home(); // Go back to Home screen
        }
    }

    public static void main(String[] args) {
        new MarkAttendance(); // Open the Mark Attendance window
    }
}

package employee.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageLeave extends JFrame implements ActionListener {

    JComboBox<String> empIdDropdown;
    JTextField leaveDaysField, leaveReasonField;
    JButton applyLeave, back;

    ManageLeave() {
        setLayout(null);

        JLabel heading = new JLabel("Manage Employee Leave");
        heading.setBounds(100, 20, 300, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        add(heading);

        JLabel empIdLabel = new JLabel("Select Employee ID:");
        empIdLabel.setBounds(50, 80, 150, 30);
        add(empIdLabel);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setBounds(200, 80, 150, 30);
        add(empIdDropdown);

        populateEmployeeIds(); // Method to fetch employee IDs from the database

        JLabel leaveDaysLabel = new JLabel("Leave Days:");
        leaveDaysLabel.setBounds(50, 120, 100, 30);
        add(leaveDaysLabel);

        leaveDaysField = new JTextField();
        leaveDaysField.setBounds(200, 120, 150, 30);
        add(leaveDaysField);

        JLabel leaveReasonLabel = new JLabel("Reason for Leave:");
        leaveReasonLabel.setBounds(50, 160, 150, 30);
        add(leaveReasonLabel);

        leaveReasonField = new JTextField();
        leaveReasonField.setBounds(200, 160, 150, 30);
        add(leaveReasonField);

        applyLeave = new JButton("Apply Leave");
        applyLeave.setBounds(100, 220, 120, 30);
        applyLeave.addActionListener(this);
        add(applyLeave);

        back = new JButton("Back");
        back.setBounds(250, 220, 120, 30);
        back.addActionListener(this);
        add(back);

        setSize(500, 400);
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == applyLeave) {
            String empId = (String) empIdDropdown.getSelectedItem();
            int leaveDays;
            String leaveReason = leaveReasonField.getText();

            try {
                leaveDays = Integer.parseInt(leaveDaysField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for leave days.");
                return;
            }

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql:///employeemanagementsystems", "root", "Pranav@!03");
                String query = "INSERT INTO leave_records (empId, leave_days, leave_reason) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, empId);
                stmt.setInt(2, leaveDays);
                stmt.setString(3, leaveReason);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Leave applied successfully!");
                }

                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error applying leave: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ManageLeave();
    }
}

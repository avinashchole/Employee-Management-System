package employee.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateSalarySlip extends JFrame implements ActionListener {

    JComboBox<String> empIdDropdown;
    JTextField monthField, yearField;
    JButton generateSlip, back;

    GenerateSalarySlip() {
        setLayout(null);

        JLabel heading = new JLabel("Generate Salary Slip");
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

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, 120, 100, 30);
        add(monthLabel);

        monthField = new JTextField();
        monthField.setBounds(200, 120, 150, 30);
        add(monthField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(50, 160, 100, 30);
        add(yearLabel);

        yearField = new JTextField();
        yearField.setBounds(200, 160, 150, 30);
        add(yearField);

        generateSlip = new JButton("Generate Slip");
        generateSlip.setBounds(100, 220, 120, 30);
        generateSlip.addActionListener(this);
        add(generateSlip);

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
        if (ae.getSource() == generateSlip) {
            String empId = (String) empIdDropdown.getSelectedItem();
            String month = monthField.getText();
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid year.");
                return;
            }

            // Fetch salary details and generate slip
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql:///employeemanagementsystems", "root", "Pranav@!03");
                String query = "SELECT basic_salary, deductions, allowances, net_salary FROM salary_slip1 WHERE empId = ? AND month = ? AND year = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, empId);
                stmt.setString(2, month);
                stmt.setInt(3, year);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String basicSalary = rs.getString("basic_salary");
                    String deductions = rs.getString("deductions");
                    String allowances = rs.getString("allowances");
                    String netSalary = rs.getString("net_salary");

                    JOptionPane.showMessageDialog(this, 
                        "Salary Slip for " + empId + ":\n" +
                        "Month: " + month + " " + year + "\n" +
                        "Basic Salary: " + basicSalary + "\n" +
                        "Deductions: " + deductions + "\n" +
                        "Allowances: " + allowances + "\n" +
                        "Net Salary: " + netSalary
                    );
                } else {
                    JOptionPane.showMessageDialog(this, "No salary record found for the selected employee and period.");
                }

                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching salary slip: " + e.getMessage());
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new GenerateSalarySlip();
    }
}

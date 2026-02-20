package employee.mangement.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfsname, tfaddress, tfphone, tfemail, tfsalary, tfdesignation, tfeducation;
    JLabel lblempId, lblname, lbldob;
    JButton add, back;
    String empId;

    UpdateEmployee(String empId) {
        this.empId = empId;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Update Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 150, 150, 30);
        add(lblname);

        JLabel labelsname = new JLabel("Surname");
        labelsname.setBounds(400, 150, 150, 30);
        labelsname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsname);

        tfsname = new JTextField();
        tfsname.setBounds(600, 150, 150, 30);
        add(tfsname);

        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        lbldob = new JLabel();
        lbldob.setBounds(200, 200, 150, 30);
        add(lbldob);

        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);

        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        JLabel labeleducation = new JLabel("Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        tfeducation = new JTextField();
        tfeducation.setBounds(600, 300, 150, 30);
        add(tfeducation);

        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        JLabel labelempId = new JLabel("Employee ID");
        labelempId.setBounds(50, 400, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);

        lblempId = new JLabel();
        lblempId.setBounds(200, 400, 150, 30);
        lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblempId);

        // Load employee data
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM employee WHERE empId = ?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                lblname.setText(rs.getString("name"));
                tfsname.setText(rs.getString("sname"));
                lbldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfsalary.setText(rs.getString("salary"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfeducation.setText(rs.getString("education"));
                tfdesignation.setText(rs.getString("designation"));
                lblempId.setText(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add = new JButton("Update Details");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String sname = tfsname.getText().trim();
            String salary = tfsalary.getText().trim();
            String address = tfaddress.getText().trim();
            String phone = tfphone.getText().trim();
            String email = tfemail.getText().trim();
            String education = tfeducation.getText().trim();
            String designation = tfdesignation.getText().trim();

            try {
                // Validations
                if (!Pattern.matches("[a-zA-Z]+", sname)) {
                    JOptionPane.showMessageDialog(this, "Surname must contain only alphabets.");
                    tfsname.grabFocus();
                    return;
                }

                if (!Pattern.matches("[a-zA-Z]+", designation)) {
                    JOptionPane.showMessageDialog(this, "Designation must contain only alphabets.");
                    tfdesignation.grabFocus();
                    return;
                }

                if (!Pattern.matches("\\d{10}", phone)) {
                    JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits.");
                    tfphone.grabFocus();
                    return;
                }

                if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$", email)) {
                    JOptionPane.showMessageDialog(this, "Invalid email format.");
                    tfemail.grabFocus();
                    return;
                }

                if (!Pattern.matches("[a-zA-Z ]+", education)) {
                    JOptionPane.showMessageDialog(this, "Education must contain only alphabets.");
                    tfeducation.grabFocus();
                    return;
                }

                // Update query
                Conn conn = new Conn();
                String query = "UPDATE employee SET sname = ?, salary = ?, address = ?, phone = ?, email = ?, education = ?, designation = ? WHERE empId = ?";
                PreparedStatement pstmt = conn.c.prepareStatement(query);
                pstmt.setString(1, sname);
                pstmt.setString(2, salary);
                pstmt.setString(3, address);
                pstmt.setString(4, phone);
                pstmt.setString(5, email);
                pstmt.setString(6, education);
                pstmt.setString(7, designation);
                pstmt.setString(8, empId);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Details updated successfully.");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating employee details.");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee("101");
    }
}

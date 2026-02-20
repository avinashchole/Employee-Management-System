package employee.mangement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove, generateSlip, manageLeave, markAttendance, viewAttendance;

    Home() {
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);

        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);

        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);

        update = new JButton("Update Employee");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);

        // Add the "Generate Salary Slip" button
        generateSlip = new JButton("Generate Salary Slip");
        generateSlip.setBounds(735, 200, 200, 40);
        generateSlip.addActionListener(this);
        image.add(generateSlip);

        // Add the "Manage Leave" button
        manageLeave = new JButton("Manage Leave");
        manageLeave.setBounds(735, 260, 200, 40);
        manageLeave.addActionListener(this);
        image.add(manageLeave);

        // Add the "Mark Attendance" button
        markAttendance = new JButton("Mark Attendance");
        markAttendance.setBounds(735, 320, 200, 40);
        markAttendance.addActionListener(this);
        image.add(markAttendance);

        // Add the "View Attendance" button
        viewAttendance = new JButton("View Attendance");
        viewAttendance.setBounds(735, 380, 200, 40);
        viewAttendance.addActionListener(this);
        image.add(viewAttendance);

        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee();
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == remove) {
            setVisible(false);
            new RemoveEmployee();
        } else if (ae.getSource() == generateSlip) {
            setVisible(false);
            new GenerateSalarySlip();
        } else if (ae.getSource() == manageLeave) {
            setVisible(false);
            new ManageLeave();
        } else if (ae.getSource() == markAttendance) {
            setVisible(false);
            new MarkAttendance();  // Navigate to Mark Attendance screen
        } else if (ae.getSource() == viewAttendance) {
            setVisible(false);
            new ViewAttendance(); // Navigate to View Attendance screen
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}

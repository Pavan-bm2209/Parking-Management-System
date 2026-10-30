package com.parking;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdatePhoneFrame extends JFrame implements ActionListener {

    JLabel userLabel;
    JLabel phoneLabel;

    JTextField userField;
    JTextField phoneField;

    JButton updateButton;

    public UpdatePhoneFrame() {

        setTitle("Update Phone Number");

        setSize(400,300);

        setLayout(null);

        userLabel = new JLabel("User ID");

        userLabel.setBounds(50,50,100,30);

        add(userLabel);

        userField = new JTextField();

        userField.setBounds(170,50,150,30);

        add(userField);

        phoneLabel = new JLabel("New Phone");

        phoneLabel.setBounds(50,100,100,30);

        add(phoneLabel);

        phoneField = new JTextField();

        phoneField.setBounds(170,100,150,30);

        add(phoneField);

        updateButton = new JButton("Update");

        updateButton.setBounds(130,180,120,35);

        add(updateButton);

        updateButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            int userId =
                    Integer.parseInt(userField.getText());

            String phone =
                    phoneField.getText();

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE USERS SET phone=? WHERE user_id=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, phone);

            pst.setInt(2, userId);

            int rows =
                    pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this,
                        "Phone Updated Successfully");

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "User Not Found");
            }

        } catch(Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }
}
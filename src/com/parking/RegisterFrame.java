package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterFrame extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel nameLabel;
    JLabel emailLabel;
    JLabel phoneLabel;
    JLabel passLabel;

    JTextField nameField;
    JTextField emailField;
    JTextField phoneField;

    JPasswordField passField;

    JButton registerButton;

    public RegisterFrame() {

        setTitle("User Registration");

        setSize(400,400);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("REGISTER");

        titleLabel.setBounds(150,20,100,30);

        add(titleLabel);

        nameLabel = new JLabel("Name");

        nameLabel.setBounds(50,70,100,30);

        add(nameLabel);

        nameField = new JTextField();

        nameField.setBounds(150,70,150,30);

        add(nameField);

        emailLabel = new JLabel("Email");

        emailLabel.setBounds(50,120,100,30);

        add(emailLabel);

        emailField = new JTextField();

        emailField.setBounds(150,120,150,30);

        add(emailField);

        phoneLabel = new JLabel("Phone");

        phoneLabel.setBounds(50,170,100,30);

        add(phoneLabel);

        phoneField = new JTextField();

        phoneField.setBounds(150,170,150,30);

        add(phoneField);

        passLabel = new JLabel("Password");

        passLabel.setBounds(50,220,100,30);

        add(passLabel);

        passField = new JPasswordField();

        passField.setBounds(150,220,150,30);

        add(passField);

        registerButton = new JButton("Register");

        registerButton.setBounds(140,290,120,30);

        add(registerButton);

        registerButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText();

        String email = emailField.getText();

        String phone = phoneField.getText();

        String password = passField.getText();

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "INSERT INTO USERS(name,email,phone,password_hash) VALUES(?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, name);

            pst.setString(2, email);

            pst.setString(3, phone);

            pst.setString(4, password);

            int rows = pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this,
                        "Registration Successful");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Registration Failed");
            }

        } catch (Exception ex) {

            System.out.println(ex);

        }

    }
}
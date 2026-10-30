package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel userLabel;
    JLabel passLabel;

    JTextField userField;
    JPasswordField passField;

    JButton loginButton;
    JButton registerButton;

    public LoginFrame() {

        setTitle("Parking Management System");

        setSize(400,350);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("LOGIN");

        titleLabel.setBounds(160,20,100,30);

        add(titleLabel);

        userLabel = new JLabel("Username");

        userLabel.setBounds(50,80,100,30);

        add(userLabel);

        userField = new JTextField();

        userField.setBounds(150,80,150,30);

        add(userField);

        passLabel = new JLabel("Password");

        passLabel.setBounds(50,130,100,30);

        add(passLabel);

        passField = new JPasswordField();

        passField.setBounds(150,130,150,30);

        add(passField);

        loginButton = new JButton("Login");

        loginButton.setBounds(70,210,100,30);

        add(loginButton);

        registerButton = new JButton("Register");

        registerButton.setBounds(200,210,100,30);

        add(registerButton);

        loginButton.addActionListener(this);

        registerButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loginButton) {

            String username = userField.getText();

            String password = passField.getText();

            try {

                Connection con = DBConnection.getConnection();

                String query =
                        "SELECT * FROM USERS WHERE name=? AND password_hash=?";

                PreparedStatement pst =
                        con.prepareStatement(query);

                pst.setString(1, username);

                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if(rs.next()) {

                    JOptionPane.showMessageDialog(this,
                            "Login Successful");

                    new DashboardFrame();

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Invalid Username or Password");
                }

            } catch (Exception ex) {

                System.out.println(ex);

            }

        }

        if(e.getSource() == registerButton) {

            new RegisterFrame();

        }

    }
}
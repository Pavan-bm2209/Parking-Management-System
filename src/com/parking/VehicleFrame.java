package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleFrame extends JFrame implements ActionListener {

    JLabel titleLabel;

    JLabel userIdLabel;
    JLabel plateLabel;
    JLabel modelLabel;
    JLabel typeLabel;

    JTextField userIdField;
    JTextField plateField;
    JTextField modelField;
    JTextField typeField;

    JButton addButton;

    public VehicleFrame() {

        setTitle("Add Vehicle");

        setSize(400,400);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("ADD VEHICLE");

        titleLabel.setBounds(140,20,150,30);

        add(titleLabel);

        userIdLabel = new JLabel("User ID");

        userIdLabel.setBounds(50,80,100,30);

        add(userIdLabel);

        userIdField = new JTextField();

        userIdField.setBounds(170,80,150,30);

        add(userIdField);

        plateLabel = new JLabel("License Plate");

        plateLabel.setBounds(50,130,100,30);

        add(plateLabel);

        plateField = new JTextField();

        plateField.setBounds(170,130,150,30);

        add(plateField);

        modelLabel = new JLabel("Model");

        modelLabel.setBounds(50,180,100,30);

        add(modelLabel);

        modelField = new JTextField();

        modelField.setBounds(170,180,150,30);

        add(modelField);

        typeLabel = new JLabel("Vehicle Type");

        typeLabel.setBounds(50,230,100,30);

        add(typeLabel);

        typeField = new JTextField();

        typeField.setBounds(170,230,150,30);

        add(typeField);

        addButton = new JButton("Add Vehicle");

        addButton.setBounds(120,300,140,35);

        add(addButton);

        addButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            int userId =
                    Integer.parseInt(userIdField.getText());

            String plate =
                    plateField.getText();

            String model =
                    modelField.getText();

            String type =
                    typeField.getText();

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "INSERT INTO VEHICLE(user_id,license_plate,model,vehicle_type) VALUES(?,?,?,?)";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, userId);

            pst.setString(2, plate);

            pst.setString(3, model);

            pst.setString(4, type);

            int rows = pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this,
                        "Vehicle Added Successfully");
                new DashboardFrame();
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Failed");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }

    }
}
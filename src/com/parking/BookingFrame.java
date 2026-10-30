package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingFrame extends JFrame implements ActionListener {

    JLabel titleLabel;

    JLabel vehicleLabel;
    JLabel lotLabel;
    JLabel slotLabel;
    JLabel startTimeLabel;

    JComboBox<String> vehicleBox;
    JComboBox<String> lotBox;
    JComboBox<String> slotBox;

    JTextField startTimeField;

    JButton bookButton;

    public BookingFrame() {

        setTitle("Parking Slot Booking");

        setSize(500,450);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("BOOK PARKING SLOT");

        titleLabel.setBounds(150,20,250,30);

        add(titleLabel);

        vehicleLabel = new JLabel("Select Vehicle");

        vehicleLabel.setBounds(50,80,120,30);

        add(vehicleLabel);

        vehicleBox = new JComboBox<>();

        vehicleBox.setBounds(200,80,180,30);

        add(vehicleBox);

        lotLabel = new JLabel("Select Parking Lot");

        lotLabel.setBounds(50,140,140,30);

        add(lotLabel);

        lotBox = new JComboBox<>();

        lotBox.setBounds(200,140,180,30);

        add(lotBox);

        slotLabel = new JLabel("Select Slot");

        slotLabel.setBounds(50,200,120,30);

        add(slotLabel);

        slotBox = new JComboBox<>();

        slotBox.setBounds(200,200,180,30);

        add(slotBox);

        startTimeLabel = new JLabel("Start Time (HH:mm)");

        startTimeLabel.setBounds(50,260,140,30);

        add(startTimeLabel);

        startTimeField = new JTextField();

        startTimeField.setBounds(200,260,180,30);

        add(startTimeField);

        bookButton = new JButton("Book Slot");

        bookButton.setBounds(170,330,130,35);

        add(bookButton);

        bookButton.addActionListener(this);

        lotBox.addActionListener(this);

        loadVehicles();

        loadLots();

        setVisible(true);
    }

    public void loadVehicles() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT license_plate FROM VEHICLE";

            PreparedStatement pst =
                    con.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                vehicleBox.addItem(
                        rs.getString("license_plate")
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }

    public void loadLots() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT name FROM PARKING_LOT";

            PreparedStatement pst =
                    con.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                lotBox.addItem(
                        rs.getString("name")
                );
            }

            loadSlots();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }

    public void loadSlots() {

        try {

            slotBox.removeAllItems();

            String selectedLot =
                    lotBox.getSelectedItem().toString();

            Connection con =
                    DBConnection.getConnection();

            String lotQuery =
                    "SELECT lot_id FROM PARKING_LOT WHERE name=?";

            PreparedStatement lotPst =
                    con.prepareStatement(lotQuery);

            lotPst.setString(1, selectedLot);

            ResultSet lotRs =
                    lotPst.executeQuery();

            int lotId = 0;

            if(lotRs.next()) {

                lotId =
                        lotRs.getInt("lot_id");
            }

            String slotQuery =
                    "SELECT slot_number FROM PARKING_SLOT WHERE lot_id=?";

            PreparedStatement slotPst =
                    con.prepareStatement(slotQuery);

            slotPst.setInt(1, lotId);

            ResultSet slotRs =
                    slotPst.executeQuery();

            while(slotRs.next()) {

                slotBox.addItem(
                        slotRs.getString("slot_number")
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == lotBox) {

            loadSlots();

            return;
        }

        try {

            String vehiclePlate =
                    vehicleBox.getSelectedItem().toString();

            String slotNumber =
                    slotBox.getSelectedItem().toString();

            String startTime =
                    startTimeField.getText();

            Connection con =
                    DBConnection.getConnection();

            String vehicleQuery =
                    "SELECT vehicle_id,user_id FROM VEHICLE WHERE license_plate=?";

            PreparedStatement vehiclePst =
                    con.prepareStatement(vehicleQuery);

            vehiclePst.setString(1, vehiclePlate);

            ResultSet vehicleRs =
                    vehiclePst.executeQuery();

            int vehicleId = 0;

            int userId = 0;

            if(vehicleRs.next()) {

                vehicleId =
                        vehicleRs.getInt("vehicle_id");

                userId =
                        vehicleRs.getInt("user_id");
            }

            String slotQuery =
                    "SELECT slot_id FROM PARKING_SLOT WHERE slot_number=?";

            PreparedStatement slotPst =
                    con.prepareStatement(slotQuery);

            slotPst.setString(1, slotNumber);

            ResultSet slotRs =
                    slotPst.executeQuery();

            int slotId = 0;

            if(slotRs.next()) {

                slotId =
                        slotRs.getInt("slot_id");
            }

            String checkQuery =
                    "SELECT * FROM BOOKING WHERE slot_id=? AND status='Booked'";

            PreparedStatement checkPst =
                    con.prepareStatement(checkQuery);

            checkPst.setInt(1, slotId);

            ResultSet checkRs =
                    checkPst.executeQuery();

            if(checkRs.next()) {

                JOptionPane.showMessageDialog(this,
                        "Slot Already Booked");

                return;
            }

            String query =
                    "INSERT INTO BOOKING(user_id,vehicle_id,slot_id,start_time,status) VALUES(?,?,?,?, 'Booked')";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, userId);

            pst.setInt(2, vehicleId);

            pst.setInt(3, slotId);

            pst.setString(4, startTime);

            int rows =
                    pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this,
                        "Slot Booked Successfully");

                new DashboardFrame();

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Booking Failed");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }
}
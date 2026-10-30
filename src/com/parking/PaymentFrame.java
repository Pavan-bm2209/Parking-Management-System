package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalTime;

public class PaymentFrame extends JFrame implements ActionListener {

    JLabel titleLabel;

    JLabel bookingIdLabel;
    JLabel endTimeLabel;
    JLabel methodLabel;

    JTextField bookingIdField;
    JTextField endTimeField;

    JComboBox<String> methodBox;

    JButton payButton;

    public PaymentFrame() {

        setTitle("Payment");

        setSize(400,350);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("PAYMENT");

        titleLabel.setBounds(150,20,100,30);

        add(titleLabel);

        bookingIdLabel = new JLabel("Booking ID");

        bookingIdLabel.setBounds(50,80,100,30);

        add(bookingIdLabel);

        bookingIdField = new JTextField();

        bookingIdField.setBounds(170,80,150,30);

        add(bookingIdField);

        endTimeLabel = new JLabel("End Time (HH:mm)");

        endTimeLabel.setBounds(50,130,130,30);

        add(endTimeLabel);

        endTimeField = new JTextField();

        endTimeField.setBounds(170,130,150,30);

        add(endTimeField);

        methodLabel = new JLabel("Payment Method");

        methodLabel.setBounds(50,180,120,30);

        add(methodLabel);

        methodBox = new JComboBox<>();

        methodBox.addItem("UPI");

        methodBox.addItem("Card");

        methodBox.addItem("Cash");

        methodBox.setBounds(170,180,150,30);

        add(methodBox);

        payButton = new JButton("Pay");

        payButton.setBounds(130,250,120,35);

        add(payButton);

        payButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            int bookingId =
                    Integer.parseInt(bookingIdField.getText());

            String endTime =
                    endTimeField.getText();

            String method =
                    methodBox.getSelectedItem().toString();

            Connection con =
                    DBConnection.getConnection();

            String bookingQuery =
                    "SELECT start_time FROM BOOKING WHERE booking_id=?";

            PreparedStatement bookingPst =
                    con.prepareStatement(bookingQuery);

            bookingPst.setInt(1, bookingId);

            ResultSet rs =
                    bookingPst.executeQuery();

            String startTime = "";

            if(rs.next()) {

                startTime =
                        rs.getString("start_time");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Booking ID");

                return;
            }

            LocalTime start =
                    LocalTime.parse(startTime);

            LocalTime end =
                    LocalTime.parse(endTime);

            long hours =
                    Duration.between(start,end).toHours();

            if(hours < 0) {

                hours = hours + 24;
            }

            if(hours == 0) {

                hours = 1;
            }

            double amount =
                    (Math.ceil(hours / 2.0)) * 50;

            String query =
                    "INSERT INTO PAYMENT(booking_id,amount,payment_method,payment_status) VALUES(?,?,?,'Paid')";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, bookingId);

            pst.setDouble(2, amount);

            pst.setString(3, method);

            int rows =
                    pst.executeUpdate();

            if(rows > 0) {

                String updateQuery =
                        "UPDATE BOOKING SET end_time=?, status='Completed' WHERE booking_id=?";

                PreparedStatement updatePst =
                        con.prepareStatement(updateQuery);

                updatePst.setString(1, endTime);

                updatePst.setInt(2, bookingId);

                updatePst.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Payment Successful\nParking Hours = "
                                + hours +
                                "\nAmount = ₹" + amount);

                new DashboardFrame();

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Payment Failed");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }
}
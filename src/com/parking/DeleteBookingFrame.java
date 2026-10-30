package com.parking;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteBookingFrame extends JFrame implements ActionListener {

    JLabel bookingLabel;

    JTextField bookingField;

    JButton deleteButton;

    public DeleteBookingFrame() {

        setTitle("Delete Booking");

        setSize(400,250);

        setLayout(null);

        bookingLabel = new JLabel("Booking ID");

        bookingLabel.setBounds(50,60,100,30);

        add(bookingLabel);

        bookingField = new JTextField();

        bookingField.setBounds(170,60,150,30);

        add(bookingField);

        deleteButton = new JButton("Delete");

        deleteButton.setBounds(130,140,120,35);

        add(deleteButton);

        deleteButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            int bookingId =
                    Integer.parseInt(
                            bookingField.getText());

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "DELETE FROM BOOKING WHERE booking_id=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, bookingId);

            int rows =
                    pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this,
                        "Booking Deleted Successfully");

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Booking Not Found");
            }

        } catch(Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }
    }
}
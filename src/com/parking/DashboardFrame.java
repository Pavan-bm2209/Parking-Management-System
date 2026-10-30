package com.parking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame implements ActionListener {

    JLabel titleLabel;

    JButton bookButton;
    JButton vehicleButton;
    JButton viewButton;
    JButton paymentButton;
    JButton updateButton;
    JButton deleteButton;
    JButton exitButton;

    public DashboardFrame() {

        setTitle("Dashboard");

        setSize(450,550);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("PARKING MANAGEMENT DASHBOARD");

        titleLabel.setBounds(70,30,300,30);

        add(titleLabel);

        bookButton = new JButton("Book Slot");

        bookButton.setBounds(120,90,180,30);

        add(bookButton);

        vehicleButton = new JButton("Add Vehicle");

        vehicleButton.setBounds(120,140,180,30);

        add(vehicleButton);

        viewButton = new JButton("View Booking");

        viewButton.setBounds(120,190,180,30);

        add(viewButton);

        paymentButton = new JButton("Payment");

        paymentButton.setBounds(120,240,180,30);

        add(paymentButton);

        updateButton = new JButton("Update Phone");

        updateButton.setBounds(120,290,180,30);

        add(updateButton);

        deleteButton = new JButton("Delete Booking");

        deleteButton.setBounds(120,340,180,30);

        add(deleteButton);

        exitButton = new JButton("Logout");

        exitButton.setBounds(120,390,180,30);

        add(exitButton);

        bookButton.addActionListener(this);

        vehicleButton.addActionListener(this);

        viewButton.addActionListener(this);

        paymentButton.addActionListener(this);

        updateButton.addActionListener(this);

        deleteButton.addActionListener(this);

        exitButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == bookButton) {

            new BookingFrame();
        }

        if(e.getSource() == vehicleButton) {

            new VehicleFrame();
        }

        if(e.getSource() == viewButton) {

            new ViewBookingFrame();
        }

        if(e.getSource() == paymentButton) {

            new PaymentFrame();
        }

        if(e.getSource() == updateButton) {

            new UpdatePhoneFrame();
        }

        if(e.getSource() == deleteButton) {

            new DeleteBookingFrame();
        }

        if(e.getSource() == exitButton) {

            new LoginFrame();

            dispose();
        }
    }
}
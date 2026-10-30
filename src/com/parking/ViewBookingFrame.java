package com.parking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewBookingFrame extends JFrame {

    JTable table;

    JScrollPane scrollPane;

    public ViewBookingFrame() {

        setTitle("View Bookings");

        setSize(1000,400);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {

                "Booking ID",
                "User ID",
                "Vehicle ID",
                "Slot Number",
                "Parking Lot",
                "Start Time",
                "End Time",
                "Status"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns,0);

        table = new JTable(model);

        scrollPane = new JScrollPane(table);

        scrollPane.setBounds(20,20,940,300);

        add(scrollPane);

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT " +
                            "B.booking_id, " +
                            "B.user_id, " +
                            "B.vehicle_id, " +
                            "PS.slot_number, " +
                            "PL.name AS lot_name, " +
                            "B.start_time, " +
                            "B.end_time, " +
                            "B.status " +
                            "FROM BOOKING B " +
                            "JOIN PARKING_SLOT PS " +
                            "ON B.slot_id = PS.slot_id " +
                            "JOIN PARKING_LOT PL " +
                            "ON PS.lot_id = PL.lot_id";

            PreparedStatement pst =
                    con.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                Object[] row = {

                        rs.getInt("booking_id"),

                        rs.getInt("user_id"),

                        rs.getInt("vehicle_id"),

                        rs.getString("slot_number"),

                        rs.getString("lot_name"),

                        rs.getString("start_time"),

                        rs.getString("end_time"),

                        rs.getString("status")
                };

                model.addRow(row);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.toString());
        }

        setVisible(true);
    }
}
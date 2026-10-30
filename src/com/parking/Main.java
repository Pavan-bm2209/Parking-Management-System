package com.parking;

public class Main {

    public static void main(String[] args) {

        DBConnection.getConnection();

        new LoginFrame();

    }
}
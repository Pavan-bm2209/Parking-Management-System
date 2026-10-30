Parking Management System

A Java desktop parking management application built with Swing and JDBC. The supplied artifacts show screens for account access, registration, vehicle entry, booking lookup/deletion, and payment. Database access uses MySQL Connector/J.

## Confirmed features

- Login and account registration.
- Add a vehicle for a user, with license plate, model, and vehicle type.
- View bookings in a table showing booking, user, vehicle, slot, parking lot, start/end times, and status.
- Delete a booking by booking ID.
- Record a payment using UPI, Card, or Cash; update the booking end time and mark it completed.
- Update a user's phone number.

The booking creation flow and dashboard navigation cannot be described from the supplied files. The payment screen calculates an amount from the elapsed whole hours between start and end times, but the bytecode does not establish a currency, rate, rounding policy, or overnight handling.

## Technology and entry point

- Java, using Swing for the desktop interface and JDBC for database access.
- Main class: `com.parking.Main` (`Main.java` in package `com.parking`).
- Database driver: MySQL Connector/J (`com.mysql.cj.jdbc.Driver`). The IntelliJ module file references a project library named `mysql-connector-j-9.7.0`.
- The module file also references an IntelliJ library named `java`; its contents and purpose are not available. No other third-party runtime dependency is confirmed by the supplied bytecode.

A JDK is required to compile. A graphical desktop environment is required to display the Swing windows.

## Database expectations

The compiled connection class points to a MySQL server on `localhost`, database `parking_management`, and uses a hard-coded database account. **The password is deliberately not reproduced here.** Replace the embedded connection settings with externally configured credentials before using the application, and do not commit credentials to source control.

SQL statements in the supplied classes reference these tables and columns:

- `USERS`: `user_id`, `name`, `email`, `phone`, `password_hash`
- `VEHICLE`: `user_id`, `license_plate`, `model`, `vehicle_type`
- `BOOKING`: `booking_id`, `user_id`, `vehicle_id`, `slot_id`, `start_time`, `end_time`, `status`
- `PARKING_SLOT`: `slot_id`, `slot_number`, `lot_id`
- `PARKING_LOT`: `lot_id`, `name`
- `PAYMENT`: `booking_id`, `amount`, `payment_method`, `payment_status`

These are names observed in SQL, not a complete schema specification. Column types, primary and foreign keys, nullability, indexes, initial records, and any triggers or constraints were not included in the upload. The application expects the joined booking query and inserts/updates above to be supported by the database. Obtain the project's original schema/setup script or confirm the schema with its author before creating a database; do not treat this list as executable DDL.

## Setup and run

The uploaded artifacts are insufficient to build or launch the whole application. To run it, first obtain the complete project, including the Java sources (especially `DashboardFrame` and the booking screen), the project's dependency configuration, and a compatible MySQL schema. Then:

1. Install a JDK and MySQL Server.
2. Create/configure the `parking_management` database using the project's verified schema script. Start MySQL and configure the connection URL, username, and password in the application without hard-coding secrets.
3. Add MySQL Connector/J to the project classpath. The supplied IntelliJ module names version `9.7.0`; use that version if available or verify compatibility with the project's JDK and MySQL server.
4. Ensure sources are laid out under `src/com/parking/` and declare `package com.parking;`.
5. From the project root, compile and launch (replace the jar path with the Connector/J jar location):

   ```sh
   javac -cp "lib/mysql-connector-j-9.7.0.jar" -d out src/com/parking/*.java
   java -cp "out;lib/mysql-connector-j-9.7.0.jar" com.parking.Main
   ```

   On macOS/Linux, use `:` instead of `;` between classpath entries in the `java` command.

The commands assume a complete source tree and a local `lib/` directory containing the connector jar. Neither is present in the supplied artifacts. If using IntelliJ IDEA, import the full project, configure its JDK and MySQL Connector/J library, then run `com.parking.Main` after the database settings and schema are ready.

## Supplied artifact inventory

The available upload included `Main.class`, `DBConnection.class`, `LoginFrame.class`, `RegisterFrame.class`, `VehicleFrame.class`, `ViewBookingFrame.class`, `DeleteBookingFrame.class`, `PaymentFrame.class`, `UpdatePhoneFrame.class`, and `demo(1).iml`. The `.iml` identifies `src` as the source root and names the Connector/J library. Two copies of an unrelated spreadsheet were also attached and were not used to infer application behavior.

This README describes the compiled artifacts provided for review. It does not claim that the missing sources, dashboard, booking creation screen, complete database schema, or build configuration have been verified.

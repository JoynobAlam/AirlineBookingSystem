import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //========FLIGHT DATABASE===========

        FlightDatabase db = new FlightDatabase();
        db.loadFlights();

        FileHelper file = new FileHelper();

        Booking booking = new Booking();
        Cancel cancel = new Cancel();

        Menu menu = new Menu("All", "Chicken Rice", "Available");
        Staff staff = new Staff(1, "Admin", "Manager", "Airport");

        staff.showStaff(); // improved
        menu.showMenu();   // improved

        Ticket ticket = null;

        // ================= USER REGISTRATION =================
        System.out.println("\n===== USER REGISTRATION =====");

        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        System.out.print("First Name: ");
        String fname = sc.nextLine();

        System.out.print("Last Name: ");
        String lname = sc.nextLine();

        System.out.print("Birthdate: ");
        String bdate = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        User user = new User(id, fname, lname, bdate, pass);
        file.saveUser(id, fname, lname, bdate, pass);//*

        user.showUser();// improved

        // ================= MODE SELECTION =================
        while (true) {

            System.out.println("\nEnter mode (admin/user/exit): ");
            String mode = sc.nextLine();

            // ================= ADMIN LOOP =================
            if (mode.equalsIgnoreCase("admin")) {

                while (true) {

                    System.out.println("\n===== ADMIN PANEL =====");
                    System.out.println("1. Add Flight");
                    System.out.println("2. View Flights");
                    System.out.println("3. Exit Admin Panel");

                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    // ADD FLIGHT
                    if (choice == 1) {

                        System.out.println("\n===== CREATE FLIGHT =====");

                        System.out.print("Flight ID: ");
                        int flightId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Source: ");
                        String source = sc.nextLine();

                        System.out.print("Destination: ");
                        String destination = sc.nextLine();

                        System.out.println("\nEnter Seats:");

                        System.out.print("First Class: ");
                        int f = sc.nextInt();

                        System.out.print("Business Class: ");
                        int b = sc.nextInt();

                        System.out.print("Premium Economy: ");
                        int p = sc.nextInt();

                        System.out.print("Economy: ");
                        int e = sc.nextInt();

                        sc.nextLine();

                        Route route = new Route(source, destination);

                        Cabin first = new Cabin("First", f);
                        Cabin business = new Cabin("Business", b);
                        Cabin premium = new Cabin("Premium Economy", p);
                        Cabin economy = new Cabin("Economy", e);

                        Flight flight = new Flight(flightId, route, first, business, premium, economy);

                        db.flights[db.count++] = flight; //* 
                        db.saveFlights();

                        System.out.println("Flight added successfully");
                    }

                    // VIEW FLIGHTS
                    else if (choice == 2) {

                        for (int i = 0; i < db.count; i++) { //* 
                            db.flights[i].displayFlight();
                        }
                    }

                    // EXIT ADMIN
                    else {
                        System.out.println("Exiting Admin Panel...");
                        break;
                    }
                }
            }

            // ================= USER MODE =================
            else if (mode.equalsIgnoreCase("user")) {

                while (true) {

                    System.out.println("\n===== USER MENU =====");
                    System.out.println("1. View Flights");
                    System.out.println("2. Book Ticket");
                    System.out.println("3. Cancel Ticket");
                    System.out.println("4. Exit User Mode");

                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    // VIEW FLIGHTS
                    if (choice == 1) {

                        for (int i = 0; i < db.count; i++) { // runs many times to show exact numbers of flight
                            db.flights[i].displayFlight();
                        }
                    }

                    // BOOK TICKET
                    else if (choice == 2) {

                        System.out.println("\nEnter Source: ");
                        String source = sc.nextLine();

                        System.out.println("Enter Destination: ");
                        String destination = sc.nextLine();

                        Flight flight = db.findFlight(source, destination);

                        if (flight == null) {
                            System.out.println("No flight found.");
                            continue;
                        }

                        System.out.println("\nAvailable Seats:");  
                        System.out.println("First: " + flight.firstClass.availableSeats);
                        System.out.println("Business: " + flight.businessClass.availableSeats);
                        System.out.println("Premium: " + flight.premiumEconomy.availableSeats);
                        System.out.println("Economy: " + flight.economy.availableSeats);

                        System.out.print("\nPassenger Name: ");
                        String name = sc.nextLine();

                        System.out.print("Cabin Type: ");
                        String cabin = sc.nextLine();

                        ticket = booking.bookTicket(1, name, flight, cabin);

                        if (ticket != null) {

                            Payment payment = new Payment(10000, 8000, 6000, 4000);
                            payment.makePayment(cabin);

                            db.saveFlights();

                            System.out.println("Booking successful");
                        }
                    }

                    // CANCEL
                    else if (choice == 3) {

                        Payment payment = new Payment(10000, 8000, 6000, 4000);

                        if (ticket != null) {
                            cancel.cancelTicket(ticket, db.flights[0], payment); // need help
                            ticket = null;

                            db.saveFlights();
                        } else {
                            System.out.println("No ticket found.");
                        }
                    }

                    // EXIT USER MODE
                    else {
                        System.out.println("Exiting User Mode...");
                        break;
                    }
                }
            }

            // ================= EXIT PROGRAM =================
            else {
                System.out.println("System closed.");
                break;
            }
        }

        sc.close();
    }
}

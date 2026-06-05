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

        Menu menu3= new Menu("Economy", "Chicken Soup and Salad", "Available");
        Menu menu = new Menu("Premium Economy", "Chicken Rice", "Available");
        Menu menu2= new Menu("Business", " Lobster ", "Available");
        Menu menu4= new Menu("First", "Beef and rice Or Fried rice with chicken", "Available");
        Staff staff = new Staff(1, "Admin", "Manager", "Airport");

        staff.showStaff(); 
        menu4.showMenu();
        menu2.showMenu();
        menu.showMenu();
        menu3.showMenu();   

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
        file.saveUser(id, fname, lname, bdate, pass);

        user.showUser();

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

                        System.out.print("Enter Economy Price: ");
                        double ePrice = sc.nextDouble();
                        System.out.print("Enter Premium Economy Price: ");
                        double pPrice = sc.nextDouble();
                        System.out.print("Enter Business Price: ");
                        double bPrice = sc.nextDouble();
                        System.out.print("Enter First Class Price: ");
                        double fPrice = sc.nextDouble();
                       

                        Route route = new Route(source, destination);

                        Cabin first = new Cabin("First", f);
                        Cabin business = new Cabin("Business", b);
                        Cabin premium = new Cabin("Premium Economy", p);
                        Cabin economy = new Cabin("Economy", e);

                        Flight flight = new Flight(flightId, new Route(source, destination), 
                           first, business, premium, economy, 
                           ePrice, pPrice, bPrice, fPrice);

                       

                        db.flights[db.count++] = flight; 
                        db.saveFlights();

                        System.out.println("Flight added successfully");
                    }

                    
                    else if (choice == 2) {

                        for (int i = 0; i < db.count; i++) { 
                            db.flights[i].displayFlight();
                        }
                    }

                    
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
        System.out.println("3. Find Cheapest Flight");
        System.out.println("4. Cancel Ticket");
        System.out.println("5. Find Cheapest Flight for your route");
        System.out.println("6. Exit User Mode");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            for (int i = 0; i < db.count; i++) {
                db.flights[i].displayFlight();
            }
        } 
        else if (choice == 2) {
            System.out.print("Source: ");
            String source = sc.nextLine();
            System.out.print("Destination: ");
            String destination = sc.nextLine();
            Flight flight = db.findFlight(source, destination);

            if (flight == null) {
                System.out.println("No flight found.");
                continue;
            }

            System.out.print("Passenger Name: ");
            String name = sc.nextLine();
            System.out.print("Cabin (First/Business/Premium Economy/Economy): ");
            String cabin = sc.nextLine();

            System.out.print("Enter Row (1-10): ");
            int row = sc.nextInt() - 1; 
            System.out.print("Enter Column (A-F): ");
            int col = sc.next().toUpperCase().charAt(0) - 'A';
            sc.nextLine();

            ticket = booking.bookTicket(1, name, flight, cabin, row, col);

            if (ticket != null) {
                Payment payment = new Payment(
                    flight.firstClassPrice, 
                    flight.businessPrice, 
                    flight.preEcoPrice, 
                    flight.ecoPrice
                );
                payment.makePayment(cabin);
                db.saveFlights();
            }
        } 
        else if (choice == 3) {
            Flight bestDeal = db.findGlobalCheapest();
            if (bestDeal != null) {
                bestDeal.displayFlight();
                System.out.print("Book this? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Cabin: "); String cabin = sc.nextLine();
                    System.out.print("Row (1-10) & Col (A-F): ");
                    int r = sc.nextInt() - 1; 
                    int c = sc.next().toUpperCase().charAt(0) - 'A';
                    sc.nextLine();
                    ticket = booking.bookTicket(1, name, bestDeal, cabin, r, c);
                }
            }
        } 
        else if (choice == 4) {
            if (ticket != null) {
                cancel.cancelTicket(ticket, new Payment(
                    ticket.flight.firstClassPrice,
                    ticket.flight.businessPrice,
                    ticket.flight.preEcoPrice,
                    ticket.flight.ecoPrice
                ));
                ticket = null;
                db.saveFlights();
            } else {
                System.out.println("No ticket to cancel.");
            }
        } 
        else if (choice == 5) {
            System.out.print("Enter Source: ");
            String s = sc.nextLine();
            System.out.print("Enter Destination: ");
            String d = sc.nextLine();

            
            Flight bestDeal = db.findCheapestOnRoute(s, d);

            if (bestDeal != null) {
                System.out.println("\nCheapest flight found:");
                bestDeal.displayFlight();
                
                System.out.print("\nBook this flight? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    System.out.print("Passenger Name: ");
                    String name = sc.nextLine();
                    System.out.print("Cabin (First/Business/Premium Economy/Economy): ");
                    String cabin = sc.nextLine();
                    System.out.print("Row (1-10): ");
                    int r = sc.nextInt() - 1;
                    System.out.print("Column (A-F): ");
                    int c = sc.next().toUpperCase().charAt(0) - 'A';
                    sc.nextLine();

                    
                    ticket = booking.bookTicket(1, name, bestDeal, cabin, r, c);

                    
                    if (ticket != null) {
                        Payment payment = new Payment(
                            bestDeal.firstClassPrice,
                            bestDeal.businessPrice,
                            bestDeal.preEcoPrice,
                            bestDeal.ecoPrice
                        );
                        payment.makePayment(cabin);
                        db.saveFlights();
                        System.out.println("Booking finalized!");
                    }
                }
            } else {
                System.out.println("No flights found on this route.");
            }
        } 
        else if (choice == 6) {
            break; 
        }
        }
    }

        else if (mode.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            break;
        }

        sc.close();
        }

        }}
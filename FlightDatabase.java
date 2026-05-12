import java.io.*;
import java.util.*;

class FlightDatabase {

    Flight[] flights = new Flight[50];
    int count = 0;      //* 

    // Load flights from file
    void loadFlights() {
        try {
            File file = new File("flights.txt");
            if (!file.exists()) return;         //* 

            Scanner fileScanner = new Scanner(file);    //* 

            while (fileScanner.hasNextLine()) {

                Scanner sc = new Scanner(fileScanner.nextLine());
                sc.useDelimiter(",");   //* 

                int id = sc.nextInt();
                String source = sc.next();
                String dest = sc.next();

                int f = sc.nextInt();
                int b = sc.nextInt();
                int p = sc.nextInt();
                int e = sc.nextInt();

                sc.close();

                Route route = new Route(source, dest);

                Cabin fc = new Cabin("First",f); // total seats
                Cabin bc = new Cabin("Business", b);
                Cabin pc = new Cabin("Premium Economy", p);
                Cabin ec = new Cabin("Economy", e);

                Flight flight = new Flight(id, route, fc, bc, pc, ec);

                flights[count++] = flight;//* 
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading flights");
        }
    }

    // Save flights to file
    void saveFlights() {
        try {
            FileWriter fw = new FileWriter("flights.txt");

            for (int i = 0; i < count; i++) {
                Flight f = flights[i];      //* 

                fw.write(                   //* 
                    f.flightId + "," +
                    f.route.source + "," +
                    f.route.destination + "," +
                    f.firstClass.totalSeats + "," +
                    f.businessClass.totalSeats + "," +
                    f.premiumEconomy.totalSeats + "," +
                    f.economy.totalSeats + "\n"
                );
            }

            fw.close();

        } catch (Exception e) {
            System.out.println("Error saving flights");
        }
    }

    // Find flight by route
    Flight findFlight(String source, String dest) {

        for (int i = 0; i < count; i++) {  //* 
            Flight f = flights[i];

            if (f.route.source.equalsIgnoreCase(source)
                    && f.route.destination.equalsIgnoreCase(dest)) {
                return f;
            }
        }
        return null;
    }
}


import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

class FlightDatabase {
    Flight[] flights = new Flight[50];
    int count = 0;    

    
    void loadFlights() {
        try {
            File file = new File("flights.txt");
            if (!file.exists()) {
                System.out.println("flights.txt not found. Starting with empty database.");
                return;
            }         

            Scanner fileScanner = new Scanner(file);    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; 

                Scanner sc = new Scanner(line);
                sc.useDelimiter(",");   

                int id = sc.nextInt();
                String source = sc.next();
                String dest = sc.next();

                
                int fSeats = sc.nextInt();
                int bSeats = sc.nextInt();
                int pSeats = sc.nextInt();
                int eSeats = sc.nextInt();

                
                double ePrice = sc.nextDouble();
                double pPrice = sc.nextDouble();
                double bPrice = sc.nextDouble();
                double fPrice = sc.nextDouble();

                sc.close();

                Route route = new Route(source, dest);
                Cabin fc = new Cabin("First Class", fSeats); 
                Cabin bc = new Cabin("Business Class", bSeats);
                Cabin pc = new Cabin("Premium Economy", pSeats);
                Cabin ec = new Cabin("Economy", eSeats);

                Flight flight = new Flight(id, route, fc, bc, pc, ec, ePrice, pPrice, bPrice, fPrice);
                flights[count++] = flight;
            }
            fileScanner.close();
            System.out.println("Flights successfully loaded into Joy Airlines system.");
        } catch (Exception e) {
            System.out.println("Error parsing flights.txt database file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    void saveFlights() {
        try {
            FileWriter fw = new FileWriter("flights.txt");
            for (int i = 0; i < count; i++) { 
                Flight f = flights[i];      
                fw.write( 
                    f.flightId + "," +
                    f.route.source + "," +
                    f.route.destination + "," +
                    f.firstClass.availableSeats + "," +
                    f.businessClass.availableSeats + "," +
                    f.premiumEconomy.availableSeats + "," +
                    f.economy.availableSeats + "," +
                    f.ecoPrice + "," +
                    f.preEcoPrice + "," +
                    f.businessPrice + "," +
                    f.firstClassPrice + "\n"
                );
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Critical Error: Could not save flight data changes to disk.");
        }
    }

    
   public Flight findGlobalCheapest() {
    if (count == 0) return null;
    
    Flight globalCheapest = flights[0];
    for (int i = 1; i < count; i++) {
        if (flights[i].ecoPrice < globalCheapest.ecoPrice) {
            globalCheapest = flights[i];
        }
    }
    return globalCheapest;
    }

    
    Flight findFlight(String source, String dest) {
        for (int i = 0; i < count; i++) {  
            Flight f = flights[i];
            if (f.route.source.equalsIgnoreCase(source) && f.route.destination.equalsIgnoreCase(dest)) {
                return f;
            }
        }
        return null;
    }


    Flight findFlightById(int flightId) {
        for (int i = 0; i < count; i++) {
            if (flights[i].flightId == flightId) {
                return flights[i];
            }
        }
        return null;
    }

    public Flight findCheapestOnRoute(String source, String destination) {
    Flight cheapest = null;
    double minPrice = Double.MAX_VALUE;

    for (int i = 0; i < count; i++) {
        Flight f = flights[i];
        
        if (f.route.source.equalsIgnoreCase(source) && 
            f.route.destination.equalsIgnoreCase(destination)) {
            
            
            if (f.ecoPrice < minPrice) {
                minPrice = f.ecoPrice;
                cheapest = f;
            }
        }
    }
    return cheapest;
}
}

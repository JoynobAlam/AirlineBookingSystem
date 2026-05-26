public class Flight {
    
    public int flightId;
    public Route route;
    public String date;       
    public String time;       
    
    public Cabin firstClass;
    public Cabin businessClass;
    public Cabin premiumEconomy;
    public Cabin economy;

    public double ecoPrice;
    public double preEcoPrice;
    public double businessPrice;
    public double firstClassPrice;

    public boolean[][] occupied = new boolean[10][6]; 

    public boolean bookSeat(int row, int col) {
        if (row < 0 || row > 9 || col < 0 || col > 5) return false;
        if (occupied[row][col]) 
            return false; 

        occupied[row][col] = true;
        return true;
    }
    
    

    
    public Flight(int flightId, Route route, Cabin first, Cabin business, Cabin premium, Cabin economy, 
                  double ep, double pp, double bp, double fp) {
        this.flightId = flightId;
        this.route = route;
        this.firstClass = first;
        this.businessClass = business;
        this.premiumEconomy = premium;
        this.economy = economy;
        this.ecoPrice = ep;
        this.preEcoPrice = pp;
        this.businessPrice = bp;
        this.firstClassPrice = fp;
    }

    
    public void displayFlight() {
        System.out.println("Flight ID: " + flightId);
        if (route != null) {
            System.out.println("Route: " + route.source + " to " + route.destination);
        }
        System.out.println("Schedule: " + time + " | Date: " + date);
        System.out.println("Fares - Eco: " + ecoPrice + " | Premium Eco: " 
        + preEcoPrice + " | Business: " + businessPrice + " | First: " + firstClassPrice);
    }

    public String getSeatLabel(int row, int col) {
        char seatLetter = (char) ('A' + col);
        return (row + 1) + "" + seatLetter;
    }

    public double getPriceForCabin(String cabinType) {
        if (cabinType.equalsIgnoreCase("First Class") || cabinType.equalsIgnoreCase("First")) {
            return firstClassPrice;
        } else if (cabinType.equalsIgnoreCase("Business Class") || cabinType.equalsIgnoreCase("Business")) {
            return businessPrice;
        } else if (cabinType.equalsIgnoreCase("Premium Economy")) {
            return preEcoPrice;
        } else {
            return ecoPrice;
        }
    }
}

class Flight {
    
    int flightId;
    Route route;

    Cabin firstClass;
    Cabin businessClass;
    Cabin premiumEconomy;
    Cabin economy;

    int totalSeats;
    
    Flight(int flightId, Route route,
           Cabin firstClass,
           Cabin businessClass,
           Cabin premiumEconomy,
           Cabin economy) {

        this.flightId = flightId;
        this.route = route;

        this.firstClass = firstClass;
        this.businessClass = businessClass;
        this.premiumEconomy = premiumEconomy;
        this.economy = economy;

        this.totalSeats =
                (firstClass.totalSeats +
                businessClass.totalSeats +     // class field
                premiumEconomy.totalSeats +
                economy.totalSeats);
    }

    void displayFlight(){
        System.out.println("Flight ID: "+flightId);
        route.showRoute();

        System.out.println("Seats available in first class: "+
            firstClass.availableSeats);
        System.out.println("Seats available in business class: "+
            businessClass.availableSeats);
        System.out.println("Seats available in premium economy class: "+
            premiumEconomy.availableSeats);
        System.out.println("Seats available in economy class: "+
            economy.availableSeats);

        System.out.println("Total Seats are: "+totalSeats);
        
    }
}    


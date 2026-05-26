class Ticket {
    int ticketId;
    String passengerName;
    String cabinType;
    
    
    public Flight flight;
    public String seatNumber;
    String cabinClass;
    User user;
    
    Ticket(int ticketId, String passengerName, String cabinType){
    this.ticketId = ticketId;
    this.passengerName = passengerName;
    this.cabinType = cabinType;
    }

    public Ticket(Flight flight, String seatNumber, String cabinClass, User user) {
    this.flight = flight;
    this.seatNumber = seatNumber;
    this.cabinClass = cabinClass;
    this.user = user;
    
    }
    
    void showTicket(){
        System.out.println("Ticket ID: "+ticketId);
        System.out.println("Name of the passenger: "+passengerName);
        System.out.println("Class: "+cabinType);
    }

    
}

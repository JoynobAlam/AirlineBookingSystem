class Booking {
    
    Ticket bookTicket(int ticketId, String name, Flight flight, String cabinType){
    boolean valid = true;

    if(cabinType.equalsIgnoreCase("First")){
        flight.firstClass.bookSeat();
    }      
    else if (cabinType.equalsIgnoreCase("Business")) {
        flight.businessClass.bookSeat();
    } 
    else if (cabinType.equalsIgnoreCase("Premium Economy")) {
        flight.premiumEconomy.bookSeat();
    }
    else if (cabinType.equalsIgnoreCase("Economy")) {
        flight.economy.bookSeat();
    }
    else {
        System.out.println("Invalid cabin type.");
        valid = false;
    }

    if(!valid) return null;

    return new Ticket(ticketId, name, cabinType);
    }
}
// bookTicket will return a ticket object 
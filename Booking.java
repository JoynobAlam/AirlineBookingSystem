class Booking {
    
    Ticket bookTicket(int ticketId, String name, Flight flight, String cabinType){
        if(cabinType.equalsIgnoreCase("First")){
            flight.firstClass.bookSeat();
        }      
        else if (cabinType.equalsIgnoreCase("Business")) {
            flight.businessClass.bookSeat();
        } 
        else if (cabinType.equalsIgnoreCase("Premium Economy")) {
            flight.premiumEconomy.bookSeat();
        }
        else if (cabinType.equalsIgnoreCase("Economu")) {
            flight.economy.bookSeat();
        }
        else {
            System.out.println("Invalid cabin type.");
        }
        return new Ticket(ticketId, name, cabinType);
    }
}
// bookTicket will return a ticket object 
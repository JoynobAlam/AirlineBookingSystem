class Booking {
    Ticket bookTicket(int ticketId, String name, Flight flight, String cabinType, int row, int col) {
        
        if (!flight.bookSeat(row, col)) {
            System.out.println("Seat " + flight.getSeatLabel(row, col) + " is already taken!");
            return null;
        }

        if (cabinType.equalsIgnoreCase("First")) {
            flight.firstClass.bookSeat();
        } else if (cabinType.equalsIgnoreCase("Business")) {
            flight.businessClass.bookSeat();
        } else if (cabinType.equalsIgnoreCase("Premium Economy")) {
            flight.premiumEconomy.bookSeat();
        } else if (cabinType.equalsIgnoreCase("Economy")) {
            flight.economy.bookSeat();
        } else {
            System.out.println("Invalid cabin type.");
            return null;
        }

        Ticket t = new Ticket(ticketId, name, cabinType);
        t.flight = flight; 
        t.seatNumber = flight.getSeatLabel(row, col);
        
        System.out.println("Booking successful for " + t.seatNumber);
        return t;
    }
}
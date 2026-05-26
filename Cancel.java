class Cancel {
    void cancelTicket(Ticket ticket, Payment payment) {
        if (ticket == null || ticket.flight == null) {
            System.out.println("No ticket found to cancel.");
            return;
        }

        int row = Integer.parseInt(ticket.seatNumber.replaceAll("[^0-9]", "")) - 1;
        int col = ticket.seatNumber.toUpperCase().charAt(ticket.seatNumber.length() - 1) - 'A';
        ticket.flight.occupied[row][col] = false;

        double price = 0;
        if (ticket.cabinType.equalsIgnoreCase("First")) {
            ticket.flight.firstClass.cancelSeat();
            price = payment.firstClassAmount;
        } 
        else if (ticket.cabinType.equalsIgnoreCase("Business")) {
            ticket.flight.businessClass.cancelSeat();
            price = payment.businessAmount;
        } 
        else if (ticket.cabinType.equalsIgnoreCase("Premium Economy")) {
            ticket.flight.premiumEconomy.cancelSeat();
            price = payment.preEcoAmount;
        } 
        else if (ticket.cabinType.equalsIgnoreCase("Economy")) {
            ticket.flight.economy.cancelSeat();
            price = payment.ecoAmount;
        }

        double refundAmount = price * 0.80;
        System.out.println("Ticket for seat " + ticket.seatNumber + " cancelled.");
        System.out.println("Refund amount (80%): " + refundAmount + " taka");
    }
}
class Cancel {
    void cancelTicket(Ticket ticket, Flight flight, Payment payment){
        
        if(ticket==null){
            System.out.println("No ticket found. ");
            return;
        }
        String cabinType= ticket.cabinType;
        double price=0;
        
        if(cabinType.equalsIgnoreCase("First")){
            flight.firstClass.cancelSeat();
            price=payment.firstClassAmount;

        }
        else if(cabinType.equalsIgnoreCase("Business")){
            flight.businessClass.cancelSeat();
            price=payment.businessAmount;
        }
        else if(cabinType.equalsIgnoreCase("Premium Economy")){
            flight.premiumEconomy.cancelSeat();
            price=payment.preEcoAmount;
        }
        else if(cabinType.equalsIgnoreCase("Economy")){
            flight.economy.cancelSeat();
            price=payment.ecoAmount;
        }
        else {
            System.out.println("Invalid cabin type. No refund possible.");
            return; // This stops the method right here so the refund math never happens
        }
        
        double refundAmount=price*0.80;

        System.out.println("Ticket cancelled.");
        System.out.println("Cabin class: "+cabinType);
        System.out.println("Refund amount (80%): "+refundAmount+"taka");
    }
}

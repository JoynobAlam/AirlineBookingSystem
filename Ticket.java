class Ticket {
    int ticketId;
    String passengerName;
    String cabinType;

    Ticket(int ticketId, String passengeName, String cabinType){
        this.ticketId=ticketId;
        this.passengerName=passengeName;
        this.cabinType=cabinType;
    }
    void showTicket(){
        System.out.println("Ticket ID: "+ticketId);
        System.out.println("Name of the passenger: "+passengerName);
        System.out.println("Class: "+cabinType);
    }
}

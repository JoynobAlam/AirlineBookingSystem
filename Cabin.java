class Cabin {
    String cabinName; // determines which type of cabin it is e.g first cls
    int totalSeats;
    int availableSeats;
    
    Cabin(String cabinName, int totalSeats){
        this.cabinName=cabinName;
        this.totalSeats=totalSeats;
        this.availableSeats=totalSeats;
    }
    void bookSeat(){
        if(availableSeats>0){
            availableSeats--;
            System.out.println("Seat booked in "+cabinName);
        }
       else{
        System.out.println("No seats available in "+cabinName);
    }}
    void cancelSeat(){
        if(availableSeats<totalSeats){
            availableSeats++;
            System.out.println("Seat cancelled in "+cabinName);
        }
    }
        
}

class Menu {
    String cabinType, foodItem, status;

    Menu(String cabinType, String foodItem, String status){
        this.cabinType=cabinType;
        this.foodItem=foodItem;
        this.status=status;
    }

    void showMenu(){
        System.out.println(".............Food Menu...........");
        System.out.println("Cabin: "+cabinType);
        System.out.println("Food: "+foodItem);
        System.out.println("Status: "+status);
    }
    void showMainOptions() {
        System.out.println("..............Airline Menu...............");
        System.out.println("1. View Available Flights"); // Brain: Run flight.displayFlight()
        System.out.println("2. Book a New Ticket");       // Brain: Run booking.bookTicket()
        System.out.println("3. Cancel a Ticket");         // Brain: Run cancel.cancelTicket()
        System.out.println("4. Check Food Menu");         // Brain: Run menu.showFoodMenu()
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
}

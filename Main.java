import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        //===============file=================
        FileHelper file=new FileHelper();
        //=========input flight details==========
        System.out.println("Enter Flight ID: ");
        int flightId=sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Source:");
        String source= sc.nextLine();

        System.out.println("Enter Destination:");
        String destination= sc.nextLine();

        Route route= new Route(source, destination);
        //=================class input======================
        System.out.println("Enter First Class Seats:");
        int f= sc.nextInt();

        System.out.println("Enter Business Class Seats:");
        int b=sc.nextInt();

        System.out.println("Enter Premium Economy Class Seats:");
        int p=sc.nextInt();

        System.out.println("Enter Economy Class Seats:");
        int e=sc.nextInt();

        Cabin first=new Cabin("First", f);
        Cabin business=new Cabin("Business", b);                // cabin object
        Cabin premium=new Cabin("Premium Economy", p);
        Cabin economy=new Cabin("Economy", e);

        Flight flight=new Flight(flightId, route, first, business, premium, economy);// here this is cabin object thats why first or business instead of firstclass or business class
        //============payment==============
        System.out.println("Enter First Class Price:");
        double pf=sc.nextDouble();

        System.out.println("Enter Business Class Price:");
        double pb=sc.nextDouble();

        System.out.println("Enter Premium Economy Class Price:");
        double pp=sc.nextDouble();

        System.out.println("Enter Economy Class Price:");
        double pe=sc.nextDouble();

        Payment payment= new Payment(pf, pb, pp, pe);// need help

        Booking booking =new Booking();
        Cancel cancel=new Cancel();
        Menu menu=new Menu("All","Chicken Rice", "Available");
        Staff staff =new Staff(1,"Admin", "Manager", "Airport");

        Ticket ticket=null;
        //=================Register User=========================
        System.out.println("\nRegister User");

        System.out.print("ID: ");
        String id= sc.next();
        
        sc.nextLine();

        System.out.print("First Name: ");
        String fname=sc.nextLine();

        System.out.print("Last Name: ");
        String lname=sc.nextLine();

        System.out.print("Birthdate: ");
        String bdate=sc.nextLine();

        System.out.print("Password: ");
        String pass =sc.nextLine();

        User user = new User(id, fname, lname, bdate, pass);

        file.saveUser(id, fname, lname, bdate, pass);
        //===========Main System Menu===============
        while (true) {
            System.out.println("\n=============Airline Menu===========d=");
            System.out.println("1. View Flight");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View Staff");
            System.out.println("5. View Food Menu");
            System.out.println("6. Exit");

            int choice=sc.nextInt();
            sc.nextLine();

            if(choice==1){
                flight.displayFlight();// flight cls
            }
            else if(choice==2){
                System.out.println("Passenger Name:");
                String name=sc.nextLine();
                
                System.out.println("Cabin Type:");
                String cabin=sc.nextLine();

                ticket = booking.bookTicket(1, name, flight, cabin);
                if(ticket != null){
                    payment.makePayment(cabin);
                    System.out.println("Ticket Booked.");
                }
                else {
                    System.out.println("Booking failed.");
                }
            }   
            else if(choice==3){
                cancel.cancelTicket(ticket, flight, payment);
                ticket=null;
            }
            else if(choice==4){
                staff.showStaff();
            }
            else if(choice==5){
                menu.showMenu();
            }
            else{
                break;
            }
        }
        sc.close();
    }    
}

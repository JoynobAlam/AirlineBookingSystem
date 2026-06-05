import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainGui {
    private FlightDatabase db = new FlightDatabase();
    private FileHelper fileHelper = new FileHelper();
    private Booking booking = new Booking();
    private Cancel cancel = new Cancel();
    private Ticket activeTicket = null; 
    private String lastBookedClass = "";
    private User currentUser = null;

   
    private java.util.ArrayList<Ticket> myTickets = new java.util.ArrayList<>();

    private JFrame frame = new JFrame("Joy Airlines - Royal Service");


    Color royalBlue = new Color(0, 31, 63);   
    Color goldAccent = new Color(196, 160, 82); 
    Color creamWhite = new Color(245, 245, 240); 
    Font royalFontBold = new Font("Serif", Font.BOLD, 22);
    Font menuFont = new Font("SansSerif", Font.BOLD, 14);

    public MainGui() {
        db.loadFlights(); 
        frame.setSize(1100, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupRoyalNavbar();   
        showRoyalHome(); 
        frame.setVisible(true);
    }

    private void setupRoyalNavbar() {
        JMenuBar navBar = new JMenuBar();
        navBar.setBackground(royalBlue);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, goldAccent));

        JLabel logo = new JLabel("  JOY AIRLINES  ");
        logo.setFont(royalFontBold);
        logo.setForeground(goldAccent);
        navBar.add(logo);
        navBar.add(Box.createHorizontalStrut(30));

       
        navBar.add(createRoyalMenu("SEARCH ROUTES", e -> showFlightSearch()));
        navBar.add(createRoyalMenu("BOOK FLIGHT", e -> showAllFlights()));

        navBar.add(createRoyalMenu("MY RESERVATIONS", e -> showCancel()));
        
        navBar.add(Box.createHorizontalGlue());

        JButton loginBtn = new JButton(" SIGN IN / REGISTER ");
        loginBtn.setBackground(goldAccent);
        loginBtn.setForeground(royalBlue);
        loginBtn.addActionListener(e -> showRegistration());
        navBar.add(loginBtn);
        navBar.add(Box.createHorizontalStrut(25));
        frame.setJMenuBar(navBar);
    }

    private JMenuItem createRoyalMenu(String text, java.awt.event.ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(menuFont);
        item.setForeground(Color.WHITE);
        item.setBackground(royalBlue);
        item.addActionListener(action);
        return item;
    }

    public void showRoyalHome() {
        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(creamWhite);
        JLabel msg = new JLabel("Experience the Royalty of Flight");
        msg.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 48));
        msg.setForeground(royalBlue);
        hero.add(msg);
        updateContent(hero);
    }


    public void showAllFlights() {
    
    JPanel p = createRoyalPanel("BOOK YOUR FLIGHT");

    JTextField srcField = createRoyalField();
    JTextField destField = createRoyalField();
    JButton findBtn = createGoldButton("FIND FLIGHTS");

    p.add(createLabel("Source:")); p.add(srcField);
    p.add(createLabel("Destination:")); p.add(destField);
    p.add(findBtn);


    findBtn.addActionListener(e -> {
    db.loadFlights();
    String s = srcField.getText().trim();
    String d = destField.getText().trim();

    p.removeAll(); 
    p.add(createLabel("Source:")); p.add(srcField);
    p.add(createLabel("Destination:")); p.add(destField);
    p.add(findBtn);

    
    java.util.Set<String> addedFlights = new java.util.HashSet<>();

    for (Flight f : db.flights) {
        if (f.route.source.trim().equalsIgnoreCase(s) && 
            f.route.destination.trim().equalsIgnoreCase(d)) {
            
            
            if (!addedFlights.contains(String.valueOf(f.flightId))) {
                JButton b = createGoldButton("Flight #" + f.flightId + " | Price: " + f.ecoPrice);
                b.addActionListener(act -> showBooking(f));
                p.add(b);
                
                addedFlights.add(String.valueOf(f.flightId));
            }
        }
    }
    p.revalidate();
    p.repaint();
    });

    updateContent(p);
}


    public void showFlightSearch() {
        db.loadFlights();
        JPanel p = createRoyalPanel("ALL AVAILABLE FLIGHTS✈️");

    
        p.removeAll();

    
        java.util.Set<String> addedFlights = new java.util.HashSet<String>();

    
        java.util.List<Flight> allFlights = new java.util.ArrayList<Flight>();
    
        for (Flight f : db.flights) { 
            allFlights.add(f); }
        allFlights.sort((f1, f2) -> Double.compare(f1.ecoPrice, f2.ecoPrice));

    
        for (Flight f : allFlights) {
            String fId = String.valueOf(f.flightId);
        
        
            if (!addedFlights.contains(fId)) {
                boolean isCheapest = (f == allFlights.get(0));
                String labelText = (isCheapest ? " ⭐CHEAPEST: " : "") + 
                                "Flight #" + f.flightId +" | From "+f.route.source +" To "+f.route.destination
                                        +"; | Price: " + f.ecoPrice + " Taka";
            
                JButton b = createGoldButton(labelText);
                b.addActionListener(e -> {
                    int choice = JOptionPane.showConfirmDialog(frame, 
                        "Do you want to book Flight #" + f.flightId + " from "+f.route.source+" to "+f.route.destination+"?", 
                        "Confirm Booking", JOptionPane.YES_NO_OPTION);
                
                    if (choice == JOptionPane.YES_OPTION) {
                        if (currentUser == null) {
                            JOptionPane.showMessageDialog(frame, "Please Sign In / Register first.");
                            showRegistration();
                        } 
                        else {
                            showBooking(f);
                        }
                    }
                });
                p.add(b);
                addedFlights.add(fId); 
            }
        }
    
    
            p.revalidate();
            p.repaint();
            updateContent(p);
    }

  public void showBooking(Flight f) {

    if (currentUser == null) {
        JOptionPane.showMessageDialog(frame, "Please Sign In / Register to book this flight.");
        showRegistration();
        return; 
    }

    
    JPanel p = createRoyalPanel("BOOKING: " + f.route.source + "🛬 -> " + f.route.destination);
    
    updateContent(p);

    String[] cabins = {"Economy", "Premium Economy", "Business", "First Class"};
    JComboBox<String> cabinBox = new JComboBox<>(cabins);
    p.add(new JLabel("Select Cabin Class:"));
    p.add(cabinBox);

    JPanel seatGrid = new JPanel(new GridLayout(0, 4));
    p.add(seatGrid);

    cabinBox.addActionListener(e -> {
        seatGrid.removeAll();
        String selected = (String) cabinBox.getSelectedItem();
        
        
        int seats;
        if (selected.equals("First Class")) seats = f.firstClass.availableSeats;
        else if (selected.equals("Business")) seats = f.businessClass.availableSeats;
        else if (selected.equals("Premium Economy")) seats = f.premiumEconomy.availableSeats;
        else seats = f.economy.availableSeats;

        
        double price;
        if (selected.equals("First Class")) price = f.firstClassPrice; 
        else if (selected.equals("Business")) price = f.businessPrice;
        else if (selected.equals("Business")) price = f.preEcoPrice;
        else price = f.ecoPrice;

        
        for (int i = 0; i < seats; i++) {
            String seatLabel = (i / 4 + 1) + "" + (char)('A' + (i % 4));
            JButton seatBtn = new JButton(seatLabel);
            
            seatBtn.addActionListener(action -> {
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Confirm payment of " + price + " Taka for " + selected + " " + seatLabel + "?", 
                    "Payment", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    myTickets.add(new Ticket(f, seatLabel, selected, currentUser));
                    seatBtn.setEnabled(false);
                    
                    if (selected.equals("First Class")) f.firstClass.availableSeats--;
                    else if (selected.equals("Business")) f.businessClass.availableSeats--;
                    else if (selected.equals("Premium Economy")) f.premiumEconomy.availableSeats--;
                    else f.economy.availableSeats--;
                    
                    db.saveFlights(); 
                    JOptionPane.showMessageDialog(frame, "Booking Successful!");
                }
            });
            seatGrid.add(seatBtn);
        }
        seatGrid.revalidate();
        seatGrid.repaint();
        });

    updateContent(p);
    }

    public void showCancel() {
    JPanel p = createRoyalPanel("MY RESERVATIONS💺");

    if (myTickets.isEmpty()) {
        p.add(createLabel("No bookings found."));
    } else {
        for (Ticket t : myTickets) {
            JPanel ticketRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ticketRow.setBackground(Color.WHITE);
            
            ticketRow.setMaximumSize(new Dimension(800, 60)); 
            
            JLabel info = new JLabel("Flight #" + t.flight.flightId + " | " + t.cabinClass + " | Seat: " + t.seatNumber);
            ticketRow.add(info);

            JButton cancelBtn = new JButton("CANCEL");
            cancelBtn.setBackground(new Color(180, 0, 0));
            cancelBtn.setForeground(Color.WHITE);
            
            cancelBtn.addActionListener(e -> {
                if (t.cabinClass.equals("First Class")) t.flight.firstClass.availableSeats++;
                else if (t.cabinClass.equals("Business")) t.flight.businessClass.availableSeats++;
                else if (t.cabinClass.equals("Premium Economy")) t.flight.premiumEconomy.availableSeats++;
                else t.flight.economy.availableSeats++;
                
                db.saveFlights();
                myTickets.remove(t);
                
                JOptionPane.showMessageDialog(frame, "Booking Cancelled.");
                showCancel();
            });
            
            ticketRow.add(cancelBtn);
            p.add(ticketRow);
            }
        }

    
    p.add(Box.createVerticalGlue());
    
    updateContent(p);
    }

    public void showRegistration() {
        JPanel p = createRoyalPanel("MEMBER REGISTRATION");
        JTextField idF = createRoyalField(); 
        JTextField nameF = createRoyalField(); 
        JTextField dobF = createRoyalField();
        JTextField passF = createRoyalField();

        p.add(createLabel("Passport/ID Number:")); p.add(idF);
        p.add(createLabel("Full Legal Name:")); p.add(nameF);
        p.add(createLabel("Date of Birth:")); p.add(dobF);
        p.add(createLabel("Security Password:")); p.add(passF);

        JButton btn = createGoldButton("JOIN JOY AIRLINES");
        btn.addActionListener(e -> {
        
        currentUser = new User(idF.getText(), nameF.getText(), "User", dobF.getText(), passF.getText());
        
        
        fileHelper.saveUser(idF.getText(), nameF.getText(), "User", dobF.getText(), passF.getText());
        
        JOptionPane.showMessageDialog(frame, "Welcome To Joy Airlines " + nameF.getText() + "! Best Of Luck With Your Journey. Hope You Enjoy Your Flight With Us.");
        showRoyalHome(); 
        });

        p.add(Box.createVerticalStrut(15));
        p.add(btn);
        updateContent(p);
    }

    private JPanel createRoyalPanel(String title) {
        JPanel p = new JPanel(); 
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); 
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(goldAccent, 2), title, TitledBorder.CENTER, TitledBorder.TOP, menuFont, royalBlue));
        
        return p;
    }

    private JLabel createLabel(String t) { 
        JLabel l = new JLabel(t); 
        l.setFont(menuFont); 
        l.setForeground(royalBlue); 
        l.setAlignmentX(Component.CENTER_ALIGNMENT); 
        return l; 
    }

    private JTextField createRoyalField() { 
        JTextField f = new JTextField(); 
        f.setMaximumSize(new Dimension(350, 40)); 
        f.addActionListener(e -> f.transferFocus());
        return f; 
    }

    private JButton createGoldButton(String t) { 
        JButton b = new JButton(t); 
        b.setBackground(royalBlue); 
        b.setForeground(goldAccent); 
        b.setAlignmentX(Component.CENTER_ALIGNMENT); 
        b.setPreferredSize(new Dimension(250, 45)); 
        return b; 
    }

    private void updateContent(JPanel panel) { 
    frame.getContentPane().removeAll();
    
    
    panel.setPreferredSize(new Dimension(800, 1200)); 
    
    JScrollPane scrollPane = new JScrollPane(panel);
    
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();

    }

    public static void main(String[] args) { 
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
        if (e.getID() == java.awt.event.KeyEvent.KEY_PRESSED && e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            if (e.getSource() instanceof JTextField) {
                ((Component) e.getSource()).transferFocus();
                return true; 
            }
        }
        return false;
    });
        new MainGui(); 
    }
}
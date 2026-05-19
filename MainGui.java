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

    private JFrame frame = new JFrame("Joy Airlines - Royal Service");
    
    // Royal Palette
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
        navBar.add(createRoyalMenu("BOOK FLIGHT", e -> showBooking()));
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

    
    public void showFlightSearch() {
        JPanel p = createRoyalPanel("FLIGHT INQUIRY");
        JTextField sF = createRoyalField(); 
        JTextField dF = createRoyalField();
        p.add(createLabel("Departure:"));
        p.add(sF);
        p.add(createLabel("Arrival:")); 
        p.add(dF);

        JButton btn = createGoldButton("CHECK DETAILS");
        btn.addActionListener(e -> {
            Flight f = db.findFlight(sF.getText().trim(), dF.getText().trim());
            if (f != null) {
                
                String details = "--- JOY AIRLINES ROUTE DETAILS ---\n" +
                 "Route: " + f.route.source + " to " + f.route.destination + "\n" +
                 "Total Available Seats: " + (f.firstClass.availableSeats + f.businessClass.availableSeats + 
                  f.premiumEconomy.availableSeats + f.economy.availableSeats) + "\n\n" +
                 "--- FARE CHART (PER PERSON) ---\n" +
                 "1. Economy: 6000 Taka\n" +
                 "2. Premium Economy: 8500 Taka\n" +
                 "3. Business: 12000 Taka\n" +
                 "4. First Class: 25000 Taka";
                JOptionPane.showMessageDialog(frame, details);
            } else {
                JOptionPane.showMessageDialog(frame, "No routes found.");
            }
        });
        p.add(btn);
        updateContent(p);
    }

    
    public void showBooking() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(frame, "Please Sign In to Joy Airlines first.");
            showRegistration();
            return;
        }

        JPanel p = createRoyalPanel("RESERVE YOUR SEAT");
        JTextField sF = createRoyalField(); 
        JTextField dF = createRoyalField();
        
        String[] types = {"Economy", "Premium Economy", "Business", "First Class"};
        JComboBox<String> classBox = new JComboBox<>(types);

        p.add(createLabel("Departure:")); 
        p.add(sF);
        p.add(createLabel("Arrival:")); 
        p.add(dF);
        p.add(createLabel("Class:")); 
        p.add(classBox);

        JButton btn = createGoldButton("PROCEED TO PAYMENT");
        btn.addActionListener(e -> {
            Flight f = db.findFlight(sF.getText().trim(), dF.getText().trim());
            String selectedClass = (String)classBox.getSelectedItem();

            if (f != null) {
                int ticketPrice = 0;
                String logicClass = "Economy"; 

                if (selectedClass.equalsIgnoreCase("First Class")) {
                    ticketPrice = 25000;
                    logicClass = "Business"; 
                } else if (selectedClass.equalsIgnoreCase("Business")) {
                    ticketPrice = 12000;
                    logicClass = "Business";
                } else if (selectedClass.equalsIgnoreCase("Premium Economy")) {
                    ticketPrice = 8500;
                    logicClass = "Economy";
                } else {
                    ticketPrice = 6000;
                    logicClass = "Economy";
                }

                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "JOY AIRLINES - OFFICIAL RECEIPT\n" +
                    "Passenger: " + currentUser.firstname + "\n" +
                    "Class: " + selectedClass + "\n" +
                    "Amount Due: " + ticketPrice + " Taka", 
                    "Secure Payment Gateway", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    this.activeTicket = booking.bookTicket(1001, currentUser.firstname, f, logicClass);
                    
                    if (this.activeTicket != null) {
                        this.lastBookedClass = selectedClass; // Save for showCancel
                        db.saveFlights();
                        JOptionPane.showMessageDialog(frame, "Payment Successful! " + selectedClass + " is confirmed.");
                        showRoyalHome(); 
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Booking Failed: Route not found.");
            }
        });
        
        p.add(Box.createVerticalStrut(15));
        p.add(btn);
        updateContent(p);
    }

    public void showCancel() {
        JPanel p = createRoyalPanel("MY RESERVATIONS");

        if (this.activeTicket != null) {
            p.add(createLabel("PASSENGER: " + currentUser.firstname));
            p.add(createLabel("STATUS: PAID & CONFIRMED"));
            p.add(createLabel("TICKET ID: #1001"));
            p.add(Box.createVerticalStrut(20));
            
            JButton btn = createGoldButton("CANCEL RESERVATION");
            btn.addActionListener(e -> {
                int originalPrice = 0;
                
                
                if (lastBookedClass.equalsIgnoreCase("First Class")) originalPrice = 25000;
                else if (lastBookedClass.equalsIgnoreCase("Business")) originalPrice = 12000;
                else if (lastBookedClass.equalsIgnoreCase("Premium Economy")) originalPrice = 8500;
                else originalPrice = 6000;

                double refundAmount = originalPrice * 0.8; 

                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Confirm Cancellation?\n" +
                    "Original Price: " + originalPrice + " Taka\n" +
                    "Refund (80%): " + refundAmount + " Taka", 
                    "Refund Policy", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    this.activeTicket = null; 
                    JOptionPane.showMessageDialog(frame, "Reservation Cancelled. " + refundAmount + " Taka refunded.");
                    showRoyalHome();
                }
            });
            p.add(btn);
        } else {
            p.add(createLabel("No active bookings found."));
        }
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
            if(nameF.getText().isEmpty() || idF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your details.");
                return;
            }
            // This stores the user globally so 'showBooking' knows you are logged in
            currentUser = new User(idF.getText(), nameF.getText(), "User", dobF.getText().trim(), passF.getText());
            fileHelper.saveUser(idF.getText(), nameF.getText(), "User", dobF.getText().trim(), passF.getText());
            
            JOptionPane.showMessageDialog(frame, "Welcome, " + nameF.getText() + "! You are now authorized to book.");
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
        p.setPreferredSize(new Dimension(450, 450)); 
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
        frame.setLayout(new GridBagLayout()); 
        frame.add(panel); 
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
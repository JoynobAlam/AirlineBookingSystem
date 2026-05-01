class Payment {
    double firstClassAmount, businessAmount, preEcoAmount, ecoAmount;

    Payment(double fA, double bA, double pE, double eco) {
        firstClassAmount = fA;
        businessAmount = bA;
        preEcoAmount = pE;
        ecoAmount = eco;
    }

    void makePayment(String cabinType) {
        if(cabinType.equalsIgnoreCase("First")){
            System.out.println("Payment successful: " + firstClassAmount + " taka");
        }
        else if(cabinType.equalsIgnoreCase("Business")){
            System.out.println("Payment successful: " + businessAmount + " taka");
        }
        else if(cabinType.equalsIgnoreCase("Premium Economy")){
            System.out.println("Payment successful: " + preEcoAmount + " taka");
        }
        else if(cabinType.equalsIgnoreCase("Economy")){
            System.out.println("Payment successful: " + ecoAmount + " taka");
        }
        else{
            System.out.println("Invalid cabin type.");
        }
    }
}

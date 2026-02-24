// BasicUser - lower limits, higher fees
public class BasicUser extends User {
    private static final double TRANSACTION_LIMIT = 500.0; // $500 max
    private static final double FEE_PERCENTAGE = 0.01; // 1% fee
    
    public BasicUser(String name, String walletAddress, String encryptedPrivateKey) {
        super(name, walletAddress, encryptedPrivateKey);
    }
    
    @Override
    public double getTransactionLimit() {
        return TRANSACTION_LIMIT;
    }
    
    @Override
    public double calculateTransactionFee(double amount) {
        return amount * FEE_PERCENTAGE;
    }
    
    @Override
    public java.util.ArrayList<SavingsPlan> getUserSavingsPlans() {
        java.util.ArrayList<SavingsPlan> plans = new java.util.ArrayList<>();
        
        // Plan 1: Limited Saver (Max $500, 2% interest)
        plans.add(new SavingsPlan(
            "Limited Saver",
            "Perfect for small amounts with quick returns",
            2.0,      // 2% annual interest
            50.0,     // Minimum $50
            "3 months",
            90
        ));
        
        // Plan 2: Basic Growth (Max $500, 2.5% interest)
        plans.add(new SavingsPlan(
            "Basic Growth",
            "Grow your money with basic commitment",
            2.5,      // 2.5% annual interest
            100.0,    // Minimum $100
            "6 months",
            180
        ));
        
        // Plan 3: Basic Emergency Fund (Anytime access, 1% interest)
        plans.add(new SavingsPlan(
            "Quick Cash",
            "Emergency savings with instant access",
            1.0,      // 1% annual interest
            50.0,     // Minimum $50
            "Anytime",
            0
        ));
        
        return plans;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [BASIC USER]";
    }
}

// PremiumUser - higher limits, lower fees
class PremiumUser extends User {
    private static final double TRANSACTION_LIMIT = 10000.0; // $10,000 max
    private static final double FEE_PERCENTAGE = 0.005; // 0.5% fee
    
    public PremiumUser(String name, String walletAddress, String encryptedPrivateKey) {
        super(name, walletAddress, encryptedPrivateKey);
    }
    
    @Override
    public double getTransactionLimit() {
        return TRANSACTION_LIMIT;
    }
    
    @Override
    public double calculateTransactionFee(double amount) {
        return amount * FEE_PERCENTAGE;
    }
    
    @Override
    public java.util.ArrayList<SavingsPlan> getUserSavingsPlans() {
        java.util.ArrayList<SavingsPlan> plans = new java.util.ArrayList<>();
        
        // Plan 1: Premium Growth (Up to $10,000, 4% interest)
        plans.add(new SavingsPlan(
            "Premium Growth",
            "Excellent returns for medium-term savings",
            4.0,      // 4% annual interest
            500.0,    // Minimum $500
            "6 months",
            180
        ));
        
        // Plan 2: Executive Invest (Up to $50,000, 5% interest)
        plans.add(new SavingsPlan(
            "Executive Invest",
            "Maximum returns for serious investors",
            5.0,      // 5% annual interest
            1000.0,   // Minimum $1000
            "1 year",
            365
        ));
        
        // Plan 3: Premium Emergency (Anytime access, 2.5% interest)
        plans.add(new SavingsPlan(
            "Premium Reserve",
            "Flexible premium emergency savings",
            2.5,      // 2.5% annual interest
            200.0,    // Minimum $200
            "Anytime",
            0
        ));
        
        // Plan 4: VIP Investment (Up to $100,000, 6% interest)
        plans.add(new SavingsPlan(
            "VIP Investment",
            "Elite returns for committed capital",
            6.0,      // 6% annual interest
            5000.0,   // Minimum $5000
            "18 months",
            540
        ));
        
        return plans;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [PREMIUM USER]";
    }
}

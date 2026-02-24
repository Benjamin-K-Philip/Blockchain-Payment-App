import java.util.ArrayList;

// SavingsPlansFactory - Creates and manages different savings plan options
public class SavingsPlansFactory {
    
    // Create all available plans for new users
    public static ArrayList<SavingsPlan> createDefaultPlans() {
        ArrayList<SavingsPlan> plans = new ArrayList<>();
        
        // Plan 1: Short-term Savings (3 months)
        plans.add(new SavingsPlan(
            "Quick Save",
            "Perfect for short-term savings with quick access",
            3.5,      // 3.5% annual interest
            100.0,    // Minimum $100
            "3 months",
            90
        ));
        
        // Plan 2: Medium-term Savings (6 months)
        plans.add(new SavingsPlan(
            "Growth Plan",
            "Build your wealth over 6 months with better returns",
            4.5,      // 4.5% annual interest
            200.0,    // Minimum $200
            "6 months",
            180
        ));
        
        // Plan 3: Long-term Savings (1 year)
        plans.add(new SavingsPlan(
            "Premium Save",
            "Maximum returns for committed long-term savings",
            5.5,      // 5.5% annual interest
            500.0,    // Minimum $500
            "1 year",
            365
        ));
        
        // Plan 4: Emergency Fund (Flexible)
        plans.add(new SavingsPlan(
            "Emergency Fund",
            "Flexible access to your emergency savings anytime",
            2.0,      // 2% annual interest
            50.0,     // Minimum $50
            "Anytime",
            0
        ));
        
        // Plan 5: Investment Plan (18 months)
        plans.add(new SavingsPlan(
            "Investment Plus",
            "High returns for strategic long-term investment",
            6.5,      // 6.5% annual interest
            1000.0,   // Minimum $1000
            "18 months",
            540
        ));
        
        return plans;
    }
    
    // Get a specific plan by name
    public static SavingsPlan getPlanByName(String planName) {
        ArrayList<SavingsPlan> plans = createDefaultPlans();
        for (SavingsPlan plan : plans) {
            if (plan.getPlanName().equalsIgnoreCase(planName)) {
                return new SavingsPlan(
                    plan.getPlanName(),
                    plan.getDescription(),
                    plan.getInterestRate(),
                    plan.getMinimumAmount(),
                    plan.getLockingPeriod(),
                    plan.getLockingDays()
                );
            }
        }
        return null;
    }
    
    // Display all available plans
    public static void displayAllPlans() {
        System.out.println("\n=== AVAILABLE SAVINGS PLANS ===");
        ArrayList<SavingsPlan> plans = createDefaultPlans();
        int counter = 1;
        for (SavingsPlan plan : plans) {
            System.out.println(counter + ". " + plan.getPlanName());
            System.out.println("   Description: " + plan.getDescription());
            System.out.println("   Interest Rate: " + plan.getInterestRate() + "% p.a.");
            System.out.println("   Minimum: " + plan.getMinimumAmount() + " USDT");
            System.out.println("   Locking Period: " + plan.getLockingPeriod());
            System.out.println();
            counter++;
        }
    }
}

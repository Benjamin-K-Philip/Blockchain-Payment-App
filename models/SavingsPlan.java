// SavingsPlan class - Represents different savings plan options for users
public class SavingsPlan {
    private String planName;
    private String description;
    private double interestRate; // Annual interest rate as percentage
    private double minimumAmount; // Minimum amount to start plan
    private String lockingPeriod; // How long money is locked (e.g., "3 months", "6 months", "1 year")
    private int lockingDays; // Locking period in days
    private double currentAmount; // Current savings in this plan
    
    public SavingsPlan(String planName, String description, double interestRate, 
                       double minimumAmount, String lockingPeriod, int lockingDays) {
        this.planName = planName;
        this.description = description;
        this.interestRate = interestRate;
        this.minimumAmount = minimumAmount;
        this.lockingPeriod = lockingPeriod;
        this.lockingDays = lockingDays;
        this.currentAmount = 0.0;
    }
    
    // Deposit money into the plan
    public void deposit(double amount) {
        if (amount < minimumAmount) {
            throw new IllegalArgumentException("Minimum deposit amount is " + minimumAmount);
        }
        this.currentAmount += amount;
    }
    
    // Calculate interest earned
    public double calculateInterest() {
        // Simple interest: Interest = (Principal * Rate * Time) / 100
        // Time = lockingDays / 365
        double time = lockingDays / 365.0;
        return (currentAmount * interestRate * time) / 100.0;
    }
    
    // Get total amount after interest
    public double getTotalAmount() {
        return currentAmount + calculateInterest();
    }
    
    // Withdraw money (only if locking period is over)
    public double withdraw() {
        double totalAmount = getTotalAmount();
        this.currentAmount = 0.0;
        return totalAmount;
    }
    
    // Getters
    public String getPlanName() {
        return planName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public double getMinimumAmount() {
        return minimumAmount;
    }
    
    public String getLockingPeriod() {
        return lockingPeriod;
    }
    
    public int getLockingDays() {
        return lockingDays;
    }
    
    public double getCurrentAmount() {
        return currentAmount;
    }
    
    @Override
    public String toString() {
        return "Plan: " + planName + 
               " | Interest: " + interestRate + "% p.a." +
               " | Locking: " + lockingPeriod +
               " | Min: " + minimumAmount + " USDT" +
               " | Current Savings: " + String.format("%.2f", currentAmount) + " USDT" +
               " | Expected Return: " + String.format("%.2f", getTotalAmount()) + " USDT";
    }
}

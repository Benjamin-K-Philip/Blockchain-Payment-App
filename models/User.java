import java.util.ArrayList;

// Abstract class demonstrating INHERITANCE & POLYMORPHISM
public abstract class User {
    protected String name;
    protected String walletAddress;
    protected String encryptedPrivateKey;
    protected double balance; // in USDT
    protected ArrayList<SavingsPlan> savingsPlan; // Multiple savings plans
    
    // Constructor
    public User(String name, String walletAddress, String encryptedPrivateKey) {
        this.name = name;
        this.walletAddress = walletAddress;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.balance = 0.0;
        this.savingsPlan = new ArrayList<>();
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double getTransactionLimit();
    
    // Abstract method for transaction fees
    public abstract double calculateTransactionFee(double amount);
    
    // Abstract method - each user type has different savings plans
    public abstract ArrayList<SavingsPlan> getUserSavingsPlans();
    
    // Concrete methods
    public void updateBalance(double amount) {
        this.balance += amount;
    }
    
    public String getName() {
        return name;
    }
    
    public String getWalletAddress() {
        return walletAddress;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    // Savings plan methods
    public void addSavingsPlan(SavingsPlan plan) {
        this.savingsPlan.add(plan);
    }
    
    public ArrayList<SavingsPlan> getSavingsPlans() {
        return savingsPlan;
    }
    
    public double getTotalSavings() {
        double total = 0;
        for (SavingsPlan plan : savingsPlan) {
            total += plan.getCurrentAmount();
        }
        return total;
    }
    
    public double getTotalSavingsWithInterest() {
        double total = 0;
        for (SavingsPlan plan : savingsPlan) {
            total += plan.getTotalAmount();
        }
        return total;
    }

    @Override
    public String toString() {
        return "User: " + name + " | Wallet: " + walletAddress + " | Balance: " + balance + " USDT";
    }
}

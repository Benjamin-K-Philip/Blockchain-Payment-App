import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

// Singleton pattern - only ONE instance of BlockchainService
public class BlockchainService {
    
    // Step 1: Private static instance
    private static BlockchainService instance = null;
    
    // Step 2: Private constructor
    private BlockchainService() {
        System.out.println("✅ BlockchainService initialized (Singleton)");
    }
    
    // Step 3: Public static method to get instance
    public static BlockchainService getInstance() {
        if (instance == null) {
            synchronized (BlockchainService.class) {
                if (instance == null) {
                    instance = new BlockchainService();
                }
            }
        }
        return instance;
    }
    
    // ArrayList to store transaction history
    private ArrayList<Transaction> transactionHistory = new ArrayList<>();
    
    // ArrayList of observers (Observer pattern)
    private ArrayList<TransactionObserver> observers = new ArrayList<>();
    
    // HashMap to store all users by wallet address 
    private HashMap<String, User> userRegistry = new HashMap<>();
    
    // Register a user in the system (simple registration)
    public void registerUser(User user) {
        userRegistry.put(user.getWalletAddress(), user);
        System.out.println("✅ User registered: " + user.getName());
    }
    
    // Get user by wallet address 
    public User getUserByAddress(String walletAddress) {
        return userRegistry.get(walletAddress);
    }
    
    // Register observer
    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }
    
    // Notify all observers
    private void notifyTransactionSuccess(Transaction transaction) {
        for (TransactionObserver observer : observers) {
            observer.onTransactionCompleted(transaction);
        }
    }
    
    private void notifyTransactionFailure(Transaction transaction, String reason) {
        for (TransactionObserver observer : observers) {
            observer.onTransactionFailed(transaction, reason);
        }
    }
    
    // Send money method with exception handling 
    public void sendMoney(User sender, String recipientAddress, double amount) 
            throws InsufficientBalanceException, InvalidAddressException, TransactionLimitExceededException {
        
        // Validation 1: Check recipient address
        if (recipientAddress == null || recipientAddress.length() < 10) {
            throw new InvalidAddressException("Invalid recipient address: " + recipientAddress);
        }
        
        // Validation 2: Check transaction limit
        if (amount > sender.getTransactionLimit()) {
            throw new TransactionLimitExceededException(
                "Amount " + amount + " exceeds limit of " + sender.getTransactionLimit());
        }
        
        // Calculate fee using polymorphism
        double fee = sender.calculateTransactionFee(amount);
        double totalCost = amount + fee;
        
        // Validation 3: Check balance
        if (sender.getBalance() < totalCost) {
            throw new InsufficientBalanceException(
                "Insufficient balance. Required: " + totalCost + ", Available: " + sender.getBalance());
        }
        
        // Create transaction
        Transaction transaction = new Transaction(sender.getWalletAddress(), recipientAddress, amount, fee);
        
        try {
            // Simulate blockchain transaction
            System.out.println("\n⏳ Processing blockchain transaction...");
            Thread.sleep(1000); // Simulate network delay
            
            // Deduct from sender
            sender.updateBalance(-totalCost);
            
            // Add to recipient if they exist in system 
            User recipient = userRegistry.get(recipientAddress);
            if (recipient != null) {
                recipient.updateBalance(amount); // Recipient gets the amount WITHOUT fee
                System.out.println("✅ Recipient balance updated: " + recipient.getName() + " received " + amount + " USDT");
            } else {
                System.out.println("⚠️  Recipient not in system. Money sent to external wallet: " + recipientAddress);
            }
            
            // Mark transaction as success
            transaction.setStatus("SUCCESS");
            
            // Add to history
            transactionHistory.add(transaction);
            
            // Notify observers
            notifyTransactionSuccess(transaction);
            
            System.out.println("✅ Transaction successful!");
            
        } catch (InterruptedException e) {
            transaction.setStatus("FAILED");
            notifyTransactionFailure(transaction, e.getMessage());
            System.out.println("❌ Transaction failed!");
        }
    }
    
    // Get transaction history 
    public ArrayList<Transaction> getTransactionHistory() {
        Collections.sort(transactionHistory);
        return transactionHistory;
    }
    
    // Get user's transactions only
    public ArrayList<Transaction> getUserTransactions(String walletAddress) {
        ArrayList<Transaction> userTransactions = new ArrayList<>();
        for (Transaction tx : transactionHistory) {
            if (tx.toString().contains(walletAddress.substring(0, 10))) {
                userTransactions.add(tx);
            }
        }
        return userTransactions;
    }
    
    // Simulate checking balance from blockchain
    public double checkBalance(String walletAddress) {
        System.out.println("🔍 Checking blockchain balance for: " + walletAddress);
        return Math.random() * 1000;
    }
}

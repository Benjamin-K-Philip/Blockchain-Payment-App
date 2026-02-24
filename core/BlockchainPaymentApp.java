import java.util.ArrayList;
import java.util.Scanner;

public class BlockchainPaymentApp {
    
    public static void main(String[] args) {
        System.out.println("=== BLOCKCHAIN PAYMENT SYSTEM ===\n");
        
        // Get Singleton instance
        BlockchainService blockchain = BlockchainService.getInstance();
        
        // Add observer (Observer pattern)
        EmailNotifier emailNotifier = new EmailNotifier();
        blockchain.addObserver(emailNotifier);
        
        // Create users (demonstrating inheritance and polymorphism)
        User user1 = new BasicUser("Ahmed", "0x1234567890abcdef", "encryptedKey1");
        User user2 = new PremiumUser("Fatima", "0xfedcba0987654321", "encryptedKey2");
        
        // Set initial balances
        user1.setBalance(600.0);
        user2.setBalance(5000.0);
        
        // REGISTER USERS IN THE SYSTEM (NEW!)
        blockchain.registerUser(user1);
        blockchain.registerUser(user2);
        
        System.out.println(user1);
        System.out.println(user2);
        System.out.println("\n📊 Savings Plans by User Type:");
        System.out.println("   " + user1.getName() + " (Basic): " + user1.getSavingsPlans().size() + " custom plans");
        System.out.println("   " + user2.getName() + " (Premium): " + user2.getSavingsPlans().size() + " custom plans");
        System.out.println();
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Send Money (Ahmed - Basic User)");
            System.out.println("2. Send Money (Fatima - Premium User)");
            System.out.println("3. View Transaction History");
            System.out.println("4. Check Balance");
            System.out.println("5. View Savings Plans");
            System.out.println("6. Deposit to Savings Plan");
            System.out.println("7. View Savings Summary");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            try {
                switch (choice) {
                    case 1:
                        performTransaction(scanner, blockchain, user1, user2);
                        break;
                    case 2:
                        performTransaction(scanner, blockchain, user2, user1);
                        break;
                    case 3:
                        viewTransactionHistory(blockchain);
                        break;
                    case 4:
                        System.out.println("\n" + user1);
                        System.out.println(user2);
                        break;
                    case 5:
                        viewSavingsPlans(scanner, user1, user2);
                        break;
                    case 6:
                        depositToSavingsPlan(scanner, user1, user2);
                        break;
                    case 7:
                        viewSavingsSummary(user1, user2);
                        break;
                    case 8:
                        running = false;
                        System.out.println("👋 Thank you for using Blockchain Payment System!");
                        break;
                    default:
                        System.out.println("❌ Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private static void performTransaction(Scanner scanner, BlockchainService blockchain, User sender, User otherUser) {
        try {
            System.out.println("\n--- SEND MONEY ---");
            System.out.println("1. Send to " + otherUser.getName() + " (" + otherUser.getWalletAddress() + ")");
            System.out.println("2. Send to external wallet");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            String recipient;
            if (choice == 1) {
                recipient = otherUser.getWalletAddress();
            } else {
                System.out.print("Enter recipient address: ");
                recipient = scanner.nextLine();
            }
            
            System.out.print("Enter amount (USDT): ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            blockchain.sendMoney(sender, recipient, amount);
            
        } catch (InsufficientBalanceException | InvalidAddressException | TransactionLimitExceededException e) {
            System.out.println("❌ Transaction failed: " + e.getMessage());
        }
    }
    
    private static void viewTransactionHistory(BlockchainService blockchain) {
        System.out.println("\n=== TRANSACTION HISTORY (Sorted by Date) ===");
        ArrayList<Transaction> history = blockchain.getTransactionHistory();
        
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction tx : history) {
                System.out.println(tx);
            }
        }
    }
    
    private static void viewSavingsPlans(Scanner scanner, User user1, User user2) {
        System.out.println("\n--- SELECT USER ---");
        System.out.println("1. " + user1.getName() + " (Basic User)");
        System.out.println("2. " + user2.getName() + " (Premium User)");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        User selectedUser = (choice == 1) ? user1 : user2;
        System.out.println("\n=== SAVINGS PLANS FOR " + selectedUser.getName().toUpperCase() + " ===");
        
        ArrayList<SavingsPlan> plans = selectedUser.getSavingsPlans();
        if (plans.isEmpty()) {
            System.out.println("No savings plans available.");
        } else {
            int counter = 1;
            for (SavingsPlan plan : plans) {
                System.out.println("\n" + counter + ". " + plan.getPlanName());
                System.out.println("   " + plan);
                counter++;
            }
        }
    }
    
    private static void depositToSavingsPlan(Scanner scanner, User user1, User user2) {
        System.out.println("\n--- SELECT USER ---");
        System.out.println("1. " + user1.getName() + " (Basic User)");
        System.out.println("2. " + user2.getName() + " (Premium User)");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        User selectedUser = (choice == 1) ? user1 : user2;
        
        System.out.println("\n=== DEPOSIT TO SAVINGS PLAN ===");
        ArrayList<SavingsPlan> plans = selectedUser.getSavingsPlans();
        
        int counter = 1;
        for (SavingsPlan plan : plans) {
            System.out.println(counter + ". " + plan.getPlanName() + " (Min: " + plan.getMinimumAmount() + " USDT, Interest: " + plan.getInterestRate() + "%)");
            counter++;
        }
        
        System.out.print("Select plan (1-" + plans.size() + "): ");
        int planChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (planChoice < 1 || planChoice > plans.size()) {
            System.out.println("❌ Invalid plan selection!");
            return;
        }
        
        SavingsPlan selectedPlan = plans.get(planChoice - 1);
        
        System.out.print("Enter deposit amount (USDT): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        try {
            // Check if user has enough balance
            if (selectedUser.getBalance() < amount) {
                System.out.println("❌ Insufficient balance! Available: " + selectedUser.getBalance() + " USDT");
                return;
            }
            
            selectedPlan.deposit(amount);
            selectedUser.updateBalance(-amount); // Deduct from main balance
            System.out.println("✅ Deposited " + amount + " USDT to " + selectedPlan.getPlanName());
            System.out.println("✅ Expected return after " + selectedPlan.getLockingPeriod() + ": " + 
                             String.format("%.2f", selectedPlan.getTotalAmount()) + " USDT");
            System.out.println("✅ Your new balance: " + selectedUser.getBalance() + " USDT");
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    private static void viewSavingsSummary(User user1, User user2) {
        System.out.println("\n=== SAVINGS SUMMARY ===");
        
        System.out.println("\n" + user1.getName() + " (Basic User):");
        System.out.println("  Main Balance: " + user1.getBalance() + " USDT");
        System.out.println("  Total Savings: " + user1.getTotalSavings() + " USDT");
        System.out.println("  Expected Returns (with interest): " + String.format("%.2f", user1.getTotalSavingsWithInterest()) + " USDT");
        System.out.println("  Total Assets: " + String.format("%.2f", user1.getBalance() + user1.getTotalSavingsWithInterest()) + " USDT");
        
        System.out.println("\n" + user2.getName() + " (Premium User):");
        System.out.println("  Main Balance: " + user2.getBalance() + " USDT");
        System.out.println("  Total Savings: " + user2.getTotalSavings() + " USDT");
        System.out.println("  Expected Returns (with interest): " + String.format("%.2f", user2.getTotalSavingsWithInterest()) + " USDT");
        System.out.println("  Total Assets: " + String.format("%.2f", user2.getBalance() + user2.getTotalSavingsWithInterest()) + " USDT");
    }
}


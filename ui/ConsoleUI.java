import java.util.ArrayList;
import java.util.Scanner;

/**
 * ConsoleUI - Enhanced Console-based User Interface for Blockchain Payment System
 * Replaces HTML/CSS/JavaScript with rich Java console output
 */
public class ConsoleUI {
    
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    
    // Colors
    private static final String CYAN = "\033[36m";
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String YELLOW = "\033[33m";
    private static final String BLUE = "\033[34m";
    
    private BlockchainService blockchain;
    private Scanner scanner;
    private User user1;
    private User user2;
    private boolean darkMode = true;
    
    public ConsoleUI() {
        this.blockchain = BlockchainService.getInstance();
        this.scanner = new Scanner(System.in);
        this.user1 = new BasicUser("Ahmed", "0x1234567890abcdef", "encryptedKey1");
        this.user2 = new PremiumUser("Fatima", "0xfedcba0987654321", "encryptedKey2");
    }
    
    public void start() {
        // Setup
        blockchain.addObserver(new EmailNotifier());
        user1.setBalance(600.0);
        user2.setBalance(5000.0);
        blockchain.registerUser(user1);
        blockchain.registerUser(user2);
        
        boolean running = true;
        
        while (running) {
            clearScreen();
            displayHeader();
            displayMainMenu();
            
            System.out.print("\n" + BOLD + "Choose option: " + RESET);
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        displayUsersSection();
                        break;
                    case 2:
                        registerNewUser();
                        break;
                    case 3:
                        sendMoneySection();
                        break;
                    case 4:
                        displayExchangeRates();
                        break;
                    case 5:
                        vaultSection();
                        break;
                    case 6:
                        analyticsSection();
                        break;
                    case 7:
                        viewTransactionHistory();
                        break;
                    case 8:
                        toggleDarkMode();
                        break;
                    case 9:
                        running = false;
                        System.out.println("\n" + GREEN + "👋 Thank you for using Blockchain Payment System!" + RESET);
                        break;
                    default:
                        System.out.println(RED + "❌ Invalid option!" + RESET);
                        pause();
                }
            } catch (Exception e) {
                System.out.println(RED + "❌ Error: " + e.getMessage() + RESET);
                pause();
            }
        }
        
        scanner.close();
    }
    
    private void displayHeader() {
        System.out.println(CYAN + "╔" + "═".repeat(60) + "╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + "        💰 BLOCKCHAIN PAYMENT SYSTEM 💰        " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + "    Secure & Fast Cross-Border Transactions    " + "║" + RESET);
        System.out.println(CYAN + "╚" + "═".repeat(60) + "╝" + RESET);
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + BOLD + BLUE + "📋 MAIN MENU" + RESET);
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ 1. 👥  Users                            │");
        System.out.println("│ 2. ➕  Register New User                │");
        System.out.println("│ 3. 💸  Send Money                       │");
        System.out.println("│ 4. 💱  Exchange Rates                   │");
        System.out.println("│ 5. 🏦  Family Vault                     │");
        System.out.println("│ 6. 📊  Analytics                        │");
        System.out.println("│ 7. 📜  Transaction History              │");
        System.out.println("│ 8. 🌙  Toggle Dark Mode                 │");
        System.out.println("│ 9. ❌  Exit                             │");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    private void displayUsersSection() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "👥 USERS" + RESET);
        System.out.println("┌─────────────────────────────────────────┐");
        
        displayUserCard(user1);
        System.out.println("");
        displayUserCard(user2);
        
        System.out.println("└─────────────────────────────────────────┘");
        pause();
    }
    
    private void displayUserCard(User user) {
        String userType = (user instanceof BasicUser) ? "Basic User" : "Premium User";
        String icon = (user instanceof BasicUser) ? "🔹" : "💎";
        
        System.out.println("│ " + icon + " " + BOLD + user.getName() + RESET + " (" + userType + ")");
        System.out.println("│   💰 Balance: $" + String.format("%.2f", user.getBalance()) + " USDT");
        System.out.println("│   📍 Wallet: " + user.getWalletAddress());
        System.out.println("│   📊 Savings Plans: " + user.getSavingsPlans().size());
    }
    
    private void registerNewUser() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "➕ REGISTER NEW USER" + RESET);
        
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        
        System.out.println("\nUser Type:");
        System.out.println("1. Basic User ($500 limit, 1% fee)");
        System.out.println("2. Premium User ($10,000 limit, 0.5% fee)");
        System.out.print("Choose: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Initial Balance (USDT): ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        
        // Generate wallet address
        String walletAddress = "0x" + Integer.toHexString((int)(Math.random() * Integer.MAX_VALUE)).toUpperCase();
        
        User newUser;
        if (typeChoice == 1) {
            newUser = new BasicUser(name, walletAddress, "encryptedKey_" + name);
        } else {
            newUser = new PremiumUser(name, walletAddress, "encryptedKey_" + name);
        }
        
        newUser.setBalance(balance);
        blockchain.registerUser(newUser);
        
        System.out.println("\n" + GREEN + "✅ User Created Successfully!" + RESET);
        System.out.println("   Name: " + name);
        System.out.println("   Type: " + (typeChoice == 1 ? "Basic" : "Premium"));
        System.out.println("   Wallet: " + walletAddress);
        System.out.println("   Balance: $" + String.format("%.2f", balance) + " USDT");
        
        pause();
    }
    
    private void sendMoneySection() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "💸 SEND MONEY" + RESET);
        
        System.out.println("\nSelect Sender:");
        System.out.println("1. " + user1.getName() + " ($" + String.format("%.2f", user1.getBalance()) + " USDT)");
        System.out.println("2. " + user2.getName() + " ($" + String.format("%.2f", user2.getBalance()) + " USDT)");
        System.out.print("Choose: ");
        int senderChoice = scanner.nextInt();
        scanner.nextLine();
        
        User sender = (senderChoice == 1) ? user1 : user2;
        
        System.out.println("\nSelect Recipient:");
        System.out.println("1. " + (senderChoice == 1 ? user2.getName() : user1.getName()));
        System.out.println("2. External Wallet");
        System.out.print("Choose: ");
        int recipientChoice = scanner.nextInt();
        scanner.nextLine();
        
        String recipient;
        if (recipientChoice == 1) {
            recipient = (senderChoice == 1) ? user2.getWalletAddress() : user1.getWalletAddress();
        } else {
            System.out.print("Enter recipient wallet address: ");
            recipient = scanner.nextLine();
        }
        
        System.out.print("Enter amount (USDT): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        try {
            System.out.println("\n" + YELLOW + "⏳ Processing blockchain transaction..." + RESET);
            blockchain.sendMoney(sender, recipient, amount);
            System.out.println(GREEN + "✅ Transaction successful!" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "❌ Transaction failed: " + e.getMessage() + RESET);
        }
        
        pause();
    }
    
    private void displayExchangeRates() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "💱 EXCHANGE RATES" + RESET);
        
        ExchangeRateService rateService = ExchangeRateService.getInstance();
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ USDT/AED: " + String.format("%.4f", rateService.getRate("AED")) + " │");
        System.out.println("│ USDT/EUR: " + String.format("%.4f", rateService.getRate("EUR")) + " │");
        System.out.println("│ USDT/INR: " + String.format("%.4f", rateService.getRate("INR")) + " │");
        System.out.println("│ USDT/PHP: " + String.format("%.4f", rateService.getRate("PHP")) + " │");
        System.out.println("└─────────────────────────────────────────┘");
        
        pause();
    }
    
    private void vaultSection() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "🏦 FAMILY VAULT - SECURE MULTI-SIGNATURE ESCROW" + RESET);
        
        System.out.println("\n1. Create New Vault");
        System.out.println("2. View Vaults");
        System.out.println("3. Back to Menu");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                createVault();
                break;
            case 2:
                viewVaults();
                break;
        }
    }
    
    private void createVault() {
        System.out.print("\nEnter vault name: ");
        String vaultName = scanner.nextLine();
        
        System.out.print("Enter vault purpose: ");
        String purpose = scanner.nextLine();
        
        System.out.print("Enter maximum amount (USDT): ");
        double maxAmount = scanner.nextDouble();
        scanner.nextLine();
        
        // Generate creator address
        String creatorAddress = "0x" + Integer.toHexString((int)(Math.random() * Integer.MAX_VALUE)).toUpperCase();
        
        // Persist vault via VaultService so it is manageable from UI
        VaultService vs = VaultService.getInstance();
        FamilyVault vault = vs.createVault(vaultName, purpose, maxAmount, creatorAddress);
        System.out.println(GREEN + "✅ Vault created and saved: " + vault.getVaultName() + " (ID: " + vault.getVaultId() + ")" + RESET);
        System.out.println("Creator wallet address: " + creatorAddress);
        
        // Ask to add guardians now
        System.out.print("Would you like to add guardians now? (y/n): ");
        String yn = scanner.nextLine().trim().toLowerCase();
        if (yn.equals("y") || yn.equals("yes")) {
            boolean adding = true;
            while (adding) {
                addGuardianToVault(vault);
                System.out.print("Add another guardian? (y/n): ");
                String more = scanner.nextLine().trim().toLowerCase();
                if (!(more.equals("y") || more.equals("yes"))) {
                    adding = false;
                }
            }
        }

        pause();
    }
    
    private void viewVaults() {
        clearScreen();
        displayHeader();
        System.out.println("\n📋 VAULTS");

        VaultService vs = VaultService.getInstance();
        ArrayList<FamilyVault> vaults = vs.getAllVaults();

        if (vaults.isEmpty()) {
            System.out.println("\n⚠️  No vaults yet. Create one first.");
            pause();
            return;
        }

        System.out.println("\nSelect a vault to view/manage:");
        for (int i = 0; i < vaults.size(); i++) {
            System.out.println((i+1) + ". " + vaults.get(i).toString());
        }
        System.out.println((vaults.size()+1) + ". Back");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= vaults.size()) {
            FamilyVault selected = vaults.get(choice-1);
            manageVault(selected);
        }
    }

    private void manageVault(FamilyVault vault) {
        boolean managing = true;
        while (managing) {
            clearScreen();
            displayHeader();
            System.out.println("\n" + BOLD + BLUE + "🏦 VAULT: " + RESET + vault.getVaultName() + " (" + vault.getVaultId() + ")");
            System.out.println(vault.toString());
            System.out.println("\nGuardians:");
            if (vault.getGuardians().isEmpty()) {
                System.out.println(" - (none)");
            } else {
                for (Guardian g : vault.getGuardians()) {
                    System.out.println(" - " + g.toString());
                }
            }

            System.out.println("\nPending Requests: " + vault.getPendingRequests().size());
            System.out.println("\n1. Add Guardian");
            System.out.println("2. Create Withdrawal Request");
            System.out.println("3. View/Approve Pending Requests");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGuardianToVault(vault);
                    break;
                case 2:
                    createWithdrawalRequestUI(vault);
                    break;
                case 3:
                    approveOrRejectRequests(vault);
                    break;
                default:
                    managing = false;
            }
        }
    }

    private void addGuardianToVault(FamilyVault vault) {
        System.out.print("\nGuardian name: ");
        String name = scanner.nextLine();
        System.out.print("Guardian wallet address: ");
        String wallet = scanner.nextLine();
        System.out.print("Guardian role (e.g. Parent, Sibling): ");
        String role = scanner.nextLine();

        Guardian g = new Guardian(name, wallet, role);
        vault.addGuardian(g);
        System.out.println("\n" + GREEN + "✅ Guardian added: " + g.getName() + " (" + g.getWalletAddress() + ")" + RESET);
        pause();
    }

    private void createWithdrawalRequestUI(FamilyVault vault) {
        System.out.print("\nRequester wallet address: ");
        String requester = scanner.nextLine();
        System.out.print("Amount (USDT): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Purpose: ");
        String purpose = scanner.nextLine();
        System.out.print("Proof description (optional): ");
        String proof = scanner.nextLine();

        vault.createWithdrawalRequest(requester, amount, purpose, proof);
        System.out.println("\n" + GREEN + "✅ Withdrawal request submitted." + RESET);
        pause();
    }

    private void approveOrRejectRequests(FamilyVault vault) {
        ArrayList<WithdrawalRequest> pending = vault.getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("\nNo pending requests.");
            pause();
            return;
        }

        System.out.println("\nPending Requests:");
        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i+1) + ". " + pending.get(i).toString());
        }
        System.out.println((pending.size()+1) + ". Back");
        System.out.print("Choose a request: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= pending.size()) {
            WithdrawalRequest req = pending.get(choice-1);
            System.out.print("Enter your guardian wallet address: ");
            String guardianAddr = scanner.nextLine();
            System.out.println("1. Approve\n2. Reject");
            System.out.print("Choose: ");
            int act = scanner.nextInt();
            scanner.nextLine();
            boolean approve = (act == 1);
            boolean result = vault.processApproval(req.getRequestId(), guardianAddr, approve);
            if (result) {
                System.out.println("\n" + GREEN + "Action applied and request processed (may have released funds)." + RESET);
            } else {
                System.out.println("\n" + YELLOW + "Action recorded. Request may still be pending or was rejected by majority." + RESET);
            }
            pause();
        }
    }
    
    private void analyticsSection() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "📊 ANALYTICS" + RESET);
        
        ArrayList<Transaction> transactions = blockchain.getTransactionHistory();
        
        double totalVolume = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        double totalFees = transactions.stream().mapToDouble(Transaction::getFee).sum();
        double avgAmount = transactions.isEmpty() ? 0 : totalVolume / transactions.size();
        
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ 📈 Total Transactions: " + String.format("%20d", transactions.size()) + "│");
        System.out.println("│ 💰 Total Volume: $" + String.format("%-27.2f", totalVolume) + "│");
        System.out.println("│ 💸 Total Fees: $" + String.format("%-29.2f", totalFees) + "│");
        System.out.println("│ 📊 Avg Transaction: $" + String.format("%-27.2f", avgAmount) + "│");
        System.out.println("└─────────────────────────────────────────┘");
        
        pause();
    }
    
    private void viewTransactionHistory() {
        clearScreen();
        displayHeader();
        System.out.println("\n" + BOLD + BLUE + "📜 TRANSACTION HISTORY" + RESET);
        
        ArrayList<Transaction> transactions = blockchain.getTransactionHistory();
        
        if (transactions.isEmpty()) {
            System.out.println("\n⚠️  No transactions yet.");
        } else {
            System.out.println("\n┌─────────────────────────────────────────┐");
            for (Transaction tx : transactions) {
                System.out.println("│ " + tx);
                System.out.println("│");
            }
            System.out.println("└─────────────────────────────────────────┘");
        }
        
        pause();
    }
    
    private void toggleDarkMode() {
        darkMode = !darkMode;
        System.out.println(darkMode ? "🌙 Dark mode enabled" : "☀️  Light mode enabled");
        pause();
    }
    
    private void clearScreen() {
        // Works on Windows 10+ and Unix-based systems
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void pause() {
        System.out.print("\n" + YELLOW + "Press Enter to continue..." + RESET);
        scanner.nextLine();
    }
    
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.start();
    }
}

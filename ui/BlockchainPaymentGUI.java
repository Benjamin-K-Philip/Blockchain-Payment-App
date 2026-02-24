import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * BlockchainPaymentGUI - Main GUI Application for Blockchain Payment System
 * A Swing-based desktop application with tabbed interface
 */
public class BlockchainPaymentGUI extends JFrame {
    
    private JTabbedPane tabbedPane;
    private BlockchainService blockchain;
    private User user1, user2;
    private ArrayList<User> allUsers;
    private JPanel usersPanel;
    private JPanel historyPanel;
    private JPanel analyticsPanel;
    private JPanel vaultsListPanel;
    private JComboBox<String> senderCombo;
    private JComboBox<String> recipientCombo;
    
    // Color scheme (matches the web interface)
    private static final Color PRIMARY_COLOR = new Color(102, 126, 234);
    private static final Color SECONDARY_COLOR = new Color(118, 75, 162);
    private static final Color LIGHT_BG = new Color(245, 245, 250);
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    
    public BlockchainPaymentGUI() {
        setTitle("💰 Blockchain Payment System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Initialize blockchain service
        blockchain = BlockchainService.getInstance();
        blockchain.addObserver(new EmailNotifier());
        
        // Create users
        user1 = new BasicUser("Ahmed", "0x1234567890abcdef", "encryptedKey1");
        user2 = new PremiumUser("Fatima", "0xfedcba0987654321", "encryptedKey2");
        
        user1.setBalance(600.0);
        user2.setBalance(5000.0);
        
        blockchain.registerUser(user1);
        blockchain.registerUser(user2);
        
        allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Add tabs
        usersPanel = createUsersPanel();
        historyPanel = createHistoryPanel();
        analyticsPanel = createAnalyticsPanel();
        
        tabbedPane.addTab("👥 Users", usersPanel);
        tabbedPane.addTab("➕ Register", createRegisterPanel());
        tabbedPane.addTab("💸 Send Money", createSendMoneyPanel());
        tabbedPane.addTab("💱 Rates", createRatesPanel());
        tabbedPane.addTab("🏦 Vault", createVaultPanel());
        tabbedPane.addTab("📊 Analytics", analyticsPanel);
        tabbedPane.addTab("📜 History", historyPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Add header panel
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("💰 Blockchain Payment System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        
        JLabel subtitle = new JLabel("Secure & Fast Cross-Border Transactions");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(220, 220, 220));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(PRIMARY_COLOR);
        textPanel.add(title);
        textPanel.add(subtitle);
        
        header.add(textPanel, BorderLayout.WEST);
        
        return header;
    }
    
    private JPanel createUsersPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel userContainer = new JPanel();
        userContainer.setLayout(new BoxLayout(userContainer, BoxLayout.Y_AXIS));
        userContainer.setBackground(LIGHT_BG);
        userContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add all users
        for (User user : allUsers) {
            JPanel userCard = createUserCard(user);
            userContainer.add(userCard);
            userContainer.add(Box.createVerticalStrut(10));
        }
        
        userContainer.add(Box.createVerticalGlue());
        scrollPane.setViewportView(userContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private void refreshUsersPanel() {
        if (usersPanel == null) return;
        
        usersPanel.removeAll();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel userContainer = new JPanel();
        userContainer.setLayout(new BoxLayout(userContainer, BoxLayout.Y_AXIS));
        userContainer.setBackground(LIGHT_BG);
        userContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        for (User user : allUsers) {
            JPanel userCard = createUserCard(user);
            userContainer.add(userCard);
            userContainer.add(Box.createVerticalStrut(10));
        }
        
        userContainer.add(Box.createVerticalGlue());
        scrollPane.setViewportView(userContainer);
        usersPanel.add(scrollPane, BorderLayout.CENTER);
        usersPanel.revalidate();
        usersPanel.repaint();
    }
    
    private JPanel createUserCard(User user) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        String userType = (user instanceof BasicUser) ? "🔹 Basic User" : "💎 Premium User";
        String limits = (user instanceof BasicUser) ? "$500 limit, 1% fee" : "$10,000 limit, 0.5% fee";
        
        JLabel name = new JLabel(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        name.setForeground(TEXT_COLOR);
        
        JLabel type = new JLabel(userType);
        type.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        type.setForeground(SECONDARY_COLOR);
        
        JLabel balance = new JLabel("💰 Balance: $" + String.format("%.2f", user.getBalance()) + " USDT");
        balance.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balance.setForeground(PRIMARY_COLOR);
        
        JLabel address = new JLabel("📍 Wallet: " + user.getWalletAddress());
        address.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        address.setForeground(new Color(100, 100, 100));
        
        JLabel savings = new JLabel("📊 Savings Plans: " + user.getSavingsPlans().size());
        savings.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        savings.setForeground(new Color(100, 100, 100));
        
        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(type);
        card.add(Box.createVerticalStrut(5));
        card.add(new JLabel("(" + limits + ")"));
        card.add(Box.createVerticalStrut(10));
        card.add(balance);
        card.add(Box.createVerticalStrut(5));
        card.add(address);
        card.add(Box.createVerticalStrut(5));
        card.add(savings);
        
        return card;
    }
    
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_BG);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField nameField = new JTextField(30);
        nameField.setMaximumSize(new Dimension(400, 30));
        
        // Type
        JLabel typeLabel = new JLabel("User Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        String[] types = {"Basic User ($500 limit, 1% fee)", "Premium User ($10,000 limit, 0.5% fee)"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setMaximumSize(new Dimension(400, 30));
        
        // Balance
        JLabel balanceLabel = new JLabel("Initial Balance (USDT):");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField balanceField = new JTextField("1000");
        balanceField.setMaximumSize(new Dimension(400, 30));
        
        // Register button
        JButton registerBtn = new JButton("Register User 👤");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        registerBtn.setBackground(PRIMARY_COLOR);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setMaximumSize(new Dimension(400, 35));
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        registerBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter a name", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                boolean isPremium = typeCombo.getSelectedIndex() == 1;
                double balance = Double.parseDouble(balanceField.getText());
                
                String walletAddress = "0x" + Integer.toHexString((int)(Math.random() * Integer.MAX_VALUE)).toUpperCase();
                
                User newUser = isPremium ? 
                    new PremiumUser(name, walletAddress, "encryptedKey_" + name) :
                    new BasicUser(name, walletAddress, "encryptedKey_" + name);
                
                newUser.setBalance(balance);
                blockchain.registerUser(newUser);
                allUsers.add(newUser);
                
                // Refresh users panel
                refreshUsersPanel();
                
                // Update combo boxes in Send Money tab
                updateSendMoneyComboBoxes();
                
                JOptionPane.showMessageDialog(panel, 
                    "✅ User Created!\n\nName: " + name + "\nWallet: " + walletAddress + 
                    "\nBalance: $" + String.format("%.2f", balance) + " USDT",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                nameField.setText("");
                balanceField.setText("1000");
                typeCombo.setSelectedIndex(0);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid balance amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(nameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(typeLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(typeCombo);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(balanceLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(balanceField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(registerBtn);
        
        panel.add(formPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createSendMoneyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_BG);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Sender
        JLabel senderLabel = new JLabel("From (Sender):");
        senderLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        senderCombo = new JComboBox<>();
        senderCombo.setMaximumSize(new Dimension(400, 30));
        
        for (User user : allUsers) {
            senderCombo.addItem(user.getName() + " (" + user.getWalletAddress() + ")");
        }
        
        // Recipient
        JLabel recipientLabel = new JLabel("To (Recipient):");
        recipientLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        recipientCombo = new JComboBox<>();
        recipientCombo.setMaximumSize(new Dimension(400, 30));
        
        for (User user : allUsers) {
            recipientCombo.addItem(user.getName() + " (" + user.getWalletAddress() + ")");
        }
        
        // Amount
        JLabel amountLabel = new JLabel("Amount (USDT):");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(400, 30));
        
        // Send button
        JButton sendBtn = new JButton("💰 Send Money");
        sendBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sendBtn.setBackground(PRIMARY_COLOR);
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setMaximumSize(new Dimension(400, 35));
        sendBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        sendBtn.addActionListener(e -> {
            try {
                User sender = allUsers.get(senderCombo.getSelectedIndex());
                User recipient = allUsers.get(recipientCombo.getSelectedIndex());
                double amount = Double.parseDouble(amountField.getText());
                
                if (sender == recipient) {
                    JOptionPane.showMessageDialog(panel, "Cannot send to yourself!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                blockchain.sendMoney(sender, recipient.getWalletAddress(), amount);
                
                // Refresh all panels
                refreshHistoryPanel();
                refreshUsersPanel();
                refreshAnalyticsPanel();
                
                JOptionPane.showMessageDialog(panel, 
                    "✅ Transaction Successful!\n\nFrom: " + sender.getName() + 
                    "\nTo: " + recipient.getName() + 
                    "\nAmount: $" + String.format("%.2f", amount) + " USDT",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                amountField.setText("");
                
            } catch (InsufficientBalanceException | InvalidAddressException | TransactionLimitExceededException ex) {
                JOptionPane.showMessageDialog(panel, "❌ Error: " + ex.getMessage(), "Transaction Failed", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(senderLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(senderCombo);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(recipientLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(recipientCombo);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(amountLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(amountField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(sendBtn);
        
        panel.add(formPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createRatesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_BG);
        
        ExchangeRateService rates = ExchangeRateService.getInstance();
        
        String[] currencies = {"AED", "SAR", "INR", "PHP", "PKR", "EUR"};
        
        for (String currency : currencies) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(PRIMARY_COLOR);
            card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel currencyLabel = new JLabel("USDT → " + currency);
            currencyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            currencyLabel.setForeground(Color.WHITE);
            
            JLabel rateLabel = new JLabel(String.format("%.4f", rates.getRate(currency)));
            rateLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            rateLabel.setForeground(Color.WHITE);
            
            card.add(currencyLabel);
            card.add(Box.createVerticalStrut(10));
            card.add(rateLabel);
            
            panel.add(card);
        }
        
        return panel;
    }
    
    private JPanel createVaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_BG);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel vaultNameLabel = new JLabel("Vault Name:");
        vaultNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField vaultNameField = new JTextField(30);
        vaultNameField.setMaximumSize(new Dimension(400, 30));
        
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField purposeField = new JTextField(30);
        purposeField.setMaximumSize(new Dimension(400, 30));
        
        JLabel amountLabel = new JLabel("Total Amount (USDT):");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JTextField amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(400, 30));
        
        JButton createBtn = new JButton("🔐 Create Vault");
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        createBtn.setBackground(PRIMARY_COLOR);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setMaximumSize(new Dimension(400, 35));
        createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        createBtn.addActionListener(e -> {
            try {
                String vaultName = vaultNameField.getText().trim();
                String purpose = purposeField.getText().trim();
                double amount = Double.parseDouble(amountField.getText());

                String creatorAddress = "0x" + Integer.toHexString((int)(Math.random() * Integer.MAX_VALUE)).toUpperCase();

                // Persist vault through VaultService
                VaultService vs = VaultService.getInstance();
                FamilyVault vault = vs.createVault(vaultName, purpose, amount, creatorAddress);

                JOptionPane.showMessageDialog(panel, 
                    "✅ Vault Created Successfully!\n\nName: " + vaultName + 
                    "\nPurpose: " + purpose + 
                    "\nAmount: $" + String.format("%.2f", amount) + " USDT\nVault ID: " + vault.getVaultId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

                vaultNameField.setText("");
                purposeField.setText("");
                amountField.setText("");

                // Refresh vaults list
                refreshVaultsPanel();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(new JLabel("🏦 Create New Vault"));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(vaultNameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(vaultNameField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(purposeLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(purposeField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(amountLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(amountField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createBtn);
        
        panel.add(formPanel, BorderLayout.WEST);

        // Right side: list of vaults and management
        vaultsListPanel = new JPanel();
        vaultsListPanel.setLayout(new BoxLayout(vaultsListPanel, BoxLayout.Y_AXIS));
        vaultsListPanel.setBackground(Color.WHITE);
        vaultsListPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane vaultsScroll = new JScrollPane(vaultsListPanel);
        vaultsScroll.setPreferredSize(new Dimension(500, 0));
        panel.add(vaultsScroll, BorderLayout.CENTER);

        // Initial population
        refreshVaultsPanel();
        
        return panel;
    }

    private void refreshVaultsPanel() {
        if (vaultsListPanel == null) return;

        vaultsListPanel.removeAll();

        VaultService vs = VaultService.getInstance();
        ArrayList<FamilyVault> vaults = vs.getAllVaults();

        if (vaults.isEmpty()) {
            JLabel empty = new JLabel("No vaults created yet.");
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            empty.setForeground(TEXT_COLOR);
            vaultsListPanel.add(empty);
        } else {
            for (FamilyVault v : vaults) {
                JPanel card = new JPanel();
                card.setLayout(new BorderLayout());
                card.setBackground(LIGHT_BG);
                card.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

                JLabel title = new JLabel(v.getVaultName() + " (" + v.getVaultId() + ")");
                title.setFont(new Font("Segoe UI", Font.BOLD, 14));
                title.setForeground(TEXT_COLOR);

                JTextArea desc = new JTextArea(v.toString());
                desc.setEditable(false);
                desc.setBackground(LIGHT_BG);

                JButton manageBtn = new JButton("Manage");
                manageBtn.setBackground(PRIMARY_COLOR);
                manageBtn.setForeground(Color.WHITE);
                manageBtn.addActionListener(ae -> showManageVaultDialog(v));

                card.add(title, BorderLayout.NORTH);
                card.add(desc, BorderLayout.CENTER);
                card.add(manageBtn, BorderLayout.EAST);

                vaultsListPanel.add(card);
                vaultsListPanel.add(Box.createVerticalStrut(8));
            }
        }

        vaultsListPanel.revalidate();
        vaultsListPanel.repaint();
    }

    private void showManageVaultDialog(FamilyVault vault) {
        JDialog dialog = new JDialog(this, "Manage Vault: " + vault.getVaultName(), true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new BorderLayout());

        // Top: basic info
        JTextArea info = new JTextArea(vault.toString());
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        content.add(info, BorderLayout.NORTH);

        // Center: Tabs for Guardians, Requests
        JTabbedPane tabbed = new JTabbedPane();

        // Guardians panel
        JPanel guardiansPanel = new JPanel();
        guardiansPanel.setLayout(new BoxLayout(guardiansPanel, BoxLayout.Y_AXIS));
        guardiansPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        DefaultListModel<String> guardModel = new DefaultListModel<>();
        for (Guardian g : vault.getGuardians()) {
            guardModel.addElement(g.toString() + " - " + g.getWalletAddress());
        }
        JList<String> guardList = new JList<>(guardModel);
        JScrollPane guardScroll = new JScrollPane(guardList);
        guardiansPanel.add(guardScroll);

        JPanel addGuardForm = new JPanel();
        addGuardForm.setLayout(new GridLayout(4,2,8,8));
        JTextField gName = new JTextField();
        JTextField gWallet = new JTextField();
        JTextField gRole = new JTextField();
        JButton addGBtn = new JButton("Add Guardian");
        addGBtn.setBackground(PRIMARY_COLOR);
        addGBtn.setForeground(Color.WHITE);
        addGBtn.addActionListener(a -> {
            String name = gName.getText().trim();
            String wallet = gWallet.getText().trim();
            String role = gRole.getText().trim();
            if (name.isEmpty() || wallet.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name and wallet required", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Guardian ng = new Guardian(name, wallet, role.isEmpty()?"Guardian":role);
            vault.addGuardian(ng);
            guardModel.addElement(ng.toString() + " - " + ng.getWalletAddress());
            gName.setText(""); gWallet.setText(""); gRole.setText("");
            refreshVaultsPanel();
        });

        addGuardForm.add(new JLabel("Name:")); addGuardForm.add(gName);
        addGuardForm.add(new JLabel("Wallet Address:")); addGuardForm.add(gWallet);
        addGuardForm.add(new JLabel("Role:")); addGuardForm.add(gRole);
        addGuardForm.add(new JLabel()); addGuardForm.add(addGBtn);

        guardiansPanel.add(Box.createVerticalStrut(8));
        guardiansPanel.add(addGuardForm);

        tabbed.addTab("Guardians", guardiansPanel);

        // Requests panel
        JPanel reqPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> reqModel = new DefaultListModel<>();
        for (WithdrawalRequest r : vault.getRequests()) {
            reqModel.addElement(r.toString());
        }
        JList<String> reqList = new JList<>(reqModel);
        JScrollPane reqScroll = new JScrollPane(reqList);
        reqPanel.add(reqScroll, BorderLayout.CENTER);

        JPanel reqActions = new JPanel(new GridLayout(3,2,8,8));
        JTextField requesterField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField purposeField = new JTextField();
        JButton submitReqBtn = new JButton("Submit Withdrawal Request");
        submitReqBtn.setBackground(PRIMARY_COLOR);
        submitReqBtn.setForeground(Color.WHITE);
        submitReqBtn.addActionListener(a -> {
            try {
                String requester = requesterField.getText().trim();
                double amt = Double.parseDouble(amountField.getText().trim());
                String purp = purposeField.getText().trim();
                vault.createWithdrawalRequest(requester, amt, purp, "(from GUI)");
                reqModel.addElement(vault.getRequests().get(vault.getRequests().size()-1).toString());
                requesterField.setText(""); amountField.setText(""); purposeField.setText("");
                refreshVaultsPanel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        reqActions.add(new JLabel("Requester Wallet:")); reqActions.add(requesterField);
        reqActions.add(new JLabel("Amount:")); reqActions.add(amountField);
        reqActions.add(new JLabel("Purpose:")); reqActions.add(purposeField);

        JPanel bottomReq = new JPanel(new BorderLayout());
        bottomReq.add(reqActions, BorderLayout.CENTER);
        bottomReq.add(submitReqBtn, BorderLayout.SOUTH);

        // Approve/Reject area
        JPanel approvePanel = new JPanel(new GridLayout(2,2,8,8));
        JTextField guardianAddrField = new JTextField();
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        approveBtn.setBackground(new Color(34,139,34)); approveBtn.setForeground(Color.WHITE);
        rejectBtn.setBackground(new Color(178,34,34)); rejectBtn.setForeground(Color.WHITE);

        approveBtn.addActionListener(a -> {
            int idx = reqList.getSelectedIndex();
            if (idx < 0) { JOptionPane.showMessageDialog(dialog, "Select a request first", "Info", JOptionPane.INFORMATION_MESSAGE); return; }
            WithdrawalRequest r = vault.getRequests().get(idx);
            boolean ok = vault.processApproval(r.getRequestId(), guardianAddrField.getText().trim(), true);
            JOptionPane.showMessageDialog(dialog, ok?"Request processed":"Action recorded (may still be pending)");
            // refresh models
            reqModel.set(idx, r.toString());
            info.setText(vault.toString());
            refreshVaultsPanel();
        });

        rejectBtn.addActionListener(a -> {
            int idx = reqList.getSelectedIndex();
            if (idx < 0) { JOptionPane.showMessageDialog(dialog, "Select a request first", "Info", JOptionPane.INFORMATION_MESSAGE); return; }
            WithdrawalRequest r = vault.getRequests().get(idx);
            boolean ok = vault.processApproval(r.getRequestId(), guardianAddrField.getText().trim(), false);
            JOptionPane.showMessageDialog(dialog, ok?"Request processed":"Action recorded (may still be pending)");
            reqModel.set(idx, r.toString());
            info.setText(vault.toString());
            refreshVaultsPanel();
        });

        approvePanel.add(new JLabel("Guardian wallet to act as:")); approvePanel.add(guardianAddrField);
        approvePanel.add(approveBtn); approvePanel.add(rejectBtn);

        JPanel rightReq = new JPanel(new BorderLayout());
        rightReq.add(bottomReq, BorderLayout.NORTH);
        rightReq.add(approvePanel, BorderLayout.SOUTH);

        reqPanel.add(rightReq, BorderLayout.SOUTH);

        tabbed.addTab("Requests", reqPanel);

        content.add(tabbed, BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(a -> dialog.dispose());
        content.add(close, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.setVisible(true);
    }
    
    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(LIGHT_BG);
        
        updateAnalyticsPanelContent(panel);
        
        return panel;
    }
    
    private void updateAnalyticsPanelContent(JPanel panel) {
        panel.removeAll();
        
        ArrayList<Transaction> transactions = blockchain.getTransactionHistory();
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        
        double totalVolume = 0;
        double totalFees = 0;
        
        for (Transaction tx : transactions) {
            totalVolume += tx.getAmount();
            totalFees += tx.getFee();
        }
        
        double avgAmount = transactions.isEmpty() ? 0 : totalVolume / transactions.size();
        
        statsPanel.add(createStatCard("📈 Total Transactions", String.valueOf(transactions.size())));
        statsPanel.add(createStatCard("💰 Total Volume", "$" + String.format("%.2f", totalVolume)));
        statsPanel.add(createStatCard("📊 Average Amount", "$" + String.format("%.2f", avgAmount)));
        statsPanel.add(createStatCard("💸 Total Fees", "$" + String.format("%.2f", totalFees)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
    
    private void refreshAnalyticsPanel() {
        if (analyticsPanel == null) return;
        updateAnalyticsPanelContent(analyticsPanel);
    }
    
    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PRIMARY_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelComponent.setForeground(new Color(200, 200, 220));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueComponent.setForeground(Color.WHITE);
        
        card.add(labelComponent);
        card.add(Box.createVerticalStrut(10));
        card.add(valueComponent);
        
        return card;
    }
    
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(LIGHT_BG);
        
        updateHistoryPanelContent(panel);
        
        return panel;
    }
    
    private void updateHistoryPanelContent(JPanel panel) {
        panel.removeAll();
        
        ArrayList<Transaction> transactions = blockchain.getTransactionHistory();
        
        if (transactions.isEmpty()) {
            JLabel noData = new JLabel("No transactions yet");
            noData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noData.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noData, BorderLayout.CENTER);
        } else {
            String[] columns = {"ID", "Sender", "Recipient", "Amount", "Fee", "Status", "Date"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            
            for (Transaction tx : transactions) {
                String senderAddr = tx.getSenderAddress() == null ? "" : tx.getSenderAddress();
                String recipientAddr = tx.getRecipientAddress() == null ? "" : tx.getRecipientAddress();
                String senderShort = senderAddr.length() <= 10 ? senderAddr : senderAddr.substring(0, 10) + "...";
                String recipientShort = recipientAddr.length() <= 10 ? recipientAddr : recipientAddr.substring(0, 10) + "...";

                model.addRow(new Object[]{
                    tx.getTransactionId(),
                    senderShort,
                    recipientShort,
                    "$" + String.format("%.2f", tx.getAmount()),
                    "$" + String.format("%.2f", tx.getFee()),
                    tx.getStatus(),
                    tx.getFormattedTimestamp()
                });
            }
            
            JTable table = new JTable(model);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            table.getTableHeader().setBackground(PRIMARY_COLOR);
            table.getTableHeader().setForeground(Color.WHITE);
            table.setSelectionBackground(PRIMARY_COLOR);
            
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        panel.revalidate();
        panel.repaint();
    }
    
    private void refreshHistoryPanel() {
        if (historyPanel == null) return;
        updateHistoryPanelContent(historyPanel);
    }
    
    private void updateSendMoneyComboBoxes() {
        if (senderCombo == null || recipientCombo == null) return;
        
        senderCombo.removeAllItems();
        recipientCombo.removeAllItems();
        
        for (User user : allUsers) {
            senderCombo.addItem(user.getName() + " (" + user.getWalletAddress() + ")");
            recipientCombo.addItem(user.getName() + " (" + user.getWalletAddress() + ")");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlockchainPaymentGUI());
    }
}

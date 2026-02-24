import java.util.ArrayList;
import java.util.HashMap;

public class VaultService {
    private static VaultService instance = null;
    private HashMap<String, FamilyVault> vaults;
    
    private VaultService() {
        vaults = new HashMap<>();
        System.out.println("✅ VaultService initialized (Singleton)");
    }
    
    public static VaultService getInstance() {
        if (instance == null) {
            instance = new VaultService();
        }
        return instance;
    }
    
    public FamilyVault createVault(String vaultName, String purpose, double totalAmount, String creatorAddress) {
        FamilyVault vault = new FamilyVault(vaultName, purpose, totalAmount, creatorAddress);
        vaults.put(vault.getVaultId(), vault);
        System.out.println("🏦 Vault created: " + vaultName);
        return vault;
    }
    
    public FamilyVault getVault(String vaultId) {
        return vaults.get(vaultId);
    }
    
    public ArrayList<FamilyVault> getUserVaults(String userAddress) {
        ArrayList<FamilyVault> userVaults = new ArrayList<>();
        for (FamilyVault vault : vaults.values()) {
            if (vault.getCreatorAddress().equals(userAddress)) {
                userVaults.add(vault);
            } else {
                // Check if user is a guardian
                for (Guardian g : vault.getGuardians()) {
                    if (g.getWalletAddress().equals(userAddress)) {
                        userVaults.add(vault);
                        break;
                    }
                }
            }
        }
        return userVaults;
    }
    
    public ArrayList<FamilyVault> getAllVaults() {
        return new ArrayList<>(vaults.values());
    }
    
    public int getTotalVaults() {
        return vaults.size();
    }
    
    public double getTotalSecuredAmount() {
        double total = 0;
        for (FamilyVault vault : vaults.values()) {
            total += vault.getRemainingBalance();
        }
        return total;
    }
}

public class Guardian {
    private String name;
    private String walletAddress;
    private String role;
    private boolean isActive;
    
    public Guardian(String name, String walletAddress, String role) {
        this.name = name;
        this.walletAddress = walletAddress;
        this.role = role;
        this.isActive = true;
    }
    
    public String getName() { return name; }
    public String getWalletAddress() { return walletAddress; }
    public String getRole() { return role; }
    public boolean isActive() { return isActive; }
    
    public void deactivate() { this.isActive = false; }
    
    @Override
    public String toString() {
        return String.format("👤 %s (%s) - %s", name, role, isActive ? "Active" : "Inactive");
    }
}

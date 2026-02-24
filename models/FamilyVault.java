import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FamilyVault {
    private String vaultId;
    private String vaultName;
    private String purpose;
    private double totalAmount;
    private double releasedAmount;
    private String creatorAddress;
    private ArrayList<Guardian> guardians;
    private ArrayList<WithdrawalRequest> requests;
    private VaultStatus status;
    private LocalDateTime createdDate;
    private static int vaultCounter = 1000;
    
    public enum VaultStatus {
        ACTIVE, LOCKED, DISPUTED, COMPLETED
    }
    
    public FamilyVault(String vaultName, String purpose, double totalAmount, String creatorAddress) {
        this.vaultId = "VAULT" + (++vaultCounter);
        this.vaultName = vaultName;
        this.purpose = purpose;
        this.totalAmount = totalAmount;
        this.releasedAmount = 0.0;
        this.creatorAddress = creatorAddress;
        this.guardians = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.status = VaultStatus.ACTIVE;
        this.createdDate = LocalDateTime.now();
    }
    
    public void addGuardian(Guardian guardian) {
        guardians.add(guardian);
        System.out.println("✅ Guardian added: " + guardian.getName());
    }
    
    public double getRemainingBalance() {
        return totalAmount - releasedAmount;
    }
    
    public double getProgressPercentage() {
        return (releasedAmount / totalAmount) * 100;
    }
    
    public void createWithdrawalRequest(String requesterAddress, double amount, String purpose, String proofDescription) {
        WithdrawalRequest request = new WithdrawalRequest(
            vaultId, requesterAddress, amount, purpose, proofDescription
        );
        requests.add(request);
        notifyGuardians(request);
    }
    
    private void notifyGuardians(WithdrawalRequest request) {
        System.out.println("\n🔔 NOTIFICATION TO ALL GUARDIANS:");
        System.out.println("New withdrawal request in vault: " + vaultName);
        System.out.println("Amount: $" + request.getAmount());
        System.out.println("Purpose: " + request.getPurpose());
        for (Guardian guardian : guardians) {
            System.out.println("📧 Notified: " + guardian.getName());
        }
    }
    
    public boolean processApproval(String requestId, String guardianAddress, boolean approve) {
        WithdrawalRequest request = findRequest(requestId);
        if (request == null) return false;
        
        Guardian guardian = findGuardian(guardianAddress);
        if (guardian == null) return false;
        
        if (approve) {
            request.addApproval(guardianAddress);
            System.out.println("✅ " + guardian.getName() + " APPROVED request " + requestId);
        } else {
            request.addRejection(guardianAddress);
            System.out.println("❌ " + guardian.getName() + " REJECTED request " + requestId);
        }
        
        // Check if enough approvals
        int requiredApprovals = (guardians.size() + 1) / 2; // Majority
        
        if (request.getApprovalCount() >= requiredApprovals) {
            processRelease(request);
            return true;
        } else if (request.getRejectionCount() >= requiredApprovals) {
            request.setStatus(WithdrawalRequest.RequestStatus.REJECTED);
            System.out.println("❌ Request REJECTED by majority");
            return false;
        }
        
        return false;
    }
    
    private void processRelease(WithdrawalRequest request) {
        if (getRemainingBalance() >= request.getAmount()) {
            releasedAmount += request.getAmount();
            request.setStatus(WithdrawalRequest.RequestStatus.APPROVED);
            System.out.println("\n💰 FUNDS RELEASED!");
            System.out.println("Amount: $" + request.getAmount());
            System.out.println("Purpose: " + request.getPurpose());
            System.out.println("Remaining vault balance: $" + getRemainingBalance());
        } else {
            System.out.println("❌ Insufficient vault balance!");
        }
    }
    
    private WithdrawalRequest findRequest(String requestId) {
        for (WithdrawalRequest req : requests) {
            if (req.getRequestId().equals(requestId)) {
                return req;
            }
        }
        return null;
    }
    
    private Guardian findGuardian(String address) {
        for (Guardian g : guardians) {
            if (g.getWalletAddress().equals(address)) {
                return g;
            }
        }
        return null;
    }
    
    // Getters
    public String getVaultId() { return vaultId; }
    public String getVaultName() { return vaultName; }
    public String getPurpose() { return purpose; }
    public double getTotalAmount() { return totalAmount; }
    public double getReleasedAmount() { return releasedAmount; }
    public String getCreatorAddress() { return creatorAddress; }
    public ArrayList<Guardian> getGuardians() { return guardians; }
    public ArrayList<WithdrawalRequest> getRequests() { return requests; }
    public VaultStatus getStatus() { return status; }
    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdDate.format(formatter);
    }
    
    public ArrayList<WithdrawalRequest> getPendingRequests() {
        ArrayList<WithdrawalRequest> pending = new ArrayList<>();
        for (WithdrawalRequest req : requests) {
            if (req.getStatus() == WithdrawalRequest.RequestStatus.PENDING) {
                pending.add(req);
            }
        }
        return pending;
    }
    
    @Override
    public String toString() {
        return String.format("🏦 %s | Purpose: %s | Balance: $%.2f / $%.2f (%.1f%%) | Guardians: %d | Status: %s",
            vaultName, purpose, getRemainingBalance(), totalAmount, getProgressPercentage(), 
            guardians.size(), status);
    }
}

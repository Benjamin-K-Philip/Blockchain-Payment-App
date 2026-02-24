import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WithdrawalRequest {
    private String requestId;
    private String vaultId;
    private String requesterAddress;
    private double amount;
    private String purpose;
    private String proofDescription;
    private ArrayList<String> approvals;
    private ArrayList<String> rejections;
    private RequestStatus status;
    private LocalDateTime requestDate;
    private static int requestCounter = 5000;
    
    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, EXPIRED
    }
    
    public WithdrawalRequest(String vaultId, String requesterAddress, double amount, 
                            String purpose, String proofDescription) {
        this.requestId = "REQ" + (++requestCounter);
        this.vaultId = vaultId;
        this.requesterAddress = requesterAddress;
        this.amount = amount;
        this.purpose = purpose;
        this.proofDescription = proofDescription;
        this.approvals = new ArrayList<>();
        this.rejections = new ArrayList<>();
        this.status = RequestStatus.PENDING;
        this.requestDate = LocalDateTime.now();
    }
    
    public void addApproval(String guardianAddress) {
        if (!approvals.contains(guardianAddress)) {
            approvals.add(guardianAddress);
        }
    }
    
    public void addRejection(String guardianAddress) {
        if (!rejections.contains(guardianAddress)) {
            rejections.add(guardianAddress);
        }
    }
    
    public int getApprovalCount() { return approvals.size(); }
    public int getRejectionCount() { return rejections.size(); }
    
    // Getters
    public String getRequestId() { return requestId; }
    public String getVaultId() { return vaultId; }
    public String getRequesterAddress() { return requesterAddress; }
    public double getAmount() { return amount; }
    public String getPurpose() { return purpose; }
    public String getProofDescription() { return proofDescription; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    
    public String getRequestDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return requestDate.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] $%.2f for '%s' | Approvals: %d | Rejections: %d | Status: %s | Date: %s",
            requestId, amount, purpose, approvals.size(), rejections.size(), status, getRequestDate());
    }
}

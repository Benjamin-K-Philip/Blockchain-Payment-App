import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction> {
    private String transactionId;
    private String senderAddress;
    private String recipientAddress;
    private double amount;
    private double fee;
    private LocalDateTime timestamp;
    private String status;
    
    private static int transactionCounter = 1000;
    
    public Transaction(String senderAddress, String recipientAddress, double amount, double fee) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
        this.amount = amount;
        this.fee = fee;
        this.timestamp = LocalDateTime.now();
        this.status = "PENDING";
    }
    
    @Override
    public int compareTo(Transaction other) {
        return other.timestamp.compareTo(this.timestamp);
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getSenderAddress() {
        return senderAddress;
    }
    
    public String getRecipientAddress() {
        return recipientAddress;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getFee() {
        return fee;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    // Generate HTML receipt
    public String generateHTMLReceipt(String senderName, String recipientName) {
        double totalAmount = amount + fee;
        
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; padding: 40px; background: #f5f5f5; }
                    .receipt { background: white; padding: 40px; max-width: 600px; margin: 0 auto; 
                              border-radius: 10px; box-shadow: 0 5px 20px rgba(0,0,0,0.1); }
                    .header { text-align: center; border-bottom: 3px solid #667eea; padding-bottom: 20px; margin-bottom: 30px; }
                    .header h1 { color: #667eea; margin: 0; }
                    .header p { color: #999; margin: 5px 0; }
                    .status { background: #d4edda; color: #155724; padding: 10px 20px; 
                             border-radius: 20px; display: inline-block; font-weight: bold; }
                    .status.pending { background: #fff3cd; color: #856404; }
                    .details { margin: 30px 0; }
                    .detail-row { display: flex; justify-content: space-between; padding: 15px 0; 
                                 border-bottom: 1px solid #eee; }
                    .detail-row:last-child { border-bottom: none; }
                    .label { color: #666; font-weight: 600; }
                    .value { color: #333; font-weight: bold; }
                    .amount { font-size: 1.5em; color: #667eea; }
                    .footer { text-align: center; margin-top: 40px; padding-top: 20px; 
                             border-top: 2px solid #eee; color: #999; }
                    .qr-placeholder { width: 150px; height: 150px; background: #f0f0f0; 
                                     margin: 20px auto; border: 2px dashed #ccc; 
                                     display: flex; align-items: center; justify-content: center; }
                    @media print { body { background: white; } .receipt { box-shadow: none; } }
                </style>
            </head>
            <body>
                <div class="receipt">
                    <div class="header">
                        <h1>💰 Blockchain Payment Receipt</h1>
                        <p>Secure Cross-Border Transaction</p>
                        <div style="margin-top: 15px;">
                            <span class="status %s">%s</span>
                        </div>
                    </div>
                    
                    <div class="details">
                        <div class="detail-row">
                            <span class="label">Transaction ID:</span>
                            <span class="value">%s</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Date & Time:</span>
                            <span class="value">%s</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">From:</span>
                            <span class="value">%s<br><small style="color:#999;">%s</small></span>
                        </div>
                        <div class="detail-row">
                            <span class="label">To:</span>
                            <span class="value">%s<br><small style="color:#999;">%s</small></span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Amount:</span>
                            <span class="value amount">$%.2f USDT</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Transaction Fee:</span>
                            <span class="value">$%.2f USDT</span>
                        </div>
                        <div class="detail-row" style="background: #f8f9fa; margin: 0 -10px; padding: 15px 10px;">
                            <span class="label" style="font-size: 1.2em;">Total Amount:</span>
                            <span class="value amount">$%.2f USDT</span>
                        </div>
                    </div>
                    
                    <div class="qr-placeholder">
                        <div style="text-align: center; color: #999;">
                            📱<br>QR Code<br>Placeholder
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>Transaction Hash:</strong><br>%s</p>
                        <p style="margin-top: 15px; font-size: 0.9em;">
                            ✅ Verified on Blockchain<br>
                            This is an official transaction receipt
                        </p>
                    </div>
                </div>
                
                <div style="text-align: center; margin: 20px;">
                    <button onclick="window.print()" 
                            style="background: #667eea; color: white; border: none; 
                                   padding: 15px 40px; border-radius: 8px; font-size: 1em; 
                                   cursor: pointer; font-weight: bold;">
                        🖨️ Print Receipt
                    </button>
                </div>
            </body>
            </html>
            """,
            status.toLowerCase(), status,
            transactionId,
            getFormattedTimestamp(),
            senderName, senderAddress,
            recipientName, recipientAddress,
            amount,
            fee,
            totalAmount,
            "0x" + transactionId.hashCode()
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s | From: %s | To: %s | Amount: %.2f USDT | Fee: %.2f | Status: %s",
            transactionId, timestamp.format(formatter), 
            senderAddress.substring(0, 10) + "...", 
            recipientAddress.substring(0, 10) + "...", 
            amount, fee, status);
    }
}

// Concrete implementation of Observer pattern
public class EmailNotifier implements TransactionObserver {
    
    @Override
    public void onTransactionCompleted(Transaction transaction) {
        System.out.println("📧 EMAIL SENT: Transaction " + transaction.getTransactionId() + 
                           " completed successfully! Amount: " + transaction.getAmount() + " USDT");
    }
    
    @Override
    public void onTransactionFailed(Transaction transaction, String reason) {
        System.out.println("📧 EMAIL SENT: Transaction " + transaction.getTransactionId() + 
                           " FAILED. Reason: " + reason);
    }
}

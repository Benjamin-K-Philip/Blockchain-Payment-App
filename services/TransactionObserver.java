// Observer interface - design pattern
public interface TransactionObserver {
    void onTransactionCompleted(Transaction transaction);
    void onTransactionFailed(Transaction transaction, String reason);
}

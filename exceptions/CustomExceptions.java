// Custom exception for insufficient balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Custom exception for invalid wallet address
class InvalidAddressException extends Exception {
    public InvalidAddressException(String message) {
        super(message);
    }
}

// Custom exception for exceeding transaction limit
class TransactionLimitExceededException extends Exception {
    public TransactionLimitExceededException(String message) {
        super(message);
    }
}

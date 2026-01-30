/**
 * Custom Checked Exception for invalid bank transactions
 * Extends Exception (checked) to force handling
 */
public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message) {
        super(message);
    }
}
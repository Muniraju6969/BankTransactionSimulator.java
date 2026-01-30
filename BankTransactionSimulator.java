import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Bank Transaction Simulator - Demonstrates Exception Handling & Custom Exceptions
 * Covers all hints:
 * 1-2. Runtime exception handling with try-catch
 * 3. Finally block for cleanup
 * 4. Custom exception class
 * 5. Manual throwing
 * 6. Checked (custom) vs unchecked (runtime)
 * 7. Meaningful messages
 * 8. Logging via console (can extend to file/logger)
 */
public class BankTransactionSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null; // Will initialize later

        System.out.println("=== Bank Transaction Simulator ===\n");

        // Create account with initial balance
        try {
            System.out.print("Enter account number: ");
            String accNum = scanner.nextLine();
            System.out.print("Enter initial balance: ");
            double initBalance = scanner.nextDouble();
            scanner.nextLine(); // clear newline

            if (initBalance < 0) {
                throw new InvalidTransactionException("Initial balance cannot be negative!");
            }
            account = new BankAccount(accNum, initBalance);
            System.out.println("Account created successfully! Balance: ₹" + account.getBalance());
        } catch (InvalidTransactionException e) {
            System.out.println("Custom Exception: " + e.getMessage());
            System.out.println("Cannot create account. Exiting...");
            scanner.close();
            return;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Balance must be a number.");
            scanner.close();
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");

            System.out.print("Choose operation (1-4): ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                case 2:
                    System.out.print("Enter amount: ₹");
                    double amount;
                    try {
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount! Must be a number.");
                        scanner.nextLine();
                        continue;
                    }

                    try {
                        if (choice == 1) {
                            account.deposit(amount); // May throw custom exception
                        } else {
                            account.withdraw(amount); // May throw custom exception
                        }
                    } catch (InvalidTransactionException e) {
                        // Custom checked exception handling
                        System.out.println("Transaction Failed: " + e.getMessage());
                    } catch (Exception e) {
                        // Catch any unexpected runtime exceptions
                        System.out.println("Unexpected error during transaction: " + e.getMessage());
                    } finally {
                        // Cleanup / logging in finally (always executes)
                        System.out.println("[LOG] Transaction attempt completed. Current balance: ₹" + account.getBalance());
                    }
                    break;

                case 3:
                    System.out.println("Current Balance: ₹" + account.getBalance());
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        System.out.println("\nThank you for using the simulator. Goodbye!");
        scanner.close();
    }
}
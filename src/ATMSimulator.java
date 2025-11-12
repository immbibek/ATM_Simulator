import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Bank Account Class
class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;
    private String accountHolderName;

    public BankAccount(String accountNumber, String pin, double balance, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        return true;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }
        balance += amount;
    }

    public void changePin(String newPin) {
        this.pin = newPin;
    }
}

// ATM Class
class ATM {
    private Map<String, BankAccount> accounts;
    private BankAccount currentAccount;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeAccounts();
    }

    private void initializeAccounts() {
        // Pre-loaded demo accounts
        accounts.put("1234567890", new BankAccount("1234567890", "1234", 5000.00, "John Doe"));
        accounts.put("0987654321", new BankAccount("0987654321", "5678", 10000.00, "Jane Smith"));
        accounts.put("1111222233", new BankAccount("1111222233", "9999", 2500.50, "Alice Johnson"));
    }

    public void start() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   WELCOME TO ABC BANK ATM          ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        if (login()) {
            showMainMenu();
        }
    }

    private boolean login() {
        System.out.println("Please login to continue");
        System.out.println("Demo Accounts:");
        System.out.println("  Account: 1234567890, PIN: 1234");
        System.out.println("  Account: 0987654321, PIN: 5678");
        System.out.println("  Account: 1111222233, PIN: 9999");
        System.out.println();

        for (int attempts = 3; attempts > 0; attempts--) {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            BankAccount account = accounts.get(accountNumber);

            if (account != null && account.validatePin(pin)) {
                currentAccount = account;
                System.out.println("\n✓ Login Successful!");
                System.out.println("Welcome, " + currentAccount.getAccountHolderName() + "!\n");
                return true;
            } else {
                System.out.println("✗ Invalid account number or PIN!");
                System.out.println("Attempts remaining: " + (attempts - 1) + "\n");
            }
        }

        System.out.println("Too many failed attempts. Exiting...");
        return false;
    }

    private void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║          MAIN MENU                 ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Check Balance                  ║");
            System.out.println("║  2. Withdraw Cash                  ║");
            System.out.println("║  3. Deposit Cash                   ║");
            System.out.println("║  4. Change PIN                     ║");
            System.out.println("║  5. Mini Statement                 ║");
            System.out.println("║  6. Exit                           ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("\nEnter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    withdrawCash();
                    break;
                case "3":
                    depositCash();
                    break;
                case "4":
                    changePIN();
                    break;
                case "5":
                    miniStatement();
                    break;
                case "6":
                    System.out.println("\nThank you for using ABC Bank ATM!");
                    System.out.println("Please take your card. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n✗ Invalid choice! Please try again.");
            }
        }
    }

    private void checkBalance() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("        BALANCE INQUIRY");
        System.out.println("═══════════════════════════════════");
        System.out.printf("Account Number: %s\n", currentAccount.getAccountNumber());
        System.out.printf("Account Holder: %s\n", currentAccount.getAccountHolderName());
        System.out.printf("Available Balance: $%.2f\n", currentAccount.getBalance());
        System.out.println("═══════════════════════════════════");
    }

    private void withdrawCash() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("        CASH WITHDRAWAL");
        System.out.println("═══════════════════════════════════");
        System.out.println("Select Amount:");
        System.out.println("1. $20");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. $200");
        System.out.println("5. $500");
        System.out.println("6. Other Amount");
        System.out.println("7. Back to Main Menu");
        System.out.print("\nEnter your choice: ");

        String choice = scanner.nextLine();
        double amount = 0;

        switch (choice) {
            case "1": amount = 20; break;
            case "2": amount = 50; break;
            case "3": amount = 100; break;
            case "4": amount = 200; break;
            case "5": amount = 500; break;
            case "6":
                System.out.print("Enter amount: $");
                try {
                    amount = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("✗ Invalid amount!");
                    return;
                }
                break;
            case "7":
                return;
            default:
                System.out.println("✗ Invalid choice!");
                return;
        }

        if (currentAccount.withdraw(amount)) {
            System.out.println("\n✓ Transaction Successful!");
            System.out.printf("Amount Withdrawn: $%.2f\n", amount);
            System.out.printf("Remaining Balance: $%.2f\n", currentAccount.getBalance());
            System.out.println("Please collect your cash.");
        }
    }

    private void depositCash() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("         CASH DEPOSIT");
        System.out.println("═══════════════════════════════════");
        System.out.print("Enter amount to deposit: $");

        try {
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.deposit(amount);
            System.out.println("\n✓ Deposit Successful!");
            System.out.printf("Amount Deposited: $%.2f\n", amount);
            System.out.printf("New Balance: $%.2f\n", currentAccount.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid amount!");
        }
    }

    private void changePIN() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("          CHANGE PIN");
        System.out.println("═══════════════════════════════════");
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.nextLine();

        if (!currentAccount.validatePin(currentPin)) {
            System.out.println("✗ Incorrect PIN!");
            return;
        }

        System.out.print("Enter new PIN (4 digits): ");
        String newPin = scanner.nextLine();

        if (newPin.length() != 4 || !newPin.matches("\\d+")) {
            System.out.println("✗ Invalid PIN! Must be 4 digits.");
            return;
        }

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.nextLine();

        if (!newPin.equals(confirmPin)) {
            System.out.println("✗ PINs do not match!");
            return;
        }

        currentAccount.changePin(newPin);
        System.out.println("\n✓ PIN changed successfully!");
    }

    private void miniStatement() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("        MINI STATEMENT");
        System.out.println("═══════════════════════════════════");
        System.out.printf("Account Number: %s\n", currentAccount.getAccountNumber());
        System.out.printf("Account Holder: %s\n", currentAccount.getAccountHolderName());
        System.out.printf("Current Balance: $%.2f\n", currentAccount.getBalance());
        System.out.println("\nLast 5 Transactions:");
        System.out.println("(Transaction history feature coming soon)");
        System.out.println("═══════════════════════════════════");
    }
}

// Main Class
public class ATMSimulator {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
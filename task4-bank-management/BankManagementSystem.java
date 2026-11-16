import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Task 4 - Bank Management System (Console)
 * Features: Account Creation, Deposit/Withdraw, Balance Check, Transaction History
 */
public class BankManagementSystem {

    // Records one transaction
    static class Transaction {
        private final String type;
        private final double amount;
        private final double balanceAfter;
        private final String time;

        Transaction(String type, double amount, double balanceAfter) {
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        @Override
        public String toString() {
            return String.format("%s | %-10s | Amount: %10.2f | Balance: %10.2f",
                    time, type, amount, balanceAfter);
        }
    }

    // Step 1: Account class (account no, balance)
    static class Account {
        private final int accountNo;
        private final String holderName;
        private double balance;
        private final ArrayList<Transaction> history = new ArrayList<>();

        Account(int accountNo, String holderName, double openingBalance) {
            this.accountNo = accountNo;
            this.holderName = holderName;
            this.balance = openingBalance;
            if (openingBalance > 0) {
                history.add(new Transaction("OPENING", openingBalance, balance));
            }
        }

        int getAccountNo() { return accountNo; }

        // Step 2 + 4: Deposit with validation
        boolean deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be greater than zero.");
                return false;
            }
            balance += amount;
            history.add(new Transaction("DEPOSIT", amount, balance));
            return true;
        }

        // Step 2 + 4: Withdrawal with validation
        boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be greater than zero.");
                return false;
            }
            if (amount > balance) {
                System.out.println("Insufficient balance. Available: " + String.format("%.2f", balance));
                return false;
            }
            balance -= amount;
            history.add(new Transaction("WITHDRAW", amount, balance));
            return true;
        }

        // Step 3: Show account details
        void showDetails() {
            System.out.println("\n----- Account Details -----");
            System.out.println("Account No   : " + accountNo);
            System.out.println("Holder Name  : " + holderName);
            System.out.printf("Balance      : %.2f%n", balance);
        }

        // Step 5: Transaction history
        void showHistory() {
            System.out.println("\n----- Transaction History (Acc " + accountNo + ") -----");
            if (history.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (Transaction t : history) System.out.println(t);
            }
        }

        double getBalance() { return balance; }
    }

    private static final Map<Integer, Account> accounts = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);
    private static int nextAccountNo = 1001;

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double opening = readDouble("Enter opening deposit (0 or more): ");
        if (opening < 0) {
            System.out.println("Opening deposit cannot be negative.");
            return;
        }
        Account acc = new Account(nextAccountNo, name, opening);
        accounts.put(nextAccountNo, acc);
        System.out.println("Account created successfully! Your account number is " + nextAccountNo);
        nextAccountNo++;
    }

    private static Account getAccount() {
        int no = readInt("Enter account number: ");
        Account acc = accounts.get(no);
        if (acc == null) System.out.println("Account not found.");
        return acc;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Bank Management System =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance Check / Account Details");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            int choice = readInt("Enter your choice: ");
            Account acc;
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> {
                    acc = getAccount();
                    if (acc != null && acc.deposit(readDouble("Enter amount to deposit: ")))
                        System.out.printf("Deposit successful. New balance: %.2f%n", acc.getBalance());
                }
                case 3 -> {
                    acc = getAccount();
                    if (acc != null && acc.withdraw(readDouble("Enter amount to withdraw: ")))
                        System.out.printf("Withdrawal successful. New balance: %.2f%n", acc.getBalance());
                }
                case 4 -> {
                    acc = getAccount();
                    if (acc != null) acc.showDetails();
                }
                case 5 -> {
                    acc = getAccount();
                    if (acc != null) acc.showHistory();
                }
                case 6 -> {
                    System.out.println("Thank you for banking with us!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

import java.util.*;  
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;

public class ATM {
    public static void main(String[] args) {
        User user = new User("user123", "1234");
        ATMInterface atm = new ATMInterface(user);
        atm.start();
    }
}

// ======================= User Class =======================
class User {
    private String userId;
    private String userPin;
    private BankAccount account;

    public User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.account = new BankAccount();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public BankAccount getAccount() {
        return account;
    }
}

// ======================= BankAccount Class =======================
class BankAccount {
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount() {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("✅ Deposit successful. New Balance: ₹" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            System.out.println("✅ Withdrawal successful. Remaining Balance: ₹" + balance);
        } else {
            System.out.println("❌ Insufficient balance.");
        }
    }

    public void transfer(double amount, BankAccount recipient) {
        if (amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            transactionHistory.add(new Transaction("Transfer", amount));
            System.out.println("✅ Transfer successful.");
        } else {
            System.out.println("❌ Insufficient balance.");
        }
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("📭 No transactions yet.");
        } else {
            for (Transaction t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    public double getBalance() {
        return balance;
    }
}

// ======================= Transaction Class =======================
class Transaction {
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return type + " of ₹" + amount + " on " + timestamp;
    }
}

// ======================= ATMInterface Class =======================
class ATMInterface {
    private User user;
    private Scanner scanner;

    public ATMInterface(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("===== Welcome to ATM =====");

        System.out.print("Enter User ID: ");
        String inputId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String inputPin = scanner.nextLine();

        if (inputId.equals(user.getUserId()) && inputPin.equals(user.getUserPin())) {
            System.out.println("✅ Login successful.");
            showMenu();
        } else {
            System.out.println("❌ Invalid credentials.");
        }
    }

    private void showMenu() {
        int choice;
        do {
            System.out.println("\n======= ATM MENU =======");
            System.out.println("1. View Transaction History");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    user.getAccount().printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double deposit = scanner.nextDouble();
                    user.getAccount().deposit(deposit);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdraw = scanner.nextDouble();
                    user.getAccount().withdraw(withdraw);
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ₹");
                    double transfer = scanner.nextDouble();
                    BankAccount dummyRecipient = new BankAccount(); // Simulated recipient
                    user.getAccount().transfer(transfer, dummyRecipient);
                    break;
                case 5:
                    System.out.println("👋 Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        } while (choice != 5);
    }
}

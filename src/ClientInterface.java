

import exceptions.AccountException;
import exceptions.AccountNotFoundException;
import exceptions.DuplicateUserIdException;
import exceptions.InvalidAmountException;
import exceptions.InvalidUserIdException;
import exceptions.NonZeroBalanceException;
import exceptions.PasswordMismatchException;
import exceptions.WrongPasswordException;
import model.BankAccount;
import model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {

        String option;
        while (true) {
            printMainMenu();
            option = scanner.next();

            int choice;
            try {
                choice = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                System.err.println("Error: please enter a valid choice (1 thru 6)\n");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addNewAccount();
                        break;
                    case 2:
                        deleteAccount();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        printInfo();
                        break;
                    case 6:
                        return;
                    default:
                        System.err.println("Error: please enter a valid choice (1 thru 6)\n");
                }
            } catch (AccountException e) {
                System.err.println("Error: " + e.getMessage() + "\n");
            }
        }

    }

    private void printMainMenu() throws IOException {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private void addNewAccount() {

        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        var name = scanner.next();
        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();

        Long id = parseUserId(userId);
        if (findById(id) != null) {
            throw new DuplicateUserIdException("Account with user id " + id + " already exists");
        }
        BankAccount.checkPassword(password);
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Password and confirmation do not match");
        }

        accounts.add(new CheckingAccount(name, id, password));
        System.out.println("\nAccount was created successfully!");
    }

    private void deleteAccount() {

        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = getAccount(userId, password);
        if (account.getAccountBalance() != 0) {
            throw new NonZeroBalanceException("Cannot delete account with non-zero balance ("
                    + account.getAccountBalance() + ")");
        }

        accounts.remove(account);
        System.out.println("\nAccount was deleted successfully!");
    }

    private void deposit() {

        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount account = getAccount(userId, password);
        account.deposit(parseAmount(amount));
        System.out.println("\nDeposit was made successfully! New balance: " + account.getAccountBalance());
    }

    private void withdraw() {

        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount account = getAccount(userId, password);
        account.withdraw(parseAmount(amount));
        System.out.println("\nWithdrawal was made successfully! New balance: " + account.getAccountBalance());
    }

    private void printInfo() {

        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = getAccount(userId, password);

        System.out.println("\nAccount info: ");

        System.out.println(" - customer name: " + account.getCustomerName());
        System.out.println(" - account balance: " + account.getAccountBalance());
        System.out.println(" - account type: Checking");
        if (account instanceof CheckingAccount checking) {
            System.out.println(" - account daily withdrawal limit: " + checking.getDailyWithdrawalLimit());
        }

        System.out.print("\nPress Enter to return to main menu...");

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long parseUserId(String userId) {
        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new InvalidUserIdException("User id must be a number: " + userId);
        }
    }

    private double parseAmount(String amount) {
        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Amount must be a number: " + amount);
        }
    }

    private BankAccount findById(Long userId) {
        for (BankAccount account : accounts) {
            if (account.getUserId().equals(userId)) {
                return account;
            }
        }
        return null;
    }

    // шукає рахунок і перевіряє пароль
    private BankAccount getAccount(String userId, String password) {
        BankAccount account = findById(parseUserId(userId));
        if (account == null) {
            throw new AccountNotFoundException("Account with user id " + userId + " not found");
        }
        if (!account.getUserPassword().equals(password)) {
            throw new WrongPasswordException("Wrong password");
        }
        return account;
    }

}

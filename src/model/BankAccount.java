package model;

import exceptions.InvalidAmountException;
import exceptions.InvalidPasswordException;

public abstract class BankAccount {
    private String customerName;
    private Long userId;
    private String userPassword;
    private Double accountBalance;

    public BankAccount(String customerName, Long userId, String userPassword) {
        this.customerName = customerName;
        this.userId = userId;
        setUserPassword(userPassword);
        this.accountBalance = 0.0;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    // пароль: мінімум 8 символів і хоча б одна цифра
    public static void checkPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            throw new InvalidPasswordException("Password must contain at least one digit");
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        checkPassword(userPassword);
        this.userPassword = userPassword;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        if (accountBalance == null || accountBalance < 0) {
            throw new InvalidAmountException("Balance cannot be negative");
        }
        this.accountBalance = accountBalance;
    }
}

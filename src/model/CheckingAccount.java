package model;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;

public class CheckingAccount extends BankAccount {
    private double dailyWithdrawalLimit = 300;

    public CheckingAccount(String customerName, Long userId, String userPassword) {
        super(customerName, userId, userPassword);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than 0");
        }
        setAccountBalance(getAccountBalance() + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than 0");
        }
        if (amount > dailyWithdrawalLimit) {
            throw new WithdrawalLimitExceededException("Amount exceeds the daily withdrawal limit ("
                    + dailyWithdrawalLimit + ")");
        }
        if (amount > getAccountBalance()) {
            throw new InsufficientFundsException("Insufficient funds. Balance: "
                    + getAccountBalance());
        }
        setAccountBalance(getAccountBalance() - amount);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit < 0) {
            throw new InvalidAmountException("Daily withdrawal limit cannot be negative");
        }
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }
}

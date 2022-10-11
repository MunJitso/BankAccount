package src.Accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Account {
    private final String ownerName;
    private double balance;
    private final AccountType accountType;
    private final int accountId;
    private double pocket;

    public Account(int accountId, String ownerName, double balance, AccountType accountType, double pocket) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountType = accountType;
        this.pocket = pocket;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPocket() {
        return pocket;
    }

    private void setPocket(double pocket) {
        this.pocket = pocket;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void getAccountInfo() {
        System.out.println("-> Your AccountId: " + this.accountId);
        System.out.println("-> Your Name: " + getOwnerName());
        System.out.println("-> Your balance: " + getBalance());
        System.out.println("-> Your pocket: " + getPocket());
        System.out.println("-> Your Account's Type: " + this.accountType + "\n");
    }

    private Account getTargetAccount(Map<Integer, Account> accounts) {
        accounts.forEach((key, value) -> {
            if (key != this.accountId) {
                System.out.println("Account Id: " + key + ", Account Name: " + value.getOwnerName());
            }
        });
        System.out.print("Who's the target (Account Id ): ");
        Scanner scanner = new Scanner(System.in);
        int destinationID = scanner.nextInt();

        List<Integer> check = new ArrayList<>();
        accounts.forEach((key, account) -> check.add(key));
        if (!check.contains(destinationID)) {
            System.out.println("We did not find your target");
            getTargetAccount(accounts);
        } else if (destinationID == this.accountId) {
            System.out.println("You can not set yourself as a destination");
            getTargetAccount(accounts);
        }

        return accounts.get(destinationID);
    }

    public void deposit() {
        System.out.println("Your pocket contains " + getPocket() + "$");
        System.out.print("How much you want to deposit?: ");
        Scanner depositInput = new Scanner(System.in);
        double depositAmount = depositInput.nextDouble();
        if (depositAmount > 0 && getPocket() >= depositAmount) {
            setPocket(getPocket() - depositAmount);
            setBalance(getBalance() + depositAmount);
            System.out.printf("Your Account : %s, Your pocket: %s%n", getBalance(), getPocket());
        } else if (getPocket() < depositAmount) {
            System.out.println("You do not have that amount in your pocket.");
        } else {
            System.out.println("We cannot issue this process.");
        }
    }

    public void withdraw() {
        System.out.print("How much you want to withdraw?: ");
        Scanner withdrawInput = new Scanner(System.in);
        double withdrawAmount = withdrawInput.nextDouble();
        if (withdrawAmount > 0 && getBalance() - withdrawAmount >= 0) {
            setPocket(getPocket() + withdrawAmount);
            setBalance(getBalance() - withdrawAmount);
            System.out.println("You have right now in your pocket " + getPocket() + "$");
        } else {
            System.out.println("We cannot issue the process.");
        }
    }

    public void donate(Map<Integer, Account> accounts) {
        if (accounts.size() > 1) {
            Account destination = getTargetAccount(accounts);
            System.out.print("How much you want to donate?: ");
            Scanner donateInput = new Scanner(System.in);
            double donateAmount = donateInput.nextDouble();
            if (donateAmount > 0 && donateAmount <= 100) {
                if (this.accountType == AccountType.STUDENT) {
                    if (getBalance() - donateAmount >= 0) {
                        setBalance(getBalance() - donateAmount);
                        destination.setBalance(destination.getBalance() + donateAmount);
                        System.out.println("Your new balance is " + getBalance() + "$");
                    } else {
                        System.out.println("You can not donate.");
                    }
                } else {
                    double balanceWithTaxes = getBalance() - donateAmount * 1.0135;
                    if (balanceWithTaxes >= 0) {
                        setBalance(balanceWithTaxes);
                        destination.setBalance(destination.getBalance() + donateAmount);
                        System.out.println("Your new balance is " + getBalance() + "$");
                    } else {
                        System.out.println("You can not donate.");
                    }
                }
            } else {
                System.out.println("We cannot issue this process.");
            }
        } else {
            System.out.println("There's no target");
        }
    }

    public void transfer(Map<Integer, Account> accounts) {
        if (accounts.size() > 1) {
            Account destination = getTargetAccount(accounts);
            System.out.print("How much you want to transfer?: ");
            Scanner transferInput = new Scanner(System.in);
            double transferAmount = transferInput.nextDouble();
            if (transferAmount > 0) {
                double studentTransfer = getBalance() - transferAmount - transferAmount * 0.015 - 0.30;
                double normalTransfer = getBalance() - transferAmount - transferAmount * 0.039 - 0.30;
                if (this.accountType == AccountType.STUDENT) {
                    if (studentTransfer > 0 && getBalance() - studentTransfer >= 0) {
                        setBalance(studentTransfer);
                        destination.setBalance(destination.getBalance() + transferAmount);
                    } else {
                        System.out.println("We cannot issue the process.");
                    }
                } else if (this.accountType == AccountType.NORMAL && normalTransfer > 0 && getBalance() - normalTransfer >= 0) {
                    setBalance(normalTransfer);
                    destination.setBalance(destination.getBalance() + transferAmount);
                }
            } else {
                System.out.println("We cannot issue the process.");
            }

        } else {
            System.out.println("There's no target");
        }
    }
}

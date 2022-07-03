import java.util.Scanner;

public class AccountMethods {
    private final String ownerName;
    private double balance;
    private final AccountType accountType;
    private final int accountId;
    private double pocket;

    public AccountMethods(String ownerName, double balance, AccountType accountType, int accountId, double pocket) {
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountType = accountType;
        this.accountId = accountId;
        this.pocket = pocket;
    }

    public void getAccountInfo() {
        System.out.println("-> Your AccountId: " + this.accountId);
        System.out.println("-> Your Name: " + this.ownerName);
        System.out.println("-> Your balance: " + this.balance);
        System.out.println("-> Your pocket: " + this.pocket);
        System.out.println("-> Your Account's Type: " + this.accountType + "\n");
    }

    public void donate() {
        System.out.print("How much you want to donate?: ");
        Scanner donateInput = new Scanner(System.in);
        double donateAmount = donateInput.nextDouble();
        if (donateAmount > 0) {
            if (this.accountType == AccountType.STUDENT) {
                if (this.balance - donateAmount >= 0) {
                    this.balance = balance - donateAmount;
                    System.out.println("Your new balance is " + this.balance + "$");
                } else {
                    System.out.println("You can not donate.");
                }
            } else {
                double balanceWithTaxes = balance - donateAmount * 1.0135;
                if (balanceWithTaxes >= 0) {
                    this.balance = balanceWithTaxes;
                    System.out.println("Your new balance is " + this.balance + "$");
                } else {
                    System.out.println("You can not donate.");
                }
            }
        } else {
            System.out.println("We cannot issue this process.");
        }

    }

    public void deposit() {
        System.out.println("Your pocket contains " + this.pocket + "$");
        System.out.print("How much you want to deposit?: ");
        Scanner depositInput = new Scanner(System.in);
        double depositAmount = depositInput.nextDouble();
        if (depositAmount > 0 && this.pocket >= depositAmount) {
            this.balance += depositAmount;
            System.out.printf("Your Account : %s, Your pocket: %s%n", this.balance, this.pocket);
        } else if (this.pocket < depositAmount) {
            System.out.println("You do not have that amount in your pocket.");
        } else {
            System.out.println("We cannot issue this process.");
        }
    }

    public void withdraw() {
        System.out.print("How much you want to withdraw?: ");
        Scanner withdrawInput = new Scanner(System.in);
        double withdrawAmount = withdrawInput.nextDouble();
        if (withdrawAmount > 0 && this.balance-withdrawAmount >= 0) {
            this.balance -= withdrawAmount;
            this.pocket += withdrawAmount;
            System.out.println("You have right now in your pocket " + this.pocket + "$");
        } else {
            System.out.println("We cannot issue the process.");
        }
    }

    public void transfer() {
        System.out.print("How much you want to transfer?: ");
        Scanner transferInput = new Scanner(System.in);
        double transferAmount = transferInput.nextDouble();
        if (transferAmount > 0) {
            double studentTransfer = balance - transferAmount - transferAmount * 0.015 - 0.30;
            double normalTransfer = balance - transferAmount - transferAmount * 0.039 - 0.30;
            if (this.accountType == AccountType.STUDENT) {
                if (studentTransfer > 0 && this.balance-studentTransfer >= 0) {
                    this.balance -= studentTransfer;
                } else {
                    System.out.println("We cannot issue the process.");
                }
            } else if (this.accountType == AccountType.NORMAL && normalTransfer > 0 && this.balance-normalTransfer >= 0) {
                this.balance -= normalTransfer;
            }
        } else {
            System.out.println("We cannot issue the process.");
        }
    }
}

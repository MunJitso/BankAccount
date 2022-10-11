package src;

import src.Accounts.Account;
import src.Accounts.AccountType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher {
    public static Map<Integer, Account> accounts = new HashMap<>();
    public static String[] forbiddenChars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "[", "]", "/", "|", "\\", "\"", "!", "#", "@", "$", "%", "^", "*", "(", ")", "+", "=", ",", ";", ":", "'", "?", "<", ">", "`", "~", "."};

    public static void main(String[] args) {
        if (accounts.isEmpty()) accountCreation();
        else accountSelection();
    }

    public static void accountCreation() {
        // ownerName
        StringBuilder ownerName = new StringBuilder();
        boolean validName = true;
        String ownerNameTemp = "";
        do {
            System.out.print("Enter your name: ");
            Scanner nameInput = new Scanner(System.in);
            String name = nameInput.nextLine();
            for (String forbiddenChar : forbiddenChars) {
                if (!name.contains(forbiddenChar)) {
                    validName = true;
                    ownerNameTemp = name;
                } else {
                    System.out.println("A name does not accept special characters or numbers");
                    validName = false;
                    break;
                }
            }
        } while (!validName);
        String[] ownerCapitalName = ownerNameTemp.split(" ");
        for (int i = 0; i < ownerCapitalName.length; i++) {
            if (i == ownerCapitalName.length - 1) {
                ownerName.append(ownerCapitalName[i].substring(0, 1).toUpperCase()).append(ownerCapitalName[i].substring(1));
            } else {
                ownerName.append(ownerCapitalName[i].substring(0, 1).toUpperCase()).append(ownerCapitalName[i].substring(1)).append(" ");
            }
        }

        // Balance
        double balance = 0.0;
        boolean validBalance;
        do {
            try {
                System.out.print("Enter your balance: ");
                Scanner balanceInput = new Scanner(System.in);
                balance = balanceInput.nextDouble();
                validBalance = true;
            } catch (InputMismatchException err) {
                System.out.println("Enter a Valid Number");
                validBalance = false;
            }
        } while (!validBalance);

        // Account Type
        AccountType accountType;
        System.out.print("Student or Normal Account? (s if you're student): ");
        Scanner accTypeInput = new Scanner(System.in);
        String accType = accTypeInput.nextLine();
        if (accType.equalsIgnoreCase("Student") || accType.equalsIgnoreCase("s")) {
            accountType = AccountType.STUDENT;
        } else {
            accountType = AccountType.NORMAL;
        }

        //Account ID
        int accountId = ThreadLocalRandom.current().nextInt(100000, 999999);
        Account account = new Account(accountId, ownerName.toString(), balance, accountType, 0.0);
        accounts.put(accountId, account);
        accountMenu(account);
    }

    public static void accountSelection() {
        System.out.println("What account you want to select");
        try {
            accounts.forEach((key, value) -> System.out.println("Account Id: " + key + ", Account Name: " + value.getOwnerName()));
            System.out.print("Which account you want to log in? ( use Account ID ):");
            Scanner selectionInput = new Scanner(System.in);
            int selection = selectionInput.nextInt();
            Account account = accounts.get(selection);
            accountMenu(account);
        } catch (InputMismatchException err) {
            System.out.println("There's no choice with the index you choose.");
            accountSelection();
        }

    }

    public static void accountMenu(Account account) {
        boolean menuScreen = true;
        while (menuScreen) {
            System.out.println("""
                    --------------------------------
                    1. Account Info.
                    2. deposit.
                    3. donate.
                    4. withdraw.
                    5. transfer.
                    6. logout
                    7. create another account.
                    8. exit.
                    --------------------------------
                    """);

            System.out.print("Your Choice: ");
            Scanner choiceInput = new Scanner(System.in);
            try {
                int choice = choiceInput.nextInt();
                switch (choice) {
                    case 1 -> account.getAccountInfo();
                    case 2 -> account.deposit();
                    case 3 -> account.donate(accounts);
                    case 4 -> account.withdraw();
                    case 5 -> account.transfer(accounts);
                    case 6 -> accountSelection();
                    case 7 -> accountCreation();
                    case 8 -> menuScreen = false;
                    default -> throw new IllegalStateException("The choice you selected is not available here.");
                }
            } catch (IllegalStateException err) {
                System.out.println("There's no choice with the index u choose.");
                accountMenu(account);
            }

        }
    }
}
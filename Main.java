import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final double pocket = 0.0;
    public static List<List<String>> accounts = new ArrayList<>();
    public static StringBuilder ownerName = new StringBuilder();
    public static double balance = 0.0;
    public static AccountType accountType;
    public static int accountId;
    public static String[] forbiddenChars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "[", "]", "/", "|", "\\", "\"", "!", "#", "@", "$", "%", "^", "*", "(", ")", "+", "=", ",", ";", ":", "'", "?", "<", ">", "`", "~", "."};
    public static AccountMethods account;

    public static void main(String[] args) {
        if (accounts.isEmpty()){
            accountCreation();
        } else {
            accountSelection();
        }
        accountMenu();
    }

    public static void accountCreation() {
        List<String> bankAccount = new ArrayList<>();
        // ownerName
        boolean validName = true;
        String ownerNameTemp = "";
        do {
            System.out.print("Enter your name: ");
            Scanner nameInput = new Scanner(System.in);
            String name = nameInput.nextLine();
            for (String forbiddenChar : forbiddenChars) {
                if (name.contains(forbiddenChar)) {
                    System.out.println("A name does not accept special characters or numbers");
                    validName = false;
                    break;
                } else if (!name.contains(forbiddenChar)) {
                    validName = true;
                    ownerNameTemp = name;
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
        bankAccount.add(String.valueOf(ownerName));

        // Balance
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
        bankAccount.add(String.valueOf(balance));
        bankAccount.add(String.valueOf(pocket));

        // Account Type
        System.out.print("Student or Normal Account? (s if you're student): ");
        Scanner accTypeInput = new Scanner(System.in);
        String accType = accTypeInput.nextLine();
        if (accType.equalsIgnoreCase("Student") || accType.equalsIgnoreCase("s")) {
            accountType = AccountType.STUDENT;
        } else {
            accountType = AccountType.NORMAL;
        }
        bankAccount.add(String.valueOf(accountType));

        //Account ID
        accountId = ThreadLocalRandom.current().nextInt(100000, 999999);
        bankAccount.add(String.valueOf(accountId));
        accounts.add(bankAccount);

        account = new AccountMethods(String.valueOf(ownerName), balance, accountType, accountId, pocket);
    }

    public static void accountSelection() {
        System.out.println("What account you want to select");
        try {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println(i + " - " + accounts.get(i));
            }
            System.out.print("Which account you want to log in? :");
            Scanner selectionInput = new Scanner(System.in);
            int selection = selectionInput.nextInt();
            account = new AccountMethods(String.valueOf(accounts.get(selection).get(0)), Double.parseDouble(accounts.get(selection).get(1)), AccountType.valueOf(accounts.get(selection).get(3)), Integer.parseInt(accounts.get(selection).get(4)), Double.parseDouble(accounts.get(selection).get(2)));
        } catch (IndexOutOfBoundsException err) {
            System.out.println("There's no choice with the index u choose.");
            accountSelection();
        }

    }

    public static void accountMenu() {
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
                    case 3 -> account.donate();
                    case 4 -> account.withdraw();
                    case 5 -> account.transfer();
                    case 6 -> accountSelection();
                    case 7 -> accountCreation();
                    case 8 -> menuScreen = false;
                    default -> throw new IllegalStateException("The choice you selected is not available here.");
                }
            } catch (IllegalStateException err){
                System.out.println("There's no choice with the index u choose.");
                accountMenu();
            }

        }
    }
}
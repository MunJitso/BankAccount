import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static StringBuilder ownerName = new StringBuilder();
    public static double balance = 0.0;
    public static AccountType accountType;
    public static int accountId;
    public static String[] forbiddenChars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "[", "]", "/", "|", "\\", "\"", "!", "#", "@", "$", "%", "^", "*", "(", ")", "+", "=", ",", ";", ":", "'", "?", "<", ">", "`", "~", "."};
    static AccountMethods account;
    public static void main(String[] args) {
        System.out.println("Hello User!");
        accountCreation();
        account = new AccountMethods(ownerName.toString(), balance, accountType, accountId);
        accountMenu();
    }

    public static void accountCreation() {
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

        // Account Type
        System.out.print("Student or Normal Account? (s if you're student): ");
        Scanner accTypeInput = new Scanner(System.in);
        String accType = accTypeInput.nextLine();
        if (accType.equalsIgnoreCase("Student") || accType.equalsIgnoreCase("s")) {
            accountType = AccountType.STUDENT;
        } else {
            accountType = AccountType.NORMAL;
        }

        //Account ID
        accountId = ThreadLocalRandom.current().nextInt(100000, 999999);
    }
    public static void accountMenu(){
        // Account Details
        boolean menuScreen = true;
        while (menuScreen) {
            System.out.println("""
                    --------------------------------
                    1. Account Info.
                    2. deposit.
                    3. donate.
                    4. withdraw.
                    5. transfer.
                    6. exit.
                    --------------------------------
                    """);
            Scanner choiceInput = new Scanner(System.in);
            int choice = choiceInput.nextInt();
            switch (choice) {
                case 1 -> account.getAccountInfo();
                case 2 -> account.deposit();
                case 3 -> account.donate();
                case 4 -> account.withdraw();
                case 5 -> account.transfer();
                case 6 -> menuScreen = false;
            }
        }

    }
}
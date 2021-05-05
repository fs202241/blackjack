package blackjack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Blackjack {

    static int userCardTotal = 0;
    static int computerCardTotal = 0;
    static int userMoney = 0;
    static int globalBet = 0;
    static Scanner input = new Scanner(System.in);
    public static boolean playagain = true;
    public static String folderDirectory;
    public static ArrayList<String> textItems = new ArrayList<>();

    public static void main(String[] args) {
        String loadGame = " ";
        getDir();
        System.out.println("The game autosaves every time you win or lose!");
        System.out.println("Would you like to load a game? Y/N");
        loadGame = input.next();
        if (loadGame.equals("Y")) {
            readFile();
            System.out.println("Your money is £" + userMoney);
        } else if (loadGame.equals("N")) {
            userMoney = 100;
        }
        while (playagain == true) {
            System.out.println("Place your bet.");
            System.out.println("You have £" + userMoney);
            int userBet = input.nextInt();
            globalBet = userBet;
            userMoney = userMoney - userBet;
            System.out.println("Dealing cards...");
            getCards();
            getComputerCards();
            getDecision();
            checkWin();
            playAgain();
        }
    }

    public static void getDecision() {
        while ((userCardTotal < 21) && (computerCardTotal < 21)) {
            System.out.println("Do you want to hit, or stand?");
            String action = input.next();
            if (action.equals("Hit")) {
                getCards();
                getComputerCards();
            } else if (action.equals("Stand")) {
                getComputerCards();
            }
        }
    }

    public static void getCards() {
        Random random = new Random();
        int userCardNum = 0;
        for (int i = 0; i < 1; i++) {
            userCardNum = random.nextInt((13 - 1) + 1) + 1;
        }
        if (userCardNum == 1) {
            System.out.println("You got an ace. This card can be 1 or 11. What value do you want to assign to the card?");
            int a = input.nextInt();
            if (a == 1) {
                userCardTotal++;
            } else if (a == 11) {
                userCardTotal = userCardTotal + 11;
            }
        } else if (userCardNum > 10) {
            userCardTotal = userCardTotal + 10;
        } else {
            userCardTotal = userCardTotal + userCardNum;
        }
        System.out.println("Your card total is " + userCardTotal);
    }

    public static void getComputerCards() {
        Random random = new Random();
        int computerCardNum = random.nextInt((13 - 1) + 1) + 1;
        if (computerCardNum == 1) {
            if (computerCardTotal > 10) {
                computerCardTotal++;
            } else if (computerCardTotal <= 10) {
                computerCardTotal = computerCardTotal + 11;
            }
        } else if (computerCardNum > 10) {
            computerCardTotal = computerCardTotal + 10;
        } else {
            computerCardTotal = computerCardTotal + computerCardNum;
        }
        if (computerCardTotal >= 16) {
            int randy = random.nextInt(3);
            if (randy == 3) {
                computerCardTotal = computerCardTotal + computerCardNum;
            } else {
            }
        }
    }

    public static void checkWin() {
        if (userCardTotal > 21) {
            System.out.println("Your card total is " + userCardTotal + ". You lose!");
            playagain = false;
            globalBet = 0;
            writeFile();
        } else if (computerCardTotal > 21) {
            System.out.println("The opponent's card total has exceeded 21. You win!");
            playagain = false;
            userMoney = userMoney + (globalBet * 2);
            globalBet = 0;
            writeFile();
        } else if (userCardTotal == 21) {
            System.out.println("Your card total is 21. You win!");
            playagain = false;
            userMoney = userMoney + (globalBet * 2);
            globalBet = 0;
            writeFile();
        } else if (computerCardTotal == 21) {
            System.out.println("The opponent's card total is 21. You lose!");
            playagain = false;
            globalBet = 0;
            writeFile();
        }

    }

    public static void playAgain() {

        String saveGame = " ";
        System.out.println("Play again? Y/N");
        String play = input.next();
        if (play.equals("Y")) {
            userCardTotal = 0;
            computerCardTotal = 0;
            playagain = true;
        } else if (play.equals("N")) {
            System.out.println("Your money is £" + userMoney);
            System.exit(0);
        }
    }

    public static void getDir() {
        folderDirectory = System.getProperty("user.dir") + "\\money.txt";
    }

    public static void writeFile() {
        try {
            FileWriter writeToFile = new FileWriter(folderDirectory, false);
            PrintWriter printToFile = new PrintWriter(writeToFile);
            printToFile.println(userMoney);
            printToFile.close();
            writeToFile.close();
        } catch (Exception E) {
            System.out.println("Error: +e");
        }
    }

    public static void readFile() {
        String inputLine;
        try {
            BufferedReader read = new BufferedReader(new FileReader(folderDirectory));
            while ((inputLine = read.readLine()) != null) {
                textItems.add(inputLine);
                String userMoney1 = textItems.get(textItems.size() - 1);
                userMoney = Integer.parseInt(userMoney1);
            }

            read.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
}

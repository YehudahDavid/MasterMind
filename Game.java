import java.util.*;

public class Game {
    static int size;
    static int tries = 10;

    public static void main(String[] args) throws InterruptedException {
        Scanner inp = new Scanner(System.in);
        prompt(inp);
    }

    public static void prompt(Scanner inp) throws InterruptedException {
        System.out.printf("\t\tWelcome to Mastermind!\n" +
                          "\tChoose a level below: 1, 2, or 3\n\n" +
                          "1: Sequence of 3 numbers to guess\n" +
                          "2: Sequence of 4 numbers to guess\n" +
                          "3: Sequence of 5 numbers to guess\n\n");
        chooseLevel(inp);
    }

    public static void chooseLevel(Scanner inp) throws InterruptedException {
        Random rand = new Random();
        int level = checkLevel(inp);
        System.out.println("...");
        Thread.sleep(rand.nextInt(2100));
        System.out.println("I chose " + size + " digits. Good Luck!");
        game(inp, size);
    }

    public static void game(Scanner inp, int size) throws InterruptedException {
        Random rand = new Random();
        int[] AI = createSequence(size);
        Thread.sleep(rand.nextInt(2100));
        System.out.println("\nYou have " + tries + " tries.\n");
        Thread.sleep(rand.nextInt(2100));
        int[] guessArr = guess(inp, size);
        boolean match = checkMatch(guessArr, AI, size);
        if (match) {
            System.out.println("Congrats! You Matched: " + Arrays.toString(AI));
        }
        while (!match) {
            if (tries == 0) {
                System.out.println("You Lost: " + Arrays.toString(AI));
                break;
            }
            Thread.sleep(rand.nextInt(2100));
            System.out.println("\nYou have " + tries + " tries.\n");
            Thread.sleep(rand.nextInt(2100));
            int[] guessarr = guess(inp, size);
            match = checkMatch(guessarr, AI, size);
            if (match) {
                System.out.println("Congrats! You Matched: " + Arrays.toString(AI));
            }
        }
        endGame(tries, match, inp);
    }

    public static int[] createSequence(int size) {
        int[] seq = new int[size];
        Random rand = new Random();
        for (int i = 0 ; i < size ; i++) {
            seq[i] = rand.nextInt(10);
        }
        return seq;
    }

    public static int convertToInt(Scanner inp, int size) {
        boolean converted = false;
        int guess = 0;
        while (!converted) {
            System.out.print("Your Input: ");
            String guessStr = inp.next();
            try {
                if (guessStr.length() > size || guessStr.length() < size) {
                    System.out.println("Invalid Input! Write only " + size + " digits.");
                    continue;
                }
                guess = Integer.parseInt(guessStr);
                converted = true;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Input! Write only " + size + " digits.");
            }
        }
        return guess;
    }

    public static int[] guess(Scanner inp, int size) {
       int[] guessArr = new int[size];
        int guess = convertToInt(inp, size);
        for (int i = size - 1 ; i >= 0 ; i--){
            guessArr[i] = guess % 10;
            guess = guess / 10;
        }
        System.out.println(Arrays.toString(guessArr));
        return guessArr;
    }

    public static int checkLevel(Scanner inp) {
        System.out.print("Which level? ");
        int level = 1;
        try {
            level = inp.nextInt();
            if (level == 1) {
                size = 3;
                return level;
            } else if (level == 2) {
                size = 4;
                return level;
            } else if (level == 3){
                size = 5;
                return level;
            } else {
                System.out.println("Invalid input! Choose between 1 and 3.");
                checkLevel(inp);
            }
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Input! Try typing a number.");
            inp.nextLine();
            checkLevel(inp);
        }
        return level;
    }

    public static boolean checkMatch(int[] guessArr, int[] AI, int size) {
        boolean match = Arrays.equals(guessArr, AI);
        int hits = 0;
        if (!match) {
            for (int i = 0; i < size ; i++) {
                if (guessArr[i] == AI[i]) {
                    hits++;
                }
            }
            System.out.println("You correctly guessed " + hits + ".");
            tries--;
        }
        return match;
    }
    public static void endGame(int trie, boolean match, Scanner inp) throws InterruptedException {
        if (tries == 0) {
            System.out.println("You ran out of tries! Try again? (y/n)");
            String answer = inp.next().toLowerCase();
            if (answer.equals("y")) {
                tries = 10;
                prompt(inp);
            } else if (answer.equals("n")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Not an answer, try again.");
                endGame(trie,match,inp);
            }
        } else {
            System.out.println("Play again? (y/n)");
            String answer = inp.next().toLowerCase();
            if (answer.equals("y")) {
                tries = 10;
                prompt(inp);
            } else if (answer.equals("n")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Not an answer, try again.");
                endGame(trie,match,inp);
            }
        }
    }
}

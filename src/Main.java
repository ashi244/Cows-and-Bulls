import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args){
        System.out.println("Hello! Welcome to my game: COWS and BULLS");
        System.out.println("The rules of this game are very simple. You will be trying to guess a specific number. For every time you guess, you the computer will inform you of how many 'cows' and 'bulls' you have in your guess");
        System.out.println("A cow means that the digit is the correct number but it is in the wrong position. A bull means you have the correct number in the correct position");
        System.out.println("You will have 12 attempts at guessing the number. Good luck!");
        boolean contGame = true;
        int count = 0;
        while (contGame){
            contGame = game();
        }
    }

    public static boolean game(){
        int correctGuess = 0;
        int guessLength = 0;
        Random rand = new Random();
        System.out.println("Please choose a difficulty for your game ranging from easy, medium, hard and impossible");
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.next();
        try {
            if (difficulty.equals("easy")) {
                correctGuess = rand.nextInt(999 - 100) + 100;
                guessLength = 3;
                guessOne(correctGuess, guessLength);
            } else if (difficulty.equals("medium")) {
                correctGuess = rand.nextInt(9999 - 1000) + 1000;
                guessLength = 4;
                guessOne(correctGuess, guessLength);
            } else if (difficulty.equals("hard")) {
                correctGuess = rand.nextInt(99999 - 10000) + 10000;
                guessLength = 5;
                guessOne(correctGuess, guessLength);
            } else if (difficulty.equals("impossible")) {
                correctGuess = rand.nextInt(999999 - 100000) + 100000;
                guessLength = 6;
                guessOne(correctGuess, guessLength);
            } else {
                throw new IllegalArgumentException("That is not a valid difficulty!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return game();  // Recursively call the game() method to allow the user to try again
        }
        System.out.println("Would you like to replay the game? Type 1 for yes and 0 for no");
        int response = scanner.nextInt();
        if (response == 1){
            return true;
        }
        else{
            System.out.println("Thanks for playing my game! Please feel free to offer suggestions as to how it can be improved");
            return false;
        }
    }

    public static void guessOne(int secretNumber, int guessLength) {
        int bulls = 0;
        int cows = 0;
        int guessAttempt = 1;
        while (bulls != guessLength) {
            boolean validGuess = true; // Reset validGuess to true for each guess attempt
            int guess = 0;
            boolean notInt = false;
            boolean errorMessageGivenAlready = false;
            while (validGuess) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter a " + guessLength + " digit number for guess number: " + guessAttempt);
                String currentGuess = scanner.next();

                for (int i = 0; i < currentGuess.length(); i++) {
                    if (!Character.isDigit(currentGuess.charAt(i))) {
                        validGuess = false;
                        notInt = true;
                        break;
                    }
                }

                if (!validGuess) {
                    if (!errorMessageGivenAlready) {
                        System.out.println("You must enter a number! Please enter again");
                        errorMessageGivenAlready = true;
                    }
                } else if (currentGuess.length() != guessLength) {
                    validGuess = false;
                    System.out.println("You have not entered a " + guessLength + " digit number! Please enter again");
                }
                if ((!notInt) && (currentGuess.length() == guessLength)) {
                    guess = Integer.parseInt(currentGuess);
                    break;
                }
            }

            if (!validGuess) {
                continue; // Skip the cows and bulls logic and ask for another guess
            }

            String numberString = Integer.toString(guess);
            int[] guessArray = new int[numberString.length()];
            for (int i = 0; i < numberString.length(); i++) {
                char digitChar = numberString.charAt(i);
                int digit = Character.getNumericValue(digitChar);
                guessArray[i] = digit;
            }
            String correctGuess = Integer.toString(secretNumber);
            int[] numberArray = new int[numberString.length()];
            for (int i = 0; i < numberString.length(); i++) {
                char digitChar = correctGuess.charAt(i);
                int digit = Character.getNumericValue(digitChar);
                numberArray[i] = digit;
            }
            bulls = findBulls(numberArray, guessArray);
            cows = findCows(numberArray, guessArray);
            if (bulls != guessLength) {
                System.out.println("Cows: " + cows);
                System.out.println("Bulls: " + bulls);
                guessAttempt++;
                System.out.println("You have " + (13 - guessAttempt) + " guess left");
            } else {
                System.out.println("Congratulations, you've successfully guessed the number!");
            }
        }
    }
    public static int findCows(int[] secretNumber, int[] userGuess){
        int count = 0;
        int cows = 0;
        for (int i = 0; i < secretNumber.length; i++) {
            int secretDigit = secretNumber[i];
            for (int j = 0; j < userGuess.length; j++) {
                int guessDigit = userGuess[j];
                if (secretDigit == guessDigit && i != j) {
                    cows++;
                    break;
                }
            }
        }
        return cows;
    }

    public static int findBulls(int [] secretNumber, int [] userGuess){
        int count = 0;
        for (int i = 0; i < secretNumber.length; i++){
            if (secretNumber[i] == userGuess[i]){
                count += 1;
            }
        }
        return count;
    }
}

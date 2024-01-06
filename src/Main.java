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
        boolean noDoubles = false;
        int correctGuess = 0;
        int guessLength = 0;
        Random rand = new Random();
        System.out.println("Please choose a difficulty for your game ranging from easy, medium, hard and impossible");
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.next();
        while (noDoubles == false){
            try {
                if (difficulty.equals("easy")) {
                    correctGuess = rand.nextInt(999 - 100) + 100;
                    guessLength = 3;
                } else if (difficulty.equals("medium")) {
                    correctGuess = rand.nextInt(9999 - 1000) + 1000;
                    guessLength = 4;
                } else if (difficulty.equals("hard")) {
                    correctGuess = rand.nextInt(99999 - 10000) + 10000;
                    guessLength = 5;
                } else if (difficulty.equals("impossible")) {
                    correctGuess = rand.nextInt(999999 - 100000) + 100000;
                    guessLength = 6;
                } else {
                    throw new IllegalArgumentException("That is not a valid difficulty!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return game();
            }
            noDoubles = true;
            String guessString = Integer.toString(correctGuess);
            for (int i = 0; i < guessString.length(); i++){
                for (int j = i+1; j < guessString.length(); j++){
                    if (guessString.charAt(i) == guessString.charAt(j)){
                        noDoubles = false;
                    }
                }
            }
        }
        guessOne(correctGuess, guessLength);
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
        boolean hintUsed = false;
        boolean damageDone = false;
        while (bulls != guessLength) {
            boolean validGuess = true;
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
                continue;
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
                if (guessAttempt == 13){
                    System.out.println("You've run out of correct guesses! Better luck next time");
                    break;
                }
                if (guessAttempt >= 9){
                    if (hintUsed == false){
                        System.out.println("You only have " + (13 - guessAttempt) + " guesses remaining and you might lose! Would you like a hint?");
                        System.out.println("Hints is a randomly generated system. You might get a very useful hint or you might get a useless hint");
                        System.out.println("You can only use a hint once");
                        System.out.println("Type yes or no");
                        Scanner scanner = new Scanner(System.in);
                        String answer = scanner.next();
                        if (answer.equals("yes")){
                            hintUsed = true;
                            damageDone = hint(secretNumber, guessLength);
                        }
                        else if (answer.equals("no")){
                            hintUsed = false;
                        }
                        else{
                            System.out.println("Shame on you for not entering a valid input! As punishment, you've lost your chance to use a hint");
                        }
                    }
                }
                if (damageDone == true){
                    guessAttempt++;
                    damageDone = false;
                }
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

    public static boolean hint(int correctGuess, int guessLength){
        Random rand = new Random();
        int difficulty = rand.nextInt(4);
        int position = rand.nextInt(guessLength - 1);
        String guessString = Integer.toString(correctGuess);
        char digit = guessString.charAt(position);
        if (difficulty == 3){
            System.out.println("Congrats, you've received a super-duper helpful hint!");
            System.out.println("The digit at position"  + position + " is " + digit);
            return false;
        }
        else if (difficulty % 2 == 0){
            System.out.println("Oh you didn't hit jackpot, but you didn't get a completely useless hint either!");
            System.out.println("One of the digits in your secret number is " + digit);
            return false;
        }
        else{
            System.out.println("oH NOOOO you got really unlucky! Not only did you get no information, you also lost another guess attempt");
            System.out.println("Sucks to be you, I guess. Anyways, continuing on with the game");
            return true;
        }

    }
}

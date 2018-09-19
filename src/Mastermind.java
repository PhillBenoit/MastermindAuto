import java.util.Scanner;

/**
 * Main class for a game of Mastermind.
 * 
 * @author Phillip Benoit
 *
 */
public class Mastermind {

    //Strings for display
    private static final String CONTINUE_MESSAGE = "Do you want to play again?";
    private static final String GUESS_MESSAGE = "Enter guess number %d: ";
    private static final String RCRP_MESSAGE = "Colors in the correct place: %d";
    private static final String RCWP_MESSAGE = "Colors correct but in wrong position: %d";
    private static final String YOU = "YOU ";
    private static final String WIN_MESSAGE = "WIN!";
    private static final String LOSE_MESSAGE = "LOSE!";
    private static final String LENGTH_ERROR_MESSAGE = "Please use %d letters for your guess";
    private static final String FORMAT_ERROR_MESSAGE = "Please only use colors ";

    private static final String SEPARATOR = "*************************************";
    private static final String TURN_RESULTS = "Turn %2d %s %d,%d";
    private static final String ANSWER = "Answer  ";

    private static final String CONFIRMATION = "You entered: ";
    private static final String UNEXPECTED_INPUT = "Error: Unexpected input.";
    private static final String PAUSE_MESSAGE = "-- Press 'Enter' or 'Return' to continue --";
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String SINGLE_CHAR_YES = "y";
    private static final String SINGLE_CHAR_NO = "n";



    //Number of turns before a loss
    private static final int NUMBER_OF_TURNS = 10;

    //keyboard scanner for input
    private static Scanner keyboard = new Scanner(System.in);

    //holds turn results
    static private int[][] results;
    static private String[] guesses;

    //controller for the answer
    static private MastermindController controller;

    //turn number of the current game
    static private int turn;

    /**
     * Main method
     * 
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        //welcome message
        System.out.println("Welcome to Mastermind!\n");

        //while the user wants to play:
        do {
            //init results
            results = new int[2][NUMBER_OF_TURNS];
            guesses = new String[NUMBER_OF_TURNS];

            //model (whose constructor builds the secret answer)
            MastermindModel answer = new MastermindModel();

            //controller, passing in the model
            controller = new MastermindController(answer);

            //turn loop
            for (turn = 0; turn < NUMBER_OF_TURNS; turn++) {
                getGuess();

                //visual separator
                System.out.println(SEPARATOR);
                System.out.println();

                //display the board
                printBoard();
            }

            //get string value of answer
            String answer_string = "";
            for (int step = 0; step < MastermindModel.ANSWER_SIZE; step++)
                answer_string = answer_string + answer.getColorAt(step);

            //display answer
            System.out.println(ANSWER + answer_string);
            System.out.println();

            System.out.print(YOU);
            //Determine win or loss, wins are attributed with a value greater than
            //the max number of turns
            String winLossString;
            if (turn == NUMBER_OF_TURNS) winLossString = LOSE_MESSAGE;
            else winLossString = WIN_MESSAGE;            
            System.out.println(winLossString);

            //run while user wants to play
        } while (getYorN(CONTINUE_MESSAGE));

        //close keyboard input
        if(keyboard != null) keyboard.close();
    }

    /**
     * Stores the processed results of a guess
     * 
     * @param guess Guess to be added to the game board
     * @throws MastermindIllegalLengthException from controller.isCorrect
     * @throws MastermindIllegalColorException from controller.isCorrect
     */
    private static void processTurn(String guess) {

        //store results
        results[0][turn] = controller.getRightColorRightPlace(guess);
        results[1][turn] = controller.getRightColorWrongPlace(guess);
        guesses[turn] = guess;

        //Check whether or not the guess is correct (by asking the controller)
        //assigning a value greater than max ends turn for loop
        if (controller.isCorrect(guess)) turn = NUMBER_OF_TURNS + 1;

        // If not, display the relevant statistics  (by asking the controller)
        else {            
            System.out.println(String.format(RCRP_MESSAGE, results[0][turn]));
            System.out.println(String.format(RCWP_MESSAGE, results[1][turn]));
            pause();
        }
    }

    /**
     * Get the guess for a turn
     */
    private static void getGuess() {
        /*

        //boolean to store test results that prevent erroneous inputs
        boolean pass_try_catch = false;

        do {
            try {
                //get input
                String guess = getString(String.format(GUESS_MESSAGE, turn+1));

                //process input
                processTurn(guess.toUpperCase());

                //end loop if results are valid
                pass_try_catch = true;
            }

            //catch errors from processing the guess
            catch (MastermindIllegalColorException e) {
                System.out.print(FORMAT_ERROR_MESSAGE);
                //display valid colors
                for (char color : MastermindModel.COLOR_KEY)
                    System.out.print(color + " ");
                System.out.println();
            }
            catch (MastermindIllegalLengthException e) {
                System.out.println(
                        String.format(LENGTH_ERROR_MESSAGE, MastermindModel.ANSWER_SIZE));
            }

            //run loop until valid input is found
        } while (!pass_try_catch);*/
    }
    

    /**
     * Displays the game board
     */
    private static void printBoard() {
        for (int step=0; step < NUMBER_OF_TURNS; step++) {
            //prevent printing turns that have not been played
            if (guesses[step] != null) System.out.println(
                    String.format(TURN_RESULTS, step+1, guesses[step],
                            results[0][step], results[1][step]));
        }
        System.out.println();
    }

    /**
     * Pause the program by waiting for input 
     */
    private static void pause() {
        System.out.println(PAUSE_MESSAGE);
        keyboard.nextLine();
    }

    /**
     * Get string from input
     * 
     * @param msg message used to prompt the user
     * @return string that was entered by the user
     */
    private static String getString(String msg) {
        String answer = "";
        System.out.println(msg);
        answer = keyboard.nextLine(); 
        System.out.println(CONFIRMATION + answer + "\n");
        return answer;
    }

    /**
     * Get yes or no from user input
     * 
     * @param msg message used to prompt user
     * @return true/false based on yes/no response
     */
    private static boolean getYorN(String msg) {
        String answer = getString(msg);

        while (true) {
            if  (answer.compareToIgnoreCase(SINGLE_CHAR_YES)   == 0  
                    || answer.compareToIgnoreCase(YES) == 0)
                return true;
            if  (answer.compareToIgnoreCase(SINGLE_CHAR_NO)   == 0  
                    || answer.compareToIgnoreCase(NO) == 0)
                return false;
            System.out.println(UNEXPECTED_INPUT);
            answer = getString(msg);
        }
    }

}

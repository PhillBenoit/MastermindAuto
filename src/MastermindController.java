/**
 * Controller for MastermindMidel class
 * 
 * Various methods to test the correctness of a guess based on a stored answer
 * 
 * @author Phillip Benoit
 *
 */
public class MastermindController {

    //holds correct answer
    private MastermindModel answer;

    //holds the most recently processed guess
    private String guess;

    //count for the processed results of the most recently asked guess
    private int rcrp, rcwp;

    /**
     * Constructor
     * 
     * @param model model containing the correct answer
     */
    public MastermindController(MastermindModel model) {
        answer = model;
        guess = new String();
    }

    /**
     * Processes a guess
     * 
     * @param guess String of a guess to test
     * @throws MastermindIllegalColorException when an unknown color is found in the guess
     * @throws MastermindIllegalLengthException when the guess
     * length does not match defined size in MastermindModel
     */
    private void processGuess(String guess) {

        //avoid testing the same input twice
        if (this.guess.compareTo(guess) == 0) return;

        /*
        //reject guesses of an illegal length
        if (guess.length() != MastermindModel.ANSWER_SIZE)
            throw new MastermindIllegalLengthException();

        //reject guesses with illegal colors
        for (char color : guess.toCharArray())
            if (getColorIndex(color) == -1) throw new MastermindIllegalColorException();
            */

        //assign accepted guess to the most recently processed guess
        this.guess = guess;

        //clear counters
        rcrp = 0;
        rcwp = 0;

        //arrays used to process results
        boolean[] correct = new boolean[MastermindModel.ANSWER_SIZE];
        int[] color_counter = new int[MastermindModel.COLOR_KEY.length];

        //right color / right place processing
        for (int step = 0; step < MastermindModel.ANSWER_SIZE; step++) {

            //get a character from the answer
            char char_from_answer = answer.getColorAt(step);

            //compare to answer in same position of the guess and
            //store results for later use
            correct[step] = guess.charAt(step) == char_from_answer;

            //step up counter
            if (correct[step]) rcrp++;

            //load incorrect guesses for right color / wrong place processing
            else color_counter[getColorIndex(char_from_answer)]++;
        }

        //right color / wrong place processing
        for (int step = 0; step < MastermindModel.ANSWER_SIZE; step++)

            //skip colors that have been found
            if (!correct[step]) {

                //get index value for a color in the guess
                int guess_color_index = getColorIndex(guess.charAt(step));

                //compare index to the count of colors found in rcrp processing
                if (color_counter[guess_color_index] > 0) {
                    rcwp++;
                    color_counter[guess_color_index]--;
                }
            }
    }

    /**
     * Finds the index of a color in the model color key
     * 
     * @param color color to get the index for
     * @return index of the color
     */
    public int getColorIndex(char color) {
        for (int step = 0; step < MastermindModel.COLOR_KEY.length; step++)
            if (color == MastermindModel.COLOR_KEY[step]) return step;
        //return an illegal index for colors not found
        return -1;
    }

    /**
     * Determines if whole guess is correct
     * 
     * @param guess String of a guess
     * @return true if true
     * @throws MastermindIllegalColorException from processGuess
     * @throws MastermindIllegalLengthException from processGuess
     */
    public boolean isCorrect(String guess) {
        processGuess(guess);
        return rcrp == MastermindModel.ANSWER_SIZE;
    }

    /**
     * Returns the count of colors in a guess that are the right color and right position
     * 
     * @param guess String of a guess
     * @return count from processing rcrp
     * @throws MastermindIllegalColorException from processGuess
     * @throws MastermindIllegalLengthException from processGuess
     */
    public int getRightColorRightPlace(String guess) { 
        processGuess(guess);
        return rcrp;
    }

    /**
     * Returns the count of colors in a guess that are the right color but wrong position
     * 
     * @param guess String of a guess
     * @return count from processing rcwp
     * @throws MastermindIllegalColorException from processGuess
     * @throws MastermindIllegalLengthException from processGuess
     */
    public int getRightColorWrongPlace(String guess) {
        processGuess(guess);
        return rcwp;
    }
    
    public String getAnswer() {
        return answer.getAnswer();
    }

}

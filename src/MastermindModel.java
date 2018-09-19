/**
 * Holds the answer for a game of Mastermind
 * 
 * @author Phillip Benoit
 *
 */
public class MastermindModel {

    //holds answer
    private char[] answer;

    //constants used to define size of an answer and the available colors
    public static final int ANSWER_SIZE = 4;
    public static final char[] COLOR_KEY = {'R','O','Y','G','B','P'};


    /**
     * Constructor
     */
    public MastermindModel() { 
        answer = new char[ANSWER_SIZE];

        //randomly generate answer
        for (int counter = 0; counter < ANSWER_SIZE; counter++)
            answer[counter] = COLOR_KEY[(int)(Math.random() * COLOR_KEY.length)];
    }

    /**
     * This method is a special constructor to allow us to use JUnit to test our model.
     * 
     * Instead of creating a random solution, it allows us to set the solution from a 
     * String parameter.
     * 
     * 
     * @param answer A string that represents the four color solution
     */
    public MastermindModel(String answer) {
        this.answer = answer.toCharArray();
    }


    /**
     * "Getter"
     * 
     * @param index index of character in answer to return
     * @return character at given index
     */
    public char getColorAt(int index) {
        return answer[index];
    }
    
    public String getAnswer() {
        return new String(answer);
    }

}

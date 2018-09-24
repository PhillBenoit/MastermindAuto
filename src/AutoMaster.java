import java.util.Arrays;

public class AutoMaster {
	
	static private int turn_count;
	
	static private String[] guesses;
	
	static private int[][] results;
	
	static private boolean[][] possible;
	
	static private MastermindController controller;
	
	static private boolean is_solved;
	
	static private final int MAX_TURNS = 10;
	
	public static void main(String[] args) {
		is_solved = false;
		turn_count = 0;
		guesses = new String[MAX_TURNS];
		results = new int[2][MAX_TURNS];
		possible = new boolean[6][4];
		for (boolean[] row : possible) Arrays.fill(row, true);
		
		MastermindModel answer = new MastermindModel();
		
        controller = new MastermindController(answer);
        
        runOpeningGambit();
        
        printPossible();
        
        System.out.println(controller.getAnswer());
        System.out.format("Turns: %d\n", turn_count);
        System.out.println("Solved: " + is_solved);
	}
	
	static private void printPossible() {
		for (boolean[] row : possible) {
			for (boolean position : row)
				System.out.print(position + " ");
			System.out.println();
		}
	}
	
	static private void runOpeningGambit() {
	    String gambit_mask = "0011";
	    char[][] gambit_colors = {{'R','O'},{'Y','G'},{'B','P'}};
	    
	    int found_counter = 0;
	    
	    int step_counter = 0;
	    
	    do {
	    	processTurn(gambit_mask.replace('0', gambit_colors[step_counter][0])
	    			.replace('1', gambit_colors[step_counter][1]));
		    analizeGambit(step_counter*2);
	    	found_counter += results[0][turn_count-1] + results[1][turn_count-1];
		    step_counter++;
		} while (found_counter < 4 && step_counter < 3);
	    
	    for (; step_counter < 3; step_counter++) {
	    	eliminateColor(step_counter*2);
	    	eliminateColor((step_counter*2)+1);
	    }
	    
	    System.out.println("Found count: " + found_counter);
	}
	
	static private void eliminateColor(int index) {
		Arrays.fill(possible[index], false);
	}
	
	static private void analizeGambit(int starting_index) {
	    if (results[0][turn_count-1] == 0) {
            possible[starting_index][0] = false;
            possible[starting_index][1] = false;
            possible[starting_index+1][2] = false;
            possible[starting_index+1][3] = false;
        }
        if (results[1][turn_count-1] == 0) {
            possible[starting_index+1][0] = false;
            possible[starting_index+1][1] = false;
            possible[starting_index][2] = false;
            possible[starting_index][3] = false;
        }
	}
	
	static private void processTurn(String guess) {
	        results[0][turn_count] = controller.getRightColorRightPlace(guess);
	        results[1][turn_count] = controller.getRightColorWrongPlace(guess);
	        guesses[turn_count] = guess;
	        is_solved = results[0][turn_count] == 4;
            System.out.format("RCRP: %d  RCWP: %d\n", results[0][turn_count],results[1][turn_count]);
	        turn_count++;
	}

}

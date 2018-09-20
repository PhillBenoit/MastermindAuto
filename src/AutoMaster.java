import java.util.Arrays;

public class AutoMaster {
	
	static private int turn_count;
	
	static private String[] guesses;
	
	static private int[][] results;
	
	static private boolean[][] possible;
	
	static private MastermindController controller;
	
	static private final int MAX_TURNS = 10;
	
	public static void main(String[] args) {
		turn_count = 0;
		guesses = new String[MAX_TURNS];
		results = new int[2][MAX_TURNS];
		possible = new boolean[6][4];
		for (boolean[] row : possible) Arrays.fill(row, true);
		
		MastermindModel answer = new MastermindModel("OROO");

        controller = new MastermindController(answer);
        
        runOpeningGambit();
        
        printPossible();
        
        System.out.println(controller.getAnswer());
        System.out.format("Turns: %d\n", turn_count);
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
	    
		processTurn(gambit_mask.replace('0', gambit_colors[0][0]).replace('1', gambit_colors[0][1]));
	    analizeGambit(0);
		
		processTurn(gambit_mask.replace('0', gambit_colors[1][0]).replace('1', gambit_colors[1][1]));
		analizeGambit(2);
		
		processTurn(gambit_mask.replace('0', gambit_colors[2][0]).replace('1', gambit_colors[2][1]));
		analizeGambit(4);
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
            System.out.format("RCRP: %d  RCWP: %d\n", results[0][turn_count],results[1][turn_count]);
	        turn_count++;
	}

}

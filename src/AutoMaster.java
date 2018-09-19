import java.util.ArrayList;
import java.util.Arrays;

public class AutoMaster {
	
	static private int turn_count;
	
	static private ArrayList<String> guesses;
	
	static private ArrayList<Integer>[] results;
	
	static private boolean[][] possible;
	
	static private MastermindController controller;
	
	public static void main(String[] args) {
		turn_count = 0;
		guesses = new ArrayList<String>();
		results = new ArrayList[2];
		results[0] = new ArrayList<Integer>();
		results[1] = new ArrayList<Integer>();
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
		int rcrp, rcwp;
		rcrp = controller.getRightColorRightPlace("RROO");
		rcwp = controller.getRightColorWrongPlace("RROO");
		results[0].add(rcrp);
		results[1].add(rcwp);
		guesses.add("RROO");
		turn_count++;
		if (rcrp == 0) {
			possible[0][0] = false;
			possible[0][1] = false;
			possible[1][2] = false;
			possible[1][3] = false;
		}
		if (rcwp == 0) {
			possible[1][0] = false;
			possible[1][1] = false;
			possible[0][2] = false;
			possible[0][3] = false;
		}
		System.out.format("RCRP: %d  RCWP: %d\n", rcrp,rcwp);

		rcrp = controller.getRightColorRightPlace("YYGG");
		rcwp = controller.getRightColorWrongPlace("YYGG");
		results[0].add(rcrp);
		results[1].add(rcwp);
		guesses.add("YYGG");
		turn_count++;
		if (rcrp == 0) {
			possible[2][0] = false;
			possible[2][1] = false;
			possible[3][2] = false;
			possible[3][3] = false;
		}
		if (rcwp == 0) {
			possible[3][0] = false;
			possible[3][1] = false;
			possible[2][2] = false;
			possible[2][3] = false;
		}
	}

}

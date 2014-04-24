import java.util.ArrayList;

class Dice {
	
	public static final int NUM_DICE = 2;
	public static final int DRAW = 3;
	private int[] dice = {1,1};
	private int winner;
	
	
	public void rollDice () {
		for (int i=0; i<NUM_DICE; i++) {
			dice[i] = 1 + (int)(Math.random() * 6);   
		}
		return;
	}
	
	
	public void displayDice (int playerId) {
		System.out.print("Player ");
		Board.displayChecker(playerId);
		System.out.println(" rolls " + dice[0] + " " + dice[1] + ".");
	}
	
	
	public int rollDieEach () {
		rollDice();
		if (dice[0] > dice[1]) {
			winner = Board.X_PLAYER_ID;
		}
		else if (dice[0] < dice[1]) {
			winner = Board.O_PLAYER_ID;
		}
		else {
			winner = DRAW;
		}
		return winner;
	}
	
	
	public void displayDieEach () {
		System.out.print("Starting roll: ");
		Board.displayChecker(Board.X_PLAYER_ID);
		System.out.print(" rolls " + dice[0] + " and ");
		Board.displayChecker(Board.O_PLAYER_ID);
		System.out.println(" rolls " + dice[1] + ".");
		if (winner == DRAW) {
			System.out.println("Tie - roll again.");
		}
		else {
			System.out.print("Player ");
			Board.displayChecker(winner);
			System.out.println(" starts.");
		}
		return;
	}
	
	
	public int getDie (int index) {
		return dice[index];
	}
	
	
	public boolean isDouble () {
		return (dice[0] == dice[1]);
	}

	
	public ArrayList<Integer> getDiceList () {
		ArrayList<Integer> diceList = new ArrayList<Integer>();
		diceList.add(dice[0]);
		diceList.add(dice[1]);
		if (isDouble()) {
			diceList.add(dice[0]);
			diceList.add(dice[1]);
		}		
		return diceList;
	}
	
	
}
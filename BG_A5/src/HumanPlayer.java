import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer {

	public static final int QUIT = 0;
	public static final int PASS = 1;
	public static final int PLAY = 2;
	
	private int playerId;
	private Play enteredPlay;
	private Board gameBoard;
	private Dice gameDice;	

	
	HumanPlayer (int setPlayerId, Board setBoard, Dice setDice) {
		playerId = setPlayerId;
		gameBoard = setBoard;
		gameDice = setDice;
	    return;
	}
	
	
	public int getPlayerId () {
		return playerId;
	}
	
	
	public Play getPlay () {
		return enteredPlay;
	}
	
	
	public int getDecision () {
		// if user enters "quit" the return value is null
		// otherwise the return value is equal to the move entered
	
		Scanner in = new Scanner(System.in);
		String command;
		String[] commandArray;
		int[] intCommandArray;
		boolean valid = false;
		int response = 0;
		ArrayList<Play> allPlayList;
		
		do {
//			System.out.print("Play options: ");
//			allPlayList = gameBoard.allPossiblePlays (playerId, gameDice);
//			System.out.println(allPlayList);
			
			System.out.print("Enter move for player ");
			Board.displayChecker(playerId);
			System.out.print(": \t");

			command = in.nextLine();
			command = command.trim();
			
			if (command.equals("quit")) {
				valid = true;
				response = QUIT;
				enteredPlay = null;
			}
			else if (command.equals("pass")) {
				valid = true;
				response = PLAY;
				enteredPlay = new Play();
			}
			else if (command.matches("([0-9]{1,2}( )*-( )*[1-6])(( )+[0-9]{1,2}( )*-( )*[1-6]){0,3}( )*")) {
				commandArray = command.split("( )*-( )*|( )+");
				intCommandArray = new int[commandArray.length];
				for (int i=0; i<commandArray.length; i++) {
					intCommandArray[i] = Integer.parseInt(commandArray[i]);
				}
				valid = true;
				response = PLAY;
				enteredPlay = new Play(intCommandArray);
			}
			else {
				valid = false;
			}
			
			if (!valid) {
				System.out.println("Command syntax error");
			}

		} while (!valid);
		
		
		return response;
	}
	
	
	
}
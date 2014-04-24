import java.util.ArrayList;
import java.util.Collections;

public class Board {
	
	private static final int[] RESET = {0,0,0,0,0,0,5,0,3,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,2,0};
	public static final int BAR  = 25;       // index of the BAR
	public static final int OFF  = 0;        // index of the BEAR OFF
	public static final int INNER_END = 6;   // index for the end of the inner board
	public static final int NUM_PIPS = 26;   // including BAR and BEAR OFF 
	private static final int NUM_PLAYERS = 2; 
	private static final int NUM_CHECKERS = 15; 
	public static final int X_PLAYER_ID = 0;
	public static final int O_PLAYER_ID = 1;

	// FOR DEBUG ONLY: mock resets to test bear off:
	// Backgammon
	//private static final int[] RESET = {14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
	// Gammon
	//private static final int[] RESET = {14,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//Single
	//private static final int[] RESET = {14,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};	
	
	
	public int[][] checkers = new int[NUM_PLAYERS][NUM_PIPS];
			// 2D array of checkers
	        // 1st index: 0 = X_PLAYER_ID's checkers, 1 = O's checkers 
			// 2nd index: number of checkers on each pip, 0 to 25
			// pip 0 is bear off, pip 25 is the bar, pips 1-24 are on the baord
	private int winnerId;
	
	
	Board () {
		for (int p=0; p<NUM_PLAYERS; p++)  {
			for (int i=0; i<NUM_PIPS; i++)   {
				checkers[p][i] = RESET[i];
			}
		}
		return;
	}
	
	
	Board (Board copyBoard) {
		for (int p=0; p<NUM_PLAYERS; p++)  {
			for (int i=0; i<NUM_PIPS; i++)   {
				checkers[p][i] = copyBoard.checkers[p][i];
			}
		}
		return;
	}
	

	
	public int opposingPlayer (int playerId) {
		int oppPlayerId;
		if (playerId == X_PLAYER_ID) {
			oppPlayerId = O_PLAYER_ID;
		}
		else {
			oppPlayerId = X_PLAYER_ID;
		}
		return oppPlayerId;
	}
	
	
	public int opposingPip (int pip) {
		return BAR-pip;
	}
	
	
	private int findLastChecker (int playerId) {
		int lastChecker = BAR;
		while (checkers[playerId][lastChecker] == 0) {
			lastChecker--;
		}
		return lastChecker;
	}
	
	
	public static void displayChecker (int playerId) {
		if (playerId == X_PLAYER_ID) {
			System.out.print("X");
		}
		else {
			System.out.print("O");
		}
		return;
	}

	
	private void displayPip (int pip) {
		// display the number of checkers on a pip
		int oppPip = opposingPip(pip);  
		
		if  (checkers[X_PLAYER_ID][pip] > 0) {
			displayChecker(X_PLAYER_ID);
			System.out.print(checkers[X_PLAYER_ID][pip] + "  ");
		}
		else
			if (checkers[O_PLAYER_ID][oppPip] > 0) {
				displayChecker(O_PLAYER_ID);
				System.out.print(checkers[O_PLAYER_ID][oppPip] + "  ");
			}
			else {
				System.out.print("|   ");		
			}
		return;
	}	
	
	
	private  void displayOffBoard (int playerId, int pip) {
		// display the number of checkers on the bar or on the bear off
		if  (checkers[playerId][pip] > 0) {
			displayChecker(playerId);
			System.out.print(checkers[playerId][pip] + "  "); 
		}
		else
			System.out.print("    ");			
		return;
	}
	
	
	public void displayBoard (int playerId) {
		// display the whole board
		// board is facing player 0
		// player 0 is X_PLAYER_ID anti-clockwise, player 1 is O clockwise
		
		// far boards
		if (playerId == X_PLAYER_ID) {
			System.out.println("13--+---+---+---+---18 BAR  19--+---+---+---+---24  OFF");
		}
		else {
			System.out.println("12--+---+---+---+---07 BAR  06--+---+---+---+---01  OFF");
		}
		
		for (int i=13; i<=18; i++) {
			displayPip(i);                // player 1 outer board
		}
		displayOffBoard(X_PLAYER_ID,BAR);       // player 0 bar
		for (int i=19; i<=24; i++) {
			displayPip(i);                // player 1 inner board
		}
		displayOffBoard(O_PLAYER_ID,OFF);         // player 1 bear off
		System.out.println("");

		// separator
		System.out.println("");

		// near boards
		for (int i=12; i>=7; i--) {
			displayPip(i);                 // player 0 outer board
		}
		displayOffBoard(O_PLAYER_ID,BAR);          // player 1 bar
		for (int i=6; i>=1; i--) {
			displayPip(i);                 // player 0 inner board
		}
		displayOffBoard(X_PLAYER_ID,OFF);        // player 0 bear off
		System.out.println("");

		if (playerId == X_PLAYER_ID) {
			System.out.println("12--+---+---+---+---07 BAR  06--+---+---+---+---01  OFF");
		}
		else {
			System.out.println("13--+---+---+---+---18 BAR  19--+---+---+---+---24  OFF");			
		}
		
		System.out.println("");
		
		return;
	}	
	
	public int displayResult () {
		int oppLastChecker, oppPlayerId;

		oppPlayerId = opposingPlayer(winnerId);

    	System.out.print("PLAYER ");
    	displayChecker(winnerId);
    	System.out.print(" WINS ");
    	oppLastChecker = findLastChecker(oppPlayerId);
    	if (oppLastChecker >= opposingPip(INNER_END)) {
    		System.out.println("A BACKGAMMON!!!");
    	}
    	else if (oppLastChecker > INNER_END) {
    		System.out.println("A GAMMON!!");
    	}
    	else {
    		System.out.println("A SINGLE!");
    	}
		return winnerId;
	}
	
	
	private ArrayList<Play> searchForPlays (int playerId, ArrayList<Integer> diceList, Board currentBoard) {
		int oppPlayerId, currentDie, lastChecker, oppEndPip;
		Play newPlay;
		ArrayList<Play> playList  = new ArrayList<Play>(), childPlayList = new ArrayList<Play>();
		
		oppPlayerId = opposingPlayer(playerId);
		currentDie = diceList.get(0);
		
		lastChecker = currentBoard.findLastChecker(playerId);
		
		for (int currentStartPip=BAR; currentStartPip>0; currentStartPip--) {
			oppEndPip = opposingPip(currentStartPip-currentDie);
			if ( ( (currentStartPip > currentDie) &&                                    // normal move
				   (currentBoard.checkers[playerId][currentStartPip] > 0) && 
				   (currentBoard.checkers[oppPlayerId][oppEndPip] <= 1) )  ||
				 ( (currentStartPip == currentDie) &&                                   // exact bear off
				   (currentBoard.checkers[playerId][currentStartPip] > 0) &&
				   (lastChecker <= INNER_END) ) ||
				 ( (currentStartPip < currentDie) &&                                    // short bear off
				   (currentStartPip == lastChecker) &&
				   (currentBoard.checkers[playerId][currentStartPip] > 0) &&
				   (lastChecker <= INNER_END)) ) {
				Move newMove = new Move(currentStartPip,currentDie);
				Board childBoard = new Board(currentBoard);
				if ( (diceList.size() > 1) && (checkers[playerId][BAR] != NUM_CHECKERS) ) {   
					childBoard.doMove(playerId, newMove);                                // search for children
					ArrayList<Integer> childDiceList = new ArrayList<Integer>(diceList);
					childDiceList.remove(0);
					childPlayList = searchForPlays(playerId,  childDiceList, childBoard);
				}
				if (childPlayList.isEmpty()) {        
					newPlay = new Play();                                                // no children, create new play with found move
					newPlay.addMove(newMove);
					playList.add(newPlay);
				}
				else {
					for (int i=0; i<childPlayList.size(); i++) {                         // have children, add found move to them
						newPlay = childPlayList.get(i);
						newPlay.addMove(new Move(currentStartPip,currentDie));
						childPlayList.set(i,newPlay);
					}
					playList.addAll(childPlayList);
				}
			}
		}
		
		return playList;
	}
	
		
	public ArrayList<Play> allPossiblePlays (int playerId, Dice dice) {
		ArrayList<Integer> diceList = new ArrayList<Integer>();
		ArrayList<Play> playList = new ArrayList<Play>();
		int maxLength = 0, playIndex, maxByPips = 0, numOnBar, numFromBar;
		Play thisPlay;
		
		diceList = dice.getDiceList();	                   // search for plays in dice order
		playList.addAll(searchForPlays(playerId, diceList, this));     
		
		if (!dice.isDouble()) {                            // search for plays in reverse dice order
			Collections.reverse(diceList);
			playList.addAll(searchForPlays(playerId, diceList, this));		
		}
		
		numOnBar = checkers[playerId][BAR];                // must play checkers on bar first
		if (numOnBar > 0) {
			playIndex = 0;
			while (playIndex < playList.size()) {
				thisPlay = playList.get(playIndex);
				numFromBar = 0;
				while ( (numFromBar < thisPlay.length()) && (thisPlay.getMove(numFromBar).getFromPip() == BAR) ) {
					numFromBar++;
				}
				if ( (numFromBar < numOnBar) && (numFromBar < thisPlay.length() ) ) {
					playList.remove(playIndex);
				}
				else {
					playIndex++;
				}
			}
		}		

		for (int i=0; i<playList.size(); i++) {              // remove plays with fewer than max number of moves
			if (playList.get(i).length() > maxLength) {
				maxLength = playList.get(i).length();
			}
		}
		playIndex = 0;
		while (playIndex < playList.size()) {                    
			if (playList.get(playIndex).length() < maxLength) {
				playList.remove(playIndex);
			}
			else {
				playIndex++;
			}
		}
		
		if (maxLength == 1) {                                 // remove single move plays with smaller die
			for (int i=0; i<playList.size(); i++) {
				if (playList.get(i).getMove(0).getByPips() > maxByPips) {
					maxByPips = playList.get(i).getMove(0).getByPips();
				}				
			}
			playIndex = 0;
			while (playIndex < playList.size()) {
				if (playList.get(playIndex).getMove(0).getByPips() < maxByPips) {
					playList.remove(playIndex);
				}		
				else {
					playIndex++;
				}
			}
		}
			
		return playList;
	}
	
	
	public boolean checkPlay (int playerId, Play play, Dice dice) {
		int startPip, byPips, endPip, oppPlayerId, oppEndPip, lastChecker;
		Board updateBoard = new Board(this); 
		ArrayList<Integer> diceList;
		Move move;
		boolean valid = true, longBearOff, found;   // unless proven otherwise
		ArrayList<Play> possiblePlayList;
		
		diceList = dice.getDiceList();	
		lastChecker = findLastChecker(playerId);
		
	    for (int i=0; i<play.length(); i++) {
	    	move     = play.getMove(i);
	    	startPip = move.getFromPip();
    		byPips   = move.getByPips();
    		endPip   = startPip - byPips;
    		if (endPip < 0) {
    			endPip = 0;
    			longBearOff = true;
    		}
    		else {
    			longBearOff = false;
    		}
    		oppPlayerId = opposingPlayer(playerId);
    		oppEndPip = opposingPip(endPip);
	    	if ((startPip < OFF) || (startPip > BAR)) {
	    		System.out.println("Invalid pip number: " + startPip);
	    		valid = false;
	    	}
	    	else if (updateBoard.checkers[playerId][startPip] < 1) {
	    		System.out.println("Not enough checkers at pip: " + startPip);
	    		valid = false;
	    	}
	    	else if (!diceList.contains(byPips)) {
	    		System.out.println("Roll not available: " + byPips);
	    		valid = false;
	    	}
	    	else if ((endPip < BAR) && (endPip > OFF) && (updateBoard.checkers[oppPlayerId][oppEndPip] > 1)) {
	    		System.out.println("Block at pip " + endPip);
	    		valid = false;
	    	}
	    	else if ((endPip == OFF) && (lastChecker > INNER_END)) {
	    		System.out.println("Can't bear off yet");
	    		valid = false;
	    	}
	    	else if (longBearOff && (startPip < lastChecker)) {
	    		System.out.println("Can only bear off the last man with a high roll");
	    		valid = false;
	    	}
	    	else if ((startPip != BAR) && (updateBoard.checkers[playerId][BAR] > 0)) {
	    		System.out.println("Must move the checker on the bar first");
	    		valid = false;
	    	}
	    	else {
	    		updateBoard.checkers[playerId][startPip]--;
	    		updateBoard.checkers[playerId][endPip]++;
	    		diceList.remove(diceList.indexOf(byPips));
	    		lastChecker = updateBoard.findLastChecker(playerId);
	    	}
	    }

	    if (valid) {
		    possiblePlayList = allPossiblePlays (playerId, dice);
	    	found = false;
		    for (Play possiblePlay : possiblePlayList) {
		    	if (possiblePlay.equals(play)) {
		    		found = true;
		    	}
		    }
		    if (!found) {
		    	System.out.println("Must make valid play");
		    	valid = false;
		    }
	    }
	    
		return valid;
	}
	
	
	
	private void doMove (int playerId, Move move) {
		int startPip, endPip, oppPip, oppPlayerId;

		oppPlayerId = opposingPlayer(playerId);	
    	startPip = move.getFromPip();
    	checkers[playerId][startPip] -= 1;
		endPip   = startPip - move.getByPips();
 		if (endPip < OFF)	{							// check for long bear offs
    		endPip = OFF;   
 		}
    	checkers[playerId][endPip] += 1;
    	oppPip = opposingPip(endPip);
    	if ((checkers[oppPlayerId][oppPip]==1) && (endPip!=BAR) && (endPip!=OFF))	{	// check for HIT
    		checkers[oppPlayerId][oppPip] -= 1;
    		checkers[oppPlayerId][BAR] += 1;
    	}
		return;
	}
	
	
	
	public boolean doPlay (int playerId, Play play) {
		Move move;
		boolean finished;
			
	    for (int i=0; i<play.length(); i++) {
	    	move = play.getMove(i);
	    	doMove(playerId,move);
	    }	
	    
	    if (checkers[playerId][OFF] == NUM_CHECKERS) {
	    	winnerId = playerId;
	    	finished = true;
	    }
	    else {
	    	finished = false;
	    }
	    
	    return finished;
	}
	public void reset(){
		for (int p=0; p<NUM_PLAYERS; p++)  {
			for (int i=0; i<NUM_PIPS; i++)   {
				checkers[p][i] = RESET[i];
			}
		}
	}

}
import java.util.ArrayList;

public class Play {
	// a play contains 0-4 moves
	public static final int MAX_MOVES = 4;
	public static final int MAX_STEPS = 2*MAX_MOVES;
	
	private ArrayList<Move> moveList = new ArrayList<Move>();
		
	
	Play (Play copyPlay) {
		for (int i=0; i<copyPlay.length(); i++) {
			Move newMove = new Move(copyPlay.getMove(i));
			moveList.add(newMove);
		}
		return;
	}
	
	
	Play (int... pips) {
		int numMoves;
		moveList.clear();
		numMoves = pips.length / 2;
		for (int i=0; i<numMoves; i++ ) {
			Move newMove = new Move(pips[2*i],pips[2*i+1]);
			moveList.add(newMove);
		}
		return;
	}


	public void addMove (Move move) {
		moveList.add(0,move);
		return;
	}
	
	
	public Move getMove (int index) {
		return moveList.get(index);
	}
	
	
	public boolean equals (Play comparison) {
		boolean same = true;   // unless proven otherwise
		if (moveList.size() == comparison.length()) {
			for (int i=0; i<moveList.size(); i++) {
				if (!moveList.get(i).equals(comparison.getMove(i))) {
					same = false;
				}
			}			
		}
		else {
			same = false;
		}
		return same;
	}
	
	
	public int length() {
		return moveList.size();
	}
	
	
	public String toString() {
		String text = new String("");
		for (int i=0; i<moveList.size()-1; i++) {
			text += moveList.get(i) + " ";
		}
		if (moveList.size() > 0) {
			text += moveList.get(moveList.size()-1);
		}
		return text;
	}

	
	public void displayPlay(int playerId) {
		System.out.print("Player "); 
		Board.displayChecker(playerId);
		if (moveList.isEmpty()) {
			System.out.println(" has no moves.");
		}
		else {
			System.out.println(" plays " + this + ".");
		}
		return;
	}
	
}
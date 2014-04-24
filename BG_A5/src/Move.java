public class Move {
	private int fromPip = 0;
	private int byPips = 0;;
	
	
	Move (Move copyMove) {
		fromPip = copyMove.fromPip;
		byPips = copyMove.byPips;
	}
	
	
	Move (int setFromPip, int setByPips) {
		fromPip = setFromPip;
		byPips = setByPips;
		return;		
	}
	
	
	public int getFromPip () {
		return fromPip;
	}
	
	
	public int getByPips () {
		return byPips;
	}
	
	
	public boolean equals (Move comparison) {
		boolean same;
		if ( (fromPip == comparison.getFromPip()) && (byPips == comparison.getByPips()) ) {
			same = true;
		}
		else {
			same = false;
		}
		return same;
	}
	
	public String toString() {
		return fromPip + "-" + byPips;
	}
}
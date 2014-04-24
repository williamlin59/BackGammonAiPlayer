 public class BgOnePlayer {

		
	private static final int HUMAN_ID = Board.X_PLAYER_ID;
	private static final int BOT_ID = Board.O_PLAYER_ID;
	 
	public static void main (String[] args) {

		Board gameBoard = new Board();
		Dice gameDice = new Dice();
		Play currentPlay = new Play();
		HumanPlayer human = new HumanPlayer(HUMAN_ID, gameBoard, gameDice);
		AiPlayer bot = new AiPlayer(BOT_ID, gameBoard, gameDice);
		int winner, response = HumanPlayer.PLAY;	
		boolean validPlay = true, finished = false, firstMove = true;
		
		do {
			winner = gameDice.rollDieEach();
			gameDice.displayDieEach();
		} while (winner == Dice.DRAW);
		
		do {
			if (firstMove) {
				gameBoard.displayBoard(HUMAN_ID);
			}
			if ( (!firstMove) || (firstMove && (winner == BOT_ID)) ) {
				gameDice.rollDice();
				gameDice.displayDice(BOT_ID);
				currentPlay = bot.getPlay();
				currentPlay.displayPlay(BOT_ID);
				finished = gameBoard.doPlay(BOT_ID, currentPlay);
				gameBoard.displayBoard(HUMAN_ID);
			}
			if (!finished) {
				gameDice.rollDice();
				gameDice.displayDice(HUMAN_ID);
				do {
					response = human.getDecision();     // PLAY or QUIT
					if (response == HumanPlayer.PLAY) {
						currentPlay = human.getPlay();
						validPlay = gameBoard.checkPlay(HUMAN_ID, currentPlay, gameDice);
					}
				}
				while ( (response == HumanPlayer.PLAY) && (!validPlay) );
				if (response == HumanPlayer.PLAY) {
					finished = gameBoard.doPlay(HUMAN_ID, currentPlay);
					gameBoard.displayBoard(HUMAN_ID);
				}
			}
			firstMove = false;
		} while ( (response == HumanPlayer.PLAY) && (!finished) );
	
		if (finished) {
			gameBoard.displayResult();
		}
		
		System.out.println("Game Complete");
		
		return;
	}
	
 }
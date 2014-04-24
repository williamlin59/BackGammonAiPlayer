/**Not used anymore was used early in project, permissions were temporarily changed*/


public class BotTester {
	private static final int BOT2_ID = Board.X_PLAYER_ID;
	private static final int BOT1_ID = Board.O_PLAYER_ID;
	 
	public static void main (String[] args) {
		Board gameBoard = new Board();
		Dice gameDice = new Dice();
		Play currentPlay = new Play();
		
		AiPlayer bot1 = new AiPlayer(BOT1_ID, gameBoard, gameDice);
		aaa bot2 = new aaa(BOT2_ID, gameBoard, gameDice);
		int winner;//, response = HumanPlayer.PLAY;	
		boolean validPlay = true, finished = false, firstMove = true;
		int bot1Score = 0;
		int bot2Score = 0;
		for(int i=0;i<10000;i++){//the time of playing games.
			gameBoard.reset();
			System.out.print("Game "+i);
		do {
			winner = gameDice.rollDieEach();
//			gameDice.displayDieEach();
		} while (winner == Dice.DRAW);
			int turn=winner;
		do {
			if (firstMove&&turn==0) {
//				gameBoard.displayBoard(BOT1_ID);
			}
			else if(firstMove&&turn==1){
//				gameBoard.displayBoard(BOT2_ID);
			}
			if (  turn%2==0 ) {
				gameDice.rollDice();
//				gameDice.displayDice(BOT1_ID);
				currentPlay = bot1.getPlay();
//				currentPlay.displayPlay(BOT1_ID);
				finished = gameBoard.doPlay(BOT1_ID, currentPlay);
//				gameBoard.displayBoard(BOT2_ID);
			}
			else  {
				gameDice.rollDice();
//				gameDice.displayDice(BOT2_ID);
				currentPlay = bot2.getPlay();
//				currentPlay.displayPlay(BOT2_ID);
				finished = gameBoard.doPlay(BOT2_ID, currentPlay);
//				gameBoard.displayBoard(BOT1_ID);
			}
			turn++;
		} while  (!finished ) ;
		System.out.print(" - finished\n ");
		int score = 0;
		if (finished) {
			score=gameBoard.displayResult();
		}
		if(score==BOT1_ID){
			bot1Score++;
		}
		else{
			bot2Score++;
		}
		}
		System.out.println("Bot1Score is "+bot1Score+"   "+"Bot2Score is "+bot2Score);
		System.out.println("Game Complete");
		
		return;
	}
}
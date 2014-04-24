/**
 * Assignment 5
 * Team Oreos
 * Name:					Student No.		GitHub
 * Maria Treacy				09724940		Maria47127	
 * Songjun (William) Lu		12251990		williamlin59 
 * Sofi Drury				12302361		SofiD
 * 
 * */

import java.util.ArrayList;

public class AiPlayer {

	private int playerId;
	private Board gameBoard;
	private Dice gameDice;
	private int opponentId;
	
	
	AiPlayer (int setPlayerId, Board setBoard, Dice setDice) {
		playerId = setPlayerId;
		gameBoard = setBoard;
		gameDice = setDice;
	    return;
	}
	
	
	public int getPlayerId () {
		return playerId;
	}
	
	/**
	 * Method to get the opponents Id
	 * */
	private void getOpponent(){
		 opponentId= gameBoard.opposingPlayer(playerId);
	}

	
	/**
	 * Method to locate the last checker similar to the one in Board Class.
	 * */
	@SuppressWarnings("static-access")
	private int findLastChecker (int playerId,Board board) {
		int lastChecker = board.BAR;
		while (board.checkers[playerId][lastChecker] == 0) {
			lastChecker--;
		}
		return lastChecker;
	}
	
	/**
	 * Method to count pips in a specified area for a specified team
	 * */
	private int countArea(int Id,Board selectedBoard,int start,int end){
		int count=0;
		for (int i = start; i<=end; i++){
			count += selectedBoard.checkers[Id][i];
		}
		return count;
	}

	
	/**
	 * Method to score the play for Blitz Strategy
	 * Call submethods to count hits mades, 
	 * uses fatalBlitz for increased score when pip was on the inner board
	 * */
	private double[] Blitz ( ArrayList<Board> allBoardsList) {
		int listSize = allBoardsList.size();
		double[] score=new double[listSize];		
		double hits=0;
		int countOpPip = countArea(opponentId,gameBoard,1,6);					//original number of opponent pips on inner board.
		for(int currentBoard = 0; currentBoard < listSize; currentBoard++){
			hits=allBoardsList.get(currentBoard).checkers[opponentId][25];  //gets the number of hits on the board.
			hits+= fatalBlitz(allBoardsList.get(currentBoard),countOpPip);  //gets the number of hits on inner board and increase score. 
			score[currentBoard] = hits*3;									//weighting is applied to the score.
		}
		return score;
		
	}
	/**
	 * Method to find if there has been an opponent pip taken, 
	 * that was in their inner Board.
	 * And gives these moves a score
	 * */
		private double fatalBlitz(Board current, int original){
		  double bonus =0;
		  bonus+= original - countArea(opponentId,current,1,6);			//by subtracting from original count n inner board can find out if there has been a change
		  bonus+= (original - countArea(opponentId,current,7,12))*0.5;//*0.70;	
		  return bonus;
	}

	
	
	
	/**
	 * Method to Score for the Holding/Back Game Strategy
	 * Uses submethod countArea to count pips in specified section of the board.
	 * */
	private double[] holdingGame(ArrayList<Play> allPlayList){
		int count = countArea(playerId,gameBoard,0,10);							//gets number of pips in the inner board and with in a few dice rolls
		int listSize = allPlayList.size();										
		float moves = gameDice.getDiceList().size();							//get number of possible dice to use,float to avoid integer division error
		int startingBlock = countArea(playerId,gameBoard,19,25); 				//counts pips in starting area/ area nearest bar
		double[] score= new double[listSize];
		
			for (int j=0; j < listSize; j++  ){									
				if(count >= 10){												//play strategy for 10 or more pips almost home/off
					for(int d = 0; d< allPlayList.get(j).length();d++){
					
							if(allPlayList.get(j).getMove(d).getFromPip()>10){  //score increase each die that moves pips from further back
								score[j]+= (1/moves);
							}
						
					}
				}
			
				else {															//alternate strategy
					for(int d = 0; d< allPlayList.get(j).length();d++){
						
							int from =allPlayList.get(j).getMove(d).getFromPip();
							if(from > 18 && from <= 25 && startingBlock >3){ 	//when there are more than 3 pips to be moved from ear start they are priority
								score[j]+= (1/moves);								
							}
							
							else if(from > 6 && from < 19 && startingBlock <= 3){ //when 3 or less they are held back for hit opportunities until approaching bear off valid
								score[j]+= (1/moves);
							}
							else{
								score[j]+= 0;
							}
							
						
					}
					
				}
				score[j]=0.45*score[j];											//strategy weighting
			}	
			
			return score;
	}


	
	
	/**
	 * Method to Score the Number Home Sub Strategy
	 * Higher Score for more bore off checkers.
	 * */
	@SuppressWarnings("unused")
	private double[] Numberhome(ArrayList<Board> allBoardsList){
		int listSize = allBoardsList.size();
		double score[]=new double[listSize];
		if(countArea(playerId,gameBoard, 0, 6) == 15){
			int initialOff = gameBoard.checkers[playerId][0];
			int totalNewBear; 
			int currentTotalBear;
			for(int currentBoard=0; currentBoard<listSize;currentBoard++){
				currentTotalBear =  allBoardsList.get(currentBoard).checkers[playerId][0];
				totalNewBear = currentTotalBear-initialOff;
				score[currentBoard]= totalNewBear*5;
			}
		}
		else{
			for(int currentBoard=0; currentBoard<listSize;currentBoard++){
				score[currentBoard]=0;
			}
		}
		return score;
		
	}
		
	/**
	 * Method to score the play for Safety strategy 
	 * Multiple sections to avoid repeating for loop several times for all subsections.
	 * */
	public double[] Safety(Dice dice,ArrayList<Board> allBoardList){		
		double [] grade=new double[allBoardList.size()];
		double[][] score=new double[4][allBoardList.size()];
		//counters for each checking
		int j=0;
		for(Board board : allBoardList){							//get each board from allboardList
			
			/**gets socre for entire board*/
			int blocks=0;
			int blots=0;
			
			for(int i=1;i<25;i++){
				if(board.checkers[playerId][i]==1){
					blots++;
				}
				else if(board.checkers[playerId][i]>1){
					blocks++;
				}
			}
			
			score[0][j]=(blocks-blots);								//score for the whole board
			
			/**gets inner board block and blots scores*/
			blocks=0;
			blots=0;
			for(int b=1;b<7;b++){
				if(board.checkers[playerId][b]==1){
					blots++;
				}
				else if(board.checkers[playerId][b]>1){
					blocks++;
				}
			}
			if(findLastChecker(playerId,board)<findLastChecker(opponentId,board))
				score[1][j]=(blocks-blots)*2;							//score for the inner board
			else
				score[1][j]=0;
			
			
			/**get number of consecutive blocks*/
			int c=1;
			int maxPrime=0;
			while(c<25){
				int prime=0;
				
				while(board.checkers[playerId][c]>1&&c<25){
					prime++;
					c++;
				}
				if(prime>maxPrime)	maxPrime=prime;
				if(prime==0&&c<25)	c++;
				
			}
			if(findLastChecker(playerId,board)<findLastChecker(opponentId,board))
				score[2][j]=maxPrime*2;								//prime score for the whole board
			else
				score[2][j]=0;
			
			/**get number of consecutive blocks inner board*/
			int innerMaxPrime=0;
			int e=1;
			while(e<7){
				int prime=0;
				
				while(board.checkers[playerId][e]>1&&e<7){
					prime++;
					e++;
				}
				if(prime>innerMaxPrime)	innerMaxPrime=prime;
				if(prime==0&&e<7)	e++;
				
			}
			if(findLastChecker(playerId,board)<findLastChecker(opponentId,board))
				score[3][j]=innerMaxPrime*2;//prime score for the inner board
			else
				score[3][j]=0;	
			
			
			/**weights the score  from the total of the subscores*/
			grade[j]=0.85*(score[0][j]+score[1][j]+score[2][j]+score[3][j]);

			j++;
			
		}

		return grade;
	}	
	
	
	/**
	 * Method to Score for Basic Racer Strategy
	 * */
	private double[] Racer (ArrayList<Board> allBoardsList) {
		double[] score=new double[allBoardsList.size()];
		int a=0;

		for(Board board : allBoardsList){
			score[a]=(board.checkers[playerId][0]+board.checkers[opponentId][25]);	//generates a racer score for all the boards/plays
			a++;
				
		}
		return score;
	}

	/**
	 * Method to get Scores from all the strategy types and find the highest
	 * */
	public int findBestBoard(ArrayList<Board> allBoardsList,ArrayList<Play> allPlayList){
		getOpponent();
		double max=0;
		double []SafeScore=Safety(gameDice, allBoardsList);
		double []RacerScore=Racer ( allBoardsList);
		double []NumberhomeScore= Numberhome( allBoardsList);
		double[]HoldingGameScore=holdingGame(allPlayList);
		double []BlitzScore = Blitz (allBoardsList);
		for(int i=0;i<allBoardsList.size();i++){										//goes through all to get the maximum score.
			if(SafeScore[i]+RacerScore[i]+BlitzScore[i]+HoldingGameScore[i]+NumberhomeScore[i]>max){
				max= SafeScore[i]+RacerScore[i]+BlitzScore[i]+HoldingGameScore[i]+NumberhomeScore[i];
			}
		}
		for(int j=0;j<allBoardsList.size();j++){										//finds a match for highest
			if(SafeScore[j]+RacerScore[j]+BlitzScore[j]+HoldingGameScore[j]+NumberhomeScore[j]==max){
				return j;
			}
		}
		return 0;
	}

	
	
	public Play getPlay () {
		ArrayList<Play> allPlayList;
		ArrayList<Board> allBoardsList = new ArrayList<Board>();
		int bestBoard;
		Play chosenPlay;
		
		allPlayList = gameBoard.allPossiblePlays (playerId, gameDice);
		if (!allPlayList.isEmpty()) {
			for (int i=0; i<allPlayList.size(); i++) {
				allBoardsList.add(new Board(gameBoard));
				allBoardsList.get(i).doPlay(playerId, allPlayList.get(i));
			}
			bestBoard = findBestBoard(allBoardsList,allPlayList);
			chosenPlay = allPlayList.get(bestBoard);
		}
		else {
			chosenPlay = new Play();
		}
		
		return chosenPlay;
	}
}
package othello;
public class Board {
	private final int BOARD_WIDTH = 8;
	private final int BOARD_HEIGHT = 8;
	private int flippedDisc = 0;
	private int blackScore = 0;
	private int whiteScore = 0;
	private boolean gameOver = false;
	private Disc[][] board = new Disc[BOARD_HEIGHT][BOARD_WIDTH];
	/**
	 * so far it only prints out the board later it will populate with initial discs
	 */
	public Board()
	{//start board
	this.populate();
	}//start board
	
	
	/**
	 * populates the board with neutral discs, resets the score, and makes the game playable
	 */
	public void populate() {
		this.resetBlackScore();
		this.resetWhiteScore();
		this.gameOver = false;
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int column = 0; column < BOARD_WIDTH; column++) {
				board[row][column] = new Disc(0,row,column);
			}//end of column for loop
		}//end of row for loop
	}
	
	/**
	 * overrides standard toString to print out the board
	 */
	public String toString() {
		System.out.print("   ");
		for(int i = 0; i < BOARD_HEIGHT; i++) {
			System.out.printf("%-4d",i+1);
		}
		System.out.println();
		String cell = "_%s_|"; 
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			System.out.print(row + 1 +"|");
			for(int column = 0; column < BOARD_WIDTH; column++) {
				System.out.printf(cell,board[row][column].getCurrentColor());
			}//end of column for loop
			System.out.println();
		}//end of row for loop	
		return board.toString();
	}
	
	
	
	/**
	 * makes the board aware that a player passed by reference is going to claim a neutral disc
	 * @param p is of the player class 
	 */
	public void takeTurn(Player p) {
		this.updateBoard();
		p.turn(System.in);
		if(p.getPassCon() == true) {
			System.out.println();
			return;
		}
		if(p.getQuitCon() == true) {
			this.quitGame(p);
			this.gameOver = true;
			return;
		}
		flippedDisc = 0;//resets the value of flippedDisc to see if any discs are claimed
		if(this.checkMoves(p) == true) {
			return;
		}
		else {
		this.takeTurn(p);
		}
		
	}
	/**checks all 8 possible spots around the players move choice and validates it
	 * @param p is of the player class
	 * @return boolean
	 */
	public boolean checkMoves(Player p) {
		if(board[p.getRowMove()][p.getColumnMove()].getState() != 0)return false;
		if(board[p.getRowMove()][p.getColumnMove()].getState() == p.getId())return false;
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, 0, 1)) {//checking to the right
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, 0, 1);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, 0, -1)){//checking to the left
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, 0, -1);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, 1, 0)) {//checking down
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, 1, 0);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, -1, 0)){//checking up
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, -1, 0);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, 1, 1)) {//checking down right
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, 1, 1);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, 1, -1)){//checking down left
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, 1, -1);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, -1, 1)){//checking up right
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, -1, 1);}
		if(this.validateMove(p.getRowMove(), p.getColumnMove(), p, -1, -1)) {//checking up left
			this.flipDirection(p.getRowMove(), p.getColumnMove(), p, -1, -1);}
		if(flippedDisc == 0) {return false;}//if no change then the move is not a valid one
		return true;
	}
	
	/**
	 * method for quitting the game if a player is done
	 * the board must quit with a reference to a player
	 * @param p
	 */
	public void quitGame(Player p) {
		if(p.getId() == 1) {
			for(int row = 0; row < BOARD_HEIGHT; row++) {
				for(int column = 0; column < BOARD_WIDTH; column++) {
					board[row][column].setState(2);
				}
			}
		}
		else {
			for(int row = 0; row < BOARD_HEIGHT; row++) {
				for(int column = 0; column < BOARD_WIDTH; column++) {
					board[row][column].setState(1);
				}
			}
		}
	}
	
	/**
	 * used to check if a disc has been successfully flipped over
	 * @param pop
	 * @param p
	 * @return Integer
	 */
	public int flipCheck(int pop,Player p) {
		int playerOneDiscs = 0;
		int playerTwoDiscs = 0;
		
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int column = 0; column < BOARD_WIDTH; column++) {//counts how many spaces are unclaimed
				if(board[row][column].getState() == 1) playerOneDiscs++;
				if(board[row][column].getState() == 2) playerTwoDiscs++;
			}
		}
		if(p.getId() == 1) {
			pop = playerOneDiscs;
		}
		else if(p.getId() == 2) {
			pop = playerTwoDiscs;
		}
		return pop;
	}


	/**
	 * checks to see whether a certain number is within the bounds of the board
	 * @param num
	 * @return boolean
	 */
	public boolean isInBounds(int num) {
		if(num < 8 && num >=0) return true;
		else return false;
	}
	/**
	 * checks to see whether the player piece is in the board bounds
	 * @param row
	 * @param column
	 * @return boolean
	 */
	public boolean isInBounds(int row, int column) {
		if((row < 8 && row >=0 ) && (column < 8 && column >= 0)) return true;
		else return false;
	}
	/**
	 * this move checks whether a move is valid by checking the entire row,column, or diagonal
	 * @param row
	 * @param column
	 * @param p
	 * @param dRow
	 * @param dColumn
	 * @return boolean
	 */
	public boolean validateMove(int row, int column, Player p, int dRow, int dColumn) {
		row+=dRow;
		column+=dColumn;
		if(!this.isInBounds(row, column))return false;//checking board bounds
		for(;isInBounds(row,column); row+=dRow,column+=dColumn) {
			if(board[row][column].getState() == p.getId())return true;
			if(board[row][column].getState() == 0)return false;//checks to see if it is empty
		}
		return false;
	}
	private void flipDirection(int row, int column, Player p, int dRow, int dColumn) {
		board[row][column].setState(p.getId());
		for(;board[row+dRow][column+dColumn].getState() != p.getId(); row+=dRow,column+=dColumn) {
			board[row+dRow][column+dColumn].setState(p.getId());
			flippedDisc++;
		}
	}
	
	
	
	/**
	 * calculates the score of both players and formats the data into a readable csv style format
	 * @return String
	 */
	public String Score() {
		int spread;
		int whoWon = 0;
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int column = 0; column < BOARD_WIDTH; column++) {
				if(board[row][column].getCurrentColor() == "B") blackScore++;
				if(board[row][column].getCurrentColor() == "W") whiteScore++;
			}
		}
		spread = blackScore -whiteScore;
		if(spread > 0) {
			whoWon = 1;
		}
		else if(spread < 0) {
			whoWon = -1;
		}
		String message ="";
		message = String.format("Scores:,    black:, %d, white:, %d,   Spread:, %d, Win Score:, %d",this.getBlackScore(),this.getWhiteScore(),spread,whoWon);
		return message;
	}
	/**
	 * this method checks for any neutral discs left in the array
	 */
	public void winCondition(){
		int nuetralSpace = 0;
		int playerOne = 0;
		int playerTwo = 0;
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int column = 0; column < BOARD_WIDTH; column++) {//counts how many spaces are unclaimed
				if(board[row][column].getState() == 0) nuetralSpace++;
				if(board[row][column].getState() == 1) playerOne++;
				if(board[row][column].getState() == 2) playerTwo++;
			}
			
		}
		if(playerOne == 0 ||nuetralSpace == 0||playerTwo == 0 ) {
			this.toString();
			System.out.println("GAME OVER!!! THAT WAS FUN!!!");
			this.gameOver = true;
		}
		
	}
	/**
	 * Method that refresh the board after moves
	 */
	public void updateBoard() {
	this.toString();
	}//end of update board method
	
	public void startState() {
		board[3][3].setState(1);
		board[3][4].setState(2);
		board[4][4].setState(1);
		board[4][3].setState(2);
		
	}
	/**
	 * returns the value of blackScore
	 * @return Integer
	 */
	public int getBlackScore() {
		return blackScore;
	}
	/**
	 * returns the value of whiteScore
	 * @return Integer
	 */
	public int getWhiteScore() {
		return whiteScore;
	}
	/**
	 * resets the value of blackScore
	 */
	public void resetBlackScore() {
		blackScore = 0;
	}
	/**
	 * resets the value of whiteScore
	 */
	public void resetWhiteScore() {
		whiteScore = 0;
	}
	/**
	 * returns the value of the board width
	 * @return Integer
	 */
	public int getBOARD_WIDTH() {
		return BOARD_WIDTH;
	}
	/**
	 * returns the value of the board height
	 * @return Integer
	 */
	public int getBOARD_HEIGHT() {
		return BOARD_HEIGHT;
	}
	/**
	 * returns the value of the gameOver variable
	 * @return boolean
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	/**
	 * sets the value of game over to true when the game is over
	 */
	public void gameOver() {
		gameOver = true;
	}
	/**
	 * this returns the state of the game board
	 * @return Disc[][]
	 */
	public Disc[][] getBoard(){
		return board;
	}
	
}//end of Board class
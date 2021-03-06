package othello;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

	private int id;
	private int Arow,Acolumn;
	private boolean quitCon = false;
	private boolean passCon = false;
	public Player(int id) {
		this.setId(id);
	}
	
	/**
	 * returns the players disc color
	 * @return String
	 */
	public String getPlayerColor() {
		if(this.id == 1) {
			return "white";
		}
		else {return "black";}
	}
	
	
	/**
	 * returns player input -1 to make sure index is in range
	 * @return Integer
	 */
	public int getRowMove() {
		return Arow - 1;
	}
	/**
	 * returns player input -1 to make sure index is in range
	 * @return Integer
	 */
	public int getColumnMove() {
		return Acolumn -1;
	}
	/**
	 * returns the value for the quit condition
	 * @return boolean
	 */
	public boolean getQuitCon() {
		return quitCon;
	}
	/**
	 * returns the value for the pass condition
	 * @return boolean
	 */
	public boolean getPassCon() {
		return passCon;
	}
	
	/**
	 * returns the player Id
	 * @return Integer
	 */
	public int getId() {
		return id;
	}
	/**
	 * sets the player id the player Id
	 */
	private void setId(int id) {
		this.id = id;
	}
	/**
	 * returns the id of the other player
	 * @return Integer
	 */
	public int otherPlayer() {
		if(this.getId() == 1) return 2;
		if(this.getId() == 2) return 1;
		
		else return 0;
	}
	
	
	/**
	 * this function will allow the player to place a disc down in a possible coordinate spot
	 * @param in
	 */
	public void turn(InputStream in){
		quitCon = false;//resets for every turn so to not continuously quit
		passCon = false;//resets for every turn so to not continuously pass
		System.out.println(this.getPlayerColor() + " player it is your turn, please put in two whole numbers seperated by a space(do not include \"\" in your answer)\nas your move... for example \"8 5\" would place your piece "
				+ "on the 8th row 5th column\nif you would like to forfiet the match then type in \"quit\"(without quotations)"
				+ " or type in \"pass\" to pass your turn");
		Scanner scanner = new Scanner(in);
		int row = 0,column = 0;
		String quit = "quit";
		String pass = "pass";
		try {
			//checks for quit in stream
			if(scanner.hasNext(quit)) {
				quitCon = true;
				return;
			}
			else if(scanner.hasNext(pass)) {
				System.out.printf("%s player has passed their turn",this.getPlayerColor());
				passCon = true;
				return;
			}
			else if(scanner.hasNextInt() && scanner.hasNextInt()) {
				row = scanner.nextInt();
				column = scanner.nextInt();
				}
			else if((scanner.next() instanceof String)) {
				System.out.println("this value is not an int between 1 - 8\nPlease input an acceptable value and try again");
				System.out.println("trace 0");
				turn(in);
				return;
			}
			else {
				System.out.println("this value is not an int between 1 - 8\nPlease input an acceptable value and try again");
				System.out.println("trace 1");
				turn(in);
				return;
			}
			if(row > 8 || row <= 0 ||column <= 0 || column > 8) {
				System.out.println("this value is not an int between 1 - 8\nPlease input an acceptable value and try again");
				System.out.println("trace 2");
				turn(in);
				return;
			}
		}
		catch (InputMismatchException e) {
			System.out.println("this value is not an int between 1 - 8\nPlease input an acceptable value and try again");
			System.out.println("trace 3");
			turn(in);
			return;
		}
		
		Arow = row;
		Acolumn = column;
	}
}

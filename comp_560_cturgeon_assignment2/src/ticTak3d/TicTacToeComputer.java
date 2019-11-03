package ticTak3d;

import java.util.Random;

public class TicTacToeComputer {
	
	private LocationUtility[][][] utilities;
	private int myInt;
	private int BOARDSIZE;
	
	private double EPSILON = 0.3; // exploration rate
	
	public TicTacToeComputer(int myInt, LocationUtility[][][] utilities) {
		this.utilities = utilities;
		this.myInt = myInt;
	}
	
	public TicTacToeComputer(int myInt, int boardSize) {
		this.myInt = myInt;
		this.BOARDSIZE = boardSize;
		utilities = new LocationUtility[boardSize][boardSize][boardSize];
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				for (int z = 0; z < boardSize; z++) {
					utilities[x][y][z] = new LocationUtility();
				}
			}
		}
	}
	
	
	public void printUtilities() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					System.out.printf("%.5f" + "  ", utilities[x][y][z].getUtility());
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public int getInt() {
		return myInt;
	}
	
	public void setUtility(int x, int y, int z, double value) {
		utilities[x][y][z].setUtility(value);
	}
	
	public double getUtility(int x, int y, int z) {
		return utilities[x][y][z].getUtility();
	}
	
	
	public boolean action() {
		Random r = new Random();
		if (r.nextDouble() <= EPSILON) {
			return true;
		}else {
			return false;
		}
	}
	
}

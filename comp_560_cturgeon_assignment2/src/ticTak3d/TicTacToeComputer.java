package ticTak3d;

import java.util.Random;

public class TicTacToeComputer {
	
	private LocationUtility[][][] utilities;
	private int myInt;
	private int BOARDSIZE;
	
	private double EPSILON = 0.25; // exploration rate
	private double ALPHA = 0.3; // learning rate
	
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
					if (utilities[x][y][z].getUtility() >= 0) {
						System.out.print(" ");
					}
					System.out.printf("%.3f" + "\t", utilities[x][y][z].getUtility());
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public int getInt() {
		return myInt;
	}
	
	
	/**
	 * the main utility function that allows for learning
	 * @param x
	 * @param y
	 * @param z
	 * @param value
	 * @param turn
	 */
	public void setUtility(int x, int y, int z, double value, double turn) {
		double utilValue = utilities[x][y][z].getUtility() + (ALPHA / turn) * (value - utilities[x][y][z].getUtility());				
		utilities[x][y][z].setUtility(utilValue);
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
	
	public double getEpsilon() {
		return EPSILON;
	}
	
}

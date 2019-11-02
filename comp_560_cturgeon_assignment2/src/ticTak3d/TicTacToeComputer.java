package ticTak3d;

public class TicTacToeComputer {
	
	private LocationUtility[][][] utilities;
	private int myInt;
	
	public TicTacToeComputer(int myInt, LocationUtility[][][] utilities) {
		this.utilities = utilities;
		this.myInt = myInt;
	}
	
	public TicTacToeComputer(int myInt, int boardSize) {
		this.myInt = myInt;
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				for (int z = 0; z < boardSize; z++) {
					utilities[x][y][z] = new LocationUtility();
				}
			}
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
	
}

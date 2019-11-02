package ticTak3d;

import java.util.Random;

public class TicTakLearner {
	
	
	public int[][][] BOARD;
	public int BOARDSIZE = 4;
	public int PLAYER;
	public int OPPONENT;
	public TicTacToeComputer COMPUTER2;
	public TicTacToeComputer COMPUTER1;
	
	
	
	public LocationUtility[][][] utilityFunctions;
	public int CONDITION;
	public int USERTURN;
	

	public TicTakLearner(int user) {
		
		if (user == 1) {
			PLAYER = 1;
		}else if (user == 2) {
			PLAYER = 2;
			COMPUTER2 = new TicTacToeComputer(2, BOARDSIZE);
		}
		
		
		
		BOARD = new int[BOARDSIZE][BOARDSIZE][BOARDSIZE];
		utilityFunctions = new LocationUtility[BOARDSIZE][BOARDSIZE][BOARDSIZE];
		
		
		// just random for new TODO
		Random r = new Random();
		double s = 0.0;
		
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					BOARD[x][y][z] = 0;
					utilityFunctions[x][y][z] = new LocationUtility();
					
					// TODO just random for now
					s = r.nextDouble();
					utilityFunctions[x][y][z].setUtility(s); 
				}
			}
		}
		
		COMPUTER1 = new TicTacToeComputer(9, utilityFunctions);
		OPPONENT = COMPUTER1.getInt();
	}
	
	
	public void runGame() {
		
		int check = 10;
		
		while (check > 0) {
			while (!checkWin(OPPONENT, BOARD) && !checkWin(PLAYER, BOARD)) {
				if (!isBoardFull()) {
					lastPick();
					findBestMove(USERTURN);
				}
			}
			check--;
			System.out.println("Winner: " + USERTURN + " with condition: " + CONDITION);
		}
		
		
		
	}
	
	
	public void reset() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					BOARD[x][y][z] = 0;
				}
			}
		}
	}
	
	
	public void lastPick() {
		if (USERTURN != PLAYER) {
			USERTURN = PLAYER;
		} else {
			USERTURN = OPPONENT;
		}
	}
	
	public void printUtilities() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					System.out.printf("%.5f" + "  ", utilityFunctions[x][y][z].getUtility());
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public void printBoard() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (BOARD[x][y][z] == 0) {
						System.out.print(" _ ");
					}else if (BOARD[x][y][z] == PLAYER) {
						System.out.print(" X ");
					}else {
						System.out.print(" O ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public int[][][] boardCopy() {
		int[][][] boardCopy = new int[BOARDSIZE][BOARDSIZE][BOARDSIZE];
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					boardCopy[x][y][z] = BOARD[x][y][z];
				}
			}
		}
		return boardCopy;
	}
	
	
	/*
	 * bestMove = NULL
	 * for each move in board :
	 * 		if current move is better than bestMove
	 * 			bestMove = current move
	 * return bestMove
	 */
	public void findBestMove(int user) {
		LocationUtility bestMove = new LocationUtility();
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (BOARD[x][y][z] == 0) {
						if (utilityFunctions[x][y][z].getUtility() > bestMove.getUtility()) {
							i = x;
							j = y;
							k = z;
							bestMove = utilityFunctions[x][y][z];
						}
					}
				}
			}
		}
		
		BOARD[i][j][k] = user;
	}
	
	
	
	// try to get 4 in a row, add good amount to each utility in win conditional
	// block opponent 4 in a row if able, add good amount to utility
	// try to get 3 in a row
	// block opponent 3 in a row if able, add okay amount to utility
	// do best utility
	
	
	public int[] winChecker(int user) {
		
		int[] location = new int[3];
		
		int[][][] board = boardCopy();
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (board[x][y][z] == 0) {
						board[x][y][z] = user;
						if (checkWin(user, board)) {
							location[0] = x;
							location[1] = y;
							location[2] = z;
							return location;
						}
						board[x][y][z] = 0;
					}
				}
			}
		}
		
		return location;
	}
	
	
	
	
	/*
	 * minimax(board, depth, isMaximizingPlayer);
	 * 
	 * 
	 * if terminal
	 * 		return value
	 * 
	 * 
	 * if maxiPlayer
	 * 		bestVal = -10000
	 * 		iterate each move on board
	 * 			value = minimax(board, depth+1, false)
	 * 			bestValue = max (bestVal, value)
	 * 		return bestVal
	 * else
	 * 		bestVal = 10000
	 * 		iterate each move on board
	 * 			value = minimax(board, depth+1, true)
	 * 			bestVal = min(bestVal, value)
	 * 		return bestVal
	 * 
	 */			
	public int Minimax(int depth, boolean isMaxPlayer, int user) {
		int value = 0;
		if (isBoardFull()) {
			return value;
		}
		
		if (isMaxPlayer) {
			value = -10000;
			
		}
		
		return value;
		
	}
	
	public boolean checkRow3D(int user, int[][][] board) {
		int check = 0;
		for (int col = 0; col < BOARDSIZE; col++ ) {
			for (int i = 0; i < BOARDSIZE; i++) { 
				if (board[i][col][i] == user) {
					check++;
				}
				if (check == BOARDSIZE) {
					return true;
				}
			}
			check = 0;
		}
		
	check = 0;
		for (int col = 0; col < BOARDSIZE; col++) {
			for (int i = 0; i < BOARDSIZE; i++) { 
				if (board[i][col][(BOARDSIZE - 1) - i] == user) {
					check++;
				}
			}
			if (check == BOARDSIZE) {
				return true;
			}
			check = 0;
		}
	return false;
	}
	
	public boolean checkCol3D(int user, int[][][] board) {
		int check = 0;
			for (int row = 0; row < BOARDSIZE; row++ ) {
				for (int i = 0; i < BOARDSIZE; i++) { 
					if (board[i][i][row] == user) {
						check++;
					}
					if (check == BOARDSIZE) {
						return true;
					}
				}
				check = 0;
			}
			
		check = 0;
			for (int row = 0; row < BOARDSIZE; row++) {
				for (int i = 0; i < BOARDSIZE; i++) { 
					if (board[i][(BOARDSIZE - 1) - i][row] == user) {
						check++;
					}
				}
				if (check == BOARDSIZE) {
					return true;
				}
				check = 0;
			}
		return false;
	}
	
	public boolean checkRow(int user, int[][][] board) {
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int row = 0; row < BOARDSIZE; row++) {
				for (int i = 0; i < BOARDSIZE; i++) {
					if (board[level][row][i] == user) {
						check++;
					}
				}
				if (check == BOARDSIZE) {
					return true;
				}
				check = 0;
			}
		}
		return false;
	}
	
	public boolean checkCol(int user, int[][][] board) {
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int col = 0; col < BOARDSIZE; col++) {
				for (int i = 0; i < BOARDSIZE; i++) {
					if (board[level][i][col] == user) {
						check++;
					}
				}
				if (check == BOARDSIZE) {
					return true;
				}
				check = 0;
			}
		}
		return false;
	}
	
	public boolean checkDiagonal3D(int user, int[][][] board) {
		
		int check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][i][i] == user) {
				check++;
			}
			if (check == BOARDSIZE) {
				return true;
			}
		}
		
		check = 0;
		for (int i = BOARDSIZE - 1; i >= 0; i--) {
			if (board[i][(BOARDSIZE - 1) - i][(BOARDSIZE - 1) - i] == user) {
				check++;
			}
			if (check == BOARDSIZE) {
				return true;
			}
		}
		
		check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][i][(BOARDSIZE - 1) - i] == user) {
				check++;
			}
			if (check == BOARDSIZE) {
				return true;
			}
		}
		
		check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][(BOARDSIZE - 1) - i][i] == user) {
				check++;
			}
			if (check == BOARDSIZE) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean checkDiagonal(int user, int[][][] board) {
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[level][i][i] == user) {
					check++;
				}
			}
			if (check == BOARDSIZE) {
				return true;
			}
			check = 0;
		}
		
		check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[level][i][(BOARDSIZE - 1) - i] == user) {
					check++;
				}
			}
			if (check == BOARDSIZE) {
				return true;
			}
			check = 0;
		}
		return false;
	}
	
	public boolean checkVertical(int user, int[][][] board) {
		int check = 0;
		for (int row = 0; row < BOARDSIZE; row++) {
			for (int col = 0; col < BOARDSIZE; col++) {
				for (int level = 0; level < BOARDSIZE; level++) {
					if (board[level][row][col] == user) {
						check++;
					}
				}
				if (check == BOARDSIZE) {
					return true;
				}
				check = 0;
			}
		}
		return false;
	}
	
	
	/** Terminal state
	 * check diagonals, columns, rows, verticals for matching item
	 * @return
	 */
	public boolean checkWin(int user, int[][][] board) {
		boolean check = false;
		if (checkCol(user, board)) {
			CONDITION = 1;
			check = true;
		}else if (checkCol3D(user, board)) {
			CONDITION = 2;
			check = true;
		}else if (checkDiagonal(user, board)) {
			CONDITION = 3;
			check = true;
		}else if (checkDiagonal3D(user, board)) {
			CONDITION = 4;
			check = true;
		}else if (checkRow(user, board)) {
			CONDITION = 5;
			check = true;
		}else if (checkRow3D(user, board)) {
			CONDITION = 6;
			check = true;
		}else if (checkVertical(user, board)) {
			CONDITION = 7;
			check = true;
		}
		
		return check;		
	}
	

	/**
	 * 
	 * @return false if any spot is blank
	 */
	public boolean isBoardFull() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (BOARD[x][y][z] == 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
}















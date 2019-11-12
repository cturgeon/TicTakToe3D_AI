package ticTak3d;

import java.util.Random;

public class TicTakLearner {

	public int[][][] BOARD;
	public int BOARDSIZE = 4;
	public int PLAYER;
	public int OPPONENT;
	public TicTacToeComputer COMPUTER;
	public TicTacToeComputer ticTacToeAI;

	public int USERTURN;

	public TicTakLearner(int user) {

		if (user == 1) {
			PLAYER = 1;
		} else if (user == 2) {
			PLAYER = 2;
			COMPUTER = new TicTacToeComputer(2, BOARDSIZE);
		}

		BOARD = new int[BOARDSIZE][BOARDSIZE][BOARDSIZE];

		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					BOARD[x][y][z] = 0;
				}
			}
		}

		ticTacToeAI = new TicTacToeComputer(9, BOARDSIZE);
		OPPONENT = ticTacToeAI.getInt();
	}

	public void runGame(int run) {
		int check = run;
		while (check > 0) {
			reset();
			Random r = new Random();
			boolean b = r.nextBoolean();
			USERTURN = b ? PLAYER : OPPONENT; // essentially picks who goes second

			LocationInfo[] plyInfo = checkWin(PLAYER, BOARD);
			LocationInfo[] oppInfo = checkWin(OPPONENT, BOARD);
			while (plyInfo[BOARDSIZE].inARow() != 4 && oppInfo[BOARDSIZE].inARow() != 4) {
				if (!isBoardFull()) {
					lastPick();
					int[] best = findBestMove(USERTURN, check);
					BOARD[best[0]][best[1]][best[2]] = USERTURN;
					plyInfo = checkWin(PLAYER, BOARD);
					oppInfo = checkWin(OPPONENT, BOARD);
				}
			}
			if (USERTURN == OPPONENT) { // sets winners and losers utilities
				winUtilities(OPPONENT, check);
				lossUtilities(PLAYER, check);
			} else {
				winUtilities(PLAYER, check);
				lossUtilities(OPPONENT, check);
			}
			check--;
		}
	}

	public int[] exploration(int user, int checkLevel) {
		int[] bestMoveLocation = new int[BOARDSIZE];
		TicTacToeComputer computer = null;
		computer = (user == ticTacToeAI.getInt()) ? ticTacToeAI : COMPUTER;
		double epsilon = computer.getEpsilon() / checkLevel;
		Random r = new Random();
		double n = r.nextDouble();
		if (n > epsilon) {
			bestMoveLocation[BOARDSIZE - 1] = 0;
			return bestMoveLocation;
		} else {

			int i = -1;
			int j = -1;
			int k = -1;

			for (int z = 0; z < BOARDSIZE; z++) {
				for (int y = 0; y < BOARDSIZE; y++) {
					for (int x = 0; x < BOARDSIZE; x++) {
						if (BOARD[x][y][z] == 0) {
							i = x;
							j = y;
							k = z;
						}
					}
				}
			}

			bestMoveLocation[0] = i;
			bestMoveLocation[1] = j;
			bestMoveLocation[2] = k;
			bestMoveLocation[BOARDSIZE - 1] = 4;
			return bestMoveLocation;
		}
	}

	/**
	 * sets the win utilities for the user
	 * @param user
	 * @param turn
	 */
	public void winUtilities(int user, int turn) {
		TicTacToeComputer computer = null;
		if (user == ticTacToeAI.getInt()) {
			computer = ticTacToeAI;
		} else {
			computer = COMPUTER;
		}
		LocationInfo[] info = checkWin(computer.getInt(), BOARD);
		for (LocationInfo entry : info) {
			if (entry.inARow() == 0) {
				computer.setUtility(entry.x(), entry.y(), entry.z(), 1.0, turn);
			}

		}

	}

	/**
	 * sets the loser utilities for the user
	 * @param user
	 * @param turn
	 */
	public void lossUtilities(int user, int turn) {
		TicTacToeComputer computer = null;
		if (user == ticTacToeAI.getInt()) {
			computer = ticTacToeAI;
		} else {
			computer = COMPUTER;
		}

		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (BOARD[x][y][z] == user) {
						computer.setUtility(x, y, z, -1.0, turn);
					}
				}
			}
		}
	}

	/**
	 * resets board back to blank
	 */
	public void reset() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					BOARD[x][y][z] = 0;
				}
			}
		}
	}

	/**
	 * switches who's turn it is
	 */
	public void lastPick() {
		USERTURN = (USERTURN != PLAYER) ? PLAYER : OPPONENT;
	}

	/**
	 * prints, may be unneeded as it is used in another class
	 * 
	 * @param user
	 */
	public void printUtilities(int user) {
		TicTacToeComputer computer = null;
		computer = (user == ticTacToeAI.getInt()) ? ticTacToeAI : COMPUTER;
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					System.out.printf("%.5f" + "  ", computer.getUtility(x, y, z));
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	/**
	 * prints the board at current state used mainly for testing or human playing
	 */
	public void printBoard() {
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (BOARD[x][y][z] == 0) {
						System.out.print(" _ ");
					} else if (BOARD[x][y][z] == PLAYER) {
						System.out.print(" X ");
					} else {
						System.out.print(" O ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	/**
	 * copies board in current state
	 * 
	 * @param board
	 * @return
	 */
	public int[][][] boardCopy(int[][][] board) {
		int[][][] boardCopy = new int[BOARDSIZE][BOARDSIZE][BOARDSIZE];
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					boardCopy[x][y][z] = board[x][y][z];
				}
			}
		}
		return boardCopy;
	}

	/**
	 * bestMove = NULL for each move in board : if current move is better than
	 * bestMove bestMove = current move return bestMove
	 */
	public int[] findBestMove(int user, int check) {
		int[] bestMoveLocation = new int[3];
		TicTacToeComputer computer = null;
		TicTacToeComputer opponent = null;
		computer = (user == ticTacToeAI.getInt()) ? ticTacToeAI : COMPUTER;
		opponent = (user == ticTacToeAI.getInt()) ? COMPUTER : ticTacToeAI;
		LocationUtility bestMove = new LocationUtility();

		int i = 0;
		int j = 0;
		int k = 0;

		int[] explore = exploration(computer.getInt(), check);
		int[] win = winChecker(computer.getInt(), 2);
		int[] oppWin = winChecker(opponent.getInt(), 2);
		if (win[3] == 2) { // for 4 in a row
			bestMoveLocation[0] = win[0];
			bestMoveLocation[1] = win[1];
			bestMoveLocation[2] = win[2];
			return bestMoveLocation;
		} else if (oppWin[3] == 2) {
			bestMoveLocation[0] = oppWin[0];
			bestMoveLocation[1] = oppWin[1];
			bestMoveLocation[2] = oppWin[2];
			return bestMoveLocation;
		} else if (explore[BOARDSIZE - 1] == 4) {
			bestMoveLocation[0] = explore[0];
			bestMoveLocation[1] = explore[1];
			bestMoveLocation[2] = explore[2];
			return bestMoveLocation;
		} else {
			for (int x = 0; x < BOARDSIZE; x++) {
				for (int y = 0; y < BOARDSIZE; y++) {
					for (int z = 0; z < BOARDSIZE; z++) {
						if (BOARD[x][y][z] == 0) {
							if (computer.getUtility(x, y, z) >= bestMove.getUtility()) { // make it work for both
								i = x;
								j = y;
								k = z;
								bestMove.setUtility(computer.getUtility(x, y, z));
							}
						}
					}
				}
			}

			bestMoveLocation[0] = i;
			bestMoveLocation[1] = j;
			bestMoveLocation[2] = k;

			return bestMoveLocation;
		}
	}

	// try to get 4 in a row, add good amount to each utility in win conditional
	// block opponent 4 in a row if able, add good amount to utility
	// try to get 3 in a row
	// block opponent 3 in a row if able, add okay amount to utility
	// do best utility
	public int[] winChecker(int user, int depth) {
		@SuppressWarnings("unused")
		TicTacToeComputer computer = null;
		computer = (user == ticTacToeAI.getInt()) ? ticTacToeAI : COMPUTER;

		if (depth == 0) {
			return null;
		}

		int[] location = new int[4];

		int[][][] board = boardCopy(BOARD);
		for (int x = 0; x < BOARDSIZE; x++) {
			for (int y = 0; y < BOARDSIZE; y++) {
				for (int z = 0; z < BOARDSIZE; z++) {
					if (board[x][y][z] == 0) {
						board[x][y][z] = user;
						LocationInfo[] plyInfo = checkWin(user, board);
						if (plyInfo[4].inARow() == 4) { // gets four in a row
							location[0] = x;
							location[1] = y;
							location[2] = z;
							location[3] = depth;
							return location;
						}

						// TODO the optimization of my program does not really allow for searching 3 in a row
						// the time it takes to run the program as number of runs gets larger makes the search
						// unreasonable 
//						for (int i = 0; i < BOARDSIZE; i++) {
//							for (int j = 0; j < BOARDSIZE; j++) {
//								for (int k = 0; k < BOARDSIZE; k++) {
//									if (board[i][j][k] == 0) {
//										board[i][j][k] = user;
//										if (plyInfo[4].inARow() == 4) {
//											location[0] = i;
//											location[1] = j;
//											location[2] = k;
//											location[3] = depth--;
//											return location;
//										}
//										board[i][j][k] = 0;
//									}
//								}
//							}
//						}
						
						board[x][y][z] = 0;
					}
				}
			}
		}

		return location;
	}

	public LocationInfo[] checkRow3D(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int col = 0; col < BOARDSIZE; col++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[i][col][i] == user) {
					locations[check] = new LocationInfo(i, col, i, 0);
					check++;
				}
				if (check == BOARDSIZE) {

					locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
					return locations;
				}
			}
			check = 0;
		}

		check = 0;
		for (int col = 0; col < BOARDSIZE; col++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[i][col][(BOARDSIZE - 1) - i] == user) {
					locations[check] = new LocationInfo(i, col, i, 0);
					check++;
				}
			}
			if (check == BOARDSIZE) {
				locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
				return locations;
			}
			check = 0;
		}

		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkCol3D(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int row = 0; row < BOARDSIZE; row++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[i][i][row] == user) {
					locations[check] = new LocationInfo(i, i, row, 0);
					check++;
				}
			}
			if (check == BOARDSIZE) {
				locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
				return locations;
			}
			check = 0;
		}

		check = 0;
		for (int row = 0; row < BOARDSIZE; row++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[i][(BOARDSIZE - 1) - i][row] == user) {
					locations[check] = new LocationInfo(i, (BOARDSIZE - 1) - i, row, 0);
					check++;
				}
			}
			if (check == BOARDSIZE) {
				locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
				return locations;
			}
			check = 0;
		}
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkRow(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int row = 0; row < BOARDSIZE; row++) {
				for (int i = 0; i < BOARDSIZE; i++) {
					if (board[level][row][i] == user) {
						locations[check] = new LocationInfo(level, row, i, 0);
						check++;
					}
				}
				if (check == BOARDSIZE) {
					locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
					return locations;
				}
				check = 0;
			}
		}
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkCol(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int col = 0; col < BOARDSIZE; col++) {
				for (int i = 0; i < BOARDSIZE; i++) {
					if (board[level][i][col] == user) {
						locations[check] = new LocationInfo(level, i, col, 0);
						check++;
					}
				}
				if (check == BOARDSIZE) {
					locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
					return locations;
				}
				check = 0;
			}
		}
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkDiagonal3D(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][i][i] == user) {
				locations[check] = new LocationInfo(i, i, i, 0);
				check++;
			}
		}
		if (check == BOARDSIZE) {
			locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
			return locations;
		}
		check = 0;
		for (int i = BOARDSIZE - 1; i >= 0; i--) {
			if (board[i][(BOARDSIZE - 1) - i][(BOARDSIZE - 1) - i] == user) {
				locations[check] = new LocationInfo(i, (BOARDSIZE - 1) - i, (BOARDSIZE - 1) - i, 0);
				check++;
			}
		}
		if (check == BOARDSIZE) {
			locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
			return locations;
		}
		check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][i][(BOARDSIZE - 1) - i] == user) {
				locations[check] = new LocationInfo(i, i, (BOARDSIZE - 1) - i, 0);
				check++;
			}
		}
		if (check == BOARDSIZE) {
			locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
			return locations;
		}
		check = 0;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board[i][(BOARDSIZE - 1) - i][i] == user) {
				locations[check] = new LocationInfo(i, (BOARDSIZE - 1) - i, i, 0);
				check++;
			}
		}
		if (check == BOARDSIZE) {
			locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
			return locations;
		}
		check = 0;
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkDiagonal(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[level][i][i] == user) {
					locations[check] = new LocationInfo(level, i, i, 0);
					check++;
				}
			}
			if (check == BOARDSIZE) {
				locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
				return locations;
			}
			check = 0;
		}

		check = 0;
		for (int level = 0; level < BOARDSIZE; level++) {
			for (int i = 0; i < BOARDSIZE; i++) {
				if (board[level][i][(BOARDSIZE - 1) - i] == user) {
					locations[check] = new LocationInfo(level, i, (BOARDSIZE - 1) - i, 0);
					check++;
				}
			}
			if (check == BOARDSIZE) {
				locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
				return locations;
			}
			check = 0;
		}
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	public LocationInfo[] checkVertical(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];
		int check = 0;
		for (int row = 0; row < BOARDSIZE; row++) {
			for (int col = 0; col < BOARDSIZE; col++) {
				for (int level = 0; level < BOARDSIZE; level++) {
					if (board[level][row][col] == user) {
						locations[check] = new LocationInfo(level, row, col, 0);
						check++;
					}
				}
				if (check == BOARDSIZE) {
					locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 4);
					return locations;
				}
				check = 0;
			}
		}
		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
	}

	/**
	 * Terminal state check diagonals, columns, rows, verticals for matching item
	 * 
	 * @return
	 */
	public LocationInfo[] checkWin(int user, int[][][] board) {
		LocationInfo[] locations = new LocationInfo[BOARDSIZE + 1];

		locations = checkCol(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkCol3D(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkDiagonal(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkDiagonal3D(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkRow(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkRow3D(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations = checkVertical(user, board);
		if (locations[BOARDSIZE].inARow() == 4) {
			return locations;
		}

		locations[BOARDSIZE] = new LocationInfo(0, 0, 0, 1);
		return locations;
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

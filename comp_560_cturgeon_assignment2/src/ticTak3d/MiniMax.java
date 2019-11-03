package ticTak3d;

public class MiniMax {

	
//	/*
//	 * minimax(board, depth, isMaximizingPlayer);
//	 * 
//	 * 
//	 * if terminal
//	 * 		return value
//	 * 
//	 * 
//	 * if maxiPlayer
//	 * 		bestVal = -10000
//	 * 		iterate each move on board
//	 * 			value = minimax(board, depth+1, false)
//	 * 			bestValue = max (bestVal, value)
//	 * 		return bestVal
//	 * else
//	 * 		bestVal = 10000
//	 * 		iterate each move on board
//	 * 			value = minimax(board, depth+1, true)
//	 * 			bestVal = min(bestVal, value)
//	 * 		return bestVal
//	 * 
//	 */			
//	public double miniMax(int depth, int[][][] board, int user) {
//		if (isBoardFull() || depth == 0) {
//			return score(user, board);
//		}
//		
//		if (user == PLAYER) { // maximizer
//			return getMax(user, board, depth);
//		} else {
//			return getMin(user, board, depth);
//		}
//	}
//	
//	public double getMax(int user, int[][][] board, int depth) {
//		double bestScore = -1.0;
//		int i = 0;
//		int j = 0;
//		int k = 0;
//		
//		for (int x = 0; x < BOARDSIZE; x++) {
//			for (int y = 0; y < BOARDSIZE; y++) {
//				for (int z = 0; z < BOARDSIZE; z++) {
//					if (board[x][y][z] == 0) {
//						int[][][] boardCopy = boardCopy(board);
//						
//						double score = miniMax(user, boardCopy, depth--);
//						
//						if (score >= bestScore) {
//							bestScore = score;
//							i = x;
//							j = y;
//							k = z;
//						}
//					}
//				}
//			}
//		}
//		
//		board[i][j][k] = user;
//		return bestScore;
//	}
//	
//	public double getMin(int user, int[][][] board, int depth) {
//		double bestScore = 1.0;
//		int i = 0;
//		int j = 0;
//		int k = 0;
//		
//		for (int x = 0; x < BOARDSIZE; x++) {
//			for (int y = 0; y < BOARDSIZE; y++) {
//				for (int z = 0; z < BOARDSIZE; z++) {
//					if (board[x][y][z] == 0) {
//						int[][][] boardCopy = boardCopy(board);
//						
//						double score = miniMax(user, boardCopy, depth--);
//						
//						if (score <= bestScore) {
//							bestScore = score;
//							i = x;
//							j = y;
//							k = z;
//						}
//					}
//				}
//			}
//		}
//		
//		board[i][j][k] = user;
//		return bestScore;
//	}
//	
//	public double score(int user, int[][][] board) {
//		int opponent = (user == PLAYER) ? OPPONENT : PLAYER;
//		
//		if (checkWin(user, board)) {
//			return 0.1; // TODO figure out values
//		} else if (checkWin(opponent, board)) {
//			return -0.1;
//		}else {
//			return 0; // for no winner
//		}
//	}
	

}

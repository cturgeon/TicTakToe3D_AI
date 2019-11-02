package ticTak3d;

public class TicTacToe3DTester {

	public static void main(String[] args) {
		TicTakLearner board = new TicTakLearner(1);
		
		board.runGame();
		board.printBoard();
		board.printUtilities();
	}
}

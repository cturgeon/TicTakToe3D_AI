package ticTak3d;

import java.util.Scanner;

public class TicTacToe3DTester {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int size1 = scanner.nextInt();
		int size2 = scanner.nextInt();
		int size3 = scanner.nextInt();
		
		TicTakLearner board1 = new TicTakLearner(2);
		board1.runGame(size1);
		System.out.println("----------");
		board1.printUtilities(9);
		System.out.println("----------");
		
		TicTakLearner board2 = new TicTakLearner(2);
		board2.runGame(size2);
		System.out.println("----------");
		board2.printUtilities(9);
		System.out.println("----------");
		
		TicTakLearner board3 = new TicTakLearner(2);
		board3.runGame(size3);
		System.out.println("----------");
		board3.printUtilities(9);
		System.out.println("----------");
		
		scanner.close();
	}
}

package ticTak3d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TicTacToe3DTester {

	public static void main(String[] args) {
		TicTakLearner board = new TicTakLearner(2);
		board.runGame(1000);
		board.printUtilities(2);
	}
	
	
//	public void executeScript() throws IOException, InterruptedException {
//	    Process p = Runtime.getRuntime().exec("sh /root/Desktop/chat/script.sh");
//	    p.waitFor();
//
//	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//	    BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//
//	    String line = "";
//	    while ((line = reader.readLine()) != null) {
//	        System.out.println(line);
//	    }
//
//	    line = "";
//	    while ((line = errorReader.readLine()) != null) {
//	        System.out.println(line);
//	    }
//	}
}

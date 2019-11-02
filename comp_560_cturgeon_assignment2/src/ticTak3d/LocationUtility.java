package ticTak3d;

public class LocationUtility {
	private double utility;
	
	private double EPSILON = 0.3;
	
	public LocationUtility() {
		utility = 0.0;
	}
	
	public void setUtility(double u) {
		utility = u;
	}
	
	public double getUtility() {
		return utility;
	}
	
}

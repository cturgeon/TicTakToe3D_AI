package ticTak3d;

public class LocationInfo {
	
	private int x;
	private int y;
	private int z;
	
	private int inARow;
	
	public LocationInfo(int x, int y, int z, int inARow) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.inARow = inARow;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int z() {
		return z;
	}
	
	public int inARow() {
		return inARow;
	}

}

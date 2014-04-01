package worms.model;

public class Food {
	
	private double x;
	private double y;
	
	public double getX() {
		return this.x;
	}
	
	public boolean isValidX(double x) {
		return (!Double.isNaN(x));
	}
	
	public void setX(double x) throws ModelException {
		if (!isValidX(x))
			throw new ModelException("Invalid x-coordinate.");
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public boolean isValidY(double y) {
		return (!Double.isNaN(y));
	}
	
	public void setY(double y) throws ModelException {
		if (!isValidY(y))
			throw new ModelException("Invalid y-coordinate");
		this.y = y;
	}


	
	

}

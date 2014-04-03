package worms.model;

import java.util.ArrayList;
import java.util.Random;

public class World{
	
	private double width;
	private double height;
	private boolean[][] passableMap;
	
	private Teams team;
	private Worm worm;
	private Food food;
	
	public World(double width, double height, boolean[][] passableMap, Random random) {
		setPassableMap(passableMap);
		setHeight(height);
		setWidth(width);
	}
	
	public final void setWidth(double width) {
		if (! isValidWidth(width))
			throw new ModelException("Width is invalid.");
		this.width = width;
	}
	
	public boolean isValidWidth(double width) {
		if (width < 0 || width > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	public final void setHeight(double height) {
		if (! isValidHeight(height))
			throw new ModelException("Height is invalid.");
		this.height = height;
	}
	
	public boolean isValidHeight(double height) {
		if (height < 0 || height > Double.MAX_VALUE)
			return false;
		return true;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setPassableMap(boolean[][] passableMap) {
		this.passableMap = passableMap;
	}
	
	/*
	 * returns true if the map at the given coordinates is passable.
	 * 
	 * Zou de typecast naar een integer problemen geven? Met problemen bedoel ik geen accurate controle op de positie.
	 * Ik denk eerlijk gezegd van niet, aangezien de onnauwkeurigheid maximum 1 pixel zal zijn,
	 * wat voldoende klein is volgens mij. (omdat de map enkele 1000en pixels heeft)
	 */
	public boolean getPassableMap(double x, double y) {
		return passableMap[(int) x][(int) y];
	}

	
	/*
	 * TODO
	 * passableMap geeft true of false terug als op een bepaalde positie (x,y) het terrein passable of impassable is.
	 * Hoe controleren op (x,y) + cirkel van straal radius errond? Nu controleert hij enkel boven, onder en naast...
	 * Misschien op de een of andere manier via graden werken, en dan een for-loop die 360 graden rond gaat?
	 * Kan dit een arrayOutOfBoundsException geven (als vb. x - radius < 0)?
	 */
	public boolean isImpassable(double x, double y, double radius) {
		if (getPassableMap(x + radius, y + radius))
			return false;
		if (getPassableMap(x + radius, y - radius))
			return false;
		if (getPassableMap(x - radius, y + radius))
			return false;
		if (getPassableMap(x - radius, y - radius))
			return false;
		return true;
	}
	
	//Location is adjacent if: location itself is passable + impassable at 0.1*radius.
	public boolean isAdjacent(double x, double y, double radius) {
		double newRadius = radius * 0.1;
		return ( !isImpassable(x, y, 0) && isImpassable(x, y, newRadius) );
	}
	
	//TODO
	public void removeWorm() {
	}
	
	//TODO werkt zo'n setter om te voorkomen dat het aangemaakte worm-object in deze klasse null is?
	public void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	//TODO
	//Aangemaakte worm-object is null, setter voorkomt dit (hopelijk)...
	public void addNewWorm() {
		(worm.getAllWorms()).add(worm);
	}

	public void addNewTeam(String newName) {
		team.addTeam(newName);
	}
	
	public void setFood(Food food) {
		this.food = food;
	}
	
	public void addNewFood() {
		(food.getAllFood()).add(food);
	}
	
	public ArrayList<Food> getFood() {
		return food.getAllFood();
	}
}
	

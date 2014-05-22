package worms.model;

public class Entity {
	
	public Entity() {
		
	}
	
	private Worm worm;
	public Entity(Worm worm) {
		this.worm = worm;
	}
	
	private Food food;
	public Entity(Food food) {
		this.food = food;
	}
	
	public Object getEntity() {
		if (worm!=null)
			return worm;
		if (food!=null)
			return food;
		return null;
	}

}
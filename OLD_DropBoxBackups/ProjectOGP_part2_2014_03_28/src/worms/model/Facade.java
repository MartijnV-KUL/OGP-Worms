package worms.model;

import java.util.Collection;
import java.util.Random;

public class Facade implements IFacade {

	public Facade() {}

	
	/**@Override
	public Worm createWorm(double x, double y, double direction, double radius, String name) {
		return new Worm(x, y, direction, radius, name);
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.canMove(nbSteps);
	}

	@Override
	public void move(Worm worm, int nbSteps) {
		worm.activeMove(nbSteps);
	}*/

	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.canTurn(angle);
	}

	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	/**@Override
	public void jump(Worm worm) {
		worm.jump();
	}

	@Override
	public double getJumpTime(Worm worm) {
		return worm.jumpTime();
	}*/

	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(t);
	}

	@Override
	public double getX(Worm worm) {
		return worm.getX();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getY();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}

	@Override
	public double getMinimalRadius(Worm worm) {
		return worm.getMinimalRadius();
	}

	@Override
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	@Override
	public void rename(Worm worm, String newName) {
		worm.setName(newName);
	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}


	@Override
	public void addEmptyTeam(World world, String newName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addNewFood(World world) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addNewWorm(World world) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean canFall(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean canMove(Worm worm) {
		return worm.canMove();
	}


	@Override
	public Food createFood(World world, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}


	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		return new Worm(world, x, y, direction, radius, name);
	}


	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Projectile getActiveProjectile(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Worm getCurrentWorm(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Food> getFood(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getHitPoints(Worm worm) {
		return worm.getHitPoints();
	}


	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		return projectile.jumpStep(t);
	}


	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();
	}


	@Override
	public double getRadius(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getRadius(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getSelectedWeapon(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getTeamName(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Worm> getWorms(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getX(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getX(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getY(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getY(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacent(x, y, radius);
	}


	@Override
	public boolean isAlive(Worm worm) {
		return worm.wormIsAlive();
	}


	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		return world.isImpassable(x, y, radius);
	}


	@Override
	public void jump(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void jump(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void move(Worm worm) {
		worm.activeMove();
	}


	@Override
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startNextTurn(World world) {
		// TODO Auto-generated method stub
		
	}
}

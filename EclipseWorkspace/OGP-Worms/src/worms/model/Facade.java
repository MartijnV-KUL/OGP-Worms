package worms.model;

import java.util.Collection;
import java.util.Random;

public class Facade implements IFacade {

	public Facade() {}

	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.canTurn(angle);
	}

	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(t);
	}

	@Override
	public double getX(Worm worm) {
		return worm.getPosition().getX();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getPosition().getY();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getPosition().getDirection();
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
		world.addTeam(new Team(newName));
	}


	@Override
	public void addNewFood(World world) {
		world.addNewFood();
	}


	@Override
	public void addNewWorm(World world) {
		world.addNewWorm();
		
	}


	@Override
	public boolean canFall(Worm worm) {
		return worm.canFall();
	}


	@Override
	public boolean canMove(Worm worm) {
		return worm.canMove();
	}


	@Override
	public Food createFood(World world, double x, double y) {
		return world.addNewFood(x, y);
	}


	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}


	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		return world.addNewWorm(x, y, direction, radius, name);
	}


	@Override
	public void fall(Worm worm) {
		worm.fall();
	}


	@Override
	public Projectile getActiveProjectile(World world) {
		return world.getProjectile();
	}


	@Override
	public Worm getCurrentWorm(World world) {
		return world.getActiveWorm();
	}


	@Override
	public Collection<Food> getFood(World world) {
		return world.getFood();
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
		return projectile.jumpTime(timeStep);
	}


	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		return worm.jumpTime(timeStep);
	}


	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();
	}


	@Override
	public double getRadius(Food food) {
		return food.getRadius();
	}


	@Override
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}


	@Override
	public String getSelectedWeapon(Worm worm) {
		return worm.getEquippedWeapon().getName();
	}


	@Override
	public String getTeamName(Worm worm) {
		return worm.getTeam().getName();
	}


	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Worm> getWorms(World world) {
		return world.getWorms();
	}


	@Override
	public double getX(Food food) {
		return food.getX();
	}


	@Override
	public double getX(Projectile projectile) {
		return projectile.getPosition().getX();
	}


	@Override
	public double getY(Food food) {
		return food.getY();
	}


	@Override
	public double getY(Projectile projectile) {
		return projectile.getPosition().getY();
	}


	@Override
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return (!food.isTerminated());
	}


	@Override
	public boolean isActive(Projectile projectile) {
		return (!projectile.isTerminated());
	}


	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacent(x, y, radius);
	}


	@Override
	public boolean isAlive(Worm worm) {
		return worm.isAlive();
	}


	@Override
	public boolean isGameFinished(World world) {
		return world.isGameFinished();
	}


	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		return !world.isPassable(x, y, radius);
	}


	@Override
	public void jump(Projectile projectile, double timeStep) {
		projectile.jump(timeStep);
	}


	@Override
	public void jump(Worm worm, double timeStep) {
		worm.jump(timeStep);
	}


	@Override
	public void move(Worm worm) {
		worm.activeMove();
	}


	@Override
	public void selectNextWeapon(Worm worm) {
		worm.equipNextWeapon();
	}


	@Override
	public void shoot(Worm worm, int yield) {
		worm.shoot(yield);
	}


	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		world.startGame();
	}


	@Override
	public void startNextTurn(World world) {
		world.nextTurn();
	}
}

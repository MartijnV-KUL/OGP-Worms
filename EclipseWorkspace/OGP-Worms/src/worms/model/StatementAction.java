package worms.model;

import worms.gui.GUIConstants;

public class StatementAction extends Statement {
	
	private final StatementAction.ActionTypes actionType;
	
	public StatementAction.ActionTypes getActionType() {
		return actionType;
	}

	public StatementAction(int line, int column, StatementAction.ActionTypes type) {
		super(line, column, new Statement[0], new Expression[0]);
		this.actionType = type;
	}
	
	public StatementAction( int line, int column, StatementAction.ActionTypes type, Expression e ) {
		super(line, column, new Statement[0], new Expression[] {e});
		this.actionType = type;
	}
	
	public static enum ActionTypes {
		TURN,
		MOVE,
		JUMP,
		TOGGLEWEAP,
		FIRE,
		SKIP
	}

	@Override
	public void execute() {
		if (getRootProgram().getCurrentLine() > getLine())
			return;
		if (getRootProgram().getCurrentColumn() > getColumn())
			return;
		
		preExecute();
		Program program = getRootProgram();
		Worm worm = program.getWorm();
		
		if ( getActionType() == StatementAction.ActionTypes.TURN ) {
			if ( !(getExpressions().get(0).evaluate().getValue() instanceof Number) )
				program.typeErrorOccurred();
			if ( !worm.canTurn((double)((Type<Double>)getExpressions().get(0).evaluate()).getValue()) )
				program.stopProgram();
			getRootProgram().getHandler().turn(worm, (double)((Type<Double>)getExpressions().get(0).evaluate()).getValue());
			return;
		}
		if ( getActionType() == StatementAction.ActionTypes.MOVE ) {
			if ( !worm.canMove() )
				program.stopProgram();
			getRootProgram().getHandler().move(worm);
			return;
		}
		if ( getActionType() == StatementAction.ActionTypes.JUMP ) {
			if ( !worm.canJump() )
				program.stopProgram();
			getRootProgram().getHandler().jump(worm);
			return;
		}
		if ( getActionType() == StatementAction.ActionTypes.TOGGLEWEAP ) {
			if ( !worm.canMove() )
				program.stopProgram();
			getRootProgram().getHandler().toggleWeapon(worm);
			return;
		}
		if ( getActionType() == StatementAction.ActionTypes.FIRE ) {
			if ( !worm.canShoot() )
				program.stopProgram();
			if ( !(getExpressions().get(0).evaluate().getValue() instanceof Number) )
				program.typeErrorOccurred();
			getRootProgram().getHandler().fire(worm, (int)Math.floor(((Type<Double>)getExpressions().get(0).evaluate()).getValue()));
			return;
		}
		if ( getActionType() == StatementAction.ActionTypes.SKIP ) {
			// do nothing
			return;
		}
		program.typeErrorOccurred();

	}

	@Override
	public boolean containsActionStatement() {
		return true;
	}

}
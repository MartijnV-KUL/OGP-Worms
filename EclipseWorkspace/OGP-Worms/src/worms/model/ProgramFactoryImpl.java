package worms.model;

import java.util.List;

import worms.model.programs.*;

public class ProgramFactoryImpl implements ProgramFactory<Expression, Statement, Type<?>> {
	
	@Override
	public Expression createDoubleLiteral(int line, int column, double d) {
		return new ExpressionDouble(line,column,d);
	}

	@Override
	public Expression createBooleanLiteral(int line, int column, boolean b) {
		if (b)
			return new Expression(line,column,Expression.Types.TRUE);
		else
			return new Expression(line,column,Expression.Types.FALSE);
	}

	@Override
	public Expression createAnd(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.DISJUNCTION, e1, e2);
	}

	@Override
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.CONJUNCTION, e1, e2);
	}

	@Override
	public Expression createNot(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.NEGATION, e);
	}

	@Override
	public Expression createNull(int line, int column) {
		return new Expression(line,column,Expression.Types.NULL);
	}

	@Override
	public Expression createSelf(int line, int column) {
		return new Expression(line,column,Expression.Types.SELF);
	}

	@Override
	public Expression createGetX(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETX, e);
	}

	@Override
	public Expression createGetY(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETY, e);
	}

	@Override
	public Expression createGetRadius(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETRADIUS, e);
	}

	@Override
	public Expression createGetDir(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETDIR, e);
	}

	@Override
	public Expression createGetAP(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETAP, e);
	}

	@Override
	public Expression createGetMaxAP(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETMAXAP, e);
	}

	@Override
	public Expression createGetHP(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETHP, e);
	}

	@Override
	public Expression createGetMaxHP(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.GETMAXHP, e);
	}

	@Override
	public Expression createSameTeam(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.SAMETEAM, e);
	}

	@Override
	public Expression createSearchObj(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.SEARCHOBJ, e);
	}

	@Override
	public Expression createIsWorm(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.ISWORM, e);
	}

	@Override
	public Expression createIsFood(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.ISFOOD, e);
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		return new ExpressionVariable(line,column,name);
	}

	@Override
	public Expression createLessThan(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.LESSTHAN, e1, e2);
	}

	@Override
	public Expression createGreaterThan(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.GREATERTHAN, e1, e2);
	}

	@Override
	public Expression createLessThanOrEqualTo(int line, int column, Expression e1,
			Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.LESSTHANOREQUALTO, e1, e2);
	}

	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column, Expression e1,
			Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.GREATERTHANOREQUALTO, e1, e2);
	}

	@Override
	public Expression createEquality(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.EQUALTO, e1, e2);
	}

	@Override
	public Expression createInequality(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.DIFFERENTFROM, e1, e2);
	}

	@Override
	public Expression createAdd(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.ADDITION, e1, e2);
	}

	@Override
	public Expression createSubtraction(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.SUBTRACTION, e1, e2);
	}

	@Override
	public Expression createMul(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.MULTIPLICATION, e1, e2);
	}

	@Override
	public Expression createDivision(int line, int column, Expression e1, Expression e2) {
		return new ExpressionBinary(line,column,Expression.Types.DIVISION, e1, e2);
	}

	@Override
	public Expression createSqrt(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.SQUAREROOT, e);
	}

	@Override
	public Expression createSin(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.SINE, e);
	}

	@Override
	public Expression createCos(int line, int column, Expression e) {
		return new ExpressionSingular(line,column,Expression.Types.COSINE, e);
	}

	@Override
	public Statement createTurn(int line, int column, Expression angle) {
		return new StatementAction(line,column,StatementAction.ActionTypes.TURN, angle);
	}

	@Override
	public Statement createMove(int line, int column) {
		return new StatementAction(line,column,StatementAction.ActionTypes.MOVE);
	}

	@Override
	public Statement createJump(int line, int column) {
		return new StatementAction(line,column,StatementAction.ActionTypes.JUMP);
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new StatementAction(line,column,StatementAction.ActionTypes.TOGGLEWEAP);
	}

	@Override
	public Statement createFire(int line, int column, Expression yield) {
		return new StatementAction(line,column,StatementAction.ActionTypes.FIRE, yield);
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new StatementAction(line,column,StatementAction.ActionTypes.SKIP);
	}

	@Override
	public Statement createAssignment(int line, int column, String variableName,
			Expression rhs) {
		return new StatementAssignment(line,column,variableName, rhs);
	}

	@Override
	public Statement createIf(int line, int column, Expression condition, Statement then,
			Statement otherwise) {
		return new StatementIfThenElse(line,column,condition, then, otherwise);
	}

	@Override
	public Statement createWhile(int line, int column, Expression condition,
			Statement body) {
		return new StatementWhile(line,column,condition, body);
	}

	@Override
	public Statement createForeach(int line, int column, ForeachType type,
			String variableName, Statement body) {
		return new StatementForEach(line,column,type, variableName, body);
	}

	@Override
	public Statement createSequence(int line, int column, List<Statement> statements) {
		return new StatementSequence(line,column,statements);
	}

	@Override
	public Statement createPrint(int line, int column, Expression e) {
		return new StatementPrint(line,column,e);
	}

	@Override
	public Type<?> createDoubleType() {
		return new Type<Double>();
	}

	@Override
	public Type<?> createBooleanType() {
		return new Type<Boolean>();
	}

	@Override
	public Type<?> createEntityType() {
		return new Type<Entity>();
	}

}

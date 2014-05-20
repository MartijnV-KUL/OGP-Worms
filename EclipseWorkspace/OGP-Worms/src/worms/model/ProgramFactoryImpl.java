package worms.model;

import java.util.List;

import worms.model.expressions.ExpressionAddition;
import worms.model.expressions.ExpressionConjunction;
import worms.model.expressions.ExpressionCosine;
import worms.model.expressions.ExpressionDifferentFrom;
import worms.model.expressions.ExpressionDisjunction;
import worms.model.expressions.ExpressionDivision;
import worms.model.expressions.ExpressionDouble;
import worms.model.expressions.ExpressionEqualTo;
import worms.model.expressions.ExpressionFalse;
import worms.model.expressions.ExpressionGetAP;
import worms.model.expressions.ExpressionGetDirection;
import worms.model.expressions.ExpressionGetHP;
import worms.model.expressions.ExpressionGetMaxAP;
import worms.model.expressions.ExpressionGetMaxHP;
import worms.model.expressions.ExpressionGetRadius;
import worms.model.expressions.ExpressionGetX;
import worms.model.expressions.ExpressionGetY;
import worms.model.expressions.ExpressionGreaterThan;
import worms.model.expressions.ExpressionGreaterThanOrEqualTo;
import worms.model.expressions.ExpressionIsFood;
import worms.model.expressions.ExpressionIsWorm;
import worms.model.expressions.ExpressionLessThan;
import worms.model.expressions.ExpressionLessThanOrEqualTo;
import worms.model.expressions.ExpressionMultiplication;
import worms.model.expressions.ExpressionNegation;
import worms.model.expressions.ExpressionNull;
import worms.model.expressions.ExpressionSameTeam;
import worms.model.expressions.ExpressionSearchObj;
import worms.model.expressions.ExpressionSelf;
import worms.model.expressions.ExpressionSine;
import worms.model.expressions.ExpressionSquareRoot;
import worms.model.expressions.ExpressionSubtraction;
import worms.model.expressions.ExpressionTrue;
import worms.model.expressions.ExpressionVariable;
import worms.model.expressions.ExpressionVariableAccess;
import worms.model.programs.*;
import worms.model.statements.StatementActionFire;
import worms.model.statements.StatementActionJump;
import worms.model.statements.StatementActionMove;
import worms.model.statements.StatementActionSkip;
import worms.model.statements.StatementActionToggleWeapon;
import worms.model.statements.StatementActionTurn;
import worms.model.statements.StatementAssignment;
import worms.model.statements.StatementForEach;
import worms.model.statements.StatementIfThenElse;
import worms.model.statements.StatementPrint;
import worms.model.statements.StatementSequence;
import worms.model.statements.StatementWhile;

public class ProgramFactoryImpl implements ProgramFactory<Expression, Statement, Type<?>> {
	
	@Override
	public Expression createDoubleLiteral(int line, int column, double d) {
		return new ExpressionDouble(line,column,d);
	}

	@Override
	public Expression createBooleanLiteral(int line, int column, boolean b) {
		if (b)
			return new ExpressionTrue(line,column);
		else
			return new ExpressionFalse(line,column);
	}

	@Override
	public Expression createAnd(int line, int column, Expression e1, Expression e2) {
		return new ExpressionDisjunction(line,column, e1, e2);
	}

	@Override
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		return new ExpressionConjunction(line,column, e1, e2);
	}

	@Override
	public Expression createNot(int line, int column, Expression e) {
		return new ExpressionNegation(line,column, e);
	}

	@Override
	public Expression createNull(int line, int column) {
		return new ExpressionNull(line,column);
	}

	@Override
	public Expression createSelf(int line, int column) {
		return new ExpressionSelf(line,column);
	}

	@Override
	public Expression createGetX(int line, int column, Expression e) {
		return new ExpressionGetX(line,column, e);
	}

	@Override
	public Expression createGetY(int line, int column, Expression e) {
		return new ExpressionGetY(line,column, e);
	}

	@Override
	public Expression createGetRadius(int line, int column, Expression e) {
		return new ExpressionGetRadius(line,column, e);
	}

	@Override
	public Expression createGetDir(int line, int column, Expression e) {
		return new ExpressionGetDirection(line,column, e);
	}

	@Override
	public Expression createGetAP(int line, int column, Expression e) {
		return new ExpressionGetAP(line,column, e);
	}

	@Override
	public Expression createGetMaxAP(int line, int column, Expression e) {
		return new ExpressionGetMaxAP(line,column, e);
	}

	@Override
	public Expression createGetHP(int line, int column, Expression e) {
		return new ExpressionGetHP(line,column, e);
	}

	@Override
	public Expression createGetMaxHP(int line, int column, Expression e) {
		return new ExpressionGetMaxHP(line,column, e);
	}

	@Override
	public Expression createSameTeam(int line, int column, Expression e) {
		return new ExpressionSameTeam(line,column, e);
	}

	@Override
	public Expression createSearchObj(int line, int column, Expression e) {
		return new ExpressionSearchObj(line,column, e);
	}

	@Override
	public Expression createIsWorm(int line, int column, Expression e) {
		return new ExpressionIsWorm(line,column, e);
	}

	@Override
	public Expression createIsFood(int line, int column, Expression e) {
		return new ExpressionIsFood(line,column, e);
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		return new ExpressionVariable(line,column,name);
	}

	@Override
	public Expression createLessThan(int line, int column, Expression e1, Expression e2) {
		return new ExpressionLessThan(line,column, e1, e2);
	}

	@Override
	public Expression createGreaterThan(int line, int column, Expression e1, Expression e2) {
		return new ExpressionGreaterThan(line,column, e1, e2);
	}

	@Override
	public Expression createLessThanOrEqualTo(int line, int column, Expression e1,
			Expression e2) {
		return new ExpressionLessThanOrEqualTo(line,column, e1, e2);
	}

	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column, Expression e1,
			Expression e2) {
		return new ExpressionGreaterThanOrEqualTo(line,column, e1, e2);
	}

	@Override
	public Expression createEquality(int line, int column, Expression e1, Expression e2) {
		return new ExpressionEqualTo(line,column, e1, e2);
	}

	@Override
	public Expression createInequality(int line, int column, Expression e1, Expression e2) {
		return new ExpressionDifferentFrom(line,column, e1, e2);
	}

	@Override
	public Expression createAdd(int line, int column, Expression e1, Expression e2) {
		return new ExpressionAddition(line,column, e1, e2);
	}

	@Override
	public Expression createSubtraction(int line, int column, Expression e1, Expression e2) {
		return new ExpressionSubtraction(line,column, e1, e2);
	}

	@Override
	public Expression createMul(int line, int column, Expression e1, Expression e2) {
		return new ExpressionMultiplication(line,column, e1, e2);
	}

	@Override
	public Expression createDivision(int line, int column, Expression e1, Expression e2) {
		return new ExpressionDivision(line,column, e1, e2);
	}

	@Override
	public Expression createSqrt(int line, int column, Expression e) {
		return new ExpressionSquareRoot(line,column, e);
	}

	@Override
	public Expression createSin(int line, int column, Expression e) {
		return new ExpressionSine(line,column, e);
	}

	@Override
	public Expression createCos(int line, int column, Expression e) {
		return new ExpressionCosine(line,column, e);
	}

	@Override
	public Statement createTurn(int line, int column, Expression angle) {
		return new StatementActionTurn(line,column,angle);
	}

	@Override
	public Statement createMove(int line, int column) {
		return new StatementActionMove(line,column);
	}

	@Override
	public Statement createJump(int line, int column) {
		return new StatementActionJump(line,column);
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new StatementActionToggleWeapon(line,column);
	}

	@Override
	public Statement createFire(int line, int column, Expression yield) {
		return new StatementActionFire(line,column,yield);
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new StatementActionSkip(line,column);
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
		return new Type<Object>();
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name,
			Type<?> type) {
		return new ExpressionVariableAccess(line, column, name, type);
	}

}

package worms.model.expressions;

import worms.model.Expression;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionSameTeam extends Expression {
	
	public ExpressionSameTeam(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Boolean> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		if ( val instanceof Worm ) //TODO static or dynamic type checked? should be dynamic type, because static type is "Object" 
			if (getRootProgram().getWorm().getTeam() == null) {	//Checker if there are no teams created
				System.out.println("Team null, returning false.");
				return new Type<Boolean>(false);
			}
			else {
				System.out.println("Team not null, returning true/false");
				System.out.println("Returntype: " + getRootProgram().getWorm().getTeamName());		//Team from PCworm
				return new Type<Boolean>(getRootProgram().getWorm().getTeam() == ((Worm)val).getTeam());
			}
			//return new Type<Boolean>(getStatement().getRootProgram().getWorm().getTeam()==((Worm)val).getTeam());
		getRootProgram().typeErrorOccurred();
		return null;
	}

}

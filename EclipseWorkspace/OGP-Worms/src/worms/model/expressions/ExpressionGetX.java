package worms.model.expressions;

import worms.model.Expression;
import worms.model.Food;
import worms.model.Type;
import worms.model.Worm;

public class ExpressionGetX extends Expression {
	
	public ExpressionGetX(int line, int column, Expression e) {
		super(line,column,e);
	}

	@Override
	public Type<Double> evaluate() {
		Object val = getExpressions().get(0).evaluate().getValue();
		
		
		
		
//		try {
//			return new Type<Double>(((Worm)(((Entity)val).getEntity())).getX());
//		} catch (Exception e1) {
//			try {
//				return new Type<Double>(((Food)(((Entity)val).getEntity())).getX());
//			} catch (Exception e2) {
//				System.out.println("derp");
//				getRootProgram().typeErrorOccurred();
//				return null;
//			}
//		}
		
		
		if ( val instanceof Worm )
			return new Type<Double>(((Worm)val).getX());
		if ( val instanceof Food )
			return new Type<Double>(((Food)val).getX());
		getRootProgram().typeErrorOccurred();
		return null;
		
		
		
		
		
	}

}

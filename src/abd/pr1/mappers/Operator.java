package abd.pr1.mappers;

/**
 * Representa un operador de comparación. Se utiliza en QueryCondition.
 * 
 * @author Manuel Montenegro
 *
 */
public enum Operator {
	EQ, LE, LT, GE, GT, NEQ, LIKE;
	
	/*
	 * Sería conveniente añadir un atributo a cada enum con la representación
	 * de cada operador (en forma de cadena)
	 */
	
	@Override
	public String toString() {
		switch(this){
		
		case EQ: 
			return "=";
		case LE:
			return "<=";
		case LT:
			return "<";
		case GE:
			return ">=";
		case GT:
			return ">";
		case NEQ:
			return "!=";
		case LIKE:
			return "LIKE";
		default:
			return null;
		}

	}
}

package abd.pr1.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;


public abstract class AbstractMapper<T,K> {

	protected DataSource ds; 
	
	/**
	 * Devuelve el nombre de la tabla asociada al mapper concreto. Esta
	 * tabla será la utilizada en todas las consultas SQL.
	 * 
	 * @return Cadena con el nombre de la tabla
	 */
	protected abstract String getTableName();

	/**
	 * Devuelve los nombres de las columnas de la tabla asociada al mapper
	 * concreto.
	 * 
	 * @return Array con los nombres de columnas de la tabla.
	 */
	protected abstract String[] getColumnNames();

	/**
	 * Divide un objeto dado en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getColumnNames() 
	 * 
	 * @param object Objeto a dividir
	 * @return Componentes del objeto dividido
	 */
	protected abstract Object[] serializeObject(T object);

	/**
	 * Devuelve los nombres de las columnas que forman la clave primaria de
	 * la tabla del mapper concreto.
	 * 
	 * @return Array con nombres de columnas clave
	 */
	protected abstract String[] getKeyColumnNames();
	
	/**
	 * Divide una clave primaria en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getKeyColumnNames() 
	 * 
	 * @param key Clave a dividir
	 * @return Componentes de la clave pasada como parámetro
	 */
	protected abstract Object[] serializeKey(K key);
	
	/**
	 * Construye un objeto a partir del resultado de una consulta.
	 * 
	 * @param rs ResultSet con el resultado actual de la consulta.
	 * @return Objeto (de tipo T) representado por la fila contenida en rs
	 * @throws SQLException
	 */
	protected abstract T buildObject(ResultSet rs) throws SQLException;

	/**
	 * Obtiene la clave primaria del objeto pasado como parámetro. 
	 * 
	 * @param object Objeto
	 * @return Clave primera del objeto pasado como parámetro.
	 */
	protected abstract K getKey(T object);

	
	public AbstractMapper(DataSource ds) {
		this.ds = ds;
	}
	
	
	/**
	 * Devuelve la lista de objetos que satisfacen todas las condiciones
	 * del array pasado como parámetro
	 * 
	 * @param conditions Objetos de la clase QueryCondition que especifican las condiciones
	 *                   de los objetos a buscar
	 * @return Lista de objetos de la tabla que cumplen las condiciones dadas. 
	 *         Si ninguno de ellos las cumple, se devuelve una lista vacía.
	 */
	protected List<T> findByConditions(QueryCondition[] conditions) {	
		Connection con			= null;
		PreparedStatement pst 	= null;
		ResultSet rs			= null;		
		List<T> resultList 		= new ArrayList<>();
		
		try{
			con = ds.getConnection();
			String[] columnNames 			= getColumnNames();
			String columnNamesWithCommas 	= StringUtils.join(columnNames, ", ");
			
			String[] conditionsStr 			= new String[conditions.length];
			
			for(int i = 0; i < conditions.length; i++){
				conditionsStr[i] = conditions[i].getColumnName() + " " + conditions[i].getOperator() +" ?";
			}
			String sql = "SELECT " + columnNamesWithCommas + 
						" FROM "   + getTableName() + 
						" WHERE "  + StringUtils.join(conditionsStr, " AND ");
			
			pst = con.prepareStatement(sql);
			
			for(int i = 0; i < conditions.length; i++){
				pst.setObject(i + 1,  conditions[i].getValue());
			}
					
			rs = pst.executeQuery();
			while(rs.next()){
				resultList.add(buildObject(rs));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		 }

		return resultList;
	}

	public T findById(K key) {
		 QueryCondition[] conditions = getConditionsFromKey(key);
		 T result = null;	 
		 List<T> results = findByConditions(conditions);
		 
		if(!results.isEmpty())
			result = results.get(0);
		
		return result;
		
	}

	

	public void update(T object) {
		Connection con = null;
		String[] columnNames 	= getColumnNames();					// Nombre de las columnas
		String[] keyColumnNames = getKeyColumnNames();				// Nombre de la columna clave
		String[] assignments 	= new String[columnNames.length];	// Asignaciones para el SET
		
		for(int i = 0 ; i < assignments.length; i++){
			assignments[i] = columnNames[i] + " = ?";
		}
		
		QueryCondition[] conditions = getConditionsFromKey(getKey(object));
		
		String sql = "UPDATE " + getTableName() + 
					 " SET " + StringUtils.join(assignments, ", ") + 
					 " WHERE " + getWhereCondition(conditions);
		
		PreparedStatement stm = null;
		try{
			con = ds.getConnection();
			stm = con.prepareStatement(sql);
		
			Object[] objectFields = serializeObject(object);
			
			int j = 1;
			
			for(int i = 0 ; i < columnNames.length; i++){
				stm.setObject(j, objectFields[i]);
				j++;
			}
			for(int i = 0 ; i < keyColumnNames.length; i++){
				stm.setObject(j, conditions[i].getValue());
				j++;
			}
			stm.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) stm.close();
			if (con != null) con.close();
		} catch (Exception e) {}
	}
	

	public void insert(T object) {
		Connection con = null;
		String[] columnNames 	= getColumnNames();	           		// Nombre de la columna clave
		String[] interrogations = new String[columnNames.length];	// Asignaciones para el SET
	
		
		
		for(int i = 0 ; i < columnNames.length; i++){
			interrogations[i] = "?";
		}
		
		String sql = "INSERT"  + 
					" INTO "  + getTableName() + 
					" VALUES (" + StringUtils.join(interrogations, ", ") + ")";
					
		PreparedStatement stm = null;
		try{
			con = ds.getConnection();
			stm = con.prepareStatement(sql);
		
			Object[] objectFields = serializeObject(object);

			for(int i = 0 ; i < columnNames.length; i++){
				stm.setObject(i+1, objectFields[i]);
			}
			
			stm.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) stm.close();
			if (con != null) con.close();
		} catch (Exception e) {}
		
		
	}
	
	public void delete(T object) {
		Connection con = null;
		String[] keyColumnNames = getKeyColumnNames();				// Nombre de la columna clave
		
		QueryCondition[] conditions = getConditionsFromKey(getKey(object));
		
		String sql = "DELETE FROM " + getTableName() + 
					 " WHERE " +  getWhereCondition(conditions);
		
		PreparedStatement stm = null;
		
		try{
			con = ds.getConnection();
			stm = con.prepareStatement(sql);
		
			Object[] objectFields = serializeObject(object);
			for(int i = 0 ; i < keyColumnNames.length; i++){
				stm.setObject(i+1, objectFields[i]);
			}
	
			stm.executeUpdate();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (stm != null) stm.close();
			if (con != null) con.close();
		} catch (Exception e) {}
	}
	
	
	private String getWhereCondition(QueryCondition[] conditions) {
		String[] arrayCondition = new String[conditions.length];
		String conditionsStr = "";
		QueryCondition qc;
		for(int i = 0; i < conditions.length; i++){
			qc = conditions[i];
			arrayCondition[i] = qc.getColumnName() + " " + qc.getOperator().toString() + " ?"; 
		}
		
		conditionsStr = StringUtils.join(arrayCondition, " AND ");
		return conditionsStr;		
	}

	private QueryCondition[] getConditionsFromKey(K key){
		String[] keyColumnNames 	= getKeyColumnNames();
		QueryCondition[] conditions = new QueryCondition[keyColumnNames.length];
		Object[] columnValues 		= serializeKey(key);
		
		for(int i = 0; i < conditions.length; i++){
			conditions[i] = new QueryCondition(keyColumnNames[i], Operator.EQ, columnValues[i]);
		}
		
		return conditions;		
	}
}
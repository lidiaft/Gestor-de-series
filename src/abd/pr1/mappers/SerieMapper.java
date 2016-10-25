package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Serie;

public class SerieMapper extends AbstractMapper<Serie, Integer>{


	private static final String[] SERIE_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] SERIE_COLUMN_NAMES = new String[] { "id", "titular", "nombre",  "sinopsis", "añoFinalizacion"};
	private static final String SERIE_TABLE_NAME = "serie";

	
	public SerieMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return SERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return SERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Serie object) {
		return new Object[] { 
				object.getId(), 	
				object.getTitular(),
				object.getNombre(),
				object.getSinopsis(),
				object.getAnioFin()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return SERIE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected Serie buildObject(ResultSet rs) throws SQLException {
		Integer id 				 = rs.getInt("id");
		String titular  	 	 = rs.getString("titular");
		String nombre 			 = rs.getString("nombre");
		String sinopsis 		 = rs.getString("sinopsis");
		Integer anioFinalizacion = rs.getInt("añoFinalizacion");
		
		return new Serie(id, titular, nombre, sinopsis, anioFinalizacion);
	}

	@Override
	protected Integer getKey(Serie object) {
		return object.getId();
	}
	
	public List<Serie> buscarSeries(Object parteNombre){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, "%" + parteNombre + "%");
			
		List<Serie> serie = findByConditions(conditions);
		
		return serie;
	}
	
	public Serie buscarUnaSerie(Object titular){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, titular);
		Serie serie = null;
		List<Serie> series = findByConditions(conditions);
		if(!series.isEmpty())
			serie = series.get(0);
		return serie;
	}

}

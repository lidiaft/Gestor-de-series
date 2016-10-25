package abd.pr1.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.ComentarioSerie;

public class ComenSerieMapper extends AbstractMapper<ComentarioSerie, Integer>{

	private static final String[] COMENTARIO_SERIE_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] COMENTARIO_SERIE_COLUMN_NAMES = new String[] { "id", "fecha", "texto", "nickUsuario", "idSerie"};
	private static final String COMENTARIO_SERIE_TABLE_NAME = "comentarioserie";

	
	
	public ComenSerieMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return COMENTARIO_SERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return COMENTARIO_SERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(ComentarioSerie object) {
		return new Object[] { 
				object.getId(), 	
				object.getFecha(), 
				object.getTexto(),
				object.getIdSerie()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return COMENTARIO_SERIE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected ComentarioSerie buildObject(ResultSet rs) throws SQLException {
		Integer id 			= rs.getInt("id");
		Date fecha 			= rs.getDate("fecha");
		String texto 		= rs.getString("texto");
		String nickUsuario 	= rs.getString("nickUsuario");
		Integer idSerie 	= rs.getInt("idSerie");
		
		return new ComentarioSerie(id, fecha, texto, nickUsuario, idSerie);
	}

	@Override
	protected Integer getKey(ComentarioSerie object) {
		return object.getId();
	}

}

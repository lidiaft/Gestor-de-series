package abd.pr1.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.ComentarioEpisodio;

public class ComenEpisodioMapper extends AbstractMapper<ComentarioEpisodio, Integer>{

	private static final String[] COMENTARIO_EPI_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] COMENTARIO_EPI_COLUMN_NAMES = new String[] { "id", "fecha", "texto", "nickUsuario", "idEpisodio"};
	private static final String COMENTARIO_EPI_TABLE_NAME = "comentarioepisodio";

	
	
	public ComenEpisodioMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return COMENTARIO_EPI_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return COMENTARIO_EPI_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(ComentarioEpisodio object) {
		return new Object[] { 
				object.getId(), 	
				object.getFecha(), 
				object.getTexto(), 
				object.getIdEpisodio()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return COMENTARIO_EPI_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected ComentarioEpisodio buildObject(ResultSet rs) throws SQLException {
		Integer id 			= rs.getInt("id");
		Date fecha 			= rs.getDate("fecha");
		String texto 		= rs.getString("texto");
		String nickUsuario 	= rs.getString("nickUsuario");
		Integer idEpisodio 	= rs.getInt("idEpisodio");
		
		return new ComentarioEpisodio(id, fecha, texto, nickUsuario, idEpisodio);
	}

	@Override
	protected Integer getKey(ComentarioEpisodio object) {
		return object.getId();
	}

}

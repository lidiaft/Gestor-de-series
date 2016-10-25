package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.VotoEpisodio;

public class VotoEpisodioMapper extends AbstractMapper<VotoEpisodio, Integer>{

	private static final String[] VOTO_EPISODIO_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] VOTO_EPISODIO_COLUMN_NAMES = new String[] { "id", "fecha", "texto", "nickUsuario", "idEpisodio"};
	private static final String VOTO_EPISODIO_TABLE_NAME = "votoepisodio";

	
	
	public VotoEpisodioMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return VOTO_EPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return VOTO_EPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(VotoEpisodio object) {
		return new Object[] { 
				object.getId(), 
				object.getIdEpisodio(),
				object.getNota(),
				object.getNickUsuario()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return VOTO_EPISODIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected VotoEpisodio buildObject(ResultSet rs) throws SQLException {
		Integer id 			= rs.getInt("id");
		String nickUsuario 	= rs.getString("nickUsuario");
		Integer idEpisodio 	= rs.getInt("idEpisodio");
		Integer nota 		= rs.getInt("nota");
		
		return new VotoEpisodio(id, idEpisodio, nota, nickUsuario);
	}

	@Override
	protected Integer getKey(VotoEpisodio object) {
		return object.getId();
	}

}

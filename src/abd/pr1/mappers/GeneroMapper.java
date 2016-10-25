package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Generos;


public class GeneroMapper extends AbstractMapper<Generos, Integer>{


	private static final String[] GENERO_KEY_COLUMN_NAMES = new String[] { "idSerie" };
	private static final String[] GENERO_COLUMN_NAMES = new String[] { "idSerie", "comedia", "terror", "thriller", "drama" };
	private static final String GENERO_TABLE_NAME = "genero";

	public GeneroMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return GENERO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return GENERO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Generos object) {
		return new Object[] { 
				object.getSerie(),	
				object.getComedia(),
				object.getDrama(),
				object.getTerror(),
				object.getThriller()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return GENERO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}

	
	@Override
	protected Generos buildObject(ResultSet rs) throws SQLException {
		Integer idSerie 	= rs.getInt("idSerie");
		String comedia 		= rs.getString("comedia");
		String thriller 	= rs.getString("thriller");
		String terror 		= rs.getString("terror");
		String drama 		= rs.getString("drama");
		return new Generos(idSerie, comedia, drama, terror, thriller);
	}

	@Override
	protected Integer getKey(Generos object) {
		return object.getSerie();
	}

}

package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.VotoSerie;

public class VotoSerieMapper extends AbstractMapper<VotoSerie, Integer>{

	private static final String[] VOTO_SERIE_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] VOTO_SERIE_COLUMN_NAMES = new String[] { "id", "idSerie", "nota", "nickUsuario"};
	private static final String VOTO_SERIE_TABLE_NAME = "votoserie";
	
	public VotoSerieMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return VOTO_SERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return VOTO_SERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(VotoSerie object) {
		return new Object[] { 
				object.getId(), 
				object.getIdSerie(),
				object.getNota(),
				object.getNickUsuario()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return VOTO_SERIE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected VotoSerie buildObject(ResultSet rs) throws SQLException {
		Integer id 			= rs.getInt("id");
		String nickUsuario 	= rs.getString("nickUsuario");
		Integer idSerie 	= rs.getInt("idSerie");
		Integer nota 		= rs.getInt("nota");
		
		return new VotoSerie(id, idSerie, nota, nickUsuario);
	}

	@Override
	protected Integer getKey(VotoSerie object) {
		return object.getId();
	}

	public VotoSerie buscarVoto(Integer idSerie, String nickUsuario) {
		QueryCondition[] conditions = new QueryCondition[2];
		conditions[0] = new QueryCondition("idSerie", Operator.LIKE, idSerie);
		conditions[1] = new QueryCondition("nickUsuario", Operator.EQ, nickUsuario);
		List<VotoSerie> votosSerie = findByConditions(conditions);
		VotoSerie voto = null;
		if(!votosSerie.isEmpty())
			voto = votosSerie.get(0);
		return voto;
		
	}

}
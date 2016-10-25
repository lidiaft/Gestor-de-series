package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Seguido;
import abd.pr1.tiposDeDatos.SeguidoClave;
import abd.pr1.tiposDeDatos.Serie;

public class SeguidoMapper extends AbstractMapper<Seguido, SeguidoClave>{
	
	private static final String[] SEGUIDOR_KEY_COLUMN_NAMES = new String[] {"idSerie", "nickUsuario"};
	private static final String[] SEGUIDOR_COLUMN_NAMES = new String[] {"idSerie", "nickUsuario"};
	private static final String SEGUIDOR_TABLE_NAME = "seguido";

	public SeguidoMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return SEGUIDOR_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return SEGUIDOR_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Seguido object) {
		return new Object[] { 
				object.getIdSerie(), 
				object.getNickUsuario()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return SEGUIDOR_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(SeguidoClave key) {
		return new Object[] { 
				key.getIdSerie(), 
				key.getNickUsuario()
		};
	}

	
	@Override
	protected Seguido buildObject(ResultSet rs) throws SQLException {
		Integer idSerie 	= rs.getInt("idSerie");
		String nickUsuario  = rs.getString("nickUsuario");

		return new Seguido(idSerie, nickUsuario);
	}

	@Override
	protected SeguidoClave getKey(Seguido object) {
		return new SeguidoClave(object.getIdSerie(), object.getNickUsuario());
	}
	
	public List<Serie> buscarSeriesSeguidas(String nick){
		SerieMapper serieMapper = new SerieMapper(ds);
		
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nickUsuario", Operator.EQ, nick);
		
		List<Seguido> listSeguido = findByConditions(conditions);

		List<Serie> listSeriesSeguidas = new ArrayList<>();
		Seguido seguido;	
		int idSerie;
		
		for(int i = 0; i < listSeguido.size(); i++){
			seguido = listSeguido.get(i);
			idSerie = seguido.getIdSerie();
			listSeriesSeguidas.add(serieMapper.findById(idSerie));
		}
		
		return listSeriesSeguidas;
		
	}

}

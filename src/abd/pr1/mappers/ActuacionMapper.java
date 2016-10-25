package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Actuacion;
import abd.pr1.tiposDeDatos.ActuacionClave;

public class ActuacionMapper extends AbstractMapper<Actuacion, ActuacionClave> {

	private static final String[] ACTUACION_KEY_COLUMN_NAMES = new String[] {"idEpisodio", "nifActor", "idPersonaje" };
	private static final String[] ACTUACION_COLUMN_NAMES = new String[] { "idEpisodio", "nifActor", "idPersonaje"};
	private static final String ACTUACION_TABLE_NAME = "actua";

	public ActuacionMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return ACTUACION_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return ACTUACION_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Actuacion object) {
		return new Object[] { 
				object.getIdEpisodio(), 
				object.getNifActor(), 			
				object.getIdPersonaje()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return ACTUACION_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ActuacionClave key) {
		return new Object[] { 
				key.getIdEpisodio(), 
				key.getNifActor(), 
				key.getIdPersonaje() 
		};
	}

	
	@Override
	protected Actuacion buildObject(ResultSet rs) throws SQLException {
		Integer idEpisodio 		= rs.getInt("idEpisodio");
		String nifActor 		= rs.getString("nifActor");
		Integer idPersonaje 	= rs.getInt("idPersonaje");

		return new Actuacion(idEpisodio, nifActor, idPersonaje);
	}

	@Override
	protected ActuacionClave getKey(Actuacion object) {
		return new ActuacionClave(object.getIdEpisodio(), object.getNifActor(), object.getIdPersonaje());
	}
	
	public List<Actuacion> buscarActuaciones(String nif){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nifActor", Operator.EQ, nif);
		List<Actuacion> actuaciones = findByConditions(conditions);
		
		return actuaciones;
	}

}

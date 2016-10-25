package abd.pr1.mappers;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Actor;

public class ActorMapper extends AbstractMapper<Actor, String>{

	private static final String[] ACTOR_KEY_COLUMN_NAMES = new String[] { "nif" };
	private static final String[] ACTOR_COLUMN_NAMES = new String[] { "nif", "nombre", "fechaNacimiento", "foto" };
	private static final String ACTOR_TABLE_NAME = "actor";

	public ActorMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return ACTOR_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return ACTOR_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Actor object) {
		return new Object[] { 
				object.getNif(), 	
				object.getNombre(), 
				object.getFechaNac(), 
				object.getFoto()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return ACTOR_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(String key) {
		return new Object[] { 
				key 
		};
	}

	
	@Override
	protected Actor buildObject(ResultSet rs) throws SQLException {
		String nif 			= rs.getString("nif");
		String nombre 		= rs.getString("nombre");
		Date fechaNac		= rs.getDate("fechaNacimiento");
		Blob foto 		    = rs.getBlob("foto");
		return new Actor(nif, nombre, fechaNac, foto);
	}

	@Override
	protected String getKey(Actor object) {
		return object.getNif();
	}

	
	public Actor buscarUnActor(Object nombre){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, nombre);
		Actor actor = null;
		List<Actor> actores = findByConditions(conditions);
		if(!actores.isEmpty())
			actor = actores.get(0);
		return actor;
	}
	
	
	public List<Actor> buscarActores(Object parteNombre){
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, "%" + parteNombre + "%");
			
		List<Actor> actor = findByConditions(conditions);
		
		return actor;
	}

}

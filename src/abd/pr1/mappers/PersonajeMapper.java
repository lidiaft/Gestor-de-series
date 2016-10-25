package abd.pr1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Personaje;

public class PersonajeMapper extends AbstractMapper<Personaje, Integer>{


	private static final String[] PERSONAJE_KEY_COLUMN_NAMES = new String[] { "id" };
	private static final String[] PERSONAJE_COLUMN_NAMES = new String[] { "id", "nombre", "descripcion"};
	private static final String PERSONAJE_TABLE_NAME = "personaje";

	public PersonajeMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return PERSONAJE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return PERSONAJE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Personaje object) {
		return new Object[] { 
				object.getId(), 	
				object.getNombre(), 
				object.getDescripcion()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return PERSONAJE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		return new Object[] { 
				key 
		};
	}

	
	@Override
	protected Personaje buildObject(ResultSet rs) throws SQLException {
		Integer id 			= rs.getInt("id");
		String nombre 		= rs.getString("nombre");
		String descripcion 	= rs.getString("descripcion");
		
		return new Personaje(id, nombre, descripcion);
	}

	@Override
	protected Integer getKey(Personaje object) {
		return object.getId();
	}

	public List<Personaje> buscarPersonajes(String nombre) {
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, "%" + nombre + "%");
			
		List<Personaje> personajes = findByConditions(conditions);
		
		return personajes;
	}

	public Personaje buscarUnPersonaje(String nombre) {
		QueryCondition[] conditions = new QueryCondition[1];
		conditions[0] = new QueryCondition("nombre", Operator.LIKE, nombre);
		Personaje personaje = null;
		List<Personaje> personajes = findByConditions(conditions);
		if(!personajes.isEmpty())
			personaje = personajes.get(0);
		return personaje;
	}

}

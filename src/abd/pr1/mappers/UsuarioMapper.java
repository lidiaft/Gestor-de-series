package abd.pr1.mappers;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import abd.pr1.tiposDeDatos.Usuario;

public class UsuarioMapper extends AbstractMapper<Usuario, String>{
	private static final String[] USUARIO_KEY_COLUMN_NAMES = new String[] { "nick" };
	private static final String[] USUARIO_COLUMN_NAMES = new String[] { "nick", "contraseña", "foto", "fechaNacimiento"};
	private static final String USUARIO_TABLE_NAME = "usuario";

	
	public UsuarioMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return USUARIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return USUARIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Usuario object) {
		return new Object[] { 
				object.getNick(),
				object.getPassword(),
				object.getFoto(),
				object.getFechaNac()
		};
	}

	@Override
	protected String[] getKeyColumnNames() {
		return USUARIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(String key) {
		return new Object[] { 
				key 
		};
	}


	@Override
	protected Usuario buildObject(ResultSet rs) throws SQLException {
		String nick 	= rs.getString("nick");
		String password = rs.getString("contraseña");
		Blob foto 		= rs.getBlob("foto");
		Date fechaNac 	= rs.getDate("fechaNacimiento");

		return new Usuario(nick, password, foto, fechaNac);
	}

	@Override
	protected String getKey(Usuario object) {
		return object.getNick();
	}
	
}

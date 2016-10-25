package abd.pr1.tiposDeDatos;

import java.sql.Blob;
import java.util.Date;

public class Usuario {

	private String nick;
	private String password;
	private Blob foto;
	private Date fechaNac;
	

	

	public Usuario(String nick, String password, Blob foto, Date fechaNac){
		this.nick 		= nick;
		this.password	= password;
		this.foto 		= foto;
		this.fechaNac 	= fechaNac;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}
	
	public String toString(){
		return "'" + nick 	  + "', '" +
				password 	  + "', "  +
				foto + ", "   +
				fechaNac();	
	}

	@SuppressWarnings("deprecation")
	private String fechaNac() {
		return  "'" + fechaNac.getYear() + "-" + fechaNac.getMonth()+ "-" + fechaNac.getDay() + "'";
	}

	
}

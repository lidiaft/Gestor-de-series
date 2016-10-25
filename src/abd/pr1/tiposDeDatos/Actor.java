package abd.pr1.tiposDeDatos;

import java.sql.Blob;
import java.util.Date;

public class Actor {
	private String nif;
	private String nombre;
	private Date fechaNac;
	private Blob foto;
	
	public Actor(){
		
	}
	
	public Actor(String nif, String nombre, Date fechaNac, Blob foto){
		this.nif = nif;
		this.nombre	= nombre;
		this.fechaNac = fechaNac;
		this.foto = foto;
	}
	

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return nombre;	
	}
}

package abd.pr1.tiposDeDatos;

import java.sql.Date;

public class ComentarioSerie {
	private Integer id;
	private Date fecha;
	private String texto;
	private String nickUsuario;
	private Integer idSerie;
	
	
	public ComentarioSerie (Integer id ,Date fecha, String texto, String nickUsuario, Integer idSerie)
	{
		this.id = id;
		this.fecha = fecha;
		this.texto = texto;
		this.nickUsuario = nickUsuario;
		this.idSerie = idSerie;
	}
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getNickUsuario() {
		return nickUsuario;
	}
	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}

	public Integer getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}
	
	public String toString(){
		return "Comentario [id = " + id + 
						", fecha = " + fecha + 
						", texto = " + texto +
						", nickUsuario = " + nickUsuario +
						", idSerie = " + idSerie + "]";	
	}
}

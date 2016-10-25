package abd.pr1.tiposDeDatos;

import java.sql.Date;

public class ComentarioEpisodio {
	private Integer id;
	private Date fecha;
	private String texto;
	private String nickUsuario;
	private Integer idEpisodio;
	
	
	public ComentarioEpisodio (Integer id ,Date fecha, String texto, String nickUsuario, Integer idEpisodio)
	{
		this.id = id;
		this.fecha = fecha;
		this.texto = texto;
		this.nickUsuario = nickUsuario;
		this.idEpisodio = idEpisodio;
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
	public Integer getIdEpisodio() {
		return idEpisodio;
	}
	public void setIdEpisodio(Integer idEpisodio) {
		this.idEpisodio = idEpisodio;
	}

	
	public String toString(){
		return "Comentario [id = " + id + 
						", fecha = " + fecha + 
						", texto = " + texto +
						", nickUsuario = " + nickUsuario +
						", idEpisodio = " + idEpisodio + "]";	
	}
}

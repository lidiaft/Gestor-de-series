package abd.pr1.tiposDeDatos;

public class Visionado {

	private String nickUsuario;
	private Integer idEpisodio;

	public Visionado(String nickUsuario, Integer idEpisodio){
		this.nickUsuario = nickUsuario;
		this.idEpisodio = idEpisodio;
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
		return "Usuario [nickUsuario = " + nickUsuario + 
					  ", idEpisodio = " + idEpisodio +"]";	
	}
}

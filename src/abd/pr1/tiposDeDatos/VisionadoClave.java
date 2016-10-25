package abd.pr1.tiposDeDatos;

public class VisionadoClave {
	private String nickUsuario;
	private Integer idEpisodio;

	public VisionadoClave(String nickUsuario, Integer idEpisodio){
		this.nickUsuario = nickUsuario;
		this.idEpisodio  = idEpisodio;
	}

	public String getNickUsuario() {
		return nickUsuario;
	}

	public Integer getIdEpisodio() {
		return idEpisodio;
	}
}

package abd.pr1.tiposDeDatos;

public class VotoEpisodio {

	private Integer id;
	private Integer idEpisodio;
	private Integer nota;
	private String nickUsuario;

	
	public VotoEpisodio(Integer id, Integer idEpisodio, Integer nota, String nickUsuario){
		this.id = id;
		this.idEpisodio = idEpisodio;
		this.nota = nota;
		this.nickUsuario = nickUsuario;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEpisodio() {
		return idEpisodio;
	}

	public void setIdEpisodio(Integer idEpisodio) {
		this.idEpisodio = idEpisodio;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public String getNickUsuario() {
		return nickUsuario;
	}

	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}

	public String toString(){
		return "Usuario [id = " + id +
				", idEpisodio = " + idEpisodio +
				", nota = " + nota +
				", nickUsuario = " + nickUsuario +"]";	
	}
	
}

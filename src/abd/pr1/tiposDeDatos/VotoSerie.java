package abd.pr1.tiposDeDatos;

public class VotoSerie {

	private Integer id;
	private Integer idSerie;
	private Integer nota;
	private String nickUsuario;

	
	public VotoSerie(Integer id, Integer idSerie, Integer nota, String nickUsuario){
		this.id = id;
		this.idSerie = idSerie;
		this.nota = nota;
		this.nickUsuario = nickUsuario;
	}


	public VotoSerie(Integer idSerie, int nota, String nickUsuario) {
		this.idSerie = idSerie;
		this.nota = nota;
		this.nickUsuario = nickUsuario;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
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
				", idSerie = " + idSerie +
				", nota = " + nota +
				", nickUsuario = " + nickUsuario +"]";	
	}
	
}

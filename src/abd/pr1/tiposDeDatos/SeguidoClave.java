package abd.pr1.tiposDeDatos;

public class SeguidoClave {
	private Integer idSerie;
	private String nickUsuario ;

	public SeguidoClave(Integer idSerie, String nickUsuario){
		this.idSerie 	 = idSerie;
		this.nickUsuario = nickUsuario;
	}

	public Integer getIdSerie() {
		return idSerie;
	}

	public String getNickUsuario() {
		return nickUsuario;
	}
}

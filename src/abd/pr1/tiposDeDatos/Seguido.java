package abd.pr1.tiposDeDatos;

public class Seguido {

	private Integer idSerie;
	private String nickUsuario ;

	public Seguido(Integer idSerie, String nickUsuario){
		this.idSerie = idSerie;
		this.nickUsuario =nickUsuario;
	}

	public Integer getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	public String getNickUsuario() {
		return nickUsuario;
	}

	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}


	public String toString(){
		return "Seguidor [idSerie = " + idSerie + 
					   ", nickUsuario = " + nickUsuario + "]";	
	}
	
}

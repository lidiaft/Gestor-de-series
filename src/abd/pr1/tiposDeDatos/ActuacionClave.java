package abd.pr1.tiposDeDatos;

public class ActuacionClave {
	private Integer idEpisodio;
	private String nifActor;
	private Integer idPersonaje;

	public ActuacionClave(Integer idEpisodio, String nifActor, Integer idPersonaje){
		this.idEpisodio  = idEpisodio;
		this.nifActor 	 = nifActor;
		this.idPersonaje = idPersonaje;	
	}

	public Integer getIdEpisodio() {
		return idEpisodio;
	}

	public String getNifActor() {
		return nifActor;
	}

	public Integer getIdPersonaje() {
		return idPersonaje;
	}
}

package abd.pr1.tiposDeDatos;


public class Actuacion {

	private Integer idEpisodio;
	private String nifActor;
	private Integer idPersonaje;

	public Actuacion(Integer idEpisodio, String nifActor, Integer idPersonaje){
		this.idEpisodio = idEpisodio;
		this.nifActor = nifActor;
		this.idPersonaje = idPersonaje;
		
	}

	public Integer getIdEpisodio() {
		return idEpisodio;
	}

	public void setIdEpisodio(Integer idEpisodio) {
		this.idEpisodio = idEpisodio;
	}

	public String getNifActor() {
		return nifActor;
	}

	public void setNifActor(String nifActor) {
		this.nifActor = nifActor;
	}

	public Integer getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(Integer idPersonaje) {
		this.idPersonaje = idPersonaje;
	}
	
	public String toString(){
		return "Actuacion [idEpisodio = " + idEpisodio + 
						", nifActor = " + nifActor + 
						", idPersonaje = " + idPersonaje + "]";	
	}
}

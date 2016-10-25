package abd.pr1.tiposDeDatos;

public class Generos {
	private Integer idSerie;
	private String comedia;
	private String drama;
	private String terror;
	private String thriller;
	//comedia, drama, terror, thriller, animacion, accion, suspense, romantica;

	public Generos(Integer idSerie, String comedia, String drama, String terror, String thriller){
		this.idSerie  = idSerie;
		this.comedia  = comedia;
		this.terror   = terror;
		this.thriller = thriller;	
		this.drama 	  = drama;
	}

	public Generos(String comedia, String drama, String terror, String thriller){
		this.comedia  = comedia;
		this.terror   = terror;
		this.thriller = thriller;	
		this.drama 	  = drama;	
	}
	
	public Integer getSerie() {
		return this.idSerie;
	}

	public void setSerie(Integer idSerie){
		this.idSerie = idSerie;
	}
	
	public String getComedia() {
		return comedia;
	}

	public void setComedia(String comedia) {
		this.comedia = comedia;
	}

	public String getDrama() {
		return drama;
	}

	public void setDrama(String drama) {
		this.drama = drama;
	}

	public String getTerror() {
		return terror;
	}

	public void setTerror(String terror) {
		this.terror = terror;
	}

	public String getThriller() {
		return thriller;
	}

	public void setThriller(String thriller) {
		
		this.thriller = thriller;
	}
	
	public String toString(){
		return comedia + " " + terror + " " + thriller + " " + drama;
	}
}

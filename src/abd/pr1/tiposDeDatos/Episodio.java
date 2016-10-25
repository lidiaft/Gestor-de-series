package abd.pr1.tiposDeDatos;

import java.util.Date;


public class Episodio {
	private Integer id;
	private Integer numEpisodio;
	private Integer numTemporada;
	private String sinopsis;
	private Date fechaInicio;
	private String titulo;
	private Integer idSerie;
	
	
	public Episodio(Integer id,Integer numEpisodio,Integer numTemporada,String sinopsis,Date fechaInicio, String titulo,Integer idSerie)
	{
		this.id = id;
		this.numEpisodio = numEpisodio;
		this.numTemporada = numTemporada;
		this.sinopsis = sinopsis;
		this.fechaInicio= fechaInicio;
		this.titulo = titulo;
		this.idSerie = idSerie;
	}
	
		
	public Episodio(String titulo) {
		this.titulo = titulo;
	}


	public Episodio(Integer numEpisodio,Integer numTemporada,String sinopsis,Date fechaInicio, String titulo,Integer idSerie){
		this.numEpisodio = numEpisodio;
		this.numTemporada = numTemporada;
		this.sinopsis = sinopsis;
		this.fechaInicio= fechaInicio;
		this.titulo = titulo;
		this.idSerie = idSerie;
	}


	public Episodio(int idEpisodio) {		
		this.id = idEpisodio;
	}


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNumEpisodio() {
		return numEpisodio;
	}
	
	public void setNumEpisodio(Integer numEpisodio) {
		this.numEpisodio = numEpisodio;
	}
	
	public Integer getNumTemporada() {
		return numTemporada;
	}
	
	public void setNumTemporada(Integer numTemporada) {
		this.numTemporada = numTemporada;
	}
	
	public String getSinopsis() {
		return sinopsis;
	}
	
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getIdSerie() {
		return idSerie;
	}
	
	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}
	
	public boolean equals(Object obj) {
		return (obj.getClass() == this.getClass() && ((Episodio)obj).titulo.equalsIgnoreCase(titulo));
	}
	
	public String toString(){
		return "Episodio [id = " + id + 
					   ", numEpisodio = " + numEpisodio + 
					   ", numTemporada = " + numTemporada +
					   ", sinopsis = " + sinopsis +
					   ", fechaInicio = " + fechaInicio +
					   ", titulo = " + titulo +
					   ", idSerie = " + idSerie + "]";	
	}

}

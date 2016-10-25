package abd.pr1.tiposDeDatos;

public class Serie {
	
	private Integer id;
	private String titular;
	private String nombre;
	private String sinopsis;
	private Integer anioFin;

	public Serie(Integer id, String titular, String nombre, String sinopsis, Integer anioFin) {
		this.id = id;
		this.titular = titular;
		this.nombre = nombre;
		this.sinopsis = sinopsis;
		this.anioFin = anioFin;
	}

	public Serie(String titular, String nombre, String sinopsis, Integer anioFin) {
		this.titular = titular;
		this.nombre = nombre;
		this.sinopsis = sinopsis;
		this.anioFin = anioFin;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Integer getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(Integer anioFin) {
		this.anioFin = anioFin;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}

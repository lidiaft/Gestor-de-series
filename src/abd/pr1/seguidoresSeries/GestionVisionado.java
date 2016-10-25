package abd.pr1.seguidoresSeries;

import javax.sql.DataSource;

import abd.pr1.mappers.SerieMapper;

public class GestionVisionado {

	private DataSource ds;
	
	public GestionVisionado(DataSource ds) {
		this.ds = ds;
	}

	public void verEpisodio( String nombreSerie, String nombreEpisodio, String nickUsuario) {
		

		new SerieMapper(ds);
	}
	
}

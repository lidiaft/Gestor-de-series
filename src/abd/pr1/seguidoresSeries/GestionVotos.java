package abd.pr1.seguidoresSeries;

import javax.sql.DataSource;

import abd.pr1.mappers.SerieMapper;
import abd.pr1.mappers.VotoSerieMapper;
import abd.pr1.observables.Observable;
import abd.pr1.observables.VotosObserver;
import abd.pr1.tiposDeDatos.Serie;
import abd.pr1.tiposDeDatos.VotoSerie;

public class GestionVotos extends Observable<VotosObserver>{

	private DataSource ds;
	
	public GestionVotos(DataSource ds) {
		this.ds = ds;
	}

	public void votarSerie(int nota, String nombreSerie, String nickUsuario) {
		
		SerieMapper serieMapper = new SerieMapper(ds);
		Serie serie = serieMapper.buscarUnaSerie(nombreSerie);
		VotoSerieMapper votoSerieMapper = new VotoSerieMapper(ds);
		VotoSerie votoSerie= votoSerieMapper.buscarVoto(serie.getId(), nickUsuario);
		
		if(votoSerie != null){  //modificamos
			votoSerie.setNota(nota);
			votoSerieMapper.update(votoSerie);
		}
		else {		// creamos
			votoSerieMapper.insert(new VotoSerie(serie.getId(), nota, nickUsuario));
		}
	}
	
	
}

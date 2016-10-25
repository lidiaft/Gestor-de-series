package abd.pr1.seguidoresSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.mappers.EpisodioMapper;
import abd.pr1.mappers.SerieMapper;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.Observable;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Serie;

public class GestionEpisodios extends Observable<InfoEpisodioObserver>{
	
	private DataSource ds;

	public GestionEpisodios(DataSource ds) {
		this.ds = ds;
	}

	public void mostrarCapitulos(String nombreSerie) {
		SerieMapper serieMapper = new SerieMapper(ds);
		List<Episodio> episodios = new ArrayList<>();
		if(serieMapper.buscarUnaSerie(nombreSerie) != null) {
			EpisodioMapper episodioMapper = new EpisodioMapper(ds);
			episodios = episodioMapper.buscarEpisodios(nombreSerie);
		}
		
		mostrarDatosEpisodios(episodios);
	}
	
	private void mostrarDatosEpisodios(List<Episodio> episodios) {
		Iterator<InfoEpisodioObserver> it = super.iterator();
		while(it.hasNext()){
			it.next().mostrarCapitulosSeries(episodios);
		}	
	}

	public void datosEpisodio(String nombreEpisodio) {
		EpisodioMapper episodioMapper = new EpisodioMapper(ds);
		Episodio episodio = episodioMapper.buscarEpisodio(nombreEpisodio);
		
		String titulo = episodio.getTitulo();
		String fechaInicio = episodio.getFechaInicio().toString();
		String sinopsis = episodio.getSinopsis();
		String numCap = episodio.getNumEpisodio().toString();
		String numTemp = episodio.getNumTemporada().toString();
		
		avisarDatosEpisodio(titulo, fechaInicio, sinopsis, numCap, numTemp);
		
	}

	private void avisarDatosEpisodio(String titulo, String fechaInicio, String sinopsis, String numCap, String numTemp) {
		Iterator<InfoEpisodioObserver> it = super.iterator();
		while(it.hasNext())
		{
			it.next().datosEpisodio(titulo, fechaInicio, sinopsis, numCap, numTemp);
		}	
	}

	public void modificarEpisodio(String nombreAntiguo, String titulo ,String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {

		EpisodioMapper episodioMapper = new EpisodioMapper(ds);
		
		SerieMapper serieMapper = new SerieMapper(ds);
		Serie serie = serieMapper.buscarUnaSerie(nombreSerie);
		int idSerie = serie.getId();
		
		int idEpisodio = episodioMapper.buscarEpisodio(nombreAntiguo).getId();
		episodioMapper.update(new Episodio(idEpisodio, numCap , numTemp, sinopsis, anioIni ,titulo, idSerie));
					
		mostrarCapitulos(nombreSerie);
	}

	public boolean nuevoEpisodio(String titulo, String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {
		SerieMapper serieMapper = new SerieMapper(ds);
		
		List<Episodio> episodios = new ArrayList<>();
		EpisodioMapper episodioMapper = new EpisodioMapper(ds);
		episodios = episodioMapper.buscarEpisodios(nombreSerie);
		Episodio tmpEpisodio = null;
		String error = "";
		Iterator<Episodio> it = episodios.iterator();
		while(it.hasNext() && error.isEmpty()) {
			tmpEpisodio = it.next();
			if(tmpEpisodio.getTitulo().equalsIgnoreCase(titulo))
				error = "Ya existe un episodio con ese nombre.";
			else if(tmpEpisodio.getNumEpisodio() == numCap && tmpEpisodio.getNumTemporada() == numTemp)
				error = "Ya existe el número de episodio " +  numCap +" en la temporada " +  numTemp +".";
		}
		
		if(error == null || error.isEmpty()){
			Serie serie = serieMapper.buscarUnaSerie(nombreSerie);
			episodioMapper.insert(new Episodio(numCap, numTemp, sinopsis, anioIni, titulo, serie.getId()));
			mostrarCapitulos(nombreSerie);
		}
		else {
			mostrarError(error);
		}
		
		return error.isEmpty();
	}

	private void mostrarError(String error) {
		Iterator<InfoEpisodioObserver> it = super.iterator();
		while(it.hasNext()){
			it.next().errorProducido(error);
		}	
	}

	public void eliminarEpisodio(String nombreEpisodio, String nombreSerie) {
		EpisodioMapper episodioMapper = new EpisodioMapper(ds);
		int idEpisodio = episodioMapper.buscarEpisodio(nombreEpisodio).getId();
		episodioMapper.delete(new Episodio(idEpisodio));
		mostrarCapitulos(nombreSerie);
	}
}

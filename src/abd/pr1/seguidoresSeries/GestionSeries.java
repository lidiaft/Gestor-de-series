package abd.pr1.seguidoresSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.mappers.EpisodioMapper;
import abd.pr1.mappers.GeneroMapper;
import abd.pr1.mappers.SerieMapper;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.observables.Observable;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Generos;
import abd.pr1.tiposDeDatos.Serie;

public class GestionSeries extends Observable<InfoSerieObserver>{

	private DataSource ds;
	
	public GestionSeries(DataSource ds) {
		this.ds = ds;
	}

		// Series
		public void nuevaSerie(String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
			GeneroMapper generoMapper 	= new GeneroMapper(ds);
			SerieMapper serieMapper	= new SerieMapper(ds);
			// controlar que no exista ya la serie
			if(serieMapper.buscarUnaSerie(titulo) == null) {
				serieMapper.insert(new Serie( resumen, titulo, sinopsis, anioFin));
				int idSerie = serieMapper.buscarUnaSerie(titulo).getId();
				
				String[] genero = crearGenero( boxes);
				generoMapper.insert(new Generos(idSerie, genero[0], genero[1], genero[2], genero[3]));
			}
			else {
				
			}
			
		}
		
		private String[] crearGenero(boolean[] boxes) {
			String[] arryGeneros = new String[] {"Comedia", "Terror", "Thriller", "Drama"}; 
			for(int i = 0; i < arryGeneros.length; i++){
				if(!boxes[i])
					arryGeneros[i] = "";
			}
																			
			return arryGeneros;
		}

		
		public void modificarSerie(String nombreAntiguo, String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
			GeneroMapper generoMapper 	= new GeneroMapper(ds);
			SerieMapper serieMapper	= new SerieMapper(ds);
			
			int idSerie = serieMapper.buscarUnaSerie(nombreAntiguo).getId();
			serieMapper.update(new Serie(idSerie, resumen, titulo, sinopsis, anioFin));
						
			String[] genero = crearGenero( boxes);
			generoMapper.update(new Generos(idSerie, genero[0], genero[1], genero[2], genero[3]));
			
			mostrarCatalagoSeries(new ArrayList<Serie>());
		}
		
		public void eliminarSerie(){	
		}

		public void verSerie(){		
		}
		
		public List<Serie> buscarSerie(String tituloSerie){
			SerieMapper serieMapper = new SerieMapper(ds);
			List<Serie> series = serieMapper.buscarSeries(tituloSerie);
			mostrarCatalagoSeries(series);
			
			return series;	
		}
		
		private void mostrarCatalagoSeries(List<Serie> series) {
			Iterator<InfoSerieObserver> it = super.iterator();
			while(it.hasNext())
			{
				it.next().mostrarTodasLasSeries(series);
			}	
		}
		
		public void datosSerie(String nombreSerie){
			SerieMapper serieMapper = new SerieMapper(ds);
			Serie serie = serieMapper.buscarUnaSerie(nombreSerie);
			
			GeneroMapper generoMapper = new GeneroMapper(ds);
			Generos generos = generoMapper.findById(serie.getId());
			
			String nombre = serie.getNombre();
			String fechaInicio = fechaEstreno(nombre);
			String fechaFin = serie.getAnioFin().toString();
			String titular = serie.getTitular();
			String stringGenero = generos.toString();
			String sinopsis = serie.getSinopsis();
			
			avisarDatosSerie(nombre, fechaInicio, fechaFin, titular, stringGenero, sinopsis);
			
		}
		
		
		private String fechaEstreno(String nombreSerie) {

			EpisodioMapper episodioMapper = new EpisodioMapper(ds);
			Episodio episodio = episodioMapper.buscarPrimerEpisodio(nombreSerie);
			Date fechaIni = null;
			if(episodio != null){
				 fechaIni = episodio.getFechaInicio();
			}
			return fechaIni.toString();
		}

		private void avisarDatosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis) {
			Iterator<InfoSerieObserver> it = super.iterator();
			while(it.hasNext()){
				it.next().datosSerie(nombre, fechaIni, fechaFin, titular, generos, sinopsis);
			}	
		}

		
	
}

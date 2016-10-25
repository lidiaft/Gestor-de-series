package abd.pr1.seguidoresSeries;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.ImageIcon;

import abd.pr1.Utilidades;
import abd.pr1.mappers.ActorMapper;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.Observable;
import abd.pr1.tiposDeDatos.Actor;

public class GestionActores extends Observable<InfoActoresObserver>{

	private DataSource ds;
	
	public GestionActores(DataSource ds) {
		this.ds = ds;
	}
	
	
	public List<Actor> buscarActor(String nombreActor){
		ActorMapper actorMapper = new ActorMapper(ds);
		List<Actor> actores = actorMapper.buscarActores(nombreActor);
		mostrarCatalogoActores(actores);
		
		return actores;	
	}


	private void mostrarCatalogoActores(List<Actor> actores) {
		Iterator<InfoActoresObserver> it = super.iterator();
		while(it.hasNext())
		{
			it.next().mostrarTodosLosActores(actores);
		}	
	}


	public void datosActores(String nombreActor) {
		ActorMapper actorMapper = new ActorMapper(ds);
		Actor actor = actorMapper.buscarUnActor(nombreActor);
	
		String nif = actor.getNif();
		String nombre = actor.getNombre();
		String fechaNacimiento = actor.getFechaNac().toString();
		Blob blobFoto = actor.getFoto();
		ImageIcon foto = Utilidades.blobToImage(blobFoto);
		
		avisarDatosActores(nif, nombre, fechaNacimiento, foto);
	}


	private void avisarDatosActores(String nif, String nombre, String fechaNacimiento, ImageIcon foto) {
		Iterator<InfoActoresObserver> it = super.iterator();
		while(it.hasNext())
		{
			it.next().datosActor(nif, nombre, fechaNacimiento, foto);
		}
	}


	public boolean nuevoActor(String nif, String nombre, Date fechaNac, ImageIcon foto) {
		List<Actor> actores = new ArrayList<>();
		ActorMapper actorMapper = new ActorMapper(ds);
		actores = actorMapper.buscarActores(nombre);
		Actor tmpActor = null;
		String error = "";
		Iterator<Actor> it = actores.iterator();
		while(it.hasNext() && error.isEmpty()) {
			tmpActor = it.next();
			if(tmpActor.getNombre().equalsIgnoreCase(nombre))
				error = "Ya existe un actor con ese nombre.";
			else if(tmpActor.getNif().equalsIgnoreCase(nif));
				error = "Ya existe un actor con ese NIF";
		}
		
		if(error == null || error.isEmpty()){
			Blob image = Utilidades.imgToBlob(foto);
			actorMapper.insert(new Actor(nif, nombre, fechaNac, image));
			mostrarCatalogoActores(actores);
		}
		else {
			mostrarError(error);
		}
		
		return error.isEmpty();
	}
	
	
	private void mostrarError(String error) {
		Iterator<InfoActoresObserver> it = super.iterator();
		while(it.hasNext()){
			it.next().errorProducido(error);
		}	
	}

	
	/*public void mostrarActores(String nombreActor) {
		SerieMapper serieMapper = new SerieMapper(ds);
		List<Episodio> episodios = new ArrayList<>();
		if(serieMapper.buscarUnaSerie(nombreSerie) != null) {
			EpisodioMapper episodioMapper = new EpisodioMapper(ds);
			episodios = episodioMapper.buscarEpisodios(nombreSerie);
		}
		
		mostrarDatosEpisodios(episodios);
	}*/
	
	
	public void dameTodosLosActores(){
		ActorMapper actorMapper = new ActorMapper(ds);
		List<Actor> actores = actorMapper.buscarActores("");
		mostrarCatalogoActores(actores);
	}
	
}

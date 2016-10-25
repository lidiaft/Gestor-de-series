package abd.pr1.seguidoresSeries;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.mappers.ActorMapper;
import abd.pr1.mappers.ActuacionMapper;
import abd.pr1.mappers.EpisodioMapper;
import abd.pr1.mappers.PersonajeMapper;
import abd.pr1.mappers.SerieMapper;
import abd.pr1.observables.ActuaObserver;
import abd.pr1.observables.Observable;
import abd.pr1.tiposDeDatos.Actor;
import abd.pr1.tiposDeDatos.Actuacion;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Personaje;
import abd.pr1.tiposDeDatos.Serie;

public class GestionActuar extends Observable<ActuaObserver>{

	private DataSource ds;
	
	public GestionActuar(DataSource ds) {
		this.ds = ds;
	}
	
	public void datosActua(String nombreActor) {
		ActorMapper actorMapper = new ActorMapper(ds);
		Actor actor = actorMapper.buscarUnActor(nombreActor);
		ActuacionMapper actuacionMapper = new ActuacionMapper(ds);
		
		List<Actuacion> actuaciones = actuacionMapper.buscarActuaciones(actor.getNif());
		if(!actuaciones.isEmpty()) {
			
			EpisodioMapper episodioMapper = new EpisodioMapper(ds);
			Episodio episodio;
			
			PersonajeMapper personajeMapper = new PersonajeMapper(ds);
			Personaje personaje;

			SerieMapper serieMapper = new SerieMapper(ds);
			Serie serie;
			
			Actuacion actuacion;
			
			Iterator<Actuacion> it = actuaciones.iterator();
			while(it.hasNext()) {
				actuacion = it.next();
				episodio = episodioMapper.findById(actuacion.getIdEpisodio());
				serie = serieMapper.findById( episodio.getIdSerie());
				personaje = personajeMapper.findById(actuacion.getIdPersonaje());
				avisarDatosActua(serie.getNombre(), episodio.getTitulo(), personaje.getNombre());
			}
			
		}
		
	}

	private void avisarDatosActua(String nombreSerie, String nombreEpisodio, String personaje) {
		Iterator<ActuaObserver> it = super.iterator();
		while(it.hasNext()) {
			it.next().datosActua(nombreSerie, nombreEpisodio, personaje);
		}
	}

}

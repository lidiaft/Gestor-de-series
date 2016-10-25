package abd.pr1.seguidoresSeries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import abd.pr1.mappers.PersonajeMapper;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.observables.Observable;
import abd.pr1.tiposDeDatos.Personaje;

public class GestionPersonajes extends Observable<InfoPersonajeObserver>{
	private DataSource ds;

	public GestionPersonajes(DataSource ds) {
		this.ds = ds;
	}

	public boolean nuevoPersonaje(String nombre, String descripcion) {
		List<Personaje> personajes = new ArrayList<>();
	    PersonajeMapper personajeMapper = new PersonajeMapper(ds);
		personajes = personajeMapper.buscarPersonajes(nombre);
		Personaje tmpPersonaje = null;
		String error = "";
		Iterator<Personaje> it = personajes.iterator();
		while(it.hasNext() && error.isEmpty()) {
			tmpPersonaje = it.next();
			if(tmpPersonaje.getNombre().equalsIgnoreCase(nombre)) {
				error = "Ya existe un Personaje con ese nombre.";
			}
		}
		
		if(error == null || error.isEmpty()){
			personajeMapper.insert(new Personaje( nombre, descripcion));
			avisarDatosPersonajes(nombre, descripcion);
		}
		else mostrarError(error);

		return error.isEmpty();
	}

	private void mostrarError(String error) {
		Iterator<InfoPersonajeObserver> it = super.iterator();
		while(it.hasNext()){
			it.next().errorProducido(error);
		}	
	}

	public List<Personaje> buscarPersonaje(String nombrePersonaje) {
		PersonajeMapper personajeMapper = new PersonajeMapper(ds);
		List<Personaje> personajes = personajeMapper.buscarPersonajes(nombrePersonaje);
		mostrarCatalogoPersonajes(personajes);
		
		return personajes;	
	}

	private void mostrarCatalogoPersonajes(List<Personaje> personajes) {
		Iterator<InfoPersonajeObserver> it = super.iterator();
		while(it.hasNext())
		{
			it.next().mostrarTodosLosPersonajes(personajes);
		}	
	}
	
	public void datosPersonajes(String nombrePersonaje) {
		PersonajeMapper personajeMapper = new PersonajeMapper(ds);
		Personaje personaje = personajeMapper.buscarUnPersonaje(nombrePersonaje);
	
		String nombre = personaje.getNombre();
		String descripcion = personaje.getDescripcion();
		
		avisarDatosPersonajes(nombre, descripcion);
	}

	private void avisarDatosPersonajes(String nombre, String descripcion) {
		Iterator<InfoPersonajeObserver> it = super.iterator();
		while(it.hasNext()){
			it.next().datosPersonaje(nombre, descripcion);
		}	
	}
	
	public void dameTodosLosPersonajes(String nombreEpisodio){
		PersonajeMapper personajeMapper = new PersonajeMapper(ds);
		List<Personaje> todosPersonajes = personajeMapper.buscarPersonajes("");
		
		//Episodio episodio = new EpisodioMapper(ds).buscarEpisodio(nombreEpisodio);
		//Actuacion actua = new ActuacionMapper(ds).findById(episodio.getId());
		
		
		//List<Personaje> personajesLibres = new ArrayList<>();		
		
		//Iterator<Personaje> it = todosPersonajes.iterator();

		mostrarCatalogoPersonajes(todosPersonajes);
	}
}

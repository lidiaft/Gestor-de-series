package abd.pr1.observables;

import java.util.List;

import abd.pr1.tiposDeDatos.Personaje;

public interface InfoPersonajeObserver {
	
	public void mostrarTodosLosPersonajes(List<Personaje> personajes);

	public void datosPersonaje(String nombre, String descripcion);
	
	public void errorProducido(String error);
}

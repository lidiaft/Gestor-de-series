package abd.pr1.observables;

import java.util.List;

import javax.swing.ImageIcon;

import abd.pr1.tiposDeDatos.Actor;

public interface InfoActoresObserver {
	public void mostrarTodosLosActores(List<Actor> actores);

	public void datosActor(String nif, String nombre, String fechaNacimiento, ImageIcon foto);
	
	public void errorProducido(String error);
}

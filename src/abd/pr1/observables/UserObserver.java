package abd.pr1.observables;

import java.util.List;

import javax.swing.ImageIcon;

import abd.pr1.tiposDeDatos.Serie;

public interface UserObserver {
	
	public void actualizarDatosUsuario(String nombre,long edad, ImageIcon imagen);

	public void aniadidaSerieSeguida(List<Serie> seriesSeguidas);
	
	

}

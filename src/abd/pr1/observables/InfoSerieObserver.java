package abd.pr1.observables;

import java.util.List;

import abd.pr1.tiposDeDatos.Serie;

public interface InfoSerieObserver {
	
	public void datosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis);

	public void mostrarTodasLasSeries(List<Serie> series);

	public void errorProducido(String error);
	
}

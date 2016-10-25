package abd.pr1.observables;

import java.util.List;

import abd.pr1.tiposDeDatos.Episodio;


public interface InfoEpisodioObserver {
	
	public void mostrarCapitulosSeries(List<Episodio> episodios);

	public void datosEpisodio(String titulo, String fechaInicio, String sinopsis, String numCap, String numTemp);

	public void errorProducido(String error);

}

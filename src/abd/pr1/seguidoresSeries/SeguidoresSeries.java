package abd.pr1.seguidoresSeries;

import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.ImageIcon;

import abd.pr1.observables.ActuaObserver;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.observables.UserObserver;
import abd.pr1.tiposDeDatos.Actor;
import abd.pr1.tiposDeDatos.Personaje;
import abd.pr1.tiposDeDatos.Serie;
import abd.pr1.tiposDeDatos.Usuario;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SeguidoresSeries {
	private GestionUsuarios gestionUsuarios;
	private GestionSeries gestionSeries;
	private GestionEpisodios gestionEpisodio;
	private GestionActores gestionActor;
	private GestionPersonajes gestionPersonaje;
	private GestionVotos gestionVotos;
	private GestionActuar gestionActua;
	private GestionVisionado gestionVisionados;
	
	private DataSource ds;
	
	
	
	public SeguidoresSeries(String usuario, String password) throws PropertyVetoException{
		initDB(usuario, password);
		gestionUsuarios  = new GestionUsuarios(ds);
		gestionSeries 	 = new GestionSeries(ds);
		gestionEpisodio  = new GestionEpisodios(ds);
		gestionActor 	 = new GestionActores(ds);
		gestionPersonaje = new GestionPersonajes(ds);
		gestionVotos 	 = new GestionVotos(ds);
		gestionActua     = new GestionActuar(ds);
		
	}

	private void initDB(String usuario, String password) throws PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.gjt.mm.mysql.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost/pr1");
		cpds.setUser(usuario);
		cpds.setPassword(password);
		cpds.setAcquireRetryAttempts(1);
		cpds.setAcquireRetryDelay(1);
		cpds.setBreakAfterAcquireFailure(true);
		
		this.ds = cpds;
	}
	
	
	public boolean nuevoUsuario(String nick, String password){
		return gestionUsuarios.nuevoUsuario(nick, password);
	}
	
	
	
	public Usuario modificarUsuario(String pass, Date fechaNac, ImageIcon foto){
		return gestionUsuarios.modificarUsuario(pass, fechaNac, foto);
	}
	

	public void eliminarUsuario(){
		gestionUsuarios.eliminarUsuario();
	}
	
	public Usuario buscarUsuario(String nick) {
		return gestionUsuarios.buscarUsuario(nick);
	}
	
	public boolean login(String nick, String password) {
		return gestionUsuarios.login(nick, password);
		
	}
	
	public void datosUsuariosIniciales(){
		gestionUsuarios.datosUsuariosIniciales();
	}
	
	// Series
	public void nuevaSerie(String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
		gestionSeries.nuevaSerie(titulo, resumen, boxes, sinopsis, anioFin);
	}

	
	public void modificarSerie(String nombreAntiguo, String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
		gestionSeries.modificarSerie(nombreAntiguo, titulo, resumen, boxes, sinopsis, anioFin);
	}
	
	public void eliminarSerie(){
		gestionSeries.eliminarSerie();
	}

	public void verSerie(){
		gestionSeries.verSerie();
	}
	
	public List<Serie> buscarSerie(String tituloSerie){
		return gestionSeries.buscarSerie(tituloSerie);
	}

	public void addUserObserver(UserObserver userObserver) {
		gestionUsuarios.addObserver(userObserver);
	}
	
	public void addInfoSerieObserver(InfoSerieObserver infoSerieObserver){
		gestionSeries.addObserver(infoSerieObserver);
	}
	

	public void addInfoEpisodioObserver(InfoEpisodioObserver infoEpisodioObserver) {
		gestionEpisodio.addObserver(infoEpisodioObserver);
	}
	
	public void addInfoActoresObserver(InfoActoresObserver infoActoresObserver) {
		gestionActor.addObserver(infoActoresObserver);
	}
	
	public void addInfoPersonajesObserver(InfoPersonajeObserver infoPersonajeObserver) {
		gestionPersonaje.addObserver(infoPersonajeObserver);
	}
	
	public void addActuaObserver(ActuaObserver actuaObserver) {
		gestionActua.addObserver(actuaObserver);
	}
	
	public void datosSeries(String nombreSerie) {
		gestionSeries.datosSerie(nombreSerie);
	}

	public boolean seguirSerie(String nombreSerie) {
		return gestionUsuarios.seguirSerie(nombreSerie);
	}

	public void mostrarCapitulos(String nombreSerie) {
		gestionEpisodio.mostrarCapitulos(nombreSerie);
	}

	public void datosEpisodio(String nombreEpisodio) {
		gestionEpisodio.datosEpisodio(nombreEpisodio);
		
	}

	public void modificarEpisodio(String nombreAntiguo,String titulo ,String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {
		gestionEpisodio.modificarEpisodio( nombreAntiguo,titulo ,sinopsis, anioIni, numCap, numTemp, nombreSerie);
	}

	public boolean nuevoEpisodio(String titulo, String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {
		return gestionEpisodio.nuevoEpisodio(titulo ,sinopsis, anioIni, numCap, numTemp, nombreSerie);
	}

	public void eliminarEpisodio(String nombreEpisodio, String nombreSerie) {
		gestionEpisodio.eliminarEpisodio(nombreEpisodio, nombreSerie);
	}
	
	public  List<Actor> buscarActor (String nombreActor) {
		return gestionActor.buscarActor(nombreActor);
	}

	public void datosActores(String nombreActor) {
		gestionActor.datosActores(nombreActor);
	}

	public boolean nuevoActor(String nif, String nombre, Date fechaNac, ImageIcon foto) {
		return gestionActor.nuevoActor(nif, nombre,fechaNac,foto);
	}

	public boolean nuevoPersonaje(String nombre, String descripcion) {
		return gestionPersonaje.nuevoPersonaje(nombre, descripcion);
	}

	public List<Personaje> buscarPersonaje(String nombrePersonaje) {
		return gestionPersonaje.buscarPersonaje(nombrePersonaje);
		
	}
	public void votarSerie(int value, String nombreSerie, String nickUsuario) {
		gestionVotos.votarSerie(value, nombreSerie, nickUsuario);
	}

	public void datosPersonaje(String nombrePersonaje) {
		gestionPersonaje.datosPersonajes(nombrePersonaje);
	}

	public void datosActua(String nifActor) {
		gestionActua.datosActua(nifActor);
	}

	public void dameTodosPersonajes(String nombreEpisodio) {
		gestionPersonaje.dameTodosLosPersonajes(nombreEpisodio);
	}

	public void dameTodosActores() {
		gestionActor.dameTodosLosActores();
	}

	public Usuario obtenerUsuario(String nickUsuario) {
		return gestionUsuarios.buscarUsuario(nickUsuario);
		
	}

	public void verEpisodio( String nombreSerie, String nombreEpisodio, String nickUsuario) {
		gestionVisionados.verEpisodio(nombreSerie, nombreEpisodio, nickUsuario);
	}

	

	
}

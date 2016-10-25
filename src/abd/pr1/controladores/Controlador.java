package abd.pr1.controladores;

import java.util.Date;

import javax.swing.ImageIcon;

import abd.pr1.observables.ActuaObserver;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.observables.UserObserver;
import abd.pr1.seguidoresSeries.SeguidoresSeries;
import abd.pr1.tiposDeDatos.Usuario;

public class Controlador {
	
	private SeguidoresSeries seguidoresSeries;
	
	public Controlador(SeguidoresSeries seguidoresSeries){
		this.seguidoresSeries = seguidoresSeries;
	}
	
	public void registerUserObserver(UserObserver userObserver){
		seguidoresSeries.addUserObserver(userObserver);
	}
	
	public void registerInfoSerieObserver(InfoSerieObserver infoSerieObserver){
		seguidoresSeries.addInfoSerieObserver(infoSerieObserver);
	}
	
	public void registerInfoEpisodioObserver(InfoEpisodioObserver infoEpisodioObserver) {
		seguidoresSeries.addInfoEpisodioObserver(infoEpisodioObserver);
	}	
	
	public void registerInfoActorObserver(InfoActoresObserver infoActoresObserver) {
		seguidoresSeries.addInfoActoresObserver(infoActoresObserver);
	}	
	
	public void registerInfoPersonajeObserver(InfoPersonajeObserver infoPersonajeObserver) {
		seguidoresSeries.addInfoPersonajesObserver(infoPersonajeObserver);
	}
	
	public void registerActuaObserver(ActuaObserver actuaObserver) {
		seguidoresSeries.addActuaObserver(actuaObserver);
	}
	
	public boolean login(String nick, String password) {
		return seguidoresSeries.login(nick, password);
	}
	
	//Usuario
	public void nuevoUsuario(String nick, String password){
		seguidoresSeries.nuevoUsuario(nick, password);
	}
	
	public boolean buscarUsuario(String nick){
		return (null != seguidoresSeries.buscarUsuario(nick));
	}
	
	public void modificarUsuario(String password, Date date, ImageIcon foto){
		seguidoresSeries.modificarUsuario(password, date, foto);
	}
	
	public void eliminarUsuario(){
		seguidoresSeries.eliminarUsuario();
	}

	public void datosUsuariosIniciales() {
		seguidoresSeries.datosUsuariosIniciales();
	}
	
	// Serie

	public void buscarSerie(String nombre){
		seguidoresSeries.buscarSerie(nombre);
	}

	public void datosSeries(String nombreSerie) {
		seguidoresSeries.datosSeries(nombreSerie);
	}
	
	public boolean verEpisodio(String nombreSerie) {
		return seguidoresSeries.seguirSerie(nombreSerie);
	}
	
	public void nuevaSerie(String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
		seguidoresSeries.nuevaSerie(titulo, resumen, boxes, sinopsis, anioFin);
	}
	
	public void modificarSerie(String nombreAntiguo, String titulo, String resumen, boolean[] boxes, String sinopsis, int anioFin){
		seguidoresSeries.modificarSerie(nombreAntiguo, titulo, resumen, boxes, sinopsis, anioFin);
	}

	
	//Episodio
	
	public void mostrarCapitulos(String nombreSerie) {
		seguidoresSeries.mostrarCapitulos(nombreSerie);
	}

	public boolean nuevoEpisodio(String titulo, String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {
		return seguidoresSeries.nuevoEpisodio(titulo, sinopsis, anioIni, numCap, numTemp, nombreSerie);
	}

	public void modificarEpisodio(String nombreAntiguo, String titulo, String sinopsis, Date anioIni, int numCap, int numTemp, String nombreSerie) {
		seguidoresSeries.modificarEpisodio(nombreAntiguo, titulo, sinopsis, anioIni, numCap, numTemp, nombreSerie);
	}
	
	public void datosEpisodio (String nombreEpisodio) {
		seguidoresSeries.datosEpisodio(nombreEpisodio);
	}

	public void eliminarEpisodio(String nombreEpisodio, String nombreSerie) {
		seguidoresSeries.eliminarEpisodio(nombreEpisodio, nombreSerie);
	}
	
	// Actor
	
	public void buscarActores(String nombreActor) {
		seguidoresSeries.buscarActor(nombreActor);
	}
	
	public void datosActores(String nombreActor) {
		seguidoresSeries.datosActores(nombreActor);
	}

	
	public boolean nuevoActor(String nif, String nombre, Date fechaNac, ImageIcon foto) {
		return seguidoresSeries.nuevoActor(nif,nombre, fechaNac, foto);
	}

	//Personaje
	
	public void buscarPersonajes(String nombreActor) {
		seguidoresSeries.buscarPersonaje(nombreActor);
	}
	
	public void datosPersonajes(String nombrePersonaje) {
		seguidoresSeries.datosPersonaje(nombrePersonaje);
	}
	
	public boolean nuevoPersonaje(String nombre, String descripcion) {
		return seguidoresSeries.nuevoPersonaje(nombre, descripcion);
	}

	
	//Voto
	
	public void votarSerie(int value, String nombreSerie, String nickUsuario) {
		seguidoresSeries.votarSerie(value, nombreSerie, nickUsuario);
	}
	
	
	//Actua
	
	public void datosActua(String nifActor) {
		seguidoresSeries.datosActua(nifActor);
	}
	/*
	
	
	 
	public void nuevoActor(){
		seguidoresSeries.nuevoActor();
	}
	
	public void modificarActor(){
		seguidoresSeries.modificarActor();
	}
	
	public void eliminarActor(){
		seguidoresSeries.eliminarActor();
	}

	
	// Personaje
	public void nuevoPersonaje(){
		seguidoresSeries.nuevoPersonaje();
	}
	
	public void modificarPersonaje(){
		seguidoresSeries.modificarPersonaje();
	}
	
	public void eliminarPersonaje(){
		
	}
	
	
	// Comentario
	public void escribirComentario(){
		
	}
*/

	public void dameTodosPersonajes(String nombreEpisodio) {
		seguidoresSeries.dameTodosPersonajes(nombreEpisodio);
	}

	public void dameTodosActores() {
		seguidoresSeries.dameTodosActores();
	}

	public Usuario obtenerUsuario(String nickUsuario) {
		return seguidoresSeries.obtenerUsuario(nickUsuario);
	}

	public void seguirSerie(String nombreSerie, String nombreEpisodio, String nickUsuario) {
		seguidoresSeries.verEpisodio(nombreSerie, nombreEpisodio, nickUsuario);
	}

	
	
}

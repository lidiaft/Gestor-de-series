package abd.pr1.seguidoresSeries;

import java.sql.Blob;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.ImageIcon;

import abd.pr1.Utilidades;
import abd.pr1.mappers.SeguidoMapper;
import abd.pr1.mappers.SerieMapper;
import abd.pr1.mappers.UsuarioMapper;
import abd.pr1.observables.Observable;
import abd.pr1.observables.UserObserver;
import abd.pr1.tiposDeDatos.Seguido;
import abd.pr1.tiposDeDatos.SeguidoClave;
import abd.pr1.tiposDeDatos.Serie;
import abd.pr1.tiposDeDatos.Usuario;

public class GestionUsuarios extends Observable<UserObserver>{

	private DataSource ds;
	private String usuario;
	private String password;
	
	public GestionUsuarios(DataSource ds) {
		this.ds = ds;
	}

		// Usuarios
		public boolean nuevoUsuario(String nick, String password){
			boolean encontrado = (buscarUsuario(nick) != null);
			
			if(!encontrado){
				UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
				ImageIcon defaultImage = new ImageIcon("src/abd/pr1/swing/images/images.jpg");
				Blob blobImage = Utilidades.imgToBlob(defaultImage);
				
				Date defaultDate = new Date();

				Usuario user = new Usuario(nick, password, blobImage, defaultDate);
				usuarioMapper.insert(user);
				this.usuario = nick;
				this.actualizarUsuario(0, defaultImage);
				this.password = password;
				return true;
			}
			else
				return false;
		}
		
		
		
		public Usuario modificarUsuario(String pass, Date fechaNac, ImageIcon foto){
			if(!pass.isEmpty() && pass != password)
				password = pass;
			
			UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
			Usuario user = new Usuario(this.usuario, password, Utilidades.imgToBlob(foto), fechaNac);
			usuarioMapper.update(user);
		
			long edad = Utilidades.calcularEdad(fechaNac);
			actualizarUsuario(edad, foto);
			
			return user;
		}
		

		private void actualizarUsuario(long edad, ImageIcon foto) {
			Iterator<UserObserver> it = super.iterator();
			while(it.hasNext())
			{
				it.next().actualizarDatosUsuario(usuario, edad, foto);
			}	
		}

		public void eliminarUsuario(){
			
		}
		
		public Usuario buscarUsuario(String nick) {
			UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
			Usuario user = usuarioMapper.findById(nick);
			
			return user;
		}
		
		public boolean login(String nick, String password) {
			UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
			Usuario user = usuarioMapper.findById(nick);
			
			if(user != null && user.getPassword().equals(password)){
				this.usuario = nick;
				this.password = password;
				
				return true;
			}
			return false;
		}
		
		
		
		public void datosUsuariosIniciales(){
			Usuario user = buscarUsuario(usuario);
			Blob blobFoto = user.getFoto();
			ImageIcon foto = Utilidades.blobToImage(blobFoto);
			long edad = Utilidades.calcularEdad(user.getFechaNac());
			this.actualizarUsuario(edad, foto );
			actualizarSeriesSeguidas();
		}


		public boolean seguirSerie(String nombreSerie) {
			SerieMapper serieMapper = new SerieMapper(ds);
			Serie serie = serieMapper.buscarUnaSerie(nombreSerie);
			int idSerie = serie.getId();
			Seguido seguido = new Seguido(idSerie, usuario);
			
			SeguidoMapper seguidoMapper = new SeguidoMapper(ds);
			
			if(seguidoMapper.findById(new SeguidoClave(idSerie, usuario)) == null){
				seguidoMapper.insert(seguido);			
				actualizarSeriesSeguidas();
				return true;
			}
			return false;
		}



		private void actualizarSeriesSeguidas() {
			SeguidoMapper seguidoMapper = new SeguidoMapper(ds);
			List<Serie> seriesSeguidas = seguidoMapper.buscarSeriesSeguidas(usuario);
			avisarSeriesSeguidas(seriesSeguidas);
		}

		private void avisarSeriesSeguidas(List<Serie> seriesSeguidas) {
			Iterator<UserObserver> it = super.iterator();
			while(it.hasNext()){
				it.next().aniadidaSerieSeguida(seriesSeguidas);
			}	
		}
}

package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.tiposDeDatos.Actor;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Personaje;
import abd.pr1.tiposDeDatos.Serie;


@SuppressWarnings({"serial", "unused"})
public class AdminFrame extends JFrame implements InfoSerieObserver, InfoEpisodioObserver, InfoActoresObserver, InfoPersonajeObserver{

	private Controlador controlador;
	
	private Container adminWindow;
	private NewSerieFrame newSerie;
	private InfoSerieFrame modSerie;
	private InfoEpisodioFrame episodioFrame;
	private NewActorFrame newActor;
	private NewPersonajeFrame newPersonaje;
	private InfoActua infoActua;
	
	private JTabbedPane pestaniasPanel;				// 1. Panel de pestañas
	
	private JPanel nameSearchSeries;				// 1.1 Panel "Series"
												
	private JButton searchButton;					// 1.1.1 Panel superior "Series"
	private JTextField searchField;
	
	private DefaultListModel<String> listModel; 	// 1.1.2 Panel central "Series"
	private JList<String> listSeries;
	
	private JButton infoButton;						// 1.1.3 Panel inferior "Series"
	private JButton newSerieButton;
	private JButton infoEpisodios;
	
	private JPanel actors;							// 1.2 Panel "Actores"
	
	private JButton searchActors;
	private JTextField actorsField;
	
	private DefaultListModel<String> listModelActor; 	// 1.1.2 Panel central "Actores"
	private JList<String> listActors;
	
	private JButton infoButtonActor;						// 1.1.3 Panel inferior "Actores"
	private JButton newActorButton;
	private JButton infoActorSeries;
	
	private JPanel characters;						// 1.3 Panel "Personajes"

	private JButton searchPersonajes;
	private JTextField personajesField;
	
	private DefaultListModel<String> listModelPersonaje; 	// 1.1.2 Panel central "Actores"
	private JList<String> listPersonajes;
	
	private JButton infoButtonPersonaje;						// 1.1.3 Panel inferior "Actores"
	private JButton newPersonajeButton;
//private JButton infoActorSeries;
	
	
	public AdminFrame(Controlador controlador){
		super("Administración");
		this.controlador = controlador;
		controlador.registerInfoSerieObserver(this);
		controlador.registerInfoEpisodioObserver(this);
		controlador.registerInfoActorObserver(this);
		controlador.registerInfoPersonajeObserver(this);
		initAdminFrame();
		startAdmin();
	}

	private void initAdminFrame() {
		this.setPreferredSize(new Dimension(640, 600));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		adminWindow = this.getContentPane();
		initPestaniasPanel();
		adminWindow.add(pestaniasPanel);
		
		fixedButton();
	}
	
	
	
	
	private void initPestaniasPanel() {
		
		pestaniasPanel = new JTabbedPane();
		initNameSearchSeries();
		initActors();
		initCharacters();
		
		pestaniasPanel.addTab("Series", null, nameSearchSeries, "Primer panel"); 
		pestaniasPanel.addTab("Actores", null, actors, "Segundo panel"); 
		pestaniasPanel.addTab("Personajes", null, characters, "Tercer panel");
	}

	
	private void initNameSearchSeries() {
		nameSearchSeries = new JPanel(new BorderLayout()); 
		actors = new JPanel(new BorderLayout());
		characters = new JPanel(new BorderLayout());
		initPanelSuperior();
		initPanelCentral();
		initPanelInferior();	
	}

	
	private void initPanelSuperior() {
		JPanel panelSuperior = new JPanel();
		JLabel search = new JLabel("Buscar serie");
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(240, 20));
		
		searchButton = new JButton(); 
		searchButton.setName("ButtonSearch");
		searchButton.setText("Buscar");
		
		panelSuperior.add(search, FlowLayout.LEFT);
		panelSuperior.add(searchField, FlowLayout.CENTER);
		panelSuperior.add(searchButton, FlowLayout.RIGHT);
		
		nameSearchSeries.add(panelSuperior, BorderLayout.NORTH);		
	}
	
	private void initPanelCentral() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		listModel = new DefaultListModel<String>();
		
		listSeries = new JList<String>(listModel);
		panelCentral.add(listSeries); 
		
		JScrollPane scrollValue = new JScrollPane(listSeries);
		scrollValue.setPreferredSize(new Dimension(500, 300));
		panelCentral.add(scrollValue);
		nameSearchSeries.add(panelCentral, BorderLayout.CENTER);
	}
	
	private void initPanelInferior() {
		JPanel panelInferior = new JPanel();
		
		infoButton = new JButton();
		infoButton.setText("Ver Informacion");
		infoButton.setName("ButtonInfo");
		
		newSerieButton = new JButton();
		newSerieButton.setText("Nueva Serie");
		newSerieButton.setName("ButtonNewSerie");
		
		infoEpisodios = new JButton();
		infoEpisodios.setText("Información Episodios");
		infoEpisodios.setName("ButtonEpisodios");
		
		panelInferior.add(infoButton);
		panelInferior.add(newSerieButton);
		panelInferior.add(infoEpisodios);
		
		nameSearchSeries.add(panelInferior, BorderLayout.SOUTH);	
	}

	
	private void initCharacters() {
		initPanelSuperiorPersonajes();
		initPanelCentralPersonajes();
		initPanelInferiorPersonajes();
	}
	
	private void initPanelSuperiorPersonajes(){
		JPanel panelSuperior = new JPanel();
		JLabel search = new JLabel("Buscar personajes:");
		personajesField = new JTextField();
		personajesField.setPreferredSize(new Dimension(240, 20));
		
		searchPersonajes = new JButton(); 
		searchPersonajes.setName("ButtonSearchPersonajes");
		searchPersonajes.setText("Buscar");
		
		panelSuperior.add(search, FlowLayout.LEFT);
		panelSuperior.add(personajesField, FlowLayout.CENTER);
		panelSuperior.add(searchPersonajes, FlowLayout.RIGHT);
		
		characters.add(panelSuperior, BorderLayout.NORTH);
	}

	private void initPanelCentralPersonajes() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		listModelPersonaje = new DefaultListModel<String>();
		
		listPersonajes = new JList<String>(listModelPersonaje);
		panelCentral.add(listPersonajes); 
		
		JScrollPane scrollValue = new JScrollPane(listPersonajes);
		scrollValue.setPreferredSize(new Dimension(500, 300));
		panelCentral.add(scrollValue);
		characters.add(panelCentral, BorderLayout.CENTER);
	}

	private void initPanelInferiorPersonajes() {
		JPanel panelInferior = new JPanel();
		
		infoButtonPersonaje = new JButton();
		infoButtonPersonaje.setText("Ver Informacion");
		infoButtonPersonaje.setName("ButtonInfoPersonaje");
		
		newPersonajeButton = new JButton();
		newPersonajeButton.setText("Nuevo Personaje");
		newPersonajeButton.setName("ButtonNewPersonaje");
		
	/*	infoActorSeries = new JButton();
		infoActorSeries.setText("Información de su trabajo");
		infoActorSeries.setName("ButtonActor");*/
		
		panelInferior.add(infoButtonPersonaje);
		panelInferior.add(newPersonajeButton);
	//	panelInferior.add(infoActorSeries);
		
		characters.add(panelInferior, BorderLayout.SOUTH);	
	}

	private void initActors() {
		initPanelSuperiorActors();
		initPanelCentralActors();
		initPanelInferiorActors();
	}
	
	private void initPanelSuperiorActors() {
		JPanel panelSuperior = new JPanel();
		JLabel search = new JLabel("Buscar actores");
		actorsField = new JTextField();
		actorsField.setPreferredSize(new Dimension(240, 20));
		
		searchActors = new JButton(); 
		searchActors.setName("ButtonSearchActors");
		searchActors.setText("Buscar");
		
		panelSuperior.add(search, FlowLayout.LEFT);
		panelSuperior.add(actorsField, FlowLayout.CENTER);
		panelSuperior.add(searchActors, FlowLayout.RIGHT);
		
		actors.add(panelSuperior, BorderLayout.NORTH);
	}

	private void initPanelCentralActors() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		listModelActor = new DefaultListModel<String>();
		
		listActors = new JList<String>(listModelActor);
		panelCentral.add(listActors); 
		
		JScrollPane scrollValue = new JScrollPane(listActors);
		scrollValue.setPreferredSize(new Dimension(500, 300));
		panelCentral.add(scrollValue);
		actors.add(panelCentral, BorderLayout.CENTER);
	}

	private void initPanelInferiorActors() {
		JPanel panelInferior = new JPanel();
		
		infoButtonActor = new JButton();
		infoButtonActor.setText("Ver Informacion");
		infoButtonActor.setName("ButtonInfoActor");
		
		newActorButton = new JButton();
		newActorButton.setText("Nuevo Actor");
		newActorButton.setName("ButtonNewActor");
		
		infoActorSeries = new JButton();
		infoActorSeries.setText("Información de su trabajo");
		infoActorSeries.setName("ButtonActor");
		
		panelInferior.add(infoButtonActor);
		panelInferior.add(newActorButton);
		panelInferior.add(infoActorSeries);
		
		actors.add(panelInferior, BorderLayout.SOUTH);	
	}

	public void startAdmin(){
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}
	
	private void fixViewInfoButton(){
		infoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String nombreSerie = listSeries.getSelectedValue();
				if(nombreSerie != null){
					newSerie = new NewSerieFrame(controlador, false, nombreSerie);
					AdminFrame.this.setVisible(true);
					controlador.datosSeries(nombreSerie);
				}
			}
		});
	}
	
	
	private void fixNewSerieButton(){
		newSerieButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				newSerie = new NewSerieFrame(controlador, true, "");
				AdminFrame.this.setVisible(true);
			}
		});
	}
	
	private void fixSearchButton(){
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(!searchField.getText().isEmpty()){
					controlador.buscarSerie(searchField.getText());
				}
			}
		});
	}
	
	private void fixInfoEpisodios() {
		infoEpisodios.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String nombreSerie = listSeries.getSelectedValue();
				if(nombreSerie != null){
					episodioFrame = new InfoEpisodioFrame(controlador, nombreSerie);
					controlador.mostrarCapitulos(nombreSerie);
					AdminFrame.this.setVisible(true);
				}
			}
		});
	}
	

	private void fixSearchActor() {
		searchActors.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(!searchActors.getText().isEmpty()){
					controlador.buscarActores(actorsField.getText());
				}
			}
		});
	}
	
	private void fixViewActorInfo() {
		infoButtonActor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String nombreActor = listActors.getSelectedValue();
				if(nombreActor != null){
					newActor = new NewActorFrame(controlador, false, nombreActor);
					AdminFrame.this.setVisible(true);
					controlador.datosActores(nombreActor);
				}
			}
		});
	}
	
	private void fixSearchPersonaje() {
		searchPersonajes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(!searchPersonajes.getText().isEmpty()){
					controlador.buscarPersonajes(personajesField.getText());
				}
			}
		});
	}
	
	private void fixNewActor() {
		newActorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				newActor = new NewActorFrame(controlador, true, "");
				AdminFrame.this.setVisible(true);
			}
		});
	}
	
	private void fixViewPersonajeInfo() {
		infoButtonPersonaje.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String nombrePersonaje = listPersonajes.getSelectedValue();
				if(nombrePersonaje != null){
					newPersonaje = new NewPersonajeFrame(controlador, false, nombrePersonaje);
					AdminFrame.this.setVisible(true);
					controlador.datosPersonajes(nombrePersonaje);
				}
			}
		});
	}
	
	private void fixNewPersonaje() {
		newPersonajeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				newPersonaje = new NewPersonajeFrame(controlador, true, "");
				AdminFrame.this.setVisible(true);
			}
		});
	}
	
	private void fixInfoActorSerie() {
		infoActorSeries.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String nombreActor = listActors.getSelectedValue();
				if(nombreActor != null) {
					infoActua = new InfoActua(controlador,nombreActor);
					controlador.datosActua(nombreActor);
					AdminFrame.this.setVisible(true);
				}
			}
		});
	}

	
	private void fixedButton(){
		fixViewInfoButton();
		fixNewSerieButton();
		fixSearchButton();
		fixInfoEpisodios();
		fixSearchActor();
		fixViewActorInfo();
		fixNewActor();
		fixNewPersonaje();
		fixViewPersonajeInfo();
		fixSearchPersonaje();
		fixInfoActorSerie();
		
	}


	
	@Override
	public void datosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis) {
	}

	@Override
	public void mostrarTodasLasSeries(List<Serie> series) {
		listModel.clear();
		for(int i = 0; i < series.size(); i++)
			listModel.add(i, series.get(i).toString());		
	}

	@Override
	public void errorProducido(String error) {
		JOptionPane.showOptionDialog(null, error, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);		
	}

	@Override
	public void mostrarCapitulosSeries(List<Episodio> episodios) {	}

	@Override
	public void datosEpisodio(String titulo, String fechaInicio, String sinopsis, String numCap, String numTemp) {}

	@Override
	public void mostrarTodosLosActores(List<Actor> actores) {
		listModelActor.clear();
		for(int i = 0; i < actores.size(); i++)
			listModelActor.add(i, actores.get(i).toString());	
	}

	@Override
	public void datosActor(String nif, String nombre, String fechaNacimiento, ImageIcon foto) {

	}

	@Override
	public void mostrarTodosLosPersonajes(List<Personaje> personajes) {
		listModelPersonaje.clear();
		for(int i = 0; i < personajes.size(); i++) 
			listModelPersonaje.add(i, personajes.get(i).toString());	
	}

	@Override
	public void datosPersonaje(String nombre, String descripcion) {
	}
	
}

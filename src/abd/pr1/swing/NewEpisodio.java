package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abd.pr1.Utilidades;
import abd.pr1.controladores.Controlador;
import abd.pr1.observables.ActuaObserver;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.tiposDeDatos.Actor;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Personaje;

@SuppressWarnings("serial")
public class NewEpisodio extends JFrame implements InfoEpisodioObserver, ActuaObserver, InfoPersonajeObserver,InfoActoresObserver{

	private Controlador controlador;

	private Container newEpisodioWindow;

	private JPanel episodioPanel;
	private JButton addButton;
	private JButton modButton;
	private JButton closeButton;
	private JTextField titleEpisodio;
	private JTextField anioIniEpisodio;
	private JTextArea sinopsisText;
	private JTextField numeroTemp;
	private JTextField numeroCap;
	
	private ActuaTable actuaTable;
	private JTable table;
	private JComboBox<String> personajesCombo;
	private JComboBox<String> actoresCombo;
	
	private boolean nuevaEpisodio;
	private String nombreEpisodio;
	private String nombreSerie;


	public NewEpisodio(Controlador controlador, boolean nueva, String nombreEpisodio,String nombreSerie) {
		super();
		this.controlador = controlador;
		this.nombreEpisodio = nombreEpisodio;
		this.nuevaEpisodio = nueva;
		this.nombreSerie = nombreSerie;
		controlador.registerInfoPersonajeObserver(this);
		controlador.registerInfoActorObserver(this);
		if (!nuevaEpisodio)
			controlador.registerInfoEpisodioObserver(this);
		initNewSerieFrame();
		startNew();
	}

	private void initNewSerieFrame() {
		this.setPreferredSize(new Dimension(640, 700));
		newEpisodioWindow = this.getContentPane();
		initNewSeriePanel();
		newEpisodioWindow.add(episodioPanel);

		fixedButton();
	}

	private void initNewSeriePanel() {
		episodioPanel = new JPanel(new BorderLayout());
		initPanelSuperior();
		initPanelCentral();
		initPanelInferior();
	}

	private void initPanelSuperior() {
		JPanel panelSuperior = new JPanel(new BorderLayout());

		// Titulo
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(560, 70));
		JLabel title = new JLabel("Titulo");

		titleEpisodio = new JTextField();
		titleEpisodio.setPreferredSize(new Dimension(580, 30));
		titleEpisodio.setFont(new Font("Calibri", Font.BOLD, 20));
		titleEpisodio.setToolTipText("Titulo");
		titlePanel.add(title, BorderLayout.PAGE_START);
		titlePanel.add(new JScrollPane(titleEpisodio), BorderLayout.CENTER);

		// Año	
		JPanel miniPanel = new JPanel();
		JLabel anioLabel = new JLabel("Año Inicio:");
		anioIniEpisodio = new JTextField();
		anioIniEpisodio.setPreferredSize(new Dimension(100, 20));
		miniPanel.add(anioLabel);
		miniPanel.add(anioIniEpisodio);
		
		JLabel capLabel = new JLabel("Nº capitulo:");
		JLabel tempLabel = new JLabel("Nº temporada:");
		numeroCap = new JTextField();
		numeroTemp = new JTextField();
		numeroCap.setPreferredSize(new Dimension(80, 20));
		numeroTemp.setPreferredSize(new Dimension(80, 20));
		
		miniPanel.add(capLabel);
		miniPanel.add(numeroCap);
		miniPanel.add(tempLabel);
		miniPanel.add(numeroTemp);
		
		panelSuperior.add(titlePanel, BorderLayout.NORTH);
		panelSuperior.add(miniPanel, BorderLayout.CENTER);

		episodioPanel.add(panelSuperior, BorderLayout.NORTH);
	}

	private void initPanelCentral() {
		JPanel panelCentral = new JPanel(new BorderLayout());

		JPanel miniPanel  = new JPanel();
		JPanel panelTable = new JPanel(new BorderLayout());
	
		actuaTable = new ActuaTable();
		table = new JTable(actuaTable);
		JScrollPane scrollTable = new JScrollPane(table);
		
			
		JLabel actorLabel = new JLabel("Actores: ");
		JLabel personajesLabel = new JLabel("Personajes: ");
		personajesCombo = new JComboBox<String>();
		actoresCombo = new JComboBox<String>();
		
		miniPanel.add(actorLabel);
		miniPanel.add(actoresCombo);
		miniPanel.add(personajesLabel);
		miniPanel.add(personajesCombo);
		
		
		panelTable.add(scrollTable, BorderLayout.NORTH);
		panelTable.add(miniPanel, BorderLayout.CENTER);
		
		JPanel aux = new JPanel(new BorderLayout());
		JLabel sinopsis = new JLabel("Sinopsis");
		aux.add(sinopsis, BorderLayout.LINE_START);
		

		panelTable.add(scrollTable, BorderLayout.NORTH);
		panelTable.add(miniPanel, BorderLayout.CENTER);

		sinopsisText = new JTextArea();
		sinopsisText.setEditable(true);
		sinopsisText.setPreferredSize(new Dimension(600, 300));
		JScrollPane scroll = new JScrollPane(sinopsisText);

		panelCentral.add(aux, BorderLayout.NORTH);
		panelCentral.add(scroll, BorderLayout.CENTER);
		panelCentral.add(panelTable, BorderLayout.SOUTH);

		episodioPanel.add(panelCentral, BorderLayout.CENTER);

	}

	private void initPanelInferior() {
		JPanel panelInferior = new JPanel();

		addButton = new JButton();
		addButton.setText("Añadir episodio");
		addButton.setName("ButtonMod");

		modButton = new JButton();
		modButton.setText("Modificar serie");
		modButton.setName("ButtonMod");

		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");

		if (nuevaEpisodio)
			panelInferior.add(addButton);
		else
			panelInferior.add(modButton);

		panelInferior.add(closeButton);

		episodioPanel.add(panelInferior, BorderLayout.SOUTH);
	}

	private void fixedButton() {
		fixNewButton();
		fixModButton();
		fixCloseButton();
		fixComboPersonaje() ;
	}

	private void fixModButton() {
		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (anioIniEpisodio.getText().isEmpty())
					errorProducido("Introduzca una fecha con el formato (YYYY-MM-DD)");
				if (fecha() && Utilidades.isPositiveNumber(numeroCap.getText()) && Utilidades.isPositiveNumber(numeroTemp.getText())) {
					String fecha = anioIniEpisodio.getText().trim(); 
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaToken;
					try {
						fechaToken = (Date)f.parse(fecha);
						controlador.modificarEpisodio(nombreEpisodio, titleEpisodio.getText(),sinopsisText.getText(), fechaToken
								,Integer.parseInt(numeroCap.getText()), Integer.parseInt(numeroTemp.getText()), nombreSerie);
						dispose();
					} 
					catch (ParseException e) {}	
				}
			}
		});
	}

	private void fixNewButton() {
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (anioIniEpisodio.getText().isEmpty())
					errorProducido("Introduzca una fecha con el formato (YYYY-MM-DD)");
				if (fecha() && Utilidades.isPositiveNumber(numeroCap.getText()) && Utilidades.isPositiveNumber(numeroTemp.getText())) {
					String fecha = anioIniEpisodio.getText().trim(); 
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaToken;
					try {
						fechaToken = (Date)f.parse(fecha);
						if(controlador.nuevoEpisodio(titleEpisodio.getText(),sinopsisText.getText(), fechaToken
								,Integer.parseInt(numeroCap.getText()), Integer.parseInt(numeroTemp.getText()), nombreSerie))
						dispose();
					} 
					catch (ParseException e) {
					}	
				}
			}
		});
	}

	private void fixCloseButton() {
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewEpisodio.this.setVisible(false);

			}
		});
	}

	public void startNew() {
		this.pack();
		setLocationRelativeTo(null);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

	@Override
	public void datosEpisodio(String nombre, String fechaIni, String sinopsis, String numEp, String numTemp) {
		this.titleEpisodio.setText(nombre);
		this.numeroTemp.setText(numTemp);
		this.numeroCap.setText(numEp);
		this.anioIniEpisodio.setText(fechaIni);
		this.sinopsisText.setText(sinopsis);
	}

	@Override
	public void mostrarCapitulosSeries(List<Episodio> episodios) {}

	private boolean fecha() {
		String[] arrayFecha = anioIniEpisodio.getText().trim().split("-");

		if (arrayFecha.length != 3)
			return false;

		if (!Utilidades.isPositiveNumber(arrayFecha[0])|| !Utilidades.isPositiveNumber(arrayFecha[1]) || !Utilidades.isPositiveNumber(arrayFecha[2]))
			return false;

		int dia = Integer.parseInt(arrayFecha[0]);
		int mes = Integer.parseInt(arrayFecha[1]);
		int ano = Integer.parseInt(arrayFecha[2]);

		GregorianCalendar dat = new GregorianCalendar(ano + 1900, mes, dia);

		return (dat != null);
	}
	
	private void fixComboPersonaje() {
		personajesCombo.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {  
            	//String nombrePersonaje = (String) personajesCombo.getSelectedItem();
            	//controlador.mostrarCapitulos(nombreActor);
            }
        });
	}
	
	
	@Override
	public void errorProducido(String error) {}

	@Override
	public void datosActua(String nombreSerie, String nombreEpisodio, String personaje) {
		String[] datos = new String[] {nombreSerie, nombreEpisodio, personaje};
		actuaTable.setValue(datos);
		actuaTable.fireTableDataChanged();
	}
	

	@Override
	public void mostrarTodosLosPersonajes(List<Personaje> personajes) {
		personajesCombo.removeAllItems();
		personajesCombo.addItem("Elige un personaje...");
		Iterator<Personaje> it = personajes.iterator();
		while(it.hasNext()){
			personajesCombo.addItem(it.next().getNombre());
		}
	}

	@Override
	public void mostrarTodosLosActores(List<Actor> actores) {
		actoresCombo.removeAllItems();
		actoresCombo.addItem("Elige un actor...");
		Iterator<Actor> it = actores.iterator();
		while(it.hasNext()){
			actoresCombo.addItem(it.next().getNombre());
		}}

	@Override
	public void datosActor(String nif, String nombre, String fechaNacimiento, ImageIcon foto) {}
	
	@Override
	public void datosPersonaje(String nombre, String descripcion) {}


}

package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.observables.UserObserver;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Serie;
import abd.pr1.tiposDeDatos.Usuario;

@SuppressWarnings({ "unused", "serial" })
public class UserFrame extends JFrame implements UserObserver, InfoSerieObserver, InfoEpisodioObserver{
	
	private Controlador controlador;
	
	private Container userWindow;
	
	private ModUserFrame modUserFrame; 
	private InfoEpisodioUser infoEpisodio;
	
	private JPanel infoUser;						// 1. Panel informacion usuario
	private JLabel nombreLabel;
	private JLabel edadLabel;
	private ImageIcon avatarImage;
	private JButton avatarButton;
		
	private JTabbedPane pestaniasPanel;				// 2. Panel de pestañas	
	
	private JPanel nameSearchSeries;				// 2.1 Panel "Buscar series"	
	
	private JTextField searchField;					// 2.1.1 Panel superior "Buscar series"
	private JButton searchButton;	
	
	private DefaultListModel<String> listModel;		// 2.1.2 Panel central "Buscar series"
	private JList<String> listSeries;	
	
	private JButton infoSerieButton;				// 2.1.3 Panel inferior "Buscar series"
	private InfoSerieFrame infoSerie;
		
	private JPanel mySeries;						// 2.2 Panel "Mis Series"	
	
	private JComboBox<String> comboSeries;			// 2.2.1 Panel superior "Mis Series"
	
	private EpisodioTable episoTable;				// 2.2.2 Panel central "Mis Series"
	
	private JButton infoChapterButton;				// 2.2.3 Panel inferior "Mis Series" 
	private JButton markButton;

	private JTable table;

	private String nickUsuario;

	
	
	public UserFrame(Controlador controlador, String nick){
		super("Seguidores de serie");
		this.controlador = controlador;
		this.nickUsuario = nick;
		initUserFrame();
		controlador.registerUserObserver(this);
		controlador.registerInfoSerieObserver(this);
		controlador.registerInfoEpisodioObserver(this);
		controlador.registerInfoEpisodioObserver(this);
		controlador.datosUsuariosIniciales();
		startUser();
	}
	

	private void initUserFrame() {
		this.setPreferredSize(new Dimension(640, 600));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		userWindow = this.getContentPane();
		initInfoPanel();
		initPestaniasPanel();
		userWindow.add(infoUser, BorderLayout.NORTH);
		userWindow.add(pestaniasPanel);
		
		fixedButton();
	}
	
	private void initPestaniasPanel() {
		
		pestaniasPanel = new JTabbedPane();
		initNameSearchSeries();
		initMySeries();
		
		pestaniasPanel.addTab("Buscar Series", null, nameSearchSeries, "Primer panel"); 
		pestaniasPanel.addTab("Mis Series", null, mySeries, "Segundo panel"); 
	}

	
	private void initInfoPanel() {
		infoUser = new JPanel();
		nombreLabel = new JLabel("Flores");
		nombreLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		
		edadLabel = new JLabel("33 años");
		edadLabel.setFont(new Font("Calibri", Font.BOLD, 16));
		
		avatarButton = new JButton();
		avatarButton.setName("ButtonAvatar");
		avatarImage = new ImageIcon(getClass().getResource("Images/images.jpg"));
		avatarButton.setIcon(avatarImage);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(nombreLabel, BorderLayout.NORTH);
		panel.add(edadLabel, BorderLayout.CENTER);
		
		infoUser.add(avatarButton, FlowLayout.LEFT);
		infoUser.add(panel);	
	}

	private void initNameSearchSeries() {
		nameSearchSeries = new JPanel(new BorderLayout()); 
		initPanelSuperiorSearch();
		initPanelCentralSearch();
		initPanelInferiorSearch();
		
	}

	private void initPanelSuperiorSearch() {
		JPanel panelSuperior = new JPanel();
		JLabel search = new JLabel("Buscar series");
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
	
	private void initPanelCentralSearch() {
		JPanel panelCentral = new JPanel(new BorderLayout());		
		
		listModel = new DefaultListModel<String>();
		
		listSeries = new JList<String>(listModel);
		panelCentral.add(listSeries); 
		
		JScrollPane scrollValue = new JScrollPane(listSeries);
		scrollValue.setPreferredSize(new Dimension(500, 300));
		panelCentral.add(scrollValue);
		nameSearchSeries.add(panelCentral, BorderLayout.CENTER);
	}
	
	private void initPanelInferiorSearch() {
		JPanel panelInferior = new JPanel();
		
		infoSerieButton = new JButton();
		infoSerieButton.setText("Ver Informacion");
		infoSerieButton.setName("ButtonInfo");
		
		panelInferior.add(infoSerieButton);
		
		nameSearchSeries.add(panelInferior, BorderLayout.SOUTH);	
	}

	
	private void initMySeries() {
		mySeries = new JPanel(new BorderLayout());
		initPanelSuperiorSerie();
		initPanelCentralSerie();
		initPanelInferiorSerie();
	}
	
	private void initPanelSuperiorSerie() {
		JPanel panelSuperiorSerie = new JPanel();
		JLabel serie = new JLabel("Serie:");
		
		comboSeries = new JComboBox<String>();
		
		panelSuperiorSerie.add(serie);
		panelSuperiorSerie.add(comboSeries);
		
		fixComboSeries();
		mySeries.add(panelSuperiorSerie, BorderLayout.NORTH);
	}
	
	private void fixComboSeries() {
		comboSeries.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {  
            	String nombreSerie = (String) comboSeries.getSelectedItem();
            	controlador.mostrarCapitulos(nombreSerie);
            }
        });
	}


	private void initPanelCentralSerie() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		JLabel episodios = new JLabel("Episodios no vistos:");
		episoTable = new EpisodioTable();
		table = new JTable(episoTable);
		JScrollPane scrollTable = new JScrollPane(table);
		
		panelCentral.add(episodios, BorderLayout.NORTH);
		panelCentral.add(scrollTable);
		
		mySeries.add(panelCentral, BorderLayout.CENTER);
	}

	private void initPanelInferiorSerie() {
		JPanel panelInferior = new JPanel();
		
		infoChapterButton = new JButton();
		infoChapterButton.setText("Informacion");
		infoChapterButton.setName("ButtonInformacion");
		
		markButton = new JButton();
		markButton.setText("Marcar como visto");
		markButton.setName("ButtonMark");
		
		panelInferior.add(infoChapterButton);
		panelInferior.add(markButton);
		
		mySeries.add(panelInferior, BorderLayout.SOUTH);
	}
	
	
	public void startUser(){
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}
	
	
	

	private void fixedButton(){
		fixInfoSerieButton();
		fixInfoChapterButton();
		fixMarkButton();
		fixAvatarButton();
		fixSearchButton();
	}
		
	private void fixInfoSerieButton(){
		infoSerieButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String serieName = listSeries.getSelectedValue();
				if(serieName != null){
					infoSerie = new InfoSerieFrame(controlador, serieName, nickUsuario);
					controlador.datosSeries(serieName.toString());
					UserFrame.this.setVisible(true);
				}
			}
		});
	}
	
	private void fixInfoChapterButton(){
		infoChapterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				int row = table.getSelectedRow();
				if(0 <= row){
					String nombreEpisodio = episoTable.getTituloEpisodio(row);
					infoEpisodio = new InfoEpisodioUser(controlador, nombreEpisodio);
					controlador.datosEpisodio(nombreEpisodio);
					UserFrame.this.setVisible(true);
				}
			}
		});
	}
	
	private void fixMarkButton(){
		markButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				JOptionPane.showOptionDialog(null, "¿Marcar como vista?", "Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				int row = table.getSelectedRow();
				if(0 <= row){
					String nombreSerie = (String)comboSeries.getSelectedItem();
					String nombreEpisodio = episoTable.getTituloEpisodio(row);
					controlador.seguirSerie(nombreSerie, nombreEpisodio, nickUsuario);
					UserFrame.this.setVisible(true);
				}
			}
		});
	}

	private void fixAvatarButton(){
		avatarButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Usuario user = controlador.obtenerUsuario(nickUsuario);
				modUserFrame = new ModUserFrame(controlador, avatarButton,avatarImage, user);
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


	@Override
	public void actualizarDatosUsuario(String nombre, long edad, ImageIcon newImagen) {
		String anio = " años";
		if(edad == 1)
			anio = " año";
			
		nombreLabel.setText(nombre);
		edadLabel.setText(edad + anio);
		avatarImage = newImagen;
		avatarButton.setIcon(avatarImage);
	}


	@Override
	public void mostrarTodasLasSeries(List<Serie> series) {
		listModel.clear();
		for(int i = 0; i < series.size(); i++)
			listModel.add(i, series.get(i).toString());
	}


	@Override
	public void datosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis) {
	}


	@Override
	public void aniadidaSerieSeguida(List<Serie> seriesSeguidas) {
		comboSeries.removeAllItems();
		comboSeries.addItem("Elige una serie...");
		Iterator<Serie> it = seriesSeguidas.iterator();
		while(it.hasNext()){
			comboSeries.addItem(it.next().getNombre());
		}
	}


	@Override
	public void mostrarCapitulosSeries(List<Episodio> episodios) {
		episoTable.refresh(episodios);
	}


	@Override
	public void datosEpisodio(String titulo, String fechaInicio, String sinopsis, String numCap, String numTemp) {
	}
	
	@Override
	public void errorProducido(String error) {
		JOptionPane.showOptionDialog(null, error, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);		
	}
}

package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abd.pr1.Utilidades;
import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.tiposDeDatos.Serie;

@SuppressWarnings("serial")
public class NewSerieFrame extends JFrame implements InfoSerieObserver{

	private Controlador controlador;
	
	private Container newSerieWindow;
	
	private JPanel seriePanel;
	private JButton addButton;
	private JButton modButton;
	private JButton closeButton;
	private JTextField titleSerie;
	private JTextField anioFinSerie;
	private JTextArea resumen;
	private JTextArea sinopsisText;
	
	private JCheckBox comediaBox;
	private JCheckBox terrorBox;
	private JCheckBox thrillerBox;
	private JCheckBox dramaBox;
	
	private boolean nuevaSerie;
	private String nombreSerie;
	
	
	public NewSerieFrame(Controlador controlador, boolean nueva, String nombreSerie){
		super();
		this.controlador = controlador;
		this.nombreSerie = nombreSerie;
		this.nuevaSerie = nueva;
		if(!nuevaSerie)
			controlador.registerInfoSerieObserver(this);
		initNewSerieFrame();
		startNew();
	}

	private void initNewSerieFrame(){
		this.setPreferredSize(new Dimension(640, 450));
		newSerieWindow = this.getContentPane();
		initNewSeriePanel();
		newSerieWindow.add(seriePanel);
		
		fixedButton();
	}

	

	private void initNewSeriePanel() {
		seriePanel = new JPanel(new BorderLayout());
		initPanelSuperior();
		initPanelCentral();
		initPanelInferior();
	}

	private void initPanelSuperior() {
		JPanel panelSuperior = new JPanel(new BorderLayout());
		
		//Titulo
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(560, 70));
		JLabel title = new JLabel("Titulo");
		
		titleSerie = new JTextField();
		titleSerie.setPreferredSize(new Dimension(580, 30));
		titleSerie.setFont(new Font("Calibri", Font.BOLD, 20));	
		titleSerie.setToolTipText("Titulo");
		titlePanel.add(title, BorderLayout.PAGE_START);
		titlePanel.add(new JScrollPane (titleSerie),BorderLayout.CENTER);
		
		//Resumen
		JLabel resumenLabel = new JLabel("Resumen");	
		resumen = new JTextArea();
		resumen.setPreferredSize(new Dimension(580, 40));
		JScrollPane scroll = new JScrollPane(resumen);
		scroll.setSize(new Dimension(585, 70));
		JPanel resumenPanel = new JPanel(new BorderLayout());
		resumenPanel.setPreferredSize(new Dimension(570, 100));
		resumenPanel.add(resumenLabel, BorderLayout.PAGE_START);
		resumenPanel.add(scroll, BorderLayout.CENTER);
		
		//Genero
		JPanel generoPanel = new JPanel(new BorderLayout());
		JPanel miniPanel = new JPanel();
		JLabel anioLabel = new JLabel("Año fin:");
		anioFinSerie = new JTextField();
		anioFinSerie.setPreferredSize(new Dimension(100, 20));
		miniPanel.add(anioLabel);
		miniPanel.add(anioFinSerie);
		
		generoPanel.setPreferredSize(new Dimension(570, 80));
		JLabel genero = new JLabel("Genero");
		JPanel checkPanel = initCheckBox();		
		generoPanel.add(genero, BorderLayout.PAGE_START);
		generoPanel.add(checkPanel, BorderLayout.CENTER);
		generoPanel.add(miniPanel, BorderLayout.PAGE_END);
		
		panelSuperior.add(titlePanel, BorderLayout.NORTH);
		panelSuperior.add(resumenPanel, BorderLayout.CENTER);
		panelSuperior.add(generoPanel, BorderLayout.SOUTH);
		
		seriePanel.add(panelSuperior , BorderLayout.NORTH);
	}

	private JPanel initCheckBox() {
		JPanel checkBoxPanel = new JPanel(new FlowLayout());
		checkBoxPanel.setPreferredSize(new Dimension(570, 30));
		
		comediaBox 	 = new JCheckBox("Comedia");
		terrorBox 	 = new JCheckBox("Terror");
		thrillerBox  = new JCheckBox("Thriller");
		dramaBox 	 = new JCheckBox("Drama");

		checkBoxPanel.add(comediaBox);
		checkBoxPanel.add(terrorBox);
		checkBoxPanel.add(thrillerBox);
		checkBoxPanel.add(dramaBox);
	
		return checkBoxPanel;
	}

	private void initPanelCentral() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		JPanel aux = new JPanel(new BorderLayout());
		JLabel sinopsis = new JLabel("Sinopsis");
		aux.add(sinopsis, BorderLayout.LINE_START);
		
		sinopsisText = new JTextArea();
		sinopsisText.setEditable(true);
		sinopsisText.setPreferredSize(new Dimension(600, 300));
		JScrollPane scroll = new JScrollPane(sinopsisText);
		
		panelCentral.add(aux, BorderLayout.NORTH);
		panelCentral.add(scroll, BorderLayout.CENTER);
		
		seriePanel.add(panelCentral, BorderLayout.CENTER);
		
	}


	private void initPanelInferior() {
		JPanel panelInferior = new JPanel();
		
		addButton = new JButton();
		addButton.setText("Añadir serie");
		addButton.setName("ButtonMod");
		
		modButton = new JButton();
		modButton.setText("Modificar serie");
		modButton.setName("ButtonMod");
		
		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");
		
		if(nuevaSerie)
			panelInferior.add(addButton);
		else
			panelInferior.add(modButton);
		
		panelInferior.add(closeButton);
		
		seriePanel.add(panelInferior, BorderLayout.SOUTH);
	}
	
	private void fixedButton() {
		fixNewButton();
		fixModButton();
		fixCloseButton();
	}
	
	
	
	
	private void fixModButton() {
		modButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if(anioFinSerie.getText().isEmpty())
				anioFinSerie.setText("0");
			if(Utilidades.isNumber(anioFinSerie.getText())){
				boolean[] boxes = obtenerGeneros();
				controlador.modificarSerie(nombreSerie, titleSerie.getText(), resumen.getText(), boxes, sinopsisText.getText(), Integer.parseInt(anioFinSerie.getText()));
				dispose();
			}
			
		}

	});
		
	}

	private void fixNewButton(){
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
				if(anioFinSerie.getText().isEmpty())
					anioFinSerie.setText("0");
				if(Utilidades.isNumber(anioFinSerie.getText())){
					boolean[] boxes = obtenerGeneros();
					controlador.nuevaSerie(titleSerie.getText(), resumen.getText(), boxes, sinopsisText.getText(), Integer.parseInt(anioFinSerie.getText()));
					dispose();
				}
				
			}

			
		});
	}
	
	private boolean[] obtenerGeneros() {
		boolean generos[] = new boolean[4];
		
		generos[0] = comediaBox.isSelected();
		generos[1] = terrorBox.isSelected();
		generos[2] = thrillerBox.isSelected();
		generos[3] = dramaBox.isSelected();
		
		return generos;
	}
	
	private void fixCloseButton(){
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				NewSerieFrame.this.setVisible(false);
				
			}
		});
	}
	
	
	public void startNew(){
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}

	@Override
	public void datosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis) {	
	
		
		this.titleSerie.setText(nombre);
		this.resumen.setText(titular);
		
		String arrayGenero[] = new String[4];
		arrayGenero = generos.trim().split(" ");

		comediaBox.setSelected(checkearGenero("comedia",  arrayGenero));
		terrorBox.setSelected(checkearGenero("terror",   arrayGenero));
		thrillerBox.setSelected(checkearGenero("thriller", arrayGenero));
		dramaBox.setSelected(checkearGenero("drama",	   arrayGenero));
		
		this.anioFinSerie.setText(fechaFin);
		
		this.sinopsisText.setText(sinopsis);
		
	}

	private boolean checkearGenero(String genero, String[] generos){
		int i = 0;
		boolean encontrado = false;
		
		while(!encontrado && i < generos.length){
			encontrado = (generos[i].equalsIgnoreCase(genero));
			i++;
		}
		return encontrado;
	}
	
	@Override
	public void mostrarTodasLasSeries(List<Serie> series) {
	}

	@Override
	public void errorProducido(String error) {}

}	
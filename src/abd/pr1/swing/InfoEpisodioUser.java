package abd.pr1.swing;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.tiposDeDatos.Episodio;

@SuppressWarnings({"serial", "unused"})
public class InfoEpisodioUser extends JFrame implements InfoEpisodioObserver{
	
	private Controlador controlador;
	
	private Container infoWindow;
	private String nombreEpisodio;
	
	private JPanel episodioPanel;
	private JButton commentButton;
	private JButton closeButton;
	private JLabel titleEpisodio;
	private JLabel fechaEstreno;
	private JTextArea sinopsis;
	private JTextArea titulo;
	
	public InfoEpisodioUser(Controlador controlador, String nombreEpisodio){
		super();
		this.controlador = controlador;
		this.nombreEpisodio = nombreEpisodio;
		this.controlador.registerInfoEpisodioObserver(this);
		initInfoEpisodioFrame();
		starInfo();
	}

	private void initInfoEpisodioFrame(){
		this.setPreferredSize(new Dimension(640, 450));
		infoWindow = this.getContentPane();
		initInfoEpisodioPanel();
		infoWindow.add(episodioPanel);
		
		fixButton();
	}

	private void fixButton() {
		fixCloseButton();
	}


	private void initInfoEpisodioPanel() {
		episodioPanel = new JPanel(new BorderLayout());
		initPanelSuperior();
		initPanelInferior();
	}

	private void initPanelSuperior() {
		JPanel panelSuperior = new JPanel(new BorderLayout());
		
		JPanel aux = new JPanel();
		
		titleEpisodio = new JLabel(nombreEpisodio);
		titleEpisodio.setFont(new Font("Calibri", Font.BOLD, 30));
		
		sinopsis = new JTextArea();
		sinopsis.setEditable(false);
		sinopsis.setPreferredSize(new Dimension(600, 300));
		JScrollPane scroll = new JScrollPane(sinopsis);
		
		aux.add(titleEpisodio);
		
		JPanel panelSouth = new JPanel(new BorderLayout());
		JLabel fecha = new JLabel("Fecha de estreno:  ");
		fechaEstreno = new JLabel();
		
		
		panelSouth.add(fecha, BorderLayout.LINE_START);
		panelSouth.add(fechaEstreno, BorderLayout.CENTER);
		
		panelSuperior.add(aux, BorderLayout.NORTH);
		panelSuperior.add(scroll, BorderLayout.CENTER);
		panelSuperior.add(panelSouth, BorderLayout.SOUTH);
		
		episodioPanel.add(panelSuperior , BorderLayout.NORTH);
	}

	private void initPanelInferior() {
		JPanel panelInferior = new JPanel();
		
		commentButton = new JButton();
		commentButton.setText("Comentar");
		commentButton.setName("ButtonComment");
		
		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");
		
		panelInferior.add(commentButton);
		panelInferior.add(closeButton);
		
		episodioPanel.add(panelInferior, BorderLayout.SOUTH);
	}
	
	
	private void fixCloseButton(){
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				InfoEpisodioUser.this.setVisible(false);
			}
		});
	}
	
	public void starInfo(){
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}

	@Override
	public void mostrarCapitulosSeries(List<Episodio> episodios) {
	}

	@Override
	public void datosEpisodio(String titulo, String fechaInicio, String sinopsis, String numCap, String numTemp) {
		this.titleEpisodio.setText(titulo + " (" + "Episodio " + numCap+ ", Temporada " + numTemp + " )");
		this.fechaEstreno.setText(fechaInicio);
		this.sinopsis.setText(sinopsis);
	}

	@Override
	public void errorProducido(String error) {
	}
		
}



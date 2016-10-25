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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoSerieObserver;
import abd.pr1.tiposDeDatos.Serie;

@SuppressWarnings("serial")
public class InfoSerieFrame extends JFrame implements InfoSerieObserver{
	
	private Controlador controlador;
	
	private Container infoWindow;
	
	private JPanel seriePanel;
	private JButton followButton;
	private JButton closeButton;
	private JLabel generoSerie;
	private JLabel titleSerie;
	private JTextArea resumen;
	private JTextArea sinopsisText;

	private String nombreSerie;

	private JLabel mediaVotoSerie;
	private JButton voteButton;
	private JSlider voteSlider;
	private String nickUsuario;
	
	public InfoSerieFrame(Controlador controlador, String serieName, String nickUsuario){
		super();
		this.controlador = controlador;
		controlador.registerInfoSerieObserver(this);
		this.nombreSerie = serieName;
		this.nickUsuario = nickUsuario;
		initInfoSerieFrame();
		starInfo();
	}

	private void initInfoSerieFrame(){
		this.setPreferredSize(new Dimension(640, 450));
		infoWindow = this.getContentPane();
		initInfoSeriesPanel();
		infoWindow.add(seriePanel);
		
		fixedButton();
	}

	private void initInfoSeriesPanel() {
		seriePanel = new JPanel(new BorderLayout());
			
		initPanelSuperior();
		initPanelCentral();
		initPanelInferior();
	}

	private void initPanelSuperior() {
		JPanel panelSuperior = new JPanel(new BorderLayout());
		
		JPanel aux = new JPanel();
		
		titleSerie = new JLabel("HOW I MET YOUR MOTHER (2005-?)");
		titleSerie.setFont(new Font("Calibri", Font.BOLD, 30));
		
		resumen = new JTextArea();
		resumen.setEditable(false);
		//resumen.setPreferredSize(new Dimension(600, 40));
		JScrollPane scroll = new JScrollPane(resumen);
		
		aux.add(titleSerie);
		
		JPanel panelSouth = new JPanel(new BorderLayout());
		JLabel genero = new JLabel("Genero:  ");
		generoSerie = new JLabel("Comedia, Romántica, Drama");
		
		panelSouth.add(genero, BorderLayout.LINE_START);
		panelSouth.add(generoSerie, BorderLayout.CENTER);
		
		panelSuperior.add(aux, BorderLayout.NORTH);
		panelSuperior.add(scroll, BorderLayout.CENTER);
		panelSuperior.add(panelSouth, BorderLayout.SOUTH);
		
		seriePanel.add(panelSuperior , BorderLayout.NORTH);
	}

	private void initPanelCentral() {
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		JPanel aux = new JPanel(new BorderLayout());
		JLabel sinopsis = new JLabel("Sinopsis");
		aux.add(sinopsis, BorderLayout.LINE_START);
		
		sinopsisText = new JTextArea();
		sinopsisText.setEditable(false);
	//	sinopsisText.setPreferredSize(new Dimension(600, 300));
		JScrollPane scroll = new JScrollPane(sinopsisText);
		
		panelCentral.add(aux, BorderLayout.NORTH);
		panelCentral.add(scroll, BorderLayout.CENTER);
		
		JPanel votosPanel = new JPanel(new FlowLayout());
		JLabel mediaVotos = new JLabel("Media votos: ");
		mediaVotoSerie = new JLabel("?");
		
		voteSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		voteSlider.setMajorTickSpacing(1);
		voteSlider.setPaintLabels(true);
		
		votosPanel.add(mediaVotos);
		votosPanel.add(mediaVotoSerie);
		votosPanel.add(new JSeparator());
		votosPanel.add(voteSlider);
		
		panelCentral.add(aux, BorderLayout.NORTH);
		panelCentral.add(scroll, BorderLayout.CENTER);		
		panelCentral.add(votosPanel, BorderLayout.SOUTH);		
		
		seriePanel.add(panelCentral, BorderLayout.CENTER);	
	}


	private void initPanelInferior() {
		JPanel panelInferior = new JPanel();
		
		followButton = new JButton();
		followButton.setText("Seguir serie");
		followButton.setName("ButtonFollow");
		
		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");
		
		voteButton = new JButton();
		voteButton.setText("Votar");
		voteButton.setName("buttonVoto");
		
		panelInferior.add(followButton);
		panelInferior.add(voteButton);
		panelInferior.add(closeButton);
		
		seriePanel.add(panelInferior, BorderLayout.SOUTH);
	}
	
	private void fixedButton() {
		fixFollowButton();
		fixCloseButton();
		fixVoteButton();
	}
	
	
	private void fixVoteButton() {
		voteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				controlador.votarSerie(voteSlider.getValue(), nombreSerie, nickUsuario);
			}
		});
	}

	private void fixFollowButton(){
		followButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				int op = JOptionPane.showOptionDialog(null, titleSerie.getText() + "?", "¿Quieres seguir ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				if(op == JOptionPane.YES_OPTION){
					if(!controlador.verEpisodio(nombreSerie)){
						String message = "Esta serie ya la sigues";
						JOptionPane.showOptionDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, null, null);	
					}		
				}
			}
		});
	}
	
	private void fixCloseButton(){
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				InfoSerieFrame.this.dispose();
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
	public void datosSerie(String nombre, String fechaIni, String fechaFin, String titular, String generos, String sinopsis) {
		nombre += " (";
		
		if(fechaIni == null){
			nombre += "Sin comenzar)";		
		}
		else{
			nombre += fechaIni.split("-")[0] + " - ";
			if(fechaFin.equalsIgnoreCase("0"))
				nombre += " ?)";
			else
				nombre += fechaFin + ")";
		}
		
		this.titleSerie.setText(nombre);
		this.resumen.setText(titular);
		this.generoSerie.setText(generos);
		this.sinopsisText.setText(sinopsis);
		
	}

	@Override
	public void mostrarTodasLasSeries(List<Serie> series) {}

	@Override
	public void errorProducido(String error) {}

	
	
}

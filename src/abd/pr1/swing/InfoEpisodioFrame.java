package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.observables.InfoEpisodioObserver;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.tiposDeDatos.Actor;
import abd.pr1.tiposDeDatos.Episodio;
import abd.pr1.tiposDeDatos.Personaje;

@SuppressWarnings({ "unused", "serial" })
public class InfoEpisodioFrame extends JFrame implements InfoEpisodioObserver{
	
	private Controlador controlador;
	private String nameSerie;
	
	private JButton newEpisodeButton;
	private JButton modEpisodeButton;
	private JButton deleteEpisodeButton;
	private JButton closeButton;
	
	private NewEpisodio nuevoEpisodio;
	
	private EpisodioTable episoTable;
	private JTable table;	
	
	public InfoEpisodioFrame(Controlador controlador, String serie) {
		super(serie);
		this.controlador = controlador;
		this.nameSerie = serie;
		initEpisodio();
		controlador.registerInfoEpisodioObserver(this);
		
		starInfo();
	}

	private void initEpisodio() {
		JPanel panelPrincipal= new JPanel(new BorderLayout());
		JPanel panelInferior = new JPanel();

		episoTable = new EpisodioTable();
		
		newEpisodeButton = new JButton();
		newEpisodeButton.setText("Nuevo");
		newEpisodeButton.setName("ButtonNewCap");
		
		modEpisodeButton = new JButton();
		modEpisodeButton.setText("Ver Informacion");
		modEpisodeButton.setName("ButtonModCap");
		
		deleteEpisodeButton = new JButton();
		deleteEpisodeButton.setText("Eliminar");
		deleteEpisodeButton.setName("ButtonDeleteCap");
		
		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");
		
		panelInferior.add(modEpisodeButton);
		panelInferior.add(newEpisodeButton);
		panelInferior.add(deleteEpisodeButton);
		panelInferior.add(closeButton);		
		
	    table = new JTable(episoTable);
		JScrollPane scrollTable = new JScrollPane(table);
		panelPrincipal.add(scrollTable, BorderLayout.CENTER);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		this.add(panelPrincipal);
		fixedButtons();
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

	private void fixedButtons() {
		fixNewButton();
		fixModButton();
		fixDeleteButton();
		fixCloseButton();
	}

	private void fixNewButton() {
		newEpisodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nuevoEpisodio = new NewEpisodio(controlador, true, "", nameSerie);
				InfoEpisodioFrame.this.setVisible(true);
			}
		});
	}

	private void fixModButton() {
		modEpisodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(0 <= row){
					String nombreEpisodio =episoTable.getTituloEpisodio(row);
					nuevoEpisodio = new NewEpisodio(controlador, false, nombreEpisodio, nameSerie);
					controlador.datosEpisodio(nombreEpisodio);
					controlador.dameTodosPersonajes(nombreEpisodio);
					controlador.dameTodosActores();
				}
			}
		});
	}

	private void fixDeleteButton() {
		deleteEpisodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(0 <= row){
					String nombreEpisodio = episoTable.getTituloEpisodio(row);
					if(confirmar("¿Eliminar " + nombreEpisodio  + "?", "Eliminar capitulo") == JOptionPane.YES_OPTION) {
						controlador.eliminarEpisodio(nombreEpisodio, nameSerie);
					}
				}
			}
		});
	}

	private void fixCloseButton() {
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InfoEpisodioFrame.this.setVisible(false);
			}
		});
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
	}
	
	private int confirmar(String mensaje, String title){
		return JOptionPane.showConfirmDialog(null, mensaje, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
	}
}

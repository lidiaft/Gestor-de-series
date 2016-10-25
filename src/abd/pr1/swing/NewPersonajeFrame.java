
package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoPersonajeObserver;
import abd.pr1.tiposDeDatos.Personaje;

@SuppressWarnings("serial")
public class NewPersonajeFrame extends JFrame implements InfoPersonajeObserver {

	private Controlador controlador;
	private boolean nuevo;
		
	private JButton cancelButton;
	private JButton nuevoButton;
	private JTextField nombreField;
	private JTextArea descripcion;
	
	
	public NewPersonajeFrame (Controlador controlador, boolean nuevo, String nombrePersonaje) {
		super(nombrePersonaje);
		this.controlador = controlador;
		this.nuevo= nuevo;
		controlador.registerInfoPersonajeObserver(this);
		initInfoPanel();
		fixButtons();	
		startNewPersonaje();
	}

	private void fixButtons() {
		fixCancelButton();
		fixNuevoButton();
	}

	private void initInfoPanel() {
		JPanel panelSuperior= new JPanel(new BorderLayout());
		JPanel panelNombre = new JPanel();
		JPanel panelDescripcion = new JPanel();
		
		JLabel labelNombre = new JLabel("Nombre: ");
		nombreField = new JTextField();
		nombreField.setPreferredSize(new Dimension(100, 20));
		nombreField.setEditable(false);
		panelNombre.add(labelNombre);
		panelNombre.add(nombreField);
		
		JLabel labelFecha = new JLabel("Descripcion: ");
		descripcion = new JTextArea();
		descripcion.setPreferredSize(new Dimension(300, 400));
		descripcion.setEditable(false);
		panelDescripcion.add(labelFecha);
		panelDescripcion.add(descripcion);
		
		panelSuperior.add(panelNombre, BorderLayout.NORTH);
		panelSuperior.add(panelDescripcion, BorderLayout.SOUTH);
		
		
		JPanel minipanel = new JPanel();
		cancelButton = new JButton();
		cancelButton.setText("Cerrar");
		nuevoButton = new JButton();
		nuevoButton.setText("Aceptar");
		if(nuevo) {
			nombreField.setEditable(true);
			nombreField.setEditable(true);
			descripcion.setEditable(true);
			minipanel.add(nuevoButton);
		}
		minipanel.add(cancelButton);
		
		this.add(panelSuperior , BorderLayout.NORTH);
		this.add(minipanel, BorderLayout.SOUTH);
			
	}


	
	private void fixNuevoButton() {
		nuevoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
						if(controlador.nuevoPersonaje(nombreField.getText(), descripcion.getText()));
							dispose();
			} 
		});
	}
	
	private void fixCancelButton() {
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				dispose();
			}
		});
	}
	

	public void startNewPersonaje() {
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });	
	}


	
	@Override
	public void errorProducido(String error) {
		JOptionPane.showOptionDialog(null, error, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);	
	}

	@Override
	public void mostrarTodosLosPersonajes(List<Personaje> actores) {
	}

	@Override
	public void datosPersonaje(String nombre, String descripcion) {
		this.nombreField.setText(nombre);
		this.descripcion.setText(descripcion);
	}
}

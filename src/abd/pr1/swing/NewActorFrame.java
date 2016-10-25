package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import abd.pr1.Utilidades;
import abd.pr1.controladores.Controlador;
import abd.pr1.observables.InfoActoresObserver;
import abd.pr1.tiposDeDatos.Actor;

@SuppressWarnings("serial")
public class NewActorFrame extends JFrame implements InfoActoresObserver{

	private Controlador controlador;
	private boolean nuevo;
	
	private JLabel labelImage;
	private ImageIcon imageIcon;
	
	private String pathArchivo;
	
	private JButton cancelButton;
	private JButton nuevoButton;
	private JTextField nifField;
	private JTextField nombreField;
	private JTextField fechaNac;
	private JButton changeImButton;
	
	
	public NewActorFrame(Controlador controlador, boolean nuevo, String nombreActor) {
		super(nombreActor);
		this.controlador = controlador;
		this.nuevo= nuevo;
		controlador.registerInfoActorObserver(this);
		initInfoPanel();
		fixButtons();	
		startNewActor();
	}

	private void fixButtons() {
		fixCancelButton();
		fixNuevoButton();
		fixChangeImgButton();
	}

	private void initInfoPanel() {
		JPanel panelSuperior= new JPanel(new BorderLayout());
		JPanel panelNombre = new JPanel();
		JPanel panelNif = new JPanel();
		JPanel panelFecha = new JPanel();
		
		JLabel labelNombre = new JLabel("Nombre: ");
		nombreField = new JTextField();
		nombreField.setPreferredSize(new Dimension(100, 20));
		nombreField.setEditable(false);
		panelNombre.add(labelNombre);
		panelNombre.add(nombreField);
		
		JLabel labelNif = new JLabel("NIF: ");
		nifField = new JTextField();
		nifField.setPreferredSize(new Dimension(100, 20));
		nifField.setEditable(false);
		panelNif.add(labelNif);
		panelNif.add(nifField);
		
		JLabel labelFecha = new JLabel("Fecha Nacimiento: ");
		fechaNac = new JTextField();
		fechaNac.setPreferredSize(new Dimension(100, 20));
		fechaNac.setEditable(false);
		panelFecha.add(labelFecha);
		panelFecha.add(fechaNac);
		
		panelSuperior.add(panelNombre, BorderLayout.NORTH);
		panelSuperior.add(panelNif, BorderLayout.CENTER);
		panelSuperior.add(panelFecha, BorderLayout.SOUTH);
		
		JPanel panelInferior = new JPanel();
		imageIcon = new ImageIcon(getClass().getResource("Images/images.jpg"));
		labelImage = new JLabel(imageIcon);
		
		panelInferior.add(labelImage);
		
		JPanel minipanel = new JPanel();
		cancelButton = new JButton();
		cancelButton.setText("Cerrar");
		nuevoButton = new JButton();
		nuevoButton.setText("Aceptar");
		changeImButton = new JButton();
		changeImButton.setText("Cambiar imagen");
		if(nuevo) {
			nombreField.setEditable(true);
			nifField.setEditable(true);
			fechaNac.setEditable(true);
			minipanel.add(nuevoButton);
			minipanel.add(changeImButton);
		}
		minipanel.add(cancelButton);
		
		this.add(panelSuperior , BorderLayout.NORTH);
		this.add(panelInferior , BorderLayout.CENTER);
		this.add(minipanel, BorderLayout.SOUTH);
			
	}

	private void fixChangeImgButton() {
		changeImButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser imgElegida = new JFileChooser();
				FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
				imgElegida.setFileFilter(filtroImagen);
				int opcion = imgElegida.showOpenDialog(changeImButton);

				if (opcion == JFileChooser.APPROVE_OPTION) {
					pathArchivo = imgElegida.getSelectedFile().getPath();
					 if(!pathArchivo.isEmpty()){
						 Image image = Utilidades.redimensionarImagen(new ImageIcon(pathArchivo));
				    	 imageIcon.setImage(image);
				    	 labelImage.setIcon(imageIcon);
				     }
				}
			}
		});
	}
	
	
	private void fixNuevoButton() {
		nuevoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if (fechaNac.getText().isEmpty())
					errorProducido("Introduzca una fecha con el formato (YYYY-MM-DD)");
				if (fecha()) {
					String fecha = fechaNac.getText().trim(); 
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaToken;
					try {
						fechaToken = (Date)f.parse(fecha);
						if(controlador.nuevoActor(nifField.getText(), nombreField.getText(), fechaToken , imageIcon));
						dispose();
					} 
					catch (ParseException e) {}	
				}
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
	@Override
	public void mostrarTodosLosActores(List<Actor> actores) {
	}

	@Override
	public void datosActor(String nif, String nombre, String fechaNacimiento, ImageIcon foto) {
		this.nifField.setText(nif);
		this.nombreField.setText(nombre);
		this.fechaNac.setText(fechaNacimiento);
		Image image = Utilidades.redimensionarImagen(foto);
		this.imageIcon.setImage(image);
		labelImage.setIcon(imageIcon);
	}

	public void startNewActor() {
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });	
		
	}

	private boolean fecha() {
		String[] arrayFecha = fechaNac.getText().trim().split("-");

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

	
	@Override
	public void errorProducido(String error) {
	}
}

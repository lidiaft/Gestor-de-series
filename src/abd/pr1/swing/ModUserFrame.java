package abd.pr1.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import abd.pr1.Utilidades;
import abd.pr1.controladores.Controlador;
import abd.pr1.tiposDeDatos.Usuario;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class ModUserFrame extends JFrame{
	
	private Controlador controlador;
	
	private Container newUserFrame;
	
	private JTextField passText;
	
	private JTextField repePassText;
	
	private JTextField dateText;
	private JCalendar calendar;
	
	private JLabel labelImage;
	
	private JButton acceptButton;
	private JButton cancelButton;
	private ImageIcon actualImage;
	private JButton changeImButton;
	
	private JButton previusImButton;
	private String pathArchivo;
	
	private Date dateSql;

	private Usuario user;
	
	public ModUserFrame(Controlador controlador, JButton avatarButton, ImageIcon avatarImage, Usuario user){
		super("Nuevo usuario");
		this.controlador = controlador;
		actualImage = avatarImage;
		previusImButton = avatarButton;
		pathArchivo = "";
		this.user = user;
		initNewUser();
		fixButtons();
		
		startNewUser();
	}
	

	private void initNewUser() {
		this.setPreferredSize(new Dimension(280, 440));
		this.setResizable(true);
		newUserFrame = this.getContentPane();

		JPanel infoUser = new JPanel();
		
		JLabel pass = new JLabel("Nueva contraseña:");
		passText = new JTextField();
		passText.setPreferredSize(new Dimension(100,20));
		
		JLabel repePass = new JLabel("Repite contraseña:");
		repePassText = new JTextField();
		repePassText.setPreferredSize(new Dimension(100,20));
		
		JLabel date = new JLabel("Fecha nacimiento: ");
		dateText = new JTextField();
		dateText.setPreferredSize(new Dimension(100,20));
		dateText.setEditable(false);
		dateText.setText(user.getFechaNac().toString());
		calendar = new JCalendar();
		calendar.setDate(user.getFechaNac());
		
		acceptButton = new JButton();
		acceptButton.setText("Aceptar");
		cancelButton = new JButton();
		cancelButton.setText("Cancelar");
		changeImButton = new JButton();
		changeImButton.setText("Cambiar imagen");
		
		labelImage = new JLabel(actualImage);
		
		infoUser.add(pass);
		infoUser.add(passText);
		infoUser.add(repePass);
		infoUser.add(repePassText);
		infoUser.add(date);
		infoUser.add(dateText);
		infoUser.add(calendar);
		infoUser.add(labelImage);
		infoUser.add(changeImButton);
		infoUser.add(acceptButton);
		infoUser.add(cancelButton);
	
		
		newUserFrame.add(infoUser);	
	}

	private void fixButtons() {
		fixAcceptButton();
		fixCancelButton();
		fixChangeImgButton();
		fixCalendar();
	}

	private void fixCalendar() {
		calendar.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
					if (evt.getPropertyName().compareTo("day") == 0){
						SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
						dateText.setText(formatoDeFecha.format(calendar.getDate()));
						}
					}
				});
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
				    	 actualImage.setImage(image);
				    	 labelImage.setIcon(actualImage);
				     }
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

	
	private void fixAcceptButton() {
		acceptButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){			    
				if(!pathArchivo.isEmpty()){
			    	 previusImButton.setIcon(actualImage);
			    }
				if(fechaCorrecta()){
				    if(passText.getText().equalsIgnoreCase(repePassText.getText())){
				    	controlador.modificarUsuario(passText.getText(), dateSql, actualImage);
				    	 dispose();
				    }
				    else{
				    	String message = "Verifique los campos contraseña. Estan vacios o no coindicen";
				    	JOptionPane.showOptionDialog(null, message, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);		
				    }
				}
				else{
					String message = "Fecha incorrecta. Por favor, repitala";
			    	JOptionPane.showOptionDialog(null, message, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);	
				}
			}

			@SuppressWarnings({ "static-access" })
			private boolean fechaCorrecta() {
				boolean correct = false;
				
				Calendar calAc  = Calendar.getInstance();
				int diaAc    = (calAc.get(Calendar.DATE));
				int mesAc    = (calAc.get(Calendar.MONTH) + 1);
				int anioAc   = (calAc.get(Calendar.YEAR));
				
				Calendar calNac  = calendar.getCalendar();
				int diaNac   = (calNac.get(calNac.DATE));
				int mesNac   = (calNac.get(calNac.MONTH ));
				int anioNac  = (calNac.get(calNac.YEAR));
				
				if(anioNac < anioAc)
					correct = true;
				else if(anioNac == anioAc){
					if(mesNac < mesAc)
						correct = true;
					else if(mesNac == mesAc){
						if(diaNac < diaAc)
							correct = true;
						else if(diaNac == diaAc){
							correct = true;
						}
					}
				}
				
				if(correct){
					GregorianCalendar gre = new GregorianCalendar(anioNac, mesNac, diaNac);
					dateSql = gre.getTime();
				}
				return correct;
			}
		});
	}

	
	
	public void startNewUser() {
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });	
		
	}

}

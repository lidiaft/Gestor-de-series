package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import abd.pr1.controladores.Controlador;

@SuppressWarnings({"serial", "unused"})
public class LoginFrame extends JFrame{

	private Controlador controlador;
	
	private Container mainPanel;
	private JButton acceptButton;
	private JButton	newUserButton;
	private ImageIcon icon;
	private JLabel img;
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JPanel panelDatos;
	private JTextField nickField;
	private JPasswordField passField;
	private JPanel panelImagen;

	
	
	public LoginFrame(Controlador controlador){
		super("Bienvenido a SEGUIDORES DE SERIES");
		this.controlador = controlador;
		initLoginFrame();
		
	}

	private void initLoginFrame() {
		this.setPreferredSize(new Dimension(350, 200));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		mainPanel = this.getContentPane();
		initPanelSuperior();
		initPanelInferior();
		mainPanel.add(panelSuperior, BorderLayout.CENTER);
		mainPanel.add(panelInferior, BorderLayout.SOUTH);
		
		fixedButtons();

	}

	private void fixedButtons() {
		fixAcceptButton();
		fixNewUserButton();	
	}

	private void initPanelSuperior() {
		panelSuperior = new JPanel(new BorderLayout());
		initImageIcon();
		initPanelDatos();
		panelSuperior.add(panelImagen, BorderLayout.WEST);
		panelSuperior.add(panelDatos, BorderLayout.CENTER);
		
	}

	private void initPanelDatos() {
		panelDatos = new JPanel();
		JLabel nick = new JLabel("Nick :");
		JLabel pass = new JLabel("Password :");
		
		nickField = new JTextField();
		passField = new JPasswordField();
		
		nickField.setName("nickField");
		nickField.setPreferredSize(new Dimension(200,20));
	
		passField.setName("passField");
		passField.setPreferredSize(new Dimension(200,20));
		
		panelDatos.add(nick);
		panelDatos.add(nickField);
		panelDatos.add(pass);
		panelDatos.add(passField);	
	}

	private void initPanelInferior() {
		panelInferior = new JPanel();
		initButtons();
		panelInferior.add(acceptButton, BorderLayout.WEST);
		panelInferior.add(newUserButton, BorderLayout.EAST);
		
	}


	private void initImageIcon() {
		panelImagen = new JPanel();
		img = new JLabel(); 
		icon = new ImageIcon(getClass().getResource("images/UsuariodelMes.jpg"));	
		img.setIcon(icon);
		img.setPreferredSize(new Dimension (100, 100));
		img.repaint();
		mainPanel.repaint();
		
		panelImagen.add(img);
	}

	private void initButtons() {
		acceptButton = new JButton();
		acceptButton.setName("ButtonAccept");
		acceptButton.setText("Aceptar");
		
		newUserButton = new JButton();
		newUserButton.setName("ButtonNewUser");
		newUserButton.setText("Nuevo usuario");
		
	}
	
	
	private void fixAcceptButton() {
		acceptButton.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0){
				String nick = nickField.getText();
				if(controlador.login(nick, passField.getText())){
					UserFrame userFrame = new UserFrame(controlador, nick);
					LoginFrame.this.dispose();	
				}
				else{
					String message = "No se ha encontrado el usuario. Por favor, revise el nick y contraseña";
					JOptionPane.showOptionDialog(null, message, "Error de login", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);	
				}
			}
		});
	}
	
	
	private void fixNewUserButton() {
		newUserButton.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0){
				String errorLogin = "Error de login";
				String nick = nickField.getText();
				if(nick == null || nick.isEmpty())
					showError(errorLogin, "El usuario esta vacio. Por favor, introduzca un nick");			
				
				else if(passField.getText() == null || passField.getText().isEmpty())
					showError(errorLogin, "La contraseña esta vacia. Por favor, introduzca una contraseña");
							
				else if(!controlador.buscarUsuario(nickField.getText())){
					controlador.nuevoUsuario(nickField.getText(), passField.getText());
					UserFrame userFrame = new UserFrame(controlador, nick);
					LoginFrame.this.dispose();				
				}
				
				else
					showError(errorLogin, "El usuario ya existe. Por favor, modifique sus datos");				
			}
		});
	}
	
	private void showError(String type, String message){
		JOptionPane.showOptionDialog(null, message, type, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);	
	}

	public void startLogin(){
		this.pack();						
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}

	

}

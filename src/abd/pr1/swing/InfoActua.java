package abd.pr1.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import abd.pr1.controladores.Controlador;
import abd.pr1.observables.ActuaObserver;

@SuppressWarnings("serial")
public class InfoActua extends JFrame implements ActuaObserver{

	private ActuaTable tableActua;
	private JButton closeButton;
	private JTable table;
	
	
	public InfoActua(Controlador controlador, String nombreActor) {
		super(nombreActor);
		initActua();
		controlador.registerActuaObserver(this);
		
		starActua();
	}

	private void initActua() {
		JPanel panelPrincipal= new JPanel(new BorderLayout());
		JPanel panelInferior = new JPanel();

		tableActua = new ActuaTable();
		
		closeButton = new JButton();
		closeButton.setText("Cerrar");
		closeButton.setName("ButtonClose");

		panelInferior.add(closeButton);		
		
	    table = new JTable(tableActua);
		JScrollPane scrollTable = new JScrollPane(table);
		panelPrincipal.add(scrollTable, BorderLayout.CENTER);
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		this.add(panelPrincipal);
		fixedButtons();
	}
	
	
	private void fixedButtons() {
		fixCloseButton();
	}


	private void fixCloseButton() {
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}
	
	public void starActua(){
		this.pack();							
		setLocationRelativeTo(null);			
		EventQueue.invokeLater(new Runnable(){
        	public void run() {
        		setVisible(true);
        	}
        });		
	}
	
	
	@Override
	public void datosActua(String nombreSerie, String nombreEpisodio, String personaje) {
		String[] datos = new String[] {nombreSerie, nombreEpisodio, personaje};
		tableActua.setValue(datos);
		tableActua.fireTableDataChanged();
	}

}

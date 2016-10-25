package abd.pr1;


import java.beans.PropertyVetoException;

import abd.pr1.controladores.Controlador;
import abd.pr1.seguidoresSeries.SeguidoresSeries;
import abd.pr1.swing.LoginFrame;

public class MainUser {
	
	public static void main(String[] args) {
		SeguidoresSeries segSeries;
		
		try {
			segSeries = new SeguidoresSeries("UsuarioP1", "UsuarioP1");
			Controlador controlador = new Controlador(segSeries);
			LoginFrame mainWindow 	= new LoginFrame(controlador);
			mainWindow.startLogin();	
		
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

}

package abd.pr1;


import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

import abd.pr1.controladores.Controlador;
import abd.pr1.seguidoresSeries.SeguidoresSeries;
import abd.pr1.swing.AdminFrame;

public class MainAdmin {

	public static void main(String[] args) {
		SeguidoresSeries segSeries;
		try {
			segSeries = new SeguidoresSeries("AdminP1", "AdminP1");
			Controlador controlador = new Controlador(segSeries);
			new AdminFrame(controlador);	
		} 
		catch (PropertyVetoException e) {
			String message = "Imposible acceder a la base de datos. Por favor, compruebe la conexion";
			JOptionPane.showOptionDialog(null, message, "Error de conexión", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);		
		}
	}

}

package abd.pr1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;

public class Utilidades {

	public static Blob imgToBlob(ImageIcon image) {
		int width  = image.getIconWidth();
		int height = image.getIconHeight();
		int type   = BufferedImage.TYPE_INT_RGB;
		
		BufferedImage bi = new BufferedImage(width, height,type);
		Graphics g = bi.getGraphics();
		image.paintIcon(null, g, 0, 0);
		g.dispose();
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", byteStream);
		} catch (IOException e) {}
		
		byte[] array = byteStream.toByteArray();
		
		Blob blobImage = null; 
		try {
			blobImage = new SerialBlob(array);
		} catch (SQLException e) {}
		
		return blobImage;
	}

	public static ImageIcon blobToImage(Blob blobFoto) {
		ImageIcon foto = null;
		try {
			byte[] bytesBlob = blobFoto.getBytes(1, (int)blobFoto.length());
            foto = new ImageIcon(bytesBlob);            
		} catch (SQLException e) {
			foto = new ImageIcon("Images/images.jpg");
		}
		
		
		return foto;
	}

	public static long calcularEdad( Date fechaNac) {
		long nac = fechaNac.getTime();
		long now = new Date().getTime();
		long edadMs = now - nac;
		long edad = (int)(edadMs/1000/60/60/24/365);
		return edad;
	}

	public static boolean isNumber(String string) {
		boolean isNumber = (!(string == null || string.isEmpty()));
			
	    if(isNumber){ 
	    	int i = 0;
		    if (string.charAt(0) == '-') {
		    	isNumber = (string.length() > 1); 
		        i++;	        
		    }
		    while(isNumber && i < string.length()) {
		    	isNumber = Character.isDigit(string.charAt(i));	        
		    	i++;
		    }
	    }
	    
	    return isNumber;
	}
	
	public static Image redimensionarImagen(ImageIcon imageIcon){
		Image img = imageIcon.getImage();
		Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

		return newImg;		
	}
	
	public static boolean isPositiveNumber(String string) {
			boolean is = (!(string == null || string.isEmpty()));
				
		    if(is){ 
		    	int i = 0;
			    while(is && i < string.length()) {
			    	is = Character.isDigit(string.charAt(i));	        
			    	i++;
			    }
		    }
		    
		    return is;
		}
}

package py.gov.ande.control.gateway.util;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericValidations {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericValidations.class);

	public static boolean validateIpAddress(String iedIp) {
        InetAddress address = null;
        
        try {
        	address = InetAddress.getByName(iedIp);
            return true;
        }catch(IOException e2){
        	logger.error("error de ip");
        	return false;
        }
        
	}

	public static boolean validatePortAddress(String iedPort) {
		try {
			int port = Integer.parseInt(iedPort);
			return true;
		} catch (NumberFormatException e2) {
			logger.error("Verifique que el puerto sea valido");
        	return false;
		}
		
	}

	public static boolean validateString(String iedName) {
		try {
			if(iedName == null || iedName == "" || iedName.isEmpty() || iedName.length()<1){
				return false;
			}else{
				return true;
			}
		} catch (Exception e2) {
			logger.error("Verifique el String");
			return false;
		}
		
	}
		
}



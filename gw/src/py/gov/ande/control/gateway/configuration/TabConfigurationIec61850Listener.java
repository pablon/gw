package py.gov.ande.control.gateway.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.IedManager;

public class TabConfigurationIec61850Listener implements ActionListener{
	
	TabConfigurationIec61850View theView;
	private ClientAssociation association;
	private IedManager iedModel;
	
	private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIec61850Listener.class);
	
	public TabConfigurationIec61850Listener(TabConfigurationIec61850View theView, IedManager iedModel){
		this.theView = theView;
		this.iedModel = iedModel;
	}

	/**
	 * llamar al metodo inspeccionar ied
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("actionPerformed del boton IEDExplorer");
		//leer y guardar datos del modelo
		
		// actualizar vista
		
        if(connectToIed()){
        	if(iedModel.saveIed(theView.getInputIp(), Integer.valueOf(theView.getInputPort()))){
            	JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron guardados",
          		      "Advertencia",JOptionPane.INFORMATION_MESSAGE);
        	}else{
            	JOptionPane.showMessageDialog(null,"Error: No se guardaron los datos del IED",
          		      "Advertencia",JOptionPane.WARNING_MESSAGE);
        	}
        }
		
	}
	
	/**
	 * Método que realiza una conexión al ied para comprobar el ip/port
	 * @return
	 */
	private Boolean connectToIed(){
        ClientSap clientSap = new ClientSap();

        InetAddress address = null;
        try {
            address = InetAddress.getByName(theView.getInputIp());
        } catch (UnknownHostException e1) {
        	logger.error("error de ip");
        	JOptionPane.showMessageDialog(null,"Error: Verifique que la dirección ip sea valida",
        		      "Advertencia",JOptionPane.WARNING_MESSAGE);
            e1.printStackTrace();
            return false;
        }

        int remotePort = 102;
        try {
            remotePort = Integer.parseInt(theView.getInputPort());
            if (remotePort < 1 || remotePort > 0xFFFF) {
                throw new NumberFormatException("port must be in range [1, 65535]");
            }
        } catch (NumberFormatException e1) {
        	logger.error("NumberFormatException");
            e1.printStackTrace();
            return false;
        }  
        
        clientSap.setTSelLocal(theView.getTselLocal());
        clientSap.setTSelRemote(theView.getTselRemote());
        
        TabConfigurationIec61850View eventHandler = new TabConfigurationIec61850View();
        
        try {
            association = clientSap.associate(address, remotePort, null, eventHandler);
            logger.info("association = clientSap.associate");
        } catch (IOException e1) {
            logger.error("Error connecting to server: " + e1.getMessage());
            return false;
        }
        
        ServerModel serverModel;
        try {
            serverModel = association.retrieveModel();
            association.getAllDataValues();
            logger.info("association.retrieveModel && getAllDataValues");
        } catch (ServiceError e1) {
            logger.error("Service Error requesting model.", e1);
            association.close();
            return false;
        } catch (IOException e1) {
            logger.error("Fatal IOException requesting model.", e1);
            return false;
        }
        association.disconnect();

        logger.info("Conectado con exito. Se procede a desconectar");
        return true;
	} 



}

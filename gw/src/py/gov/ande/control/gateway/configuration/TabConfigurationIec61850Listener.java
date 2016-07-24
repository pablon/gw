package py.gov.ande.control.gateway.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.BrcbManager;
import py.gov.ande.control.gateway.manager.IedManager;
import py.gov.ande.control.gateway.manager.ReportingCapabilityManager;
import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;
import py.gov.ande.control.gateway.manager.UrcbManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.ReportingCapability;
import py.gov.ande.control.gateway.util.GenericManager;

public class TabConfigurationIec61850Listener implements ActionListener{
	
	TabConfigurationIec61850View theView;
	private ClientAssociation association;
	private IedManager iedModel;
	private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIec61850Listener.class);
	private ServerModel serverModel;
	private Ied ied;
	
	public TabConfigurationIec61850Listener(TabConfigurationIec61850View theView, IedManager iedModel){
		this.theView = theView;
		this.iedModel = iedModel;
	}

	/**
	 * acción sobre el botón explorar Ied.
	 * Realiza una prueba de conexión del ied, y guarda los datos del mismo.
	 * guarda todos los tags encontrados con sus respectivos telegramAddress.
	 * identifica cuales tags corresponden a un reporte con su dataset.
	 * @author pablo
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("boton IEDExplorer");
		
        if(connectToIed()){
        	int option = JOptionPane.showConfirmDialog(null, "Se a realizado la conexión al IED. Confirmar que se quiere guardar los TAGS encontrados", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
        	if(option == JOptionPane.OK_OPTION){
	        	
	        	ied = iedModel.saveIed(theView.getInputIp(), Integer.valueOf(theView.getInputPort()));
	        	if(ied != null){
	        		Session session = GenericManager.createNewSession();
	                Transaction tx = session.beginTransaction();
	        		
	        		try {
	                	TagMonitorIec61850Manager.saveAllTagIec61850(ied, serverModel, session, tx);
	                	BrcbManager.saveAllTagWithBuffer(ied, serverModel, session, tx);
	                	//UrcbManager.saveAllTagWithOutBuffer(ied, serverModel, session, tx);
	                    tx.commit();
	            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron guardados",
	              		      "Advertencia",JOptionPane.INFORMATION_MESSAGE);            	
					} catch (Exception e2) {
						tx.rollback();
	            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED no fueron guardados",
	                		      "Advertencia",JOptionPane.ERROR_MESSAGE);            	
					} finally{
						session.close();
					}
	        		
	        	}else{
	            	JOptionPane.showMessageDialog(null,"Error: No se pudo realizar la conexión al IED",
	          		      "Advertencia",JOptionPane.ERROR_MESSAGE);
	        	}
        	}
        	disconnectToIed();
        }
        
     // actualizar vista
        
	}
	
	/**
	 * Método que realiza una conexión al ied para comprobar el ip/port
	 * @return
	 * @author pablo
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

        logger.info("Conectado con exito");
        return true;
	} 
	
	/**
	 * Metodo que realiza una desconexión al ied.
	 * @return boolean
	 * @author pablo
	 */
	public Boolean disconnectToIed(){
		try {
			association.disconnect();
			logger.info("se Desconecta del ied");
			return true;
		} catch (Exception e) {
			logger.error("Disconnect Error", e);
			return false;
		}
	}



}

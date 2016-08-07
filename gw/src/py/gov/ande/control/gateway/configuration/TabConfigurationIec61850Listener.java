package py.gov.ande.control.gateway.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaType;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.SclParseException;
import org.openmuc.openiec61850.ServerEventListener;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServerSap;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.BrcbManager;
import py.gov.ande.control.gateway.manager.IedManager;
import py.gov.ande.control.gateway.manager.ReportingCapabilityManager;
import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;
import py.gov.ande.control.gateway.manager.UrcbManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedHome;
import py.gov.ande.control.gateway.model.ReportingCapability;
import py.gov.ande.control.gateway.util.GenericManager;
import py.gov.ande.control.gateway.util.TestConnections;

public class TabConfigurationIec61850Listener extends Thread implements ActionListener, ServerEventListener{
	
	TabConfigurationView theView;
	private ClientAssociation association;
	private IedManager iedModel;
	private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIec61850Listener.class);
	private ServerModel serverModel;
	private Ied ied;
	ConfigurationController controller;
	String sclFilePath = "/home/pn/Documentos/Siprotec_WR24_F003.cid";
	private ServerSap serverSapServer = null;
	
	public TabConfigurationIec61850Listener(TabConfigurationView theView, IedManager iedModel, ConfigurationController controller){
		this.theView = theView;
		this.iedModel = iedModel;
		this.controller = controller;
	}

	/**
	 * acción sobre el botón explorar Ied.
	 * Realiza una prueba de conexión del ied, y guarda los datos del mismo.
	 * guarda todos los tags encontrados con sus respectivos telegramAddress.
	 * identifica cuales tags corresponden a un reporte con su dataset.
	 * 
	 * Acción para registrar un nuevo ied. Dos modos: 
	 * 1) conectandose directamente al ied y obteniendo su modelo.
	 * 2) importando el archivo de configuración del Ied
	 * @author pablo
	 * @date 2016-07-26
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == theView.tabConfIec61850View.btnConnectToIED){
			logger.info("btnAddIed");
	        if(connectToIed()){
	        	int option = JOptionPane.showConfirmDialog(null, "Se a realizado la conexión al IED. Confirmar que se quiere guardar los TAGS encontrados", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
	        	if(option == JOptionPane.OK_OPTION){
	
		        		try {
		        			ied = iedModel.saveIed(theView.tabConfIec61850View.getInputIp(), Integer.valueOf(theView.tabConfIec61850View.getInputPort()));
		                	TagMonitorIec61850Manager.saveAllTagIec61850(ied, serverModel);
		                	BrcbManager.saveAllTagWithBuffer(ied, serverModel);
		                	UrcbManager.saveAllTagWithOutBuffer(ied, serverModel);
		                	
		                	ied.setName(TagMonitorIec61850Manager.getFirstElement(ied.getId()));
		                	GenericManager.updateObject(ied);
	
		            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron guardados",
		              		      "Advertencia",JOptionPane.INFORMATION_MESSAGE);
		            		
		            		controller.buildTreeConfiguration();
		            		controller.rebuildMapping();
		            		
						} catch (Exception e2) {
							logger.error("Los datos del IED no fueron guardados. Error: "+e2);
		            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED no fueron guardados",
		                		      "Advertencia",JOptionPane.ERROR_MESSAGE);            	
						}
	        	}
	        	disconnectToIed();
	        }
	        
		}else if(e.getSource() == theView.tabConfIec61850View.btnExploreCid){
			logger.info("btnExploreCid");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(theView.tabConfIec61850View);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			    if(exploreCid(selectedFile.getAbsolutePath())){
		        	int option = JOptionPane.showConfirmDialog(null, "Archivo cid válido. Confirmar que se quiere guardar los TAGS encontrados", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
		        	if(option == JOptionPane.OK_OPTION){
		        		try {
		        			ied = iedModel.saveIed("127.0.0.1", 102);
		                	TagMonitorIec61850Manager.saveAllTagIec61850(ied, serverModel);
		                	BrcbManager.saveAllTagWithBuffer(ied, serverModel);
		                	UrcbManager.saveAllTagWithOutBuffer(ied, serverModel);

		                	ied.setName(TagMonitorIec61850Manager.getFirstElement(ied.getId()));
		                	GenericManager.updateObject(ied);
		                	
		            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron guardados",
		              		      "Advertencia",JOptionPane.INFORMATION_MESSAGE);
		            		
		            		controller.buildTreeConfiguration();
		            		controller.rebuildMapping();
		            		
						} catch (Exception e2) {
							logger.error("Los datos del IED no fueron guardados. Error: "+e2);
		            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED no fueron guardados",
		                		      "Advertencia",JOptionPane.ERROR_MESSAGE);            	
						}		        		
		        	}
			    }
			}
		}
	}
	
	/**
	 * Aun no se como explorar un archivo cid sin conectarme a un server.
	 * Se procede a instanciar un server 127.0.0.1 de forma provisoria hasta tener una respuesta de los creadores de la librería.
	 * Se procede a dar de baja al servidor una vez que se logre realizar la asociación.
	 * @param selectedFile
	 */
	private boolean exploreCid(String selectedFile) {
		logger.info("inicio");
		try {
			runServer(sclFilePath, 102);
		} catch (SclParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		 ClientSap clientSap = new ClientSap();
		 ClientAssociation association;  
		 TabConfigurationIec61850View eventHandler = new TabConfigurationIec61850View();
		 
        try {
            association = clientSap.associate(InetAddress.getByName("127.0.0.1"), 102, null, eventHandler);
            logger.info("Conectado con exito");
        } catch (IOException e) {
            logger.error("Error connecting to server: " + e.getMessage());
            return false;
        }  
        
        serverModel = null;
        
        try {
			serverModel = association.getModelFromSclFile(selectedFile);
		} catch (SclParseException e) {
			e.printStackTrace();
			return false;
		}

        association.disconnect();
        serverSapServer.stop();
        return true;
	}
	
	/**
	 * Método que crea una instancia de servidor IEC61850. Se debe implementar ServerEventListener
	 * @param sclFilePath
	 * @param port
	 * @throws SclParseException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void runServer(String sclFilePath, int port) throws SclParseException, IOException, InterruptedException {
		logger.info("inicio");
		serverSapServer = null;
		ServerModel serversServerModel = null;
        List<ServerSap> serverSaps = null;
        serverSaps = ServerSap.getSapsFromSclFile(sclFilePath);
        serverSapServer = serverSaps.get(0);
        serverSapServer.setPort(port);
        serverSapServer.startListening(this);
        serversServerModel = serverSapServer.getModelCopy();
        start();
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
        	String iedIp = theView.tabConfIec61850View.getInputIp();
            address = InetAddress.getByName(iedIp);
            if(IedManager.findIedForIpAddress(iedIp)){
            	JOptionPane.showMessageDialog(null,"Error: Ya se encuentra registrado un IED con la dirección ip: "+iedIp,
            		      "Advertencia",JOptionPane.WARNING_MESSAGE);
              	return false;
            }
            if(!TestConnections.testConnection(address)){
            	JOptionPane.showMessageDialog(null,"Error: Test de conexión fallida",
          		      "Advertencia",JOptionPane.WARNING_MESSAGE);
            	return false;
            }
        } catch (UnknownHostException e1) {
        	logger.error("error de ip");
        	JOptionPane.showMessageDialog(null,"Error: Verifique que la dirección ip sea valida",
        		      "Advertencia",JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
        	logger.error("error de red");
        	JOptionPane.showMessageDialog(null,"Error: Verifique que está conectado a una red válida",
        		      "Advertencia",JOptionPane.WARNING_MESSAGE);
			return false;
        }

        int remotePort = 102;
        try {
            remotePort = Integer.parseInt(theView.tabConfIec61850View.getInputPort());
            if (remotePort < 1 || remotePort > 0xFFFF) {
                throw new NumberFormatException("port must be in range [1, 65535]");
            }
        } catch (NumberFormatException e1) {
        	logger.error("NumberFormatException");
            e1.printStackTrace();
            return false;
        }  
        
        clientSap.setTSelLocal(theView.tabConfIec61850View.getTselLocal());
        clientSap.setTSelRemote(theView.tabConfIec61850View.getTselRemote());
        
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

	@Override
	public List<ServiceError> write(List<BasicDataAttribute> bdas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serverStoppedListening(ServerSap serverSAP) {
		// TODO Auto-generated method stub
		
	}
	
	/*private void rebuildMapping(){
		//intentar borrar todo el mapping,
		TabMappingView.deleteMappingIedView();
		
		//y instanciar todo de vuelta. buildMappingIedView
		TabMappingView.buildMappingIedView();
		
		//construir arbol. buildTreeMapping
		//listener del arbol. this.theView.panelMapping.addTreeListener

	}*/



}

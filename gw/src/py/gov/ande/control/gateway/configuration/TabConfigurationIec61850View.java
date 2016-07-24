package py.gov.ande.control.gateway.configuration;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaVisibleString;
import org.openmuc.openiec61850.Brcb;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.DataSet;
import org.openmuc.openiec61850.ModelNode;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.openmuc.openiec61850.Urcb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedHome;
import py.gov.ande.control.gateway.util.GenericManager;
import javax.swing.SwingConstants;

//import org.openmuc.openiec61850.ClientSap;

public class TabConfigurationIec61850View extends JPanel implements ClientEventListener {

	private static final long serialVersionUID = -386731185449901198L;
	private int tselLocal1 = 0;
    private int tselLocal2 = 0;
    private int tselRemote1 = 0;
    private int tselRemote2 = 1;
    private JTextField tselLocalField1 = new JTextField();
    private JTextField tselLocalField2 = new JTextField();
    private JTextField tselRemoteField1 = new JTextField();
    private JTextField tselRemoteField2 = new JTextField();  	
	private JLabel lblIp;
	private JTextField inputIp;
    private JTextField inputPort;
    JButton btnAgregarIED = new JButton();
    
    private ClientAssociation association;
    private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIec61850View.class);
    private static SessionFactory sessionFactory;
    
	/**
	 * Create the panel.
	 */
	public TabConfigurationIec61850View() {

		
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setVisible(false);
		
		GridBagLayout gbl_panelIec61850 = new GridBagLayout();
		gbl_panelIec61850.columnWidths = new int[]{207, 61, 55, 0};
		gbl_panelIec61850.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelIec61850.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelIec61850.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panelIec61850);
		
		JLabel lblPropiedades = new JLabel("Propiedades");
		lblPropiedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropiedades.setVerticalAlignment(SwingConstants.TOP);
		lblPropiedades.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblPropiedades = new GridBagConstraints();
		gbc_lblPropiedades.gridwidth = 3;
		gbc_lblPropiedades.fill = GridBagConstraints.BOTH;
		gbc_lblPropiedades.anchor = GridBagConstraints.NORTH;
		//gbc_lblPropiedades.gridwidth = 3;
		gbc_lblPropiedades.insets = new Insets(5, 5, 5, 5);
		gbc_lblPropiedades.gridx = 0;
		gbc_lblPropiedades.gridy = 0;
		gbc_lblPropiedades.weightx = 1;
		add(lblPropiedades, gbc_lblPropiedades);
		
		lblIp = new JLabel("Dirección IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.WEST;
		gbc_lblIp.insets = new Insets(5, 5, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 2;
		add(lblIp, gbc_lblIp);
		
		inputIp = new JTextField("10.2.28.231");
		GridBagConstraints gbc_inputIp = new GridBagConstraints();
		gbc_inputIp.gridwidth = 2;
		gbc_inputIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputIp.insets = new Insets(5, 5, 5, 5);
		gbc_inputIp.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputIp.gridx = 1;
		gbc_inputIp.gridy = 2;
		add(inputIp, gbc_inputIp);
		//inputIp.setColumns(10);
		
		JLabel lblPort = new JLabel("Puerto");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(5, 5, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 3;
		add(lblPort, gbc_lblPort);
		
		inputPort = new JTextField("102");
		GridBagConstraints gbc_inputPort = new GridBagConstraints();
		gbc_inputPort.gridwidth = 2;
		gbc_inputPort.insets = new Insets(5, 5, 5, 5);
		gbc_inputPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPort.gridx = 1;
		gbc_inputPort.gridy = 3;
		add(inputPort, gbc_inputPort);
		//inputPort.setColumns(10);
		
		//--------------------------------------------------------------------
		JLabel lblTsel = new JLabel("TSelLocal");
		GridBagConstraints gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(5, 5, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 4;
		add(lblTsel, gbc_lblTsel);		
		
		
	    tselLocalField1 = new JTextField(Integer.toString(tselLocal1));
		GridBagConstraints gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(5, 5, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 4;
		add(tselLocalField1, gbc_inputTsel);
		
	    tselLocalField2 = new JTextField(Integer.toString(tselLocal2));
		GridBagConstraints gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(5,5, 5, 5);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 4;
		add(tselLocalField2, gbc_inputTsel2);
	    
		//--------------------------------------------------------------------
		JLabel lblTselRem = new JLabel("TSelRemoto");
		gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(5, 5, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 5;
		add(lblTselRem, gbc_lblTsel);		
		
		
		tselRemoteField1 = new JTextField(Integer.toString(tselRemote1));
		gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(5, 5, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 5;
		add(tselRemoteField1, gbc_inputTsel);
		
		tselRemoteField2 = new JTextField(Integer.toString(tselRemote2));
		gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(5, 5, 5, 5);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 5;
		add(tselRemoteField2, gbc_inputTsel2);	    
   		
		btnAgregarIED = new JButton("Explorar IED");
		GridBagConstraints gbc_btnAgregarIED = new GridBagConstraints();
		gbc_btnAgregarIED.gridwidth = 2;
		gbc_btnAgregarIED.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarIED.gridx = 1;
		gbc_btnAgregarIED.gridy = 6;		
		add(btnAgregarIED, gbc_btnAgregarIED);		
		
	}
	
 /*   private void iedInspections() throws IOException {
    	System.out.println("Funcion Inspeccionar. IP: "+inputIp.getText()+", Puerto: "+inputPort.getText());
    	
        ClientSap clientSap = new ClientSap();

        InetAddress address = null;
        try {
            address = InetAddress.getByName(inputIp.getText());
        } catch (UnknownHostException e1) {
        	System.out.println("error de ip");
        	JOptionPane.showMessageDialog(null,"Error: Verifique que la dirección ip sea valida",
        		      "Advertencia",JOptionPane.WARNING_MESSAGE);
            e1.printStackTrace();
            return;
        }

        int remotePort = 102;
        try {
            remotePort = Integer.parseInt(inputPort.getText());
            if (remotePort < 1 || remotePort > 0xFFFF) {
                throw new NumberFormatException("port must be in range [1, 65535]");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }  
        
        clientSap.setTSelLocal(getTselLocal());
        clientSap.setTSelRemote(getTselRemote());

        TabConfigurationIec61850View eventHandler = new TabConfigurationIec61850View();
        
        try {
            association = clientSap.associate(address, remotePort, null, eventHandler);
        } catch (IOException e) {
            logger.error("Error connecting to server: " + e.getMessage());
            return;
        }

        ServerModel serverModel;
        try {
            serverModel = association.retrieveModel();
            association.getAllDataValues();
        } catch (ServiceError e) {
            logger.error("Service Error requesting model.", e);
            association.close();
            return;
        } catch (IOException e) {
            logger.error("Fatal IOException requesting model.", e);
            return;
        }
        
        System.out.println("Inicio de exploracion del servidor");
        try {
			association.getAllDataValues();
		} catch (ServiceError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

        try {
        	createIED(address, remotePort);	
        	JOptionPane.showMessageDialog(null,"Información: Se a guadado los datos del ied en la Base de datos",
        		      "Información",JOptionPane.INFORMATION_MESSAGE);
        	
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"Error: No se a podido guardar los datos del ied en la Base de datos",
      		      "Advertencia",JOptionPane.WARNING_MESSAGE);
		}
        
        try {
        	guardarDatosDelIedEnArchivos(serverModel);
        	JOptionPane.showMessageDialog(null,"Información: Se a guadado los datos del ied en los archivos",
      		      "Información",JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"Error: No se a podido guardar los datos del ied en los archivos",
        		      "Advertencia",JOptionPane.WARNING_MESSAGE);
		}
    }*/	
    
    protected byte[] getTselLocal() {
        tselLocal1 = parseTextField(tselLocalField1, tselLocal1);
        tselLocal2 = parseTextField(tselLocalField2, tselLocal2);    	
        return new byte[] { (byte) tselLocal1, (byte) tselLocal2 };
    }
    
    protected byte[] getTselRemote() {
        tselLocal1 = parseTextField(tselRemoteField1, tselRemote1);
        tselLocal2 = parseTextField(tselRemoteField2, tselRemote2);    	
        return new byte[] { (byte) tselLocal1, (byte) tselLocal2 };
    }
    
    private int parseTextField(JTextField field, int oldValue) {
        int value = oldValue;
        try {
            int newValue = Integer.parseInt(field.getText());
            if (newValue >= 0 && newValue <= 255) {
                value = newValue;
            }
        } catch (NumberFormatException e) {
            return oldValue;
        }
        return value;
    }

    protected String getInputIp(){
    	return inputIp.getText();
    }
    
    protected String getInputPort(){
    	return inputPort.getText();
    }
    
	@Override
	public void newReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void associationClosed(IOException e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Crea un nuevo ied
	 * como de momento no se logra obtener el nombre del ied, se coloca uno provisorio
	 * @param address
	 * @param remotePort
	 */
/*	private void createIED(InetAddress address, int remotePort){
		logger.info("metodo createIED. Address: "+address.getHostAddress()+", port: "+remotePort);
		String name = "Nombre Provisorio";
		
		Integer i;
		
		i = GenericManager.getCountObjets("From Ied"); //hasta saber porque no funciona el auto incremental
		
		Ied ied = new Ied();
		ied.setId(++i);
		ied.setIpAddress(address.getHostAddress().toString());
		ied.setPortAddress(remotePort);
		ied.setName(name);

		try {
			GenericManager.saveObject(ied);
			logger.info("luego del metodo generic manager Save()");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error, luego del metodo generic manager Save()");
		}

		i = GenericManager.getCountObjets("From Drivers"); //hasta saber porque no funciona el auto incremental
		
		Drivers driver = new Drivers();
		driver.setId(++i);
		driver.setDescription(name);
		driver.setIec101(false);
		driver.setIec61850(false);
		driver.setIed(true);
		try {
			GenericManager.saveObject(driver);
			logger.info("luego del metodo generic manager Save()");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error, luego del metodo generic manager Save()");
		}
	}*/
	
	/**
	 * metodo provisorio que explora un ied y guardar  en archivos sus propiedades
	 * @param serverModel
	 * @throws IOException
	 */
	private void guardarDatosDelIedEnArchivos(ServerModel serverModel) throws IOException{
        Files file = new Files("getBasicDataAttributes","txt");
		for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
			file.writeLine(bda.toString());
		}   
			file.closeFile();

		System.out.println("FIN DE EXPLORACION y del archivo");
		
		/*Files fileReference = new Files("Reference","txt", serverModel.getReference().toString());
			fileReference.closeFile();*/
		
		Files fileDevice = new Files("Device", "txt");
		for (ModelNode modelNode : serverModel) {
				fileDevice.writeLine(modelNode.toString());
		}
		fileDevice.closeFile();

		Files fileDataset = new Files("Datasets","txt");							//UC_SSAACTRL/LLN0.ESTADOS
		for (DataSet modelNode : serverModel.getDataSets()) {
			fileDataset.writeLine(modelNode.toString());							//lista de dataSets
		}
		fileDataset.closeFile();

		Files fileBrcbs = new Files("Brcbs", "txt"); 							//lista de reportes+dataset
		for (Brcb modelNode : serverModel.getBrcbs()) {
			//fileBrcbs.writeLine(modelNode.toString());							//UC_SSAACTRL/LLN0.brcbESTADOS2 [BR]
			fileBrcbs.writeLine("reporte: "+modelNode.getReference().toString());	//UC_SSAACTRL/LLN0.brcbESTADOS2
			fileBrcbs.writeLine("dataset: "+modelNode.getDatSet().getStringValue());//UC_SSAACTRL/LLN0$ESTADOS2
		}
		fileBrcbs.closeFile();

		Files fileUrcbs = new Files("Urcbs", "txt"); 
		for (Urcb modelNode : serverModel.getUrcbs()) {
			fileUrcbs.writeLine(modelNode.toString());
		}
		fileUrcbs.closeFile();
		
		Files fileBrcbsDataset = new Files("BrcbsDatasetTag", "txt"); 							//lista de reportes+dataset+tag
		fileBrcbsDataset.writeLine("LISTA DE BRCB, CON LA LISTA DE TAG ASOCIADO");
		for (Brcb modelNodebrcbs : serverModel.getBrcbs()) {
			fileBrcbsDataset.writeLine("reporte: "+modelNodebrcbs.getReference().toString());	//UC_SSAACTRL/LLN0.brcbESTADOS2
			fileBrcbsDataset.writeLine("    dataset: "+modelNodebrcbs.getDatSet().getStringValue());//UC_SSAACTRL/LLN0$ESTADOS2
		
			String datasetOld = modelNodebrcbs.getDatSet().getStringValue();
			String datasetNew = datasetOld.replace('$', '.');
			
			fileBrcbsDataset.writeLine("    dataset: "+datasetNew);
			
		    DataSet dataset2 = serverModel.getDataSet(datasetNew);
		    for (ModelNode modelNode2 : dataset2) {
		    	fileBrcbsDataset.writeLine("        tag: " + modelNode2.getReference().toString());//UC_SSAACTRL/GGIO2.Ind02
			}
		}
		fileBrcbsDataset.closeFile();
		
		Files fileUrcbsDataset = new Files("UrcbsDatasetTag", "txt"); 							//lista de reportes+dataset+tag
		fileUrcbsDataset.writeLine("LISTA DE URCB, CON LA LISTA DE TAG ASOCIADO");
		for (Urcb modelNodeUrcbs : serverModel.getUrcbs()) {
			fileUrcbsDataset.writeLine("reporte: "+modelNodeUrcbs.getReference().toString());	//UC_SSAACTRL/LLN0.brcbESTADOS2
			fileUrcbsDataset.writeLine("    dataset: "+modelNodeUrcbs.getDatSet().getStringValue());//UC_SSAACTRL/LLN0$ESTADOS2
		
			String datasetOld = modelNodeUrcbs.getDatSet().getStringValue();
			String datasetNew = datasetOld.replace('$', '.');
			
			fileUrcbsDataset.writeLine("    dataset: "+datasetNew);
			
		    DataSet dataset2 = serverModel.getDataSet(datasetNew);
		    for (ModelNode modelNode2 : dataset2) {
		    	fileUrcbsDataset.writeLine("        tag: " + modelNode2.getReference().toString());//UC_SSAACTRL/GGIO2.Ind02
			}
		}
		fileUrcbsDataset.closeFile();
	}
	
	void addBtnExploreIed(ActionListener listenForBtnClick){
		btnAgregarIED.addActionListener(listenForBtnClick);
		
	}
	
}

//System.out.println("texto: " + serverModel.getBrcb("UC_SSAACTRL/LLN0.brcbESTADOS2").getParent().getParent());	// UC_SSAACTRL


        


//Files fileName = new Files("getName","txt", serverModel.getName());
//fileName.closeFile();
/*        
//getServicesSupportedCalling()
System.out.println("serverModel.copy().getReference()"+serverModel.copy().getReference());		//Null
//System.out.println("serverModel.copy().getName()"+serverModel.copy().getName());				explota
System.out.println("serverModel.copy().getParent()"+serverModel.copy().getParent());			//Null
        


Files fileBrcbEstados2Child = new Files("fileBrcbEstados2Child", "txt");
Brcb brcb = serverModel.getBrcb("UC_SSAACTRL/LLN0.brcbESTADOS2");
for (ModelNode modelNode : brcb.getChildren()) {
	fileBrcbEstados2Child.writeLine(modelNode.toString());					//propiedades de un reporte
}
fileBrcbEstados2Child.closeFile();

Files fileDatasetEstados2Child = new Files("fileDatasetEstados2Child", "txt");	//lista de TAGs de un dataset
DataSet dataset = serverModel.getDataSet("UC_SSAACTRL/LLN0.ESTADOS2");
for (ModelNode modelNode : dataset) {
	//fileDatasetEstados2Child.writeLine(modelNode.toString());				//UC_SSAACTRL/GGIO2.Ind02 [ST]
	fileDatasetEstados2Child.writeLine(modelNode.getReference().toString());//UC_SSAACTRL/GGIO2.Ind02
}
fileDatasetEstados2Child.closeFile();

Files fileBrcbEstados2Dataset = new Files("fileBrcbEstados2Dataset", "txt");	//lista el dataset del reporte
Brcb brcb2 = serverModel.getBrcb("UC_SSAACTRL/LLN0.brcbESTADOS2");
BdaVisibleString modelNode = brcb2.getDatSet();
	//fileBrcbEstados2Dataset.writeLine(modelNode.getReference().toString());	//responde: UC_SSAACTRL/LLN0.brcbESTADOS2.DatSet
	//fileBrcbEstados2Dataset.writeLine(modelNode.getName());					//responde: DatSet
	fileBrcbEstados2Dataset.writeLine(modelNode.getStringValue());			//UC_SSAACTRL/LLN0$ESTADOS2  este es el indicado
fileBrcbEstados2Dataset.closeFile();

*/        

      

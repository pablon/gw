package py.gov.ande.control.gateway.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;
import javax.xml.bind.ParseConversionEvent;

import org.hibernate.mapping.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.IedManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

public class TabConfigurationIedListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIedListener.class);
	TabConfigurationView theView;
	ConfigurationController controller;

	public TabConfigurationIedListener (TabConfigurationView theView, ConfigurationController controller){
		this.theView = theView;
		this.controller = controller;
	}
	
	/**
	 * Acción que borra o actualiza un ied
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == theView.tabConfIedView.btnDeleteIed){
			logger.info("btnDeleteIed");
			int iedId = theView.tabConfIedView.iedId;
			Ied ied = GenericManager.getObjectById(Ied.class, iedId);
			if(ied != null){
				int option = JOptionPane.showConfirmDialog(null, "Confirmar que se quiere eliminar todos los datos del Ied. Este procedimiento es irreversible", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
	        	if(option == JOptionPane.OK_OPTION){
	        		if(IedManager.deleteIed(iedId)){
	            		JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron eliminados de forma satisfactoria",
		              		      "Advertencia",JOptionPane.INFORMATION_MESSAGE); 
	            		controller.buildTreeConfiguration();
	        		}else{
	            		JOptionPane.showMessageDialog(null,"Información: Hubo un error en el proceso de eliminación",
	                		      "Advertencia",JOptionPane.ERROR_MESSAGE);   
	        		}
	        	}
			}else{
        		JOptionPane.showMessageDialog(null,"Información: Hubo un error en el proceso de eliminación",
          		      "Advertencia",JOptionPane.ERROR_MESSAGE); 
			}
		}else if(e.getSource() == theView.tabConfIedView.btnSaveChanges){
			logger.info("btnSaveChanges");
        	int option = JOptionPane.showConfirmDialog(null, "Confirmar los cambios realizados", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
        	if(option == JOptionPane.OK_OPTION){
				int iedId =theView.tabConfIedView.iedId;
				String iedIp = theView.tabConfIedView.inputIp.getText();
				String iedName = theView.tabConfIedView.inputName.getText();
				String iedPort = theView.tabConfIedView.inputPort.getText();
				
				if(!IedManager.validateWidget(iedIp, iedName, iedPort)){
	        		JOptionPane.showMessageDialog(null,"Información: Existe un error en los campos ingresados",
	          		      "Advertencia",JOptionPane.ERROR_MESSAGE);   
	        		return;
				}
				
				if(IedManager.updateIed(iedIp, iedName, iedPort, iedId)){
	        		JOptionPane.showMessageDialog(null,"Información: Los datos del IED fueron actualizados de forma satisfactoria",
	            		      "Advertencia",JOptionPane.INFORMATION_MESSAGE); 
	        		controller.buildTreeConfiguration();
				}
        	}
		}
		logger.info("fin");
	}

}

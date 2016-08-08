package py.gov.ande.control.gateway.configuration;

import java.awt.Dimension;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.hibernate.criterion.Order;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.manager.IedManager;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

public class ConfigurationController {
	
	private ConfigurationView theView;
	protected DriversManager driverModel;
	private IedManager iedModel;

	public ConfigurationController(ConfigurationView theView, DriversManager driverModel){
		this.theView = theView;
		this.driverModel = driverModel;
		this.iedModel = new IedManager();
		buildTreeConfiguration();
		buildTreeMapping();
		
		this.theView.panelConf.addTreeListener(
				new TabConfigurationListener(
						this.theView.panelConf, this.driverModel));
		
		this.theView.panelConf.tabConfIec61850View.addBtnExploreIed(
				new TabConfigurationIec61850Listener(
						this.theView.panelConf, this.iedModel, this));
		
		this.theView.panelConf.tabConfIedView.addBtnIed(
				new TabConfigurationIedListener(this.theView.panelConf, this));

		this.theView.panelMapping.addTreeListener(
				new TabMappingListener(
						this.theView.panelMapping, this.driverModel));
		
	}


	/**
	 * Construye el arbol de drivers para el Tab Configuración
	 * @author pablo
	 */
	protected void buildTreeConfiguration() {
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.panelConf.treeConf.getModel().getRoot();
        DriversManager.buildTree(nodeRoot);
        theView.panelConf.treeConf.repaint();
        theView.panelConf.treeConf.updateUI();
	}
	
	/**
	 * Construye el arbol de drivers para el Tab Mapping
	 */
	private void buildTreeMapping() {
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.panelMapping.treeConf.getModel().getRoot();
        DriversManager.buildTree(nodeRoot);
     	theView.panelMapping.treeConf.repaint();
        theView.panelMapping.treeConf.updateUI();
	}
	

	
	/**
	 * Método que reconstruye la vista de la pestaña mapping.
	 * Borra el mapping de ied actual. reconstruye las tablas.
	 * reconstruye el arbol de drivers. y finalmente agrega el listener al arbol
	 * @author pablo
	 * @date 2016-08-02
	 */
	protected void rebuildMapping(){
		theView.panelMapping.deleteMappingIedView();
		theView.panelMapping.buildMappingIedView();
		buildTreeMapping();
		this.theView.panelMapping.addTreeListener(
				new TabMappingListener(
						this.theView.panelMapping, this.driverModel));
	}

}

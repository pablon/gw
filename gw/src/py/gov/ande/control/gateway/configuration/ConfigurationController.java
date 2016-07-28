package py.gov.ande.control.gateway.configuration;

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
		
	}

	/**
	 * Construye el arbol de drivers para el Tab Configuración
	 * @author pablo
	 */
	public void buildTreeConfiguration() {
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.panelConf.treeConf.getModel().getRoot();

        buildTree(nodeRoot);
    	/*Se refresca el arbol*/
        theView.panelConf.treeConf.repaint();
        theView.panelConf.treeConf.updateUI();
	}
	
	/**
	 * Construye el arbol de drivers para el Tab Mapping
	 */
	public void buildTreeMapping() {
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.panelMapping.treeConf.getModel().getRoot();
        
        buildTree(nodeRoot);
 
    	/*Se refresca el arbol*/
        theView.panelMapping.treeConf.repaint();
        theView.panelMapping.treeConf.updateUI();
	}
	
	/**
	 * 	Construye el arbol de drivers para el Tab Configuración, así como la lista de IED
	 * @param nodeRoot
	 */
	public void buildTree(DefaultMutableTreeNode nodeRoot){
        DefaultMutableTreeNode subestacion = null, iec61850 = null, ied = null, iec101, temp;
        if(nodeRoot.getChildCount()>0){
        	nodeRoot.removeAllChildren();
        }
        
        List<Drivers> driverList = GenericManager.getListBasedOnCriteria("From Drivers drivers ORDER BY "
        		+ "drivers.subestation DESC,"
        		+ "drivers.iec61850 DESC,"
        		+ "drivers.iec101 DESC");
        
        /* se explora lista de drivers*/
        for (Drivers drivers : driverList) {
        	if(drivers.getSubestation()){
        		subestacion = new DefaultMutableTreeNode(drivers.getDescription());
        		nodeRoot.add(subestacion);
        	}else if(drivers.getIec61850()){
        		iec61850 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec61850);
        	}else if(drivers.getIec101()){
        		iec101 = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(iec101);
        	}else{
        		temp = new DefaultMutableTreeNode(drivers.getDescription());
        		subestacion.add(temp);
        	} 
		}
    
        /*Se explora lista de ied's */
    	List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
    	for (Ied ieds : iedList) {
    		ied = new DefaultMutableTreeNode(ieds.getName());
    		iec61850.add(ied);
		}

	}

}

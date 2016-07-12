package py.gov.ande.control.gateway.configuration;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.hibernate.criterion.Order;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

public class ConfigurationController {
	
	protected ConfigurationView theView;

	public ConfigurationController(ConfigurationView theView){
		this.theView = theView;
		buildTree();
		
	}

	/**
	 * Construye el arbol de drivers, así como la lista de IED
	 * @author pablo
	 */
	private void buildTree() {
        DefaultMutableTreeNode subestacion = null, iec61850 = null, ied = null, iec101, temp;
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.panelConf.treeConf.getModel().getRoot();

        List<Drivers> driverList = GenericManager.getListBasedOnCriteria("From Drivers drivers ORDER BY "
        		+ "drivers.subestation DESC,"
        		+ "drivers.iec61850 DESC,"
        		+ "drivers.ied DESC,"
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

    	/*Se refresca el arbol*/
        theView.panelConf.treeConf.repaint();
        theView.panelConf.treeConf.updateUI();
	}
}

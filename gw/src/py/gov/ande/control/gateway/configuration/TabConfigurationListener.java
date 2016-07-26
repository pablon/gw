package py.gov.ande.control.gateway.configuration;

import java.util.List;
import java.util.Objects;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.hibernate.criterion.Order;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.util.GenericManager;

public class TabConfigurationListener implements TreeSelectionListener{

	private TabConfigurationView theView;
	protected DriversManager driverModel;
	
	public TabConfigurationListener(TabConfigurationView theView, DriversManager driverModel){
		this.theView = theView;
		this.driverModel = driverModel;
	} 
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		driverModel.valueChangedOfTheTree(
				(DefaultMutableTreeNode) theView.treeConf.getLastSelectedPathComponent()
				);
		theView.valueChangedOfTheTree(
				driverModel.getValueChangedOfTheTree()
				);
	}

}

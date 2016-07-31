package py.gov.ande.control.gateway.configuration;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import py.gov.ande.control.gateway.manager.DriversManager;

public class TabMappingListener  implements TreeSelectionListener{

	private TabMappingView theView;
	private DriversManager driverModel;

	public TabMappingListener(TabMappingView theView, DriversManager driverModel) {
		this.theView = theView;
		this.driverModel = driverModel;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//actualiza el modelo
		driverModel.valueChangedOfTheTree(
				(DefaultMutableTreeNode) theView.treeConf.getLastSelectedPathComponent()
				);
		//actualiza la vista
		theView.valueChangedOfTheTree(
				driverModel.getValueChangedOfTheTree()
				);
	}

}

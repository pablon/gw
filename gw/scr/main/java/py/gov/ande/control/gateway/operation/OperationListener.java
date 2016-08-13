package py.gov.ande.control.gateway.operation;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.model.DriversOperation;
import py.gov.ande.control.gateway.model.IedOperation;

public class OperationListener implements TreeSelectionListener {

	private static final Logger logger = LoggerFactory.getLogger(OperationListener.class);
	private OperationView theView;
	private DriversManager driverModel;

	public OperationListener(OperationView theView, DriversManager driverModel) {
		this.theView = theView;
		this.driverModel = driverModel;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		driverModel.valueChangedOfTheTreeOperation(
				(DefaultMutableTreeNode) theView.treeConf.getLastSelectedPathComponent()
				);
		theView.valueChangedOfTheTree(
				driverModel.getValueChangedOfTheTree()
				);
	}

}

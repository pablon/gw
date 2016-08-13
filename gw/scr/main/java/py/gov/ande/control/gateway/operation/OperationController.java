package py.gov.ande.control.gateway.operation;

import javax.swing.tree.DefaultMutableTreeNode;

import py.gov.ande.control.gateway.manager.DriversManager;

public class OperationController {

	private OperationView theView;
	private DriversManager driver;

	public OperationController(OperationView theView, DriversManager driver) {
		this.theView = theView;
		this.driver = driver;
		
		buildTree();
		
		this.theView.addTreeListener(
				new OperationListener(
						this.theView, this.driver));
		
		this.theView.gatewayView.addBtnListener(
				new GatewayListener(this.theView, this.driver)
				);
		
		}
	
	/**
	 * Método que construye el árbol de drivers.
	 * @author Pablo
	 * @date 2016-08-08
	 */
	private void buildTree() {
		DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.treeConf.getModel().getRoot();
		DriversManager.buildTreeOperation(nodeRoot);
		theView.treeConf.repaint();
		theView.treeConf.updateUI();
	}
	
}

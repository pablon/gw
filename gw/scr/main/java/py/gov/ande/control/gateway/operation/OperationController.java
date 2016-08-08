package py.gov.ande.control.gateway.operation;

import javax.swing.tree.DefaultMutableTreeNode;

import py.gov.ande.control.gateway.manager.DriversManager;

public class OperationController {

	private OperationView theView;

	public OperationController(OperationView theView) {
		this.theView = theView;
		
		buildTree();
		
		this.theView.addTreeListener(
				new OperationListener(
						this.theView));
		}
	
	/**
	 * Método que construye el árbol de drivers.
	 * @author Pablo
	 * @date 2016-08-08
	 */
	private void buildTree() {
		DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) theView.treeConf.getModel().getRoot();
		DriversManager.buildTree(nodeRoot);
		theView.treeConf.repaint();
		theView.treeConf.updateUI();
	}
	
}

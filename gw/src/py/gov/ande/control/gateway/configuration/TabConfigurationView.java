package py.gov.ande.control.gateway.configuration;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import py.gov.ande.control.gateway.model.DriversManager;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

public class TabConfigurationView extends JPanel {

	protected JScrollPane scrollPaneConf;
	protected JScrollPane scrollPaneDetails;
	protected PanelIec61850 panelIec61850 = new PanelIec61850();
	protected JTree treeConf;

	public TabConfigurationView() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{181, 79, 3, 0};
		gridBagLayout.rowHeights = new int[]{403, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
				
		scrollPaneConf = new JScrollPane();
		GridBagConstraints gbc_scrollPaneConf = new GridBagConstraints();
		gbc_scrollPaneConf.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneConf.anchor = GridBagConstraints.WEST;
		gbc_scrollPaneConf.gridx = 0;
		gbc_scrollPaneConf.gridy = 0;
		add(scrollPaneConf, gbc_scrollPaneConf);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Gateway");
		treeConf = new JTree(top);		
		treeConf.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		scrollPaneConf.setViewportView(treeConf);

		scrollPaneDetails = new JScrollPane();
		GridBagConstraints gbc_scrollPaneDetails = new GridBagConstraints();
		gbc_scrollPaneDetails.gridheight = 2;
		gbc_scrollPaneDetails.anchor = GridBagConstraints.EAST;
		gbc_scrollPaneDetails.gridx = 1;
		gbc_scrollPaneDetails.gridy = 0;
		add(scrollPaneDetails, gbc_scrollPaneDetails);
		
		//scrollPaneDetails.setViewportView(panelIec61850);
		//panelIec61850.setVisible(true);
		
	}
	
	/**
	 * metodo para eventos del arbol de drivers.
	 * @param listenForTreeClick
	 */
	void addTreeListener(TreeSelectionListener listenForTreeClick){
		System.out.println("AddTreeListener");
		treeConf.addTreeSelectionListener(listenForTreeClick);
	}

	void displayErrorMessage(String errorMessage){
		
		JOptionPane.showMessageDialog(this, errorMessage);
		
	}

	/**
	 * mostrará el panel de detalles según se le haya dado click en el arbol de drivers.
	 * @param valueChangedOfTheTree
	 */
	public void valueChangedOfTheTree(DriversManager driverModel) {
		System.out.println("TabConfigurationView.valueChange");
		if(driverModel.getValueChangedOfTheTree().getIec61850()){
			System.out.println("getIec61850 true");
			scrollPaneDetails.setViewportView(panelIec61850);
			panelIec61850.setVisible(true);
//		}else{
//			scrollPaneDetails.removeAll();
		}
		
		//scrollPaneDetails.repaint();
		//scrollPaneDetails.updateUI();
		
	}
}

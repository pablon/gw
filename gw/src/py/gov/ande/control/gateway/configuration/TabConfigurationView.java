package py.gov.ande.control.gateway.configuration;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import py.gov.ande.control.gateway.manager.DriversManager;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import javax.swing.border.BevelBorder;

public class TabConfigurationView extends JPanel {

	protected JScrollPane scrollPaneConf;
	protected JScrollPane scrollPaneDetails;
	protected TabConfigurationIec61850View tabConfIec61850View = new TabConfigurationIec61850View();
	protected TabConfigurationIec101View tabConfIec101View = new TabConfigurationIec101View();
	protected TabConfigurationIedView tabConfIedView = new TabConfigurationIedView();
	public JTree treeConf;
	private GridBagLayout gridBagLayout;
	protected JPanel panelDetails = new JPanel(); 

	public TabConfigurationView() {

		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
				
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Gateway");
		treeConf = new JTree(top);
		ToolTipManager.sharedInstance().registerComponent(treeConf);
		treeConf.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		scrollPaneConf = new JScrollPane(treeConf);
		GridBagConstraints gbc_scrollPaneConf = new GridBagConstraints();
		gbc_scrollPaneConf.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneConf.anchor = GridBagConstraints.WEST;
		gbc_scrollPaneConf.gridx = 0;
		gbc_scrollPaneConf.gridy = 0;
		gbc_scrollPaneConf.weightx = 0.2;
		gbc_scrollPaneConf.weighty = 1;
		gbc_scrollPaneConf.insets = new Insets(5, 5, 5, 5);
		add(scrollPaneConf, gbc_scrollPaneConf);
		
		scrollPaneDetails = new JScrollPane();
		GridBagConstraints gbc_scrollPaneDetails = new GridBagConstraints();
		gbc_scrollPaneDetails.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDetails.anchor = GridBagConstraints.NORTHWEST;
		gbc_scrollPaneDetails.gridx = 1;
		gbc_scrollPaneDetails.gridy = 0;
		gbc_scrollPaneDetails.weightx = 0.8;
		gbc_scrollPaneDetails.weighty = 1;
		add(scrollPaneDetails, gbc_scrollPaneDetails);

		panelDetails.add(tabConfIec61850View);
		panelDetails.add(tabConfIec101View);
		panelDetails.add(tabConfIedView);
		scrollPaneDetails.add(panelDetails);
		tabConfIec61850View.setVisible(false);
		tabConfIec101View.setVisible(false);
		tabConfIedView.setVisible(false);
		
	}
	
	/**
	 * metodo para eventos del arbol de drivers.
	 * @param listenForTreeClick
	 */
	void addTreeListener(TreeSelectionListener listenForTreeClick){
		//System.out.println("AddTreeListener");
		treeConf.addTreeSelectionListener(listenForTreeClick);
	}

	void displayErrorMessage(String errorMessage){
		
		JOptionPane.showMessageDialog(this, errorMessage);
		
	}

	/**
	 * Acción que mostrará el panel de detalles según se le haya dado click en el arbol de drivers..
	 * Muestra las propiedades del driver iec61850, 101, e Ied.
	 * @param valueChangedOfTheTree
	 * @author pablo
	 * @date 2016-07-26
	 */
	public void valueChangedOfTheTree(DriversManager driverModel) {
		if(driverModel.getValueChangedOfTheTree().getIec61850()){
			scrollPaneDetails.setViewportView(tabConfIec61850View);
			tabConfIec61850View.setVisible(true);
		}else if(driverModel.getValueChangedOfTheTree().getIec101()){
			scrollPaneDetails.setViewportView(tabConfIec101View);
			tabConfIec101View.setVisible(true);
		}else if(driverModel.getValueChangedOfTheTree().getIed()){
			refreshJTextFieldsIed(driverModel);
			scrollPaneDetails.setViewportView(tabConfIedView);
			tabConfIedView.setVisible(true);
		}
		else{
			//scrollPaneDetails.removeAll();
			tabConfIec61850View.setVisible(false);
			tabConfIec101View.setVisible(false);
			tabConfIedView.setVisible(false);
		}
		//scrollPaneDetails.repaint();
		//scrollPaneDetails.updateUI();
	}

	/**
	 * Método utilizado para refrescar los campos de la vista de las propiedades de los IEDs
	 * @param driverModel
	 * @author pablo
	 * @date 2016-07-26
	 */
	private void refreshJTextFieldsIed(DriversManager driverModel) {
		tabConfIedView.inputIp.setText(driverModel.getIedIp());
		tabConfIedView.inputPort.setText(driverModel.getIedPort().toString());
		tabConfIedView.inputName.setText(driverModel.getIedName());
		tabConfIedView.iedId = driverModel.getIedId();
	}
	
	
}

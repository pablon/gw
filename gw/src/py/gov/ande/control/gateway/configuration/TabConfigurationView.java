package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;

public class TabConfigurationView extends JPanel {

	protected JScrollPane scrollPaneConf;
	protected JScrollPane scrollPaneDetails;
	protected PanelIec61850 panelIec61850;
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

	}

}

package py.gov.ande.control.gateway.configuration;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class TabView extends JPanel {

	private GridBagLayout gridBagLayout;
	public JTree treeConf;
	protected JScrollPane scrollPaneConf;
	protected JScrollPane scrollPaneDetails;
	
	public TabView() {

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
		
		
	}

}

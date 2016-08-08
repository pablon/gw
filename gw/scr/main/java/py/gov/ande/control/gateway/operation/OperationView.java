package py.gov.ande.control.gateway.operation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.JScrollPane;

public class OperationView extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(OperationView.class);
	private GridBagLayout gbl;
	private JPanel topPanel;
	protected JTree treeConf;
	private JScrollPane scrollPaneConf;
	private JScrollPane scrollPaneDetails;

	public OperationView() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            logger.error("Class not found: ", e);
        } catch (InstantiationException e) {
            logger.error("Object not instantiated: ", e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal acces: ", e);
        } catch (UnsupportedLookAndFeelException e) {
            logger.error("Unsupported LookAndFeel: ", e);
        }		
		
        gbl=new GridBagLayout();
        this.getContentPane().setLayout(gbl);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
	
        GridBagConstraints topPanelConstraint = new GridBagConstraints();
        topPanelConstraint.fill = GridBagConstraints.HORIZONTAL;
        topPanelConstraint.gridwidth = GridBagConstraints.REMAINDER;
        topPanelConstraint.gridx = 0;
        topPanelConstraint.gridy = 0;
        topPanelConstraint.insets = new Insets(5, 5, 5, 5);
        topPanelConstraint.anchor = GridBagConstraints.NORTH;
        gbl.setConstraints(topPanel, topPanelConstraint);
        getContentPane().add(topPanel);
        
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
        
		setSize(850, 650);
		setMinimumSize(new Dimension(420, 420));
		
	}

}

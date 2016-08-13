package py.gov.ande.control.gateway.operation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.configuration.TabMappingIedView;
import py.gov.ande.control.gateway.configuration.TabMappingTagsListener;
import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.util.GenericManager;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.JScrollPane;

public class OperationView extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(OperationView.class);
	protected GridBagLayout gbl;
	protected JPanel topPanel;
	protected JTree treeConf;
	protected JScrollPane scrollPaneConf;
	protected JScrollPane scrollPaneDetails;
	protected GatewayView gatewayView;
	protected IedView[] iedView;
	protected JPanel panelDetails = new JPanel();
	private List<IedOperation> iedList; 

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
        getContentPane().setLayout(gbl);
        
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
		getContentPane().add(scrollPaneConf, gbc_scrollPaneConf);
		
		scrollPaneDetails = new JScrollPane(new JPanel());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.8;
		gbc.weighty = 1;
		getContentPane().add(scrollPaneDetails, gbc);
		
		((JPanel)scrollPaneDetails.getViewport().getView()).add(gatewayView = new GatewayView());
		//((JPanel)scrollPaneDetails.getViewport().getView()).add(iedView = new IedView());
		
		buildIedView();
				
        gatewayView.setVisible(false);
        //iedView.setVisible(false);
		setSize(850, 650);
		setMinimumSize(new Dimension(420, 420));
		
	}
	
	/**
	 * Método que crea la vista de cada ied
	 * @author Pablo
	 * @date 2016-08-11
	 */
	protected void buildIedView(){
		iedList = GenericManager.getAllObjects(IedOperation.class, Order.asc("id"));
		iedView = new IedView[iedList.size()];
		int i = 0;
		for (IedOperation ied : iedList) {
			((JPanel)scrollPaneDetails.getViewport().getView()).add(iedView[i] = new IedView(ied));
			iedView[i].setVisible(false);
			//iedView[i].addBtnUpdateTags( new TabMappingTagsListener(this) );
			i++;
		}
	}
	
	/**
	 * Método que agrega un listener al tree
	 * @param listenForTreeClick
	 * @author Pablo
	 * @date 2016-08-08
	 */
	void addTreeListener(TreeSelectionListener listenForTreeClick){
		treeConf.addTreeSelectionListener(listenForTreeClick);
	}

	/**
	 * Método que muestra las vistas de la derecha según corresponda el click.
	 * Primeramente oculto todas las vistas
	 * @param model
	 * @author Pablo
	 * @date 2016-08-11
	 */
	public void valueChangedOfTheTree(DriversManager model) {
		List<IedOperation> iedList = GenericManager.getAllObjects(IedOperation.class, Order.asc("id"));
		for (int i = 0; i < iedList.size(); i++) {
			iedView[i].setVisible(false);	
		}
		gatewayView.setVisible(false);
		
		if(model.getValueChangedOfTheTree().getIed()){
			iedView[model.getValueChangedOfTheTree().getArrayId()].setVisible(true);
		}else if(model.getValueChangedOfTheTree().getGw()){
			gatewayView.setVisible(true);
		}
	}

}

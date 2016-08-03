package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.List;

public class TabMappingView extends JPanel {
	
	private GridBagLayout gridBagLayout;
	protected JTree treeConf;
	private JScrollPane scrollPaneConf;
	private JScrollPane scrollPaneDetails;
	protected JPanel panelDetails = new JPanel(); 
	public TabMappingIedView [] tabMappingIedView;
	private static final Logger logger = LoggerFactory.getLogger(TabMappingView.class);
	List<Ied> iedList;

	/**
	 * Create the panel.
	 */
	public TabMappingView() {
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
		
		scrollPaneDetails = new JScrollPane(new JPanel());
		GridBagConstraints gbc_scrollPaneDetails = new GridBagConstraints();
		gbc_scrollPaneDetails.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDetails.anchor = GridBagConstraints.NORTHWEST;
		gbc_scrollPaneDetails.gridx = 1;
		gbc_scrollPaneDetails.gridy = 0;
		gbc_scrollPaneDetails.weightx = 0.8;
		gbc_scrollPaneDetails.weighty = 1;
		add(scrollPaneDetails, gbc_scrollPaneDetails);
		
		buildMappingIedView();
	}
	
	/**
	 * Método que construye la vista del Mapping para los Ied's
	 * @author pablo
	 * @date 2016-08-02
	 */
	public void buildMappingIedView(){
		iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		tabMappingIedView = new TabMappingIedView[iedList.size()];
		int i = 0;
		for (Ied ied : iedList) {
			((JPanel)scrollPaneDetails.getViewport().getView()).add(tabMappingIedView[i] = new TabMappingIedView(ied));
			tabMappingIedView[i].setVisible(false);
			logger.info("vista del ied creado. i: "+i+", class: "+tabMappingIedView[i].getClass());
			i++;
		}
	}
	
	/**
	 * Método que libera memoria de la vista de las tablas del mapping
	 * @author pablo
	 * @date 2016-08-02
	 */
	public void deleteMappingIedView(){
		((JPanel)scrollPaneDetails.getViewport().getView()).removeAll();
		int i = 0;
		for (Ied ied : iedList) {
			tabMappingIedView[i] = null;
			i++;
		}
		tabMappingIedView = null;
	}

	/**
	 * Método que agrega un listener al arbol de mapping.
	 * Esto se ejecuta una sola vez.
	 * @param tabMappingListener
	 * @author Pablo
	 * @date 2016-07-31
	 */
	public void addTreeListener(TabMappingListener tabMappingListener) {
		treeConf.addTreeSelectionListener(tabMappingListener);
		
	}

	/**
	 * Método que captura los eventos del arbol.
	 * Obtiene la lista de ied, oculta sus vistas,
	 * y finalmente hace visible el que corresponde.
	 * @param model
	 */
	public void valueChangedOfTheTree(DriversManager model) {
		//logger.info("ArrayId: "+model.getValueChangedOfTheTree().getArrayId());
		List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		for (int i = 0; i < iedList.size(); i++) {
			tabMappingIedView[i]
					.setVisible(false);	
		}
		
		if(model.getValueChangedOfTheTree().getIed()){
			tabMappingIedView[model.getValueChangedOfTheTree().getArrayId()]
					.setVisible(true);
			tabMappingIedView[model.getValueChangedOfTheTree().getArrayId()]
					.invalidate();
			tabMappingIedView[model.getArrayId()].setPreferredSize(tabMappingIedView[model.getArrayId()].getParent().getSize());
			
		}		
	}

}

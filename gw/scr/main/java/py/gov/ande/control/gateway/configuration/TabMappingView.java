package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.util.GenericManager;

import java.awt.Dimension;
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
		GridBagConstraints gbc = new GridBagConstraints();
				
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Gateway");
		treeConf = new JTree(top);
		ToolTipManager.sharedInstance().registerComponent(treeConf);
		treeConf.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		scrollPaneConf = new JScrollPane(treeConf);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(scrollPaneConf, gbc);
		
		scrollPaneDetails = new JScrollPane(new JPanel());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
		add(scrollPaneDetails, gbc);
		
		buildMappingIedView();
	}
	
	/**
	 * Método que construye la vista del Mapping para los Ied's
	 * @author pablo
	 * @date 2016-08-02
	 */
	protected void buildMappingIedView(){
		iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		tabMappingIedView = new TabMappingIedView[iedList.size()];
		int i = 0;
		for (Ied ied : iedList) {
			((JPanel)scrollPaneDetails.getViewport().getView()).add(tabMappingIedView[i] = new TabMappingIedView(ied));
			tabMappingIedView[i].setVisible(false);
			tabMappingIedView[i].addBtnUpdateTags( new TabMappingTagsListener(this) );
			i++;
		}
	}
	
	/**
	 * Método que libera memoria de la vista de las tablas del mapping
	 * @author pablo
	 * @date 2016-08-02
	 */
	protected void deleteMappingIedView(){
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
	protected void addTreeListener(TabMappingListener tabMappingListener) {
		treeConf.addTreeSelectionListener(tabMappingListener);
		
	}

	/**
	 * Método que captura los eventos del arbol.
	 * Obtiene la lista de ied, oculta sus vistas,
	 * y finalmente hace visible el que corresponde.
	 * @param model
	 */
	public void valueChangedOfTheTree(DriversManager model) {
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
		}		
	}

}

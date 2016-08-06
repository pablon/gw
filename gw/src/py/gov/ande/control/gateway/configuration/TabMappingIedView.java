package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.ListUtil;
import py.gov.ande.control.gateway.util.MyTableModel;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class TabMappingIedView extends JPanel {

	private Ied ied;
	protected JButton btnUpdateTags;
	protected final JTable table;
	protected MyTableModel myTableModel;
	private String[] columnNames;
	private static final Logger logger = LoggerFactory.getLogger(TabMappingIedView.class);

	protected String[] getColumnNames() {
		return columnNames;
	}

	protected void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public TabMappingIedView(Ied ied) {
		this.ied = ied;
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		setColumnNames( TagMonitorIec61850Manager.getColumnNames());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel lblName = new JLabel("Mapping del Ied "+ied.getName());
		lblName.setFont(new Font("Dialog", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
		add(lblName, gbc);
		
		List<TagMonitorIec61850> tags = TagMonitorIec61850Manager.getAllObjects(ied);
		//logger.info(tags.isEmpty()?"tag isEmpty":"tag is not empty");
		
		
		Object[] [] data = ListUtil.ListToArray(tags,getColumnNames());
		myTableModel = new MyTableModel(data, getColumnNames());
		table = new JTable(myTableModel);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(750, 450));

        //-------definir tamaño de columnas----
		TableColumn column = null;
		for (int i = 0; i < getColumnNames().length; i++) {
			column = table.getColumnModel().getColumn(i);
			if( getColumnNames()[i] == "telegramAddress"){
				column.setPreferredWidth(2000);
			}else{
				column.setPreferredWidth(500);
			}
		}
        //-------------------------------------
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
		add(scrollPane, gbc);
		
		btnUpdateTags = new JButton("Actualizar Tags");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
		add(btnUpdateTags, gbc);
	}

	/**
	 * Método que es llamado una sola vez, 
	 * con la finalidad de agregar un Listener al botón de Actualizar los tags a la base de datos
	 * @param listenForBtnClick
	 * @author Pablo
	 * @date 2016-08-03
	 */
	public void addBtnUpdateTags(ActionListener listenForBtnClick){
		btnUpdateTags.addActionListener(listenForBtnClick);
	}

}

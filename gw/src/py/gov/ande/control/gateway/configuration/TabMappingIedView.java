package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

public class TabMappingIedView extends JPanel {

	private Ied ied;

	public TabMappingIedView(Ied ied) {
		this.ied = ied;
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Mapeo del Ied "+ied.getName());
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		List<TagMonitorIec61850> tags = TagMonitorIec61850Manager.getAllObjects(ied);
		Object[] [] data = ListUtil.ListToArray(tags, TagMonitorIec61850Manager.getColumnNames());
		MyTableModel myTableModel = new MyTableModel(data, TagMonitorIec61850Manager.getColumnNames());
		final JTable table = new JTable(myTableModel);

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 1;
		gbc_scroll.gridwidth = 3;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.anchor = GridBagConstraints.PAGE_END;
		add(scrollPane, gbc_scroll);
	}


}

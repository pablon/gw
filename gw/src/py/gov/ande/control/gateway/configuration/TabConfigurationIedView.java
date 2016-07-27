package py.gov.ande.control.gateway.configuration;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.gov.ande.control.gateway.manager.DriversManager;
import javax.swing.JButton;

public class TabConfigurationIedView extends JPanel {

	protected JTextField inputIp;
	protected JTextField inputPort;
	protected JTextField inputName;
	protected JButton btnDeleteIed;
	protected JButton btnSaveChanges;
	/**
	 * El campo id de la base de dato para la tabla Ied
	 */
	protected Integer iedId;
	
	public TabConfigurationIedView() {
		
		iedId = 0;
		
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setVisible(false);
		
		GridBagLayout gbl_panelIed = new GridBagLayout();
		gbl_panelIed.columnWidths = new int[]{207, 61, 55, 0};
		gbl_panelIed.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelIed.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelIed.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panelIed);
		
		JLabel lblPropiedades = new JLabel("Propiedades");
		lblPropiedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropiedades.setVerticalAlignment(SwingConstants.TOP);
		lblPropiedades.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblPropiedades = new GridBagConstraints();
		gbc_lblPropiedades.gridwidth = 3;
		gbc_lblPropiedades.fill = GridBagConstraints.BOTH;
		gbc_lblPropiedades.anchor = GridBagConstraints.NORTH;
		gbc_lblPropiedades.insets = new Insets(5, 5, 5, 0);
		gbc_lblPropiedades.gridx = 0;
		gbc_lblPropiedades.gridy = 0;
		gbc_lblPropiedades.weightx = 1;
		add(lblPropiedades, gbc_lblPropiedades);
		
		JLabel lblName = new JLabel("Nombre");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(5, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		add(lblName, gbc_lblName);
		
		inputName = new JTextField();
		GridBagConstraints gbc_inputName = new GridBagConstraints();
		gbc_inputName.gridwidth = 2;
		gbc_inputName.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputName.insets = new Insets(5, 5, 5, 0);
		gbc_inputName.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputName.gridx = 1;
		gbc_inputName.gridy = 2;
		add(inputName, gbc_inputName);
		
		JLabel lblIp = new JLabel("Dirección IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.WEST;
		gbc_lblIp.insets = new Insets(5, 5, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 3;
		add(lblIp, gbc_lblIp);
		
		inputIp = new JTextField();
		GridBagConstraints gbc_inputIp = new GridBagConstraints();
		gbc_inputIp.gridwidth = 2;
		gbc_inputIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputIp.insets = new Insets(5, 5, 5, 0);
		gbc_inputIp.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputIp.gridx = 1;
		gbc_inputIp.gridy = 3;
		add(inputIp, gbc_inputIp);
		
		JLabel lblPort = new JLabel("Puerto");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(5, 5, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 4;
		add(lblPort, gbc_lblPort);
		
		inputPort = new JTextField();
		GridBagConstraints gbc_inputPort = new GridBagConstraints();
		gbc_inputPort.gridwidth = 2;
		gbc_inputPort.insets = new Insets(5, 5, 5, 0);
		gbc_inputPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPort.gridx = 1;
		gbc_inputPort.gridy = 4;
		add(inputPort, gbc_inputPort);
		
		JLabel lblCantidadDeTags = new JLabel("Cantidad de Tags");
		GridBagConstraints gbc_lblCantidadDeTags = new GridBagConstraints();
		gbc_lblCantidadDeTags.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidadDeTags.gridx = 0;
		gbc_lblCantidadDeTags.gridy = 6;
		add(lblCantidadDeTags, gbc_lblCantidadDeTags);
		
		JLabel lblTags = new JLabel("0");
		GridBagConstraints gbc_lblTags = new GridBagConstraints();
		gbc_lblTags.insets = new Insets(0, 0, 5, 5);
		gbc_lblTags.gridx = 1;
		gbc_lblTags.gridy = 6;
		add(lblTags, gbc_lblTags);
		
		JLabel lblCaan = new JLabel("Reportes con Buffer");
		GridBagConstraints gbc_lblCaan = new GridBagConstraints();
		gbc_lblCaan.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaan.gridx = 0;
		gbc_lblCaan.gridy = 7;
		add(lblCaan, gbc_lblCaan);
		
		JLabel lblReportWithB = new JLabel("0");
		GridBagConstraints gbc_lblReportWithB = new GridBagConstraints();
		gbc_lblReportWithB.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportWithB.gridx = 1;
		gbc_lblReportWithB.gridy = 7;
		add(lblReportWithB, gbc_lblReportWithB);
		
		JLabel lblNewLabel = new JLabel("Reportes sin Buffers");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 8;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblReportWithOutB = new JLabel("0");
		GridBagConstraints gbc_lblReportWithOutB = new GridBagConstraints();
		gbc_lblReportWithOutB.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportWithOutB.gridx = 1;
		gbc_lblReportWithOutB.gridy = 8;
		add(lblReportWithOutB, gbc_lblReportWithOutB);
		
		btnSaveChanges = new JButton("Guardar Cambios");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = GridBagConstraints.SOUTH;
		add(btnSaveChanges, gbc_btnNewButton);
		
		btnDeleteIed = new JButton("Eliminar Ied");
		GridBagConstraints gbc_btnEliminarIed = new GridBagConstraints();
		gbc_btnEliminarIed.anchor = GridBagConstraints.SOUTH;
		gbc_btnEliminarIed.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminarIed.gridx = 1;
		gbc_btnEliminarIed.gridy = GridBagConstraints.SOUTH;
		add(btnDeleteIed, gbc_btnEliminarIed);
	}
	
	/**
	 * Método que agrega los listener correspondientes a cada botón de la vista.
	 * Actualizar ied, y borrar ied
	 * @param listenForBtnClick
	 */
	void addBtnIed(ActionListener listenForBtnClick){
		btnDeleteIed.addActionListener(listenForBtnClick);
		btnSaveChanges.addActionListener(listenForBtnClick);
	}
	
}

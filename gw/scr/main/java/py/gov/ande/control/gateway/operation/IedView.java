package py.gov.ande.control.gateway.operation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedOperation;

public class IedView extends JPanel {

	protected JButton btnStartExploration;
	protected JButton btnEndExploration;
	protected IedOperation ied;

	/**
	 * Create the panel.
	 * @param ied2 
	 */
	public IedView(IedOperation ied2) {
		this.ied = ied2;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 5;
		gbc_panel.gridwidth = 4;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("ESTADO DEL IED " + ied2.getName());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblActualStatus = new JLabel("Estado Actual:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 8;
		panel.add(lblActualStatus, gbc_lblNewLabel_1);
		
		JLabel lblStatus = new JLabel("Detenido");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 8;
		panel.add(lblStatus, gbc_lblNewLabel_2);
		
		btnStartExploration = new JButton("Iniciar Exploración");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 9;
		panel.add(btnStartExploration, gbc_btnNewButton);
		
		btnEndExploration = new JButton("Detener Exploración");
		GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
		gbc_btnNewButton2.gridx = 6;
		gbc_btnNewButton2.gridy = 9;
		panel.add(btnEndExploration, gbc_btnNewButton2);
	}
	
	/**
	 * Método que agrega funcionalidades a los botones.
	 * Iniciar exploración y cancelar.
	 * @param listenForBtnClick
	 * @author Pablo
	 * @date 2016-08-13
	 */
	void addBtnIed(ActionListener listenForBtnClick){
		btnStartExploration.addActionListener(listenForBtnClick);
		btnEndExploration.addActionListener(listenForBtnClick);
	}

}

package py.gov.ande.control.gateway.configuration;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class TabConfigurationIec101View extends JPanel {

	/**
	 * Create the panel.
	 */
	public TabConfigurationIec101View() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0};
		gridBagLayout.rowHeights = new int[]{-138};
		/*gridBagLayout.columnWidths = new int[]{96, 117, 0, 0};
		gridBagLayout.rowHeights = new int[]{300, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};*/
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Propiedades del IEC 60870-5-101");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

	}

}

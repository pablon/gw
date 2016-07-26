package py.gov.ande.control.gateway.configuration;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.hibernate.SessionFactory;
import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServerEventListener;
import org.openmuc.openiec61850.ServerSap;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.SwingConstants;


public class TabConfigurationIec61850View extends JPanel implements ClientEventListener {

	private static final long serialVersionUID = -386731185449901198L;
	private int tselLocal1 = 0;
    private int tselLocal2 = 0;
    private int tselRemote1 = 0;
    private int tselRemote2 = 1;
    private JTextField tselLocalField1 = new JTextField();
    private JTextField tselLocalField2 = new JTextField();
    private JTextField tselRemoteField1 = new JTextField();
    private JTextField tselRemoteField2 = new JTextField();  	
	private JLabel lblIp;
	private JTextField inputIp;
    private JTextField inputPort;
    JButton btnAddIED = new JButton();
    JButton btnExploreCid = new JButton();
    
    private static final Logger logger = LoggerFactory.getLogger(TabConfigurationIec61850View.class);
    
	/**
	 * Create the panel.
	 */
	public TabConfigurationIec61850View() {

		
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setVisible(false);
		
		GridBagLayout gbl_panelIec61850 = new GridBagLayout();
		gbl_panelIec61850.columnWidths = new int[]{207, 61, 55, 0};
		gbl_panelIec61850.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelIec61850.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelIec61850.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panelIec61850);
		
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
		
		lblIp = new JLabel("Dirección IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.WEST;
		gbc_lblIp.insets = new Insets(5, 5, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 2;
		add(lblIp, gbc_lblIp);
		
		inputIp = new JTextField("10.2.28.231");
		GridBagConstraints gbc_inputIp = new GridBagConstraints();
		gbc_inputIp.gridwidth = 2;
		gbc_inputIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputIp.insets = new Insets(5, 5, 5, 0);
		gbc_inputIp.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputIp.gridx = 1;
		gbc_inputIp.gridy = 2;
		add(inputIp, gbc_inputIp);
		//inputIp.setColumns(10);
		
		JLabel lblPort = new JLabel("Puerto");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(5, 5, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 3;
		add(lblPort, gbc_lblPort);
		
		inputPort = new JTextField("102");
		GridBagConstraints gbc_inputPort = new GridBagConstraints();
		gbc_inputPort.gridwidth = 2;
		gbc_inputPort.insets = new Insets(5, 5, 5, 0);
		gbc_inputPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPort.gridx = 1;
		gbc_inputPort.gridy = 3;
		add(inputPort, gbc_inputPort);
		//inputPort.setColumns(10);
		
		//--------------------------------------------------------------------
		JLabel lblTsel = new JLabel("TSelLocal");
		GridBagConstraints gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(5, 5, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 4;
		add(lblTsel, gbc_lblTsel);		
		
		
	    tselLocalField1 = new JTextField(Integer.toString(tselLocal1));
		GridBagConstraints gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(5, 5, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 4;
		add(tselLocalField1, gbc_inputTsel);
		
	    tselLocalField2 = new JTextField(Integer.toString(tselLocal2));
		GridBagConstraints gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(5, 5, 5, 0);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 4;
		add(tselLocalField2, gbc_inputTsel2);
	    
		//--------------------------------------------------------------------
		JLabel lblTselRem = new JLabel("TSelRemoto");
		gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(5, 5, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 5;
		add(lblTselRem, gbc_lblTsel);		
		
		
		tselRemoteField1 = new JTextField(Integer.toString(tselRemote1));
		gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(5, 5, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 5;
		add(tselRemoteField1, gbc_inputTsel);
		
		tselRemoteField2 = new JTextField(Integer.toString(tselRemote2));
		gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(5, 5, 5, 0);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 5;
		add(tselRemoteField2, gbc_inputTsel2);	    
   		
		btnAddIED = new JButton("Explorar IED");
		GridBagConstraints gbc_btnAgregarIED = new GridBagConstraints();
		gbc_btnAgregarIED.gridwidth = 2;
		gbc_btnAgregarIED.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregarIED.gridx = 1;
		gbc_btnAgregarIED.gridy = 6;		
		add(btnAddIED, gbc_btnAgregarIED);
		
		JLabel lblExploreFileCid = new JLabel("Explorar archivo .cid");
		GridBagConstraints gbc_lblExploreFileCid = new GridBagConstraints();
		gbc_lblExploreFileCid.insets = new Insets(0, 0, 5, 5);
		gbc_lblExploreFileCid.gridx = 0;
		gbc_lblExploreFileCid.gridy = 9;
		add(lblExploreFileCid, gbc_lblExploreFileCid);
		
		btnExploreCid = new JButton("Seleccionar Archivo");
		GridBagConstraints gbc_file= new GridBagConstraints();
		gbc_file.gridwidth = 2;
		gbc_file.insets = new Insets(0, 0, 5, 0);
		gbc_file.gridx = 1;
		gbc_file.gridy = 9;
		add(btnExploreCid, gbc_file);
		
	}
	
    protected byte[] getTselLocal() {
        tselLocal1 = parseTextField(tselLocalField1, tselLocal1);
        tselLocal2 = parseTextField(tselLocalField2, tselLocal2);    	
        return new byte[] { (byte) tselLocal1, (byte) tselLocal2 };
    }
    
    protected byte[] getTselRemote() {
        tselLocal1 = parseTextField(tselRemoteField1, tselRemote1);
        tselLocal2 = parseTextField(tselRemoteField2, tselRemote2);    	
        return new byte[] { (byte) tselLocal1, (byte) tselLocal2 };
    }
    
    private int parseTextField(JTextField field, int oldValue) {
        int value = oldValue;
        try {
            int newValue = Integer.parseInt(field.getText());
            if (newValue >= 0 && newValue <= 255) {
                value = newValue;
            }
        } catch (NumberFormatException e) {
            return oldValue;
        }
        return value;
    }

    protected String getInputIp(){
    	return inputIp.getText();
    }
    
    protected String getInputPort(){
    	return inputPort.getText();
    }
    
	void addBtnExploreIed(ActionListener listenForBtnClick){
		btnAddIED.addActionListener(listenForBtnClick);
		btnExploreCid.addActionListener(listenForBtnClick);
	}
	
	@Override
	public void newReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void associationClosed(IOException e) {
		// TODO Auto-generated method stub
		
	}
	
}
     

      

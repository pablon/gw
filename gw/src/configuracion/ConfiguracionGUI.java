package configuracion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;

import com.conexion.Conexion;

import java.awt.Component;
import javax.swing.JTextField;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.awt.Font;
import javax.swing.JButton;

import configuracion.DriverInfo;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class ConfiguracionGUI implements TreeSelectionListener {

	private JFrame frame;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
	private JPanel panelConf = new JPanel();
	private JPanel panelIec61850 = new JPanel();
	private JTree treeConf = new JTree();
	private JScrollPane scrollPaneDetails = new JScrollPane();
	private JScrollPane scrollPaneConf = new JScrollPane();
	private JTextField inputIp;
	private JLabel lblDescripcion = new JLabel();
	private JLabel lblIp;
    private DriverInfo[] driverInfo;
    private int driver = 0;
    private JTextField inputPort;
    
    private int tselLocal1 = 0;
    private int tselLocal2 = 0;
    private int tselRemote1 = 0;
    private int tselRemote2 = 1;
    private JTextField tselLocalField1 = new JTextField();
    private JTextField tselLocalField2 = new JTextField();
    private JTextField tselRemoteField1 = new JTextField();
    private JTextField tselRemoteField2 = new JTextField();    
    
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfiguracionGUI window = new ConfiguracionGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConfiguracionGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 781, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		//JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		
/*		tabbedPane.addTab("Configuracion", null, makeEtiquetaInicial("Configuracion"), "Configuraciones generales de los drivers");	
		tabbedPane.addTab("Mapeo", null, makeEtiquetaInicial("Mapeo"), "Mapeo de cada señal de cada driver");
		tabbedPane.addTab("Topologia", null, makeEtiquetaInicial("Topologia"), "Estructura topologica de cada señal de cada equipo");
*/		
		/*
		 * Inicio de vista de configuracion
		 */
        panelConf = new JPanel(false);
        panelConf.setLayout(new GridLayout(1, 1));
		tabbedPane.addTab("Configuracion", null, panelConf, "Configuraciones generales de los drivers");
		
		scrollPaneConf = new JScrollPane();
		//scrollPaneConf.setMinimumSize(new Dimension(100, 0));
		panelConf.add(scrollPaneConf);
		
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Gateway");
        createNodes(top);		

        /* a la izquierda, grafica el arbol de opciones de la pestaña Configuracion*/
		treeConf = new JTree(top);
		treeConf.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);		
		treeConf.addTreeSelectionListener(this);			//implementar valueChange
		scrollPaneConf.setViewportView(treeConf);
		
		/* a la derecha, grafica la vista de detalles */
		panelConf.add(scrollPaneDetails);
		panelIec61850.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelIec61850.setVisible(false);
		scrollPaneDetails.setViewportView(panelIec61850);
		GridBagLayout gbl_panelIec61850 = new GridBagLayout();
		gbl_panelIec61850.columnWidths = new int[]{207, 61, 55, 0};
		gbl_panelIec61850.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelIec61850.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelIec61850.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelIec61850.setLayout(gbl_panelIec61850);
		
		JLabel lblPropiedades = new JLabel("Propiedades");
		lblPropiedades.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblPropiedades = new GridBagConstraints();
		gbc_lblPropiedades.gridwidth = 3;
		gbc_lblPropiedades.insets = new Insets(0, 0, 5, 5);
		gbc_lblPropiedades.gridx = 0;
		gbc_lblPropiedades.gridy = 0;
		gbc_lblPropiedades.weightx = 1;
		panelIec61850.add(lblPropiedades, gbc_lblPropiedades);
		
		lblIp = new JLabel("Dirección IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.WEST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 2;
		panelIec61850.add(lblIp, gbc_lblIp);
		
		inputIp = new JTextField("127.0.0.1");
		GridBagConstraints gbc_inputIp = new GridBagConstraints();
		gbc_inputIp.gridwidth = 2;
		gbc_inputIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputIp.insets = new Insets(0, 0, 5, 5);
		gbc_inputIp.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputIp.gridx = 1;
		gbc_inputIp.gridy = 2;
		panelIec61850.add(inputIp, gbc_inputIp);
		inputIp.setColumns(10);
		
		JLabel lblPort = new JLabel("Puerto");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 3;
		panelIec61850.add(lblPort, gbc_lblPort);
		
		inputPort = new JTextField("102");
		GridBagConstraints gbc_inputPort = new GridBagConstraints();
		gbc_inputPort.gridwidth = 2;
		gbc_inputPort.insets = new Insets(0, 0, 5, 5);
		gbc_inputPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPort.gridx = 1;
		gbc_inputPort.gridy = 3;
		panelIec61850.add(inputPort, gbc_inputPort);
		inputPort.setColumns(10);
		
		//--------------------------------------------------------------------
		JLabel lblTsel = new JLabel("TSelLocal");
		GridBagConstraints gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 4;
		panelIec61850.add(lblTsel, gbc_lblTsel);		
		
		
	    tselLocalField1 = new JTextField(Integer.toString(tselLocal1));
		GridBagConstraints gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(0, 0, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 4;
		panelIec61850.add(tselLocalField1, gbc_inputTsel);
		
	    tselLocalField2 = new JTextField(Integer.toString(tselLocal2));
		GridBagConstraints gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(0, 0, 5, 0);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 4;
		panelIec61850.add(tselLocalField2, gbc_inputTsel2);
	    
		//--------------------------------------------------------------------
		JLabel lblTselRem = new JLabel("TSelRemoto");
		gbc_lblTsel = new GridBagConstraints();
		gbc_lblTsel.anchor = GridBagConstraints.WEST;
		gbc_lblTsel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTsel.gridx = 0;
		gbc_lblTsel.gridy = 5;
		panelIec61850.add(lblTselRem, gbc_lblTsel);		
		
		
		tselRemoteField1 = new JTextField(Integer.toString(tselRemote1));
		gbc_inputTsel = new GridBagConstraints();
		gbc_inputTsel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel.insets = new Insets(0, 0, 5, 5);
		gbc_inputTsel.gridx = 1;
		gbc_inputTsel.gridy = 5;
		panelIec61850.add(tselRemoteField1, gbc_inputTsel);
		
		tselRemoteField2 = new JTextField(Integer.toString(tselRemote2));
		gbc_inputTsel2 = new GridBagConstraints();
		gbc_inputTsel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTsel2.insets = new Insets(0, 0, 5, 0);
		gbc_inputTsel2.gridx = 2;
		gbc_inputTsel2.gridy = 5;
		panelIec61850.add(tselRemoteField2, gbc_inputTsel2);	    
   		
	    //--------------------------------------------------------------------
		JButton btnAgregarIED = new JButton("Inspeccionar IED");
		btnAgregarIED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iedInspections();
			}
		});
		GridBagConstraints gbc_btnAgregarIED = new GridBagConstraints();
		gbc_btnAgregarIED.gridwidth = 2;
		gbc_btnAgregarIED.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregarIED.gridx = 1;
		gbc_btnAgregarIED.gridy = 6;		
		panelIec61850.add(btnAgregarIED, gbc_btnAgregarIED);
 
		/*
		 * Inicio de vista de MAPEO
		 */
        JPanel panelMapping = new JPanel(false);
        JLabel labelMapping = new JLabel("Mapeo");
        labelMapping.setHorizontalAlignment(JLabel.CENTER);
        panelMapping.setLayout(new GridLayout(1, 1));
        panelMapping.add(labelMapping);
		tabbedPane.addTab("Mapeo", null, panelMapping, "Mapeo de cada señal de cada driver");		
		
		/*
		 * Inicio de vista de TOPOLOGIA
		 */
        JPanel panelTopology = new JPanel(false);
        JLabel labelTopology = new JLabel("Topologia");
        labelTopology.setHorizontalAlignment(JLabel.CENTER);
        panelTopology.setLayout(new GridLayout(1, 1));
        panelTopology.add(labelTopology);
		tabbedPane.addTab("Topologia", null, panelTopology, "Estructura topologica de cada señal de cada equipo");			
		
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
	}
	
	/**
	 * Crea cada pestaña de la vista inicial.
	 * Aún no se utiliza porque la vista Design no lo levanta.
	 * @param etiqueta
	 */
	private JPanel makeEtiquetaInicial(String etiqueta){
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(etiqueta);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;	
	}
	
	/**
	 * Crea la lista de opciones de drivers a configurar.
	 * es utilizado para la construccion del arbol de drivers
	 * @param top nodo raiz
	 * @version 1.0
	 */
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode subestacion;
        DefaultMutableTreeNode drivers;
        String driverNombre;
        Boolean iec61850;
        Boolean iec101;
        Boolean ied;
        Integer countDriver = 0;
        
        subestacion = new DefaultMutableTreeNode("Subestación");
        top.add(subestacion);
     
        try {
			Connection con = Conexion.crearConexion();
			Statement st = con.createStatement(	ResultSet.TYPE_SCROLL_INSENSITIVE, 
					   							ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM drivers");
			rs.last();
			countDriver = rs.getRow();
			driverInfo = new DriverInfo [countDriver];
			rs.beforeFirst();		

			while (rs.next())
			{
				driverNombre = new String(String.valueOf(rs.getString(2)));
				iec61850 = Objects.equals(String.valueOf(rs.getString(4)), new String("t"));
				iec101 = Objects.equals(String.valueOf(rs.getString(5)), new String("t"));
				ied = Objects.equals(String.valueOf(rs.getString(6)), new String("t"));
				driverInfo[driver] = new DriverInfo(driver, driverNombre, iec61850, iec101, ied);
				driver++;
		        drivers = new DefaultMutableTreeNode(driverNombre);
		        
		        subestacion.add(drivers);			   
			} rs.close();
			st.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }


    /**
     * Metodo que es llamado cada vez que se le da click al arbol de configuracion
     */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//e.getPath().toString()
		//panelDetails.removeAll();
		//panelDetails.repaint();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeConf.getLastSelectedPathComponent();
		if (node == null){
			System.out.println("nada fue seleccionoda");
			return;
		}
		
		Object nodeInfo = node.getUserObject();
		//Object nodeInfo = e.getNewLeadSelectionPath().getLastPathComponent();
		//inputIp.setText(String.valueOf(nodeInfo.toString()));
		
		//borrador de metodo que mostrara ventana para el caso que se le de click al nodo iec61850 de configuracion
		panelIec61850.setVisible(false);
		for (DriverInfo driverInfo2 : driverInfo) {
			if (driverInfo2.getIec61850())
				if(Objects.equals(driverInfo2.toString(), nodeInfo)){
					panelIec61850.setVisible(true);
					break;
				}
		}
		
	}

    private void iedInspections() {
    	System.out.println("Funcion Inspeccionar. IP: "+inputIp.getText()+", Puerto: "+inputPort.getText());
    	
        ClientSap clientSap = new ClientSap();

        InetAddress address = null;
        try {
            address = InetAddress.getByName(inputIp.getText());
        } catch (UnknownHostException e1) {
        	System.out.println("error de ip");
            e1.printStackTrace();
            return;
        }

        int remotePort = 10002;
        try {
            remotePort = Integer.parseInt(inputPort.getText());
            if (remotePort < 1 || remotePort > 0xFFFF) {
                throw new NumberFormatException("port must be in range [1, 65535]");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }  
        
/*        clientSap.setTSelLocal(settingsFrame.getTselLocal());
        clientSap.setTSelRemote(settingsFrame.getTselRemote());

        try {
            association = clientSap.associate(address, remotePort, null, null);
        } catch (IOException e) {
            logger.error("Error connecting to server: " + e.getMessage());
            return;
        }

        ServerModel serverModel;
        try {
            serverModel = association.retrieveModel();
            association.getAllDataValues();
        } catch (ServiceError e) {
            logger.error("Service Error requesting model.", e);
            association.close();
            return;
        } catch (IOException e) {
            logger.error("Fatal IOException requesting model.", e);
            return;
        }
*/        
    }	
	
}

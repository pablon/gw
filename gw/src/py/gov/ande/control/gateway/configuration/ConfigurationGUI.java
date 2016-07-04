package py.gov.ande.control.gateway.configuration;

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
import javax.swing.tree.TreeSelectionModel;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import py.gov.ande.control.gateway.configuration.DriverInfo;
import py.gov.ande.control.gateway.model.Drivers;
import py.gov.ande.control.gateway.model.DriversManager;
import py.gov.ande.control.gateway.util.Connections;
import py.gov.ande.control.gateway.util.GenericManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;


public class ConfigurationGUI implements TreeSelectionListener {

	private JFrame frame;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
	private JPanel panelConf = new JPanel();
	//private JPanel panelIec61850 = new JPanel();
	private JTree treeConf = new JTree();
	private JScrollPane scrollPaneDetails = new JScrollPane();
	private JScrollPane scrollPaneConf = new JScrollPane();
	private JLabel lblDescripcion = new JLabel();

    private DriverInfo[] driverInfo;
    private int driver = 0;

    
    private final PanelIec61850 panelIec61850 = new PanelIec61850();
    private static SessionFactory factory; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationGUI window = new ConfigurationGUI();
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
	public ConfigurationGUI() {
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

		scrollPaneDetails.setViewportView(panelIec61850);
		panelConf.add(scrollPaneDetails);
		
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
        
        //DriversManager dm = new DriversManager();
        List<Drivers> driverList = GenericManager.getAllObjects(Drivers.class, Order.asc("id"));

        for (Drivers drivers2 : driverList) {
	        drivers = new DefaultMutableTreeNode(drivers2.getDescription());
	        subestacion.add(drivers);
		}
        
/*        try {
			Connection con = Connections.crearConexion();
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
		        //drivers = new DefaultMutableTreeNode(driverNombre);
		        
		        //subestacion.add(drivers);			   
			} rs.close();
			st.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
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
		
		List<Drivers> driverListx = GenericManager.getAllObjects(Drivers.class, Order.asc("id"));
		for (Drivers drivers2x : driverListx) {
			if(drivers2x.getIec61850() == true){
				if(Objects.equals(drivers2x.getDescription(), nodeInfo)){
					panelIec61850.setVisible(true);
					break;
				}
			}
		}
		
/*		for (DriverInfo driverInfo2 : driverInfo) {
			if (driverInfo2.getIec61850())
				if(Objects.equals(driverInfo2.toString(), nodeInfo)){
					panelIec61850.setVisible(true);
					break;
				}
		}*/
		
	}


}

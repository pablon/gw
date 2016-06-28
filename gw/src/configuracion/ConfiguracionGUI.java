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

public class ConfiguracionGUI implements TreeSelectionListener {

	private JFrame frame;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
	private JPanel panelConf = new JPanel();
	private JPanel panelDetails = new JPanel();
	private JTree treeConf = new JTree();
	private JScrollPane scrollPaneDetails = new JScrollPane();
	private JScrollPane scrollPaneConf = new JScrollPane();
	private JTextField inputDescripcion;
	private JLabel lblDescripcion = new JLabel();
    private DriverInfo[] driverInfo;
    private int driver = 0;
	
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
		panelDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelDetails.setVisible(false);
		scrollPaneDetails.setViewportView(panelDetails);
		GridBagLayout gbl_panelDetails = new GridBagLayout();
		gbl_panelDetails.columnWidths = new int[]{207, 172, 0};
		gbl_panelDetails.rowHeights = new int[]{19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelDetails.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelDetails.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelDetails.setLayout(gbl_panelDetails);
		
		JLabel lblPropiedades = new JLabel("Propiedades");
		lblPropiedades.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblPropiedades = new GridBagConstraints();
		gbc_lblPropiedades.gridwidth = 2;
		gbc_lblPropiedades.insets = new Insets(0, 0, 5, 0);
		gbc_lblPropiedades.gridx = 0;
		gbc_lblPropiedades.gridy = 0;
		gbc_lblPropiedades.weightx = 1;
		panelDetails.add(lblPropiedades, gbc_lblPropiedades);
		
		lblDescripcion = new JLabel("Descripcion del campo");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 2;
		panelDetails.add(lblDescripcion, gbc_lblDescripcion);
		
		inputDescripcion = new JTextField();
		GridBagConstraints gbc_inputDescripcion = new GridBagConstraints();
		gbc_inputDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_inputDescripcion.anchor = GridBagConstraints.NORTHWEST;
		gbc_inputDescripcion.gridx = 1;
		gbc_inputDescripcion.gridy = 2;
		panelDetails.add(inputDescripcion, gbc_inputDescripcion);
		inputDescripcion.setColumns(10);
		
		JButton btnGuardarPropiedades = new JButton("Guardar");
		GridBagConstraints gbc_btnGuardarPropiedades = new GridBagConstraints();
		gbc_btnGuardarPropiedades.gridx = 1;
		gbc_btnGuardarPropiedades.gridy = GridBagConstraints.SOUTHWEST;;
		panelDetails.add(btnGuardarPropiedades, gbc_btnGuardarPropiedades);
 
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
		inputDescripcion.setText(String.valueOf(nodeInfo.toString()));
		
		//borrador de metodo que mostrara ventana para el caso que se le de click al nodo iec61850 de configuracion
		panelDetails.setVisible(false);
		for (DriverInfo driverInfo2 : driverInfo) {
			if (driverInfo2.getIec61850())
				if(Objects.equals(driverInfo2.toString(), nodeInfo)){
					panelDetails.setVisible(true);
					break;
				}
		}
		
	}
	
}

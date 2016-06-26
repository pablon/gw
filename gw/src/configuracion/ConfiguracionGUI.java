package configuracion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

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

import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.JButton;

public class ConfiguracionGUI implements TreeSelectionListener, ActionListener {

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

	private DefaultMutableTreeNode selectedNode;	
	
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
	    treeConf.setComponentPopupMenu(getPopUpMenu()); //agrega el popup
	    //treeConf.addMouseListener(getMouseListener());
		scrollPaneConf.setViewportView(treeConf);
		
		/* a la derecha, grafica la vista de detalles */
		panelConf.add(scrollPaneDetails);
		panelDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
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
	 * en una siguiente version se deberá levantar los datos de una BD
	 * @param top nodo raiz
	 * @version 1.0
	 */
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode subestacion;
        DefaultMutableTreeNode drivers;
        String driverNombre;

        subestacion = new DefaultMutableTreeNode("Subestación");
        top.add(subestacion);
     
        try {
			Connection con = Conexion.crearConexion();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM drivers");
			
			while (rs.next())
			{
		        //drivers = new DefaultMutableTreeNode(String.valueOf(rs.getString(2)));
				driverNombre = new String(String.valueOf(rs.getString(2)));
		        drivers = new DefaultMutableTreeNode(driverNombre, true);
		        
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
     * Ejemplo de creacion de clase interna
     * @author root
     *
     */
    private class DriverInfo {
        public String bookName;
        public URL bookURL;

        public DriverInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }

        public String toString() {
            return bookName;
        }
    }    

    /**
     * Metodo que es llamado cada vez que se le da click al arbol de configuracion
     */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
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
	}
	
	private JPopupMenu getPopUpMenu() {
	    JPopupMenu menu = new JPopupMenu();
	    JMenuItem item = new JMenuItem("Agregar IED");
	    item.addActionListener(this);
	    menu.add(item);
	    return menu;
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected. Event source: " + source.getText();
        System.out.println(s);
	}	
	
}

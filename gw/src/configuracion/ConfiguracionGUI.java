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
import java.awt.Dimension;

public class ConfiguracionGUI {

	private JFrame frame;

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
		frame.setBounds(100, 100, 642, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
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
        JPanel panelConf = new JPanel(false);
        panelConf.setLayout(new GridLayout(1, 1));
		tabbedPane.addTab("Configuracion", null, panelConf, "Configuraciones generales de los drivers");
		
		JScrollPane scrollPaneConf = new JScrollPane();
		scrollPaneConf.setMinimumSize(new Dimension(100, 0));
		panelConf.add(scrollPaneConf);
		
		JTree treeConf = new JTree();
		scrollPaneConf.setViewportView(treeConf);
		
		JScrollPane scrollPaneDetails = new JScrollPane();
		panelConf.add(scrollPaneDetails);
		
		JPanel panelDetails = new JPanel();
		scrollPaneDetails.setViewportView(panelDetails);
 
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
	
	/*
	 * Crea un TAB
	 */
	private JPanel makeEtiquetaInicial(String etiqueta){
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(etiqueta);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;	
	}
	
}

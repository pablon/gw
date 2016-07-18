package py.gov.ande.control.gateway.configuration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.DriversManager;

public class ConfigurationView extends JFrame {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationView.class);

	private JPanel topPanel;
	protected GridBagLayout gbl;
	private JTabbedPane tabbedPane;
	private GridBagConstraints gbc_tabbedPane;
	protected TabConfigurationView panelConf;
	protected TabMapping panelMapping;
	protected TabTopology panelTopology;

	public ConfigurationView() {
		
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            logger.error("Class not found: ", e);
        } catch (InstantiationException e) {
            logger.error("Object not instantiated: ", e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal acces: ", e);
        } catch (UnsupportedLookAndFeelException e) {
            logger.error("Unsupported LookAndFeel: ", e);
        }		
		
        gbl=new GridBagLayout();
        setLayout(gbl);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
	
        GridBagConstraints topPanelConstraint = new GridBagConstraints();
        topPanelConstraint.fill = GridBagConstraints.HORIZONTAL;
        topPanelConstraint.gridwidth = GridBagConstraints.REMAINDER;
        topPanelConstraint.gridx = 0;
        topPanelConstraint.gridy = 0;
        topPanelConstraint.insets = new Insets(5, 5, 5, 5);
        topPanelConstraint.anchor = GridBagConstraints.NORTH;
        gbl.setConstraints(topPanel, topPanelConstraint);
        add(topPanel);		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		gbc_tabbedPane.weightx = 1;
		gbc_tabbedPane.weighty = 1;
		gbc_tabbedPane.insets = new Insets(5, 5, 5, 5);
		gbc_tabbedPane.anchor = GridBagConstraints.NORTHWEST;
		gbl.setConstraints(topPanel, gbc_tabbedPane);
		topPanel.add(tabbedPane);
		//getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		panelConf = new TabConfigurationView();
		tabbedPane.addTab("Configuracion", null, panelConf, "Configuraciones generales de los drivers");
		panelMapping = new TabMapping();
		tabbedPane.addTab("Mapeo", null, panelMapping, "Mapeo de cada señal de cada driver");	
		panelTopology = new TabTopology();
		tabbedPane.addTab("Topologia", null, panelTopology, "Estructura topologica de cada señal de cada equipo");
		
		setSize(900, 500);
		setMinimumSize(new Dimension(420, 420));
		
	}

}


/**
-gridx: columna de la esquina superior izquierda del componente.
-gridy: línea de la esquina superior izquierda del componente.
Para estas dos propiedades, la numeración empieza por cero.
-gridwidth: número de columnas ocupadas por el componente.
-gridheight: número de líneas ocupadas por el componente.
-weightx: indica cómo se reparte el espacio adicional disponible en anchura cuando el contenedor es redimensionado. El reparto
se hace mediante prorrata de este valor. Si este valor es igual a cero, no se redimensiona. Si este valor es idéntico para todos
los componentes, se reparte el espacio de manera equitativa.
weighty: esta propiedad tiene el mismo papel que la anterior, pero en el eje vertical.
-fill: se utiliza esta propiedad cuando la zona asignada a un componente es superior a su tamaño. Determina si se redimensiona el
componente y cómo. Las constantes siguientes son posibles:
  NONE: no se redimensiona el componente.
  HORIZONTAL: se redimensiona el componente en anchura y no se cambia su altura.
  VERTICAL: se redimensiona el componente en altura y no se cambia su anchura.
  BOTH: se redimensiona el componente en anchura y altura para rellenar completamente la superficie disponible.
-anchor: indica cómo está posicionado el componente en el espacio disponible si no es redimensionado. Las constantes
siguientes están disponibles: nortwesth north noreast west center east 
*/


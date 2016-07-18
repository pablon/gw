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


public class ConfigurationGUI {

	private JFrame frame;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
	private JPanel panelConf = new JPanel();
	private JTree treeConf = new JTree();
	private JScrollPane scrollPaneDetails = new JScrollPane();
	private JScrollPane scrollPaneConf = new JScrollPane();
    private final TabConfigurationIec61850View panelIec61850 = new TabConfigurationIec61850View();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationView view = new ConfigurationView();
					DriversManager driverModel = new DriversManager();
					ConfigurationController controller = new ConfigurationController(view, driverModel);
					view.setVisible(true);

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

	}

}

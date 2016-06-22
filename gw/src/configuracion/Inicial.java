package configuracion;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class Inicial implements TreeSelectionListener{

	private JFrame frame;
	private JTree treeConfiguracion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicial window = new Inicial();
					//window.frame.pack();
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
	public Inicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);

		//prueba de layout
        GridBagLayout gbl = new GridBagLayout();
        frame.getContentPane().setLayout(gbl);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 700, 500);

        JComponent panel1 = makeTextPanel("Panel #1");
        //agregar dos objetos:
        //1)arbol de opciones de seleccion de drivers, a la izquierda
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Proyecto");
        createNodes(top);
        
        //Create a tree that allows one selection at a time.
        treeConfiguracion = new JTree(top);
        treeConfiguracion.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        treeConfiguracion.addTreeSelectionListener(this);
        
        JScrollPane treeScrollPane = new JScrollPane(treeConfiguracion);
        treeScrollPane.setMinimumSize(new Dimension(100, 0));
        treeScrollPane.setVisible(true);
        
        GridBagConstraints treeScrollPaneConstraint = new GridBagConstraints();
        treeScrollPaneConstraint.fill = GridBagConstraints.BOTH;
        treeScrollPaneConstraint.gridx = 0;
        treeScrollPaneConstraint.gridy = 1;
        treeScrollPaneConstraint.weightx = 0.2;
        treeScrollPaneConstraint.weighty = 1;
        treeScrollPaneConstraint.insets = new Insets(5, 5, 5, 5);
        gbl.setConstraints(treeScrollPane, treeScrollPaneConstraint);

        
        //2)cuadro de propiedades
        
        tabbedPane.addTab("Configuración", null, treeScrollPane, "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Mapeo", null, panel2, "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Topología", null, panel3, "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
		frame.getContentPane().add(tabbedPane);
	}
	
    
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Sistema");
        top.add(category);

        //original Tutorial
        book = new DefaultMutableTreeNode("Cliente IEC 61850");
        category.add(book);

        book = new DefaultMutableTreeNode("Esclavo IEC 60870-5-101");
        category.add(book);
        
    }
	
    
}

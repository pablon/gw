package py.gov.ande.control.gateway.operation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationView extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(OperationView.class);
	private GridBagLayout gbl;
	private JPanel topPanel;

	public OperationView() {

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
        this.getContentPane().setLayout(gbl);
        
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
        
		setSize(1100, 650);
		setMinimumSize(new Dimension(420, 420));
		
	}

}

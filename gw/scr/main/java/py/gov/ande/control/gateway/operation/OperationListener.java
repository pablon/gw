package py.gov.ande.control.gateway.operation;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationListener implements TreeSelectionListener {

	private static final Logger logger = LoggerFactory.getLogger(OperationListener.class);
	private OperationView theView;

	public OperationListener(OperationView theView) {
		this.theView = theView;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		logger.info("valueChanged");

	}

}

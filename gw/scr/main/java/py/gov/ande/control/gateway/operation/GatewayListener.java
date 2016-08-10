package py.gov.ande.control.gateway.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.DriversManager;

public class GatewayListener implements ActionListener {

	private static final Logger logger = LoggerFactory.getLogger(GatewayListener.class);
	private OperationView theView;
	private DriversManager driver;

	public GatewayListener(OperationView theView, DriversManager driver) {
		this.theView = theView;
		this.driver = driver;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("button");
		driver.rebuildDBOperation();
	}

}

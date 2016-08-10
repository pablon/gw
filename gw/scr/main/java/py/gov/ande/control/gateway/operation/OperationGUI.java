package py.gov.ande.control.gateway.operation;

import java.awt.EventQueue;

import py.gov.ande.control.gateway.manager.DriversManager;

public class OperationGUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationView view = new OperationView();
					DriversManager driver = new DriversManager();
					OperationController controller = new OperationController(view, driver);
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

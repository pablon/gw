package py.gov.ande.control.gateway.operation;

import java.awt.EventQueue;

public class OperationGUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationView view = new OperationView();
					OperationController controller = new OperationController(view);
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

package py.gov.ande.control.gateway.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.DriversManager;
import py.gov.ande.control.gateway.util.DatabaseOperationResult;

public class GatewayListener implements ActionListener {

	private static final Logger logger = LoggerFactory.getLogger(GatewayListener.class);
	private OperationView theView;
	private DriversManager driver;

	public GatewayListener(OperationView theView, DriversManager driver) {
		this.theView = theView;
		this.driver = driver;
	}

	/**
	 * Acción que realiza la actualización de la base de datos de operations
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//logger.info("button");
		int option = JOptionPane.showConfirmDialog(null, "Confirmar que se quiere actualizar el sistema. Se importarán los datos de configuración nuevamente", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
    	if(option == JOptionPane.OK_OPTION){
			DatabaseOperationResult rebuildDBOperation = DriversManager.rebuildDBOperation();
			if(rebuildDBOperation.getErrorType() == null){
	    		JOptionPane.showMessageDialog(null,"Información: Se actualizó el sistema satisfactoriamente",
	        		      "Advertencia",JOptionPane.INFORMATION_MESSAGE);
	    		//PENDIENTE: aquí debe llamar a la construcción del arbol nuevamente
			}else {
	    		JOptionPane.showMessageDialog(null,"Información: Ocurrió un problema. " + rebuildDBOperation.getException(),
	      		      "Advertencia",JOptionPane.ERROR_MESSAGE); 		
			}
    	}
	}

}

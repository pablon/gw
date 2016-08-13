package py.gov.ande.control.gateway.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IedListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(IedListener.class);

	private OperationView theView;

	public IedListener(OperationView theView) {
		this.theView = theView;
	}

	/**
	 * Acción sobre los botones de la vista del ied. 
	 * Permite arrancar y detener el driver. 
	 * @author Pablo
	 * @date 2016-08-13
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int length = theView.iedView.length;
		for(int i = 0; i < length; i++){
			if(theView.iedView[i].isVisible()){
				if(e.getSource() == theView.iedView[i].btnStartExploration){
					logger.info("btnStartExploration: "+i);				
				}else if(e.getSource() == theView.iedView[i].btnEndExploration){
					logger.info("btnEndExploration: "+i);
				}
			}
		}

	}

}

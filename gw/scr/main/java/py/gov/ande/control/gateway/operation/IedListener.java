package py.gov.ande.control.gateway.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.driverUtil.DriverOperationResult;
import py.gov.ande.control.gateway.driverUtil.PoolingRunnable;
import py.gov.ande.control.gateway.manager.BrcbManager;
import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;
import py.gov.ande.control.gateway.manager.UrcbManager;
import py.gov.ande.control.gateway.model.BufferedRcbOperation;
import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.model.TagMonitorIec61850Operation;
import py.gov.ande.control.gateway.model.UnbufferedRcbOperation;

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
					DriverOperationResult result = startExploration(theView.iedView[i].ied);
					if(result.wasError()){
			    		JOptionPane.showMessageDialog(null,"Información: Ocurrió un problema. " + result.getException(),
				      		      "Advertencia",JOptionPane.ERROR_MESSAGE); 
					}
				}else if(e.getSource() == theView.iedView[i].btnEndExploration){
					logger.info("btnEndExploration: "+i);
				}
			}
		}
	}

	/**
	 * Método que lanza un hilo de ejecución.
	 * Realizará pooling y reportes.
	 * 1) identifica si hay tags=use que no están dentro de ningún reporte.
	 * 2) lanza hilo para pooling.
	 * 3) Extraer lista de reportes para los tags seleccionados:
	 * select brcb_id en tabla TAG_MONITOR where USE=TRUE, ied_id=x AGRUPADO por brcb_id.
	 * 4) lanzar hilo por cada reporte
	 * 
	 * @param ied
	 * @return 
	 * @date 2016-08-13
	 * @author Pablo
	 * @version 1.0
	 */
	private DriverOperationResult startExploration(IedOperation ied) {
        logger.info("inicio");
		List<TagMonitorIec61850Operation> tagsWithOutAnyReport = TagMonitorIec61850Manager.getAllTagsWithOutAnyReport(ied);
		List<BufferedRcbOperation> bReportsWithSelectedTags = BrcbManager.getAllReportsWithSelectedTags(ied);
		List<UnbufferedRcbOperation> uReportsWithSelectedTags = UrcbManager.getAllReportsWithSelectedTags(ied);
		final int numberOfThreads = 1;
		
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Runnable poolingRunnable = new PoolingRunnable(tagsWithOutAnyReport);
        executor.execute(poolingRunnable);
    
        executor.shutdown();	// Cierro el Executor
        logger.info("fin del startExploration");
		
		return new DriverOperationResult();
	}
	


}

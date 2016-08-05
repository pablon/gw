package py.gov.ande.control.gateway.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.TagMonitorIec61850Manager;

public class TabMappingTagsListener implements ActionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(TabMappingTagsListener.class);
	private TabMappingView theView;

	public TabMappingTagsListener(TabMappingView theView) {
		this.theView = theView;
	}

	/**
	 * Acción que verifica cual es la instancia de MappingIedView con la que se está interactuando, 
	 * por la verificación de que estará visible.
	 * verifica que el haya sido presionado el botón btnUpdateTags.
	 * Se obtiene la lista de filas que han sido actualizadas en alguna de sus columnas.
	 * Finalmente se envía los datos actualizados al manager para actualizar la bd
	 * @author Pablo
	 * @date 2016-08-04
	 * @see TagMonitorIec61850Manager.updateTag
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("btnActualizarTags");

		int length = theView.tabMappingIedView.length;
		for(int i = 0; i< length; i++){
			if(theView.tabMappingIedView[i].isVisible()){
				if(e.getSource() == theView.tabMappingIedView[i].btnUpdateTags){
					Integer[] updatedIndexes = theView.tabMappingIedView[i].myTableModel.getUpdatedRowIndexes();
					for(int j = 0; j < updatedIndexes.length; j++){
						int idx = updatedIndexes[j];
						String name = theView.tabMappingIedView[i].myTableModel.getValueAt(idx, 0).toString();
						System.out.println(name + " row has been updated");
						
						int id = (int) theView.tabMappingIedView[i].myTableModel.getValueAt(idx,theView.tabMappingIedView[i].getColumnNames().length); 
						System.out.println("id: "+id);
						TagMonitorIec61850Manager.updateTag(id, theView.tabMappingIedView[i].myTableModel.getValueAt(idx, 0));
					}
					
				}
			}
		}
	}

}

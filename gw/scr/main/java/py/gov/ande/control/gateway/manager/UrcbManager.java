package py.gov.ande.control.gateway.manager;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmuc.openiec61850.Urcb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openmuc.openiec61850.DataSet;
import org.openmuc.openiec61850.ModelNode;
import org.openmuc.openiec61850.ServerModel;

import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.model.ReportingCapability;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.model.UnbufferedRcb;
import py.gov.ande.control.gateway.model.UnbufferedRcbOperation;
import py.gov.ande.control.gateway.util.GenericManager;

public class UrcbManager {
	
	private static UnbufferedRcb report = null;
	private static final Logger logger = LoggerFactory.getLogger(UrcbManager.class);
	
	/**
	 * Explora los rcb que tiene el ied.
	 * Explora los tags del dataset del reporte, y lo coteja con la tabla TagMonitorIec61850.
	 * Si lo encuentra entre los tagsMonitor y que debería, le setea UrcbId y Unbuffered  
	 * @param ied
	 * @param serverModel
	 * @date 2016-08-07
	 * @author Pablo
	 * @version 1.1
	 */
	public static void saveAllTagWithOutBuffer(Ied ied, ServerModel serverModel) {
		//logger.info("inicio");
		ReportingCapability reportingCapacibiliyBrcb = ReportingCapabilityManager.getObjectBrcb();
		ReportingCapability reportingCapacibiliyUrcb = ReportingCapabilityManager.getObjectUrcb();
		ReportingCapability reportingCapacibiliyNone = ReportingCapabilityManager.getObjectNoneRcb();
		ReportingCapability reportingCapacibiliyBoth = ReportingCapabilityManager.getObjectBothRcb();
		
		for (Urcb modelNodercbs : serverModel.getUrcbs()) {
			//logger.info("Urcb modelNodercbs : serverModel.getUrcbs");
			
			String datasetOld = modelNodercbs.getDatSet().getStringValue();
			String datasetReferent = datasetOld.replace('$', '.');
			
			report = new UnbufferedRcb();
			report.setIed(ied);
			report.setReferent(modelNodercbs.getReference().toString());				//UC_SSAACTRL/LLN0.urcbESTADOS2
			report.setDataset(datasetReferent);											//?
			//logger.info("antes de saveObject(report)");
			GenericManager.saveObject(report);
			//logger.info("luego de saveObject report");
			DataSet dataset = serverModel.getDataSet(datasetReferent);
		    for (ModelNode modelNode : dataset) {
		    	//logger.info("for (ModelNode modelNode : dataset)");
		    	String tag = modelNode.getReference().toString();						//UC_SSAACTRL/GGIO2.Ind02
		    	
		    	/*TagMonitorIec61850 tagMonitor = (TagMonitorIec61850) GenericManager.getFilteredObject(TagMonitorIec61850.class, 
		    			Arrays.asList(
		    					Restrictions.eq("iedId", ied.getId()) ,
		    					Restrictions.eq("telegramAddress", tag)) 
		    			);*/
		    	//logger.info("ir al metodo getObjectBasedOnCriteria. ied: "+ied.getId()+", telegram: "+tag);
		    	TagMonitorIec61850 tagMonitor = (TagMonitorIec61850) GenericManager.getObjectBasedOnCriteria("Select tag From TagMonitorIec61850 as tag inner join tag.ied as ied where ied.id ="+ied.getId()+" and tag.telegramAddress = '"+tag+"'");

		    	if(tagMonitor != null){
		    		//logger.info("tagMonitor != null");
		    		tagMonitor.setUnbufferedRcb(report);
		    		tagMonitor.setUnbuffered(true);

		    		int currentCapability = tagMonitor.getReportingCapability().getId();
		    		if(currentCapability == reportingCapacibiliyNone.getId()){
		    			tagMonitor.setReportingCapability(reportingCapacibiliyUrcb);
		    		}else if(currentCapability == reportingCapacibiliyBrcb.getId()){
		    			tagMonitor.setReportingCapability(reportingCapacibiliyBoth);
		    		}		    		
		    		//logger.info("updateObject tagMonitor");
		    		GenericManager.updateObject(tagMonitor);
		    	}else{
		    		//logger.error("tagMonitor == null");
		    	}
			}
		}
		//logger.info("fin");
	}

	/**
	 * Método que retorna la lista de reportes sin buffers para los tags seleccionados.
	 * Se toma la decisión de filtrar por tag.buffered = false, ya que se asume que si el tag estaba
	 * configurado para brcb y urcb, ya fue seleccionado el reporte con buffer.
	 * @param Ied
	 * @return List<Integer>
	 * @author Pablo
	 * @date 2016-08-13
	 * @version 1.0
	 */
	public static List<Integer> getAllReportsIdWithSelectedTags(IedOperation ied) {

		return GenericManager.getListBasedOnCriteria(
				"select urcb.id from TagMonitorIec61850Operation as tag "
				+ "inner join tag.unbufferedRcbOperation urcb "
				+ "inner join tag.iedOperation as ied "
				+ "where "
				+ "ied.id = "+ied.getId()+ "and "
				+ "tag.buffered = false and "
				+ "tag.unbuffered = true and "
				+ "tag.use = true "
				+ "group by urcb.id");
	}

}

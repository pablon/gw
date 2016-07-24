package py.gov.ande.control.gateway.manager;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmuc.openiec61850.Brcb;
import org.openmuc.openiec61850.DataSet;
import org.openmuc.openiec61850.ModelNode;
import org.openmuc.openiec61850.ServerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.BufferedRcb;
import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericManager;

public class BrcbManager {

	private static BufferedRcb report = null;
	private static final Logger logger = LoggerFactory.getLogger(BrcbManager.class);
	
	/**
	 * Explora los rcb que tiene el ied.
	 * Explora los tags del dataset del reporte, y lo coteja con la tabla TagMonitorIec61850.
	 * Si lo encuentra entre los tagsMonitor y que debería, le setea BrcbId y Buffered  
	 * @param ied
	 * @param serverModel
	 * @return
	 */
	public static void saveAllTagWithBuffer(Ied ied, ServerModel serverModel) {
		logger.info("inicio");
		int reportingCapacibiliyBrcbId = ReportingCapabilityManager.getObjectBrcb();
		int reportingCapacibiliyUrcbId = ReportingCapabilityManager.getObjectUrcb();
		int reportingCapacibiliyNoneId = ReportingCapabilityManager.getObjectNoneRcb();
		int reportingCapacibiliyBothId = ReportingCapabilityManager.getObjectBothRcb();
		TagMonitorIec61850 tagMonitor = null;
		
		for (Brcb modelNodercbs : serverModel.getBrcbs()) {
			String datasetOld = modelNodercbs.getDatSet().getStringValue();
			String datasetReferent = datasetOld.replace('$', '.');
			report = new BufferedRcb();
			report.setIedId(ied.getId());
			report.setReference(modelNodercbs.getReference().toString());				//UC_SSAACTRL/LLN0.brcbESTADOS2
			report.setDataset(datasetReferent);
			
			logger.info("datasetReferent: "+datasetReferent);
			GenericManager.saveObject(report);
			
			DataSet dataset = serverModel.getDataSet(datasetReferent);
		    for (ModelNode modelNode : dataset) {
		    	String tag = modelNode.getReference().toString();						//UC_SSAACTRL/GGIO2.Ind02
		    	//logger.info("iedId:"+ied.getId()+", telegramAddress: "+tag);
		    	tagMonitor = (TagMonitorIec61850) GenericManager.getFilteredObject(TagMonitorIec61850.class, 
		    			Arrays.asList(
		    					Restrictions.eq("iedId", ied.getId()) ,
		    					Restrictions.eq("telegramAddress", tag)) 
		    			);
		    	if(tagMonitor != null){
		    		//logger.info("tagMonitor: "+tagMonitor.getTelegramAddress()+", bufferedId: "+report.getId());
		    		tagMonitor.setBrcbId(report.getId());
		    		tagMonitor.setBuffered(true);
		    		
		    		int currentCapability = tagMonitor.getReportingCapacibiliyId();
		    		if(currentCapability == reportingCapacibiliyNoneId){
		    			tagMonitor.setReportingCapacibiliyId(reportingCapacibiliyBrcbId);
		    		}else if(currentCapability == reportingCapacibiliyUrcbId){
		    			tagMonitor.setReportingCapacibiliyId(reportingCapacibiliyBothId);
		    		}
		    		
		    		GenericManager.updateObject(tagMonitor);
		    	}
		    }
		
		}
		logger.info("fin");
	}

}

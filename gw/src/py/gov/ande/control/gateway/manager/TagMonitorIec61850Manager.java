package py.gov.ande.control.gateway.manager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaType;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.ServerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericManager;

public class TagMonitorIec61850Manager {

	private static final Logger logger = LoggerFactory.getLogger(TagMonitorIec61850Manager.class);
	
	/**
	 * guarda los datos de los tags encontrados de un ied
	 * @param ied
	 * @param serverModel
	 */
	public static void saveAllTagIec61850(Ied ied, ServerModel serverModel, Session session, Transaction tx){
		logger.info("inicio");
		TagMonitorIec61850 tag;
		int count = 0;
		
		int reportingCapacibiliyId = ReportingCapabilityManager.getObjectNoneRcb();
		logger.info("reportingCapacibiliyId: "+reportingCapacibiliyId);
		
		for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
			if(bda.getFc() == Fc.ST){
				if(bda.getBasicType() == BdaType.BOOLEAN){
					tag = new TagMonitorIec61850();
					tag.setIedId(ied.getId());
					tag.setTelegramAddress(bda.getParent().getReference().toString());	//UC_SSAACTRL/GGIO3.Ind01
					tag.setReportingCapacibiliyId(reportingCapacibiliyId);

					try {
						GenericManager.saveObject(tag, session);
						count++;
					} catch (Exception e) {
						logger.error("no se pudieron guardar los tags", e);
					}
					
				}
			}
		}
		logger.info("fin. Cantidad: "+count);
	}
	

}

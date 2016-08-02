package py.gov.ande.control.gateway.manager;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaType;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.ServerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.InformationType;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericManager;

public class TagMonitorIec61850Manager {

	private static final Logger logger = LoggerFactory.getLogger(TagMonitorIec61850Manager.class);
	
	/**
	 * Guarda los datos de los tags encontrados de un ied.
	 * Setea iedId, telegramAddress, reportingCapabilityId, informationTypeId
	 * @param ied
	 * @param serverModel
	 */
	public static void saveAllTagIec61850(Ied ied, ServerModel serverModel){
		logger.info("inicio");
		TagMonitorIec61850 tag;
		int count = 0;
		int sp = InformationTypeManager.getSpId();
		int dp = InformationTypeManager.getDpId();
		
		int reportingCapacibiliyId = ReportingCapabilityManager.getObjectNoneRcb();
		logger.info("reportingCapacibiliyId: "+reportingCapacibiliyId);
		
		for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
			if(bda.getFc() == Fc.ST){
				if(bda.getBasicType() == BdaType.BOOLEAN || bda.getBasicType() == BdaType.DOUBLE_BIT_POS){
					tag = new TagMonitorIec61850();
					tag.setIedId(ied.getId());
					tag.setTelegramAddress(bda.getParent().getReference().toString());	//UC_SSAACTRL/GGIO3.Ind01
					tag.setReportingCapacibiliyId(reportingCapacibiliyId);
					if(bda.getBasicType() == BdaType.BOOLEAN){
						tag.setInformationTypeId(sp);
					}else if(bda.getBasicType() == BdaType.DOUBLE_BIT_POS){
						tag.setInformationTypeId(dp);
					}

					try {
						GenericManager.saveObject(tag);
						count++;
					} catch (Exception e) {
						logger.error("no se pudieron guardar los tags", e);
					}
					
				}
			}
		}
		logger.info("fin. Cantidad: "+count);
	}

	/**
	 * Método que retorna el primer tag encontrado del ied, y extrae la parte de la izquierda del texto hasta la primera barra,
	 * asumiendo que es el nombre del IED.
	 * Si no encuentra ningún tag, retorna el texto "rename ied".
	 * @param id ied
	 * @return String name
	 */
	public static String getFirstElement(int id) {
		TagMonitorIec61850 object = null;
		object = (TagMonitorIec61850) GenericManager.getFilteredObject(TagMonitorIec61850.class, 
    			Arrays.asList(
    					Restrictions.eq("iedId", id)
				));
		if(object != null){
			String[] name = object.getTelegramAddress().split("/");
			System.out.println(name[0]);
			return name[0];
		}else {
			return "Rename Ied";
		}
	}

	/**
	 * Método que retorna la lista de tags de un Ied
	 * @param ied
	 * @return List<TagMonitorIec61850>
	 */
	public static List<TagMonitorIec61850> getAllObjects(Ied ied) {
		
		return GenericManager.getListBasedOnCriteria("from TagMonitorIec61850 as tag where tag.iedId = "+ied.getId());
	}
	

}

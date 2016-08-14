package py.gov.ande.control.gateway.manager;

import java.util.ArrayList;
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
import py.gov.ande.control.gateway.model.IedOperation;
import py.gov.ande.control.gateway.model.InformationType;
import py.gov.ande.control.gateway.model.ReportingCapability;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.model.TagMonitorIec61850Operation;
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
		InformationType sp = InformationTypeManager.getSpId();
		InformationType dp = InformationTypeManager.getDpId();
		
		ReportingCapability reportingCapacibility = ReportingCapabilityManager.getObjectNoneRcb();
		logger.info("reportingCapacibiliyId: "+reportingCapacibility);
		
		for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
			if(bda.getFc() == Fc.ST){
				if(bda.getBasicType() == BdaType.BOOLEAN || bda.getBasicType() == BdaType.DOUBLE_BIT_POS){
					tag = new TagMonitorIec61850();
					tag.setIed(ied);
					tag.setTelegramAddress(bda.getParent().getReference().toString());	//UC_SSAACTRL/GGIO3.Ind01
					tag.setReportingCapability(reportingCapacibility);
					tag.setUse(false);
					tag.setBuffered(false);
					tag.setUnbuffered(false);
					if(bda.getBasicType() == BdaType.BOOLEAN){
						tag.setInformationType(sp);
					}else if(bda.getBasicType() == BdaType.DOUBLE_BIT_POS){
						tag.setInformationType(dp);
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
		logger.info("inicio. Id: "+id);
		Ied ied = GenericManager.getObjectById(Ied.class, id);
		TagMonitorIec61850 object = null;
		/*object = (TagMonitorIec61850) GenericManager.getFilteredObject(TagMonitorIec61850.class, 
    			Arrays.asList(
    					Restrictions.eq("iedId", id)
				));*/
		List<TagMonitorIec61850> listTags = getAllObjects(ied);
		logger.info("cantidad de tags: "+listTags.size());
		object = listTags.get(0);
		
		if(object != null){
			String[] name = object.getTelegramAddress().split("/");
			logger.info("getTelegramAddress().split: "+name[0]);
			return name[0];
		}else {
			logger.error("no se encontraron tags para obtener el nombre del equipo");
			return "Rename Ied";
		}
	}

	/**
	 * Método que retorna la lista de tags de un Ied
	 * @param ied
	 * @return List<TagMonitorIec61850>
	 */
	public static List<TagMonitorIec61850> getAllObjects(Ied ied) {
		logger.info("inicio");
		//return GenericManager.getListBasedOnCriteria("from TagMonitorIec61850 as tag where tag.iedId = "+ied.getId());
		return GenericManager.getListBasedOnCriteria("select tag From TagMonitorIec61850 as tag inner join tag.ied as ied where ied.id = "+ied.getId());
	}
	
	/**
	 * Devuelve una lista de string con el nombre de las columnas
	 * @return ArrayList<String>
	 * @date 2016-08-01
	 */
	public static String[] getColumnNames(){
		return GenericManager.getColumnNames(TagMonitorIec61850.class);

	}

	/**
	 * Método que actualiza el campo Use de un tag.
	 * @param id
	 * @param Object valueAt
	 */
	public static void updateTag(int id, Object valueAt) {
		TagMonitorIec61850 tag = GenericManager.getObjectById(TagMonitorIec61850.class, id);
		tag.setUse(Boolean.valueOf((boolean) valueAt));
		GenericManager.updateObject(tag);
	}

	/**
	 * Método que retorna la lista de tags seleccionados que no se encuentren dentro de ningún reporte
	 * @param ied
	 * @return List<TagMonitorIec61850Operation>
	 * @author Pablo
	 * @date 2016-08-13
	 */
	public static List<TagMonitorIec61850Operation> getAllTagsWithOutAnyReport(IedOperation ied) {
		ReportingCapability rc = ReportingCapabilityManager.getObjectNoneRcb();

		return GenericManager.getListBasedOnCriteria(
				"select tag "
				+ "From TagMonitorIec61850Operation as tag "
				+ "inner join tag.iedOperation as ied "
				+ "where "
				+ "ied.id = "+ied.getId()+ "and "
				+ "tag.use = true and "
				+ "tag.buffered = false and "
				+ "tag.unbuffered = false");
	}

}

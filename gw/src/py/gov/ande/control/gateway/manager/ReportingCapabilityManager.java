package py.gov.ande.control.gateway.manager;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import py.gov.ande.control.gateway.model.ReportingCapability;
import py.gov.ande.control.gateway.util.GenericManager;

public class ReportingCapabilityManager {

	/**
	 * método que retorna el id del registro de capacidad de reporte correspondiente a Buffer
	 * @return int
	 * @author pablo
	 */
	public static Integer getObjectBrcb() {
		ReportingCapability reporte = (ReportingCapability) GenericManager.getFilteredObject(ReportingCapability.class, 
    			Arrays.asList(
    					Restrictions.eq("brcb", true) ,
    					Restrictions.eq("urcb", false)) 
				);
		if(reporte != null){
			return reporte.getId();
		}else{
			return 0;
		}
	}	
	
	/**
	 * método que retorna el id del registro de capacidad de reporte correspondiente a UnBuffer
	 * @return int
	 * @author pablo
	 */
	public static Integer getObjectUrcb() {
		ReportingCapability reporte = (ReportingCapability) GenericManager.getFilteredObject(ReportingCapability.class, 
    			Arrays.asList(
    					Restrictions.eq("brcb", false) ,
    					Restrictions.eq("urcb", true)) 
				);
		if(reporte != null){
			return reporte.getId();
		}else{
			return 0;
		}
	}
	
	/**
	 * método que retorna el id del registro de capacidad de reporte correspondiente a Ninguno
	 * @return
	 * @author pablo
	 */
	public static Integer getObjectNoneRcb() {
		ReportingCapability reporte = (ReportingCapability) GenericManager.getFilteredObject(ReportingCapability.class, 
    			Arrays.asList(
    					Restrictions.eq("brcb", false) ,
    					Restrictions.eq("urcb", false)) 
				);
		if(reporte != null){
			return reporte.getId();
		}else{
			return 0;
		}
	}
	
	/**
	 * método que retorna el id del registro de capacidad de reporte correspondiente a Ambos
	 * @return
	 * @author pablo
	 */
	public static Integer getObjectBothRcb() {
		ReportingCapability reporte = (ReportingCapability) GenericManager.getFilteredObject(ReportingCapability.class, 
    			Arrays.asList(
    					Restrictions.eq("brcb", true) ,
    					Restrictions.eq("urcb", true)) 
				);
		if(reporte != null){
			return reporte.getId();
		}else{
			return 0;
		}
	}
}

package py.gov.ande.control.gateway.manager;

import java.util.Arrays;

import org.hibernate.criterion.Restrictions;

import py.gov.ande.control.gateway.model.InformationType;
import py.gov.ande.control.gateway.util.GenericManager;

public class InformationTypeManager {

	/**
	 * Metodo que obtiene el id del tipo de información Simple Point
	 * @author Pablo
	 * @date 2016-07-25
	 * @version 1.0
	 * @return int
	 */
	public static InformationType getSpId() {
		InformationType type = (InformationType) GenericManager.getFilteredObject(InformationType.class, 
    			Arrays.asList(
    					Restrictions.eq("description", "SP") 
				));
		if(type != null){
			return type;
		}else{
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene el id del tipo de información Double Point
	 * @author Pablo
	 * @date 2016-07-25
	 * @version 1.0
	 * @return int
	 */
	public static InformationType getDpId() {
		InformationType type = (InformationType) GenericManager.getFilteredObject(InformationType.class, 
    			Arrays.asList(
    					Restrictions.eq("description", "DP") 
				));
		if(type != null){
			return type;
		}else{
			return null;
		}
	}	


}

package py.gov.ande.control.gateway.configuration;

/**
 * Informacion de Drivers e IED que se utilizaran para listar el arbol de configuracion
 * @param String driverName;
 * @param Boolean iec61850;
 * @param Boolean iec101;
 * @param Boolean ied;
 * @author root
 *
 */
public class DriverInfo {
	private int id;
    private String driverName;
    private Boolean iec61850;
    private Boolean iec101;
    private Boolean ied;

    public DriverInfo(){
    	id = 0;
        driverName = "";
    	iec61850 = false;
    	iec101 = false;
    	ied = false;
    }
    
    public DriverInfo(Integer idS, String driverNameS, Boolean iec61850S, Boolean iec101S, Boolean iedS){
    	id = idS;
        driverName = driverNameS;
    	iec61850 = iec61850S;
    	iec101 = iec101S;
    	ied = iedS;
    }

    public String toString() {
        return driverName;
    }
    
    public Boolean getIec61850(){
    	return iec61850;
    }
} 
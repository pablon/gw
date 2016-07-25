package gateway;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaBoolean;
import org.openmuc.openiec61850.BdaFloat32;
import org.openmuc.openiec61850.BdaInt16;
import org.openmuc.openiec61850.BdaQuality;
import org.openmuc.openiec61850.BdaReasonForInclusion;
import org.openmuc.openiec61850.BdaTimestamp;
import org.openmuc.openiec61850.BdaTriggerConditions;
import org.openmuc.openiec61850.BdaType;
import org.openmuc.openiec61850.Brcb;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientAssociation.ClientReceiver;
import org.openmuc.openiec61850.internal.mms.asn1.AccessResult;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.FcModelNode;
import org.openmuc.openiec61850.ModelNode;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ReportEntryData;
import org.openmuc.openiec61850.SclParseException;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.openmuc.openiec61850.Urcb;
import org.slf4j.Logger;		//agregue la libreria slf4j-jdk14 para que funcione
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.manager.ReportingCapabilityManager;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericManager;

public class SampleClientFile  implements ClientEventListener {

	private static final Logger logger = LoggerFactory.getLogger(SampleClient.class);
	
	public static void main(String[] args) throws ServiceError, IOException  {
		
		System.out.println("dentro de la clase gateway.SampleClientFile");
        String usageString = "usage: org.openmuc.openiec61850.sample.SampleClient <host> <port>";
/*      if (args.length != 2) {
            System.out.println(usageString);
            return;
        } */
        String remoteHost = "127.0.0.1";
        //String remoteHost = "10.2.28.231";
        InetAddress address;
        try {
            address = InetAddress.getByName(remoteHost);
        } catch (UnknownHostException e) {
            logger.error("Unknown host: " + remoteHost);
            return;
        }
        int remotePort;
        try {
        	remotePort = Integer.parseInt("102");
        } catch (NumberFormatException e) {
            System.out.println(usageString);
            return;
        }		
        ClientSap clientSap = new ClientSap();
        SampleClientFile eventHandler = new SampleClientFile();
        ClientAssociation association;        
        logger.info("Attempting to connect to server " + remoteHost + " on port " + remotePort);
        try {
            association = clientSap.associate(address, remotePort, null, eventHandler);
            logger.info("Conectado con exito");
        } catch (IOException e) {
            logger.error("Error connecting to server: " + e.getMessage());
            return;
        }        
       
        ServerModel serverModel;
        try {
            //serverModel = association.getModelFromSclFile("/home/pn/Documentos/uc_lt2.cid");
            //serverModel = association.getModelFromSclFile("/home/pn/Documentos/respaldo/openiec61850/run-scripts/sample/sample-model.icd");
        	serverModel = association.getModelFromSclFile("/home/pn/Documentos/Siprotec_WR24_F003.cid");
            
        } catch (SclParseException e1) {
            logger.error("Fatal IOException requesting model.", e1);
            return;
        }
        
		TagMonitorIec61850 tag;
		int count = 1;
		
		for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
			if(bda.getFc() == Fc.ST){
				if(bda.getBasicType() == BdaType.BOOLEAN){
					System.out.println("Tag Nro "+count+": "+bda.getParent().getReference().toString());	//UC_SSAACTRL/GGIO3.Ind01);
					count++;
				}
			}
		}
		System.out.println("fin. Cantidad: "+count);
        association.disconnect();
        System.out.println("fin del MAIN");
	}
	
    @Override
    public void newReport(Report report) {
        //logger.info("got report with dataset ref: " + report.getDataSet().getReferenceStr());
        //if(report.)
        //	System.out.println("report.getEntryId().getFc(): " + report.getEntryId().getValue());
    	//report.getDataSet().getBasicDataAttributes().
    	
    	//----lista los miembros del dataSet del reporte
/*    	List<FcModelNode> miembros = report.getDataSet().getMembers();
     	for (FcModelNode fcModelNode : miembros) {
    		System.out.println("referencia(): " + fcModelNode.getReference());
		}*/    
     	
/*        for(int i=0; i<report.getDataSet().getMembers().size();i++){
        	System.out.println("report.getDataSet().getMember(i):" +i + ": " + report.getDataSet().getMember(i));
        }*/   
    	
    	//getAllBdas(report.getDataSet().);
    	
     	
     	
        List<BdaReasonForInclusion> razon = report.getReasonCodes();
        for (BdaReasonForInclusion bdaReasonForInclusion : razon) {
        	if(bdaReasonForInclusion.isDataChange()){
        		System.out.println("got REPORT with dataset ref: " + report.getDataSet().getReferenceStr());	//UC_SSAACTRL/LLN0.ESTADOS
        		System.out.println("isDataChange: ");
        		imprimeReporte(report);
       		
        	}
        	if(bdaReasonForInclusion.isQualityChange()){
        		System.out.println("isQualityChange");
        		imprimeReporte(report);
        	}
        	if(bdaReasonForInclusion.isDataUpdate()){
        		System.out.println("isDataUpdate");
        		imprimeReporte(report);
        	}
        	
        	if(bdaReasonForInclusion.isIntegrity()){
        		//System.out.println("isIntegrity");
        		//imprimeReporte(report);
        	}
        	if(bdaReasonForInclusion.isGeneralInterrogation()){
        		System.out.println("isGeneralInterrogation");
        		imprimeReporte(report);
        	}
        	if(bdaReasonForInclusion.isApplicationTrigger()){
        		System.out.println("isApplicationTrigger");
        		imprimeReporte(report);
        	}
        		//bdaReasonForInclusion.
		}
        //UC_SSAACTRL/LLN0.ESTADOS UC_SSAACTRL/GGIO3.Ind01.stVal
    }

    @Override
    public void associationClosed(IOException e) {
        logger.info("Association was closed");
    }
    
    private void imprimeReporte(Report report){
        System.out.println("getRptId: " + 				report.getRptId());			//UC_SSAACTRL/LLN0$BR$brcbESTADOS
        System.out.println("getTimeOfEntry: " + 		report.getTimeOfEntry());
        System.out.println("getSqNum: " + 				report.getSqNum());
        System.out.println("getConfRev: " + 			report.getConfRev());
        System.out.println("getInclusionBitString: " + 	report.getInclusionBitString());	//[B@18cf45c. ed2_2 false
        System.out.println("getDataSetRef(): " + 		report.getDataSetRef());			//UC_SSAACTRL/LLN0$ESTADOS2
        System.out.println("getReferenceStr(): " + 		report.getDataSet().getReferenceStr());	//UC_SSAACTRL/LLN0.ESTADOS2
        System.out.println("getEntryId(): " + 	     	report.getEntryId());		// none: [0, 0, 0, 1, 0, 0, 0, 0]
        System.out.println("prueba de dataSet:");
        
        //Agregado para filtrar los supuestos cambios en uno o mas nodos recibidos
        
        
        byte[] inclusionBitString = report.getInclusionBitString();
        int shiftNum = 0;
        BdaBoolean bStVal;
        for (FcModelNode child : report.getDataSet().getMembers()) {
            if ((inclusionBitString[shiftNum / 8] & (1 << (7 - shiftNum % 8))) == (1 << (7 - shiftNum % 8))) {

            	System.out.println ("child.getName(): " + child.getName() );				//Ind03
            	System.out.println ( "child.getReference():" + child.getReference() );		//UC_SSAACTRL/GGIO2.Ind03
            	//[UC_SSAACTRL/GGIO2.Ind03.stVal: true, UC_SSAACTRL/GGIO2.Ind03.q: 0x0, 0x0, UC_SSAACTRL/GGIO2.Ind03.t: Wed Jun 01 21:33:11 PYT 2016]
            	System.out.println ( "child.getBasicDataAttributes(): " + child.getBasicDataAttributes().toString() );
            	
            	//UC_SSAACTRL/GGIO2.Ind03.stVal: true
            	System.out.println ( "child.getBasicDataAttributes().get(0): " + child.getBasicDataAttributes().get(0));
            	bStVal = (BdaBoolean) child.getBasicDataAttributes().get(0);
            	System.out.println ( "bStVal: " + bStVal.getValue());
    
/*                    List<BasicDataAttribute> subBasicDataAttributes = new LinkedList<BasicDataAttribute>();
                    for (ModelNode child1 : child.values()) {
                        subBasicDataAttributes.addAll(child1.getBasicDataAttributes());
                    }
                    return subBasicDataAttributes;*/
            	
            	
            	
            	//BOOLEAN
            	System.out.println ( "child.getBasicDataAttributes().get(0).getBasicType(): " + child.getBasicDataAttributes().get(0).getBasicType() );
            	System.out.println ( "child.getBasicDataAttributes().get(0).getName(): " + child.getBasicDataAttributes().get(0).getName());//stVal
            	System.out.println ( "child.getBasicDataAttributes().get(0).getSAddr(): " + child.getBasicDataAttributes().get(0).getSAddr());//null
            	System.out.println ( "child.getBasicDataAttributes().get(0).getClass(): " + child.getBasicDataAttributes().get(0).getClass());//class org.openmuc.openiec61850.BdaBoolean
            	System.out.println ( "child.getBasicDataAttributes().get(0).getFc(): " + child.getBasicDataAttributes().get(0).getFc() );	//ST
            	System.out.println ( "child.getBasicDataAttributes().get(0).getParent(): " + child.getBasicDataAttributes().get(0).getParent() );//UC_SSAACTRL/GGIO2.Ind02 [ST]
            	System.out.println ( "child.getBasicDataAttributes().get(0).getReference(): " + child.getBasicDataAttributes().get(0).getReference() );//UC_SSAACTRL/GGIO2.Ind02.stVal
            }
            shiftNum++;
        }
    	
    }
    
}


/* PARA ANALIZAR
 * getAllBdas(serverModel);
 * 
 *     private void getAllBdas(ServerModel serverModel) throws ServiceError, IOException {

        for (ModelNode ld : serverModel) {
            for (ModelNode ln : ld) {
                getDataRecursive(ln, clientAssociation);
            }
        }
    }
    
    private static void getDataRecursive(ModelNode modelNode, ClientAssociation clientAssociation)
            throws ServiceError, IOException {
        if (modelNode.getChildren() == null) {
            return;
        }
        for (ModelNode childNode : modelNode) {
            FcModelNode fcChildNode = (FcModelNode) childNode;
            if (fcChildNode.getFc() != Fc.CO) {
                logger.debug("calling GetDataValues(" + childNode.getReference() + ")");
                clientAssociation.getDataValues(fcChildNode);
            }
            // clientAssociation.setDataValues(fcChildNode);
            getDataRecursive(childNode, clientAssociation);
        }
    }    
    
 * */

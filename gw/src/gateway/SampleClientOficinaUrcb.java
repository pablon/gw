package gateway;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaFloat32;
import org.openmuc.openiec61850.BdaInt16;
import org.openmuc.openiec61850.BdaQuality;
import org.openmuc.openiec61850.BdaTimestamp;
import org.openmuc.openiec61850.Brcb;
import org.openmuc.openiec61850.ClientAssociation;
import org.openmuc.openiec61850.ClientEventListener;
import org.openmuc.openiec61850.ClientSap;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.FcModelNode;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServiceError;
import org.openmuc.openiec61850.Urcb;
import org.slf4j.Logger;		//agregue la libreria slf4j-jdk14 para que funcione
import org.slf4j.LoggerFactory;

public class SampleClientOficinaUrcb  implements ClientEventListener {

	private static final Logger logger = LoggerFactory.getLogger(SampleClient.class);
	
	public static void main(String[] args) throws ServiceError, IOException  {
		
		System.out.println("dentro de la clase gateway.SampleClient");

        String usageString = "usage: org.openmuc.openiec61850.sample.SampleClient <host> <port>";
/*
        if (args.length != 2) {
            System.out.println(usageString);
            return;
        }
*/
        //String remoteHost = "127.0.0.1";
        String remoteHost = "10.2.28.231";
        InetAddress address;
        try {
            address = InetAddress.getByName(remoteHost);
        } catch (UnknownHostException e) {
            logger.error("Unknown host: " + remoteHost);
            return;
        }

        int remotePort;
        try {
//            remotePort = Integer.parseInt("10002");
        	remotePort = Integer.parseInt("102");
        } catch (NumberFormatException e) {
            System.out.println(usageString);
            return;
        }		
        
        ClientSap clientSap = new ClientSap();
        SampleClient eventHandler = new SampleClient();
        ClientAssociation association;        
        logger.info("Attempting to connect to server " + remoteHost + " on port " + remotePort);
        //System.out.println("Conectando al servidor " + remoteHost + " on port " + remotePort);
        try {
            association = clientSap.associate(address, remotePort, null, eventHandler);
        } catch (IOException e) {
            logger.error("Error connecting to server: " + e.getMessage());
            //System.out.println("Error conectando al servidor"+ e.getMessage());
            return;
        }        
        
        ServerModel serverModel;
        try {
            serverModel = association.retrieveModel();
        } catch (ServiceError e) {
            logger.error("Service Error requesting model.", e);
            association.close();
            return;
        } catch (IOException e) {
            logger.error("Fatal IOException requesting model.", e);
            return;
        }
    
        association.getAllDataValues();
        for (BasicDataAttribute bda : serverModel.getBasicDataAttributes()) {
            //logger.debug("BDA: " + bda);
        	//System.out.println("BDA: "+ bda);
        }       
        System.out.println("Fin de exploracion del servidor");
        
        System.out.println("inicio de interrogacion general");
            
        Urcb urcb = serverModel.getUrcb("UC_SSAACTRL/LLN0.urcbMEDIDAS");
        if (urcb == null) {
            logger.error("ReportControlBlock not found");
        }
        else {
            association.getRcbValues(urcb);
            logger.info("urcb name: " + urcb.getName());
            logger.info("RptId: " + urcb.getRptId());
            logger.info("RptEna: " + urcb.getRptEna().getValue());
            
            System.out.println("urcb name: " + urcb.getName());
            System.out.println("RptId: " + urcb.getRptId());
            System.out.println("RptEna: " + urcb.getRptEna().getValue());
            
            association.reserveUrcb(urcb);
            association.enableReporting(urcb);
            association.startGi(urcb);
            association.disableReporting(urcb);
            association.cancelUrcbReservation(urcb);
            

        }        
        
/*        FcModelNode totW = (FcModelNode) serverModel.findModelNode("ied1lDevice1/MMXU1.TotW", Fc.MX);
        BdaFloat32 totWmag = (BdaFloat32) totW.getChild("mag").getChild("f");
        BdaTimestamp totWt = (BdaTimestamp) totW.getChild("t");
        BdaQuality totWq = (BdaQuality) totW.getChild("q");      
        
        while (true) {
            association.getDataValues(totW);
            logger.info("Logger - got totW: mag " + totWmag.getFloat() + ", time " + totWt.getDate() + ", quality " + totWq.getValidity());
           
            //System.out.println("PrintLn - got totW: mag " + totWmag.getFloat() + ", time " + totWt.getDate() + ", quality " + totWq.getValidity());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

        }        
*/        
        System.out.println("fin del MAIN");
	}
	
    @Override
    public void newReport(Report report) {
        logger.info("got report with dataset ref: " + report.getDataSet().getReferenceStr());
        System.out.println("do something with the report");
        //UC_SSAACTRL/LLN0.ESTADOS UC_SSAACTRL/GGIO3.Ind01.stVal
    }

    @Override
    public void associationClosed(IOException e) {
        logger.info("Association was closed");
    }	
	
}

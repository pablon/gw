package py.gov.ande.control.gateway.driverUtil;

import java.io.IOException;
import java.util.List;

import org.openmuc.openiec61850.BdaBoolean;
import org.openmuc.openiec61850.BdaReasonForInclusion;
import org.openmuc.openiec61850.FcModelNode;
import org.openmuc.openiec61850.ModelNode;
import org.openmuc.openiec61850.Report;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.gov.ande.control.gateway.model.BufferedRcbOperation;
import py.gov.ande.control.gateway.model.IedOperation;

public class BrcbRunnable extends DriverIec61850 implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(BrcbRunnable.class);

	public BrcbRunnable() {
		// TODO Auto-generated constructor stub
	}
	
	public BrcbRunnable(IedOperation ied, BufferedRcbOperation brcb) throws IOException, ServiceError {
		BrcbRunnable eventHandler = new BrcbRunnable();
		
		setDriverIec61850Parameters(ied.getIpAddress(), ied.getPortAddress(), 0, 0);
		checkIed();
		doAssociate(eventHandler);
		brcb(brcb.getReference());
		optFlds();
		setRcbValues();

		//enableThread();
		//disableReporting();
		//disconnectToIed();
	}

	@Override
	public void run() {
		logger.info("inicio Run");
		//try {
			//Thread.sleep(10000);
			try {
				logger.info("enableReporting");
				enableReporting();
			} catch (ServiceError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				logger.info("enableGI");
				enableGi();
			} catch (ServiceError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setEnableReport(true);
			
			try {
				enableThread();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ServiceError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		//	logger.error("error");
		//	e.printStackTrace();
		//}
		/*	try {
				disableReporting();
			} catch (ServiceError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			disconnectToIed();*/
		logger.info("fin");

	}

	@Override
	public void newReport(Report report) {
		//logger.info("got report with dataset ref: " + report.getDataSet().getReferenceStr());
		
        List<BdaReasonForInclusion> razon = report.getReasonCodes();
        for (BdaReasonForInclusion bdaReasonForInclusion : razon) {
        	if(bdaReasonForInclusion.isDataChange()){
        		System.out.println("got REPORT with dataset ref: " + report.getDataSet().getReferenceStr());	//UC_SSAACTRL/LLN0.ESTADOS
        		System.out.println("isDataChange: ");
        		imprimeReporte(report);
        	}
        	if(bdaReasonForInclusion.isGeneralInterrogation()){
        		System.out.println("isGeneralInterrogation");
        		imprimeReporte(report);
        	}
        }
	}

	@Override
	public void associationClosed(IOException e) {
		// TODO Auto-generated method stub
		
	}
	
    private void imprimeReporte(Report report){
        /*System.out.println("getRptId: " + 				report.getRptId());			//UC_SSAACTRL/LLN0$BR$brcbESTADOS
        System.out.println("getTimeOfEntry: " + 		report.getTimeOfEntry());
        System.out.println("getSqNum: " + 				report.getSqNum());
        System.out.println("getConfRev: " + 			report.getConfRev());
        System.out.println("getInclusionBitString: " + 	report.getInclusionBitString());	//[B@18cf45c. ed2_2 false
        System.out.println("getDataSetRef(): " + 		report.getDataSetRef());			//UC_SSAACTRL/LLN0$ESTADOS2
        System.out.println("getReferenceStr(): " + 		report.getDataSet().getReferenceStr());	//UC_SSAACTRL/LLN0.ESTADOS2
        System.out.println("getEntryId(): " + 	     	report.getEntryId());		// none: [0, 0, 0, 1, 0, 0, 0, 0]
        System.out.println("prueba de dataSet:");*/
        
        //Agregado para filtrar los supuestos cambios en uno o mas nodos recibidos
        
        
        byte[] inclusionBitString = report.getInclusionBitString();
        int shiftNum = 0;
        BdaBoolean bStVal;
        for (FcModelNode child : report.getDataSet().getMembers()) {
            if ((inclusionBitString[shiftNum / 8] & (1 << (7 - shiftNum % 8))) == (1 << (7 - shiftNum % 8))) {

            	//System.out.println ("child.getName(): " + child.getName() );				//Ind03
            	System.out.print ( "child.getReference():" + child.getReference() + ", " );		//UC_SSAACTRL/GGIO2.Ind03
            	//[UC_SSAACTRL/GGIO2.Ind03.stVal: true, UC_SSAACTRL/GGIO2.Ind03.q: 0x0, 0x0, UC_SSAACTRL/GGIO2.Ind03.t: Wed Jun 01 21:33:11 PYT 2016]
            	//System.out.println ( "child.getBasicDataAttributes(): " + child.getBasicDataAttributes().toString() );
            	
            	//UC_SSAACTRL/GGIO2.Ind03.stVal: true
            	//System.out.println ( "child.getBasicDataAttributes().get(0): " + child.getBasicDataAttributes().get(0));
            	bStVal = (BdaBoolean) child.getBasicDataAttributes().get(0);
            	System.out.println ( "bStVal: " + bStVal.getValue());
    
            	//BOOLEAN
            	/*System.out.println ( "child.getBasicDataAttributes().get(0).getBasicType(): " + child.getBasicDataAttributes().get(0).getBasicType() );
            	System.out.println ( "child.getBasicDataAttributes().get(0).getName(): " + child.getBasicDataAttributes().get(0).getName());//stVal
            	System.out.println ( "child.getBasicDataAttributes().get(0).getSAddr(): " + child.getBasicDataAttributes().get(0).getSAddr());//null
            	System.out.println ( "child.getBasicDataAttributes().get(0).getClass(): " + child.getBasicDataAttributes().get(0).getClass());//class org.openmuc.openiec61850.BdaBoolean
            	System.out.println ( "child.getBasicDataAttributes().get(0).getFc(): " + child.getBasicDataAttributes().get(0).getFc() );	//ST
            	System.out.println ( "child.getBasicDataAttributes().get(0).getParent(): " + child.getBasicDataAttributes().get(0).getParent() );//UC_SSAACTRL/GGIO2.Ind02 [ST]
            	System.out.println ( "child.getBasicDataAttributes().get(0).getReference(): " + child.getBasicDataAttributes().get(0).getReference() );//UC_SSAACTRL/GGIO2.Ind02.stVal
            	*/
            	/*for (ModelNode modelNode : child.getBasicDataAttributes()) {
					System.out.println("forEach child.getBasicDataAttributes: "+modelNode);	// stval, quality y tiempo
				}*/
            }
            shiftNum++;
        }
    	
    }

}

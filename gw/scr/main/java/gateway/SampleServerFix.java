package gateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openmuc.openiec61850.BasicDataAttribute;
import org.openmuc.openiec61850.BdaFloat32;
import org.openmuc.openiec61850.BdaQuality;
import org.openmuc.openiec61850.BdaQuality.Validity;
import org.openmuc.openiec61850.BdaTimestamp;
import org.openmuc.openiec61850.Fc;
import org.openmuc.openiec61850.SclParseException;
import org.openmuc.openiec61850.ServerEventListener;
import org.openmuc.openiec61850.ServerModel;
import org.openmuc.openiec61850.ServerSap;
import org.openmuc.openiec61850.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Pablo
 * 
 */
public class SampleServerFix implements ServerEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SampleServerFix.class);

    private static ServerSap serverSap = null;

    public static void main(String[] args) throws IOException {

        int port = 102;

        List<ServerSap> serverSaps = null;
        try {
            serverSaps = ServerSap.getSapsFromSclFile("/home/pn/Documentos/Siprotec_WR24_F003.cid");
        } catch (SclParseException e) {
            logger.warn("Error parsing SCL/ICD file: " + e.getMessage());
            return;
        }

        serverSap = serverSaps.get(0);
        serverSap.setPort(port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (serverSap != null) {
                    serverSap.stop();
                }
                logger.error("Server was stopped.");
            }
        });

        ServerModel serverModel = serverSap.getModelCopy();

        // create a SampleServer instance that can be passed as a callback object to startListening() and
        // setDefaultWriteListener()
        SampleServerFix sampleServer = new SampleServerFix();

        serverSap.startListening(sampleServer);

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

    }

    @Override
    public void serverStoppedListening(ServerSap serverSap) {
        logger.error("The SAP stopped listening");
        serverSap.stop();
    }

    @Override
    public List<ServiceError> write(List<BasicDataAttribute> bdas) {
        for (BasicDataAttribute bda : bdas) {
            logger.info("got a write request: " + bda);
        }
        return null;

    }

}

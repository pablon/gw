package py.gov.ande.control.gateway.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TestConnections {

	public TestConnections() {
		
	}

	/**
	 * Test de conexión tipo Ping ICMP
	 * @param address
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static boolean testConnection(InetAddress address) throws UnknownHostException, IOException {
	    try {
	    	if(address.isReachable(5000)){
	    		//System.out.println("Host is reachable");
	    		return true;
	    	}
		} catch (Exception e) {
			//System.out.println("Host is NOT reachable");
			return false;
		}
	    return false;
	}

}



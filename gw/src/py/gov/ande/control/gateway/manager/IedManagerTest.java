package py.gov.ande.control.gateway.manager;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class IedManagerTest {

	@Test
	public final void testFindIedForIpAddress() throws UnknownHostException {
		assert(IedManager.findIedForIpAddress("10.2.28.231"));
		assert(IedManager.findIedForIpAddress("127.0.0.1"));
		assertFalse(IedManager.findIedForIpAddress(null));
	}
	
	@Test
	public final void testDeleteIed() {
		assert(IedManager.deleteIed(79));
		assertFalse(IedManager.deleteIed(80));
	}

}

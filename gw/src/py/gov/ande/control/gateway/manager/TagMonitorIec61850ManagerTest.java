package py.gov.ande.control.gateway.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagMonitorIec61850ManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetFirstElement() {
		String val = TagMonitorIec61850Manager.getFirstElement(79);
		assertNotEquals("UC_SSAACTRL/ETSW1.Port1linkB", val);
		assertEquals("UC_SSAACTRL", val);
	}

}

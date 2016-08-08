package py.gov.ande.control.gateway.manager;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.criterion.Order;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import py.gov.ande.control.gateway.model.Ied;
import py.gov.ande.control.gateway.model.TagMonitorIec61850;
import py.gov.ande.control.gateway.util.GenericManager;

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
		String val = TagMonitorIec61850Manager.getFirstElement(102);
		assertNotEquals("UC_SSAACTRL/ETSW1.Port1linkB", val);
		assertEquals("UC_SSAACTRL", val);
	}
	
	@Test
	public final void testGetColumnNames(){
		String [] names = TagMonitorIec61850Manager.getColumnNames();
		for (String string : names) {
			System.out.println("name: "+string);
		}
		assertEquals("bufferedRcb", names[0]);
	}
	
	@Test
	public final void testGetAllObjects(){
		List<Ied> iedList = GenericManager.getAllObjects(Ied.class, Order.asc("id"));
		assertEquals("2: ", 2, iedList.size());

		
		
	}

}

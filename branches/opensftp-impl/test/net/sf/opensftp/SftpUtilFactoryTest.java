package net.sf.opensftp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SftpUtilFactoryTest extends SftpUtilFactory{

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetSftpUtilClassName() {
		SftpUtilFactory.setSftpUtilClassName(null);
		SftpUtilFactory.setSftpUtilClassName("");
		SftpUtilFactory.setSftpUtilClassName("a");
		SftpUtilFactory.setSftpUtilClassName(" b ");
		assertTrue(SftpUtilFactory.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
	}

	@Test
	public void testGetSftpUtilClassName() {
		SftpUtilFactory.setSftpUtilClassName("a");
		assertTrue(SftpUtilFactory.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
	}

	@Test
	public void testGetSftpUtil() {

	}

}

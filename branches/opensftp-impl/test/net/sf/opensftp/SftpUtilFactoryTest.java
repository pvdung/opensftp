package net.sf.opensftp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SftpUtilFactoryTest extends SftpUtilFactoryDup {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() {
		SftpUtilFactoryDup.sftpUtil = null;
		SftpUtilFactoryDup.sftpUtilInitialized = false;
		SftpUtilFactoryDup.sftpUtilClassName = null;
		SftpUtilFactoryDup.sftpUtilClassNameInitialized = false;
	}

	@Test
	public void testGetSftpUtilClassName() {
		assertTrue(SftpUtilFactoryDup.getSftpUtilClassName().equals("net.sf.opensftp.impl.SftpUtil"));
		tearDown();
		SftpUtilFactoryDup.setSftpUtilClassName(" net.sf.opensftp.SftpUtilMock ");
		assertTrue(SftpUtilFactoryDup.getSftpUtilClassName().equals("net.sf.opensftp.SftpUtilMock"));
	}

	@Test
	public void testGetSftpUtil() {
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
		tearDown();
		SftpUtilFactoryDup.setSftpUtilClassName(" net.sf.opensftp.SftpUtilMock ");
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.SftpUtilMock);
	}
	
	@Test
	public void testSetSftpUtilClassName() {
		SftpUtilFactoryDup.setSftpUtilClassName(null);
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
		tearDown();
		SftpUtilFactoryDup.setSftpUtilClassName("");
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
		tearDown();
		SftpUtilFactoryDup.setSftpUtilClassName(" a ");
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.impl.SftpUtil);
		tearDown();
		SftpUtilFactoryDup.setSftpUtilClassName(" net.sf.opensftp.SftpUtilMock ");
		assertTrue(SftpUtilFactoryDup.getSftpUtil() instanceof net.sf.opensftp.SftpUtilMock);
	}

}

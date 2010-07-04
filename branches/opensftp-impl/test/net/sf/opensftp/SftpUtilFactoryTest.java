package net.sf.opensftp;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bsh.This;
import bsh.util.Sessiond;

public class SftpUtilFactoryTest extends SftpUtilFactoryDup {
	private static Logger log = Logger.getLogger(SftpUtilFactoryTest.class);
	private static String defaultSftpUtilClassName = "net.sf.opensftp.impl.SftpUtil";
	private static String mockSftpUtilClassName = "net.sf.opensftp.MockSftpUtil";
	/**
	 * The value of the net.sf.opensftp.SftpUtil system property or null if the
	 * class identified by the system property doesn't exist in the classpath or
	 * not implement {@link SftpUtil}.
	 */
	private static String actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration = SftpUtilFactoryDup.sftpUtilClassName;

	@BeforeClass
	public static void setUpBeforeClass() {
		if (actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration != null) {
			defaultSftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration;
			mockSftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration;
		}
		StringBuilder str = new StringBuilder(
				"\nactualSftpUtilClassNameIdentifiedBySystemProperty: ");
		str
				.append(actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration);
		str.append("\ndefaultSftpUtilClassName: ");
		str.append(defaultSftpUtilClassName);
		str.append("\nmockSftpUtilClassName: ");
		str.append(mockSftpUtilClassName);
		log.info(str);
	}

	@Before
	public void setUp() {
		if (actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration != null) {
			SftpUtilFactoryDup.sftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration;
			SftpUtilFactoryDup.sftpUtilClassNameInitialized = true;
		}
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
		String UTName = "testGetSftpUtilClassName";
		int i = 1;
		log.info(UTName + " - case " + i++);
		assertEquals(SftpUtilFactoryDup.getSftpUtilClassName(),
				defaultSftpUtilClassName);
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.sftpUtilClassName = mockSftpUtilClassName;
		SftpUtilFactoryDup.sftpUtilClassNameInitialized = true;
		assertEquals(SftpUtilFactoryDup.getSftpUtilClassName(),
				mockSftpUtilClassName);
	}

	@Test
	public void testGetSftpUtil() throws ClassNotFoundException {
		String UTName = "testGetSftpUtil";
		int i = 1;
		log.info(UTName + " - case " + i++);
		getSftpUtil();
		assertTrue(Class.forName(defaultSftpUtilClassName).isInstance(
				SftpUtilFactoryDup.proxiedSftpUtil));
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.sftpUtilClassName = mockSftpUtilClassName;
		SftpUtilFactoryDup.sftpUtilClassNameInitialized = true;
		getSftpUtil();
		assertTrue(Class.forName(mockSftpUtilClassName).isInstance(
				SftpUtilFactoryDup.proxiedSftpUtil));
	}

	@Test
	public void testSetSftpUtilClassName() {
		String UTName = "testSetSftpUtilClassName";
		int i = 1;
		log.info(UTName + " - case " + i++);
		SftpUtilFactoryDup.setSftpUtilClassName(null);
		if (actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration);
		}
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName("");
		if (actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration);
		}
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName(" a ");
		if (actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemPropertyOrConfiguration);
		}
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName(mockSftpUtilClassName);
		assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
				mockSftpUtilClassName);
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName(mockSftpUtilClassName);
		assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
				mockSftpUtilClassName);
		SftpUtilFactoryDup.setSftpUtilClassName(defaultSftpUtilClassName);
		assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
				mockSftpUtilClassName);
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.getSftpUtil();
		SftpUtilFactoryDup.setSftpUtilClassName(mockSftpUtilClassName);
		assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
				defaultSftpUtilClassName);
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.getSftpUtilClassName();
		SftpUtilFactoryDup.setSftpUtilClassName(mockSftpUtilClassName);
		assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
				defaultSftpUtilClassName);
	}
	
	@Test
	public void testInterceptorFunctionality(){
		String UTName = "testInterceptorFunctionality";
		int i = 1;
		log.info(UTName + " - case " + i++);
		SftpUtil util = getSftpUtil();
		try {
			SftpSession session = util.connect(null, null, null, 0);
			util.cd(session,"..");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package net.sf.opensftp;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SftpUtilFactoryTest extends SftpUtilFactoryDup {
	private static Logger log = Logger.getLogger(SftpUtilFactoryTest.class);
	private static String defaultSftpUtilClassName = "net.sf.opensftp.impl.SftpUtil";
	private static String mockSftpUtilClassName = "net.sf.opensftp.MockSftpUtil";
	/**
	 * The value of the net.sf.opensftp.SftpUtil system property or null if the
	 * class identified by the system property doesn't exist in the classpath or
	 * not implement {@link SftpUtil}.
	 */
	private static String actualSftpUtilClassNameIdentifiedBySystemProperty = SftpUtilFactoryDup.sftpUtilClassName;

	@BeforeClass
	public static void setUpBeforeClass() {
		if (actualSftpUtilClassNameIdentifiedBySystemProperty != null) {
			defaultSftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemProperty;
			mockSftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemProperty;
		}
		StringBuilder str = new StringBuilder(
				"\nactualSftpUtilClassNameIdentifiedBySystemProperty: ");
		str.append(actualSftpUtilClassNameIdentifiedBySystemProperty);
		str.append("\ndefaultSftpUtilClassName: ");
		str.append(defaultSftpUtilClassName);
		str.append("\nmockSftpUtilClassName: ");
		str.append(mockSftpUtilClassName);
		log.info(str);
	}

	@Before
	public void setUp() {
		if (actualSftpUtilClassNameIdentifiedBySystemProperty != null) {
			SftpUtilFactoryDup.sftpUtilClassName = actualSftpUtilClassNameIdentifiedBySystemProperty;
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
		assertTrue(Class.forName(defaultSftpUtilClassName).isInstance(
				SftpUtilFactoryDup.getSftpUtil()));
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.sftpUtilClassName = mockSftpUtilClassName;
		SftpUtilFactoryDup.sftpUtilClassNameInitialized = true;
		assertTrue(Class.forName(mockSftpUtilClassName).isInstance(
				SftpUtilFactoryDup.getSftpUtil()));
	}

	@Test
	public void testSetSftpUtilClassName() {
		String UTName = "testSetSftpUtilClassName";
		int i = 1;
		log.info(UTName + " - case " + i++);
		SftpUtilFactoryDup.setSftpUtilClassName(null);
		if (actualSftpUtilClassNameIdentifiedBySystemProperty == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemProperty);
		}
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName("");
		if (actualSftpUtilClassNameIdentifiedBySystemProperty == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemProperty);
		}
		tearDown();

		log.info(UTName + " - case " + i++);
		setUp();
		SftpUtilFactoryDup.setSftpUtilClassName(" a ");
		if (actualSftpUtilClassNameIdentifiedBySystemProperty == null) {
			assertNull(SftpUtilFactoryDup.sftpUtilClassName);
		} else {
			assertEquals(SftpUtilFactoryDup.sftpUtilClassName,
					actualSftpUtilClassNameIdentifiedBySystemProperty);
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
}

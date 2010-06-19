package net.sf.opensftp;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.objenesis.ObjenesisStd;
import org.omg.PortableInterceptor.Interceptor;

import net.sf.opensftp.interceptor.*;

/**
 * <p>
 * Factory that implements the following algorithm to dynamically select a
 * {@link SftpUtil} implementation class to instantiate a Singleton object for.
 * </p>
 * <ul>
 * <li>Use the implementation class specified through the
 * <code>net.sf.opensftp.SftpUtil</code> system property.</li>
 * <li>Use the implementation class specified through the first and only
 * effective call to {@link #setSftpUtilClassName(String)}.</li>
 * <li>Otherwise, use the default implementation class
 * {@link net.sf.opensftp.impl.SftpUtil}.</li>
 * </ul>
 * 
 * @version
 * @author BurningXFlame
 */

public class SftpUtilFactory {

	private static Logger log = Logger.getLogger(SftpUtilFactory.class);
	/**
	 * The name (<code>net.sf.opensftp.SftpUtil</code>) of the system property
	 * identifying our {@link SftpUtil} implementation class.
	 */
	public static final String SFTPUTIL_PROPERTY = "net.sf.opensftp.SftpUtil";
	/**
	 * The name of the default {@link SftpUtil} implementation class.
	 */
	private static final String DEFAULT_SFTPUTIL_CLASSNAME = "net.sf.opensftp.impl.SftpUtil";
	/**
	 * The name of the actual {@link SftpUtil} implementation class.
	 */
	private static String sftpUtilClassName = null;
	private static volatile boolean sftpUtilClassNameInitialized = false;
	private static Object sftpUtilClassNameLock = new Object();
	/**
	 * The Singleton instance of the actual {@link SftpUtil} implementation
	 * class.
	 */
	private static SftpUtil sftpUtil = null;
	private static volatile boolean sftpUtilInitialized = false;
	private static Object sftpUtilLock = new Object();
	/**
	 * The registered interceptors
	 */
	private static List<Interceptor> interceptors = null;

	static {
		findSftpUtilClassName();
	}

	/**
	 * Check the <code>net.sf.opensftp.SftpUtil</code> system property for an
	 * {@link SftpUtil} implementation class.
	 * <p>
	 * The class identified by this property should exist in the classpath and
	 * implement {@link SftpUtil}. Otherwise, this call will take no effect.
	 */
	private static void readConfig() {
		String configFilename = "opensftp-config.xml";
		String sftputilImplNodeName = "/opensftp-impl";
		String interceptorNodeName = "/interceptors/interceptor";
		// find the configuration file
		URL url = SftpUtilFactory.class.getResource(configFilename);
		log.debug("Configuration file found at " + url);
		try {
			Document document = new SAXReader().read(url);
			// sftputil-impl
			Node sftputilImplNode = document
					.selectSingleNode(sftputilImplNodeName);
			String sftputilImplClassName = sftputilImplNode.getText();
			if (sftputilImplClassName != null
					&& sftputilImplClassName.trim().length() != 0) {
				checkAndSetSftpUtilClassName(sftputilImplClassName);
			}

			// interceptor
			List interceptorNodes = document.selectNodes(interceptorNodeName);
			for (Iterator iter = interceptorNodes.iterator(); iter.hasNext();) {
				String interceptor = (String) iter.next();
				if (interceptor != null && interceptor.trim().length() != 0) {
					checkAndAddInterceptor(interceptor);
				}
			}

		} catch (DocumentException e) {
			log.error("Failed to parse the configuration file.", e);
		}
	}

	/**
	 * Set the name of the {@link SftpUtil} implementation class with the
	 * specified name.
	 * <p>
	 * This method can be used to set the name of the {@link SftpUtil}
	 * implementation class programmatically rather than via system properties.
	 * <p>
	 * A call to this method is effective only when the following conditions are
	 * all met.
	 * <ul>
	 * <li>The <code>net.sf.opensftp.SftpUtil</code> system property was not or
	 * not effectively set. For example, It's not a effective set if you specify
	 * a class which doesn't exist in the classpath or doesn't implement
	 * {@link SftpUtil} at all.</li>
	 * <li>No previous call to {@link #getSftpUtilClassName()}.</li>
	 * <li>No previous call to {@link #getSftpUtil()}.</li>
	 * <li>The class identified by the specified name exists in the classpath
	 * and implements {@link SftpUtil}.</li>
	 * <li>No previous effective call to this method.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param name
	 *            Name of the SftpUtil implementation class to set
	 */
	public static void setSftpUtilClassName(String name) {
		log.debug("User is trying to set SftpUtil class name.");
		if (sftpUtilClassNameInitialized || name == null
				|| name.trim().length() == 0) {
			log.debug("Ignored.");
			return;
		}

		synchronized (sftpUtilClassNameLock) {
			if (sftpUtilClassNameInitialized) {
				log.debug("Ignored.");
				return;
			}

			checkAndSetSftpUtilClassName(name);
		}
	}

	/**
	 * Check whether the class identified by the specified name exists in the
	 * classpath and implements SftpUtil or not. If ture, set
	 * {@link #sftpUtilClassName} to the specified name.
	 * 
	 * @param name
	 *            Name of the SftpUtil implementation class to set
	 */
	private static void checkAndSetSftpUtilClassName(String name) {
		name = name.trim();
		try {

			if (Arrays.asList(Class.forName(name).getInterfaces()).contains(
					SftpUtil.class)) {
				sftpUtilClassName = name;
				sftpUtilClassNameInitialized = true;
				log.debug("The SftpUtil class name was set to '" + name + "'.");
				return;
			} else {
				log.warn("The specified clss '" + name
						+ "' doesn't implement SftpUtil. ");
			}
		} catch (ClassNotFoundException e) {
			log.warn("The specified SftpUtil class '" + name
					+ "' was not found.");
		}
	}

	private static void checkAndAddInterceptor(String name) {
		name = name.trim();
		try {

			if (Arrays.asList(Class.forName(name).getInterfaces()).contains(
					Interceptor.class)) {
				interceptors.add((Interceptor) (new ObjenesisStd()
						.getInstantiatorOf(Class.forName(name)).newInstance()));
				log.debug("A new Interceptor '" + name + "' added.");
				return;
			} else {
				log.warn("The clss '" + name
						+ "' doesn't implement Interceptor.");
			}
		} catch (ClassNotFoundException e) {
			log.warn("The class '" + name + "' was not found.");
		}
	}

	/**
	 * Check the <code>net.sf.opensftp.SftpUtil</code> system property for an
	 * {@link SftpUtil} implementation class.
	 * <p>
	 * The class identified by this property should exist in the classpath and
	 * implement {@link SftpUtil}. Otherwise, this call will take no effect.
	 * 
	 * @deprecated
	 */
	private static void findSftpUtilClassName() {
		log.debug("Trying to get SftpUtil class name from the system property "
				+ SFTPUTIL_PROPERTY);
		String tmp = System.getProperty(SFTPUTIL_PROPERTY, null);
		if (tmp != null && tmp.trim().length() != 0) {
			checkAndSetSftpUtilClassName(tmp);
		}
	}

	/**
	 * Return the name of the actual {@link SftpUtil} implementation class.
	 * <p>
	 * This method will set the {@link SftpUtil} implementation class to the
	 * default one before return if the name of the {@link SftpUtil}
	 * implementation class hasn't been effectively set.
	 * 
	 * @return the name of the actual {@link SftpUtil} implementation class
	 */
	public static String getSftpUtilClassName() {
		if (!sftpUtilClassNameInitialized) {
			// findSftpUtilClassName();
			synchronized (sftpUtilClassNameLock) {
				if (!sftpUtilClassNameInitialized) {
					sftpUtilClassName = DEFAULT_SFTPUTIL_CLASSNAME;
					sftpUtilClassNameInitialized = true;
					log.debug("Use the default one.");
				}
			}
		}
		return sftpUtilClassName;
	}

	/**
	 * Return the Singleton instance of the actual {@link SftpUtil}
	 * implementation class.
	 * <p>
	 * This method will set the {@link SftpUtil} implementation class to the
	 * default one before creating a Singleton instance if the name of the
	 * {@link SftpUtil} implementation class hasn't been effectively set.
	 * 
	 * @return the Singleton instance of the actual {@link SftpUtil}
	 *         implementation class
	 */
	public static SftpUtil getSftpUtil() {
		if (!sftpUtilInitialized) {
			synchronized (sftpUtilLock) {
				if (!sftpUtilInitialized) {
					try {
						sftpUtil = (SftpUtil) (new ObjenesisStd()
								.getInstantiatorOf(Class
										.forName(getSftpUtilClassName()))
								.newInstance());
						sftpUtilInitialized = true;
					} catch (ClassNotFoundException e) {
						log.error("No class define found for "
								+ getSftpUtilClassName(), e);
					}
				}
			}
		}
		return sftpUtil;
	}
}

package net.sf.opensftp;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.objenesis.ObjenesisStd;

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

public class SftpUtilFactoryDup {

	private static Logger log = Logger.getLogger(SftpUtilFactoryDup.class);
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
	protected static String sftpUtilClassName = null;
	protected static volatile boolean sftpUtilClassNameInitialized = false;
	private static Object sftpUtilClassNameLock = new Object();
	/**
	 * The Singleton instance of the actual {@link SftpUtil} implementation
	 * class.
	 */
	protected static SftpUtil sftpUtil = null;
	protected static volatile boolean sftpUtilInitialized = false;
	private static Object sftpUtilLock = new Object();
	static {
		findSftpUtilClassName();
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

			name = name.trim();
			try {
				// Check whether the class identified by the specified name
				// exists in the classpath and implements SftpUtil or not.
				if (Arrays.asList(Class.forName(name).getInterfaces())
						.contains(SftpUtil.class)) {
					sftpUtilClassName = name;
					sftpUtilClassNameInitialized = true;
					log.debug("The SftpUtil class name was set to '" + name
							+ "'.");
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
	}

	/**
	 * Check the <code>net.sf.opensftp.SftpUtil</code> system property for an
	 * {@link SftpUtil} implementation class.
	 * <p>
	 * The class identified by this property should exist in the classpath and
	 * implement {@link SftpUtil}. Otherwise, this call will take no effect.
	 */
	private static void findSftpUtilClassName() {
		/*
		 * synchronized (sftpUtilClassNameLock) { if
		 * (sftpUtilClassNameInitialized) return;
		 */
		log.debug("Trying to get SftpUtil class name from the system property "
				+ SFTPUTIL_PROPERTY);
		String tmp = System.getProperty(SFTPUTIL_PROPERTY, null);
		if (tmp != null && tmp.trim().length() != 0) {
			try {
				tmp = tmp.trim();
				// Check whether the class identified by the specified name
				// exists in the classpath and implements SftpUtil or not.
				if (Arrays.asList(Class.forName(tmp).getInterfaces()).contains(
						SftpUtil.class)) {
					sftpUtilClassName = tmp;
					sftpUtilClassNameInitialized = true;
					log.debug("The SftpUtil class name was set to " + tmp);
					return;
				} else {
					log.warn("The specified clss '" + tmp
							+ "' doesn't implement SftpUtil. ");
				}
			} catch (ClassNotFoundException e) {
				log.warn("The specified SftpUtil class '" + tmp
						+ "' was not found.");
			}
		}
		/*
		 * sftpUtilClassName = DEFAULT_SFTPUTIL_CLASSNAME;
		 * sftpUtilClassNameInitialized = true;
		 * log.debug("Use the default one."); }
		 */
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

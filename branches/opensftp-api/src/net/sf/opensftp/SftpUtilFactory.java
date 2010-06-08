package net.sf.opensftp;

import org.apache.log4j.Logger;
import org.objenesis.ObjenesisStd;

/**
 * @author BurningXFlame
 */
public class SftpUtilFactory {

	private static Logger log = Logger.getLogger(SftpUtilFactory.class);
	/**
	 * The name (<code>net.sf.opensftp.SftpUtil</code>) of the system property
	 * identifying our {@link SftpUtil} implementation class.
	 */
	private static final String SFTPUTIL_PROPERTY = "net.sf.opensftp.SftpUtil";
	public static final String defaultSftpUtilClassName = "net.sf.opensftp.impl.SftpUtil";
	private static String sftpUtilClassName = null;
	private static volatile boolean sftpUtilClassNameInitialized = false;
	private static Object sftpUtilClassNameLock = new Object();
	private static SftpUtil sftpUtil = null;
	private static volatile boolean sftpUtilInitialized = false;
	private static Object sftpUtilLock = new Object();

	public static void setSftpUtilClassName(String name) {
		log.debug("User is trying to set SftpUtil class name.");
		if (sftpUtilClassNameInitialized || name == null
				|| name.trim().length() == 0) {
			log.debug("Ignored.");
			return;
		}

		synchronized (sftpUtilClassNameLock) {
			if (sftpUtilClassNameInitialized)
				return;

			name = name.trim();
			try {
				// Check whether the user-specified SftpUtil exists or not.
				Class.forName(name);
				sftpUtilClassName = name;
				sftpUtilClassNameInitialized = true;
				log.debug("The SftpUtil class name was set to '" + name + "'.");
				return;
			} catch (ClassNotFoundException e) {
				log.warn("The user-specified SftpUtil class '" + name
						+ "' was not found.");
			}
		}
	}

	/**
	 * Checks system properties for an SftpUtil implementation specified by the
	 * user under the property names {@link #SFTPUTIL_PROPERTY}. Use the default
	 * one ({@link #defaultSftpUtilClassName}) if not found.
	 */
	private static void findSftpUtilClassName() {
		synchronized (sftpUtilClassNameLock) {
			if (sftpUtilClassNameInitialized)
				return;

			log.debug("Trying to get SftpUtil class name from system property "
					+ SFTPUTIL_PROPERTY);
			String tmp = System.getProperty(SFTPUTIL_PROPERTY, null);
			if (tmp != null && tmp.trim().length() != 0) {
				try {
					tmp = tmp.trim();
					// Check whether the user-specified SftpUtil exists or not.
					Class.forName(tmp);
					sftpUtilClassName = tmp;
					sftpUtilClassNameInitialized = true;
					log.debug("The SftpUtil class name was set to " + tmp);
					return;
				} catch (ClassNotFoundException e) {
					log.warn("The user-specified SftpUtil " + tmp
							+ " was not found.");
				}
			}

			sftpUtilClassName = defaultSftpUtilClassName;
			sftpUtilClassNameInitialized = true;
			log.debug("Use the default one.");
		}
	}

	public static String getSftpUtilClassName() {
		if (!sftpUtilClassNameInitialized)
			findSftpUtilClassName();
		return sftpUtilClassName;
	}

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

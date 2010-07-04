package net.sf.opensftp;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.objenesis.ObjenesisStd;

import bsh.EvalError;
import bsh.Interpreter;

import net.sf.opensftp.interceptor.*;

/**
 * <p>
 * Factory that implements the following algorithm to dynamically select a
 * {@link SftpUtil} implementation class to instantiate a Singleton object for.
 * </p>
 * <ul>
 * <li>Use the implementation class specified through the
 * <code>/opensftp-impl</code> node in the <code>opensftp-config.xml</code>
 * configuration file.</li>
 * <li>Use the implementation class specified through the
 * <code>net.sf.opensftp.SftpUtil</code> system property.</li>
 * <li>Use the implementation class specified through the first and only
 * effective call to {@link #setSftpUtilClassName(String)}.</li>
 * <li>Otherwise, use the default implementation class
 * {@link net.sf.opensftp.impl.SftpUtil}.</li>
 * </ul>
 * <p>
 * NOTE: Among the above approaches, one should effectivly set an SftpUtil
 * implementation class name, otherwise, the flow goes to the next one. It's not
 * a effective set if you specify a class which doesn't exist in the classpath
 * or doesn't implement {@link SftpUtil} at all.
 * </p>
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

	private static final String configFilename = "opensftp-config.xml";
	// xpath for opensftp-config.xml
	private static final String sftputilImplNodePath = "/sftputil-impl";
	private static final String interceptorNodePath = "/interceptors/interceptor";
	private static final String beanNodePath = "/beans/bean";
	private static final String beanNameAttrRelativePath = "@name";
	private static final String beanTypeAttrRelativePath = "@type";
	private static final String propertyNodeRelativePath = "property";
	private static final String propertyRefNodeRelativePath = "property-ref";
	private static final String propertyNameAttrRelativePath = "@name";
	private static final String propertyTypeAttrRelativePath = "@type";
	private static final String propertyValueAttrRelativePath = "@value";
	private static final String propertyRefAttrRelativePath = "@ref";
	private static final String initializerBlockRelativePath = "initializer-block";

	static {
		readConfig();
		if (!sftpUtilClassNameInitialized)
			findSftpUtilClassName();
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

	/**
	 * Set the name of the {@link SftpUtil} implementation class with the
	 * specified name.
	 * <p>
	 * This method can be used to set the name of the {@link SftpUtil}
	 * implementation class programmatically rather than via configuration file
	 * or system property.
	 * <p>
	 * A call to this method is effective only when the following conditions are
	 * all met.
	 * <ul>
	 * <li>The <code>/opensftp-impl</code> node in the
	 * <code>opensftp-config.xml</code> configuration file was not or not
	 * effectively set. It's not a effective set if you specify a class which
	 * doesn't exist in the classpath or doesn't implement {@link SftpUtil} at
	 * all.</li>
	 * <li>The <code>net.sf.opensftp.SftpUtil</code> system property was not or
	 * not effectively set.</li>
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
	 * Check the <code>net.sf.opensftp.SftpUtil</code> system property for an
	 * {@link SftpUtil} implementation class.
	 * <p>
	 * The class identified by this property should exist in the classpath and
	 * implement {@link SftpUtil}. Otherwise, this call will take no effect.
	 * 
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
			} else {
				log.warn("The specified clss '" + name
						+ "' doesn't implement SftpUtil. ");
			}
		} catch (ClassNotFoundException e) {
			log.warn("The specified SftpUtil class '" + name
					+ "' was not found.");
		}
	}

	/**
	 * Read the <code>opensftp-config.xml</code> configuration file for the
	 * {@link SftpUtil} implementation class and {@link Interceptor}s.
	 * <p>
	 * The specified {@link SftpUtil} implementation class should exist in the
	 * classpath and implement {@link SftpUtil}. Otherwise, it's ignored. And
	 * the other parts of the configuration will not be affected.<br>
	 * If an {@link Interceptor} is wrongly configured, it will be ignored and
	 * will not affect the other parts of the configuration.
	 */
	private static void readConfig() {
		// find the configuration file
		URL url = SftpUtilFactory.class.getResource(configFilename);
		log.debug("Configuration file found at " + url);
		log.debug("Start reading configuration.");
		try {
			Document document = new SAXReader().read(url);
			// sftputil-impl
			Node sftputilImplNode = document
					.selectSingleNode(sftputilImplNodePath);
			String sftputilImplClassName = sftputilImplNode.getText();
			if (sftputilImplClassName != null
					&& sftputilImplClassName.trim().length() != 0) {
				checkAndSetSftpUtilClassName(sftputilImplClassName);
			}

			// interceptor
			List interceptorNodes = document.selectNodes(interceptorNodePath);
			for (Iterator iter = interceptorNodes.iterator(); iter.hasNext();) {
				checkAndAddInterceptor((Node) iter.next());
			}
			log.debug("End reading configuration.");
		} catch (DocumentException e) {
			log.error("Failed to parse the configuration file.", e);
		}
	}

	/**
	 * Check whether the class identified by the specified name exists in the
	 * classpath and implements {@link Interceptor} or not. If ture, generate an
	 * instance of this interceptor and add it to {@link #interceptors}.
	 * 
	 * @param name
	 *            Name of the Interceptor implementation class to add
	 */
	private static void checkAndAddInterceptor(Node interceptorNode) {
		String interceptorClassName = interceptorNode.selectSingleNode(
				beanTypeAttrRelativePath).getText();
		if (interceptorClassName != null
				&& interceptorClassName.trim().length() != 0)
			return;
		
		interceptorClassName = interceptorClassName.trim();
		
		log.debug("Start processing Interceptor " + interceptorClassName);
		
		try {

			if (Arrays.asList(
					Class.forName(interceptorClassName).getInterfaces())
					.contains(Interceptor.class)) {
				Interceptor interceptor = (Interceptor) (new ObjenesisStd()
						.getInstantiatorOf(Class.forName(interceptorClassName))
						.newInstance());

				initializeBean(interceptorNode, false, "interceptor",
						interceptor);

				interceptors.add(interceptor);
				log.debug("A new Interceptor '" + interceptorClassName
						+ "' added.");
			} else {
				log.warn("The clss '" + interceptorClassName
						+ "' doesn't implement Interceptor.");
			}
		} catch (ClassNotFoundException e) {
			log.warn("The class '" + interceptorClassName + "' was not found.");
		}
		log.debug("End processing Interceptor " + interceptorClassName);
	}

	/**
	 * Initialize a specified bean configured through the
	 * <code>opensftp-config.xml</code> configuration file.
	 * 
	 * @param beanNode
	 *            the {@link Node} object representing the configuration node of
	 *            a specified bean
	 * @param needConstruct
	 *            a boolean value indicating whether constructing the bean.<br>
	 *            If this param is set to <code>true</code>, this method must
	 *            construct the bean. And the values of the
	 *            <code>beanName</code> and the <code>bean</code> are ignored,
	 *            because these values are fetched out from the
	 *            <code>beanNode</code>. This functionality is designed for Bean
	 *            configuration.<br>
	 *            If this param is set to <code>false</code>, this method must
	 *            not construct the bean, but use the specified
	 *            <code>beanName</code> and the <code>bean</code> as the
	 *            already-constructed bean instead. This functionality is
	 *            designed for Interceptor configuration.
	 */
	private static void initializeBean(Node beanNode, boolean needConstruct,
			String beanName, Object bean) {
		try {
			Interpreter interpreter = new Interpreter();
			StringBuilder s = new StringBuilder();
			if (needConstruct) {
				beanName = beanNode.selectSingleNode(beanNameAttrRelativePath)
						.getText();
				s.append(beanName);
				s.append(" = new ");
				s.append(beanNode.selectSingleNode(beanTypeAttrRelativePath)
						.getText());
				s.append("()");
				s.append("{{");
			} else {
				interpreter.set(beanName, bean);
			}

			log.debug("Start initializing Bean " + beanName);
			
			// property nodes
			List propertyNodes = beanNode.selectNodes(propertyNodeRelativePath);
			for (Iterator iter = propertyNodes.iterator(); iter.hasNext();) {
				Node propertyNode = ((Node) iter.next());
				String propertyName = propertyNode.selectSingleNode(
						propertyNameAttrRelativePath).getText();
				String propertyType = propertyNode.selectSingleNode(
						propertyTypeAttrRelativePath).getText();
				String propertyValue = propertyNode.selectSingleNode(
						propertyValueAttrRelativePath).getText();
				Object value = ConvertUtils.convert(propertyValue, Class
						.forName(propertyType));

				if (!needConstruct) {
					s.append(beanName);
					s.append(".");
				}
				s.append("set");
				s.append(propertyName.substring(0, 1).toUpperCase());
				s.append(propertyName.substring(1));
				s.append("(");
				interpreter.set("value", value);
				s.append("value");
				s.append(");");
			}

			// property-ref nodes
			List propertyRefNodes = beanNode
					.selectNodes(propertyRefNodeRelativePath);
			for (Iterator iter = propertyNodes.iterator(); iter.hasNext();) {
				Node propertyNode = ((Node) iter.next());
				String propertyName = propertyNode.selectSingleNode(
						propertyNameAttrRelativePath).getText();
				String propertyType = propertyNode.selectSingleNode(
						propertyTypeAttrRelativePath).getText();
				Object value = null;
				Node refBeanNode = beanNode.getDocument().selectSingleNode(
						beanNodePath + "[" + beanNameAttrRelativePath + "="
								+ beanName + "]");
				initializeBean(refBeanNode, true, null, null);

				if (!needConstruct) {
					s.append(beanName);
					s.append(".");
				}
				s.append("set");
				s.append(propertyName.substring(0, 1).toUpperCase());
				s.append(propertyName.substring(1));
				s.append("(");
				interpreter.set("value", value);
				s.append("value");
				s.append(");");

			}

			if (needConstruct) {
				// initializer-block node
				s.append(beanNode
						.selectSingleNode(initializerBlockRelativePath)
						.getText());
				s.append("}}");
			}

			// evaluation
			interpreter.eval(s.toString());

		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("End initializing Bean " + beanName);
	}

}

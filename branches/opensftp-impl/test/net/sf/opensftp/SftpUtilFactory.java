package net.sf.opensftp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.objenesis.ObjenesisStd;
import org.xml.sax.SAXException;

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
	private static SftpUtil proxiedSftpUtil = null;
	/**
	 * The Singleton proxy instance of {@link SftpUtil} backed by
	 * {@link #proxiedSftpUtil}
	 */
	private static SftpUtil sftpUtil = null;
	private static volatile boolean sftpUtilInitialized = false;
	private static Object sftpUtilLock = new Object();
	/**
	 * The registered interceptors
	 */
	private static List<Interceptor> interceptors = new ArrayList<Interceptor>();

	private static final String configXSDFileName = "/opensftp-config.xsd";
	private static final String configFilename = "opensftp-config.xml";
	// xpath for opensftp-config.xml
	private static final String ns_prefix = "o";
	private static final String ns_uri = "http://opensftp.sf.net/opensftp-config";
	private static final String rootNodePath = "/" + ns_prefix
			+ ":opensftp-config";
	private static final String sftputilImplNodePath = rootNodePath + "/"
			+ ns_prefix + ":sftputil-impl";
	private static final String interceptorNodePath = rootNodePath + "/"
			+ ns_prefix + ":interceptors" + "/" + ns_prefix + ":interceptor";
	private static final String beanNodePath = rootNodePath + "/" + ns_prefix
			+ ":beans" + "/" + ns_prefix + ":bean";
	private static final String beanNameAttrRelativePath = "@name";
	private static final String beanTypeAttrRelativePath = "@type";
	private static final String propertyNodeRelativePath = ns_prefix
			+ ":property";
	private static final String propertyRefNodeRelativePath = ns_prefix
			+ ":property-ref";
	private static final String propertyNameAttrRelativePath = "@name";
	private static final String propertyTypeAttrRelativePath = "@type";
	private static final String propertyValueAttrRelativePath = "@value";
	private static final String propertyRefAttrRelativePath = "@ref";
	private static final String initializerBlockRelativePath = ns_prefix
			+ ":initializing-block";

	private static long beanCounter = 0;

	static {
		try {
			readConfig();
		} catch (IOException e) {
			log.error("Failed to close the configuration file.");
			e.printStackTrace();
		}
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
						// the real SftpUtil object
						proxiedSftpUtil = (SftpUtil) (new ObjenesisStd()
								.getInstantiatorOf(Class
										.forName(getSftpUtilClassName()))
								.newInstance());

						// the InvocationHandler
						InvocationHandler handler = new SftpUtilInvocationHandler(
								proxiedSftpUtil);

						// the proxy object
						sftpUtil = (SftpUtil) Proxy.newProxyInstance(
								proxiedSftpUtil.getClass().getClassLoader(),
								proxiedSftpUtil.getClass().getInterfaces(),
								handler);

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
						+ "' doesn't implement 'net.sf.opensftp.SftpUtil'. ");
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
	 * 
	 * @throws IOException
	 */
	private static void readConfig() throws IOException {
		// find the configuration file
		URL url = SftpUtilFactory.class.getClassLoader().getResource(
				configFilename);
		if (url == null) {
			log
					.debug("Configuration file not found. Skip the reading configuration phase.");
			return;
		}
		log.debug("Configuration file found at '" + url + "'.");
		log.debug("Start reading configuration.");
		InputStream in = SftpUtilFactory.class.getClassLoader()
				.getResourceAsStream(configFilename);
		try {
			SAXReader configReader = new SAXReader();

			// setXPathNamespaceURIs
			Map map = new HashMap();
			map.put(ns_prefix, ns_uri);
			configReader.getDocumentFactory().setXPathNamespaceURIs(map);

			// validation
			String configXSDPath = SftpUtilFactory.class.getResource(
					configXSDFileName).toString();
			log.debug("The XSD file found at " + configXSDPath);

			configReader.setValidation(true);
			configReader.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			configReader
					.setProperty(
							"http://apache.org/xml/properties/schema/external-schemaLocation",
							ns_uri + " " + configXSDPath);

			XMLErrorHandler errorHandler = new XMLErrorHandler();
			configReader.setErrorHandler(errorHandler);

			Document document = configReader.read(in);

			Element errors = errorHandler.getErrors();
			if (errors != null && errors.hasContent()) {
				// output the errors XML
				log
						.warn("The configuration file is invalid. Ignore the configuration file. As follows list the validation erros.");
				log.warn(errors.asXML());
				new XMLWriter(OutputFormat.createPrettyPrint()).write(errors);
				return;
			}

			// sftputil-impl
			Node sftputilImplNode = document
					.selectSingleNode(sftputilImplNodePath);
			if (sftputilImplNode != null) {
				String sftputilImplClassName = sftputilImplNode.getText();
				if (sftputilImplClassName != null
						&& sftputilImplClassName.trim().length() != 0) {
					checkAndSetSftpUtilClassName(sftputilImplClassName);
				}
			}

			// interceptor
			List interceptorNodes = document.selectNodes(interceptorNodePath);
			if (interceptorNodes != null) {
				for (Iterator iter = interceptorNodes.iterator(); iter
						.hasNext();) {
					checkAndAddInterceptor((Node) iter.next());
				}
			}
			log.debug("End reading configuration.");

		} catch (DocumentException e) {
			log.error("Failed to parse the configuration file.", e);
		} catch (IOException e) {
			log.error("Failed to parse the configuration file.", e);
		} catch (SAXException e) {
			log.error("Failed to parse the configuration file.", e);
		} finally {
			in.close();
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
		String beanName = null;
		String interceptorClassName = null;
		try {
			beanName = interceptorNode.valueOf(beanNameAttrRelativePath);
			interceptorClassName = interceptorNode
					.valueOf(beanTypeAttrRelativePath);
			log.debug("Start initializing Interceptor '" + beanName + "' ("
					+ interceptorClassName + ").");
			if (beanName == null || beanName.length() == 0
					|| interceptorClassName == null
					|| interceptorClassName.length() == 0) {

				log
						.warn("Null or blank @name or @type found in the configuration of bean '"
								+ beanName + "'. Ignore this bean.");
				return;
			}

			interceptorClassName = interceptorClassName.trim();

			if (Arrays.asList(
					Class.forName(interceptorClassName).getInterfaces())
					.contains(Interceptor.class)) {
				Interceptor interceptor = (Interceptor) (new ObjenesisStd()
						.getInstantiatorOf(Class.forName(interceptorClassName))
						.newInstance());

				if (initializeBean(interceptorNode, false, beanName,
						interceptor, null) == null) {
					log.warn("Failed to initialize Interceptor '" + beanName
							+ "' (" + interceptorClassName + ").");
				} else {
					interceptors.add(interceptor);
					log.debug("A new Interceptor '" + beanName + "' ("
							+ interceptorClassName + ") is added.");
				}
			} else {
				log.warn("The clss '" + interceptorClassName
						+ "' doesn't implement 'net.sf.opensftp.Interceptor'.");
			}
		} catch (ClassNotFoundException e) {
			log.warn("The class '" + interceptorClassName + "' was not found.");
		} finally {
			log.debug("End initializing Interceptor '" + beanName + "' ("
					+ interceptorClassName + ").");
		}
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
	 *            <code>beanNode</code>. And this method must check the declared
	 *            type of this bean against the <code>expectedType</code> param.
	 *            This functionality is designed for Bean configuration.<br>
	 *            If this param is set to <code>false</code>, this method must
	 *            not construct the bean, but use the specified
	 *            <code>beanName</code> and the <code>bean</code> as the
	 *            already-constructed bean instead. This functionality is
	 *            designed for Interceptor configuration.
	 * @param beanName
	 *            the name of the bean. Please refer to the description of the
	 *            needConstruct param for more details.
	 * @param bean
	 *            the bean. Please refer to the description of the needConstruct
	 *            param for more details.
	 * @param expectedType
	 *            a String value representing a type of which this bean is
	 *            expected to be an instance. Please refer to the description of
	 *            the needConstruct param for more details.
	 * 
	 * @return the constructed and initialized bean if needConstruct is true,<br>
	 *         the <code>bean</code> param if needConstruct is false,<br>
	 *         or null if fail.
	 */
	private static Object initializeBean(Node beanNode, boolean needConstruct,
			String beanName, Object bean, String expectedType) {

		try {
			Interpreter interpreter = new Interpreter();
			StringBuilder s = new StringBuilder();

			if (needConstruct) {
				beanName = beanNode.valueOf(beanNameAttrRelativePath);
				log.debug("Start initializing Bean '" + beanName + "'.");
				String beanType = beanNode.valueOf(beanTypeAttrRelativePath);
				if (beanName == null || beanName.length() == 0
						|| beanType == null || beanType.length() == 0) {

					log
							.warn("Null or blank @name or @type found in the configuration of bean '"
									+ beanName + "'. Ignore this bean.");
					return null;
				}
				// check whether the bean conforms to the expected type
				if (!Class.forName(expectedType).isAssignableFrom(
						Class.forName(beanType))) {
					log.warn("Bean '" + beanName + "' is declared as '"
							+ beanType + "', while it's expected to be a/an '"
							+ expectedType + "'. Ignore this bean.");
					return null;
				}

				s.append(beanName);
				s.append(" = new ");
				s.append(beanType);
				s.append("()");
				s.append("{{");
			} else {
				if (beanName == null || beanName.length() == 0 || bean == null)
					return null;
				log.debug("Start initializing Bean '" + beanName + "'.");
				interpreter.set(beanName, bean);
			}

			// property nodes
			List propertyNodes = beanNode.selectNodes(propertyNodeRelativePath);
			for (Iterator iter = propertyNodes.iterator(); iter.hasNext();) {
				Node propertyNode = ((Node) iter.next());
				String propertyName = propertyNode
						.valueOf(propertyNameAttrRelativePath);
				String propertyType = propertyNode
						.valueOf(propertyTypeAttrRelativePath);
				String propertyValue = propertyNode
						.valueOf(propertyValueAttrRelativePath);
				if (propertyName == null || propertyName.length() == 0
						|| propertyType == null || propertyType.length() == 0
						|| propertyValue == null || propertyValue.length() == 0) {
					log
							.warn("Null or blank @name or @type found in the configuration of property '"
									+ propertyName
									+ "' of bean '"
									+ beanName
									+ "'. Ignore this bean.");
					return null;
				}
				log.debug("Start setting property '" + propertyName + "'.");
				Object value = ConvertUtils.convert(propertyValue, Class
						.forName(propertyType));
				if (!Class.forName(propertyType).isInstance(value)) {
					log.warn("Failed to convert property '" + propertyName
							+ "' of bean '" + beanName + "' to '"
							+ propertyType + "'. Ignore this bean.");
					return null;
				}

				if (!needConstruct) {
					s.append(beanName);
					s.append(".");
				}
				s.append("set");
				s.append(propertyName.substring(0, 1).toUpperCase());
				s.append(propertyName.substring(1));
				s.append("(");
				String varName = getDistinctVariableName();
				interpreter.set(varName, value);
				s.append(varName);
				s.append(");");
			}

			// property-ref nodes
			List propertyRefNodes = beanNode
					.selectNodes(propertyRefNodeRelativePath);
			for (Iterator iter = propertyRefNodes.iterator(); iter.hasNext();) {
				Node propertyNode = ((Node) iter.next());
				String propertyName = propertyNode
						.valueOf(propertyNameAttrRelativePath);
				String propertyType = propertyNode
						.valueOf(propertyTypeAttrRelativePath);
				String propertyRef = propertyNode
						.valueOf(propertyRefAttrRelativePath);
				if (propertyName == null || propertyName.length() == 0
						|| propertyType == null || propertyType.length() == 0
						|| propertyRef == null || propertyRef.length() == 0) {
					log
							.warn("Null or blank @name or @type found in the configuration of property-ref '"
									+ propertyName
									+ "' of bean '"
									+ beanName
									+ "'. Ignore this bean.");
					return null;
				}
				log.debug("Start setting property '" + propertyName + "'.");
				Object value = null;
				Node refBeanNode = beanNode.getDocument().selectSingleNode(
						beanNodePath + "[" + beanNameAttrRelativePath + "='"
								+ propertyRef + "']");
				if (refBeanNode == null) {
					log.warn("Bean '" + beanName
							+ "' refers to a non-existent bean '" + propertyRef
							+ "'. Ignore this Bean.");
					return null;
				}
				value = initializeBean(refBeanNode, true, null, null,
						propertyType);
				if (value == null) {
					log.warn("Bean '" + beanName + "' refers to a bean, '"
							+ propertyRef
							+ "', which is not initialized successfully. "
							+ "Ignore this Bean.");
					return null;
				} else {
					if (!needConstruct) {
						s.append(beanName);
						s.append(".");
					}
					s.append("set");
					s.append(propertyName.substring(0, 1).toUpperCase());
					s.append(propertyName.substring(1));
					s.append("(");
					String varName = getDistinctVariableName();
					interpreter.set(varName, value);
					s.append(varName);
					s.append(");");
				}
			}

			// initializer-block node
			Node initializerBlockNode = beanNode
					.selectSingleNode(initializerBlockRelativePath);
			if (initializerBlockNode != null) {
				String initializerBlock = initializerBlockNode.getText();
				if (initializerBlock != null && initializerBlock.length() != 0)
					s.append(initializerBlock);
			}

			if (needConstruct) {
				s.append("}}");
			}

			// evaluation
			Object obj = interpreter.eval(s.toString());
			if (needConstruct) {
				return obj;
			} else {
				return bean;
			}

		} catch (EvalError e) {
			log
					.warn(
							"Failed to initialize bean '"
									+ beanName
									+ "'. Please check your 'opensftp-config.xml' against the following probable causations.\n"
									+ "(1) A certain bean or property is declared as a type which provides none no-argument constructor.\n"
									+ "(2) A certain bean or property is declared as a type which doesn't exist.\n"
									+ "(3) The declared type of a certain property doesn't match its defined type.\n"
									+ "(4) A certain bean provide none setter for a certain property.\n"
									+ "(5) A certain initializing-block contains invalid code.\n",
							e);
		} catch (ClassNotFoundException e) {
			log.warn("Failed to initialize bean '" + beanName + "'.", e);
		} finally {
			log.debug("End initializing Bean '" + beanName + "'.");
		}
		return null;
	}

	private static String getDistinctVariableName() {
		String prefix = "var";
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return prefix + df.format(new Date()) + beanCounter++;
	}

	private static class SftpUtilInvocationHandler implements InvocationHandler {
		private SftpUtil proxiedObj = null;

		public SftpUtilInvocationHandler(SftpUtil proxiedObj) {
			this.proxiedObj = proxiedObj;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {

			// before invocation
			ListIterator<Interceptor> it = interceptors.listIterator();
			while (it.hasNext()) {
				it.next().beforeMethod(method, args);
			}
			SftpResult result = (SftpResult) method.invoke(this.proxiedObj,
					args);

			// after invocation
			while (it.hasPrevious()) {
				it.previous().afterMethod(method, args, result);
			}

			return result;
		}
	}
}
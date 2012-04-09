package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpUtilFactory;

public class DummyInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(DummyInterceptor.class);
	private String dummyfield1;
	private int dummyfield2;
	private HashMap dummyfield3;
	private Date dummyfield4;
	private DateFormat dummyfield5;

	public String getDummyfield1() {
		return dummyfield1;
	}

	public void setDummyfield1(String dummyfield1) {
		this.dummyfield1 = dummyfield1;
	}

	public int getDummyfield2() {
		return dummyfield2;
	}

	public void setDummyfield2(int dummyfield2) {
		this.dummyfield2 = dummyfield2;
	}

	public HashMap getDummyfield3() {
		return dummyfield3;
	}

	public void setDummyfield3(HashMap dummyfield3) {
		this.dummyfield3 = dummyfield3;
	}

	public Date getDummyfield4() {
		return dummyfield4;
	}

	public void setDummyfield4(Date dummyfield4) {
		this.dummyfield4 = dummyfield4;
	}

	public DateFormat getDummyfield5() {
		return dummyfield5;
	}

	public void setDummyfield5(DateFormat dummyfield5) {
		this.dummyfield5 = dummyfield5;
	}

	public void afterMethod(Method method, Object[] args, SftpResult result) {
		log.debug("after method " + method.getName());
	}

	public void beforeMethod(Method method, Object[] args) {
		log.debug("before method " + method.getName());
		log.debug("dummyfield1: " + dummyfield1);
		log.debug("dummyfield2: " + dummyfield2);

		log.debug("dummyfield3: ");
		Set set = dummyfield3.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			log.debug(key + " - " + dummyfield3.get(key));
		}
		
		log.debug("dummyfield4: " + dummyfield4);
		log.debug("dummyfield5: " + dummyfield5.getCalendar().getTime());
	}
}

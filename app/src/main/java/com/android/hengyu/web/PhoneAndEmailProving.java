package com.android.hengyu.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.log4j.Logger;

public class PhoneAndEmailProving {
	// private static final Logger logger = Logger
	// .getLogger(ClassPathResource.class);

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		// logger.info(m.matches() + "---");
		return m.matches();
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		// logger.info(m.matches() + "---");
		return m.matches();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(PhoneAndEmailProving
				.isEmail("189666666666@1-89.co-m.cn"));
	}
}
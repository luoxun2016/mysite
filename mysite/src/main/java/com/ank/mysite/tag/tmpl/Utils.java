package com.ank.mysite.tag.tmpl;

class Utils {

	public static String BLOCK = "__jsp_override__";

	static String getOverrideVariableName(String name) {
		return BLOCK + name;
	}

}
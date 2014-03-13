package cc.pp.tencent.utils.java;

public class JavaPattern {

	public static boolean isAllNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

}

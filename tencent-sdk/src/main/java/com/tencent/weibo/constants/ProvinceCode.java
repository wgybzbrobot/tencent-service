package com.tencent.weibo.constants;

import java.util.HashMap;

public class ProvinceCode {

	private static String[] PROVINCE_NAME = { "北京", "天津", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", //
			"上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "广西", "海南", //
			"重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾", "香港", "澳门", //
			"其他", "海外" };

	private static int[] PROVINCE_CODE = { 11, 12, 13, 4, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36,//
			37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82, 100, 400 };

	private static HashMap<Integer, String> PROVINCE;;

	static {
		PROVINCE = new HashMap<Integer, String>();
		for (int i = 0; i < PROVINCE_NAME.length; i++) {
			PROVINCE.put(PROVINCE_CODE[i], PROVINCE_NAME[i]);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ProvinceCode ProvinceCode = new ProvinceCode();
		System.out.println(ProvinceCode.codeToCityname(12));

	}

	/**
	 * 转换编码
	 * @param provincecode
	 * @return
	 */
	public String codeToCityname(int provincecode) {
		
		return PROVINCE.get(provincecode);
	}


}

package cc.pp.tencent.bozhus.common;

/**
 * Title: 微博设备来源分析相关数据
 * @author wanggang
 * @version 1.1
 * @since 2013-05-27
 */
public class SourceType {

	public static final String[] TENCENT = { "腾讯", "微博" };
	public static final String[] PC = { "空间", "QQ", "桌面", "浏览器", "云中", "皮皮", "时光机", "Windows", "Flyme", "360", "百度",
		"MSN","56","七点半","Qing","Qplus","FaWave","定时8","墨客","板报","极阅","浮云","wbTalk","WRE","精品"};
	public static final String[] ANDROID = {"Android","智能","手机","三星","华为","联想","ZTE","中兴","海信","金立","MOTO",
		"摩托","Moto","vivo","HTC","小米","索尼","Xperia","LG","Weico","OS","TCL","Coolpad","酷派","360特供","360海尔",
		"天语","兔兔","MeeGo","Arming","微格","ZAKER","四次元","猜猜看","定制","35Phone","Google"};
	public static final String[] IPHONE = {"iPhone","红围脖","梦工厂"};
	public static final String[] IPAD = {"Pad","平板","OS","HD","E"};
	
	public static int getCategory(String source)
	{
		int type = 5;
		boolean flag = true;
		if (flag) {
			for (int i = 0; i < TENCENT.length; i++) {
				if (source.contains(TENCENT[i])) {
					type = 0;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 0; i < 3; i++) {
				if (source.contains(PC[i])) {
					type = 1;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 0; i < 3; i++) {
				if (source.contains(ANDROID[i])) {
					type = 2;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 0; i < IPHONE.length; i++) {
				if (source.contains(IPHONE[i])) {
					type = 3;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 0; i < IPAD.length; i++) {
				if (source.contains(IPAD[i])) {
					type = 4;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 3; i < PC.length; i++) {
				if (source.contains(PC[i])) {
					type = 1;
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			for (int i = 3; i < ANDROID.length; i++) {
				if (source.contains(ANDROID[i])) {
					type = 2;
					flag = false;
					break;
				}
			}
		}
		
		return type;
	}
}

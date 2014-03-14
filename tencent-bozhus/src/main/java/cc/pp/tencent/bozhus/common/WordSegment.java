package cc.pp.tencent.bozhus.common;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: 分词工具
 * @author wanggang
 * @version 1.1
 * @since 2013-05-27
 */
public class WordSegment implements Serializable {

	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(WordSegment.class);

	private static final String BASEURL = "http://60.169.74.147:3305/ExtractKeyWord/WordSegment?";
	private static String TOKEN = "mnvrjierrdqdiefxanjp";
	private static String wdcount = "50";

	/**
	 * 测试函数
	 * @param args
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String result = WordSegment.getWordsSeg("初二因为什么事情不记得");
		System.out.println(result);
	}

	/**
	 * 获取情感信息
	 */
	public static String getWordsSeg(String words) {

		// token信息
		String queryString = "token=" + TOKEN;
		// 分词个数
		queryString = queryString + "&wdcount=" + wdcount;
		// 词性
		queryString = queryString + "&character=" + "";
		//对内容进行编码
		String contentEncode;
		try {
			contentEncode = URIUtil.encodeAll(words, "utf-8");
		} catch (URIException e) {
			logger.error("URIException: " + e.getMessage() + ", at WordSegment.");
			return null;
		}
		queryString = queryString + "&words=" + contentEncode;

		return HttpUtils.doGet(BASEURL + queryString, "utf-8");
	}

}

package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * 私信相关API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3">腾讯微博开放平台上私信相关的API文档<a>
 */

public class PrivateAPI extends BasicAPI {

    private String privateRecvUrl=apiBaseUrl+"/private/recv";
    private String privateSendUrl=apiBaseUrl+"/private/send";
    
    /**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     */
    public PrivateAPI(String OAuthVersion) {
        super(OAuthVersion);
    }
    
    /**
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     * @param qHttpClient 使用已有的连接管理器
     */
    public PrivateAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 获取私信收件箱列表
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式（json或xml）
	 * @param pageflag  分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime  本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-20条）
	 * @param lastid 用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	 * @param contenttype  内容过滤。0-所有类型，1-带文本，2-带链接，4-带图片，8-带视频，16-带音频，默认为0 
     * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3/%E6%94%B6%E4%BB%B6%E7%AE%B1">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String recv(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid,String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(privateRecvUrl,
				paramsList, oAuth);
	}
	
	/**
	 * 获取私信发件箱列表
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式（json或xml）
	 * @param pageflag  分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime  本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-20条）
     * @param lastid 用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	 * @param contenttype  内容过滤。0-所有类型，1-带文本，2-带链接，4-带图片，8-带视频，16-带音频，默认为0 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3/%E5%8F%91%E4%BB%B6%E7%AE%B1">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String send(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid,String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(privateSendUrl,
				paramsList, oAuth);
	}

    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        privateRecvUrl=apiBaseUrl+"/private/recv";
        privateSendUrl=apiBaseUrl+"/private/send";
        
    }
}

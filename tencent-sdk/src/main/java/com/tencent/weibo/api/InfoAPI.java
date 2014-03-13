package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;


/**
 * 数据更新相关API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E7%9B%B8%E5%85%B3">腾讯微博开放平台上数据更新相关的API文档<a>
 */

public class InfoAPI extends BasicAPI {

    private String infoUpdateUrl=apiBaseUrl+"/info/update";

    /**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     * @param OAuthVersion  根据OAuthVersion，配置通用请求参数
     */
    public InfoAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     * @param qHttpClient 使用已有的连接管理器
     */
    public InfoAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 查看数据更新条数
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式（json或xml）
	 * @param op 请求类型：  0-仅查询，1-查询完毕后将相应计数清0
	 * @param type  5-首页未读消息计数，6-@页未读消息计数，7-私信页消息计数，8-新增听众数，9-首页广播数（原创的）<br>
	 *                         op=0时，type默认为0，此时返回所有类型计数；op=1时，需带上某种类型的type，除该type类型的计数，并返回所有类型计数
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E7%9B%B8%E5%85%B3/%E6%9F%A5%E7%9C%8B%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E6%9D%A1%E6%95%B0">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String update(OAuth oAuth, String format, String op, String type)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("op", op));
		paramsList.add(new BasicNameValuePair("type", type));
		
		return requestAPI.getResource(infoUpdateUrl, paramsList,
				oAuth);
	}

    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        infoUpdateUrl=apiBaseUrl+"/info/update";
    }


}

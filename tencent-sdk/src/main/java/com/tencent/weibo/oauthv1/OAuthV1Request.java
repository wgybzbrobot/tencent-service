package com.tencent.weibo.oauthv1;

import com.tencent.weibo.api.RequestAPI;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.exceptions.OAuthClientException;
import com.tencent.weibo.utils.QHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 根据OAuth version 1 标准实现 Request API 接口的类
 */

public class OAuthV1Request implements RequestAPI {
    private QHttpClient qHttpClient;

	private static Log log = LogFactory.getLog(OAuthV1Request.class);

	/**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     */
    public OAuthV1Request(){
        qHttpClient=new QHttpClient();
    }
    
    public OAuthV1Request(QHttpClient qHttpClient) {
        this.qHttpClient=qHttpClient;
    }

    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth) {

        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());
        
        String queryString = OAuthV1Client.getOauthParams(url, "GET", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);
        
        return qHttpClient.httpGet(url, queryString);
    }

    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception {

        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());

        String queryString = OAuthV1Client.getOauthParams(url, "POST", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);

		log.debug("RequestAPI postContent queryString = " + queryString);
		return qHttpClient.httpPost(url, queryString);
    }

    public String postFile(String url, List<NameValuePair> paramsList, List<NameValuePair> files, OAuth oAuth) throws Exception {

        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());

        String queryString = OAuthV1Client.getOauthParams(url, "POST", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);

        return qHttpClient.httpPostWithFile(url, queryString, files);
    }

    /**
     * 如果是使用的是自动生成的QHttpClient，请用该方法关闭连接管理器
     */
    public void shutdownConnection() {
        qHttpClient.shutdownConnection();
        
    }

    public QHttpClient getqHttpClient() {
        return qHttpClient;
    }

    public void setqHttpClient(QHttpClient qHttpClient) {
        this.qHttpClient = qHttpClient;
    }
}

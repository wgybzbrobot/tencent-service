package com.tencent.weibo.oauthv2;

import com.tencent.weibo.api.RequestAPI;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.exceptions.OAuthClientException;
import com.tencent.weibo.utils.QHttpClient;
import com.tencent.weibo.utils.QStrOperate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 *  根据OAuth version 2 标准实现 Request API 接口的类
 */
public class OAuthV2Request implements RequestAPI {

    private QHttpClient qHttpClient;
	private static Log log = LogFactory.getLog(OAuthV2Request.class);

	/**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     */
    public OAuthV2Request(){
        qHttpClient = new QHttpClient();
    }


    public OAuthV2Request(QHttpClient qHttpClient) {
        this.qHttpClient=qHttpClient;
    }

    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth) {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        String queryString = QStrOperate.getQueryString(paramsList);
        return qHttpClient.httpGet(url, queryString);
        
    }

    private void removeExtraClientip(List<NameValuePair> paramsList, OAuthV2 oAuthV2) {
        int i=0;
        boolean found=false;
        for(NameValuePair nvp:paramsList){
            if(nvp.getName()!="clientip"){
                i++;
            }else{
                if(nvp.getValue()!="127.0.0.1"){
                    oAuthV2.setClientIP(nvp.getValue());
                }
                found=true;
                break;
            }
        }
        if(found)paramsList.remove(i);
    }

    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth)
            throws Exception {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        
        String queryString = QStrOperate.getQueryString(paramsList);

		log.debug("RequestAPI postContent queryString = " + queryString);
		return qHttpClient.httpPost(url, queryString);
        
    }


    public String postFile(String url, List<NameValuePair> paramsList,
            List<NameValuePair> files, OAuth oAuth) throws Exception {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        String queryString = QStrOperate.getQueryString(paramsList);
		log.debug("RequestAPI postContent queryString = " + queryString);
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

package com.tencent.weibo.oauthv2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.constants.OAuthConstants;

/**
 * OAuth version 2 认证参数实体类
 */
public class OAuthV2 extends OAuth implements Serializable{

    private static final long serialVersionUID = -4667312552797390709L;
    private String redirectUri = "null";// 授权回调地址
    private String clientId = "";// 申请应用时分配的app_key
    private String clientSecret="";//申请应用时分配到的app_secret
    private String responseType = "code";// code、token，默认为code
    private String type="default";//显示授权页的类型，默认授权页为pc授权页
    private String authorizeCode= null;//用来换取accessToken的授权码
    private String accessToken= null;
    private String expiresIn= null;//accessToken过期时间
    private String grantType= "authorization_code";//填authorization_code, 或refresh_token
    private String refreshToken= null;//刷新token

	//	private static Log log = LogFactory.getLog(OAuthV2.class);// 日志输出

	public OAuthV2() {
        super();
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }

    /**
     * @param redirectUri 认证成功后浏览器会被重定向到这个地址
     */
    public OAuthV2(String redirectUri) {
        super();
        this.redirectUri = redirectUri;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }

    /**
     * @param clientId 应用申请到的APP KEY
     * @param clientSecret 应用申请到的APP SECRET
     * @param redirectUri 认证成功后浏览器会被重定向到这个地址
     */
    public OAuthV2(String clientId, String clientSecret, String redirectUri) {
        super();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }

    /**Authorize code grant方式中，Authorization阶段需要的参数*/
    public List<NameValuePair> getAuthorizationParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("client_id",clientId));
            paramsList.add(new BasicNameValuePair("response_type",  responseType));
            paramsList.add(new BasicNameValuePair("redirect_uri", redirectUri));
        return paramsList;
    }

    /**Authorize code grant方式中，AccessToken阶段需要的参数*/
    public List<NameValuePair> getAccessTokenByCodeParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("client_id",clientId));
            paramsList.add(new BasicNameValuePair("client_secret",clientSecret));
            paramsList.add(new BasicNameValuePair("redirect_uri", redirectUri));
            paramsList.add(new BasicNameValuePair("grant_type",  "authorization_code"));
            paramsList.add(new BasicNameValuePair("code",  authorizeCode));
        return paramsList;
    }

    /**
     * 调用API时，需附带的OAuth鉴权信息
     * @return
     */
    public List<NameValuePair> getTokenParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("oauth_consumer_key",clientId));
            paramsList.add(new BasicNameValuePair("access_token",accessToken));
            paramsList.add(new BasicNameValuePair("openid",  openid));
            paramsList.add(new BasicNameValuePair("clientip", clientIP));
            paramsList.add(new BasicNameValuePair("oauth_version",  oauthVersion));
            paramsList.add(new BasicNameValuePair("scope", scope));
        return paramsList;
    }

    /**重定向地址*/
    public String getRedirectUri() {
        return redirectUri;
    }

    /**重定向地址*/
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**应用的APP KEY*/
    public String getClientId() {
        return clientId;
    }

    /**应用的APP KEY*/
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**授权类型*/
    public String getResponeType() {
        return responseType;
    }

    /**应用申请到的APP SECRET*/
    public String getClientSecret() {
        return clientSecret;
    }

    /**应用申请到的APP SECRET*/
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**授权类型*/
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /** 显示授权页的类型，默认授权页为pc授权页 */
    public String getType() {
        return type;
    }

    /** 显示授权页的类型，默认授权页为pc授权页 */
    public void setType(String type) {
        this.type = type;
    }

    /**授权码*/
    public String getAuthorizeCode() {
        return authorizeCode;
    }

    /**授权码*/
    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**accessToken过期时间*/
    public String getExpiresIn() {
        return expiresIn;
    }

    /**accessToken过期时间*/
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**确定请求的对象，authorization_code或refresh_token*/
    public String getGrantType() {
        return grantType;
    }

    /**确定请求的对象，authorization_code或refresh_token*/
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    /**刷新token*/
    public String getRefreshToken() {
        return refreshToken;
    }

    /**刷新token*/
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

package com.tencent.weibo.oauthv1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.constants.OAuthConstants;

/**
 * OAuth version 1 认证参数实体类
 */

public class OAuthV1 extends OAuth implements Serializable{

    private static final long serialVersionUID = 4695293221245171919L;
    private String oauthCallback = "null";// 认证成功后浏览器会被重定向到这个url中
    private String oauthConsumerKey = "";// AppKey(client credentials)
    private String oauthConsumerSecret = "";// AppSecret
    private String oauthSignatureMethod = "HMAC-SHA1";// 签名方法，暂只支持HMAC-SHA1
    private String oauthToken = ""; // Request Token 或 Access Token
    private String oauthTimestamp = "";// 时间戳
    private String oauthNonce = "";// 单次值，随机字符串，防止重放攻击
    private String oauthTokenSecret = ""; // Request Token 或 Access Token 对应的签名密钥
    private String oauthVerifier = ""; // 验证码

    // private static Log log = LogFactory.getLog(OAuthV1.class);// 日志输出

    public OAuthV1() {
        super();
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * @param oauthCallback 认证成功后浏览器会被重定向到这个地址
     */
    public OAuthV1(String oauthCallback) {
        super();
        this.oauthCallback = oauthCallback;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * 
     * @param oauthConsumerKey 应用申请到的APP KEY
     * @param oauthConsumerSecret 应用申请到的APP SECRET
     * @param oauthCallback 认证成功后浏览器会被重定向到这个地址
     */
    public OAuthV1(String oauthConsumerKey, String oauthConsumerSecret,
            String oauthCallback) {
        super();
        this.oauthConsumerKey = oauthConsumerKey;
        this.oauthConsumerSecret = oauthConsumerSecret;
        this.oauthCallback = oauthCallback;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * 生成 timestamp.
     * 
     * @return timestamp
     */
    private String generateTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 简单生成四次 15103344 到 9999999 之间的数字串，并将他们连接起来作为 Oauth_Nonce 值
     * 
     * @return Oauth_Nonce
     */
    private String generateNonce() {
        String nonce = "";
        for (int i = 0; i < 4; i++) {
            nonce=String.valueOf(random.nextInt(100000000))+nonce;
            for(;nonce.length()<(i+1)*8;nonce="0"+nonce);
        }
        return nonce;
    }

    public List<NameValuePair> getParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        oauthTimestamp = this.generateTimeStamp();
        oauthNonce = this.generateNonce();
        paramsList.add(new BasicNameValuePair("oauth_consumer_key",oauthConsumerKey));
        paramsList.add(new BasicNameValuePair("oauth_signature_method",oauthSignatureMethod));
        paramsList.add(new BasicNameValuePair("oauth_timestamp", oauthTimestamp));
        paramsList.add(new BasicNameValuePair("oauth_nonce", oauthNonce));
        paramsList.add(new BasicNameValuePair("oauth_callback", oauthCallback));
        paramsList.add(new BasicNameValuePair("oauth_version", oauthVersion));
        return paramsList;
    }

    public List<NameValuePair> getAccessParams() {
        List<NameValuePair> paramsList = this.getTokenParamsList();
        paramsList.add(new BasicNameValuePair("oauth_verifier", oauthVerifier));
        return paramsList;
    }

    public List<NameValuePair> getTokenParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        oauthTimestamp = this.generateTimeStamp();
        oauthNonce = this.generateNonce();
        paramsList.add(new BasicNameValuePair("oauth_consumer_key",oauthConsumerKey));
        paramsList.add(new BasicNameValuePair("oauth_signature_method",oauthSignatureMethod));
        paramsList.add(new BasicNameValuePair("oauth_timestamp", oauthTimestamp));
        paramsList.add(new BasicNameValuePair("oauth_nonce", oauthNonce));
        paramsList.add(new BasicNameValuePair("oauth_token", oauthToken));
        paramsList.add(new BasicNameValuePair("oauth_version", oauthVersion));
        return paramsList;
    }

    /**应用的APP KEY*/
    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    /**应用的APP KEY*/
    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    /**使用的签名方法，暂只支持HMAC-SHA1*/
    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    /**使用的签名方法，暂只支持HMAC-SHA1*/
    public void setOauthSignatureMethod(String oauthSignatureMethod) {
        this.oauthSignatureMethod = oauthSignatureMethod;
    }

    /**应用申请到的APP SECRET*/
    public String getOauthConsumerSecret() {
        return oauthConsumerSecret;
    }

    /**应用申请到的APP SECRET*/
    public void setOauthConsumerSecret(String oauthConsumerSecret) {
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    /**存放requestToken或accessToken*/
    public String getOauthToken() {
        return oauthToken;
    }

    /**存放requestToken或accessToken*/
    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    /**时间戳*/
    public String getOauthTimestamp() {
        return oauthTimestamp;
    }

    /**时间戳，通过{@link #generateTimeStamp()}可得到当前时间戳*/
    public void setOauthTimestamp(String oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    /**单次值，随机字符串，作重复检验*/
    public String getOauthNonce() {
        return oauthNonce;
    }

    /**单次值，随机字符串，作重复检验，通过{@link #generateNonce()}可得到随机值*/
    public void setOauthNonce(String oauthNonce) {
        this.oauthNonce = oauthNonce;
    }

    /**重定向地址*/
    public String getOauthCallback() {
        return oauthCallback;
    }

    /**重定向地址*/
    public void setOauthCallback(String oauthCallback) {
        this.oauthCallback = oauthCallback;
    }

    /**存放requestSecret或accessSecret*/
    public String getOauthTokenSecret() {
        return oauthTokenSecret;
    }

    /**存放requestSecret或accessSecret*/
    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    /**授权码*/
    public String getOauthVerifier() {
        return oauthVerifier;
    }

    /**授权码*/
    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }
}

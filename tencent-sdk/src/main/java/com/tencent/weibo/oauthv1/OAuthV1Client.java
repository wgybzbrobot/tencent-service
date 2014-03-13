package com.tencent.weibo.oauthv1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;

import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.exceptions.OAuthClientException;
import com.tencent.weibo.utils.QHttpClient;
import com.tencent.weibo.utils.QStrOperate;

/**
 * 工具类 OAuth version 1 认证授权以及签名相关<br>
 * 如需自定制http管理器请使用 <pre>OAuthV1Client.setQHttpClient(QHttpClient qHttpClient)</pre> <br>
 * 为本工具类指定http管理器
 */

public class OAuthV1Client {

    private static final String hASH_ALGORITHM_NAME = "HmacSHA1";

	private static Log log = LogFactory.getLog(OAuthV1Client.class);

    private static QHttpClient Q_HTTP_CLIENT=new QHttpClient();

    private OAuthV1Client(){
    }

    /**
     * 获取未授权的Request Token
     *
     * @param oAuth
     * @return
     * @throws Exception
     */
    public  static OAuthV1 requestToken(OAuthV1 oAuth) throws Exception {
        if(null==Q_HTTP_CLIENT){
            throw new OAuthClientException("1001");
        }
        String queryString = getOauthParams(OAuthConstants.OAUTH_V1_GET_REQUEST_TOKEN_URL, "GET", oAuth.getOauthConsumerSecret(), "",
                oAuth.getParamsList());
		log.debug("requestToken queryString = " + queryString);

		String responseData = Q_HTTP_CLIENT.simpleHttpGet(OAuthConstants.OAUTH_V1_GET_REQUEST_TOKEN_URL, queryString);
		log.debug("requestToken responseData = " + responseData);

		if (!parseToken(responseData, oAuth)) {// Request Token 授权不通过
            oAuth.setStatus(1);
			log.debug("requestToken past !");
		}
        return oAuth;
    }

    /**
     * 合成转向授权页面的url
     *
     * @param oAuth
     * @return
     */
    public  static String generateAuthorizationURL(OAuthV1 oAuth){
        return OAuthConstants.OAUTH_V1_AUTHORIZE_URL+"?oauth_token="+ oAuth.getOauthToken();
    }

    /**
     * 请求用户授权后，解析开放平台返回的参数是否包含授权码等信息
     *
     * @param responseData 格式：oauth_token=OAUTH_TOKEN&vcode=VERIFY_CODE&openid=OPENID&openkey=OPENKEY
     * @param oAuth
     * @return
     * @throws Exception
     */
     public  static boolean parseAuthorization(String responseData, OAuthV1 oAuth) throws Exception {
         oAuth.setStatus(2);
         if (!QStrOperate.hasValue(responseData)) {
            return false;
        }

        oAuth.setMsg(responseData);
        String[] tokenArray = responseData.split("&");

		 log.debug("parseToken response=>> tokenArray.length = " + tokenArray.length);

		 if (tokenArray.length < 4) {
            return false;
        }

        for(int i=0;i<tokenArray.length;i++){
            if (tokenArray[i].startsWith("oauth_token=")){
                oAuth.setOauthToken(tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length()));
            }
            if (tokenArray[i].startsWith("vcode=")){
                oAuth.setOauthVerifier(tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length()));
            }
            if (tokenArray[i].startsWith("openid=")){
                oAuth.setOpenid(tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length()));
            }
            if (tokenArray[i].startsWith("openkey=")){
                oAuth.setOpenkey(tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length()));
            }
        }

        //检查是否取得验证码
        if(!QStrOperate.hasValue(oAuth.getOauthVerifier())){
            return false;
        }

        oAuth.setStatus(0);
        return true;
    }

    /**
     * 使用授权后的Request Token换取Access Token
     *
     * @param oAuth
     * @return
     * @throws Exception
     */
    public  static OAuthV1 accessToken(OAuthV1 oAuth) throws Exception {

        if(null==Q_HTTP_CLIENT){
            throw new OAuthClientException("1001");
        }
		log.debug("Getting Access Token ...... \n RequestToken = " + oAuth.getOauthToken() + "\nOauthVerifier = "
				+ oAuth.getOauthVerifier());

		String url = OAuthConstants.OAUTH_V1_GET_ACCESS_TOKEN_URL;

        String queryString = getOauthParams(url, "GET", oAuth.getOauthConsumerSecret(),
                oAuth.getOauthTokenSecret(), oAuth.getAccessParams());

		log.debug("accessToken queryString = " + queryString);
		log.debug("accessToken url = " + url);

		String responseData = Q_HTTP_CLIENT.simpleHttpGet(url, queryString);
        System.out.println(responseData);
		log.debug("accessToken responseData = " + responseData);

		if (!parseToken(responseData, oAuth)) {// Access Token 授权不通过
            oAuth.setStatus(3);
        }
        return oAuth;
    }

    /**
     * 处理请求参数 和 生成签名
     *
     * @param url
     * @param httpMethod
     * @param consumerSecret
     * @param tokenSecrect
     * @param queryParamsList
     * @return
     */
    public  static String getOauthParams(String url, String httpMethod, String consumerSecret, String tokenSecrect,
            List<NameValuePair> queryParamsList) {

        // 对参数进行排序
        Comparator<NameValuePair> comparator = new Comparator<NameValuePair>() {
            @Override
			public int compare(NameValuePair p1, NameValuePair p2) {
                int result = p1.getName().compareTo(p2.getName());
                if (0 == result)
                    result = p1.getValue().compareTo(p2.getValue());
                return result;
            }
        };
        Collections.sort(queryParamsList, comparator);

        //生成请求URL
        String urlWithParameter = url;

        String queryString = QStrOperate.getQueryString(queryParamsList);
        if (QStrOperate.hasValue(queryString)) {
            urlWithParameter += "?" + queryString;
        }

        URL aUrl = null;
        try {
            aUrl = new URL(urlWithParameter);
        } catch (MalformedURLException e) {
            System.err.println("URL parse error:" + e.getLocalizedMessage());
        }

        String signature = generateSignature(aUrl, consumerSecret, tokenSecrect, httpMethod, queryParamsList);

        queryString += "&oauth_signature=";
        queryString += QStrOperate.paramEncode(signature);
        return queryString;
    }

    /**
     * 验证Token返回结果
     *
     * @param response
     * @param oAuth
     * @return
     * @throws Exception
     */
    public  static boolean parseToken(String response, OAuthV1 oAuth) throws Exception {
        if (!QStrOperate.hasValue(response)) {
            return false;
        }

        oAuth.setMsg(response);
        String[] tokenArray = response.split("&");
		log.debug("parseToken response=>> tokenArray.length = " + tokenArray.length);

		if (tokenArray.length < 2) {
            return false;
        }

        String tmpStr;
        for(int i=0;i<tokenArray.length;i++){
            if (tokenArray[i].startsWith("oauth_token=")){
                tmpStr=tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length());
                if(!QStrOperate.hasValue(tmpStr)){
                    return false;
                }
                oAuth.setOauthToken(tmpStr);
            }
            if (tokenArray[i].startsWith("oauth_token_secret=")){
                tmpStr=tokenArray[i].substring(tokenArray[i].indexOf('=')+1, tokenArray[i].length());
                if(!QStrOperate.hasValue(tmpStr)){
                    return false;
                }
                oAuth.setOauthTokenSecret(tmpStr);
            }
        }

        return true;
    }

    /**
     * 生成签名值
     *
     * @param url
     * @param consumerSecret
     * @param accessTokenSecret
     * @param httpMethod
     * @param queryParamsList
     * @return
     */
    private  static String generateSignature(URL url, String consumerSecret, String accessTokenSecret, String httpMethod,
            List<NameValuePair> queryParamsList) {
        String base = generateSignatureBase(url, httpMethod, queryParamsList);
        return generateSignature(base, consumerSecret, accessTokenSecret);
    }

    /**
     * 生成签名值
     *
     * @param base
     * @param consumerSecret
     * @param accessTokenSecret
     * @return
     */
    private  static String generateSignature(String base, String consumerSecret, String accessTokenSecret) {
        try {
            Mac mac = Mac.getInstance(hASH_ALGORITHM_NAME);
            String oAuthSignature = QStrOperate.paramEncode(consumerSecret) + "&"
                    + ((accessTokenSecret == null) ? "" : QStrOperate.paramEncode(accessTokenSecret));

			log.debug("签名密钥：" + oAuthSignature);
			log.debug("待签名原文：" + base);

			SecretKeySpec spec = new SecretKeySpec(oAuthSignature.getBytes(), hASH_ALGORITHM_NAME);
            mac.init(spec);
            byte[] bytes = mac.doFinal(base.getBytes());
            return new String(Base64Encoder.encode(bytes));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 处理签名请求和参数
     *
     * @param url
     * @param httpMethod
     * @param queryParamsList
     * @return
     */
    private  static String generateSignatureBase(URL url, String httpMethod, List<NameValuePair> queryParamsList) {

        StringBuilder base = new StringBuilder();
        base.append(httpMethod.toUpperCase());
        base.append("&");
        base.append(QStrOperate.paramEncode(getNormalizedUrl(url)));
        base.append("&");
        base.append(QStrOperate.paramEncode(QStrOperate.getQueryString(queryParamsList)));

        return base.toString();
    }

    /**
     * 处理请求URL
     *
     * @param url
     * @return
     */
    private static String getNormalizedUrl(URL url) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(url.getProtocol());
            buf.append("://");
            buf.append(url.getHost());
            if ((url.getProtocol().equals("http") || url.getProtocol().equals("https")) && url.getPort() != -1) {
                buf.append(":");
                buf.append(url.getPort());
            }
            buf.append(url.getPath());
            return buf.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static QHttpClient getQHttpClient() {
        return Q_HTTP_CLIENT;
    }

    public static void setQHttpClient(QHttpClient qHttpClient) {
        Q_HTTP_CLIENT = qHttpClient;
    }

}

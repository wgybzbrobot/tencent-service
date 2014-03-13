package com.tencent.weibo.utils;

import com.tencent.weibo.beans.RouteCfg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * 自定义参数的Httpclient。<br>
 * 提供httpGet，httpPost两种传送消息的方式<br>
 * 提供httpPost上传文件的方式
 */
@SuppressWarnings("deprecation")
public class QHttpClient {

    // SDK默认参数设置
    public static final int CON_TIME_OUT_MS = 5000;
    public static final int SO_TIME_OUT_MS = 5000;
    public static final int MAX_CONNECTIONS_PER_HOST = 20;
    public static final int MAX_TOTAL_CONNECTIONS = 200;
    
    private int conTimeOutMs;
    private int soTimeOutMs;

    // 日志输出
    private static Log log = LogFactory.getLog(QHttpClient.class);

    private final HttpClient httpClient;

    public QHttpClient() {
        this(MAX_CONNECTIONS_PER_HOST, MAX_TOTAL_CONNECTIONS, CON_TIME_OUT_MS, SO_TIME_OUT_MS,null,null);
    }

	/**
	 * 意义不大，因为腾讯的调用限制指根据appkey，和ip无关，等哪天腾讯大赦了再用吧。。。
	 *
	 * @param networkInterface
	 */
	@Deprecated
	public QHttpClient(NetworkInterface networkInterface) {
		this(MAX_CONNECTIONS_PER_HOST, MAX_TOTAL_CONNECTIONS, CON_TIME_OUT_MS, SO_TIME_OUT_MS, null, null, networkInterface);
	}

	public QHttpClient(int maxConnectionsPerHost, int maxTotalConnections, int conTimeOutMs, int soTimeOutMs, List<RouteCfg> routeCfgList, HttpHost proxy) {
		this(maxConnectionsPerHost, maxTotalConnections, conTimeOutMs, soTimeOutMs, routeCfgList, proxy, null);
	}

	/**
	 * 个性化配置连接管理器
	 *
	 * @param maxConnectionsPerHost 设置默认的连接到每个主机的最大连接数
	 * @param maxTotalConnections   设置整个管理连接器的最大连接数
	 * @param conTimeOutMs          连接超时
	 * @param soTimeOutMs           socket超时
	 * @param routeCfgList          特殊路由配置列表，若无请填null
	 * @param proxy                 代理设置，若无请填null
	 * @param networkInterface      多ip网卡，可以使用本地多ip与远端服务器建立链接，若不启用该功能请填null
	 */
	public QHttpClient(int maxConnectionsPerHost, int maxTotalConnections, int conTimeOutMs, int soTimeOutMs, List<RouteCfg> routeCfgList, HttpHost proxy, NetworkInterface networkInterface) {
		this.conTimeOutMs = conTimeOutMs;
		this.soTimeOutMs = soTimeOutMs;
		// 使用默认的 socket factories 注册 "http" & "https" protocol scheme
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		supportedSchemes.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(supportedSchemes);

		// 参数设置
		HttpParams httpParams = new SyncBasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conTimeOutMs);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOutMs);

		HttpProtocolParams.setUseExpectContinue(httpParams, false);

		connectionManager.setDefaultMaxPerRoute(maxConnectionsPerHost);
		connectionManager.setMaxTotal(maxTotalConnections);

		HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.IGNORE_COOKIES);

		// 对特定路由修改最大连接数
//		if (null != routeCfgList) {
//			for (RouteCfg routeCfg : routeCfgList) {
//				HttpHost localhost = new HttpHost(routeCfg.getHost(), routeCfg.getPort());
//				connectionManager.setMaxForRoute(new HttpRoute(localhost), routeCfg.getMaxConnetions());
//			}
//		}

		httpClient = new DefaultHttpClient(connectionManager, httpParams);

		//设置代理
		if (null != proxy) {
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		//设置多本地ip的routePlanner
		if (null != networkInterface) {
			((AbstractHttpClient) httpClient).setRoutePlanner(
					new MultiLocalAddressRoutePlanner(connectionManager.getSchemeRegistry(), networkInterface));
		}
	}

	/**
     * Get方法传送消息（无压缩）
     * 
     * @param url  连接的URL
     * @param queryString  请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String simpleHttpGet(String url, String queryString) throws Exception {

        String responseData = null;
        if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
        }
		log.debug("QHttpClient simpleHttpGet [1] url = " + url);
        
        HttpGet httpGet = new HttpGet(url);
        httpGet.getParams().setParameter("http.socket.timeout", conTimeOutMs);

		HttpResponse response;
		response = httpClient.execute(httpGet);
		log.debug("QHttpClient simpleHttpGet [2] StatusLine : " + response.getStatusLine());
		responseData = EntityUtils.toString(response.getEntity());
		httpGet.abort();
		log.debug("QHttpClient simpleHttpGet [3] Response = " + responseData.toString());

        return responseData.toString();
    }
    
    /**
     * Get方法传送消息
     * 
     * @param url  连接的URL
     * @param queryString  请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String httpGet(String url, String queryString) {

        StringBuilder responseData = new StringBuilder();
        if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
        }
		log.debug("QHttpClient httpGet [1] url = " + url);

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpGet.getParams().setParameter("http.socket.timeout", conTimeOutMs);

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		log.debug("QHttpClient httpGet [2] StatusLine : " + response.getStatusLine());

        try {
            byte[] b=new byte[2048];
            GZIPInputStream gzin = new GZIPInputStream(response.getEntity().getContent());
            int length=0;
            while((length=gzin.read(b))!=-1){
                responseData.append(new String(b,0,length));
            }
            gzin.close();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.abort();
        }
		log.debug("QHttpClient httpGet [3] Response = " + responseData.toString());

        return responseData.toString();
    }

    /**
     * Post方法传送消息
     * 
     * @param url  连接的URL
     * @param queryString 请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String httpPost(String url, String queryString) throws Exception {
        StringBuilder responseData = new StringBuilder();
        URI tmpUri = new URI(url);
        URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(), tmpUri.getPort(), tmpUri.getPath(),
				queryString, null);
		log.debug("QHttpClient httpPost [1] url = " + uri.toURL());

        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpPost.getParams().setParameter("http.socket.timeout", conTimeOutMs);
        if (queryString != null && !queryString.equals("")) {
            StringEntity reqEntity = new StringEntity(queryString);
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            // 设置请求的数据
            httpPost.setEntity(reqEntity);
		}
		HttpResponse response = httpClient.execute(httpPost);
		log.debug("QHttpClient httpPost [2] StatusLine = " + response.getStatusLine());

        try {
            byte[] b=new byte[2048];
            GZIPInputStream gzin = new GZIPInputStream(response.getEntity().getContent());
            int length=0;
            while((length=gzin.read(b))!=-1){
                responseData.append(new String(b,0,length));
            }
            gzin.close();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
        }
		log.debug("QHttpClient httpPost [3] Response = " + responseData.toString());
        return responseData.toString();
    }

    /**
     * Post方法传送消息
     * 
     * @param url  连接的URL
     * @param queryString 请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String httpPostWithFile(String url, String queryString, List<NameValuePair> files) throws Exception {

        StringBuilder responseData = new StringBuilder();

        URI tmpUri = new URI(url);
        URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(), tmpUri.getPort(), tmpUri.getPath(),
				queryString, null);
		log.debug("QHttpClient httpPostWithFile [1]  uri = " + uri.toURL());
        MultipartEntity mpEntity = new MultipartEntity();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        StringBody stringBody;
        FileBody fileBody;
        File targetFile;
        String filePath;
        FormBodyPart fbp;

        List<NameValuePair> queryParamList = QStrOperate.getQueryParamsList(queryString);
        for (NameValuePair queryParam : queryParamList) {
            stringBody = new StringBody(queryParam.getValue(), Charset.forName("UTF-8"));
			fbp = new FormBodyPart(queryParam.getName(), stringBody);
			mpEntity.addPart(fbp);
			log.debug("------- " + queryParam.getName() + " = " + queryParam.getValue());
        }

        for (NameValuePair param : files) {
			filePath = param.getValue();
			targetFile = new File(filePath);
			log.debug("---------- File Path = " + filePath + "\n---------------- MIME Types = "
					+ QHttpUtil.getContentType(targetFile));
			fileBody = new FileBody(targetFile, QHttpUtil.getContentType(targetFile), "UTF-8");
			fbp = new FormBodyPart(param.getName(), fileBody);
			mpEntity.addPart(fbp);

        }

        log.debug("---------- Entity Content Type = " + mpEntity.getContentType());

		httpPost.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(httpPost);
		log.debug("QHttpClient httpPostWithFile [2] StatusLine = " + response.getStatusLine());

        try {
            byte[] b=new byte[2048];
            GZIPInputStream gzin = new GZIPInputStream(response.getEntity().getContent());
            int length=0;
            while((length=gzin.read(b))!=-1){
                responseData.append(new String(b,0,length));
            }
            gzin.close();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}

		log.debug("QHttpClient httpPostWithFile [3] Response = " + responseData.toString());
		return responseData.toString();
    }

    /**
     * 断开QHttpClient的连接
     */
    public void shutdownConnection() {
        try {
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getConTimeOutMs() {
        return conTimeOutMs;
    }

    public void setConTimeOutMs(int conTimeOutMs) {
        this.conTimeOutMs = conTimeOutMs;
    }

    public int getSoTimeOutMs() {
        return soTimeOutMs;
    }

    public void setSoTimeOutMs(int soTimeOutMs) {
        this.soTimeOutMs = soTimeOutMs;
    }
}

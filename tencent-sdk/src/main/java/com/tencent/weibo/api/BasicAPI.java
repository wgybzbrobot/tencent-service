package com.tencent.weibo.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.tencent.weibo.constants.APIConstants;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1Request;
import com.tencent.weibo.oauthv2.OAuthV2Request;
import com.tencent.weibo.utils.QHttpClient;

/**
 * API类的通用部分
 */
public abstract class BasicAPI {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected RequestAPI requestAPI;
	protected String apiBaseUrl = null;
	protected ObjectMapper mapper = new ObjectMapper();

	public BasicAPI(String OAuthVersion) {
		if (OAuthVersion == OAuthConstants.OAUTH_VERSION_1) {
			requestAPI = new OAuthV1Request();
			apiBaseUrl = APIConstants.API_V1_BASE_URL;
		} else if (OAuthVersion == OAuthConstants.OAUTH_VERSION_2_A) {
			requestAPI = new OAuthV2Request();
			apiBaseUrl = APIConstants.API_V2_BASE_URL;
		}
		configObjectMapper();
	}

	public BasicAPI(String OAuthVersion, QHttpClient qHttpClient) {
		if (OAuthVersion == OAuthConstants.OAUTH_VERSION_1) {
			requestAPI = new OAuthV1Request(qHttpClient);
			apiBaseUrl = APIConstants.API_V1_BASE_URL;
		} else if (OAuthVersion == OAuthConstants.OAUTH_VERSION_2_A) {
			requestAPI = new OAuthV2Request(qHttpClient);
			apiBaseUrl = APIConstants.API_V2_BASE_URL;
		}
		configObjectMapper();
	}

	public String getAPIBaseUrl() {
		return apiBaseUrl;
	}

	public abstract void setAPIBaseUrl(String apiBaseUrl);

	public void setQHttpClient(QHttpClient qHttpClient) {
		requestAPI.setqHttpClient(qHttpClient);
	}

	public void shutdownConnection() {
		requestAPI.shutdownConnection();
	}

	private void configObjectMapper() {
//		mapper.getDeserializationConfig().addHandler(new DeserializationProblemHandler() {
//			@Override
//			public boolean handleUnknownProperty(DeserializationContext ctxt, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException, JsonProcessingException {
//				logger.warn("unknown property: " + propertyName + ", beanOrClass: " + beanOrClass);
//				return true;
//			}
//		});
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}

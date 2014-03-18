package cc.pp.service.tencent.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import cc.pp.service.tencent.dao.StatusDao;
import cc.pp.service.tencent.model.ShowWeiboData;

/**
 * Created by chenwei on 14-2-23.
 */
public class StatusDaoHttpClient implements StatusDao {

	public static void main(String[] args) {
		StatusDaoHttpClient dao = new StatusDaoHttpClient("http://60.169.74.152:8080", new DefaultHttpClient());
		System.out.println(dao.get("abc", 123));
	}
	private final HttpClient httpClient;
	private final String serviceAddress;

	private final ObjectMapper mapper = new ObjectMapper();

	public StatusDaoHttpClient(String serviceAddress, HttpClient httpClient) {
		this.serviceAddress = serviceAddress;
		this.httpClient = httpClient;
	}

	@Override
	public ShowWeiboData get(String uid, long wid) {
		HttpGet get = new HttpGet(serviceAddress + "/tencent/users/" + uid + "/t/" + wid);
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try (InputStream in = response.getEntity().getContent()) {
					return mapper.readValue(in, ShowWeiboData.class);
				}
			} else {
				throw new RuntimeException(response.getStatusLine().toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

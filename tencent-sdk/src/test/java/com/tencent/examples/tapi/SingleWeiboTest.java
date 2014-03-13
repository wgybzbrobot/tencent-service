package com.tencent.examples.tapi;

import cc.pp.service.tencent.model.Weibo;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

public class SingleWeiboTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String[] token = {"36c0606849704d038a7bb9367f3e688b", "5ef06c315870d84ad00f441bcb91a408"};
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token[0], token[1]);
		TAPI weibo = new TAPI(oauth.getOauthVersion());
		Weibo result = weibo.show(oauth, "json", "123");
		System.out.println(result);
	}

}

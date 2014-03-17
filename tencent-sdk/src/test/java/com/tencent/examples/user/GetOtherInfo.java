package com.tencent.examples.user;

import cc.pp.service.tencent.model.OtherInfoData;

import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

public class GetOtherInfo {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		OAuthV1 oauth = new OAuthV1();
		String[] tokens = {"2c656d405a1d4587b7b99d143102d002", "b36e96d43ed75485258f0fdf0cefe143"};
		OauthInit.oauthInit(oauth, tokens[0], tokens[1]);
		UserAPI user = new UserAPI(oauth.getOauthVersion());
		String uid = "cxpolice";
		OtherInfoData userinfo = user.otherInfo(oauth, "json", uid, "");
		System.out.println(userinfo);
	}

}

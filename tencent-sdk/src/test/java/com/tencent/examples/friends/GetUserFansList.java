package com.tencent.examples.friends;

import java.io.EOFException;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import com.tencent.weibo.api.FriendsAPI;
import com.tencent.weibo.constants.OauthInit;
import cc.pp.service.tencent.model.UserFansList;
import com.tencent.weibo.oauthv1.OAuthV1;

public class GetUserFansList {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		OAuthV1 oauth = new OAuthV1();
		String[] tokens = { "2c656d405a1d4587b7b99d143102d002", "b36e96d43ed75485258f0fdf0cefe143" };
		OauthInit.oauthInit(oauth, tokens[0], tokens[1]);
		FriendsAPI fa = new FriendsAPI(oauth.getOauthVersion());
		String uid = "cxpolice";
		ObjectMapper mapper = new ObjectMapper();
		String fansinfo = fa.userFanslist(oauth, "json", "30", "0", uid, "", "1", "0");
		try {
			UserFansList userFansList = mapper.readValue(fansinfo, UserFansList.class);
			System.out.println(userFansList.getData().getCurnum());
		} catch (UnrecognizedPropertyException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		}

	}

}

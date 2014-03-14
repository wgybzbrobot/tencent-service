package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.Infos;
import cc.pp.service.tencent.model.OtherInfo;
import cc.pp.service.tencent.model.UserFansList;
import cc.pp.service.tencent.model.UserIdolList;
import cc.pp.service.token.tencent.TencentToken;
import cc.pp.service.token.tencent.TencentTokenService;

import com.tencent.weibo.api.FriendsAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

public class TencentUserInfoDaoImpl implements TencentUserInfoDao {

	private final TencentTokenService tokenService;

	public TencentUserInfoDaoImpl(TencentTokenService tokenService) {
		this.tokenService = tokenService;
	}

	/**
	 * 用户基础信息
	 */
	@Override
	public OtherInfo getTencentUserBaseInfo(String uid) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		UserAPI userAPI = new UserAPI(oauth.getOauthVersion());
		try {
			return userAPI.otherInfo(oauth, "json", uid, "");
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", uid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

	/**
	 * 粉丝列表，cursor从1开始
	 */
	@Override
	public UserFansList getTencentUserFans(String uid, int cursor) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		FriendsAPI friendsApi = new FriendsAPI(oauth.getOauthVersion());
		try {
			return friendsApi.userFanslist(oauth, "json", "30", String.valueOf(30 * (cursor - 1)), uid, "",
					"1", "0");
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", uid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

	@Override
	public UserIdolList getTencentUserFriends(String uid, int cursor) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		FriendsAPI friendsApi = new FriendsAPI(oauth.getOauthVersion());
		try {
			return friendsApi.userIdollist(oauth, "json", "30", String.valueOf(30 * (cursor - 1)), uid,
					"", "0", "0");
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", uid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

	@Override
	public Infos getTencentUserBaseInfos(String uids) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		UserAPI userAPI = new UserAPI(oauth.getOauthVersion());
		try {
			return userAPI.infos(oauth, "json", uids, "");
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", uids, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

}

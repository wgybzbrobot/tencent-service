package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.InfosData;
import cc.pp.service.tencent.model.OtherInfoData;
import cc.pp.service.tencent.model.UserFansListData;
import cc.pp.service.tencent.model.UserIdolListData;
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
	public OtherInfoData getTencentUserBaseInfo(String uid) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		UserAPI userAPI = new UserAPI(oauth.getOauthVersion());
		return userAPI.otherInfo(oauth, "json", uid, "");
	}

	/**
	 * 粉丝列表，cursor从1开始
	 */
	@Override
	public UserFansListData getTencentUserFans(String uid, int cursor) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		FriendsAPI friendsApi = new FriendsAPI(oauth.getOauthVersion());
		return friendsApi.userFanslist(oauth, "json", "30", String.valueOf(30 * (cursor - 1)), uid, "", "1", "0");
	}

	@Override
	public UserIdolListData getTencentUserFriends(String uid, int cursor) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		FriendsAPI friendsApi = new FriendsAPI(oauth.getOauthVersion());
		return friendsApi.userIdollist(oauth, "json", "30", String.valueOf(30 * (cursor - 1)), uid, "", "0", "0");
	}

	@Override
	public InfosData getTencentUserBaseInfos(String uids) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		UserAPI userAPI = new UserAPI(oauth.getOauthVersion());
		return userAPI.infos(oauth, "json", uids, "");
	}

}

package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.ShowWeibo;
import cc.pp.service.tencent.model.UserTimeline;
import cc.pp.service.token.tencent.TencentToken;
import cc.pp.service.token.tencent.TencentTokenService;

import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

public class TencentWeiboInfoDaoImpl implements TencentWeiboInfoDao {

	private final TencentTokenService tokenService;

	public TencentWeiboInfoDaoImpl(TencentTokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public UserTimeline getTencentUserWeibos(String uid) {
		return getTencentUserWeibos(uid, "0", "0", "0");
	}

	@Override
	public UserTimeline getTencentUserWeibos(String uid, long lasttime, String lastwid) {
		return getTencentUserWeibos(uid, "1", String.valueOf(lasttime), lastwid);
	}

	private UserTimeline getTencentUserWeibos(String uid, String pageflag, String lasttime, String lastwid) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		StatusesAPI statusesApi = new StatusesAPI(oauth.getOauthVersion());
		try {
			return statusesApi.userTimeline(oauth, "json", pageflag, lasttime, "70", lastwid, uid, "", "0", "0");
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", uid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

	@Override
	public ShowWeibo getSingleWeiboDetail(String wid) {

		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		TAPI weiboApi = new TAPI(oauth.getOauthVersion());
		try {
			return weiboApi.show(oauth, "json", wid);
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", wid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

	@Override
	public UserTimeline getTencentSingleWeiboResposts(String wid) {
		return getTencentSingleWeiboRepComs("rep", wid, "0", "0", "0");
	}

	@Override
	public UserTimeline getTencentSingleWeiboResposts(String wid, long lasttime, String lastwid) {
		return getTencentSingleWeiboRepComs("rep", wid, "1", String.valueOf(lasttime), lastwid);
	}

	@Override
	public UserTimeline getTencentSingleWeiboComments(String wid) {
		return getTencentSingleWeiboRepComs("com", wid, "0", "0", "0");
	}

	@Override
	public UserTimeline getTencentSingleWeiboComments(String wid, long lasttime, String lastwid) {
		return getTencentSingleWeiboRepComs("com", wid, "1", String.valueOf(lasttime), lastwid);
	}

	private UserTimeline getTencentSingleWeiboRepComs(String type, String wid, String pageflag, String lasttime,
			String lastwid) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		TAPI weiboApi = new TAPI(oauth.getOauthVersion());
		try {
			if ("rep".equalsIgnoreCase(type)) {
				return weiboApi.reList(oauth, "json", "0", wid, pageflag, lasttime, "100", lastwid);
			} else if ("com".equalsIgnoreCase(type)) {
				return weiboApi.reList(oauth, "json", "1", wid, pageflag, lasttime, "100", lastwid);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("WeiboException: %s\t%s\t%s", wid, token.getAccessToken(),
					token.getTokenSecret()), e);
		}
	}

}

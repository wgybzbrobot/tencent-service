package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.ShowWeiboData;
import cc.pp.service.tencent.model.UserTimelineData;
import cc.pp.service.token.tencent.TencentToken;
import cc.pp.service.token.tencent.TencentTokenService;

import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

public class TencentWeiboInfoDaoImpl implements TencentWeiboInfoDao {

	//	private static Logger logger = LoggerFactory.getLogger(TencentWeiboInfoDaoImpl.class);

	private final TencentTokenService tokenService;

	public TencentWeiboInfoDaoImpl(TencentTokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public UserTimelineData getTencentUserWeibos(String uid) {
		return getTencentUserWeibos(uid, "0", "0", "0", "0");
	}

	@Override
	public UserTimelineData getTencentUserWeibos(String uid, long lasttime, String lastwid) {
		return getTencentUserWeibos(uid, "1", String.valueOf(lasttime), lastwid, "0");
	}

	@Override
	public UserTimelineData getTencentUserOriRepWeibos(String uid, long lasttime, String lastwid) {
		return getTencentUserWeibos(uid, "1", String.valueOf(lasttime), lastwid, "3");
	}

	private UserTimelineData getTencentUserWeibos(String uid, String pageflag, String lasttime, String lastwid,
			String type) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		StatusesAPI statusesApi = new StatusesAPI(oauth.getOauthVersion());
		return statusesApi.userTimeline(oauth, "json", pageflag, lasttime, "70", lastwid, uid, "", type, "0");
	}

	@Override
	public ShowWeiboData getSingleWeiboDetail(String wid) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		TAPI weiboApi = new TAPI(oauth.getOauthVersion());
		return weiboApi.show(oauth, "json", wid);
	}

	@Override
	public UserTimelineData getTencentSingleWeiboResposts(String wid) {
		return getTencentSingleWeiboRepComs("rep", wid, "0", "0", "0");
	}

	@Override
	public UserTimelineData getTencentSingleWeiboResposts(String wid, long lasttime, String lastwid) {
		return getTencentSingleWeiboRepComs("rep", wid, "1", String.valueOf(lasttime), lastwid);
	}

	@Override
	public UserTimelineData getTencentSingleWeiboComments(String wid) {
		return getTencentSingleWeiboRepComs("com", wid, "0", "0", "0");
	}

	@Override
	public UserTimelineData getTencentSingleWeiboComments(String wid, long lasttime, String lastwid) {
		return getTencentSingleWeiboRepComs("com", wid, "1", String.valueOf(lasttime), lastwid);
	}

	private UserTimelineData getTencentSingleWeiboRepComs(String type, String wid, String pageflag, String lasttime,
			String lastwid) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		TAPI weiboApi = new TAPI(oauth.getOauthVersion());
		if ("rep".equalsIgnoreCase(type)) {
			return weiboApi.reList(oauth, "json", "0", wid, pageflag, lasttime, "100", lastwid);
		} else if ("com".equalsIgnoreCase(type)) {
			return weiboApi.reList(oauth, "json", "1", wid, pageflag, lasttime, "100", lastwid);
		} else {
			return null;
		}
	}

	@Override
	public UserTimelineData getTencentUserMentions(String uid) {
		return getTencentUserMentions(uid, "0", "0", "0", "0");
	}

	@Override
	public UserTimelineData getTencentUserMentions(String uid, long lasttime, String lastwid) {
		return getTencentUserMentions(uid, "1", String.valueOf(lasttime), lastwid, "0");
	}

	@Override
	public UserTimelineData getTencentUserOriRepMentions(String uid, long lasttime, String lastwid) {
		return getTencentUserMentions(uid, "1", String.valueOf(lasttime), lastwid, "3");
	}

	private UserTimelineData getTencentUserMentions(String uid, String pageflag, String lasttime, String lastwid,
			String type) {
		TencentToken token = tokenService.getToken(uid);
		if (token == null) {
			return null;
		}
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		StatusesAPI statusesApi = new StatusesAPI(oauth.getOauthVersion());
		return statusesApi.mentionsTimeline(oauth, "json", pageflag, lasttime, "70", lastwid, type, "0");
	}

}

package cc.pp.service.tencent.dao;

import cc.pp.service.tencent.model.Weibo;
import cc.pp.service.token.TencentTokenServiceImpl;
import cc.pp.service.token.tencent.TencentToken;
import cc.pp.service.token.tencent.TencentTokenService;

import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OauthInit;
import com.tencent.weibo.oauthv1.OAuthV1;

/**
 * 微博相关的dao
 * Created by chenwei on 14-2-18.
 */
public class StatusDaoImpl implements StatusDao {

	private final TencentTokenService tokenService = new TencentTokenServiceImpl();

	@Override
	public Weibo get(String uid, long wid) {
		TencentToken token = tokenService.getRandomToken();
		OAuthV1 oauth = new OAuthV1();
		OauthInit.oauthInit(oauth, token.getAccessToken(), token.getTokenSecret());
		TAPI weiboApi = new TAPI(oauth.getOauthVersion());
		return weiboApi.show(oauth, "json", String.valueOf(wid));
	}

}

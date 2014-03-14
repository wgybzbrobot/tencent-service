package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.ShowWeibo;
import cc.pp.service.tencent.model.UserTimeline;


/**
 * 腾讯微博数据接口
 * @author wgybzb
 *
 */
public interface TencentWeiboInfoDao {

	/**
	 * 获取单条微博详细数据
	 */
	public ShowWeibo getSingleWeiboDetail(String wid);

	/**
	 * 单条微博转发列表
	 */
	public UserTimeline getTencentSingleWeiboResposts(String wid);

	public UserTimeline getTencentSingleWeiboResposts(String wid, long lasttime, String lastwid);

	/**
	 * 单条微博评论列表
	 */
	public UserTimeline getTencentSingleWeiboComments(String wid);

	public UserTimeline getTencentSingleWeiboComments(String wid, long lasttime, String lastwid);

	/**
	 * 用户微博数据
	 */
	public UserTimeline getTencentUserWeibos(String uid);

	public UserTimeline getTencentUserWeibos(String uid, long lasttime, String lastwid);

}

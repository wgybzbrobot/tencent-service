package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.ShowWeibo;
import cc.pp.service.tencent.model.UserTimelineData;


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
	public UserTimelineData getTencentSingleWeiboResposts(String wid);

	public UserTimelineData getTencentSingleWeiboResposts(String wid, long lasttime, String lastwid);

	/**
	 * 单条微博评论列表
	 */
	public UserTimelineData getTencentSingleWeiboComments(String wid);

	public UserTimelineData getTencentSingleWeiboComments(String wid, long lasttime, String lastwid);

	/**
	 * 用户微博数据
	 */
	public UserTimelineData getTencentUserWeibos(String uid);

	public UserTimelineData getTencentUserWeibos(String uid, long lasttime, String lastwid);

	public UserTimelineData getTencentUserOriRepWeibos(String uid, long lasttime, String lastwid);

	/**
	 * 获取@当前用户的最新微博
	 */
	public UserTimelineData getTencentUserMentions(String uid);

	public UserTimelineData getTencentUserMentions(String uid, long lasttime, String lastwid);

	public UserTimelineData getTencentUserOriRepMentions(String uid, long lasttime, String lastwid);

}

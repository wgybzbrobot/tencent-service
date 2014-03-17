package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.InfosData;
import cc.pp.service.tencent.model.OtherInfoData;
import cc.pp.service.tencent.model.UserFansListData;
import cc.pp.service.tencent.model.UserIdolListData;


/**
 * 腾讯用户数据接口
 * @author wgybzb
 *
 */
public interface TencentUserInfoDao {

	/**
	 * 用户基础信息
	 */
	public OtherInfoData getTencentUserBaseInfo(String uid);

	/**
	 * 用户粉丝数据
	 */
	public UserFansListData getTencentUserFans(String uid, int cursor);

	/**
	 * 用户关注数据
	 */
	public UserIdolListData getTencentUserFriends(String uid, int cursor);

	/**
	 * 获取一批人的基础信息
	 */
	public InfosData getTencentUserBaseInfos(String uids);

}

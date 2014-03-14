package cc.pp.service.tencent.dao.info;

import cc.pp.service.tencent.model.Infos;
import cc.pp.service.tencent.model.OtherInfo;
import cc.pp.service.tencent.model.UserFansList;
import cc.pp.service.tencent.model.UserIdolList;


/**
 * 腾讯用户数据接口
 * @author wgybzb
 *
 */
public interface TencentUserInfoDao {

	/**
	 * 用户基础信息
	 */
	public OtherInfo getTencentUserBaseInfo(String uid);

	/**
	 * 用户粉丝数据
	 */
	public UserFansList getTencentUserFans(String uid, int cursor);

	/**
	 * 用户关注数据
	 */
	public UserIdolList getTencentUserFriends(String uid, int cursor);

	/**
	 * 获取一批人的基础信息
	 */
	public Infos getTencentUserBaseInfos(String uids);

}

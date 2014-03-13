package cc.pp.service.tencent.dao;

import cc.pp.service.tencent.model.Weibo;

/**
 * Created by chenwei on 14-2-21.
 */
public interface StatusDao {

	Weibo get(String uid, long wid);

}

package cc.pp.service.tencent.dao;

import cc.pp.service.tencent.model.ShowWeibo;

/**
 * Created by chenwei on 14-2-21.
 */
public interface StatusDao {

	ShowWeibo get(String uid, long wid);

}

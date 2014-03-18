package cc.pp.service.tencent.dao;

import cc.pp.service.tencent.model.ShowWeiboData;

/**
 * Created by chenwei on 14-2-21.
 */
public interface StatusDao {

	ShowWeiboData get(String uid, long wid);

}

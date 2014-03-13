package cc.pp.service.tencent.api;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import cc.pp.service.tencent.dao.StatusDao;
import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.ExceptionModel;
import cc.pp.service.tencent.model.Result;

/**
 * 腾讯微博相关的restlet资源
 * Created by chenwei on 14-2-18.
 */
public class StatusResource extends ServerResource {

	private StatusDao statusDao;
	private long id;
	private String uid;

	@Override
	public void doInit() {
		TencentApiApplication application = (TencentApiApplication) getApplication();
		statusDao = application.getStatusDao();
		uid = (String) this.getRequest().getAttributes().get("uid");
		id = Long.valueOf((String) this.getRequest().getAttributes().get("id"));
	}

	@Get("json")
	public Object getWeibo() {
		try {
			return statusDao.get(uid, id);
		} catch (TencentApiException e) {
			setStatus(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST);
			return new ExceptionModel<Result<?>>("invoke tencent error").addError(e.getResult());
		}
	}

}

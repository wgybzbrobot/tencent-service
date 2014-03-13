package cc.pp.service.tencent.exception;

import cc.pp.service.tencent.model.Result;

/**
 * Created by chenwei on 14-2-21.
 */
public class TencentApiException extends RuntimeException {

	private static final long serialVersionUID = -4563517788303351847L;
	private final Result<?> result;

	public TencentApiException(Result<?> result) {
		this.result = result;
	}

	public int getErrCode() {
		return result.errcode;
	}

	public String getMsg() {
		return result.msg;
	}

	public Result<?> getResult() {
		return result;
	}

	public int getRet() {
		return result.ret;
	}
}

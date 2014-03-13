package com.tencent.weibo.exceptions;

import com.tencent.weibo.constants.ErrorCodeConstants;

/**
 * 用于记录针对 OAuthClient 的异常信息
 */
public class OAuthClientException extends RuntimeException {

    private static final long serialVersionUID = -89490187565897565L;
    
    private String errcode;
    private String errmsg;

    /**
     * 根据错误码产生Exception
     * @param errcode
     */
    public OAuthClientException(String errcode) {
        super(ErrorCodeConstants.getErrmsg(errcode));
        this.errcode = errcode;
        this.errmsg = ErrorCodeConstants.getErrmsg(errcode);
    }
    /**
     * 不建议使用，只供临时设置错误项
     * @param errcode
     * @param errmsg
     */
    public OAuthClientException(String errcode, String errmsg) {
        super();
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}

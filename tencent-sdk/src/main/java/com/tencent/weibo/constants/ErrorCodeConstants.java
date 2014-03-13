package com.tencent.weibo.constants;

import java.util.HashMap;

/**
 * 本类针对 QweiboSDK， 设定了一系列的 errcode 以及对应的 errmsg
 */
public class ErrorCodeConstants{
    
    private static MyErrorCodeHashMap myErrorCodeHashMap =new MyErrorCodeHashMap();

	/**
	 * 成功返回
	 */
	public static final int ret_0 = 0;
	public static final int error_0 = 0;

	public static String getErrmsg(String errcode){
        return myErrorCodeHashMap.get(errcode);
    }
}

class MyErrorCodeHashMap extends HashMap<String,String>{
    private static final long serialVersionUID = 2427025312680000207L;
    public MyErrorCodeHashMap(){
        //TODO errcode尚未确定
        put("1", "connect out of time");
        
        
        //OAuthClient错误
        put("1001","qHttpClient not specified");
        
    }
}


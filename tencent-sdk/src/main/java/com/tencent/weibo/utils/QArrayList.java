package com.tencent.weibo.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

/**
 * 修改了add(NameValuePair)，根据该NameValuePair的value值是否为空，决定是否把该NameValuePair放进QArrayList
 */
public class QArrayList extends ArrayList<NameValuePair> {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean add(NameValuePair nameValuePair) {
        if(QStrOperate.hasValue(nameValuePair.getValue())){
            return super.add(nameValuePair);
        }else{
            return false;
        }
    }
    

}

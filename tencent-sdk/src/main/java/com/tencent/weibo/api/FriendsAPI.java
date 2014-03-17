package com.tencent.weibo.api;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;

import cc.pp.service.tencent.exception.TencentApiException;
import cc.pp.service.tencent.model.UserFansList;
import cc.pp.service.tencent.model.UserFansListData;
import cc.pp.service.tencent.model.UserIdolList;
import cc.pp.service.tencent.model.UserIdolListData;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.constants.ErrorCodeConstants;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * 关系链相关API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%85%B3%E7%B3%BB%E9%93%BE%E7%9B%B8%E5%85%B3">腾讯微博开放平台上关系链相关的API文档<a>
 */

public class FriendsAPI extends BasicAPI {

    private String friendsFansListUrl=apiBaseUrl+"/friends/fanslist";
    private String friendsFansListnameUrl=apiBaseUrl+"/friends/fanslist_name";
    private String friendsFansListSUrl=apiBaseUrl+"/friends/fanslist_s";
    private String friendsUserFansListUrl=apiBaseUrl+"/friends/user_fanslist";

    private String friendsIdolListUrl=apiBaseUrl+"/friends/idollist";
    private String friendsIdolListnameUrl=apiBaseUrl+"/friends/idollist_name";
    private String friendsIdolListSUrl=apiBaseUrl+"/friends/idollist_s";
    private String friendsUserIdolListUrl=apiBaseUrl+"/friends/user_idollist";

    private String friendsAddUrl=apiBaseUrl+"/friends/add";
    private String friendsDelUrl=apiBaseUrl+"/friends/del";
    private String friendsCheckUrl=apiBaseUrl+"/friends/check";
    private String friendsUserSpecialListUrl=apiBaseUrl+"/friends/user_speciallist";

    /**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     */
    public FriendsAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     * @param qHttpClient 使用已有的连接管理器
     */
    public FriendsAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 我的粉丝列表
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-30)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode  获取模式，默认为0
     *                           <li>mode=0，旧模式，新粉丝在前，只能拉取1000个
     *                           <li>mode=1，新模式，拉取全量粉丝，老粉丝在前
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String fanslist(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsFansListUrl,
				paramsList, oAuth);
	}

	/**
	 * 我的粉丝名列表
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-200)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode  获取模式，默认为0
     *                           <li>mode=0，旧模式，新粉丝在前，只能拉取1000个
     *                           <li>mode=1，新模式，拉取全量粉丝，老粉丝在前
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String fanslistname(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsFansListnameUrl,
				paramsList, oAuth);
	}

	/**
	 * 我的粉丝列表，简单信息（200个)
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-200)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode  获取模式，默认为0
     *                           <li>mode=0，旧模式，新粉丝在前，只能拉取1000个
     *                           <li>mode=1，新模式，拉取全量粉丝，老粉丝在前
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8%EF%BC%8C%E7%AE%80%E5%8D%95%E4%BF%A1%E6%81%AF%EF%BC%88200%E4%B8%AA%EF%BC%89">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String fanslistS(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsFansListSUrl,
				paramsList, oAuth);
	}

	/**
     * 获取其他用户的粉丝列表
     *
     * @param oAuth 标准参数
     * @param format 返回数据的格式 是（json或xml）
     * @param reqnum 请求个数(1-30)
     * @param startindex 起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
     * @param name 用户帐户名
     * @param fopenid  他人的openid（可选） <br>
     *                              name和fopenid至少选一个，若同时存在则以name值为主
     * @param mode  获取模式，默认为0
     *                           <li>mode=0，旧模式，新粉丝在前，只能拉取1000个
     *                           <li>mode=1，新模式，拉取全量粉丝，老粉丝在前
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
     * @return
     * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
     */
	public UserFansListData userFanslist(OAuth oAuth, String format, String reqnum, String startindex, String name,
			String fopenid, String mode, String install) {
        QArrayList paramsList = new QArrayList();
        paramsList.add(new BasicNameValuePair("format", format));
        paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("format", "UTF-8"), URLEncoder.encode(format, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("reqnum", "UTF-8"), URLEncoder.encode(reqnum, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("startindex", "UTF-8"), URLEncoder.encode(startindex, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("name", "UTF-8"), URLEncoder.encode(name, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("fopenid", "UTF-8"), URLEncoder.encode(fopenid, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("mode", "UTF-8"), URLEncoder.encode(mode, "UTF-8")));
//        paramsList.add(new BasicNameValuePair(URLEncoder.encode("install", "UTF-8"), URLEncoder.encode(install, "UTF-8")));
//        //URLEncoder.encode("install", "UTF-8")
		String resource = requestAPI.getResource(friendsUserFansListUrl, paramsList, oAuth);
		try {
			UserFansList result = mapper.readValue(resource, UserFansList.class);
			if (ErrorCodeConstants.ret_0 == result.ret || ErrorCodeConstants.error_0 == result.errcode) {
				return result.getData();
			} else {
				throw new TencentApiException(result);
			}
		} catch (IOException e) {
			throw new RuntimeException(resource, e);
		}
    }

	/**
	 * 我的关注列表
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-30)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode 默认0---1000个,1---10000
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String idollist(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsIdolListUrl,
				paramsList, oAuth);
	}

	/**
	 * 我的关注名列表
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-200)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode 默认0---1000个,1---10000
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String idollistname(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsIdolListnameUrl,
				paramsList, oAuth);
	}

	/**
	 * 我的关注列表，简单信息（200个)
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum  请求个数(1-200)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填：【reqnum*（page-1）】）
	 * @param mode 默认0---1000个,1---10000
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String idollistS(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsIdolListSUrl,
				paramsList, oAuth);
	}

	/**
	 * 其他人的关注列表
	 *
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param reqnum 请求个数(1-30)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 * @param name 用户帐户名
	 * @param fopenid  他人的openid（可选） <br>
     *                              name和fopenid至少选一个，若同时存在则以name值为主
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public UserIdolListData userIdollist(OAuth oAuth, String format, String reqnum, String startindex, String name,
			String fopenid, String install, String mode) {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("install", install));
        paramsList.add(new BasicNameValuePair("mode", mode));
		String resource = requestAPI.getResource(friendsUserIdolListUrl, paramsList, oAuth);
		try {
			UserIdolList result = mapper.readValue(resource, UserIdolList.class);
			if (ErrorCodeConstants.ret_0 == result.ret || ErrorCodeConstants.error_0 == result.errcode) {
				return result.getData();
			} else {
				throw new TencentApiException(result);
			}
		} catch (IOException e) {
			throw new RuntimeException(resource, e);
		}
	}

	public String userIdollist(OAuth oAuth, String format, String reqnum,
			String startindex,String name, String fopenid, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsUserIdolListUrl,
				paramsList, oAuth);
	}
	/**
	 * 收听某个用户<br>
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param name 他人的帐户名列表，用","隔开
	 * @param fopenids  你需要读取的用户openid列表，用下划线“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB（可选，最多30个） <br>
	 *                                name和fopenids至少选一个，若同时存在则以name值为主
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%94%B6%E5%90%AC%E6%9F%90%E4%B8%AA%E7%94%A8%E6%88%B7">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String add(OAuth oAuth, String format, String name,String fopenids ) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("name", name));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids ));

		return requestAPI.postContent(friendsAddUrl, paramsList,
		        oAuth);
	}

	/**
	 * 取消收听某个用户
	 *
	 * @param oAuth 标准参数
	 * @param format 回数据的格式 是（json或xml）
	 * @param name 他人的帐户名
	 * @param fopenid  他人的openid（可选） <br>
	 *                              name和fopenid至少选一个，若同时存在则以name值为主
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%8F%96%E6%B6%88%E6%94%B6%E5%90%AC%E6%9F%90%E4%B8%AA%E7%94%A8%E6%88%B7">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String del(OAuth oAuth, String format, String name,String fopenid) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));

		return requestAPI.postContent(friendsDelUrl, paramsList,
		        oAuth);
	}

	/**
	 * 检测是否我的听众或我收听的人
	 *
	 * @param oAuth 标准参数
	 * @param format 返回数据的格式 是（json或xml）
	 * @param names names  其他人的帐户名列表，用逗号“,”分隔，如aaa,bbb（最多30个，可选）
     * @param fopenids  其他人的的用户openid列表，用“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB（可选，最多30个） <br>
     *                               names和fopenids至少选一个，若同时存在则以names值为主
	 * @param flag 0 检测听众，1检测收听的人 2 两种关系都检测
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%A3%80%E6%B5%8B%E6%98%AF%E5%90%A6%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E6%88%96%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String check(OAuth oAuth, String format, String names,String fopenids,String flag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("names", names));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids));
        paramsList.add(new BasicNameValuePair("flag", flag));

		return requestAPI.getResource(friendsCheckUrl, paramsList,
		        oAuth);
	}

	/**
	 * 其他帐户特别收听的人列表
	 *
	 * @param oAuth
	 * @param format 返回数据的格式（json或xml）
	 * @param reqnum 请求个数(1-30)
	 * @param startindex 起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 * @param name 用户帐户名（可选）
     * @param fopenid  他人的openid（可选） <br>
     *                              name和fopenid至少选一个，若同时存在则以name值为主
     * @param install  过滤安装应用好友（可选） <br>
     *                           0-不考虑该参数，1-获取已安装应用好友，2-获取未安装应用好友
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E7%89%B9%E5%88%AB%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String userSpeciallist(OAuth oAuth, String format, String reqnum,
			String startindex,String name, String fopenid,String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("install", install));

		return requestAPI.getResource(friendsUserSpecialListUrl,
				paramsList, oAuth);
	}

	@Override
	public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;

        friendsFansListUrl=apiBaseUrl+"/friends/fanslist";
        friendsFansListnameUrl=apiBaseUrl+"/friends/fanslist_name";
        friendsFansListSUrl=apiBaseUrl+"/friends/fanslist_s";
        friendsUserFansListUrl=apiBaseUrl+"/friends/user_fanslist";

        friendsIdolListUrl=apiBaseUrl+"/friends/idollist";
        friendsUserIdolListUrl=apiBaseUrl+"/friends/user_idollist";
        friendsIdolListnameUrl=apiBaseUrl+"/friends/idollist_name";
        friendsIdolListSUrl=apiBaseUrl+"/friends/idollist_s";

        friendsAddUrl=apiBaseUrl+"/friends/add";
        friendsDelUrl=apiBaseUrl+"/friends/del";
        friendsCheckUrl=apiBaseUrl+"/friends/check";
        friendsUserSpecialListUrl=apiBaseUrl+"/friends/user_speciallist";
    }
}

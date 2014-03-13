package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * 时间线相关API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上时间线相关的API文档<a>
 */

public class StatusesAPI extends BasicAPI {

    private String statusesHomeTimelineUrl=apiBaseUrl+"/statuses/home_timeline";
    private String statusesUSERTimelineUrl=apiBaseUrl+"/statuses/user_timeline";
    private String statusesMentionsTimelineUrl=apiBaseUrl+"/statuses/mentions_timeline";
    private String statusesBroadcastTimelineUrl=apiBaseUrl+"/statuses/broadcast_timeline";
    private String statusesUserTimelineIdsUrl=apiBaseUrl+"/statuses/user_timeline_ids";
    private String statusesUsersTimelineUrl=apiBaseUrl+"/statuses/users_timeline";
    /**
     * 使用完毕后，请调用 shutdownConnection() 关闭自动生成的连接管理器
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     */
    public StatusesAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion 根据OAuthVersion，配置通用请求参数
     * @param qHttpClient 使用已有的连接管理器
     */
    public StatusesAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 主页时间线
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-70条）
	 * @param type 拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 <br>
     *                          如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型 
	 * @param contenttype 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E4%B8%BB%E9%A1%B5%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String homeTimeline(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum,String type, String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(statusesHomeTimelineUrl,
				paramsList, oAuth);
	}

	/**
	 * 其他用户发表时间线
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-70条）
	 * @param lastid 用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @param name  你需要读取的用户的用户名
	 * @param fopenid 你需要读取的用户的openid（可选） <br>
     *                             name和fopenid至少选一个，若同时存在则以name值为主 
     * @param type 拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 <br>
     *                          如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型 
     * @param contenttype 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%85%B6%E4%BB%96%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String userTimeline(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid, String name, String fopenid,
			String type, String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(statusesUSERTimelineUrl,
				paramsList, oAuth);
	}

    /**
	 * 用户提及时间线
	 * 
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-100条）
	 * @param lastid 和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @param type 拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 <br>
     *                        如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型 
     * @param contenttype 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E7%94%A8%E6%88%B7%E6%8F%90%E5%8F%8A%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */ 
	public String mentionsTimeline(OAuth oAuth, String format,
			String pageflag, String pagetime, String reqnum, String lastid,
			String type, String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesMentionsTimelineUrl,
				paramsList, oAuth);
	}

    /**
	 * 我发表时间线
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）  返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-200条）
	 * @param lastid 和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @param type 拉取类型, 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 如需拉取多个类型请|上(0x1|0x2) 得到3，type=3即可,填零表示拉取所有类型
	 * @param contenttype 内容过滤 填零表示所有类型 1-带文本 2-带链接 4图片 8-带视频 0x10-带音频
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E6%88%91%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String broadcastTimeline(OAuth oAuth, String format,
			String pageflag, String pagetime, String reqnum, String lastid,
			String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesBroadcastTimelineUrl,
				paramsList, oAuth);
	}
	
    /**
	 * 其他用户发表时间线索引
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-300条）
	 * @param lastid 和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @param name 你需要读取的用户的用户名（可选） 
	 * @param fopenid 你需要读取的用户的openid（可选） 
     *                             name和fopenid至少选一个，若同时存在则以name值为主 
	 * @param type 拉取类型, 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评  拉取类型, 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评 	如需拉取多个类型请|上(0x1|0x2) 得到3，type=3即可,填零表示拉取所有类型
	 * @param contenttype 内容过滤 填零表示所有类型 1-带文本 2-带链接 4图片 8-带视频 0x10-带音频
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%85%B6%E4%BB%96%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF%E7%B4%A2%E5%BC%95">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String userTimelineIds(OAuth oAuth, String format,
			String pageflag ,String pagetime,String reqnum,String lastid,
			String name,String fopenid,String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid ));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesUserTimelineIdsUrl,
				paramsList, oAuth);
	}	

	/**
	 * 多用户发表时间线
	 * @param oAuth
	 * @param format 返回数据的格式 是（json或xml）
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param reqnum 每次请求记录的条数（1-100条）
	 * @param lastid 第一页 时填0,继续向下翻页，填上一次请求返回的最后一条记录ID，翻页用
	 * @param names 你需要读取用户列表用“,”隔开，例如：abc,bcde,effg（可选，最多30个）
	 * @param fopenids  你需要读取的用户openid列表，用下划线“_”隔开，例如：B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB（可选，最多30个） <br>
     *                                names和fopenids至少选一个，若同时存在则以names值为主 
	 * @param type 拉取类型, 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	 * @param contenttype 内容过滤 填零表示所有类型 1-带文本 2-带链接 4图片 8-带视频 0x10-带音频
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%A4%9A%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String usersTimeline(OAuth oAuth, String format,String pageflag,
			String pagetime,String reqnum,String lastid,String names,
			String fopenids, String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("names", names));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesUsersTimelineUrl,
				paramsList, oAuth);
	}

	public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        statusesHomeTimelineUrl=apiBaseUrl+"/statuses/home_timeline";
        statusesUSERTimelineUrl=apiBaseUrl+"/statuses/user_timeline";
        statusesMentionsTimelineUrl=apiBaseUrl+"/statuses/mentions_timeline";
        statusesBroadcastTimelineUrl=apiBaseUrl+"/statuses/broadcast_timeline";
        statusesUserTimelineIdsUrl=apiBaseUrl+"/statuses/user_timeline_ids";
        statusesUsersTimelineUrl=apiBaseUrl+"/statuses/users_timeline";
    }
}

import java.io.IOException;

import cc.pp.service.tencent.model.Video;
import cc.pp.tencent.utils.json.JsonUtils;


public class Demo {

	public static void main(String[] args) throws IOException {

		String str = "{\"picurl\":\"http://220.img.pp.sohu.com.cn/p220/2013/2/27/11/14/6_13de05605d0g102_52846314_7_1b.jpg\",\"player\":\"http://share.vrs.sohu.com/my/v.swf&id=52846314&topBar=1&autoplay=true\",\"realurl\":\"http://my.tv.sohu.com/pl/5173893/52846314.shtml\",\"shorturl\":\"http://url.cn/CT8Vcj\",\"title\":\"夜店小姐培训师上课视频内部流出 彻底摆脱屌丝男，吸引高端客户！\"}";
		Video music = JsonUtils.getObjectMapper().readValue(str, Video.class);
		System.out.println(JsonUtils.toJson(music));

	}

}

package cc.pp.tencent.bozhus.t2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SWeiboUtilsTest {

	@Test
	public void testGetWid() {
		String url1 = "http://t.qq.com/p/t/197562129592354";
		String url2 = "http://t.qq.com/p/t/197562129592354?a=ddd";
		assertEquals("197562129592354", SWeiboUtils.getWid(url1));
		assertEquals("197562129592354", SWeiboUtils.getWid(url2));
	}

}

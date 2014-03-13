package cc.pp.service.tencent.model;

public class Result<T> {

	public int errcode; // 返回错误码,
	public String msg; // 错误信息,
	public int ret; // 返回值，0-成功，非0-失败,
	public T data;
	public DetailErrInfo detailerrinfo;
	public long seqid; // 序列号

	public static class DetailErrInfo {
		public String accesstoken; // "xxx",
		public String apiname; // "weibo.t.show",
		public String appkey; // "11b5a3c188484c3f8654b83d32e19bab",
		public String clientip; // "122.224.126.3",
		public int cmd; // 0,
		public int proctime; // 0,
		public int ret1; // 3,
		public int ret2; // 3,
		public int ret3; // 7,
		public long ret4; // 3659629834,
		public long timestamp; // 1392778030
	}

	@Override
	public String toString() {
		return "Result{" +
				"errcode=" + errcode +
				", msg='" + msg + '\'' +
				", ret=" + ret +
				", data=" + data +
				", detailerrinfo=" + detailerrinfo +
				", seqid=" + seqid +
				'}';
	}
}

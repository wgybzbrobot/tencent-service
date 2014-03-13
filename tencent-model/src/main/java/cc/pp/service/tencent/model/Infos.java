package cc.pp.service.tencent.model;

public class Infos {

	private Data data;
	private int errcode;
	private String msg;
	private int ret;
	private String seqid;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}

}

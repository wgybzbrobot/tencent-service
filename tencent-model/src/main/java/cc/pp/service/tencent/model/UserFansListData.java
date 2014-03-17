package cc.pp.service.tencent.model;

import java.util.List;

public class UserFansListData {

	private int curnum;
	private int hasnext;
	private int nextstartpos;
	private long timestamp;
	private List<FansInfo> info;

	public int getCurnum() {
		return curnum;
	}

	public void setCurnum(int curnum) {
		this.curnum = curnum;
	}

	public int getHasnext() {
		return hasnext;
	}

	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}

	public int getNextstartpos() {
		return nextstartpos;
	}

	public void setNextstartpos(int nextstartpos) {
		this.nextstartpos = nextstartpos;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<FansInfo> getInfo() {
		return info;
	}

	public void setInfo(List<FansInfo> info) {
		this.info = info;
	}

}

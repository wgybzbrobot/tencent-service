package cc.pp.service.tencent.model;

import java.util.List;

public class UserIdolListData {

	private int curnum;
	private int hasnext;
	private List<UserIdolListInfo> info;
	private int nextstartpos;
	private Long timestamp;

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

	public List<UserIdolListInfo> getInfo() {
		return info;
	}

	public void setInfo(List<UserIdolListInfo> info) {
		this.info = info;
	}

	public int getNextstartpos() {
		return nextstartpos;
	}

	public void setNextstartpos(int nextstartpos) {
		this.nextstartpos = nextstartpos;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}

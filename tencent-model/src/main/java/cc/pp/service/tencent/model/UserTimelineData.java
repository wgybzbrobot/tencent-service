package cc.pp.service.tencent.model;

import java.util.HashMap;
import java.util.List;

public class UserTimelineData {

	private int hasnext;
	private List<UserTimelineInfo> info;
	private long timestamp;
	private int totalnum;
	private HashMap<String, String> user;

	public int getHasnext() {
		return hasnext;
	}

	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}

	public List<UserTimelineInfo> getInfo() {
		return info;
	}

	public void setInfo(List<UserTimelineInfo> info) {
		this.info = info;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}

	public HashMap<String, String> getUser() {
		return user;
	}

	public void setUser(HashMap<String, String> user) {
		this.user = user;
	}

}

package cc.pp.service.tencent.model;

import java.util.List;

public class InfosData {

	private long timestamp;
	private List<SimpleUser> info;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<SimpleUser> getInfo() {
		return info;
	}

	public void setInfo(List<SimpleUser> info) {
		this.info = info;
	}

}

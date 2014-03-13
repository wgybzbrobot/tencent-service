package cc.pp.service.tencent.model;

public class UserTimeline extends Result<Object> {

	private UserTimelineData data;

	public UserTimelineData getData() {
		return data;
	}

	public void setData(UserTimelineData data) {
		this.data = data;
	}

}

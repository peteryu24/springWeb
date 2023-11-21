package gmx.fwd.vo.logvo;

import java.util.Date;

public class LogVO {
	private int logId;
	private String username;
	private int category;
	private String activity;
	private Date createTime;
	
	public int getLogId() {
		return logId;
	}
	
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getactivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}

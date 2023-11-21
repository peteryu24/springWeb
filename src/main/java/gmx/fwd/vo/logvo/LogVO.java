package gmx.fwd.vo.logvo;

import java.util.Date;

public class LogVO {
	private int logId;
	private String email;
	private int category;
	private String activity;
	private Date createTime;
	
	public int getLogId() {
		return logId;
	}
	
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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

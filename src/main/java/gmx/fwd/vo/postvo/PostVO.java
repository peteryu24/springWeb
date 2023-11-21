package gmx.fwd.vo.postvo;

import java.util.Date;

public class PostVO {
	private int postId;
	private String username;
	private String title;
	private String content;
	private int view;
	private Date createTime;
	private boolean hasFile;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public boolean getHasFile() {
		return hasFile;
	}
	
	public void setHasFile(boolean hasFile) {
		this.hasFile=hasFile;
	}
}
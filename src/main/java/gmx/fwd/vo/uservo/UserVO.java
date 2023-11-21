package gmx.fwd.vo.uservo;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class UserVO {
	
	
	@NotBlank(message="닉네임은 공백과 null을 허용하지 않습니다.")
	private String nickname;
	
	private String username;
	
	@NotBlank(message="비밀번호는 공백과 null을 허용하지 않습니다.")
	private String password;
	
	private String createTime;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
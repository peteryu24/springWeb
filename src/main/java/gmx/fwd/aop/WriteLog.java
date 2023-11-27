package gmx.fwd.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import gmx.fwd.mapper.log.LogMapper;
import gmx.fwd.vo.logvo.LogVO;

@Aspect
@Component
public class WriteLog {

	@Autowired
	LogMapper logMapper;

	private static final Logger logger = LoggerFactory.getLogger(WriteLog.class);

	@Pointcut("execution(* gmx.fwd.jwt.MyAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
	private void onAuthenticationSuccess() {
	}

	/*
	 * 로깅을 설정할 클래스의 메소드 정의	 
	* PostService의 writePost
	*/
	@Pointcut("execution(* gmx.fwd.service.post.PostService.writePost(..))")
	private void forWritePost() {
	}

	@AfterReturning("onAuthenticationSuccess()")
	public void afterOnAuthenticationSuccess(JoinPoint joinPoint) {
		final Object[] loginInfoArr = joinPoint.getArgs();
		int loginInfoLength = loginInfoArr.length;

		Authentication authentication = null;

		// Authentication 타입 위치 찾기
		for (int i = 0; i < loginInfoLength; i++) {
			if (loginInfoArr[i] instanceof Authentication) {
				authentication = (Authentication) loginInfoArr[i];
				break;
			}
		}

		if (authentication != null) {
			String username = authentication.getName();
			logger.info("Login 시도 at: " + joinPoint.getSignature().getName());
			logger.info("Login successful for user: " + username);

			LogVO loginLog = new LogVO();
			loginLog.setCategory(1);
			loginLog.setUsername(username);
			loginLog.setActivity(username + " logged in");

			if (!(logMapper.writeLog(loginLog) > 0)) {
				logger.info(username + "의 로그인 로그 DB업로드 실패");
				return;
			}

			if ((logMapper.getLogCount() > 50)) {
				logMapper.deleteOldestLog();
			}

			logger.info(username + "의 로그인 로그 DB업로드 성공");
		}
	}

	// writePost 메소드 실행 전
	@Before("forWritePost()")
	public void beforeWritePost(JoinPoint joinPoint) {

		logger.info("writePost 시도: " + joinPoint.getSignature().getName());
	}

	// writePost의 transaction에서 에러가 발생할 경우
	@AfterThrowing(pointcut = "forWritePost()", throwing = "e")
	public void errorInWritePost(JoinPoint joinPoint, Exception e) {
		final Object[] writePostInfoArr = joinPoint.getArgs();
		final List<Object> writePostInfoList = Collections.unmodifiableList(Arrays.asList(writePostInfoArr));
		LogVO writePostLog = new LogVO();

		logger.error("writePost에서 예외 발생. Error: " + e.getMessage());

		writePostLog.setCategory(2);
		writePostLog.setUsername((String) writePostInfoList.get(0));
		writePostLog.setActivity("Failed to write Post \"" + (String) writePostInfoList.get(1) + "\" Error: " + e.getMessage());

		if (!(logMapper.writeLog(writePostLog) > 0)) {
			logger.info(writePostInfoList.get(0) + "의 게시글 작성 실패 로그 DB업로드 실패");
			return;
		}

		if ((logMapper.getLogCount() > 50)) {
			logMapper.deleteOldestLog();
		}

		logger.info(writePostInfoList.get(0) + "의 게시글 작성 실패 로그 DB업로드 성공");
	}

	// writePost 메소드 실행 후 에러가 발생하지 않는다면
	@AfterReturning("forWritePost()")
	public void noErrorInWritePost(JoinPoint joinPoint) {
		final Object[] writePostInfoArr = joinPoint.getArgs();
		final List<Object> writePostInfoList = Collections.unmodifiableList(Arrays.asList(writePostInfoArr));
		LogVO writePostLog = new LogVO();

		logger.info("writePost 성공: " + joinPoint.getSignature().getName());
		logger.info("아이디: " + writePostInfoList.get(0));
		logger.info("제목: " + writePostInfoList.get(1));

		writePostLog.setCategory(2);
		writePostLog.setUsername((String) writePostInfoList.get(0));
		writePostLog.setActivity("Post \"" + (String) writePostInfoList.get(1) + "\" Written");

		if (!(logMapper.writeLog(writePostLog) > 0)) {
			logger.info(writePostInfoList.get(0) + "의 게시글 작성 로그 DB업로드 실패");
			return;
		}

		if ((logMapper.getLogCount() > 50)) {
			logMapper.deleteOldestLog();
		}

		logger.info(writePostInfoList.get(0) + "의 게시글 작성 로그 DB업로드 성공");
	}

}

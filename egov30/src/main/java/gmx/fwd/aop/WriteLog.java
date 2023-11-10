package gmx.fwd.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
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
import org.springframework.stereotype.Component;

import gmx.fwd.mapper.log.LogMapper;
import gmx.fwd.vo.logvo.LogVO;

@Aspect
@Component
public class WriteLog {

	@Autowired
	LogMapper logMapper;

	private static final Logger logger = LoggerFactory.getLogger(WriteLog.class);

	/*
	 * 로깅을 설정할 클래스의 메소드 정의
	 * UserService의 login
	 */
	@Pointcut("execution(* gmx.fwd.service.user.UserService.login(..))")
	private void forLogin() {
	}

	// PostService의 writePost
	@Pointcut("execution(* gmx.fwd.service.post.PostService.writePost(..))")
	private void forWritePost() {
	}

	// login 메소드 실행 전
	@Before("forLogin()")
	public void beforeLogin(JoinPoint joinPoint) {
		/*
		 * joinPoint.getSignature().getName()
		 * 
		 * joinPoint
		 * AOP에서 사용하는 인터페이스
		 * 메소드 호출, 필드 접근 등의 특정 지점에 관한 정보를 담고있음
		 * 
		 * getSignature()
		 * 메소드 호출에 대한 시그니처(메소드의 이름, 반환 유형, 파라미터 유형 등을 포함한 정보)
		 * 
		 * getName()
		 * 현재 작업 중인 메소드의 이름을 문자열로
		 */
		logger.info("Login 시도: " + joinPoint.getSignature().getName());
	}

	// login 메소드 실행 후
	@After("forLogin()")
	public void afterLogin(JoinPoint joinPoint) {
		final Object[] loginInfoArr = joinPoint.getArgs(); // 메소드에 전달된 모든 인자들을 얻음
		final List<Object> loginInfoList = Collections.unmodifiableList(Arrays.asList(loginInfoArr));
		LogVO loginLog = new LogVO();

		logger.info("Login 성공: " + joinPoint.getSignature().getName());
		logger.info("아이디: " + loginInfoList.get(0));

		loginLog.setCategory("login");
		loginLog.setEmail((String) loginInfoList.get(0));
		loginLog.setActivity((String) loginInfoList.get(0) + " logged in");

		if (!(logMapper.writeLog(loginLog) > 0)) {
			logger.info(loginInfoList.get(0) + "의 로그인 로그 DB업로드 실패");
			return;
		}

		if ((logMapper.getLogCount() > 50)) {
			logMapper.deleteOldestLog();
		}

		logger.info(loginInfoList.get(0) + "의 로그인 로그 DB업로드 성공");
	}

	// writePost 메소드 실행 전
	@Before("forWritePost()")
	public void beforeWritePost(JoinPoint joinPoint) {

		logger.info("writePost 시도: " + joinPoint.getSignature().getName());
	}

	// writePost 메소드가 실행 후 에러가 발생하지 않는다면
	@AfterReturning("forWritePost()")
	public void noErrorInWritePost(JoinPoint joinPoint) {
		final Object[] writePostInfoArr = joinPoint.getArgs();
		final List<Object> writePostInfoList = Collections.unmodifiableList(Arrays.asList(writePostInfoArr));
		LogVO writePostLog = new LogVO();

		logger.info("writePost 성공: " + joinPoint.getSignature().getName());
		logger.info("아이디: " + writePostInfoList.get(0));
		logger.info("제목: " + writePostInfoList.get(1));

		writePostLog.setCategory("Post");
		writePostLog.setEmail((String) writePostInfoList.get(0));
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

	// writePost에서 에러가 발생할 경우
	@AfterThrowing(pointcut = "forWritePost()", throwing = "e")
	public void errorInWritePost(JoinPoint joinPoint, Exception e) {
		final Object[] writePostInfoArr = joinPoint.getArgs();
		final List<Object> writePostInfoList = Collections.unmodifiableList(Arrays.asList(writePostInfoArr));
		LogVO writePostLog = new LogVO();

		logger.error("writePost에서 예외 발생. Error: " + e.getMessage());

		writePostLog.setCategory("Post");
		writePostLog.setEmail((String) writePostInfoList.get(0));
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

}

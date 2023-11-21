package gmx.fwd.service.post;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.property.EgovPropertyService;
import gmx.fwd.mapper.post.PostMapper;
import gmx.fwd.service.file.FileService;
import gmx.fwd.vo.filevo.FileVO;
import gmx.fwd.vo.postvo.PostVO;

@Service
public class PostService {

	@Autowired
	PostMapper postMapper;
	@Autowired
	FileService fileService;
	@Autowired
	private EgovPropertyService propertiesService;

	/*
	 * 글 작성 서비스
	 * 
	 * @PostMapper로 쿼리문 실행
	 * 
	 * 성공여부에 따라 boolean 타입 반환
	 * 
	 */
	@Resource(name = "transactionManager")
	// DB의 트랜잭션을 시작하고, 커밋, 롤백에 사용되는 인터페이스
	private PlatformTransactionManager transactionManager; // 스프링 컨테이너로부터 주입 받음

	public void writePost(String email, String title, String content, MultipartFile mpartFile) {
		/*
		 * 트랜잭션의 정의 설정
		 * 전파 방식, 격리 수준, 타임 아웃, 읽기 전용 여부 등 설정 가능
		 * 
		 * @Transactional로 선언적 관리 방식에서는 프레임워크가 자동으로 처리하지만,
		 * 프로그래밍 방식의 트랜잭션 관리 방식에서는 개발자가 직접 제어
		 * 항상 새로운 트랜잭션을 시작하며 독립된 트랜잭션을 실행함
		 */
		DefaultTransactionDefinition transactionSetting = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		/*
		 * 트랜잭션의 상태를 나타내는 인터페이스
		 * 현재 진행 중, 새로 시작, 완료인지 등등 알 수 있음
		 * 롤백하거나 커밋할때도 이용
		 * 
		 * getTransaction(transactionSetting);에서 transactionSetting의 설정을 바탕으로 트랜잭션 시작
		 */
		TransactionStatus transactionFlag = transactionManager.getTransaction(transactionSetting); // 트랜잭션 시작되면 TransactionStatus 객체 반환

		try {
			PostVO post = new PostVO();

			post.setEmail(email);
			post.setTitle(title);
			post.setContent(content);


			if ((postMapper.writePost(post)) <= 0) {
				throw new RuntimeException("게시글 저장 실패"); // rollback
			}
			
			if (!mpartFile.isEmpty()) {

				String fileName = mpartFile.getOriginalFilename();
				// 저장 디렉토리, 파일 이름 설정
				File file = new File(propertiesService.getString("fileDownloadUrl") + fileName + "(" + title + ")");

				// DB에 파일 저장 로직
				FileVO fileVO = new FileVO();
				fileVO.setPostId(getPostIdbyTitle(title));
				fileVO.setFileName(fileName);
				fileVO.setFilePath(file.getAbsolutePath());

				if (!(fileService.saveFile(fileVO))) {
					throw new RuntimeException("파일 DB에 저장 실패"); // rollBack
				}

				// 경로에 파일 저장 / IOException 또는 IllegalStateException이 발생할 수 있는 가능
				try {
					mpartFile.transferTo(file);
				} catch (Exception e) {
					throw new RuntimeException("파일 경로 저장 실패");
				}
			}
			// commit
			transactionManager.commit(transactionFlag);
		} catch (Exception e) {
			
			// rollback
			transactionManager.rollback(transactionFlag);
			// WriteLog클래스에서 에러를 사용할 수 있도록
			throw e;
		}
	}
	/*@Transactional  // Exception에 대하여 rollBack
	public void writePost(String email, String title, String content, MultipartFile mpartFile) {
	
	    PostVO post = new PostVO();  
	
	    post.setEmail(email);  
	    post.setTitle(title);  
	    post.setContent(content);  
	
	    
	    int cnt = postMapper.writePost(post);
	    
	    if(cnt <= 0) {  
	        throw new RuntimeException("게시글 저장 실패");  // rollBack
	    }
	    
	    throw new RuntimeException("게시글 저장 실패");
	}*/

	/*
	 * 전체 게시글을 @PostMapper를 통해 쿼리문으로 가져와서
	 * 조건에 맞는 쿼리를 실행 후 다시 @Controller한테 list로 뿌려줌
	 */
	public List<PostVO> selectPostInOrder(int currentPage, String sortType, String searchedKeyword) {
		HashMap<String, Object> map = new HashMap<>();

		if (sortType.equals("조회순")) {
			map.put("orderBy", "view");
			map.put("order", "desc");
			// @Controller에서 받은 default 값: 최신순
		} else {
			map.put("orderBy", "postId");
			map.put("order", "desc");
		}

		map.put("searchedKeyword", searchedKeyword);

		map.put("start", ((currentPage - 1) * 5));

		return postMapper.selectPostInOrder(map);

	}

	public int getSearchedPostCount(String keyword) {

		int totalPageNeeded = (postMapper.searchPostCount(keyword) / 5);

		if (postMapper.getPostCount() % 5 != 0) {
			totalPageNeeded += 1;
		}

		return totalPageNeeded;

	}

	public String getWriterByPostId(int postId) {
		return postMapper.getWriterByPostId(postId);
	}

	/*
	 * 게시글 상세페이지로 이동할 때
	 * postId 이용해서 게시글 가져올 때
	 */
	public PostVO detailViewbyPostId(int postId) {
		return postMapper.detailViewbyPostId(postId);
	}

	/*
	 * 게시글 수정할 때
	 * postId를 이용해서 게시글 가져올 때 사용
	 */
	public PostVO getPostByPostId(int postId) {
		return postMapper.getPostByPostId(postId);
	}

	/*
	 * 넘겨 받은
	 * postId로 게시글 가져오고,
	 * newContent로 기존 content 업데이트
	 */
	public boolean changeContent(int postId, String newContent) {
		PostVO post = getPostByPostId(postId);

		if (post != null) {
			post.setContent(newContent);

			return postMapper.changeContent(post) > 0;
		}

		return false;

	}

	/*
	 * 조작해야하는 글의 postId를 받아서
	 * view를 증가시킴
	 * @PostMapper로 쿼리문 실행
	 */
	public void setView(int postId) {
		PostVO post = getPostByPostId(postId);

		if (post != null) {
			post.setView((post.getView() + 1));
			postMapper.setView(post);
		}

	}

	public int getPostCount() {

		int totalPageNeeded = (postMapper.getPostCount() / 5);

		if ((postMapper.getPostCount() % 5 != 0)) {
			totalPageNeeded += 1;
		}

		return totalPageNeeded;

	}

	/*
	 * @PostContoller에서
	 * 
	 * 게시글 업로드하고 해당 postId 이용해서 
	 * 파일 업로드 할 때 필요
	 */
	public int getPostIdbyTitle(String title) {
		return postMapper.getPostIdByTitle(title);
	}

	/*
	 * 전달 받은 postId를
	 * 이용하여 쿼리문 실행
	 * 
	 * 성공 여부에 따라  boolean 타입 반환
	 */
	public boolean deletePost(int postId) {

		return (postMapper.deletePost(postId)) > 0;
	}
}

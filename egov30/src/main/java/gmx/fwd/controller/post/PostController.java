package gmx.fwd.controller.post;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gmx.fwd.service.comment.CommentService;
import gmx.fwd.service.file.FileService;
import gmx.fwd.service.post.PostService;
import gmx.fwd.vo.commentvo.CommentVO;
import gmx.fwd.vo.filevo.FileVO;
import gmx.fwd.vo.postvo.PostVO;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FileService fileService;

	/*
	 * 총 필요한 페이지 갯수 계산 컨트롤러
	 * 
	 * 한 페이지에 5개씩 보여준다고 가정
	 * 5로 나눠지는 숫자만큼 게시글이 있다면 (POST / 5)
	 * 나머지가 존재하면 추가의 페이지 필요
	 */

	/*
	 * 메소드에서 반환하는 값을
	 * View Resolver를 거치지 않고, HTTP Response Body에 직접 작성해서 client에 직접 전송
	 * HttpMessageConverter를 사용하여 리턴된 객체를 특정 형식(JSON, XML등)으로 변환하여 작성
	 * dispatcher-servlet.xml 참조
	 * 
	 * View Name을 반환하는 대신에 실제 데이터나 객체를 반환해야 함
	 * produces = "application/json"을 @RequestMapping에 추가할 수도 있음(json 전송시)
	 * 
	 * 클라이언트에게 직접 데이터를 전송해야할 때 유용하게 사용
	 */
	@ResponseBody
	@GetMapping(value = "/totalPageNum.do", produces = "application/json")
	public HashMap<String, Integer> totalPageNum(@RequestParam(name="searchedKeyword", defaultValue="")String searchedKeyword) {
		HashMap<String, Integer> response = new HashMap<>();
		response.put("totalPage", postService.getSearchedPostCount(searchedKeyword));
		return response;
	}

	/*
	 * 각 페이지에 게시글 5개씩 보여주는 컨트롤러
	 * 
	 * 사용자가 희망하는 방식대로 정렬된 orderedPost
	 * 사용자가 선택한 페이지 번호 받음(기본값: 1)
	 */
	@ResponseBody
	@GetMapping(value = "/postPerPage.do", produces = "application/json")
	public List<PostVO> postPerPage(@RequestParam(name = "sortType", defaultValue = "최신순", required = false) String sortType,
			@RequestParam(name = "currentPage", defaultValue = "1", required = false) int currentPage,
			@RequestParam(name = "searchedKeyword", defaultValue = "") String searchedKeyword) {

		/*
		한 페이지당 5개씩 게시물
		
		1페이지: 1~ 5         1 페이지:  1  ~   5 * 1
		2페이지: 6~ 10        2 페이지: 5(2-1)+1  ~ 5 * 2
		3페이지: 11~ 15       z페이지:  5(z-1)+1  ~  5z
		*/

		return postService.selectPostInOrder(currentPage, sortType, searchedKeyword);
	}

	// 게시글을 전체 보여주는 페이지로 단순 리다이렉트
	@RequestMapping(value = "/goShowAllPosts.do", method = { RequestMethod.GET })
	public String goToShowAllPages() {
		return "post/showAllPosts";
	}

	// 게시글 작성 페이지로 단순 리다이렉트
	@GetMapping("/showWritePost.do")
	public String showWritePost() {
		return "post/writePost";
	}

	/*
	 * 게시글과 파일 업로드하는 컨트롤러
	 * 
	 * 일단 title과 content를 넘겨받음
	 * multipart를 사용해서 파일 또한 받음
	 * 
	 * session을 통해 email을 얻어서
	 * @PostService를 통해 일단 게시글 업로드
	 * 
	 * 그로 인해 생성된 postId를 왜래키로 사용해서
	 * 다운로드 디렉토리에 파일 이름과 게시글 제목을 합쳐서 저장
	 * (중복 방지)
	 */
	// TODO 글을 작성하면 초기화면으로
	@PostMapping(value = "/writePost.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String writePost(@RequestParam String title, @RequestParam String content, @RequestParam("uploadFile") MultipartFile mpartFile,
			HttpSession session, Model model) {

		String email = (String) session.getAttribute("sessionEmail");

		try {
			postService.writePost(email, title, content, mpartFile);
			return "redirect:goShowAllPosts.do";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "post/writePost";
		}
	}

	/*
	 * 한 개의 게시글에 대한 자세한 페이지
	 * 
	 * title을 버튼화 시켜 클릭시 해당 postId를 넘겨받음
	 * 
	 * 해당 postId에 일치하는 title과 content를 표시
	 * postId를 왜래키로 가지고 있는 댓글들 전부 표시
	 * postId를 왜래키로 가지고 있는 파일들 표시
	 * 
	 * title을 클릭하여 detailPost.do로 접근시,
	 * @PostService로 해당 글의 postId를 넘겨줌
	 * 
	 * 전체 게시판 목록에서 진입했을때만 view를 증가시켜서
	 * 댓글 쓰기나 다른 경로로 접속했을 때,
	 * view 증가 X
	 */
	@GetMapping("/detailPost.do")
	public String detailViewbyTitle(@RequestParam("postId") int postId,
			@RequestParam(name = "viewSet", defaultValue = "no", required = false) String viewSet, Model model) {

		if (viewSet.equals("yes")) {
			postService.setView(postId);
			return "redirect:detailPost.do?postId=" + postId; // 새로고침시 view 증가 방지
		}

		PostVO post = postService.detailViewbyPostId(postId);
		model.addAttribute("post", post);

		List<CommentVO> comments = commentService.getCommentsByPostId(postId);
		model.addAttribute("comments", comments);

		FileVO fileVO = fileService.getFileByPostId(postId);
		model.addAttribute("file", fileVO);

		return "post/detailPost";
	}

	/*
	 * 게시글 내용 수정 페이지로 리다이렉트
	 * 
	 * postId를 넘겨줌
	 */
	@ResponseBody
	@GetMapping(value = "/checkAvailabilityToChangePost.do")
	public HashMap<String, Object> checkAvailabilityToChangePost(@RequestParam(name = "postId") int postId, HttpSession session) {
		String sessionEmail = (String) session.getAttribute("sessionEmail");
		HashMap<String, Object> response = new HashMap<>();

		if (sessionEmail == null || !((postService.getWriterByPostId(postId)).equals(sessionEmail))) { // 세션이 없거나, 작성자가 아닐 경우
			response.put("status", "fail");
			return response;
		}

		response.put("status", "success");
		response.put("redirect", "post/goChangePost.do?postId=" + postId);
		return response;
	}

	@GetMapping("/goChangePost.do")
	public String goChangePost(@RequestParam int postId, Model model) {
		model.addAttribute("postId", postId); // 컨트롤러와 뷰 사이 데이터를 전달하는 Model
		return "post/changePost";
	}

	/*
	 * 실제 게시글 내용 수정 로직 컨트롤러
	 * 
	 * ajax 비동기 통신으로
	 * postId를 넘겨받고, 변경된 내용 넘겨 받음
	 * 
	 * 해당 postId에 일치하는 게시글이 있으면
	 * 내용을 업데이트함
	 * 
	 * 다시 jsp로 성공여부 문자열 반환
	 * 
	 * consumes: ajax에서 data
	 * produces: ajax에서 dataType
	 */
	@ResponseBody
	@PostMapping(value = "/changePost.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, String> changePost(int postId, String newContent) {

		HashMap<String, String> response = new HashMap<>();

		if (!postService.changeContent(postId, newContent)) { // 게시글 작성 중 에러
			response.put("status", "error");
		}

		response.put("status", "success");

		return response;
	}

	/*
	 * 게시글 삭제 로직
	 * 
	 * postId를 넘겨받아서
	 * @PostService로 위임
	 */
	@ResponseBody
	@GetMapping("/deletePost.do")
	public HashMap<String, Object> deletePost(@RequestParam(name = "postId") int postId, HttpSession session) {

		String sessionEmail = (String) session.getAttribute("sessionEmail");
		HashMap<String, Object> response = new HashMap<>();

		if (sessionEmail == null || !((postService.getWriterByPostId(postId)).equals(sessionEmail))) { // 세션이 없거나, 작성자가 아닐 경우
			response.put("status", "fail");
			return response;
		}

		postService.deletePost(postId);
		response.put("status", "success");
		response.put("redirect", "post/goShowAllPosts.do");

		return response;
	}

}

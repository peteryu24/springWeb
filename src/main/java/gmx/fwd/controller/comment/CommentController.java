package gmx.fwd.controller.comment;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gmx.fwd.service.comment.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;

	/*
	 * 왜래키인 postId 들고
	 * 댓글 작성 페이지로 리다이렉트
	 */
	@RequestMapping(value = "/goWriteComment.do", method = RequestMethod.GET)
	public String goWriteComment(@RequestParam(name = "postId") int postId, Model model) {
		model.addAttribute("postId", postId);
		return "comment/writeComment";
	}

	/*
	 * 실제 댓글 작성 로직 컨트롤러
	 * 
	 * ajax 비동기 통신으로
	 * 왜래키 postId와 comment 넘겨 받음
	 * 
	 * 또 다른 왜래키 email은 세션으로 처리
	 */
	@ResponseBody
	@PostMapping("/writeComment.do")
	public HashMap<String, String> writeComment(@RequestParam(name = "postId") int postId, @RequestParam(name = "comment") String comment) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();

		HashMap<String, String> response = new HashMap<>();

		boolean result = commentService.writeComment(comment, postId, currentUsername);

		if (result) {
			response.put("status", "success");
		} else {
			response.put("status", "error");
		}
		return response;
	}

	@ResponseBody
	@GetMapping(value = "/checkAvailabilityToChangeComment.do")
	public HashMap<String, Object> checkAvailabilityToChangeComment(@RequestParam(name = "commentId") int commentId,
			@RequestParam int postId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		
		HashMap<String, Object> response = new HashMap<>();

		if (currentUsername == null || !((commentService.getWriterByCommentId(commentId)).equals(currentUsername))) { // 세션이 없거나, 작성자가 아닐 경우
			response.put("status", "fail");
			return response;
		}

		response.put("status", "success");
		response.put("redirect", "comment/goChangeComment.do?commentId=" + commentId + "&postId=" + postId);
		return response;
	}

	/*
	 * 댓글 변경 페이지로 리다이렉트 하는 컨트롤러
	 * 
	 * commentId와 postId 넘겨줌
	 * 
	 * postId는 뒤로가기랑 댓글 작성 후
	 * detailPost.do에 postId 넣기 위해서
	 */
	@GetMapping("/goChangeComment.do")
	public String goChangeComment(@RequestParam(name = "commentId") int commentId, @RequestParam(name = "postId") int postId, Model model) {

		model.addAttribute("commentId", commentId);
		model.addAttribute("postId", postId);
		return "comment/changeComment";
	}

	/*
	 * 실제 댓글 작성 로직
	 * 
	 * ajax 비동기 통신으로
	 * commentId, newComment, postId 넘겨 받고
	 * 
	 * boolean 타입으로 성공 여부반환
	 */
	@ResponseBody
	@PostMapping("/changeComment.do")
	public HashMap<String, String> changeComment(@RequestParam(name = "commentId") int commentId,
			@RequestParam(name = "newComment") String newComment, @RequestParam(name = "postId") int postId) {
		HashMap<String, String> response = new HashMap<>();

		boolean commentFlag = commentService.changeComment(commentId, newComment);

		if (commentFlag) {
			response.put("status", "success");
		} else {
			response.put("status", "error");
		}
		return response;
	}

	/*
	 * 댓글 삭제 로직
	 * 
	 * 삭제 후 해당 postId 이용하여
	 * 다시 해당 게시물의 detailPost로 이동(@Controller를 통해)
	 */
	@ResponseBody
	@GetMapping("/deleteComment.do")
	public HashMap<String, Object> deleteComment(@RequestParam(name = "commentId") int commentId, @RequestParam(name = "postId") int postId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		
		HashMap<String, Object> response = new HashMap<>();
		
		if (currentUsername == null || !((commentService.getWriterByCommentId(commentId)).equals(currentUsername))) { // 세션이 없거나, 작성자가 아닐 경우
			response.put("status", "fail");
			return response;
		}
		
		commentService.deleteComment(commentId);
		response.put("status", "success");
		response.put("redirect", "post/detailPost.do?postId=" + postId);

		return response;
	}
}

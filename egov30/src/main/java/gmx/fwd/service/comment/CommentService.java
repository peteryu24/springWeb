package gmx.fwd.service.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.comment.CommentMapper;
import gmx.fwd.vo.commentvo.CommentVO;

@Service
public class CommentService {

	@Autowired
	CommentMapper commentMapper;

	public boolean writeComment(String comment, int postId, String email) {
		CommentVO commentvo = new CommentVO();
		commentvo.setComment(comment);
		commentvo.setPostId(postId);
		
		commentvo.setEmail(email);
		
		int cnt = commentMapper.writeComment(commentvo);
		
		boolean result = false;
		if (cnt > 0)
			result = true;

		return result;
	}

	private CommentVO getCommentByCommentId(int commentId) {
		return commentMapper.getCommentByCommentId(commentId);
	}

	public boolean changeComment(int commentId, String newComment) {
		CommentVO comment = getCommentByCommentId(commentId);
		if (comment != null) {
			comment.setComment(newComment);
			return commentMapper.changeComment(comment) > 0;
		}
		return false;
	}

	public boolean deleteComment(int commentId) {
		return commentMapper.deleteComment(commentId) > 0;
	}
	
	 public List<CommentVO> getCommentsByPostId(int postId) {
	        return commentMapper.getCommentsByPostId(postId);
	    }

	public String getWriterByCommentId(int commentId) {
		return commentMapper.getWriterByCommentId(commentId);
	}

}

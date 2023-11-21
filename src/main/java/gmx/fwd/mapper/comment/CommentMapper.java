package gmx.fwd.mapper.comment;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.commentvo.CommentVO;

@Mapper
public interface CommentMapper {

	String getWriterByCommentId(int CommentId);

	int writeComment(CommentVO comment);
	
	int changeComment(CommentVO comment);
	
	int deleteComment(int commentId);
	
	List<CommentVO> getCommentsByPostId(int postId);
	
	CommentVO getCommentByCommentId(int commentId);
}

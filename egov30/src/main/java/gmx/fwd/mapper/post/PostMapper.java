package gmx.fwd.mapper.post;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.postvo.PostVO;

@Mapper
public interface PostMapper {
	
	String getWriterByPostId(int postId);

	int writePost(PostVO post);
	
	int changeContent(PostVO post);
	
	List<PostVO> selectPostInOrder(Map<String, Object> map);
	
	PostVO detailViewbyPostId(int postId);
	
	PostVO getPostByPostId(int postId);
	
	int deletePost(int postId);
	
	int getPostIdByTitle(String title);
	
	boolean setView(PostVO post);

	int getPostCount();

	int searchPostCount(String keyword);
	
}

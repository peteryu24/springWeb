package gmx.fwd.mapper.file;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.filevo.FileVO;

@Mapper
public interface FileMapper {
	
	FileVO getFileByPostId(int postId);
	
	FileVO getFileByFileId(int fileId);

	int saveFile(FileVO file);
	
	int deleteFile(int fileId);
}



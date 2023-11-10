package gmx.fwd.service.file;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.file.FileMapper;
import gmx.fwd.vo.filevo.FileVO;

@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;

	public boolean saveFile(FileVO file) {
		return fileMapper.saveFile(file) > 0;
	}

	/*
	 * detailPost 할 때 사용
	 */
	public FileVO getFileByPostId(int postId) {
		return fileMapper.getFileByPostId(postId);
	}

	public boolean deleteFile(int fileId) {
		int cnt = fileMapper.deleteFile(fileId);
		return cnt > 0;

	}

	public FileVO getFileByFileId(int fileId) {
		return fileMapper.getFileByFileId(fileId);
	}

	public HashMap<String, Object> getFileData(int fileId) throws Exception {
		HashMap<String, Object> fileData = new HashMap<>();

        FileVO filevo = getFileByFileId(fileId);
        String file = (String) (filevo.getFilePath());
        File downloadFile = new File(file);
        /*
		 * 파일을 byte 배열로 변환하여
		 * 이진 데이터를 처리하기에 적합한 형태로 변환
		 * 한 번에 읽지 않고 작은 크기의 버퍼로 나누어 메모리 사용 최적화
		 */
        byte[] fileByteArray = FileUtils.readFileToByteArray(downloadFile);

        fileData.put("fileVO", filevo);
        fileData.put("fileByteArray", fileByteArray);

        return fileData;
    }
}

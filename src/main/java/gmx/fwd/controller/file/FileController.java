package gmx.fwd.controller.file;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gmx.fwd.service.file.FileService;
import gmx.fwd.service.post.PostService;
import gmx.fwd.vo.filevo.FileVO;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;

	/*
	 * fileId로 파일 정보 가져와서
	 * 다운로드 전 서버에서 가져오기 위해 경로 String 타입으로 가져옴
	 * 
	 * 파일을 byte 배열로 변환
	 * 다운로드시 원본 이름과 동일하게 저장
	 * 
	 * Content-Type을 지정해서
	 * 다운로드되는 파일이 이진 파일임을 정의
	 * 
	 * 버퍼에 파일을 담아 스트림으로 출력
	 * 출력하고 flush, close
	 * close안에 flush가 있긴 함
	 * 
	 * http 관련은 service로 넘기는 거 비추
	 */
	@GetMapping("/downloadFile.do")
	public void downloadFile(@RequestParam(name = "fileId") int fileId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, Object> fileData = fileService.getFileData(fileId);
		FileVO filevo = (FileVO) fileData.get("fileVO");
		byte[] fileByte = (byte[]) fileData.get("fileByteArray");

		String fileName = filevo.getFileName();
		/*
		 * 파일 다운로드 시에 사용되는 Content-Type 
		 * 다운로드되는 파일이 이진 파일임을 정의
		 * 브라우저는 파일을 직접 열지 않고 다운로드 가능
		 */
		response.setContentType("application/octet-stream");
		// 다운로드시 원본 이름과 동일하게 저장
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		// 버퍼에 파일을 담아 스트림으로 출력
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@GetMapping("/deleteFile.do")
	public String deleteFile(@RequestParam(name = "fileId") int fileId, @RequestParam(name = "postId") int postId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		
		if (!(currentUsername == null || !((postService.getWriterByPostId(postId)).equals(currentUsername)))) {
			fileService.deleteFile(fileId);
		}
		
		return "redirect:/post/detailPost.do?postId=" + postId;
	}

}

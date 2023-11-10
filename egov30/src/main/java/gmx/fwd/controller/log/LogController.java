package gmx.fwd.controller.log;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gmx.fwd.service.log.LogService;
import gmx.fwd.vo.logvo.LogVO;

@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;

	@GetMapping(value = "/goShowLogs.do")
	public String showLogs() {
		return "log/showLogs";
	}

	@ResponseBody
	@GetMapping(value = "/logsPerPage.do")
	public List<LogVO> logsPerPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(name = "category", defaultValue ="") String category) {
		System.out.println(category);
		return logService.logsPerPage(currentPage, category);
	}

	@ResponseBody
	@GetMapping(value = "/totalPageNum.do", produces = "application/json")
	public HashMap<String, Integer> totalPageNum(@RequestParam(value="category",defaultValue="")String category) {
		HashMap<String, Integer> response = new HashMap<>();
		response.put("totalPage", logService.getPageCount(category));
		return response;
	}
}

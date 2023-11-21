package gmx.fwd.controller.log;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gmx.fwd.service.log.LogService;

@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;

	@GetMapping(value = "/goShowLogs.do")
	public String goshowLogs() {
		return "log/showLogs";
	}

	@ResponseBody
	@GetMapping(value = "/showLogs.do", produces = "application/json")
	public HashMap<String, Object> showLogs(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(name = "category", defaultValue = "0") int category) {
		
		System.out.println("currnet category: " + category);

		HashMap<String, Object> response = new HashMap<>();

		response.put("logs", logService.logsPerPage(currentPage, category));
		response.put("totalPage", logService.getPageCount(category));

		return response;
	}
}

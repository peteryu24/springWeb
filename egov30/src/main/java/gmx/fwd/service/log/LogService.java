package gmx.fwd.service.log;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.log.LogMapper;
import gmx.fwd.vo.logvo.LogVO;

@Service
public class LogService {

	@Autowired
	LogMapper logMapper;

	public List<LogVO> logsPerPage(int currentPage, String category) {
		HashMap<String, Object> map = new HashMap<>();
		
		int start = ((currentPage - 1) * 10);
		map.put("start", start);
		
		map.put("category", category);

		return logMapper.logsPerPage(map);
	}

	public int getPageCount(String category) {
		
		int totalPageNeeded = (logMapper.getLogCountByCategory(category) / 10);

		if ((logMapper.getLogCountByCategory(category) % 10) != 0) {
			totalPageNeeded += 1;
		}
		return totalPageNeeded;
	}

}

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

	public List<LogVO> logsPerPage(int currentPage, int category) {
		HashMap<String, Object> map = new HashMap<>();
		
		System.out.println("current category in service: " + category);
		
		int start = ((currentPage - 1) * 10);
		
		map.put("category", category);
		map.put("start", start);	

		return logMapper.logsPerPage(map);
	}

	public int getPageCount(int category) {
		
		int totalPageNeeded = (logMapper.getLogCountByCategory(category) / 10);

		if ((logMapper.getLogCountByCategory(category) % 10) != 0) {
			totalPageNeeded += 1;
		}
		return totalPageNeeded;
	}

}

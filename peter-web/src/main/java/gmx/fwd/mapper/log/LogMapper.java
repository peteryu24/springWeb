package gmx.fwd.mapper.log;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.logvo.LogVO;

@Mapper
public interface LogMapper {
	
	int writeLog(LogVO log);
	
	List<LogVO> logsPerPage(Map<String, Object> map);

	int getLogCount();
	
	int getLogCountByCategory(int category);

	void deleteOldestLog();
}

package gmx.fwd.service.map;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmx.fwd.mapper.map.MapMapper;
import gmx.fwd.vo.mapvo.LayerStyleVO;

@Service
public class MapService {

	@Autowired
	MapMapper mapMapper;

	public LayerStyleVO getLayerStyle(String attributeName) {
		return mapMapper.getLayerStyle(attributeName);
	}

	public String getCctvNameByCoordinates(HashMap<String, Double> params) {
		return mapMapper.getCctvNameByCoordinates(params);
	}

}

package gmx.fwd.mapper.map;

import java.util.HashMap;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import gmx.fwd.vo.mapvo.LayerStyleVO;

@Mapper
public interface MapMapper {

	LayerStyleVO getLayerStyle(String attributeName);

	String getCctvNameByCoordinates(HashMap<String, Double> params);

}

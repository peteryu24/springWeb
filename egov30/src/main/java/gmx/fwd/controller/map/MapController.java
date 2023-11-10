package gmx.fwd.controller.map;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gmx.fwd.service.map.MapService;
import gmx.fwd.vo.mapvo.LayerStyleVO;

@Controller
@RequestMapping("/map")
public class MapController {

	@Autowired
	MapService mapService;

	@GetMapping("/showViewMap.do")
	public String loginPage() {
		return "map/viewMap";
	}

	@ResponseBody
	@PostMapping("/getLayerStyle.do")
	public LayerStyleVO getLayerStyle(@RequestParam String attributeName) {
		LayerStyleVO layerStyle = mapService.getLayerStyle(attributeName);
		
		if (layerStyle == null) {
			return null;
		}

		return layerStyle;
	}

	@ResponseBody
	@PostMapping("/getCctvNameByCoordinates.do")
	public String getCctvNameByCoordinates(@RequestParam double x, @RequestParam double y) {
		HashMap<String, Double> params = new HashMap<>();
		
		/*x = Math.floor(x * 100) / 100;
		y = Math.floor(y * 100) / 100;*/
		
		params.put("x", x);
		params.put("y", y);
		
		return mapService.getCctvNameByCoordinates(params);
	}

}

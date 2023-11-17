package gmx.fwd.controller.map;

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
		
		if ((mapService.getLayerStyle(attributeName)) == null) {
			return null;
		}

		return mapService.getLayerStyle(attributeName);
	}

}

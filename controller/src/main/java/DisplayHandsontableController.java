import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class DisplayHandsontableController {

	@GetMapping(value = "displayhandsontable")
	public ModelAndView displayHandsontableController(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("displayhandsontable");

		return mav;
	}
}
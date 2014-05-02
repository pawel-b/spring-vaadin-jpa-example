package pl.pawelb.svexample.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Login Spring MVC controller
 * 
 * @author pawelb
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", defaultValue = "false", required = false) Boolean isError,
            ModelMap model) {
        if (isError) {
            model.put("isError", isError);
        }
        return "login";
    }
}

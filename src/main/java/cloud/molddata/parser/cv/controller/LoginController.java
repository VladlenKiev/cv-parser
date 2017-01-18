package cloud.molddata.parser.cv.controller;

import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.service.FileUploadService;
import cloud.molddata.parser.cv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private FileUploadService uploadService;

	/*@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This is default page!");
		model.setViewName("hello");
		return model;

	}*/
	@RequestMapping(value = {"/"})
	public String home(HttpServletRequest request,@ModelAttribute("userForm") UserSecurity userForm) {
		String sessionID = request.getSession().getId();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nameAuth = auth.getName();

		if (!"anonymousUser".equals(nameAuth))
			userService.authorization(nameAuth, sessionID);

		return "redirect:/fileUploader";
	}
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		//UserSecurity userSecurityForm = new UserSecurity();
		model.addAttribute("userForm", new UserSecurity());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") UserSecurity userForm,
							   BindingResult bindingResult, Model model, HttpServletRequest request) {
		//userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		String sessionID = request.getSession().getId();
		//System.out.println("sessID from REGISTRATION="+sessionID);
		userService.save(userForm, sessionID);


		//securityService.autologin(userForm.getUsername(), userForm.getPassword());

		return "redirect:/fileUploader";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request
							  ) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("loginBS");


		//System.out.println("sessID from LOGIN GET="+request.getSession().getId());

		String sessionID = request.getSession().getId();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nameAuth = auth.getName();
		//System.out.println("/Login name=" + nameAuth);

		if ("anonymousUser".equals(nameAuth))
			userService.authorization(nameAuth, sessionID);

		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nameAuth = auth.getName();
		System.out.println("MODELauth_NAME AUTH=" + nameAuth);

		String sessionID = request.getSession().getId();
		System.out.println("sessID from REGISTRATION="+sessionID);
		userService.authorization(nameAuth, sessionID);*/
		return model;

	}

@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public ModelAndView test1Page() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring TEST w/o SEC");
		model.addObject("message", "This is TEST page! without auth_SEC for ");
		model.setViewName("test1");
		return model;

	}



	@RequestMapping(value = "/db/{userName}", method = RequestMethod.GET)
	public String dbaPage(ModelMap model, Map<String, Object> map, @PathVariable String userName) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String authName = auth.getName();
		model.addAttribute("user", authName);
		model.addAttribute("userName", userName);
		model.addAttribute("title", "You are on personal page for ONE user!");
		model.addAttribute("message", "This page is for ROLE_ADMIN only!");
		/*map.put("fileList", uploadService.listFiles());*/
		map.put("userList", uploadService.listUsers(userName, "000"));
		map.put("cvList", uploadService.listCVes());
		map.put("userListAuth", uploadService.listUsersAuth());

		return "dba";
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage(Map<String, Object> map) {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "You are on ADMIN PAGE!");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nameAuth = auth.getName();
		System.out.println("MODELauth_NAME AUTH from /LIST=" + nameAuth);

		map.put("fileList", uploadService.listFiles());
		map.put("userList", uploadService.listUsersAll());
		map.put("userListAuth", uploadService.listUsersAuth());
		//System.out.println(map.get("userList").toString());

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

}
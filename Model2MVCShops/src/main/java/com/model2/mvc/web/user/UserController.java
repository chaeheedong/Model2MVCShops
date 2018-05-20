package com.model2.mvc.web.user;

import java.net.HttpCookie;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	@RequestMapping(value="/addUserView", method=RequestMethod.GET)
	public String addUserView() throws Exception {

		System.out.println("/addUserView.do");

		return "redirect:/user/addUserView.jsp";
	}

	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user) throws Exception {

		System.out.println("/addUser.do");
		userService.addUser(user);

		return "redirect:/user/loginView.jsp";
	}

	@RequestMapping(value="/getUser", method= {RequestMethod.GET, RequestMethod.POST})
	public String getUser(@RequestParam("userId") String userId, Model model) throws Exception {

		System.out.println("/getUser.do");
		User user = userService.getUser(userId);
		model.addAttribute("user", user);

		return "forward:/user/getUser.jsp";
	}

	@RequestMapping(value="/updateUserView", method= {RequestMethod.GET, RequestMethod.POST})
	public String updateUserView(@RequestParam("userId") String userId, Model model) throws Exception {

		System.out.println("/updateUserView.do");
		User user = userService.getUser(userId);
		model.addAttribute("user", user);

		return "forward:/user/updateUser.jsp";
	}

	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, Model model, HttpSession session) throws Exception {

		System.out.println("/updateUser.do");
		// Business Logic
		userService.updateUser(user);

		String sessionId = ((User) session.getAttribute("user")).getUserId();
		if (sessionId.equals(user.getUserId())) {
			session.setAttribute("user", user);
		}

		return "redirect:/user/getUser.do?userId=" + user.getUserId();
	}

	@RequestMapping(value="/loginView", method=RequestMethod.GET)
	public String loginView() throws Exception {

		System.out.println("/loginView.do");

		return "redirect:/user/loginView.jsp";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, HttpSession session) throws Exception {

		System.out.println("/login.do");
		// Business Logic
		User dbUser = userService.getUser(user.getUserId());

		if (user.getPassword().equals(dbUser.getPassword())) {
			session.setAttribute("user", dbUser);
		}

		return "redirect:/index.jsp";
	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {

		System.out.println("/logout.do");

		session.invalidate();

		return "redirect:/index.jsp";
	}

	@RequestMapping(value="/checkDuplication", method=RequestMethod.POST)
	public String checkDuplication(@RequestParam("userId") String userId, Model model) throws Exception {

		System.out.println("/checkDuplication.do");
		boolean result = userService.checkDuplication(userId);
		model.addAttribute("result", new Boolean(result));
		model.addAttribute("userId", userId);

		return "forward:/user/checkDuplication.jsp";
	}

	@RequestMapping(value="/listUser", method= {RequestMethod.GET, RequestMethod.POST})
	public String listUser(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)
			throws Exception {

		System.out.println("/listUser.do");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		Map<String, Object> map = userService.getUserList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/user/listUser.jsp";
	}
}
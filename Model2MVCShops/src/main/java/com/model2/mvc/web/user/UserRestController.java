package com.model2.mvc.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

@RestController
@RequestMapping("/user/*")
public class UserRestController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserRestController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	@RequestMapping(value="/json/addUser", method=RequestMethod.POST)
	public void addUser(@RequestBody User user) throws Exception {

		System.out.println("/user/json/addUser : POST");
		
		userService.addUser(user);

	}

	@RequestMapping(value="/json/getUser/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId) throws Exception {
		
		System.out.println("/user/json/getUser : GET");
		
		User dbUser = new User();
		dbUser = userService.getUser(userId);
		
		return dbUser;
		
	}

	@RequestMapping(value="/json/updateUser/{userId}", method=RequestMethod.POST)
	public void updateUser(@PathVariable String userId, @RequestBody User user) throws Exception {

		System.out.println("/user/json/updateUser : POST");
		
		userService.updateUser(user);
		
	}

	@RequestMapping(value="/json/login", method=RequestMethod.POST)
	public User login(@RequestBody User user, HttpSession session) throws Exception {

		System.out.println("/user/json/login : POST");
		// Business Logic
		User dbUser = userService.getUser(user.getUserId());

		if (user.getPassword().equals(dbUser.getPassword())) {
			session.setAttribute("user", dbUser);
			return dbUser;
		}
		
		return null;

	}

	@RequestMapping(value="/json/listUser", method= {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> listUser(@ModelAttribute("search") Search search, HttpServletRequest request)
			throws Exception {

		System.out.println("/user/json/listUser : { GET / POST }");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		Map<String, Object> map = userService.getUserList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		map.put("search", search);
		map.put("resultPage", resultPage);

		return map;
	}
}
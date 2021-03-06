package com.shortn0tes.feignexample.controllers;

import com.shortn0tes.feignexample.feign.UserClient;
import com.shortn0tes.feignexample.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 1/15/2018.
 */
@Controller
public class UserController {

	@Autowired
	UserClient userClient;

	@RequestMapping("/create")
	@ResponseBody
	String create() {
		User user = userClient.getUser(1L);
		user.setId(null);
		user = userClient.createUser(user);

		return String.format("Created a user with id %d", user.getId());
	}

	@RequestMapping("/read")
	@ResponseBody
	String read() {
		List<User> users = userClient.getUsers();

		return String.format("Retrieved %d users total", users.size());
	}

	@RequestMapping("/update")
	@ResponseBody
	String update() {
		User user = userClient.getUser(1L);
		String oldName = user.getName();
		user.setName("John");
		userClient.updateUser(1L, user);

		return String.format("Update successful. User id: %d, old name: %s, new name: %s",
			user.getId(), oldName, user.getName());
	}

	@RequestMapping("/delete")
	@ResponseBody
	String delete() {
		userClient.deleteUser(1L);

		return "Deleted";
	}
}

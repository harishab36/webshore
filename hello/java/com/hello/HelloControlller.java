package com.hello;

import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.entities.User;
import com.hello.entities.UserJsonResponse;
import com.hello.service.HelloService;

@Controller
public class HelloControlller {

	@Autowired
	HelloService helloService;

	@RequestMapping("/")
	public String displayHello() {
		return "static/hello.html";
	}

	@RequestMapping("/userinfo")
	@ResponseBody
	public String printUserInfo(@RequestBody String formData) throws IOException {

		User user = new User();
		UserJsonResponse response = new UserJsonResponse();

		ObjectMapper mapper = new ObjectMapper();
		user = mapper.readValue(formData, User.class);
		response.setUser(user);
		if (user.getName().isEmpty()) {
			response.addErrorMessgae("Enter the valid User Name");
		}
		if (user.getPlace().isEmpty()) {
			response.addErrorMessgae("Enter the proper Place");
		}

		if (StringUtils.isEmpty(response.getErrors())) {
			helloService.addUser(user);
		}

		return mapper.writeValueAsString(response);

	}

	@RequestMapping("/userList")
	@ResponseBody
	public String getUserList() throws JsonProcessingException {
		UserJsonResponse response = new UserJsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		response.setUserList(helloService.getUserList(null));
		return mapper.writeValueAsString(response);
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(@RequestBody String userId) throws JsonProcessingException {

		UserJsonResponse response = new UserJsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		response.addErrorMessgae(
				helloService.deleteUser(userId) ? "Deleted Successfully" : "Error in delete Operation");

		return mapper.writeValueAsString(response);
	}
	
	@RequestMapping("/getUser")
	@ResponseBody
	public String getUser(@RequestBody String userId) throws JsonParseException, JsonMappingException, IOException {
		UserJsonResponse response = new UserJsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		User user = helloService.getUser(Integer.parseInt(userId));
		if(user!=null) {
			response.setUser(user);
			response.addErrorMessgae("User data loaded please proceed to edit and click submit");
		}
		
		return mapper.writeValueAsString(response);
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public String updateUser(@RequestBody String userInfo) throws JsonParseException, JsonMappingException, IOException {
		UserJsonResponse response = new UserJsonResponse();
		ObjectMapper mapper = new ObjectMapper();
		
		User user = mapper.readValue(userInfo, User.class);
		User updatedUser = helloService.editUser(user);
		
		if(user.equals(updatedUser)) {
			response.addErrorMessgae("User record updated successfully.");
		}
		
		return mapper.writeValueAsString(response);
	}

}

package ijp.controllers;

import java.io.IOException;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ijp.ResponseMessage;
import ijp.models.Role;
import ijp.models.User;
import ijp.repository.UserRepository;
import ijp.services.EmailService;
import ijp.services.UserServices;

@RestController

// @RequestMapping("/ijpservices")

public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserServices userService;

	@Autowired
	EmailService emailService;

	@GetMapping("/hello")
	public ResponseEntity<ResponseMessage> hello() {
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Hello! IJP web services are running."));
	}

	@GetMapping("/getUser")
	public HashMap<String, String> getAllJobs(@AuthenticationPrincipal OidcUser user) {
		String userId = userRepository.getUserId(user.getPreferredUsername().toLowerCase());
		String userRole = userRepository.getUserRole(user.getPreferredUsername().toLowerCase());
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("userId", userId);
		data.put("email", user.getPreferredUsername());
		data.put("name", user.getFullName());
		data.put("role", userRole);
		return data;
	}

	// Request Handler to check if user has already filled up the profile or not
	@GetMapping("/profileExists")
	public ResponseEntity<ResponseMessage> profileExists(@AuthenticationPrincipal OidcUser user) throws Exception {
		boolean doesProfileExists = userService.isProfileExists(user.getPreferredUsername());
		if (doesProfileExists) {
			return new ResponseEntity<>(new ResponseMessage("User profile exists"), HttpStatus.OK);
		}

		else
			return new ResponseEntity<>(new ResponseMessage("User profile not exists"), HttpStatus.OK);
	}

	// to save Employee if not there in database
	@PostMapping("/saveUser/{user_name}/{email}")
	public ResponseEntity<ResponseMessage> saveUser(@PathVariable("user_name") String name,
			@PathVariable("email") String email) {
		try {
			if (!userService.isEmailExist(email)) {
				// Setting role as Employee.
				Role role = new Role();
				role.setRoleId(3);

//				Setting the user details to save in database
				User users = new User();
				users.setName(name);
				users.setRole(role);
				users.setEmail(email);
//
//				// calling service layer to save to database
				userService.saveUserDetails(users);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Saved User Details ::: "));

			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User already exists!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Something went wrong! Try again"));
		}

	}

	@PostMapping("/saveHrManager/{email}/{name}")
	public ResponseEntity<ResponseMessage> createHiringManager(@PathVariable("email") String email,
			@PathVariable("name") String name) {
		try {
			boolean check = userService.isEmailExist(email);
			if (check == false) {
				// Setting role as Employee.
				Role role = new Role();
				role.setRoleId(2);

				User users = new User();
//			Setting the user details to save in database
				users.setName(name);
				users.setRole(role);
				users.setEmail(email);

//			// calling service layer to save to database
				userService.saveUserDetails(users);
				return ResponseEntity.ok().body(new ResponseMessage("Hiring manager created"));
			}
			return ResponseEntity.ok().body(new ResponseMessage("Hiring manager already exists"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage("Error creating Hiring Manager :: " + e.toString()));
		}
	}

	@GetMapping("/getFile")
	public ResponseEntity<Resource> resumeViewAndDownload(@AuthenticationPrincipal OidcUser user) throws IOException {
		User getUserresume = userService.getUserDetail(user.getPreferredUsername());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(getUserresume.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + getUserresume.getFileName() + "\"")
				.body(new ByteArrayResource(getUserresume.getResume()));
	}

	@GetMapping("/getFile/{userId}")
	public ResponseEntity<Resource> resumeById(@PathVariable("userId") int userId) {
		User getUserresume = userRepository.getUserById(userId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(getUserresume.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + getUserresume.getFileName() + "\"")
				.body(new ByteArrayResource(getUserresume.getResume()));
	}

	@GetMapping("/mail")
	public void sendMail() throws MessagingException {
		// add this line of code, in the methods above, wherever email has to be send.
		emailService.sendMail("skulkarni@arigs.com", "Office365 Mail Working",
				"Hello Shreyas! This is a Test message from Internal Job Portal (sshah@arigs.com)");
	}
	
	@PostMapping("/updateUser")
	public ResponseEntity<ResponseMessage> updateUser(
			@AuthenticationPrincipal OidcUser user,
			@RequestParam("userDto") String userDto,
			@RequestParam("file") MultipartFile file) {
		try {
			List<String> skills = new ArrayList<String>();
			skills.add(userDto);
			User getUser=userRepository.checkEmail(user.getPreferredUsername());
			User user2 = new User(file.getBytes());
			user2.setUserId(getUser.getUserId());
			user2.setEmail(getUser.getEmail());
			user2.setRole(getUser.getRole());
			user2.setSkill(skills);
			user2.setName(getUser.getName());
			user2.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
			user2.setFileType(file.getContentType());
			
			// calling service layer to save to database
			userRepository.save(user2);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Saved User Details"));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Something went wrong! Try again"));
		}
		}
	
	@PostMapping("/userUpdate/{email}")
	public ResponseEntity<ResponseMessage> updateUser(
			@PathVariable("email") String email,
			@RequestParam("userDto") String userDto,
			@RequestParam("file") MultipartFile file) {
		try {			
			List<String> skills = new ArrayList<String>();
			skills.add(userDto);
			User getUser=userRepository.checkEmail(email);
			User user2 = new User(file.getBytes());
			user2.setUserId(getUser.getUserId());
			user2.setEmail(getUser.getEmail());
			user2.setRole(getUser.getRole());
			user2.setSkill(skills);
			user2.setName(getUser.getName());
			user2.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
			user2.setFileType(file.getContentType());
			
			// calling service layer to save to database
			userRepository.save(user2);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Saved User Details"));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Something went wrong! Try again"));
		}
		}

}

package ijp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ijp.models.User;
import ijp.repository.UserRepository;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;

	public User saveUserDetails(User user) {
		return userRepository.save(user);
	}
	
	public boolean isEmailExist(String email) {
		User user = userRepository.checkEmail(email);
		if (user==null) {
			return false;
		}
		return true;
	}

	public User getUserDetail(String email) {
		return userRepository.checkEmail(email);
	}
	
	public void updateResume(User user,int userID) {
		user.setUserId(userID);
		userRepository.save(user);
	}
	
	public boolean isProfileExists(String email) throws Exception {
		User user = userRepository.checkEmail(email);
		// System.out.println(user);
		if(user == null) {
		// System.out.println("inside if condition saying the user is not Exist...");
		return false;
		}
		// the above line is used to check if the user profile exist or not in User Table in database based 
		//hint: we are getting user email from the login
		if (user.getResume() != null && user.getSkill() != null) { //if user profile not available add them,
		return true;
		}
		else return false;
		}
}

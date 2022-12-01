package ijp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ijp.models.IjpUsers;
import ijp.repository.IjpRepository;

// Service for 'IjpUsers' Entity
@Service
public class UserService {

	@Autowired
	private IjpRepository repository;
	
	public IjpUsers getUserDetail(String email) {
		return repository.findById(email).get();
	}
	public IjpUsers getUserEmail(String email) {
		return repository.findById(email).get();
		}
	public void saveUserDetails(IjpUsers ijpUsers) {
		repository.save(ijpUsers);
	}
	
	public boolean checkIfEmailExists(String email) {
		IjpUsers user = repository.checkEmail(email);
		if (user==null) {
			return false;
		}
		return true;
	}
	
//	public boolean doesProfileExists(String email) throws Exception {
//		IjpUsers user = repository.findById(email).orElseThrow(() -> new Exception("User not exists"));
//		if (user.getResume() != null && user.getSkill() != null) {
//			return true;
//		}
//		else return false;
//	}
	public boolean doesProfileExists(String email) throws Exception {
		IjpUsers user = repository.checkEmail(email);
//		System.out.println("inside service class line 45" + user);
		if(user == null) {
		System.out.println("inside if loop saying the user is not Exist...");
		return false;
		}
		// the above line is used to check if the user profile exist or not in IjpUser Table in database based /hint: we are getting user email from the login
		if (user.getResume() != null && user.getSkill() != null) { //if user profile not available add them,
		return true;
		}
		else return false;
		}
}

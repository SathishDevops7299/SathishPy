package ijp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ijp.models.Application;
import ijp.repository.ApplicationRepository;

@Service
public class ApplicationService {
	@Autowired
	private ApplicationRepository applicationRepository;

	// service to fetch all job applications
	public List<Application> getAllJobApplications() {
		return applicationRepository.getApplicationAll();
	}
	
	public List<Application> getApplicationById(int userId) {
		return applicationRepository.getApplicationByUserId(userId);
	}
}

package ijp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ijp.models.AppliedDTO;
import ijp.models.AppliedJobs;
import ijp.repository.AppliedJobRepository;

// Service for 'Applying to Job'
@Service
public class ApplyJobService {

	@Autowired
	private AppliedJobRepository repository;

	// service to fetch all job applications
	public List<AppliedJobs> getAllJobApplications() {
		return repository.findAll();
	}

	// service to fetch particular job application
	public AppliedJobs getAppliedJobDetails(String email, Integer jobId) {
		return repository.getJobAppliedByEmail(email, jobId);
	}

	// service to get all jobs applied by a user
	public List<AppliedJobs> getJobsApplied(String email) {
		return repository.getJobsAppliedByEmail(email);
	}

	// service to save the job/candidate details after applying to it
	public AppliedJobs saveAppliedJob(AppliedJobs appliedJob) {
		return repository.save(appliedJob);
	}

	public List<AppliedDTO> getJobAppliedStatus(String usermail) {
		// TODO Auto-generated method stub
		return repository.getJobAppliedStatusByEmail(usermail);
	}
}

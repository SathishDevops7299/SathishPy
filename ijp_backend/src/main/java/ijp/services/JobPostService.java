package ijp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ijp.models.JobPost;
import ijp.repository.JobPostRepository;

// Service for 'JobPost' Entity
@Service
public class JobPostService {

	@Autowired
	private JobPostRepository repository;
	
	public List<JobPost> getJobs() {
		return repository.findAll();
	}
	
	public Optional<JobPost> getJobById(Integer jobId) {
		return repository.findById(jobId);
	}
	
	public JobPost postJobService(JobPost jobPost) {
		return repository.save(jobPost);
	}
	
	public boolean doesJobExists(Integer jobId) {
		Optional<JobPost> jobOptional = repository.findById(jobId);
		return jobOptional.isPresent();
	}
	
	public void deleteJobService(Integer jobId) {
		repository.deleteById(jobId);
	}
	
		public JobPost getjobById(Integer jobId) {
		return repository.findByJobId(jobId);
		}
}

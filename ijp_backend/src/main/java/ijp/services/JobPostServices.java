package ijp.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ijp.models.JobPost;
import ijp.repository.JobPostRepository;

@Service
public class JobPostServices {
	@Autowired
	private JobPostRepository jobPostRepository;
	
	public List<JobPost> getJobs() {
		return jobPostRepository.findAll();
	}
	
	public Optional<JobPost> getJobById(Integer jobId) {
		return jobPostRepository.findById(jobId);
	}
	
	public JobPost postJobService(JobPost jobPost) {
		return jobPostRepository.save(jobPost);
	}
	
	public boolean isJobExists(Integer jobId) {
		Optional<JobPost> jobOptional = jobPostRepository.findById(jobId);
		return jobOptional.isPresent();
	}
	
	public void deleteJobService(Integer jobId) {
		jobPostRepository.delAppliliedJobPost(jobId);
		jobPostRepository.deleteById(jobId);
	}
	
	public JobPost getjobById(Integer jobId) {
		return jobPostRepository.findByJobId(jobId);
	}
}

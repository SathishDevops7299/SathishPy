package ijp.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ijp.ResponseMessage;
import ijp.models.Application;
import ijp.models.JobPost;
import ijp.models.User;
import ijp.repository.JobPostRepository;
import ijp.services.ApplicationService;
import ijp.services.JobPostServices;
import ijp.services.UserServices;

@RestController

// @RequestMapping("/ijpservices")

public class JobPostController {
	
	@Autowired
	private JobPostServices jobPostService;
	
	@Autowired
	private UserServices userService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private JobPostRepository jobPostRepository;
	
	@GetMapping("/getJobs")
	public ResponseEntity<List<JobPost>> getAllJobs() {
		List<JobPost> jobs = jobPostService.getJobs();

		if (!jobs.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(jobs);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jobs);
	}

	@GetMapping("/getJobById/{jobId}")
	public ResponseEntity<JobPost> getJobById(@PathVariable("jobId") Integer jobId) {
		JobPost getJobById = jobPostService.getjobById(jobId);
		return ResponseEntity.status(HttpStatus.OK).body(getJobById);
	}

	// Posting a Job -> Delivery Manager function
	@PostMapping("/postJob/{userId}")
	public ResponseEntity<ResponseMessage> postJob(@PathVariable("userId") int userId,@RequestBody JobPost jobPost) {

		try {
			User user=new User();
			user.setUserId(userId);
			Date date=new Date();
			//Saving user Id to see who posted the job
			jobPost.setUser(user);
			jobPost.setJobDate(date);
			JobPost job = jobPostService.postJobService(jobPost);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Job Posted successfully -> " + job));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage("Could not post the job! due to error -> " + e));
		}
	}

	// Endpoint to update status or information of the Job -> Delivery Manager
	// functionality
	@PostMapping("/updateJob/{jobId}")
	public ResponseEntity<ResponseMessage> updateJob(@PathVariable("jobId") Integer jobId,
			@RequestBody JobPost jobPost) {

		try {
			JobPost job = jobPostService.getJobById(jobId).get();
			job.setJobId(jobId);
			job.setJobDesc(jobPost.getJobDesc());
			job.setJobPosition(jobPost.getJobPosition());
			job.setJobName(jobPost.getJobName());
			job.setJobType(jobPost.getJobType());
			job.setJobExperience(jobPost.getJobExperience());
			
			jobPostService.postJobService(job);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Job Updated successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Either the Job doesn't exist or Some Internal Server error!"));
		}
	}

	// Endpoint to delete a job -> Delivery Manager function
	@PostMapping("/deleteJob/{jobId}")
	public ResponseEntity<ResponseMessage> deleteJob(@PathVariable("jobId") Integer jobId) {

		try {
			jobPostService.deleteJobService(jobId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Job Deleted successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Either the Job doesn't exist or Some Internal Server error!"));
		}
	}
	
	@GetMapping("/closeJob/{jobId}/{condition}")
	public ResponseEntity<ResponseMessage> closeJob(@PathVariable("jobId") Integer jobId,@PathVariable("condition") Boolean condition) {

		try {
			jobPostRepository.updateJobStatus(condition,jobId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Job status updated successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Either the Job doesn't exist or Some Internal Server error!"));
		}
	}
	
	
	

}

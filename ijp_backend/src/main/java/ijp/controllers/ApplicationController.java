package ijp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ijp.ResponseMessage;
import ijp.models.Application;
import ijp.models.JobPost;
import ijp.models.User;
import ijp.repository.ApplicationRepository;
import ijp.repository.UserRepository;
import ijp.services.ApplicationService;

@RestController

// @RequestMapping("/ijpservices")

public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	// Apply job -> when user click on 'Apply' -> Candidate function
			@PostMapping("/applyJob/{jobId}/{userId}")
			public ResponseEntity<ResponseMessage> applyJob(@PathVariable("userId") int userId,
					@PathVariable("jobId") int jobId
					) {	
				List<Application> jobDetail = applicationRepository.getApplicationById(userId,jobId);
				if (!jobDetail.isEmpty()) {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage("You have already applied for this Job, cannot apply again!"));
				}
				try {
					JobPost applyJob=new JobPost();
					applyJob.setJobId(jobId);
					
					User applyUser=new User();
					applyUser.setUserId(userId);
					
					Application application = new Application();
					application.setJob(applyJob);
					application.setUser(applyUser);
					application.setSelectStatus(false);
					applicationRepository.save(application);
	
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Applied Job successfully"));
	
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
							.body(new ResponseMessage("Either the Job doesn't exist or You have not updated your Profile!"));
				}
			}

	@GetMapping("/getApplications")
	public ResponseEntity<List<Application>> getAllApplication() {
		List<Application> application = applicationService.getAllJobApplications();
		if (application!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(application);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(application);
	}

	@GetMapping("/getApplications/{userId}/{jobId}")
	public ResponseEntity<List<Application>> getAllApplicationByJobUserId(@PathVariable("userId") int userId,
			@PathVariable("jobId") int jobId) {
		List<Application> application = applicationRepository.getApplicationById(userId, jobId);

		if (!application.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(application);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(application);
	}
	
	@GetMapping("/getAppliedJobs_Status")
	public ResponseEntity<List<Application>> getJobAppliedStatusOfUsers(@AuthenticationPrincipal OidcUser user) {
		String id=userRepository.getUserId(user.getPreferredUsername().toLowerCase());
		int userId=Integer.parseInt(id);
		List<Application> application=applicationService.getApplicationById(userId);
		if(application!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(application);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(application);
	}
	
	@GetMapping("/jobApplied/{userId}")
	public ResponseEntity<List<Application>> getJobsApplied(@PathVariable("userId") int userId) {
		List<Application> application=applicationService.getApplicationById(userId);
		if(application!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(application);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(application);
	}
	
	@GetMapping("/selectApplicant/{jobId}/{userId}/{condition}")
	public ResponseEntity<ResponseMessage> selectApplicant(@PathVariable("jobId") Integer jobId
		    ,@PathVariable("userId") Integer userId
			,@PathVariable("condition") Boolean condition) {

		try {
			applicationRepository.updateSelectStatus(condition,jobId,userId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Employee selected successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Internal Server error!"));
		}
	}
}

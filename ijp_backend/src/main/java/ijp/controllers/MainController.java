package ijp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ijp.ResponseMessage;
import ijp.models.AdminUsers;
import ijp.models.AppliedDTO;
import ijp.models.AppliedJobs;
import ijp.models.IjpUsers;
import ijp.models.JobPost;
import ijp.repository.AdminUsersRepository;
import ijp.services.ApplyJobService;
import ijp.services.EmailService;
import ijp.services.JobPostService;
import ijp.services.UserService;
import ijp.Config;

@RestController
//@RequestMapping("/ijpservices")
public class MainController {

	@Autowired
	UserService userService;

	@Autowired
	private JobPostService jobPostService;

	@Autowired
	private ApplyJobService applyJobService;

	@Autowired
	EmailService emailService;

	@Autowired
	private AdminUsersRepository adminUsersRepository;

	@Autowired
    private Config config;

	// Endpoint to check web services are working
	@GetMapping("/hello")
	public ResponseEntity<ResponseMessage> hello() {
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Hello! IJP web services are running."));
	}

	// Getting name of the Candidate for display on his/her dashboard.
	@GetMapping("/get-user")
	public Map<String, String> getUserName(@AuthenticationPrincipal OidcUser user) {
		//System.out.println("Main Controller: getUserName() - admin email, loginemail:"+config.getAdminEmail()+", "+user.getPreferredUsername());
		Map<String, String> data = new HashMap<String, String>();

		List<AdminUsers> adminUsers = adminUsersRepository.findAll();
		List<List<String>> userRoles = new ArrayList<>();
		List<String> emails = new ArrayList<String>();
		
		for (AdminUsers ad : adminUsers) {
			emails.add(ad.getEmail());
			String userRole = adminUsersRepository.findByEmail(ad.getEmail());
			String[] splittedRoles = userRole.split(",");
			List<String> roles = Arrays.asList(splittedRoles);
			userRoles.add(roles);
		}

		boolean isAdminUser = emails.stream().anyMatch(user.getPreferredUsername()::equalsIgnoreCase);
		//System.out.println("emails contains user email?:"+isAdminUser);
		
		if (isAdminUser) {
			if (user.getPreferredUsername().toLowerCase().equals(config.getAdminEmail().toLowerCase())) {
				data.put("name", user.getFullName());
				data.put("role", "admin");
				return data;
			}
			int index = emails.indexOf(user.getPreferredUsername());
			List<String> roles = userRoles.get(index);
			data.put("name", user.getFullName());
			data.put("role", roles.toString());
			return data;
		}

		data.put("name", user.getFullName());
		data.put("role", "user");
		return data;
	}

	// create hiring managers -> admin user functionality
	@PostMapping("/create-hiring-manager")
	public ResponseEntity<ResponseMessage> createHiringManager(@RequestBody AdminUsers adminUsers) {
		AdminUsers hiringManager = new AdminUsers();
		try {
			AdminUsers ad = adminUsersRepository.checkEmail(adminUsers.getEmail());
			if (ad == null) {
				hiringManager.setEmail(adminUsers.getEmail());
				hiringManager.setRoles(adminUsers.getRoles());
				adminUsersRepository.save(hiringManager);
				return ResponseEntity.ok().body(new ResponseMessage("Hiring Manager created"));
			}
			return ResponseEntity.ok().body(new ResponseMessage("Hiring manager already exists!"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage("Error creating Hiring Manager :: " + e.toString()));
		}
	}

	// Saving/Updating Candidate's Resume and Skills
	@PostMapping("/save-user-details")
	public ResponseEntity<ResponseMessage> saveUserDetails(@RequestParam("file") MultipartFile file,
			@RequestParam String userDto, @AuthenticationPrincipal OidcUser user) {
		try {
			if (!userService.checkIfEmailExists(user.getPreferredUsername())) {
				byte[] resumeContent = file.getBytes();
				String resumeFileName = StringUtils.cleanPath(file.getOriginalFilename());
				List<String> skills = new ArrayList<String>();
				skills.add(userDto);

				// Setting the user details to save in database
				IjpUsers users = new IjpUsers();
				users.setEmail(user.getPreferredUsername());
				users.setFileName(resumeFileName);
				users.setFileType(file.getContentType());
				users.setResume(resumeContent);
				users.setSkill(skills);

				// calling service layer to save to database
				userService.saveUserDetails(users);

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Saved User Details ::: "));
			}
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User already exists!"));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Something went wrong! Try again"));
		}

	}

	// end Point to see User resume in user dashboard my profile section.
	@GetMapping("/get-file")
	public ResponseEntity<Resource> resumeViewAndDownload(@AuthenticationPrincipal OidcUser user) throws IOException {
		IjpUsers getUserresume = userService.getUserEmail(user.getPreferredUsername());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(getUserresume.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + getUserresume.getFileName() + "\"")
				// .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" +
				// jobDetail.getFileName())
				.body(new ByteArrayResource(getUserresume.getResume()));

	}

	// Request Handler to check if user has already filled up the profile or not
	@GetMapping("/profile-exists")
	public ResponseEntity<ResponseMessage> profileExists(@AuthenticationPrincipal OidcUser user) throws Exception {
		boolean doesProfileExists = userService.doesProfileExists(user.getPreferredUsername());
		if (doesProfileExists) {
			return new ResponseEntity<>(new ResponseMessage("User profile exists"), HttpStatus.OK);
		}

		else
			return new ResponseEntity<>(new ResponseMessage("User profile not exists"), HttpStatus.OK);
	}

	@GetMapping("/getAppliedJobs_Status")
	public ResponseEntity<List<AppliedDTO>> getJobAppliedStatusOfUsers(@AuthenticationPrincipal OidcUser user) {
		System.out.println(" inside line main controller line 324 ");
		IjpUsers ijpUser = userService.getUserDetail(user.getPreferredUsername());
		if(ijpUser!=null) {
			String usermail = ijpUser.getEmail();
			List<AppliedDTO> jobDetails = applyJobService.getJobAppliedStatus(usermail);
			System.out.println("Job details from applied Jobs table are " + jobDetails.toString());
			if (jobDetails != null) {
				// String status= jobDetails.getStatus();
				return ResponseEntity.status(HttpStatus.OK).body(jobDetails);
			} else
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jobDetails);
		}
		else {
			return null;
		}
	}

	// Fetching all Posted Jobs -> To Candidate dashboard
	// for history screen of admin dashboard
	@GetMapping("/get-jobs")
	public ResponseEntity<List<JobPost>> getAllJobs() {
		List<JobPost> jobs = jobPostService.getJobs();

		if (!jobs.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(jobs);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jobs);
	}

	@GetMapping("/get-jobById/{jobId}")
	public ResponseEntity<JobPost> getJobById(@PathVariable("jobId") Integer jobId) {
		JobPost getJobById = jobPostService.getjobById(jobId);
		return ResponseEntity.status(HttpStatus.OK).body(getJobById);
	}

	// Posting a Job -> Delivery Manager function
	@PostMapping("/post-job")
	public ResponseEntity<ResponseMessage> postJob(@RequestBody JobPost jobPost) {

		try {
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
	@PostMapping("/update-job/{jobId}")
	public ResponseEntity<ResponseMessage> updateJob(@PathVariable("jobId") Integer jobId,
			@RequestBody JobPost jobPost) {

		try {
			JobPost job = jobPostService.getJobById(jobId).get();
			job.setJobId(jobId);
			job.setJobDescription(jobPost.getJobDescription());
			job.setPosition(jobPost.getPosition());
//				job.setTenure(jobPost.getTenure());
			job.setJobTitle(jobPost.getJobTitle());
			job.setReportingManager(jobPost.getReportingManager());
			job.setJobType(jobPost.getJobType());
			job.setExperience(jobPost.getExperience());
			job.setJobOpenToApply(jobPost.isJobOpenToApply());

			jobPostService.postJobService(job);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Job Updated successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Either the Job doesn't exist or Some Internal Server error!"));
		}
	}

	// Endpoint to delete a job -> Delivery Manager function
	@PostMapping("/delete-job/{jobId}")
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

	// Apply job -> when user click on 'Apply' -> Candidate function
	@PostMapping("/apply-job/{jobId}")
	public ResponseEntity<ResponseMessage> applyJob(@PathVariable("jobId") Integer jobId,
			@AuthenticationPrincipal OidcUser user) {

		AppliedJobs jobDetail = applyJobService.getAppliedJobDetails(user.getPreferredUsername(), jobId);

		if (jobDetail != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseMessage("You have applied for this Job, cannot apply again"));
		}

		try {
			IjpUsers ijpUser = userService.getUserDetail(user.getPreferredUsername());

			AppliedJobs appliedJob = new AppliedJobs();
			appliedJob.setJobId(jobId);
			appliedJob.setEmail(ijpUser.getEmail());
			appliedJob.setName(user.getFullName());
			appliedJob.setResume(ijpUser.getResume());
			appliedJob.setFileName(ijpUser.getFileName());
			appliedJob.setFileType(ijpUser.getFileType());
			appliedJob.setStatus("Applied");

			applyJobService.saveAppliedJob(appliedJob);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Applied Job successfully"));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseMessage("Either the Job doesn't exist or You have not updated your Profile!"));
		}
	}

	// Endpoint to view and download resume of a candidate
	@GetMapping("/get-file/{email}/{jobId}")
	public ResponseEntity<Resource> resumeViewAndDownload(@PathVariable("email") String email,
			@PathVariable("jobId") Integer jobId) throws IOException {
		AppliedJobs jobDetail = applyJobService.getAppliedJobDetails(email, jobId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(jobDetail.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + jobDetail.getFileName() + "\"")
				// .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" +
				// jobDetail.getFileName())
				.body(new ByteArrayResource(jobDetail.getResume()));
	}

	// Endpoint for fetching all job applications to Delivery Manager Dashboard
	@GetMapping("/get-applications")
	public ResponseEntity<List<AppliedJobs>> getJobApplications() {
		return ResponseEntity.status(HttpStatus.OK).body(applyJobService.getAllJobApplications());
	}

	@GetMapping("/job-applied/{email}")
	public ResponseEntity<List<AppliedJobs>> jobApplied(@PathVariable("email") String email) {
		List<AppliedJobs> jobApplied = applyJobService.getJobsApplied(email);
		return ResponseEntity.status(HttpStatus.OK).body(jobApplied);
	}

	// Endpoint for testing if email is working.-> NO use of endpoint in IJP app.
	@GetMapping("/mail")
	public void sendMail() throws MessagingException {
		// add this line of code, in the methods above, wherever email has to be send.
		emailService.sendMail("skulkarni@arigs.com", "Office365 Mail Working",
				"Hello Shreyas! This is a Test message from Internal Job Portal (sshah@arigs.com)");
	}

}
package ijp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public class AppliedDTO {

	@Value(value = "job_id")
	private Integer jobId;
	@Value(value = "job_description")
	private String jobDescription;
	@Value(value = "position")
	private String position;
	@Value(value = "job_title")
	private String jobTitle;
	@Value(value = "reporting_manager")
	private String reportingManager;
	@Value(value = "job_type")
	private String jobType;
	@Value(value = "experience")
	private String experience;
	@Value(value = "date_ofJob_posting")
	private Date dateOfJobPosting = new Date();
	@Value(value = "isjob_open_to_apply")
	private Boolean isJobOpenToApply;
	@Value(value = "status")
	private String status;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the jobId
	 */
	public Integer getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the reportingManager
	 */
	public String getReportingManager() {
		return reportingManager;
	}

	/**
	 * @param reportingManager the reportingManager to set
	 */
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}

	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	/**
	 * @return the experience
	 */
	public String getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(String experience) {
		this.experience = experience;
	}

	/**
	 * @return the dateOfJobPosting
	 */
	public Date getDateOfJobPosting() {
		return dateOfJobPosting;
	}

	/**
	 * @param dateOfJobPosting the dateOfJobPosting to set
	 */
	public void setDateOfJobPosting(Date dateOfJobPosting) {
		this.dateOfJobPosting = dateOfJobPosting;
	}

	/**
	 * @return the isJobOpenToApply
	 */
	public boolean isJobOpenToApply() {
		return isJobOpenToApply;
	}

	/**
	 * @param isJobOpenToApply the isJobOpenToApply to set
	 */
	public void setJobOpenToApply(boolean isJobOpenToApply) {
		this.isJobOpenToApply = isJobOpenToApply;
	}

	/**
	 * @param jobId
	 * @param jobDescription
	 * @param position
	 * @param jobTitle
	 * @param reportingManager
	 * @param jobType
	 * @param experience
	 * @param dateOfJobPosting
	 * @param isJobOpenToApply
	 * @param status
	 */
	public AppliedDTO(Integer jobId, String jobDescription, String position, String jobTitle, String reportingManager,
			String jobType, String experience, Date dateOfJobPosting, Boolean isJobOpenToApply, String status) {
		super();
		this.jobId = jobId;
		this.jobDescription = jobDescription;
		this.position = position;
		this.jobTitle = jobTitle;
		this.reportingManager = reportingManager;
		this.jobType = jobType;
		this.experience = experience;
		this.dateOfJobPosting = dateOfJobPosting;
		this.isJobOpenToApply = isJobOpenToApply;
		this.status = status;
	}

	public AppliedDTO(String jobId, String jobDescription, String position, String jobTitle, String reportingManager,
			String jobType, String experience, String dateOfJobPosting, String isJobOpenToApply, String status)
			throws ParseException {
		super();
		this.jobId = Integer.parseInt(jobId);
		this.jobDescription = jobDescription;
		this.position = position;
		this.jobTitle = jobTitle;
		this.reportingManager = reportingManager;
		this.jobType = jobType;
		this.experience = experience;
		this.dateOfJobPosting = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfJobPosting);
		this.isJobOpenToApply = Boolean.parseBoolean(isJobOpenToApply);
		this.status = status;
	}

	/**
	 * 
	 */
	public AppliedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}

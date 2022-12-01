package ijp.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
// Entity class for Job details that are posted
@Entity
@Table(name = "job_post")
public final class JobPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer jobId;
	
	@NotBlank
	private String jobDescription;
	
	@NotBlank
	private String position;
	
//	@NotBlank
//	private String tenure;
//	
	@NotBlank
	private String jobTitle;
	
	@NotBlank
	private String reportingManager;
	
	@NotBlank
	private String jobType;

	private Integer experience;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfJobPosting = new Date();
	
	@JsonProperty
	private boolean isJobOpenToApply;

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

//	public String getTenure() {
//		return tenure;
//	}

//	public void setTenure(String tenure) {
//		this.tenure = tenure;
//	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Date getDateOfJobPosting() {
		return dateOfJobPosting;
	}

	public void setDateOfJobPosting(Date dateOfJobPosting) {
		this.dateOfJobPosting = dateOfJobPosting;
	}

	public boolean isJobOpenToApply() {
		return isJobOpenToApply;
	}

	public void setJobOpenToApply(boolean isJobOpenToApply) {
		this.isJobOpenToApply = isJobOpenToApply;
	}

	@Override
	public String toString() {
		return "JobPost [jobId=" + jobId + ", jobDescription=" + jobDescription + ", position=" + position +
				 ", jobTitle=" + jobTitle + ", reportingManager=" + reportingManager + ", jobType=" + jobType
				+ ", experience=" + experience + ", dateOfJobPosting=" + dateOfJobPosting + ", isJobOpenToApply="
				+ isJobOpenToApply + "]";
	}
	
	
}

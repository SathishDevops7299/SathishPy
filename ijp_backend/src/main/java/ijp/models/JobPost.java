package ijp.models;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "jobpost")
public class JobPost {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "job_id")
private int jobId;

@OneToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "user_id")
private User user;

@Column(name = "job_name")
private String jobName;

@Column(name = "job_desc")
@Size(max = 1000)
private String jobDesc;

@Column(name = "job_position")
private String jobPosition;

@Column(name = "job_type")
private String jobType;

@Column(name = "reporting_manager")
private String reportingManager;

@Column(name = "job_experience")
private int jobExperience;

@Column(name = "post_date")
@Temporal(TemporalType.DATE)
private Date jobDate = new Date();

@JsonProperty
@Column(name = "is_job_open")
private boolean isJobOpen;
public JobPost() {
	super();
	// TODO Auto-generated constructor stub
}
public JobPost(int jobId, User user, String jobName, String jobDesc, String jobPosition, String jobType,
		String reportingManager, int jobExperience, Date jobDate, boolean isJobOpen) {
	super();
	this.jobId = jobId;
	this.user = user;
	this.jobName = jobName;
	this.jobDesc = jobDesc;
	this.jobPosition = jobPosition;
	this.jobType = jobType;
	this.reportingManager = reportingManager;
	this.jobExperience = jobExperience;
	this.jobDate = jobDate;
	this.isJobOpen = isJobOpen;
}
public int getJobId() {
	return jobId;
}
public void setJobId(int jobId) {
	this.jobId = jobId;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public String getJobName() {
	return jobName;
}
public void setJobName(String jobName) {
	this.jobName = jobName;
}
public String getJobDesc() {
	return jobDesc;
}
public void setJobDesc(String jobDesc) {
	this.jobDesc = jobDesc;
}
public String getJobPosition() {
	return jobPosition;
}
public void setJobPosition(String jobPosition) {
	this.jobPosition = jobPosition;
}

public String getJobType() {
	return jobType;
}
public void setJobType(String jobType) {
	this.jobType = jobType;
}
public String getReportingManager() {
	return reportingManager;
}
public void setReportingManager(String reportingManager) {
	this.reportingManager = reportingManager;
}
public int getJobExperience() {
	return jobExperience;
}
public void setJobExperience(int jobExperience) {
	this.jobExperience = jobExperience;
}
public Date getJobDate() {
	return jobDate;
}
public void setJobDate(Date jobDate) {
	this.jobDate = jobDate;
}
public boolean isJobOpen() {
	return isJobOpen;
}
public void setJobOpen(boolean isJobOpen) {
	this.isJobOpen = isJobOpen;
}
@Override
public String toString() {
	return "JobPost [jobId=" + jobId + ", user=" + user + ", jobName=" + jobName + ", jobDesc=" + jobDesc
			+ ", jobPosition=" + jobPosition + ", jobType=" + jobType + ", reportingManager=" + reportingManager
			+ ", jobExperience=" + jobExperience + ", jobDate=" + jobDate + ", isJobOpen=" + isJobOpen + "]";
}

}

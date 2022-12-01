package ijp.models;

import java.util.Date;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "application")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private int applicationId;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "job_id")
	private JobPost job;
	
	@JsonProperty
	@Column(name = "select_status")
	private boolean selectStatus;
	
	@Column(name = "apply_date")
	@Temporal(TemporalType.DATE)
	private Date applyDate=new Date();

	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JobPost getJob() {
		return job;
	}

	public void setJob(JobPost job) {
		this.job = job;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApply_date(Date applyDate) {
		this.applyDate = applyDate;
	}

	public boolean isSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(boolean selectStatus) {
		this.selectStatus = selectStatus;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", user=" + user + ", job=" + job + ", Selected="
				+ selectStatus + ", applyDate=" + applyDate + "]";
	}
	
}

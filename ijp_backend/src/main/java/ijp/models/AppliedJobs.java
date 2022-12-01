package ijp.models;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;

import org.springframework.data.annotation.LastModifiedDate;

@NamedNativeQuery(name = "AppliedJobs.getJobAppliedStatusByEmail", query = "select distinct j.job_id, j.job_description,j.position,j.job_title,\n"
		+ "j.reporting_manager,j.job_type,j.experience, j.date_of_job_posting,j.is_job_open_to_apply ,\n"
		+ "a.status from job_post j, applied_jobs a\n" + "where a.email= :email and j.job_id = a.job_id\n" + "\n"
		+ "union all\n" + "\n" + "select distinct j.job_id, j.job_description,j.position,j.job_title,\n"
		+ "j.reporting_manager,j.job_type,j.experience, j.date_of_job_posting,j.is_job_open_to_apply ,\n"
		+ "null as status from job_post j, applied_jobs a\n"
		+ "where j.job_id not in (select a.job_id from applied_jobs a where a.email = :email)", resultSetMapping = "JobAppliedMapping")
@SqlResultSetMapping(name = "JobAppliedMapping", classes = @ConstructorResult(targetClass = AppliedDTO.class, columns = {
		@ColumnResult(name = "job_id", type = String.class),
		@ColumnResult(name = "job_description", type = String.class),
		@ColumnResult(name = "position", type = String.class), @ColumnResult(name = "job_title", type = String.class),
		@ColumnResult(name = "reporting_manager", type = String.class),
		@ColumnResult(name = "job_type", type = String.class), @ColumnResult(name = "experience", type = String.class),
		@ColumnResult(name = "date_of_job_posting", type = String.class),
		@ColumnResult(name = "is_job_open_to_apply", type = String.class),
		@ColumnResult(name = "status", type = String.class) }))

// Entity class for Job Application details
@Entity
@Table(name = "applied_jobs")
public final class AppliedJobs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// JobId of particular Job Applied
	private Integer jobId;

	@NotBlank
	private String email;

	@NotBlank
	private String name;

	@Column(columnDefinition = "varchar(255) default 'applied'")
	private String status;

	@Lob
	private byte[] resume;

	@NotBlank
	private String fileName;

	@NotBlank
	private String fileType;

	@LastModifiedDate
	private Date dateOfJobApplied = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getDateOfJobApplied() {
		return dateOfJobApplied;
	}

	public void setDateOfJobApplied(Date dateOfJobApplied) {
		this.dateOfJobApplied = dateOfJobApplied;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "AppliedJobs [id=" + id + ", jobId=" + jobId + ", email=" + email + ", name=" + name + ", status="
				+ status + ", resume=" + Arrays.toString(resume) + ", fileName=" + fileName + ", fileType=" + fileType
				+ ", dateOfJobApplied=" + dateOfJobApplied + "]";
	}
}

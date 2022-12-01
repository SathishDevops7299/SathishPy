package ijp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	private String name;

	// cascade will save the role_id when we pass the values by setting all id,
	// name, email, etc
	// Note:By default it eill create role_role_id but if we use JoinColumn its name
	// will me only 'role_id'
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name="resume_name")
	private String fileName;

	@Column(name="resume_type")
	private String fileType;

	@ElementCollection
	@CollectionTable(name = "skill")
	private List<String> skill = new ArrayList<>();

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "dept_id")
	private Department department;

	@Column(unique = true)
	private String email;

	@Lob
	private byte[] resume;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer userId, String name, Role role, String fileName, String fileType, List<String> skill,
			Department department, String email, byte[] resume) {
		super();
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.fileName = fileName;
		this.fileType = fileType;
		this.skill = skill;
		this.department = department;
		this.email = email;
		this.resume = resume;
	}



	public User(byte[] resume) {
		this.resume = resume;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<String> getSkill() {
		return skill;
	}

	public void setSkill(List<String> skill) {
		this.skill = skill;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", role=" + role + ", fileName=" + fileName + ", fileType="
				+ fileType + ", skill=" + skill + ", department=" + department + ", email=" + email + ", resume="
				+ Arrays.toString(resume) + "]";
	}

	

}

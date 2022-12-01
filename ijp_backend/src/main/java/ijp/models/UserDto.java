package ijp.models;

import java.util.List;

// Object Mapper class to save/update skills of user (mapping to IjpUsers.java)
public class UserDto {

	private List<String> skills;

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
}

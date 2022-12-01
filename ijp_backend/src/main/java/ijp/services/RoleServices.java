package ijp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ijp.models.Role;
import ijp.repository.RoleRepository;

@Service
public class RoleServices {

	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> deleteRoles(Integer id){
		roleRepository.deleteById(id);
		return null;
	}
	
	public List<Role> updateRole(Role role,int roleId) {
		role.setRoleId(roleId);;
		roleRepository.save(role);
		return null;
	}
}

package ijp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ijp.models.Department;
import ijp.repository.DeptRepository;

@Service
public class DeptServices {

	@Autowired
	DeptRepository deptRepository;
	
	public List<Department> deleteDepartment(Integer id){
		deptRepository.deleteById(id);
		return null;
	}
	
	public List<Department> updateDepartment(Department department,int roleId) {
		department.setDepartmentId(roleId);
		deptRepository.save(department);
		return null;
	}
}

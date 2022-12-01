package ijp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ijp.ResponseMessage;
import ijp.models.Department;
import ijp.repository.DeptRepository;
import ijp.services.DeptServices;

@RestController

// @RequestMapping("/ijpservices")

public class DeptController {
	@Autowired
	DeptRepository deptRepository;
	
	@Autowired
	DeptServices deptServices;
	
	@GetMapping("/getDept")
    public ResponseEntity<List<Department>> getAllDept() {
		List<Department> departments=deptRepository.findAll();
		if(departments.size()<=0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(departments);
		}
		return ResponseEntity.status(HttpStatus.OK).body(departments);
    }
	
	//get single book by id handler
	@GetMapping("/getDept/{id}")
    public ResponseEntity<Optional<Department>> getDeptById(@PathVariable("id") int id) {
		Optional<Department> roles=deptRepository.findById(id);
    	if(roles==null) {
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roles);
		}
		return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
	
	//post single book handler
	@PostMapping("/saveDept")
	public ResponseEntity<ResponseMessage> saveDeptEntity(@RequestBody Department department){
		Department dept=null;
		try {
			dept=deptRepository.save(department);
			// System.out.println(dept);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ok"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Something went wrong"));
		}
		
	}
	
	//delete book handler
	@GetMapping("/deleteDept/{deptId}")
	public ResponseEntity<ResponseMessage> deleteDeptEntity(@PathVariable("deptId") int id) {
		deptServices.deleteDepartment(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Data Deleted Succesfully"));
    }
	
	//update the data handler
	@PostMapping("/updateDept/{deptId}")
	public ResponseEntity<ResponseMessage> updateDepartment(@RequestBody Department department,@PathVariable("deptId") int id) {
		deptServices.updateDepartment(department,id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Data Updated Succesfully"));
    }


}

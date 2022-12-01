package ijp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
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
import ijp.models.Role;
import ijp.repository.RoleRepository;
import ijp.services.RoleServices;

@RestController

// @RequestMapping("/ijpservices")

public class RoleController {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RoleServices roleServices;
	
	@GetMapping("/getRole")
    public ResponseEntity<List<Role>> getbooks() {
		List<Role> roles=roleRepository.findAll();
		if(roles.size()<=0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roles);
		}
		return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
	
	//get single book by id handler
	@GetMapping("/getRole/{id}")
    public ResponseEntity<Optional<Role>> getRoleById(@PathVariable("id") int id) {
		Optional<Role> roles=roleRepository.findById(id);
    	if(roles==null) {
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roles);
		}
		return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
	
	//post single book handler
	@PostMapping("/saveRole")
	public ResponseEntity<ResponseMessage> saveRoleEntity(@RequestBody Role role){
		Role roles=null;
		try {
			roles=roleRepository.save(role);
			// System.out.println(roles);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ok"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Something went wrong"));
		}
		
	}
	
	@GetMapping("/deleteRole/{roleId}")
	public ResponseEntity<ResponseMessage> deleteRolEntity(@PathVariable("roleId") int id) {
		roleServices.deleteRoles(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Data Deleted Succesfully"));
    }
	
	//update the data handler
	@PostMapping("/updateRole/{roleId}")
	public ResponseEntity<ResponseMessage> updateRoleDetail(@RequestBody Role role,@PathVariable("roleId") int id) {
		roleServices.updateRole(role,id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Data Updated Succesfully"));
    }
	
	
}


package ijp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ijp.models.Department;

public interface DeptRepository extends JpaRepository<Department, Integer> {

}

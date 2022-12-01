package ijp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ijp.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query(value="select * from role where role_id= :id",nativeQuery = true)
	Role checkRole(@Param("id") Integer id);
	
}

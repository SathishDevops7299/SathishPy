package ijp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ijp.models.AdminUsers;

@Repository
public interface AdminUsersRepository extends JpaRepository<AdminUsers, Integer> {

	@Query("select r.roles from AdminUsers r where r.email = :email")
	String findByEmail(@Param("email") String email);
	
	@Query("select r from AdminUsers r where r.email = :email")
	AdminUsers checkEmail(@Param("email") String email);
}

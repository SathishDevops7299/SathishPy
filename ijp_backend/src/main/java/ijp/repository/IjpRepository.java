package ijp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ijp.models.IjpUsers;

// Repository for 'IjpUsers' Entity
@Transactional
public interface IjpRepository extends JpaRepository<IjpUsers, String> {
	
	@Query("select i.email from IjpUsers i")
	List<String> findAllEmails();
	
	@Query("select r from IjpUsers r where r.email = :email")
	IjpUsers checkEmail(@Param("email") String email);
}

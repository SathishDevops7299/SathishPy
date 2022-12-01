package ijp.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ijp.models.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select i from User i where i.email = :email")
	User checkEmail(@Param("email") String email);
	
	@Query("select i from User i where i.userId = :userId")
	User getUserById(@Param("userId") int userId);
	
	@Query(value = "SELECT user_id FROM public.user WHERE email= ?1",nativeQuery = true)
	String getUserId(@Param("email") String emial) ;
	
	@Query(value = "SELECT role_id FROM public.user WHERE email= ?1",nativeQuery = true)
	String getUserRole(@Param("email") String email);
	
	@Query(value = "SELECT resume FROM public.user WHERE email= ?1",nativeQuery = true)
	byte[] getResume(@Param("email") String emial);

	@Query(value = "SELECT resume FROM public.user WHERE user_Id= ?1",nativeQuery = true)
	byte[] getResumeById(@Param("userId") int userId);
	
}

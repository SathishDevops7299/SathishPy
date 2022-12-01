package ijp.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ijp.models.Application;

@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query(value = "select * from application where user_id = :userId and job_id= :jobId",nativeQuery = true)
	List<Application> getApplicationById(@Param("userId") int userId,@Param("jobId") int jobId);
	
	@Query(value = "select * from application where user_id = :userId",nativeQuery = true)
	List<Application> getApplicationByUserId(@Param("userId") int userId);
	
	@Query(value = "select * from application",nativeQuery = true)
	List<Application> getApplicationAll();

	@Modifying
	@Query(value= "update application set select_status= :cond where job_id= :jobId and user_id= :userId",nativeQuery = true)
	Integer updateSelectStatus(@Param("cond")Boolean cond,@Param("jobId")Integer jobId,@Param("userId")Integer userId);
}

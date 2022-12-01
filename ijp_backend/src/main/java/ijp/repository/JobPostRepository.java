package ijp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ijp.models.JobPost;

@Transactional
public interface JobPostRepository extends JpaRepository<JobPost, Integer>{
	@Query("select j from JobPost j where j.jobId = :jobId")
	JobPost findByJobId(@Param("jobId")Integer jobId);

	@Modifying
	@Query(value="delete from public.application where job_id= :jobId",nativeQuery = true)
	Integer delAppliliedJobPost(@Param("jobId")Integer jobId);
	
	@Modifying
	@Query(value= "update jobpost  set is_job_open= :cond where job_id= :jobId",nativeQuery = true)
	Integer updateJobStatus(@Param("cond")Boolean cond,@Param("jobId")Integer jobId);
}

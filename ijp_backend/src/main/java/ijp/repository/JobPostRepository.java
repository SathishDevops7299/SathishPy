package ijp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ijp.models.JobPost;

// Repository for 'JobPost' Entity
@Transactional
public interface JobPostRepository extends JpaRepository<JobPost, Integer>{

	@Query("select j from JobPost j where j.jobId = :jobId")
	JobPost findByJobId(@Param("jobId")Integer jobId);
}

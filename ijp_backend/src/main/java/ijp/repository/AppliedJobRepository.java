package ijp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ijp.models.AppliedDTO;
import ijp.models.AppliedJobs;

// Repository for 'AppliedJobs' Entity
@Transactional
public interface AppliedJobRepository extends JpaRepository<AppliedJobs, Integer> {

	@Query(value = "select a from AppliedJobs a where a.email = :email and a.jobId = :jobId")
	AppliedJobs getJobAppliedByEmail(@Param("email") String email, @Param("jobId") Integer jobId);

	@Query(value = "select a from AppliedJobs a where a.email = :email")
	List<AppliedJobs> getJobsAppliedByEmail(@Param("email") String email);

	@Query(nativeQuery = true)
	List<AppliedDTO> getJobAppliedStatusByEmail(String email);
}

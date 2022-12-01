package ijp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ijp.controllers.ApplicationController;
import ijp.controllers.JobPostController;
import ijp.controllers.UserController;

@SpringBootTest
class IjpBackendApplicationTests {
	@Autowired
	private UserController userController;

	

	@Autowired
	private JobPostController jobPostController;

	
	@Test
	void contextLoads() {
		Assertions.assertThat(userController).isNotNull();
		Assertions.assertThat(jobPostController).isNotNull();
	}
}

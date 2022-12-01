package ijp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ijp.controllers.MainController;

@SpringBootTest
class IjpBackendApplicationTests {

	@Autowired
	private MainController controller;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(controller).isNotNull();
	}
}

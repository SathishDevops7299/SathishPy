package ijp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ijp.models.AdminUsers;
import ijp.repository.AdminUsersRepository;

@SpringBootApplication
public class IjpBackendApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IjpBackendApplication.class, args);
	}

	@Autowired
	private AdminUsersRepository adminUsersRepository;

	@Autowired
    private Config config;
	
	@Override
	public void run(String... args) throws Exception {

		//System.out.println("admin email:"+config.getAdminEmail());

		AdminUsers ad = adminUsersRepository.checkEmail(config.getAdminEmail());
		if (ad==null) {
			AdminUsers adminUsers = new AdminUsers();
			adminUsers.setEmail(config.getAdminEmail());
			adminUsers.setRoles("admin, hiring_manager");
			adminUsersRepository.save(adminUsers);
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(IjpBackendApplication.class);
	}
}

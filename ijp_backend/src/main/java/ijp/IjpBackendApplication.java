package ijp;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import ijp.models.Role;
import ijp.models.User;
import ijp.repository.RoleRepository;
import ijp.repository.UserRepository;

@SpringBootApplication
public class IjpBackendApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IjpBackendApplication.class, args);
	}

	@Autowired
	private Config config;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		Role checkRole = roleRepository.checkRole(1);
		if (checkRole == null) {
			Role admin = new Role();
			admin.setRoleId(1);
			admin.setRoleDesc("Administrator");
			admin.setRoleName("Admin");
			roleRepository.save(admin);

			Role Hr = new Role();
			Hr.setRoleId(2);
			Hr.setRoleDesc("Hiring Manager");
			Hr.setRoleName("Hiring_Manager");
			roleRepository.save(Hr);

			Role employee = new Role();
			employee.setRoleId(3);
			employee.setRoleDesc("Employee");
			employee.setRoleName("Employee");
			roleRepository.save(employee);

			User checkUser = userRepository.checkEmail(config.getAdminEmail());
			if (checkUser == null) {
				User adminSet = new User();
				adminSet.setEmail(config.getAdminEmail());
				adminSet.setRole(admin);
				adminSet.setName(config.getName());;
				userRepository.save(adminSet);
			}
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(IjpBackendApplication.class);
	}
}

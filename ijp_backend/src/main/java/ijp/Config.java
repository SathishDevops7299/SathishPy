package ijp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("project")
public class Config {
 
    private String environment;
    private String adminEmail;
	private String name;
 
    public String getEnvironment() {
        return environment;
    }
 
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminEmail() {
        return adminEmail;
    }
 
    @Override
    public String toString() {
        return "{" + this.getEnvironment() + ", " + this.getAdminEmail() + "}";
    }
}

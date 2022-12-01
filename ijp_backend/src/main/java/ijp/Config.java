package ijp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 
import java.util.ArrayList;
import java.util.List;
 
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("project")
public class Config {
 
    private String environment;
    private String adminEmail;
 
    public String getEnvironment() {
        return environment;
    }
 
    public void setEnvironment(String environment) {
        this.environment = environment;
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
package ijp.oauth2.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Configuration class for securing endpoints, authentication and enable Oauth2.
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ObjectMapper objectMapper;
	private final TokenStore tokenStore;
	private final TokenFilter tokenFilter;

	public SecurityConfig(ObjectMapper objectMapper, TokenStore tokenStore, TokenFilter tokenFilter) {
		this.objectMapper = objectMapper;
		this.tokenStore = tokenStore;
		this.tokenFilter = tokenFilter;
	}
	
	// Securing Endpoints 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
				.authorizeRequests(a -> a.antMatchers("/", "/oauth2/**", "/login/**", "/hello/**")
						.permitAll().anyRequest().authenticated())
				.oauth2Login().authorizationEndpoint().authorizationRequestRepository(new InMemoryRequestRepository())
				.and().successHandler(this::successHandler).and().exceptionHandling()
				.authenticationEntryPoint(this::authenticationEntryPoint).and()
				.logout(cust -> cust.addLogoutHandler((request, response, authentication) -> {
					try {
						logout(request, response, authentication);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).logoutSuccessHandler(this::onLogoutSuccess))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.headers().frameOptions().disable();
	}

	private void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		// You can process token here
		request.getSession().invalidate();
//		System.out.println("Auth token is - " + request.getHeader("Authorization"));
	}

	private void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
	}

	// Cors configuration
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	// Oauth2 login success -> actions to do next
	private void successHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws JsonProcessingException, IOException {
		String token = tokenStore.generateToken(authentication);
		response.getWriter().write(objectMapper.writeValueAsString(Collections.singletonMap("accessToken", token)));
	}

	// error for unauthenticated accesss to any endpoint/resource
	private void authenticationEntryPoint(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws JsonProcessingException, IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter()
				.write(objectMapper.writeValueAsString(Collections.singletonMap("error", "Unauthenticated")));
	}
}

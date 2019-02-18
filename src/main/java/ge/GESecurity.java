package ge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class GESecurity extends WebSecurityConfigurerAdapter {
	protected static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();

	protected JdbcUserDetailsManager userManager;

	@Autowired
	DataSource dataSource;

	@PostConstruct
	void createUserManager () {
		userManager = new JdbcUserDetailsManager ();
		userManager.setDataSource (dataSource);
		if (!userManager.userExists ("x")) {
			userManager.createUser (User.withUsername ("x")
			                            .password (passwordEncoder.encode (""))
			                            .roles ("USER")
			                            .build ());
		}
	}

	//@Bean
	public PasswordEncoder passwordEncoder () {
		return passwordEncoder;
	}

	//@Bean
	public JdbcUserDetailsManager userManager () {
		return userManager;
	}

	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService (userManager).passwordEncoder (passwordEncoder);
	}

	@Override
	protected void configure (HttpSecurity http)
	throws Exception {
		http.authorizeRequests ()
		    .antMatchers ("/propertysearch/**", "/PApublicServiceProxy/**")
		    .authenticated ()
		    .anyRequest ()
		    .permitAll ()
		    .and ()
			.headers ()
			.frameOptions ()
		    .sameOrigin ()
		    .and ()
		    .formLogin ()
		    .loginPage ("/login")
		    .and ()
		    .logout ()
		    .logoutRequestMatcher (new AntPathRequestMatcher ("/logout", "GET"));
	}
}

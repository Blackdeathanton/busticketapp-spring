package com.busticketapp.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private DataSource dataSource;
	
	private final String USERS_QUERY = "SELECT email, password, active FROM user WHERE email=?";
	private final String ROLES_QUERY = "SELECT u.email, r.role from user u INNER JOIN user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id = r.role_id) where u.email = ?";
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery(USERS_QUERY)
			.authoritiesByUsernameQuery(ROLES_QUERY)
			.dataSource(dataSource)
			.passwordEncoder(bCrypt);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/dashboard/*").hasAnyAuthority("ADMIN").anyRequest()
			.authenticated().and().csrf().disable()
			.formLogin().loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/dashboard")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.and().rememberMe()
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(60*60)
			.and().exceptionHandling().accessDeniedPage("/accessDenied");
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}

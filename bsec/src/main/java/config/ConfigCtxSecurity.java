package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ConfigCtxSecurity extends WebSecurityConfigurerAdapter {

	protected void memoryAuth(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("andrew").password("aaa").roles("ADMIN","EMPLOYEE")
			.and()
			.withUser("m001").password("111").roles("MANAGER")
			.and()
			.withUser("a").password("a").roles("GUEST");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		memoryAuth(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		securityFilterDefault(http);
	}
	
	protected void securityFilterDefault(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}
	
}

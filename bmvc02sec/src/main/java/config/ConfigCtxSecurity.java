package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class ConfigCtxSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		memoryAuth(auth);
	}
	protected void memoryAuth(AuthenticationManagerBuilder auth) throws Exception{
		//define memory auth.
		auth.inMemoryAuthentication()
			.withUser("andrew").password("aaa").roles("ADMIN","EMPLOYEE")
			.and()
			.withUser("m001").password("111").roles("MANAGER")
			.and()
			.withUser("guest").password("ggg").roles("GUEST")
			.and()
			.withUser("a").password("a").roles("GUEST");
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		http.addFilterBefore(encodingFilter, CsrfFilter.class);

		httpSimple(http);
	}

	protected void httpSimple(HttpSecurity http) throws Exception {
		//this is a simple setup for pages.
		http.authorizeRequests() // 1. to setup authorize request.
				.anyRequest().authenticated() // 2. all paget auths
				.and().formLogin() // 3. use default formLogin Page.
				.and().httpBasic(); // 4. can support http basic authentication.
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		
	}

}
